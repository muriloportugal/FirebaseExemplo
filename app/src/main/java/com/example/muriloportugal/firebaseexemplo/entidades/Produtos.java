package com.example.muriloportugal.firebaseexemplo.entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Produtos implements Parcelable {

    private String altura;
    private String largura;
    private String peso;
    private String cor;
    private String filtro;
    private Double valor;

    public Produtos() {
    }

    protected Produtos(Parcel in) {
        altura = in.readString();
        largura = in.readString();
        peso = in.readString();
        cor = in.readString();
        filtro = in.readString();
        if (in.readByte() == 0) {
            valor = null;
        } else {
            valor = in.readDouble();
        }
    }

    public static final Creator<Produtos> CREATOR = new Creator<Produtos>() {
        @Override
        public Produtos createFromParcel(Parcel in) {
            return new Produtos(in);
        }

        @Override
        public Produtos[] newArray(int size) {
            return new Produtos[size];
        }
    };

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getLargura() {
        return largura;
    }

    public void setLargura(String largura) {
        this.largura = largura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getFiltro() {
        filtro = "";
        if(!getAltura().equals("")) filtro += "_"+getAltura().replaceAll("[.,]","");
        if(!getLargura().equals("")) filtro += "_"+getLargura().replaceAll("[.,]","");
        if(!getPeso().equals("")) filtro += "_"+getPeso().replaceAll("[.,]","");
        if(!getCor().equals("")) filtro += "_"+getCor().replaceAll("[.,]","");
        return filtro;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(altura);
        dest.writeString(largura);
        dest.writeString(peso);
        dest.writeString(cor);
        dest.writeString(filtro);
        if (valor == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(valor);
        }
    }

}
