package com.antonio.android.appwebequipo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Formulario extends Activity {
    private RadioButton rbFecha, rbValoracion, rbEquipo, rbAsc, rbDesc, rbYo, rbTodos, rbMisE, rbEqu, rbJugador;
    private NumberPicker npD, npH;
    private Button bt;
    private EditText et;
    private ProgressDialog pDialog;
    private ArrayList<DatoEstadisticas> datose;
    private String IP_Server = "192.168.1.110";
    private String URL_connect = "http://" + IP_Server + "/bdequipos/estadisticas.php";
    private Httppostaux post;
    private String orden, desc, nombre, valmin, valmax,
            idu, filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        post = new Httppostaux();
        orden = 1 + "";
        filtro = 1 + "";
        bt = (Button) findViewById(R.id.btEstadisticas);
        et = (EditText) findViewById(R.id.etNombre);
        rbFecha = (RadioButton) findViewById(R.id.rbFecha);
        rbValoracion = (RadioButton) findViewById(R.id.rbValoracion);
        rbEquipo = (RadioButton) findViewById(R.id.rbEquipo);
        rbAsc = (RadioButton) findViewById(R.id.rbAsc);
        rbDesc = (RadioButton) findViewById(R.id.rbDesc);
        npD = (NumberPicker) findViewById(R.id.npD);
        npH = (NumberPicker) findViewById(R.id.npH);
        rbYo = (RadioButton) findViewById(R.id.rbYo);
        rbTodos = (RadioButton) findViewById(R.id.rbTodos);
        rbMisE = (RadioButton) findViewById(R.id.rbMisEquipos);
        rbEqu = (RadioButton) findViewById(R.id.rbMiEquipo);
        rbJugador = (RadioButton) findViewById(R.id.rbJugador);
        rbMisE.setChecked(false);
        rbEqu.setChecked(false);
        rbJugador.setChecked(false);
        rbYo.setChecked(false);
        rbTodos.setChecked(false);
        rbEquipo.setChecked(false);
        rbValoracion.setChecked(false);
        rbFecha.setChecked(false);
        rbDesc.setChecked(false);
        rbAsc.setChecked(false);
        npD.setMaxValue(10);
        npH.setMaxValue(10);
        npD.setMinValue(0);
        npH.setMinValue(0);
        npD.setValue(0);
        npH.setValue(10);
        et.setVisibility(View.INVISIBLE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idu = extras.getString("iduser");
        }
        datose = new ArrayList<DatoEstadisticas>();
    }

    //FILTRAR POR
    public void yo(View v) {
        rbMisE.setChecked(false);
        rbEqu.setChecked(false);
        rbJugador.setChecked(false);
        rbYo.setChecked(true);
        et.setVisibility(View.INVISIBLE);
        filtro = 0 + "";
        rbTodos.setChecked(false);

    }

    public void buscaJugador(View v) {
        rbMisE.setChecked(false);
        rbEqu.setChecked(false);
        rbYo.setChecked(false);
        et.setEnabled(true);
        et.setVisibility(View.VISIBLE);
        rbJugador.setChecked(true);
        et.setHint(R.string.hint_nombrejugador);
        filtro = 4 + "";
        rbTodos.setChecked(false);

    }

    public void buscaEquipo(View v) {
        rbMisE.setChecked(false);
        rbYo.setChecked(false);
        rbJugador.setChecked(false);
        et.setEnabled(true);
        et.setVisibility(View.VISIBLE);
        et.setHint(getString(R.string.hint_nombreequipo));
        rbEqu.setChecked(true);
        filtro = 3 + "";
        rbTodos.setChecked(false);

    }

    public void misEquipos(View v) {
        et.setVisibility(View.INVISIBLE);
        rbEqu.setChecked(false);
        rbYo.setChecked(false);
        rbJugador.setChecked(false);
        et.setEnabled(false);
        rbMisE.setChecked(true);
        filtro = 2 + "";
        rbTodos.setChecked(false);

    }

    public void todos(View v) {
        et.setVisibility(View.INVISIBLE);
        rbEqu.setChecked(false);
        rbYo.setChecked(false);
        rbJugador.setChecked(false);
        et.setEnabled(false);
        rbMisE.setChecked(false);
        rbTodos.setChecked(true);
        filtro = 1 + "";
    }

    //ORDENAR POR
    public void fecha(View v) {
        rbEquipo.setChecked(false);
        rbValoracion.setChecked(false);
        rbFecha.setChecked(true);
        orden = 0 + "";
    }

    public void valoracion(View v) {
        rbEquipo.setChecked(false);
        rbFecha.setChecked(false);
        rbValoracion.setChecked(true);
        orden = 1 + "";
    }

    public void equipo(View v) {
        rbFecha.setChecked(false);
        rbValoracion.setChecked(false);
        rbEquipo.setChecked(true);
        orden = 2 + "";
    }

    //ORDEN ascendete o descendente
    public void ascendente(View v) {
        rbDesc.setChecked(false);
        desc = 0 + "";
    }

    public void descendente(View v) {
        rbAsc.setChecked(false);
        desc = 1 + "";
    }

    //boton nueva Actividad con la las estadisticas
    public void misEstadisticas(View v) {
        valmax = +npH.getValue() + "";
        valmin = +npD.getValue() + "";
        System.out.println("datose antes async " + orden + " " + desc + " " + nombre + " " + valmin + " " + valmax + " " +
                idu + " " + filtro);
        nombre = et.getText().toString();
        new asyncEstadisticas().execute(orden, desc, nombre, valmin, valmax,
                idu, filtro);

    }


    /////////////////////////////////////////////////////////////////////////////////
    //////////////**************ASYNCTASK ESTADISTICAS***************////////////////
    /////////////////////////////////////////////////////////////////////////////////
    public int estadisticas(String orden, String desc, String nombre, String valmin, String valmax,
                            String idu, String filtro) {
        int estadoagregado = -1;
        /*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("orden", orden + ""));
        postparameters2send.add(new BasicNameValuePair("desc", desc + ""));
        postparameters2send.add(new BasicNameValuePair("nombre", nombre + ""));
        postparameters2send.add(new BasicNameValuePair("valmin", valmin));
        postparameters2send.add(new BasicNameValuePair("valmax", valmax));
        postparameters2send.add(new BasicNameValuePair("idu", idu));
        postparameters2send.add(new BasicNameValuePair("filtro", filtro));
        //realizamos una peticion y como respuesta obtenes un array JSON
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
        System.out.println("miequipo " + jdata.toString());
        SystemClock.sleep(150);
        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {
            System.out.println("datose no null");
            JSONObject json_data; //creamos un objeto JSON
            datose = new ArrayList<DatoEstadisticas>();
            try {
                for (int i = 0; i < jdata.length(); i++) {
                    //jdata.getJSONArray(i).toString();
                    json_data = jdata.getJSONObject(i);
                    System.out.println(" datose" + json_data.toString());
                    DatoEstadisticas a = new DatoEstadisticas(json_data);
                    //  System.out.println("Miequipo log"+a.toString());
                    datose.add(a);
                    System.out.println("datose " + datose.get(i).toString());
                }
                if (datose.isEmpty()) {
                    estadoagregado = 0;
                } else {
                    estadoagregado = 1;
                }
                Log.e("addequipo", "addequipo= " + estadoagregado);//muestro por log que obtuvimos
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {    //json obtenido invalido verificar parte WEB.
            Log.e("JSON  ", "ERROR");
            estadoagregado = 0;
            return estadoagregado;
        }
        return estadoagregado;
    }

    class asyncEstadisticas extends AsyncTask<String, String, String> {

        String orden, desc, nombre, valmin, valmax,
                idu, filtro;

        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(Formulario.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            //obtenemos los datos del array.
            orden = params[0];
            desc = params[1];
            nombre = params[2];
            valmin = params[3];
            valmax = params[4];
            idu = params[5];
            filtro = params[6];
            System.out.println("datos " + idu);
            //user=params[2];
            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (estadisticas(orden, desc, nombre, valmin, valmax,
                    idu, filtro) == 1) {
                return "1"; //hay datos
            } else {
                return "0";
            }

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
        pasamos a la sig. activity
        o mostramos error*/
        protected void onPostExecute(String l) {

            pDialog.dismiss();//ocultamos progess dialog.
            if (l.equals("1")) {
                Intent i = new Intent(Formulario.this, Estadisticas.class);
                i.putParcelableArrayListExtra("estadisticas", datose);
                startActivity(i);

            } else {
                tostada(getString(R.string.nohaydatos));
            }
        }

    }

    public void tostada(String s) {
        Toast toast1 = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast1.show();
    }
}
