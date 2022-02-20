package com.example.examen.Contactos;

public class Paises {

    private Integer id;
    private String pais;
    private String codigopais;

    public Paises(String pais, Integer id, String codigopais) {
        this.id = id;
        this.pais = pais;
        this.codigopais = codigopais;
    }//Conatactos con parametros

    public Paises() {

    }//constructor vacio

    public Paises(String valueOf, int parseInt) {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigopais() {
        return codigopais;
    }

    public String setCodigopais(String codigopais) {
        this.codigopais = codigopais;
        return codigopais;
    }
}
