package com.cubico.cubicodelivery.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.api.CubicoWebApiCliente;
import com.cubico.cubicodelivery.model.RutaXSolicitudPickingModel;
import com.cubico.cubicodelivery.model.TX_RegistrarMovimientoPalletATransitoModel;
import com.cubico.cubicodelivery.model.eMensaje;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cubico.cubicodelivery.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoverPickingDetail extends AppCompatActivity {
    CubicoGlobal cg;
    TextView lblCodProd,lblMaterial,lblCantidad;
    TextView lblFila,lblNivel,lblColumna,lblPasillo;
    EditText txtUbicacion,txtUA, txtCantidad;
    RutaXSolicitudPickingModel rutaXSolicitudPickingModel;
    Button btnRegistra, btnCancelar;
    eMensaje mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_picking_detail);
        cg=(CubicoGlobal)getApplication();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lblCodProd= findViewById(R.id.lblCodProd);
        lblMaterial=findViewById(R.id.lblMaterial);
        lblCantidad=findViewById(R.id.lblCantidad);
        lblFila=findViewById(R.id.lblFila);
        lblNivel=findViewById(R.id.lblNivel);
        lblColumna=findViewById(R.id.lblColumna);
        lblPasillo=findViewById(R.id.lblPasillo);
        txtUbicacion=findViewById(R.id.txtBarcodeUbicacion);
        txtUA=findViewById(R.id.txtUAPallet);
        txtCantidad=findViewById(R.id.txtCantidadBarra);
        btnRegistra=findViewById(R.id.btnRegistar);
        btnCancelar=findViewById(R.id.btnCancelar);
        txtCantidad.setText("0");
        txtCantidad.setEnabled(false);
        bloquearCamposUA(false);
        limpiardatos();
        loadData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtUbicacion.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                    //Validamos si ubicacion es el elegido
                    String tempubica= txtUbicacion.getText().toString();
                    Log.i("tmpUbica",tempubica );
                    if (tempubica.trim().equals(rutaXSolicitudPickingModel.getCodBarraUbi().trim())){
                        bloquearCamposUA(true);
                       // txtUA.setFocusable(true);
                        txtUA.requestFocus();
                        return true;
                    }
                    else{
                        if (txtUbicacion.getText().length()>0) {
                            //DisplayAlert("¡Ubicación no sugerida, intente otravez!");

//                            Snackbar.make(v, "¡Ubicación no sugerida, intente otravez!", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();

                                showToast("¡Ubicación no sugerida, intente otravez!");

                            txtUbicacion.setText("");
                        }
                        return true;
                    }



                }
                return false;
            }
        });

        txtUA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                    //validamos la UA con el contenedor
                    if(txtUA.getText().toString().trim().equals(rutaXSolicitudPickingModel.getContenedor().trim())){
                        txtCantidad.setText("" + rutaXSolicitudPickingModel.getCantidad());
                        btnRegistra.setVisibility(View.VISIBLE);
                        btnRegistra.setEnabled(true);

                        btnRegistra.requestFocus();
                       // Grabar_UAPallet();
                    }
                    else{
                        if (txtUA.getText().length()>0) {
                            //DisplayAlert("¡Ubicación no sugerida, intente otravez!");

//                            Snackbar.make(v, "¡Ubicación no sugerida, intente otravez!", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();

                            showToast("¡UA/PALLET NO CORRESPONDE A LO SOLICITADO, intente otravez!");

                            txtUA.setText("");
                            txtUA.requestFocus();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    void bloquearCamposUA(boolean flag){
        txtUA.setEnabled(flag);
        btnRegistra.setEnabled(flag);
        btnRegistra.setVisibility(View.INVISIBLE);

    }
    void loadData(){
        lblCodProd.setText(cg.getListarSolicitudesXPicking().getCodigo());
        lblMaterial.setText(cg.getListarSolicitudesXPicking().getProducto());
        lblCantidad.setText(""+ cg.getListarSolicitudesXPicking().getSaldo());
        try{
            Call<RutaXSolicitudPickingModel> call = CubicoWebApiCliente.getInstance(cg.getWebApi())
                    .getApiCubicoWebApi()
                    .Get_RutaXSolicitudPicking(cg.getListarSolicitudesXPicking().getIdSolicitudPicking(),
                                               cg.getListarSolicitudesXPicking().getId_Producto(),
                                                cg.getWareHouseId());
            call.enqueue(new Callback<RutaXSolicitudPickingModel>() {
                @Override
                public void onResponse(Call<RutaXSolicitudPickingModel> call, Response<RutaXSolicitudPickingModel> response) {
                   if (response.code()!=200){
                       showToast(response.message());
                   }
                   else{
                       if (response.isSuccessful()){
                           if (response.body() != null) {
                               rutaXSolicitudPickingModel=response.body();

                               if (rutaXSolicitudPickingModel.getCodBarraUbi()!=null){
                              lblFila.setText(rutaXSolicitudPickingModel.getFila());
                              lblNivel.setText(rutaXSolicitudPickingModel.getNivel());
                              lblColumna.setText(rutaXSolicitudPickingModel.getColumna());
                              lblPasillo.setText(rutaXSolicitudPickingModel.getPosicion());
                             // rutaXSolicitudPickingModel.
                               Log.i("Info Ubicacion",rutaXSolicitudPickingModel.getCodBarraUbi());
                               Log.i("Info cotendor",rutaXSolicitudPickingModel.getContenedor());
                               }
                               else
                               {
                                   DisplayAlert("NO hay ubicación para esta solictud...");

                               }
                           }
                           else{
                                showToast("NO hay datos...");
                                limpiardatos();
                           }

                       }
                   }
                }

                @Override
                public void onFailure(Call<RutaXSolicitudPickingModel> call, Throwable t) {

                }
            });
        }
        catch (Exception ex){

        }

    }
    void PostRegistraPalletUA(){
        boolean esExitoso=false;
        Log.i("PostRegistraPalletUA","Inicio de grabar..");
        TX_RegistrarMovimientoPalletATransitoModel tx_registrarMovimientoPalletATransitoModel= new TX_RegistrarMovimientoPalletATransitoModel();
        tx_registrarMovimientoPalletATransitoModel.setIntIdSolicitud(cg.getListarSolicitudesXPicking().getIdSolicitudPicking());
        tx_registrarMovimientoPalletATransitoModel.setStrIdTx("");
        tx_registrarMovimientoPalletATransitoModel.setIntIdUbicacion(rutaXSolicitudPickingModel.getId_Ubicacion());
        tx_registrarMovimientoPalletATransitoModel.setIntIdProducto(cg.getListarSolicitudesXPicking().getId_Producto());
        tx_registrarMovimientoPalletATransitoModel.setStrUAPallet(rutaXSolicitudPickingModel.getContenedor());
        tx_registrarMovimientoPalletATransitoModel.setDecCantidad(rutaXSolicitudPickingModel.getCantidad());
        tx_registrarMovimientoPalletATransitoModel.setIntIdAlmacen(cg.getWareHouseId());
        tx_registrarMovimientoPalletATransitoModel.setStrUsuario(cg.getUsuario());

        try{
            Call<eMensaje> call= CubicoWebApiCliente.getInstance(cg.getWebApi())
                    .getApiCubicoWebApi().Post_TX_RegistrarMovimientoPalletATransito(tx_registrarMovimientoPalletATransitoModel);
            call.enqueue(new Callback<eMensaje>() {
                @Override
                public void onResponse(Call<eMensaje> call, Response<eMensaje> response) {
                    if(response.isSuccessful()){
                        Log.i("PostRegistraPalletUA","Luego de grabar..");
                        mensaje=response.body();
                        if(mensaje.getErrNumber()==0){
                            if (mensaje.getValor2()==0){
                                showToast("Obteniendo otra ruta...");
                                Log.i("Obtener ruta",""+ mensaje.getValor2());
                                loadData();
                            }
                            else{
                                finish();
                            }
                        }
                        Log.i("Respusta Post",mensaje.getMessage()+ " Valor:"+ mensaje.getValor2());
                        showToast(mensaje.getMessage());

                    }
                    else{
                        mensaje.setErrNumber(-1);
                        mensaje.setMessage("No exitoso:" + response.code() );
                        showToast(mensaje.getMessage());
                    }
                    txtUA.setText("");
                    txtCantidad.setText("0");
                    txtUbicacion.setText("");
                    txtUbicacion.requestFocus();
                    bloquearCamposUA(false);
                }

                @Override
                public void onFailure(Call<eMensaje> call, Throwable t) {

                }
            });
        }
        catch (Exception ex){
            esExitoso=false;
            showToast(ex.getMessage());
        }


       // return esExitoso;
    }
    private void showToast(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    void limpiardatos(){
        lblFila.setText("");
        lblNivel.setText("");
        lblColumna.setText("");
        lblPasillo.setText("");
    }
    void DisplayAlert(String mensaje){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MoverPickingDetail.this);
        alertDialogBuilder.setMessage(mensaje);
        //alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("CUBICO WMS - Aviso");
        alertDialogBuilder.setNegativeButton("Aceptar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                finish();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }
//    void Grabar_UAPallet(){
//            new AlertDialog.Builder(this)
//                .setTitle("CUBICO WMS - Aviso")
//                .setMessage("¿Está seguro realizar operación?")
//                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (PostRegistraPalletUA()) {
//                            txtUA.setText("");
//                            txtCantidad.setText("0");
//                            txtUbicacion.setText("");
//                            txtUbicacion.requestFocus();
//                            bloquearCamposUA(false);
//                        }
//                    }
//                })
//                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                }).show();
//    }

    public void btnRegistrar_click(View view) {
        PostRegistraPalletUA();
    }

    public void btnLimpiar_click(View view) {
        txtUA.setText("");
        txtCantidad.setText("0");
        txtUbicacion.setText("");
        txtUbicacion.requestFocus();
        bloquearCamposUA(false);
    }
}
