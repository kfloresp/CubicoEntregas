package com.cubico.cubicodelivery.model;

public class RutaModel {
    private int  Id_Actividad;
    private String Id_Tra;
    private  int Id_Proveedor;
    private  int Id_Sucursal;
    private  String UsuarioAsignado;
    private String  Cliente;
    private String Direccion;
    private int Id_EstadoCourier;
    private  int Ruta;
    private Double Latitud;
    private Double Longitud;
    private boolean RtPhotoyFirma;
    private int Cantidad_Pedidos;
    private boolean FlagFicha;

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }

    public int getId_Actividad() {
        return Id_Actividad;
    }

    public void setId_Actividad(int id_Actividad) {
        Id_Actividad = id_Actividad;
    }

    public String getId_Tra() {
        return Id_Tra;
    }

    public void setId_Tra(String id_Tra) {
        Id_Tra = id_Tra;
    }

    public int getId_Proveedor() {
        return Id_Proveedor;
    }

    public void setId_Proveedor(int id_Proveedor) {
        Id_Proveedor = id_Proveedor;
    }

    public int getId_Sucursal() {
        return Id_Sucursal;
    }

    public void setId_Sucursal(int id_Sucursal) {
        Id_Sucursal = id_Sucursal;
    }

    public String getUsuarioAsignado() {
        return UsuarioAsignado;
    }

    public void setUsuarioAsignado(String usuarioAsignado) {
        UsuarioAsignado = usuarioAsignado;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public int getId_EstadoCourier() {
        return Id_EstadoCourier;
    }

    public void setId_EstadoCourier(int id_EstadoCourier) {
        Id_EstadoCourier = id_EstadoCourier;
    }

    public int getRuta() {
        return Ruta;
    }

    public void setRuta(int ruta) {
        Ruta = ruta;
    }

    public boolean isRtPhotoyFirma() {
        return RtPhotoyFirma;
    }

    public void setRtPhotoyFirma(boolean rtPhotoyFirma) {
        RtPhotoyFirma = rtPhotoyFirma;
    }

    public int getCantidad_Pedidos() {
        return Cantidad_Pedidos;
    }

    public void setCantidad_Pedidos(int cantidad_Pedidos) {
        Cantidad_Pedidos = cantidad_Pedidos;
    }

    public boolean isFlagFicha() {
        return FlagFicha;
    }

    public void setFlagFicha(boolean flagFicha) {
        FlagFicha = flagFicha;
    }
}
