package com.cubico.cubicodelivery.model;

import java.util.ArrayList;

public class ConfirmarPedidoModel {
    private int intIdActividad;
    private  String strIdTx;
    private String strCx;
    private String strCy;
    private int intIdEstado;
    private int intIdCausal;
    private String strObservacion;
    private String imgRtFirma;
    private String imgRtPhoto;
    private ArrayList<String> imgRtPhoto2;
    private boolean FlagDevolucion;


    public ArrayList<String> getImgRtPhoto2() {
        return imgRtPhoto2;
    }

    public void setImgRtPhoto2(ArrayList<String> imgRtPhoto2) {
        this.imgRtPhoto2 = imgRtPhoto2;
    }


    //private String imgRtPhoto;


    public boolean getFlagDevolucion() {
        return FlagDevolucion;
    }

    public void setFlagDevolucion(boolean flagDevolucion) {
        FlagDevolucion = flagDevolucion;
    }



    public int getIntIdActividad() {
        return intIdActividad;
    }

    public void setIntIdActividad(int intIdActividad) {
        this.intIdActividad = intIdActividad;
    }

    public String getStrIdTx() {
        return strIdTx;
    }

    public void setStrIdTx(String strIdTx) {
        this.strIdTx = strIdTx;
    }

    public String getStrCx() {
        return strCx;
    }

    public void setStrCx(String strCx) {
        this.strCx = strCx;
    }

    public String getStrCy() {
        return strCy;
    }

    public void setStrCy(String strCy) {
        this.strCy = strCy;
    }

    public int getIntIdEstado() {
        return intIdEstado;
    }

    public void setIntIdEstado(int intIdEstado) {
        this.intIdEstado = intIdEstado;
    }

    public int getIntIdCausal() {
        return intIdCausal;
    }

    public void setIntIdCausal(int intIdCausal) {
        this.intIdCausal = intIdCausal;
    }

    public String getStrObservacion() {
        return strObservacion;
    }

    public void setStrObservacion(String strObservacion) {
        this.strObservacion = strObservacion;
    }

    public String getImgRtFirma() {
        return imgRtFirma;
    }

    public void setImgRtFirma(String imgRtFirma) {
        this.imgRtFirma = imgRtFirma;
    }

    public String getImgRtPhoto() {
        return imgRtPhoto;
    }

    public void setImgRtPhoto(String imgRtPhoto) {
        this.imgRtPhoto = imgRtPhoto;
    }
}
