package com.antonio.android.appwebequipo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Antonio on 04/05/2015.
 */
public class AdaptadorUsuario extends ArrayAdapter<Usuario> {
    private Context contexto;
    private ArrayList<Usuario> lista;
    private int recurso;
    private static LayoutInflater i;
    private ViewHolder vh;

    static class ViewHolder {
        public LinearLayout ll;
        public TextView tv, tv1;
        public int posicion;
    }

    public AdaptadorUsuario(Context context, int resource, ArrayList<Usuario> objects) {
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
            vh.ll = (LinearLayout) convertView.findViewById(R.id.llDConv);
            vh.tv = (TextView) convertView.findViewById(R.id.tvNombreConvocatoria);
            vh.tv1 = (TextView) convertView.findViewById(R.id.tvValConvocatoria);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.posicion = position;
        if (position % 2 == 0) {
            vh.ll.setBackgroundColor(Color.GRAY);
        }
        vh.tv.setText(lista.get(position).getNombre().toString());
        vh.tv1.setText(lista.get(position).getValoracion().toString());
        return convertView;
    }

}