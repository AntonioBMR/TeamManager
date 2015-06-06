package com.antonio.android.appwebequipo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Antonio on 11/05/2015.
 */
public class DatoEstadisticas implements Parcelable, Comparable<DatoEstadisticas> {
    private String fecha;
    private String nombre;
    private String valoracion;
    private String nombree;

    public DatoEstadisticas() {
    }

    public DatoEstadisticas(String fecha, String nombre, String valoracion, String nombree) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.valoracion = valoracion;
        this.nombree = nombree;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getNombree() {
        return nombree;
    }

    public void setNombree(String nombree) {
        this.nombree = nombree;
    }

    public String getFecha() {

        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "DatoEstadisticas{" +
                "fecha='" + fecha + '\'' +
                ", nombre='" + nombre + '\'' +
                ", valoracion='" + valoracion + '\'' +
                ", nombree='" + nombree + '\'' +
                '}';
    }

    public DatoEstadisticas(JSONObject object) {
        try {
            this.fecha = object.getString("fecha") + "";
            this.nombre = object.getString("nombre");
            this.valoracion = object.getString("valoracion") + "";
            this.nombree = object.getString("nombree") + "";

        } catch (Exception ex) {
        }
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("fecha", this.fecha);
            object.put("nombre", this.nombre);
            object.put("valoracion", this.valoracion);
            object.put("nombree", this.nombree);
            return object;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fecha);
        dest.writeString(nombre);
        dest.writeString(valoracion);
        dest.writeString(nombree);

    }

    public static final Parcelable.Creator<DatoEstadisticas> CREATOR = new Parcelable.Creator<DatoEstadisticas>() {

        @Override
        public DatoEstadisticas createFromParcel(Parcel source) {
            return new DatoEstadisticas(source);
        }

        @Override
        public DatoEstadisticas[] newArray(int size) {
            return new DatoEstadisticas[size];
        }

    };

    private DatoEstadisticas(Parcel source) {
        this.fecha = source.readString();
        this.nombre = source.readString();
        this.valoracion = source.readString();
        this.nombree = source.readString();

    }

    @Override
    public int compareTo(DatoEstadisticas another) {
        return 0;
    }
}
