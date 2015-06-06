package com.antonio.android.appwebequipo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Antonio on 18/04/2015.
 */
public class Adaptador extends ArrayAdapter<Equipo> {
    private Context contexto;
    private ArrayList<Equipo> lista;
    private int recurso;
    private static LayoutInflater i;
    private ViewHolder vh;

    static class ViewHolder {
        public TextView tv;
        public int posicion;
    }

    public Adaptador(Context context, int resource, ArrayList<Equipo> objects) {
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
            vh.tv = (TextView) convertView.findViewById(R.id.tvNE);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.posicion = position;
        vh.tv.setText(lista.get(position).getNombre().toString());
        return convertView;
    }

}