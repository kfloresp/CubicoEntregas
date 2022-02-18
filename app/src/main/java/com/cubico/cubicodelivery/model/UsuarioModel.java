package com.cubico.cubicodelivery.model;

public class UsuarioModel {
    private String Almacen;
    private String ApeNom;
    private String Cliente;
    private String Correo;
    private boolean FlagActivo;
    private boolean FlagPermiso;
    private boolean FlagRestablecer;
    private String Foto;
    private int Id_Almacen;
    private int Id_cliente;
    private int Id_Perfel;
    private String Perfil;
    private String Usuario;

    public UsuarioModel() {
        super();
    }

    public String getAlmacen() {
        return Almacen;
    }

    public void setAlmacen(String almacen) {
        Almacen = almacen;
    }

    public String getApeNom() {
        return ApeNom;
    }

    public void setApeNom(String apeNom) {
        ApeNom = apeNom;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public boolean isFlagActivo() {
        return FlagActivo;
    }

    public void setFlagActivo(boolean flagActivo) {
        FlagActivo = flagActivo;
    }

    public boolean isFlagPermiso() {
        return FlagPermiso;
    }

    public void setFlagPermiso(boolean flagPermiso) {
        FlagPermiso = flagPermiso;
    }

    public boolean isFlagRestablecer() {
        return FlagRestablecer;
    }

    public void setFlagRestablecer(boolean flagRestablecer) {
        FlagRestablecer = flagRestablecer;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public int getId_Almacen() {
        return Id_Almacen;
    }

    public void setId_Almacen(int id_Almacen) {
        Id_Almacen = id_Almacen;
    }

    public int getId_cliente() {
        return Id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        Id_cliente = id_cliente;
    }

    public int getId_Perfel() {
        return Id_Perfel;
    }

    public void setId_Perfel(int id_Perfel) {
        Id_Perfel = id_Perfel;
    }

    public String getPerfil() {
        return Perfil;
    }

    public void setPerfil(String perfil) {
        Perfil = perfil;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}
