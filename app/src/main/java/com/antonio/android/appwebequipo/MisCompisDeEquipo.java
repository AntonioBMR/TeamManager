package com.antonio.android.appwebequipo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MisCompisDeEquipo extends Activity {
    private ListView lv;
    private AdaptadorJugadores a;
    String ide, iduser;
    int valoracion;
    int posicion;
    private ArrayList<Jugador> compis;
    private ArrayList<Usuario> compisConv;
    String IP_Server = "192.168.1.110";
    String URL_connect = "http://" + IP_Server + "/bdequipos/voto.php";//ruta en donde estan nuestros archivos
    String URL_connect1 = "http://" + IP_Server + "/bdequipos/convocatoria.php";//ruta en donde estan nuestros archivos
    Httppostaux post;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_compis_de_equipo);
        Bundle extras = getIntent().getExtras();
        post = new Httppostaux();
        compis = new ArrayList<Jugador>();
        if (extras != null) {
            compis = extras.getParcelableArrayList("miscompis");//compañeros de equipo
            ide = extras.getString("ide");//id  equipo
            System.out.println("prueba ide " + ide);
            iduser = extras.getString("iduser");//id user
        } else {
            compis = null;
        }
        a = new AdaptadorJugadores(this, R.layout.detalle_jugador, compis);
        lv = (ListView) findViewById(R.id.lvCompis);
        lv.setAdapter(a);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                posicion = position;
                String nombrev = compis.get(position).getNombrej();
                AlertDialog.Builder builder = new AlertDialog.Builder(MisCompisDeEquipo.this);
                builder.setTitle("Valore a " + nombrev);
                final NumberPicker np = new NumberPicker(MisCompisDeEquipo.this);
                np.setMaxValue(10);
                np.setMinValue(0);
                builder.setView(np);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        posicion = position;
                        valoracion = np.getValue();
                        String ideequipo = ide;
                        String iduv = iduser;
                        String idj = compis.get(position).getIdu() + "";
                        System.out.println("prueba " + idj);
                        dialog.dismiss();
                        new asyncMiVoto().execute(iduv, idj, ideequipo, valoracion + "");

                    }

                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        posicion = position;
                        // new asyncAceptarPeticiones().execute("0",iduser,pets.get(position).getIdj(),pets.get(position).getIde());
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mis_compis_de_equipo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_convocatoria) {
            new asyncConvocatoria().execute(ide);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void tostada(String s) {
        Toast toast1 = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast1.show();
    }

    //MI VOTO
    public int miVoto(String iduv, String idu, String ide, String val) {
        int voto = 0;
    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
         * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("iduv", iduv + ""));
        postparameters2send.add(new BasicNameValuePair("idu", idu + ""));
        postparameters2send.add(new BasicNameValuePair("ide", ide + ""));
        postparameters2send.add(new BasicNameValuePair("val", val + ""));
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
        SystemClock.sleep(450);
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            try {
                System.out.println("prueba " + jdata.toString());
                json_data = jdata.getJSONObject(0);
                voto = json_data.getInt("voto");//accedemos al valor
                System.out.println("prueba " + voto);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (voto == 1) {// [{"logstatus":"0"}]
                Log.e("loginstatus ", "invalido");
                return voto;
            } else if (voto == 2) {
                return voto;
            } else {
                return 0;
            }
        } else {    //json obtenido invalido verificar parte WEB.
            Log.e("JSON  ", "ERROR");
            voto = 0;
            return voto;
        }
    }

    class asyncMiVoto extends AsyncTask<String, String, String> {

        String iduv, idu, val, ide;

        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(MisCompisDeEquipo.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            //obtnemos usr y pass
            iduv = params[0];
            idu = params[1];
            ide = params[2];
            val = params[3];
            System.out.println("datos doin" + idu + ide);
            //user=params[2];
            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (miVoto(iduv, idu, ide, val) == 1) {
                return "1"; //hay datos
            } else if (miVoto(iduv, idu, ide, val) == 2) {
                return "2";
            } else {
                return "0";
            }

        }

        protected void onPostExecute(String l) {
            pDialog.dismiss();//ocultamos progess dialog.
            // Log.e("onPostExecute=",""+result);
            if (l.equals("1")) {
                tostada(getString(R.string.toast_votoreg));
            } else if (l.equals("2")) {
                tostada(getString(R.string.toast_votonoreg));
            } else {
                tostada(getString(R.string.toast_errorvotoreg));
            }
        }
    }

    public int convocatoria(String ide) {
        int estadoagregado = 0;
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("ide", ide + ""));
        System.out.println("prueba ide metconv " + ide);
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect1);
        SystemClock.sleep(150);
        compisConv = new ArrayList<Usuario>();
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            try {
                for (int i = 0; i < jdata.length(); i++) {
                    json_data = jdata.getJSONObject(i);
                    Usuario a = new Usuario(json_data);
                    System.out.println("prueba log" + json_data.toString());
                    System.out.println("prueba log" + a.toString());
                    compisConv.add(a);
                }
                if (compis.isEmpty()) {
                    estadoagregado = 0;
                    System.out.println("esta vacio el ioputa prueba");
                } else {
                    estadoagregado = 1;
                }
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

    class asyncConvocatoria extends AsyncTask<String, String, String> {
        String ide;
        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(MisCompisDeEquipo.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            ide = params[0];
            if (convocatoria(ide) == 1) {
                return "1"; //hay datos
            } else {
                return "0";
            }
        }
        protected void onPostExecute(String l) {
            pDialog.dismiss();//ocultamos progess dialog.
            // Log.e("onPostExecute=",""+result);
            if (l.equals("1")) {
                Intent i = new Intent(MisCompisDeEquipo.this, Convocatoria.class);
                i.putParcelableArrayListExtra("compisC", compisConv);
                System.out.println("prueba" + compisConv.get(0).toString());
                i.putExtra("ide", ide);
                i.putExtra("idu", iduser);
                startActivity(i);
            } else {
                tostada(getString(R.string.toast_noconvocat));
            }

        }

    }
}
