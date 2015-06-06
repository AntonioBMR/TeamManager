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
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*PANTALLA DE BIENVENIDA*/
public class HiScreen extends Activity {
    String user;
    String iduser;
    String nombreEquipo;
    TextView txt_usr, logoff;
    private ArrayList<Equipo> lista;
    private ArrayList<PeticionXAceptar> pets;
    String IP_Server = "192.168.1.110";
    String URL_connect = "http://" + IP_Server + "/bdequipos/addequipo.php";
    String URL_connect1 = "http://" + IP_Server + "/bdequipos/mispeticiones.php";
    String URL_connect2 = "http://" + IP_Server + "/bdequipos/deleteusuario.php";

    Httppostaux post;
    boolean result_back;
    private ProgressDialog pDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_screen);
        pets = new ArrayList<PeticionXAceptar>();
        lista = new ArrayList<Equipo>();
        txt_usr = (TextView) findViewById(R.id.usr_name);
        logoff = (TextView) findViewById(R.id.logoff);
        post = new Httppostaux();
        int valoracion = 0;
        Bundle extras = getIntent().getExtras();
        //Obtenemos datos enviados en el intent.
        if (extras != null) {
            user = extras.getString("user");//usuario
            iduser = extras.getString("idu");//idusuario
            valoracion = extras.getInt("valoracion");//valoracion
        } else {
            user = "error";
        }

        txt_usr.setText(user + " " + getString(R.string.tv_media) + valoracion);//cambiamos texto al nombre del usuario logueado

        logoff.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //'cerrar  sesion' nos regresa a la ventana anterior.
                finish();
            }
        });
    }
    /**********************************************************/
    /************************BOTONES***************************/
    /**
     * ******************************************************
     */
    public void misEquipos(View v) {

        new asyncRecibirPeticiones().execute(iduser);
//        tostada(lista.toString());
        /*Intent i=new Intent (HiScreen.this, Login.class);
        i.putExtra("idu",iduser);*/
    }

    public void borrarUsuario(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿ desea eliminar sus datos ?");
// Set up the buttons
        builder.setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new asyncBorrar().execute(iduser);
                tostada(getString(R.string.toast_usuborrado));
            }
        });
        builder.setNegativeButton(getString(R.string.boton_cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void buscador(View v) {
        new asyncBusqueda().execute(iduser);
    }

    public void verEstadisticas(View v) {
        Intent i = new Intent(HiScreen.this, Formulario.class);
        i.putExtra("iduser", iduser);
        startActivity(i);
    }

    public void crearequipo(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.hint_nombreequipo));
// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
// Set up the buttons
        builder.setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nombreEquipo = input.getText().toString();
                if (!nombreEquipo.isEmpty()) {
                    new asyncNuevoEquipo().execute(nombreEquipo, iduser);
                    dialog.dismiss();
                } else {
                    tostada(getString(R.string.toast_intronombre));
                }
            }
        });
        builder.setNegativeButton(getString(R.string.boton_cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void chat(View v) {
      /*  Client.SERVERIP="192.168.1.110";
        Intent intent = new Intent(getBaseContext(), Chat.class);
        intent.putExtra("user",user);
        //Log.e("ServerIP", Client.SERVERIP);
        startActivity(intent);*/
    }

    //Definimos que para cuando se presione la tecla BACK no volvamos para atras
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // no hacemos nada.
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**********************************************************/
    /************************aASYNCTASK***************************/
    /**
     * ******************************************************
     */
    public int agregarEquipo(String club, String idu) {
        int estadoagregado = -1;
        /*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("nombreequipo", club));
        postparameters2send.add(new BasicNameValuePair("idusuario", idu));
        //realizamos una peticion y como respuesta obtenes un array JSON
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
        SystemClock.sleep(150);
        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            try {
                json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
                estadoagregado = json_data.getInt("addequipo");//accedemos al valor
                Log.e("addequipo", "addequipo= " + estadoagregado);//muestro por log que obtuvimos
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //validamos el valor obtenido
            if (estadoagregado == 0) {// [{"logstatus":"0"}]
                Log.e("addequipo ", "invalido");
                return 0;
            }
            ///capturar idUsuario
            else if (estadoagregado == 1) {// [{"logstatus":"1"}]
                Log.e("addequipo ", "valido");
                return 1;
            }
        } else {    //json obtenido invalido verificar parte WEB.
            Log.e("JSON  ", "ERROR");
            return 2;
        }
        return estadoagregado;
    }

    class asyncNuevoEquipo extends AsyncTask<String, String, String> {
        String idu, club;

        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(HiScreen.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            //obtnemos usr y pass
            club = params[0];
            idu = params[1];
            //user=params[2];
            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (agregarEquipo(club, idu) == 1) {
                return "1"; //login valido
            } else if (agregarEquipo(club, idu) == 0) {
                return "0"; //login invalido
            } else {
                return "2";
            }
        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
        pasamos a la sig. activity
        o mostramos error*/
        protected void onPostExecute(String result) {
            pDialog.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=", "" + result);
            if (result.equals("1")) {
                tostada("equipo registrado");

            } else if (result.equals("0")) {
                tostada("equipo error");

            } else {
                tostada("ya existe equipo con ese nombre");
            }
        }
    }

    public int misEquipos(String idu) {
        int estadoagregado = -1;
    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("idusuario", idu));
        //postparameters2send.add(new BasicNameValuePair("user",user));
        //realizamos una peticion y como respuesta obtenes un array JSON
        String URL_connect = "http://" + IP_Server + "/bdequipos/misequipos.php";//ruta en donde estan nuestros archivos
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
        SystemClock.sleep(150);
        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            lista = new ArrayList<Equipo>();
            try {
                for (int i = 0; i < jdata.length(); i++) {
                    //jdata.getJSONArray(i).toString();
                    json_data = jdata.getJSONObject(i);
                    System.out.println(" miequipo" + json_data.toString());
                    Equipo a = new Equipo(json_data);
                    //  System.out.println("Miequipo log"+a.toString());
                    lista.add(a);
                    System.out.println("miequipo " + lista.get(i).toString());
                }
                if (!lista.isEmpty()) {
                    estadoagregado = 1;
                } else {
                    estadoagregado = 0;
                }                //estadoagregado=json_data.getInt("addequipo");//accedemos al valor
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

    class asyncMisEquipos extends AsyncTask<String, String, String> {
        String idu, club;

        protected void onPreExecute() {

        }

        protected String doInBackground(String... params) {
            //obtnemos idu
            idu = params[0];
            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (misEquipos(idu) == 1) {
                return "1"; //hay datos
            } else {
                return "2";
            }
        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
        pasamos a la sig. activity
        o mostramos error*/
        protected void onPostExecute(String l) {
            //  pDialog.dismiss();//ocultamos progess dialog.
            // Log.e("onPostExecute=",""+result);
            if (l.equals("1")) {
                Intent i = new Intent(HiScreen.this, MisEquipos.class);
                i.putParcelableArrayListExtra("misequipos", lista);
                i.putParcelableArrayListExtra("mispeticiones", pets);
                i.putExtra("iduser", iduser);
                startActivity(i);
            } else {
                tostada(getString(R.string.toast_noequipo));
            }
        }

    }

    public int buscarequipos(String idu) {
        int estadoagregado = -1;
    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("idusuario", idu));
        //realizamos una peticion y como respuesta obtenes un array JSON
        String URL_connect = "http://" + IP_Server + "/bdequipos/buscarequipo.php";//ruta en donde estan nuestros archivos
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
        SystemClock.sleep(150);
        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            lista = new ArrayList<Equipo>();
            try {
                for (int i = 0; i < jdata.length(); i++) {
                    json_data = jdata.getJSONObject(i);
                    System.out.println(" miequipo" + json_data.toString());
                    Equipo a = new Equipo(json_data);
                    lista.add(a);
                    System.out.println("miequipo " + lista.get(i).toString());
                }
                if (!lista.isEmpty()) {
                    estadoagregado = 1;
                } else {
                    estadoagregado = 0;
                }
                //estadoagregado=json_data.getInt("addequipo");//accedemos al valor
                Log.e("addequipo", "addequipo= " + estadoagregado);//muestro por log que obtuvimos
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                estadoagregado = 2;
            }

        } else {    //json obtenido invalido verificar parte WEB.
            Log.e("JSON  ", "ERROR");
            return 2;
        }
        return estadoagregado;
    }

    class asyncBusqueda extends AsyncTask<String, String, String> {

        String idu;

        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(HiScreen.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            //obtnemos idu
            idu = params[0];
            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (buscarequipos(idu) == 1) {
                return "1"; //hay datos
            } else {
                return "0";
            }
        }

        protected void onPostExecute(String r) {
            if (r.equals("1")) {
                pDialog.dismiss();//ocultamos progess dialog.
                Intent i = new Intent(HiScreen.this, Buscador.class);
                i.putParcelableArrayListExtra("busqueda", lista);
                i.putExtra("idu", idu);
                startActivity(i);
            } else {
                pDialog.dismiss();
                tostada(getString(R.string.toast_noequipodispo));
            }
        }

    }

    public int misPeticiones(String idu) {
        int estadoagregado = -1;
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("ida", idu + ""));
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect1);
        SystemClock.sleep(150);
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            pets = new ArrayList<PeticionXAceptar>();
            try {
                for (int i = 0; i < jdata.length(); i++) {
                    json_data = jdata.getJSONObject(i);
                    PeticionXAceptar p = new PeticionXAceptar(json_data);
                    pets.add(p);
                }
                if (pets.isEmpty()) {
                    estadoagregado = 0;
                } else {
                    estadoagregado = 1;
                }
                //estadoagregado=json_data.getInt("addequipo");//accedemos al valor
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

    class asyncRecibirPeticiones extends AsyncTask<String, String, String> {

        String idu;

        protected void onPreExecute() {

        }

        protected String doInBackground(String... params) {
            //obtnemos idu
            idu = params[0];
            System.out.println("datos doin" + idu);
            if (misPeticiones(idu) == 1) {
                return "1"; //hay datos
            } else {
                return "0";
            }
        }

        protected void onPostExecute(String l) {
            new asyncMisEquipos().execute(iduser);
        }
    }

    public int borrar(String idu) {
        int estadoagregado = -1;
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("idusuario", idu + ""));
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect2);
        SystemClock.sleep(150);
        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {
            JSONObject json_data; //creamos un objeto JSON
            try {
                json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
                estadoagregado = json_data.getInt("borrado");//accedemos al valor
                System.out.println("prueba " + json_data.toString() + "  " + json_data.getInt("borrado"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("JSON  ", "ERROR");
            estadoagregado = 0;
            return estadoagregado;
        }
        return estadoagregado;
    }

    class asyncBorrar extends AsyncTask<String, String, String> {
        String idu;

        protected void onPreExecute() {
            pDialog = new ProgressDialog(HiScreen.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            idu = params[0];
            if (borrar(idu) == 1) {
                return "1"; //hay datos
            } else {
                return "0";
            }
        }

        protected void onPostExecute(String l) {
            pDialog.dismiss();//ocultamos progess dialog.
            // Log.e("onPostExecute=",""+result);
            if (l.equals("1")) {
                tostada(getString(R.string.toast_usuborrado));
                Intent i = new Intent(HiScreen.this, Login.class);
                startActivity(i);
            } else if (l.equals("2")) {
                tostada("error server ");
            } else {
                tostada("error");
            }
        }
    }

    /**********************************************************/
    /************************TOSTADA***************************/
    /**
     * ******************************************************
     */
    public void tostada(String s) {
        Toast toast1 = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast1.show();
    }

}
