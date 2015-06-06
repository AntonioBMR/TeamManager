package com.antonio.android.appwebequipo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Antonio on 21/04/2015.
 */
public class AdaptadorPeticiones extends ArrayAdapter<PeticionXAceptar> {
    private Context contexto;
    private ArrayList<PeticionXAceptar> lista;
    private int recurso;
    private static LayoutInflater i;
    private ViewHolder vh;

    static class ViewHolder {
        public TextView tv, tv1;
        public int posicion;
    }

    public AdaptadorPeticiones(Context context, int resource, ArrayList<PeticionXAceptar> objects) {
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
            vh.tv1 = (TextView) convertView.findViewById(R.id.tvNJ);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.posicion = position;
        vh.tv.setText(lista.get(position).getNombree().toString());
        vh.tv1.setText(lista.get(position).getNombre().toString());
        return convertView;
    }

}