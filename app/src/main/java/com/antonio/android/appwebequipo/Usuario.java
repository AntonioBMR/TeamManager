package com.antonio.android.appwebequipo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Antonio on 21/04/2015.
 */
public class Usuario implements Parcelable, Comparable<Usuario> {
    String idu, nombre, passw, valoracion;

    public Usuario() {
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idu='" + idu + '\'' +
                ", nombre='" + nombre + '\'' +
                ", passw='" + passw + '\'' +
                ", valoracion='" + valoracion + '\'' +
                '}';
    }

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        if (idu != null ? !idu.equals(usuario.idu) : usuario.idu != null) return false;
        if (nombre != null ? !nombre.equals(usuario.nombre) : usuario.nombre != null) return false;
        if (passw != null ? !passw.equals(usuario.passw) : usuario.passw != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idu != null ? idu.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (passw != null ? passw.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Usuario another) {
        return 0;
    }

    public Usuario(JSONObject object) {
        try {
            this.idu = object.getInt("idu") + "";
            this.nombre = object.getString("nombreu");
            this.valoracion = object.getInt("valoracion") + "";

        } catch (Exception ex) {
        }
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("idu", this.idu);
            object.put("nombreu", this.nombre);
            object.put("valoracion", this.valoracion);
            return object;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {

        @Override
        public Usuario createFromParcel(Parcel source) {
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }

    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idu);
        dest.writeString(nombre);
        dest.writeString(valoracion);
    }

    private Usuario(Parcel source) {
        this.idu = source.readString();
        this.nombre = source.readString();
        this.valoracion = source.readString();

    }
}
