package com.antonio.android.appwebequipo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Antonio on 19/04/2015.
 */
public class Jugador implements Parcelable, Comparable<Jugador> {
    private String idj, idu, ide, nombrej, adm;

    public Jugador() {
    }

    public Jugador(String idu, String ide, String nombrej) {
        this.idu = idu;
        this.ide = ide;
        this.nombrej = nombrej;
    }

    public Jugador(String idj, String idu, String ide, String nombrej, String adm) {
        this.idj = idj;
        this.idu = idu;
        this.ide = ide;
        this.nombrej = nombrej;
        this.adm = adm;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "idj='" + idj + '\'' +
                ", idu='" + idu + '\'' +
                ", ide='" + ide + '\'' +
                ", nombrej='" + nombrej + '\'' +
                ", adm='" + adm + '\'' +
                '}';
    }

    public String getIdj() {
        return idj;
    }

    public void setIdj(String idj) {
        this.idj = idj;
    }

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getNombrej() {
        return nombrej;
    }

    public void setNombrej(String nombrej) {
        this.nombrej = nombrej;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;

        Jugador jugador = (Jugador) o;

        if (adm != null ? !adm.equals(jugador.adm) : jugador.adm != null) return false;
        if (ide != null ? !ide.equals(jugador.ide) : jugador.ide != null) return false;
        if (idj != null ? !idj.equals(jugador.idj) : jugador.idj != null) return false;
        if (idu != null ? !idu.equals(jugador.idu) : jugador.idu != null) return false;
        if (nombrej != null ? !nombrej.equals(jugador.nombrej) : jugador.nombrej != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idj != null ? idj.hashCode() : 0;
        result = 31 * result + (idu != null ? idu.hashCode() : 0);
        result = 31 * result + (ide != null ? ide.hashCode() : 0);
        result = 31 * result + (nombrej != null ? nombrej.hashCode() : 0);
        result = 31 * result + (adm != null ? adm.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Jugador another) {
        return 0;
    }

    public Jugador(JSONObject object) {
        try {
            this.idj = object.getInt("idj") + "";
            this.nombrej = object.getString("nombrej");
            this.idu = object.getInt("idu") + "";
            this.ide = object.getInt("ide") + "";
            this.adm = object.getInt("adm") + "";

        } catch (Exception ex) {
        }
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("idj", this.idj);
            object.put("nombrej", this.nombrej);
            object.put("idu", this.idu);
            object.put("ide", this.ide);
            object.put("adm", this.adm);
            return object;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Jugador> CREATOR = new Parcelable.Creator<Jugador>() {

        @Override
        public Jugador createFromParcel(Parcel source) {
            return new Jugador(source);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }

    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idj);
        dest.writeString(nombrej);
        dest.writeString(idu);
        dest.writeString(ide);
        dest.writeString(adm);
    }

    private Jugador(Parcel source) {
        this.idj = source.readString();
        this.nombrej = source.readString();
        this.idu = source.readString();
        this.ide = source.readString();
        this.adm = source.readString();

    }
}
