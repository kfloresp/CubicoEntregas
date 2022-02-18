package com.cubico.cubicodelivery.model;

public class TransferenciaModel {
    private Double Cantidad;
    private String Codigo;
    private String Id_OP;
    private int Id_Producto;
    private String Lote;
    private String PalletCodBarra;
    private String Producto;
    private String UM;

    public Double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Double cantidad) {
        Cantidad = cantidad;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getId_OP() {
        return Id_OP;
    }

    public void setId_OP(String id_OP) {
        Id_OP = id_OP;
    }

    public int getId_Producto() {
        return Id_Producto;
    }

    public void setId_Producto(int id_Producto) {
        Id_Producto = id_Producto;
    }

    public String getLote() {
        return Lote;
    }

    public void setLote(String lote) {
        Lote = lote;
    }

    public String getPalletCodBarra() {
        return PalletCodBarra;
    }

    public void setPalletCodBarra(String palletCodBarra) {
        PalletCodBarra = palletCodBarra;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getUM() {
        return UM;
    }

    public void setUM(String UM) {
        this.UM = UM;
    }
}
