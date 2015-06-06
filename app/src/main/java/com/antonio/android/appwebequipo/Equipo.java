package com.antonio.android.appwebequipo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Antonio on 18/04/2015.
 */
public class Equipo implements Parcelable, Comparable<Equipo> {
    private String id;
    private String nombre;
    private String idu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

    public Equipo(String idu, String nombre) {

        this.idu = idu;
        this.nombre = nombre;
    }

    public Equipo(String id, String nombre, String idu) {

        this.id = id;
        this.nombre = nombre;
        this.idu = idu;
    }

    @Override
    public int compareTo(Equipo another) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipo)) return false;

        Equipo equipo = (Equipo) o;

        if (id != null ? !id.equals(equipo.id) : equipo.id != null) return false;
        if (idu != null ? !idu.equals(equipo.idu) : equipo.idu != null) return false;
        if (nombre != null ? !nombre.equals(equipo.nombre) : equipo.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (idu != null ? idu.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", idu='" + idu + '\'' +
                '}';
    }

    public Equipo(JSONObject object) {
        try {
            this.id = object.getInt("ide") + "";
            this.nombre = object.getString("nombree");
            this.idu = object.getInt("idusuario") + "";

        } catch (Exception ex) {
        }
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("ide", this.id);
            object.put("idusuario", this.idu);
            object.put("nombree", this.nombre);
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
        dest.writeString(id);
        dest.writeString(idu);
        dest.writeString(nombre);

    }

    public static final Parcelable.Creator<Equipo> CREATOR = new Parcelable.Creator<Equipo>() {

        @Override
        public Equipo createFromParcel(Parcel source) {
            return new Equipo(source);
        }

        @Override
        public Equipo[] newArray(int size) {
            return new Equipo[size];
        }

    };

    private Equipo(Parcel source) {
        this.id = source.readString();
        this.idu = source.readString();
        this.nombre = source.readString();

    }
}
