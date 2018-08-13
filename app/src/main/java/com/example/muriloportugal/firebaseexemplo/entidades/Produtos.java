package com.example.muriloportugal.firebaseexemplo.entidades;

public class Produtos {

    private String altura;
    private String largura;
    private String peso;
    private String cor;
    private String filtro;
    private Double valor;

    public Produtos() {
    }

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

    public String getValorSting() {
        return String.valueOf(valor);
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

//    public boolean isEmpty(){
//        if (!getAltura().equals("") && !getLargura().equals("") && !getPeso().equals("") && !getCor().equals("") && !getValorSting().equals("")){
//            return false;
//        }
//        return true;
//    }
}
