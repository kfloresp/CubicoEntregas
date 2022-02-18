package com.cubico.cubicodelivery.model;

public class TxRegistroMovimientoUAPalletModel {
    private String strIdOP ;
    private String strUAPallet ;
    private int intIdProducto;
    private String strLote;
    private double decCantidad;
    private String strUser ;
    private int intIdAlmacen ;
    private int intTipo;

    public String getStrIdOP() {
        return strIdOP;
    }

    public void setStrIdOP(String strIdOP) {
        this.strIdOP = strIdOP;
    }

    public String getStrUAPallet() {
        return strUAPallet;
    }

    public void setStrUAPallet(String strUAPallet) {
        this.strUAPallet = strUAPallet;
    }

    public int getIntIdProducto() {
        return intIdProducto;
    }

    public void setIntIdProducto(int intIdProducto) {
        this.intIdProducto = intIdProducto;
    }

    public String getStrLote() {
        return strLote;
    }

    public void setStrLote(String strLote) {
        this.strLote = strLote;
    }

    public double getDecCantidad() {
        return decCantidad;
    }

    public void setDecCantidad(double decCantidad) {
        this.decCantidad = decCantidad;
    }

    public String getStrUser() {
        return strUser;
    }

    public void setStrUser(String strUser) {
        this.strUser = strUser;
    }

    public int getIntIdAlmacen() {
        return intIdAlmacen;
    }

    public void setIntIdAlmacen(int intIdAlmacen) {
        this.intIdAlmacen = intIdAlmacen;
    }

    public int getIntTipo() {
        return intTipo;
    }

    public void setIntTipo(int intTipo) {
        this.intTipo = intTipo;
    }
}
