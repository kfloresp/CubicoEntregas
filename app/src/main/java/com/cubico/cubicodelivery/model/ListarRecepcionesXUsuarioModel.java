package com.cubico.cubicodelivery.model;

import java.util.Date;

public class ListarRecepcionesXUsuarioModel {
    private String Cliente;
    private String CodBarraMuelle;
    private String Estado;
    private String FechaDocumento;
    private String FechaLlegada;
    private boolean FlagDetalle;
    private boolean FlagPausa;
    private int Id_Cliente;
    private int Id_Estado;
    private int Id_Muelle;
    private int Id_Proveedor;
    private int Id_TipoDocumento;
    private int Id_TipoMovimiento;
    private String Id_Tx;
    private String Muelle;
    private String NumOrden;
    private String Observacion;
    private String Proveedor;
    private String TipoDocumento;
    private String TipoMovimiento;

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getCodBarraMuelle() {
        return CodBarraMuelle;
    }

    public void setCodBarraMuelle(String codBarraMuelle) {
        CodBarraMuelle = codBarraMuelle;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getFechaDocumento() {
        return FechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        FechaDocumento = fechaDocumento;
    }

    public String getFechaLlegada() {
        return FechaLlegada;
    }

    public void setFechaLlegada(String fechaLlegada) {
        FechaLlegada = fechaLlegada;
    }

    public boolean isFlagDetalle() {
        return FlagDetalle;
    }

    public void setFlagDetalle(boolean flagDetalle) {
        FlagDetalle = flagDetalle;
    }

    public boolean isFlagPausa() {
        return FlagPausa;
    }

    public void setFlagPausa(boolean flagPausa) {
        FlagPausa = flagPausa;
    }

    public int getId_Cliente() {
        return Id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        Id_Cliente = id_Cliente;
    }

    public int getId_Estado() {
        return Id_Estado;
    }

    public void setId_Estado(int id_Estado) {
        Id_Estado = id_Estado;
    }

    public int getId_Muelle() {
        return Id_Muelle;
    }

    public void setId_Muelle(int id_Muelle) {
        Id_Muelle = id_Muelle;
    }

    public int getId_Proveedor() {
        return Id_Proveedor;
    }

    public void setId_Proveedor(int id_Proveedor) {
        Id_Proveedor = id_Proveedor;
    }

    public int getId_TipoDocumento() {
        return Id_TipoDocumento;
    }

    public void setId_TipoDocumento(int id_TipoDocumento) {
        Id_TipoDocumento = id_TipoDocumento;
    }

    public int getId_TipoMovimiento() {
        return Id_TipoMovimiento;
    }

    public void setId_TipoMovimiento(int id_TipoMovimiento) {
        Id_TipoMovimiento = id_TipoMovimiento;
    }

    public String getId_Tx() {
        return Id_Tx;
    }

    public void setId_Tx(String id_Tx) {
        Id_Tx = id_Tx;
    }

    public String getMuelle() {
        return Muelle;
    }

    public void setMuelle(String muelle) {
        Muelle = muelle;
    }

    public String getNumOrden() {
        return NumOrden;
    }

    public void setNumOrden(String numOrden) {
        NumOrden = numOrden;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String proveedor) {
        Proveedor = proveedor;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getTipoMovimiento() {
        return TipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        TipoMovimiento = tipoMovimiento;
    }
}
