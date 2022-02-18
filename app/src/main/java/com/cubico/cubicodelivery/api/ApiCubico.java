package com.cubico.cubicodelivery.api;

import com.cubico.cubicodelivery.model.AlmacenModel;
import com.cubico.cubicodelivery.model.BultoDetail;
import com.cubico.cubicodelivery.model.BultoModel;
import com.cubico.cubicodelivery.model.CausalModel;
import com.cubico.cubicodelivery.model.CedisModel;
import com.cubico.cubicodelivery.model.ConfirmarPedidoModel;
import com.cubico.cubicodelivery.model.InicioRutaModel;
import com.cubico.cubicodelivery.model.ListarRecepcionesXUsuarioModel;
import com.cubico.cubicodelivery.model.ListarSolicitudesXPicking;
import com.cubico.cubicodelivery.model.Mensajes;
import com.cubico.cubicodelivery.model.PedidosModel;
import com.cubico.cubicodelivery.model.ResumenPedidosModel;
import com.cubico.cubicodelivery.model.RutaModel;
import com.cubico.cubicodelivery.model.RutaXSolicitudPickingModel;
import com.cubico.cubicodelivery.model.TX_RegistrarMovimientoPalletATransitoModel;
import com.cubico.cubicodelivery.model.TransferenciaModel;
import com.cubico.cubicodelivery.model.TxRegistroMovimientoUAPalletModel;
import com.cubico.cubicodelivery.model.UsuarioModel;
import com.cubico.cubicodelivery.model.eMensaje;
import com.cubico.cubicodelivery.model.txModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiCubico {

    @GET("UsuarioService.svc/rest/ValidarUsuarioAndroid")
    Call<List<UsuarioModel>> userLogin (
            @Query("Usuario") String usuario,
            @Query("Clave") String password,
            @Query("idTerminal") int idterminal
    );

    /*
    Almacenamiento
    * */
    @GET("UsuarioService.svc/rest/ListarCentrosXUsuario")
    Call<List<CedisModel>> getCedis(
            @Query("strUsuario") String usuario
    );

    @GET("UsuarioService.svc/rest/ListarAlmacenesXUsuario")
    Call<List<AlmacenModel>> getAlmacenes(
            @Query("strUsuario") String usuario,
            @Query("intIdCentro") int idCentro
    );

    /* Transferencias */

    @GET("ProduccionService.svc/rest/ListarDatosXPallet")
    Call <List<TransferenciaModel>> getListarDatosXPallet(
            @Query("strIdPallet") String strIdPallet,
            @Query("intIdAlmacen") int intIdAlmacen,
            @Query("intTipo") int intTipo
    );

   @POST("ProduccionService.svc/rest/strIdOP/strUAPallet/intIdProducto/strLote/decCantidad/strUser/intIdAlmacen/intTipo" )
    Call <eMensaje> postRegistrarPaller(@Body TxRegistroMovimientoUAPalletModel txRegistroMovimientoUAPalletModel);

    @POST("RFid" )
    Call <eMensaje> postRegistrarPallerAPI(
            @Body TxRegistroMovimientoUAPalletModel txRegistroMovimientoUAPalletModel,
            @Query("api") String api
    );

    /* Picking */
    @GET("Picking")
    Call<List<ListarSolicitudesXPicking>> Get_ListarSolicitudesXPicking(@Query("intIdAlmacen") int intIdAlmacen);

    @GET("Picking")
    Call<RutaXSolicitudPickingModel>Get_RutaXSolicitudPicking(
            @Query("intIdSolicitud") int intIdSolicitud,
            @Query("intIdProducto") int intIdProducto,
            @Query("intIdAlmacen") int intIdAlmacen
    );
    @POST("Picking" )
    Call <eMensaje> Post_TX_RegistrarMovimientoPalletATransito(@Body TX_RegistrarMovimientoPalletATransitoModel tx_registrarMovimientoPalletATransitoModel);

    /* Recibo*/

    @GET("RecepcionService.svc/rest/ListarRecepcionesXUsuario_V2")
    Call<List<ListarRecepcionesXUsuarioModel>> Get_ListarRecepcionesXUsuario(
            @Query("strUsuario") String strUsuario,
            @Query("intIdAlmacen") int intIdAlmacen,
            @Query("idMuelle") int idMuelle
    );

    @GET("RecepcionService.svc/rest/TxDetalleXTx_v2")
    Call<List<txModel>> Get_TxDetalleXTx(
            @Query("strIdTx") String strIdTx
    );
    /* Delivery*/

    @POST("CourierService.svc/rest/RegistrarActividad")
    Call <List<InicioRutaModel>> Post_RegistrarActividad(
            @Query("usuarioRuta") String usuarioRuta,
            @Query("cxInicio") double cxInicio,
            @Query("cyInicio") double cyInicio,
            @Query("id_Terminal") int id_Terminal,
            @Query("intAccion") int intAccion
    );

    // http://172.16.32.15:8087/SGAA_WCF/CourierService.svc/rest/CerrarActividad?id_ACtividad={ID_ACTIVIDAD}&cxFin={CXFIN}&cyFin={CYFIN}
    @POST("CourierService.svc/rest/CerrarActividad")
    Call <List<Mensajes>> Post_CerrarActividad(
            @Query("id_ACtividad") int id_ACtividad,
            @Query("cxFin") double cxFin,
            @Query("cyFin") double cyFin

    );

    @GET("CourierService.svc/rest/NumeroPedidoxCliente_V2")
    Call <List<RutaModel>> Get_NumeroPedidoxCliente(
            @Query("usuario") String usuario,
            @Query("strIdTra") String strIdTra,
            @Query("Id_Actividad") int Id_Actividad
    );

    @GET("CourierService.svc/rest/SP_S_DetallePedidosxCliente")
    Call <List<PedidosModel>> Get_DetallePedidosxCliente(
            @Query("Id_Tra") String Id_Tra,
            @Query("Id_Proveedor") String Id_Proveedor
    );

    @GET("CourierService.svc/rest/ListarCausalesXModulo")
    Call <List<CausalModel>> Get_ListaCausales(
            @Query("intIdCliente") int intIdCliente,
            @Query("intModulo") int intModulo
    );
//http://172.16.32.15:8087/SGAA_WCF/CourierService.svc/rest/ListarBultosEntregadosXPedido?intIdActividad=1110&strIdTx=A20182280005
    @GET("CourierService.svc/rest/ListarBultosEntregadosXPedido")
    Call<List<BultoDetail>> Get_Listar_bultos(
            @Query("intIdActividad") int intIdActividad,
            @Query("strIdTx") String strIdTx
    );
   // @Headers("Content-Type: application/json")
    @POST("CourierService.svc/rest/ConfirmarEntregaBulto_v2")
    Call <List<Mensajes>> Post_ConfirmarEntregaBulto(
             @Query("strBulto") String strBulto,
             @Query("strIdTran") String strIdTran ,
            @Query("intIdActividad") String intIdActividad,
            @Query("strIdTx") String strIdTx,
            @Query("strUser") String strUser,
            @Query("intTipo") String intTipo
    );


    //http://172.16.32.15:8087/SGAA_WCF/CourierService.svc/rest/RegistrarMotivoActividad?
    // id_tx={ID_TX}&id_Actividad={ID_ACTIVIDAD}
    // &id_CausalCourier={ID_CAUSALCOURIER}&observacionCourier={OBSERVACIONCOURIER}&decCx={DECCX}&decCy={DECCY}


    // http://172.16.32.15:8087/SGAA_WCF/CourierService.svc/rest/ConfirmarEntregaPedido/
    // intIdActividad/strIdTx/strCx/strCy/intIdEstado/intIdCausal/strObservacion/imgRtFirma/imgRtPhoto

    @POST("Courier")
    Call <eMensaje> Post_ConfirmarEntregaPedido(
            @Body ConfirmarPedidoModel confirmarPedidoModel

            );

    @POST("CourierService.svc/rest/ConfirmarEntregaPedido2")
    Call <eMensaje> Post_ConfirmarEntregaPedidoV2(
            @Body ConfirmarPedidoModel confirmarPedidoModel

    );

    @GET("Courier")
    Call <ResumenPedidosModel> Get_ResumenPedido(
            @Query("id") int id

    );

}
