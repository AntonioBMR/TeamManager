package com.antonio.android.appwebequipo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Antonio on 23/04/2015.
 */
public class PeticionXAceptar implements Parcelable, Comparable<PeticionXAceptar> {
    String ide, idj, nombre, nombree;

    @Override
    public String toString() {
        return "PeticionXAceptar{" +
                "ide='" + ide + '\'' +
                ", nombree='" + nombree + '\'' +
                ", idj='" + idj + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
    public PeticionXAceptar() {

    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getIdj() {
        return idj;
    }

    public void setIdj(String idj) {
        this.idj = idj;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombree() {
        return nombree;
    }

    public void setNombree(String nombree) {
        this.nombree = nombree;
    }

    @Override
    public int compareTo(PeticionXAceptar another) {
        return 0;
    }

    public PeticionXAceptar(JSONObject object) {
        try {
            this.nombree = object.getString("nombree");
            this.ide = object.getInt("ide") + "";
            this.nombre = object.getString("nombre");
            this.idj = object.getInt("idj") + "";
        } catch (Exception ex) {
        }
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("nombree", this.nombree);
            object.put("ide", this.ide);
            object.put("nombre", this.nombre);
            object.put("idj", this.idj);

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
        dest.writeString(nombree);
        dest.writeString(ide);
        dest.writeString(nombre);
        dest.writeString(idj);

    }

    public static final Parcelable.Creator<PeticionXAceptar> CREATOR = new Parcelable.Creator<PeticionXAceptar>() {

        @Override
        public PeticionXAceptar createFromParcel(Parcel source) {
            return new PeticionXAceptar(source);
        }

        @Override
        public PeticionXAceptar[] newArray(int size) {
            return new PeticionXAceptar[size];
        }

    };

    private PeticionXAceptar(Parcel source) {
        this.nombree = source.readString();
        this.ide = source.readString();
        this.nombre = source.readString();
        this.idj = source.readString();

    }
}
