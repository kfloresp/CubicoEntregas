package com.cubico.cubicodelivery.model;

public class ResumenPedidosModel {
    private String Id_Tra;
    private String UsuarioRuta;
    private int Pendientes;
    private int Confirmados;
    private int Motivados;
    private int Recojos;

    public String getId_Tra() {
        return Id_Tra;
    }

    public void setId_Tra(String id_Tra) {
        Id_Tra = id_Tra;
    }

    public String getUsuarioRuta() {
        return UsuarioRuta;
    }

    public void setUsuarioRuta(String usuarioRuta) {
        UsuarioRuta = usuarioRuta;
    }

    public int getPendientes() {
        return Pendientes;
    }

    public void setPendientes(int pendientes) {
        Pendientes = pendientes;
    }

    public int getConfirmados() {
        return Confirmados;
    }

    public void setConfirmados(int confirmados) {
        Confirmados = confirmados;
    }

    public int getMotivados() {
        return Motivados;
    }

    public void setMotivados(int motivados) {
        Motivados = motivados;
    }

    public int getRecojos() {
        return Recojos;
    }

    public void setRecojos(int recojos) {
        Recojos = recojos;
    }
}
