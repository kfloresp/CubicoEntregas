package com.cubico.cubicodelivery.helper;

import android.graphics.Color;

public class ItemData {
    private String Texto1;
    private String Texto2;
    private String Texto3;
    private String TextoId;
    private int coloBack;
    private String txtTempo;

    private int intData1;
    private int intData2;
    private int intData3;
    private double doubleData1;
    private double doubleData2;
    private double doubleData3;

    public ItemData(String texto1, String texto2, String texto3,String textoId) {
        Texto1 = texto1;
        Texto2 = texto2;
        Texto3 = texto3;
        TextoId=textoId;
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

    public int getColoBack() {
        return coloBack;
    }

    public void setColoBack(int coloBack) {
        this.coloBack = coloBack;
    }

    public String getTxtTempo() {
        return txtTempo;
    }

    public void setTxtTempo(String txtTempo) {
        this.txtTempo = txtTempo;
    }

    public int getIntData1() {
        return intData1;
    }

    public void setIntData1(int intData1) {
        this.intData1 = intData1;
    }

    public int getIntData2() {
        return intData2;
    }

    public void setIntData2(int intData2) {
        this.intData2 = intData2;
    }

    public int getIntData3() {
        return intData3;
    }

    public void setIntData3(int intData3) {
        this.intData3 = intData3;
    }

    public double getDoubleData1() {
        return doubleData1;
    }

    public void setDoubleData1(double doubleData1) {
        this.doubleData1 = doubleData1;
    }

    public double getDoubleData2() {
        return doubleData2;
    }

    public void setDoubleData2(double doubleData2) {
        this.doubleData2 = doubleData2;
    }

    public double getDoubleData3() {
        return doubleData3;
    }

    public void setDoubleData3(double doubleData3) {
        this.doubleData3 = doubleData3;
    }
}
