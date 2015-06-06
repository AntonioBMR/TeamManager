package com.antonio.android.appwebequipo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class Convocatoria extends Activity {
    private ListView lv;
    private AdaptadorUsuario a;
    String ide, iduser;
    private ArrayList<Usuario> compis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convocatoria);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            compis = extras.getParcelableArrayList("compisC");//compañeros de equipo
            ide = extras.getString("ide");//id  equipo
            iduser = extras.getString("iduser");//id user
            System.out.println("prueba " + compis.get(0).toString());
            lv = (ListView) findViewById(R.id.lvConvocatoria);
            a = new AdaptadorUsuario(this, R.layout.detalle_convocatoria, compis);
            lv.setAdapter(a);
        }
    }
}
