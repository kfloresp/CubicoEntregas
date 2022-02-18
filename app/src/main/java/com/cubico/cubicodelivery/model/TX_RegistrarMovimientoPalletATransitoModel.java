package com.cubico.cubicodelivery.model;

public class TX_RegistrarMovimientoPalletATransitoModel {

    private int intIdSolicitud ;
    private String strIdTx ;
    private int intIdUbicacion ;
    private int intIdProducto ;
    private String strUAPallet ;
    private double decCantidad ;
    private int intIdAlmacen ;
    private String strUsuario ;

    public int getIntIdSolicitud() {
        return intIdSolicitud;
    }

    public void setIntIdSolicitud(int intIdSolicitud) {
        this.intIdSolicitud = intIdSolicitud;
    }

    public String getStrIdTx() {
        return strIdTx;
    }

    public void setStrIdTx(String strIdTx) {
        this.strIdTx = strIdTx;
    }

    public int getIntIdUbicacion() {
        return intIdUbicacion;
    }

    public void setIntIdUbicacion(int intIdUbicacion) {
        this.intIdUbicacion = intIdUbicacion;
    }

    public int getIntIdProducto() {
        return intIdProducto;
    }

    public void setIntIdProducto(int intIdProducto) {
        this.intIdProducto = intIdProducto;
    }

    public String getStrUAPallet() {
        return strUAPallet;
    }

    public void setStrUAPallet(String strUAPallet) {
        this.strUAPallet = strUAPallet;
    }

    public double getDecCantidad() {
        return decCantidad;
    }

    public void setDecCantidad(double decCantidad) {
        this.decCantidad = decCantidad;
    }

    public int getIntIdAlmacen() {
        return intIdAlmacen;
    }

    public void setIntIdAlmacen(int intIdAlmacen) {
        this.intIdAlmacen = intIdAlmacen;
    }

    public String getStrUsuario() {
        return strUsuario;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }
}
