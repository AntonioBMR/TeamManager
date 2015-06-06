package com.antonio.android.appwebequipo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class Estadisticas extends Activity {
    private ArrayList<DatoEstadisticas> datose;
    AdaptadorEstadisticas ae;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        Bundle extras = getIntent().getExtras();
        datose = new ArrayList<DatoEstadisticas>();

        if (extras != null) {
            datose = extras.getParcelableArrayList("estadisticas");
        }
        if (!datose.isEmpty()) {
            lv = (ListView) findViewById(R.id.lvEstadisticas);
            ae = new AdaptadorEstadisticas(this, R.layout.detalle_estadistica, datose);
            lv.setAdapter(ae);
        }
    }

}
