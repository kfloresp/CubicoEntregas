package com.cubico.cubicodelivery.model;

public class ListarSolicitudesXPicking {
    private int IdSolicitudPicking;
    private int Id_Producto;
    private String Codigo;
    private String Producto;
    private double CantidadSolicitada;
    private double CantidadOperada;
    private double Saldo;
    private double Sobrante;
    private int Id_Estado;
    private String Estado;

    public int getIdSolicitudPicking() {
        return IdSolicitudPicking;
    }

    public void setIdSolicitudPicking(int idSolicitudPicking) {
        IdSolicitudPicking = idSolicitudPicking;
    }

    public int getId_Producto() {
        return Id_Producto;
    }

    public void setId_Producto(int id_Producto) {
        Id_Producto = id_Producto;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public double getCantidadSolicitada() {
        return CantidadSolicitada;
    }

    public void setCantidadSolicitada(double cantidadSolicitada) {
        CantidadSolicitada = cantidadSolicitada;
    }

    public double getCantidadOperada() {
        return CantidadOperada;
    }

    public void setCantidadOperada(double cantidadOperada) {
        CantidadOperada = cantidadOperada;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double saldo) {
        Saldo = saldo;
    }

    public double getSobrante() {
        return Sobrante;
    }

    public void setSobrante(double sobrante) {
        Sobrante = sobrante;
    }

    public int getId_Estado() {
        return Id_Estado;
    }

    public void setId_Estado(int id_Estado) {
        Id_Estado = id_Estado;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
