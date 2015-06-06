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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MisEquipos extends Activity {
    private ListView lv;
    private ListView lv1;
    private Adaptador a;
    private AdaptadorPeticiones ap;
    private ArrayList<Jugador> compis;
    private ArrayList<Equipo> equipos;
    private ArrayList<PeticionXAceptar> pets;
    private String iduser;
    private int posicion;
    private int posicion1;
    String IP_Server = "192.168.1.110";//IP DE NUESTRO PC
    String URL_connect = "http://" + IP_Server + "/bdequipos/miscompis.php";//ruta en donde estan nuestros archivos
    String URL_connect1 = "http://" + IP_Server + "/bdequipos/aceptarpeticion.php";//ruta en donde estan nuestros archivos
    Httppostaux post;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_equipos);
        post = new Httppostaux();
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        pets = new ArrayList<PeticionXAceptar>();
        equipos = new ArrayList<Equipo>();
        if (extras != null) {
            if (!extras.getParcelableArrayList("misequipos").isEmpty()) {
                System.out.println(equipos.toString() + " copon");
                equipos = extras.getParcelableArrayList("misequipos");//lista de equipos del user
            }
            if (!extras.getParcelableArrayList("mispeticiones").isEmpty()) {
                System.out.println(pets.toString() + " copon");
                pets = extras.getParcelableArrayList("mispeticiones");//lista de peticiones
            }
            iduser = extras.getString("iduser");
            System.out.println("parametros");
            TextView tv = (TextView) findViewById(R.id.tvPetME);
            TextView tv1 = (TextView) findViewById(R.id.tvEquipME);
            TextView tv2 = (TextView) findViewById(R.id.tvJugME);
            tv.setVisibility(View.INVISIBLE);
            tv1.setVisibility(View.INVISIBLE);
            tv2.setVisibility(View.INVISIBLE);
            if (!pets.isEmpty()) {
                tv.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);

                ap = new AdaptadorPeticiones(this, R.layout.detalle_peticiones, pets);
                lv1 = (ListView) findViewById(R.id.lvPeticiones);
                lv1.setAdapter(ap);
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        posicion = position;
                        AlertDialog.Builder builder = new AlertDialog.Builder(MisEquipos.this);
                        builder.setTitle("el jugador desea jugar en su equipo");
                        builder.setMessage("desea agregarlo");
                        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                posicion1 = position;
                                System.out.println("posicion1" + posicion1);
                                new asyncAceptarPeticiones().execute("1", iduser, pets.get(position).getIdj(), pets.get(position).getIde());

                                pets.remove(posicion1);
                                ap.notifyDataSetChanged();
                                dialog.dismiss();
                            }

                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                posicion1 = position;
                                System.out.println("position1" + posicion1);

                                new asyncAceptarPeticiones().execute("0", iduser, pets.get(position).getIdj(), pets.get(position).getIde());
                                pets.remove(posicion1);

                                ap.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }
            if (!equipos.isEmpty()) {
                lv = (ListView) findViewById(R.id.lvMisEquipos);
                a = new Adaptador(this, R.layout.detalle_equipo, equipos);
                lv.setAdapter(a);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        new asyncMisCompis().execute(equipos.get(position).getIdu().toString(), equipos.get(position).getId());
                    }
                });
            }

        } else {
            equipos = null;
        }
    }

    //CLASE QUE UNE jUGADOR Y EQUIPO
    public int misCompis(String idu, String ide) {
        int estadoagregado = -1;
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("idu", idu + ""));
        postparameters2send.add(new BasicNameValuePair("ide", ide + ""));
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
        System.out.println("miequipo " + jdata.toString());
        SystemClock.sleep(150);
        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            compis = new ArrayList<Jugador>();
            try {
                for (int i = 0; i < jdata.length(); i++) {
                    json_data = jdata.getJSONObject(i);
                    System.out.println(" miequipo" + json_data.toString());
                    Jugador a = new Jugador(json_data);
                    compis.add(a);
                    System.out.println("miequipo " + compis.get(i).toString());
                }
                if (compis.isEmpty()) {
                    estadoagregado = 0;
                } else {
                    estadoagregado = 1;
                }
                Log.e("addequipo", "addequipo= " + estadoagregado);//muestro por log que obtuvimos
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Log.e("JSON  ", "ERROR");
            estadoagregado = 0;
            return estadoagregado;
        }
        return estadoagregado;
    }

    class asyncMisCompis extends AsyncTask<String, String, String> {
        String idu, ide;
        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(MisEquipos.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            idu = params[0];
            ide = params[1];
            System.out.println("datos doin" + idu + ide);
            if (misCompis(idu, ide) == 1) {
                return "1"; //hay datos
            } else {
                return "0";
            }
        }

        protected void onPostExecute(String l) {
            pDialog.dismiss();//ocultamos progess dialog.
            if (l.equals("1")) {
                System.out.println(" datos " + equipos.get(0).getId() + " " + equipos.get(0).getIdu());
                Intent i = new Intent(MisEquipos.this, MisCompisDeEquipo.class);
                i.putParcelableArrayListExtra("miscompis", compis);
                i.putExtra("iduser", idu);
                i.putExtra("ide", ide);
                startActivity(i);
            } else {
                tostada(getString(R.string.toast_nojugadoresdispo));
            }
        }

    }

    public int aceptarPeticiones(String sino, String idu, String idj, String ide) {
        int estadoagregado = -1;
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        System.out.println("parametros " + idu + " " + idj + " " + ide);
        postparameters2send.add(new BasicNameValuePair("sino", sino + ""));
        postparameters2send.add(new BasicNameValuePair("ida", idu + ""));
        postparameters2send.add(new BasicNameValuePair("idj", idj + ""));
        postparameters2send.add(new BasicNameValuePair("ide", ide + ""));
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect1);
        SystemClock.sleep(150);
        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            try {
                json_data = jdata.getJSONObject(0);
                System.out.println(" prueba" + json_data.toString());
                if (json_data.getInt("peticion") == 0) {
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

    class asyncAceptarPeticiones extends AsyncTask<String, String, String> {

        String sino, idu, ide, idj;

        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(MisEquipos.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            sino = params[0];
            idu = params[1];
            idj = params[2];
            ide = params[3];
            System.out.println("datos doin" + idu + ide);
            if (aceptarPeticiones(sino, idu, idj, ide) == 1) {
                return "1"; //hay datos
            } else {
                return "0";
            }
        }
        protected void onPostExecute(String l) {
            pDialog.dismiss();//ocultamos progess dialog.
            if (l.equals("1")) {
                System.out.println("posicion1 as" + posicion1);
            } else {
                tostada("error");
            }
        }
    }

    public void tostada(String s) {
        Toast toast1 = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast1.show();
    }
}
