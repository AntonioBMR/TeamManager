package com.antonio.android.appwebequipo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Antonio on 11/05/2015.
 */
public class AdaptadorEstadisticas extends ArrayAdapter<DatoEstadisticas> {
    private Context contexto;
    private ArrayList<DatoEstadisticas> lista;
    private int recurso;
    private static LayoutInflater i;
    private ViewHolder vh;

    static class ViewHolder {
        public TextView tv, tv1, tv2, tv3;
        public int posicion;
    }

    public AdaptadorEstadisticas(Context context, int resource, ArrayList<DatoEstadisticas> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista = objects;
        this.recurso = resource;
        this.i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.tvFechaE);
            vh.tv1 = (TextView) convertView.findViewById(R.id.tvNombreE);
            vh.tv2 = (TextView) convertView.findViewById(R.id.tvValE);
            vh.tv3 = (TextView) convertView.findViewById(R.id.tvNombreeE);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.posicion = position;
        vh.tv.setText(lista.get(position).getFecha().toString());
        vh.tv1.setText(lista.get(position).getNombre().toString());
        vh.tv2.setText(lista.get(position).getValoracion().toString());
        vh.tv3.setText(lista.get(position).getNombree().toString());
        return convertView;
    }

}