package com.cubico.cubicodelivery;

import android.app.Application;

import com.cubico.cubicodelivery.model.ListarSolicitudesXPicking;
import com.cubico.cubicodelivery.model.RutaModel;
import com.cubico.cubicodelivery.model.RutaXSolicitudPickingModel;

import java.util.List;

public class CubicoGlobal  extends Application {
    private String WebUrl;
    private String WebApi;
    private String TerminalId;
    private String WareHouseName;
    private int WareHouseId;
    private String UserName;
    private String Usuario;
    private int IdCedis;
    private String Cedis;
    private Double longitud;
    private Double latitud;

    public String getAPiKeyMaps() {
        return APiKeyMaps;
    }

    public void setAPiKeyMaps(String APiKeyMaps) {
        this.APiKeyMaps = APiKeyMaps;
    }

    private String APiKeyMaps;

    //* entidad ruta*//
    private ListarSolicitudesXPicking listarSolicitudesXPicking;

    private List<RutaModel> rutaModelList;

    public List<RutaModel> getRutaModelList() {
        return rutaModelList;
    }

    public void setRutaModelList(List<RutaModel> rutaModelList) {
        this.rutaModelList = rutaModelList;
    }

    public int getIdCedis() {
        return IdCedis;
    }

    public void setIdCedis(int idCedis) {
        IdCedis = idCedis;
    }

    public String getCedis() {
        return Cedis;
    }

    public void setCedis(String cedis) {
        Cedis = cedis;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getWebUrl() {
        return WebUrl;
    }

    public void setWebUrl(String webUrl) {
        WebUrl = webUrl;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public String getWebApi() {
        return WebApi;
    }

    public void setWebApi(String webApi) {
        WebApi = webApi;
    }

    public String getWareHouseName() {
        return WareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        WareHouseName = wareHouseName;
    }

    public int getWareHouseId() {
        return WareHouseId;
    }

    public void setWareHouseId(int wareHoseId) {
        WareHouseId = wareHoseId;
    }

    public String getUsername() {
        return UserName;
    }

    public void setUsername(String usename) {
        this.UserName = usename;
    }

    public ListarSolicitudesXPicking getListarSolicitudesXPicking() {
        return listarSolicitudesXPicking;
    }

    public void setListarSolicitudesXPicking(ListarSolicitudesXPicking listarSolicitudesXPicking) {
        this.listarSolicitudesXPicking = listarSolicitudesXPicking;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
}
