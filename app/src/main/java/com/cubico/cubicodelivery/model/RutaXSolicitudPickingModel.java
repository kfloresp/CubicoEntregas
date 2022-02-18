package com.cubico.cubicodelivery.model;

public class RutaXSolicitudPickingModel {
    private int Id_Ubicacion;
    private String CodBarraUbi;
    private String Sector;
    private String Pasillo;
    private String Fila;
    private String Columna;
    private String Nivel;
    private String Posicion;
    private String Contenedor;
    private String Lote;
    private double Cantidad;

    public int getId_Ubicacion() {
        return Id_Ubicacion;
    }

    public void setId_Ubicacion(int id_Ubicacion) {
        Id_Ubicacion = id_Ubicacion;
    }

    public String getCodBarraUbi() {
        return CodBarraUbi;
    }

    public void setCodBarraUbi(String codBarraUbi) {
        CodBarraUbi = codBarraUbi;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getPasillo() {
        return Pasillo;
    }

    public void setPasillo(String pasillo) {
        Pasillo = pasillo;
    }

    public String getFila() {
        return Fila;
    }

    public void setFila(String fila) {
        Fila = fila;
    }

    public String getColumna() {
        return Columna;
    }

    public void setColumna(String columna) {
        Columna = columna;
    }

    public String getNivel() {
        return Nivel;
    }

    public void setNivel(String nivel) {
        Nivel = nivel;
    }

    public String getPosicion() {
        return Posicion;
    }

    public void setPosicion(String posicion) {
        Posicion = posicion;
    }

    public String getContenedor() {
        return Contenedor;
    }

    public void setContenedor(String contenedor) {
        Contenedor = contenedor;
    }

    public String getLote() {
        return Lote;
    }

    public void setLote(String lote) {
        Lote = lote;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double cantidad) {
        Cantidad = cantidad;
    }
}
