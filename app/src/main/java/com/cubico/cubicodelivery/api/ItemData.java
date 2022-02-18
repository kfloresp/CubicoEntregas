package com.cubico.cubicodelivery.api;

import android.graphics.drawable.Drawable;

public class ItemData {
    private String Texto1;
    private String Texto2;
    private String Texto3;
    private String TextoId;
    private Drawable imagen;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    private String urlImage;

    public ItemData(String texto1, String texto2, String texto3, String textoId, Drawable imagen,String urlimage) {
        Texto1 = texto1;
        Texto2 = texto2;
        Texto3 = texto3;
        TextoId = textoId;
        this.imagen = imagen;
        urlimage=urlimage;
    }

    public ItemData() {
        super();
    }

    public String getTexto1() {
        return Texto1;
    }

    public void setTexto1(String texto1) {
        Texto1 = texto1;
    }

    public String getTexto2() {
        return Texto2;
    }

    public void setTexto2(String texto2) {
        Texto2 = texto2;
    }

    public String getTexto3() {
        return Texto3;
    }

    public void setTexto3(String texto3) {
        Texto3 = texto3;
    }

    public String getTextoId() {
        return TextoId;
    }

    public void setTextoId(String textoId) {
        TextoId = textoId;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }
}
