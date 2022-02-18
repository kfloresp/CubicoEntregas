package com.cubico.cubicodelivery.model;

public class AlmacenModel {

    private int Id_Almacen;
    private  int Id_Cliente;
    private  String Almacen;
    private  String Cliente;
    private String NombreSupervisor;

    public int getId_Almacen() {
        return Id_Almacen;
    }

    public void setId_Almacen(int id_Almacen) {
        Id_Almacen = id_Almacen;
    }

    public int getId_Cliente() {
        return Id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        Id_Cliente = id_Cliente;
    }

    public String getAlmacen() {
        return Almacen;
    }

    public void setAlmacen(String almacen) {
        Almacen = almacen;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getNombreSupervisor() {
        return NombreSupervisor;
    }

    public void setNombreSupervisor(String nombreSupervisor) {
        NombreSupervisor = nombreSupervisor;
    }
}
