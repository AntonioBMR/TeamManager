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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NuevoUsuario extends Activity {
    EditText user, pass, pass1;
    Button bCrear;
    Httppostaux post;
    String IP_Server = "192.168.1.110";//IP DE NUESTRO PC
    String URL_connect = "http://" + IP_Server + "/bdequipos/adduser.php";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_usuario);
        post = new Httppostaux();
        user = (EditText) findViewById(R.id.usuarioAU);
        pass = (EditText) findViewById(R.id.passwordAU);
        pass1 = (EditText) findViewById(R.id.password1AU);
        bCrear = (Button) findViewById(R.id.btSignup);
    }

    public void agregarusuario(View v) {
        String us = user.getText().toString();
        String ps = pass.getText().toString();
        String ps1 = pass1.getText().toString();
        if (us.isEmpty() || ps.isEmpty() || ps1.isEmpty()) {
            tostada(getString(R.string.toast_contranoigual));
        } else if (!ps.equals(ps1)) {
            tostada(getString(R.string.toast_rellena));
        } else {
            new asyncNuevoUsuario().execute(us, ps);
        }
    }
    public void tostada(String s) {
        Toast toast1 = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast1.show();
    }

    public int agregarUser(String username, String password) {
        int estadoagregado = -1;
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("usuario", username));
        postparameters2send.add(new BasicNameValuePair("password", password));
        JSONArray jdata = post.getserverdata(postparameters2send, URL_connect);
        SystemClock.sleep(150);

        //si lo que obtuvimos no es null
        if (jdata != null && jdata.length() > 0) {

            JSONObject json_data; //creamos un objeto JSON
            try {
                json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
                estadoagregado = json_data.getInt("useradd");//accedemos al valor
                Log.e("useradd", "useradd= " + estadoagregado);//muestro por log que obtuvimos
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //validamos el valor obtenido
            if (estadoagregado == 0) {// [{"logstatus":"0"}]
                Log.e("useradd ", "invalido");
                return 0;
            }

            ///capturar idUsuario
            else if (estadoagregado == 1) {// [{"logstatus":"1"}]
                Log.e("useradd ", "valido");
                return 1;
            }
        } else {    //json obtenido invalido verificar parte WEB.
            Log.e("JSON  ", "ERROR");
            return 2;
        }
        return estadoagregado;
    }

    class asyncNuevoUsuario extends AsyncTask<String, String, String> {
        String user, pass;
        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(NuevoUsuario.this);
            pDialog.setMessage(getString(R.string.progress_enviando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            user = params[0];
            pass = params[1];
            if (agregarUser(user, pass) == 1) {
                return "1"; //login valido
            } else if (agregarUser(user, pass) == 0) {
                return "0"; //login invalido
            } else {
                return "2";
            }
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=", "" + result);
            if (result.equals("1")) {
                tostada(getString(R.string.toast_usuarioreg));
                Intent i = new Intent(NuevoUsuario.this, Login.class);
                i.putExtra("user", user);
                startActivity(i);
            } else if (result.equals("0")) {
                tostada(getString(R.string.toast_usuarioigual));
            } else {
                System.out.println("error");
            }
        }
    }
}
