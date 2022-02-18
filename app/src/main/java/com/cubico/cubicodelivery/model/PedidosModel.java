package com.cubico.cubicodelivery.model;

public class PedidosModel {
    private int CantidadBultos;
    private String Cliente;
    private String  Direccion;
    private String FechaDocumento;
    private boolean FlagMotivoVar;
    private int Id_Actividad;
    private int Id_CausalCourier;
    private int Id_EstadoCourier;
    private int Id_Proveedor;
    private int Id_Sucursal;
    private String Id_Tra;
    private String Id_Tx;
    private String NumOrden;
    private String ObservacionCourier;
    private int OperacionBultos;
    private boolean RtPhotoyFirma;
    private int SaldoBultos;

    public int getCantidadBultos() {
        return CantidadBultos;
    }

    public void setCantidadBultos(int cantidadBultos) {
        CantidadBultos = cantidadBultos;
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

    public String getFechaDocumento() {
        return FechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        FechaDocumento = fechaDocumento;
    }

    public boolean isFlagMotivoVar() {
        return FlagMotivoVar;
    }

    public void setFlagMotivoVar(boolean flagMotivoVar) {
        FlagMotivoVar = flagMotivoVar;
    }

    public int getId_Actividad() {
        return Id_Actividad;
    }

    public void setId_Actividad(int id_Actividad) {
        Id_Actividad = id_Actividad;
    }

    public int getId_CausalCourier() {
        return Id_CausalCourier;
    }

    public void setId_CausalCourier(int id_CausalCourier) {
        Id_CausalCourier = id_CausalCourier;
    }

    public int getId_EstadoCourier() {
        return Id_EstadoCourier;
    }

    public void setId_EstadoCourier(int id_EstadoCourier) {
        Id_EstadoCourier = id_EstadoCourier;
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

    public String getId_Tra() {
        return Id_Tra;
    }

    public void setId_Tra(String id_Tra) {
        Id_Tra = id_Tra;
    }

    public String getId_Tx() {
        return Id_Tx;
    }

    public void setId_Tx(String id_Tx) {
        Id_Tx = id_Tx;
    }

    public String getNumOrden() {
        return NumOrden;
    }

    public void setNumOrden(String numOrden) {
        NumOrden = numOrden;
    }

    public String getObservacionCourier() {
        return ObservacionCourier;
    }

    public void setObservacionCourier(String observacionCourier) {
        ObservacionCourier = observacionCourier;
    }

    public int getOperacionBultos() {
        return OperacionBultos;
    }

    public void setOperacionBultos(int operacionBultos) {
        OperacionBultos = operacionBultos;
    }

    public boolean isRtPhotoyFirma() {
        return RtPhotoyFirma;
    }

    public void setRtPhotoyFirma(boolean rtPhotoyFirma) {
        RtPhotoyFirma = rtPhotoyFirma;
    }

    public int getSaldoBultos() {
        return SaldoBultos;
    }

    public void setSaldoBultos(int saldoBultos) {
        SaldoBultos = saldoBultos;
    }
}
