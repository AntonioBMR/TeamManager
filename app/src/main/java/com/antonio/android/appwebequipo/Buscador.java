package com.antonio.android.appwebequipo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Buscador extends Activity {
    private ListView lv;
    private Adaptador a;
    private String idj;
    private int posicion;
    private ArrayList<Equipo> equipos;
    String IP_Server = "192.168.1.110";//IP Del servidor
    String URL_connect = "http://" + IP_Server + "/bdequipos/peticion.php";//ruta en donde estan nuestros archivos
    Httppostaux post;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        post = new Httppostaux();
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) {
            equipos = extras.getParcelableArrayList("busqueda");//idusuario
            idj = extras.getString("idu");
            lv = (ListView) findViewById(R.id.lvBusquedaE);
            a = new Adaptador(this, R.layout.detalle_equipo, equipos);
            lv.setAdapter(a);
        } else {
            equipos = null;
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ida = equipos.get(position).getIdu();
                String idu = idj;
                String ide = equipos.get(position).getId();
                posicion = position;
                new asyncPeticion().execute(ida, idu, ide);
            }
        });
    }

    /*********************************************************/
    /************PETICIONES usuario a administrador***********/
    /**
     * *****************************************************
     */

    public int enviarPeticion(String ida, String idj, String ide) {
        int estadoagregado = -1;
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("ida", ida + ""));
        postparameters2send.add(new BasicNameValuePair("idj", idj + ""));
        postparameters2send.add(new BasicNameValuePair("ide", ide + ""));
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
        SystemClock.sleep(150);
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            try {
                json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
                estadoagregado = json_data.getInt("peticion");//accedemos al valor
                System.out.println("pasajson" + estadoagregado);
                Log.e("loginstatus", "logstatus= " + estadoagregado);//muestro por log que obtuvimos
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //validamos el valor obtenido
            if (estadoagregado == 0) {// [{"logstatus":"0"}]
                Log.e("loginstatus ", "invalido");
                return 0;
            } else {// [{"logstatus":"1"}]
                Log.e("loginstatus ", "valido");
                return estadoagregado;
            }
        } else {    //json obtenido invalido verificar parte WEB.
            Log.e("JSON  ", "ERROR");
            return 0;
        }
    }

    /**
     * hilo
     */
    class asyncPeticion extends AsyncTask<String, String, String> {
        String ida, idu, ide;

        protected void onPreExecute() {
            pDialog = new ProgressDialog(Buscador.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            ida = params[0];
            idu = params[1];
            ide = params[2];
            System.out.println("datos doin" + ida + idu + ide);
            if (enviarPeticion(ida, idu, ide) == 1) {
                return 1 + ""; //hay datos
            } else {
                return 0 + "";
            }
        }

        protected void onPostExecute(String lista) {
            pDialog.dismiss();//ocultamos progess dialog.
            if (lista.equals("1")) {
                tostada(getString(R.string.toast_peticionrealizada));
                equipos.remove(posicion);
                a.notifyDataSetChanged();
            } else {
                tostada(getString(R.string.toast_peticionnorealizada));
            }
        }
    }

    public void tostada(String s) {
        Toast toast1 = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast1.show();
    }
}
