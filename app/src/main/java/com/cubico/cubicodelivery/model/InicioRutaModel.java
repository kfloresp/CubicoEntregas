package com.cubico.cubicodelivery.model;

import java.util.Date;

public class InicioRutaModel {

    private String CxInicio;
    private  String CyInicio;
    private  int Id_Actividad;
    private int Id_Estado;
    private String Id_Terminal;
    private String Id_Tra;
    private String InicioActividad;
    private String UsuarioRuta;

    public String getCxInicio() {
        return CxInicio;
    }

    public void setCxInicio(String cxInicio) {
        CxInicio = cxInicio;
    }

    public String getCyInicio() {
        return CyInicio;
    }

    public void setCyInicio(String cyInicio) {
        CyInicio = cyInicio;
    }

    public int getId_Actividad() {
        return Id_Actividad;
    }

    public void setId_Actividad(int id_Actividad) {
        Id_Actividad = id_Actividad;
    }

    public int getId_Estado() {
        return Id_Estado;
    }

    public void setId_Estado(int id_Estado) {
        Id_Estado = id_Estado;
    }

    public String getId_Terminal() {
        return Id_Terminal;
    }

    public void setId_Terminal(String id_Terminal) {
        Id_Terminal = id_Terminal;
    }

    public String getId_Tra() {
        return Id_Tra;
    }

    public void setId_Tra(String id_Tra) {
        Id_Tra = id_Tra;
    }

    public String getInicioActividad() {
        return InicioActividad;
    }

    public void setInicioActividad(String inicioActividad) {
        InicioActividad = inicioActividad;
    }

    public String getUsuarioRuta() {
        return UsuarioRuta;
    }

    public void setUsuarioRuta(String usuarioRuta) {
        UsuarioRuta = usuarioRuta;
    }
}
