package com.cubico.cubicodelivery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.api.CubicoWSClient;
import com.cubico.cubicodelivery.api.CubicoWebApiCliente;
import com.cubico.cubicodelivery.model.TransferenciaModel;
import com.cubico.cubicodelivery.model.TxRegistroMovimientoUAPalletModel;
import com.cubico.cubicodelivery.model.eMensaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transferencias extends AppCompatActivity {
    String userName;
    String usuario;
    int idAlmacen;
    int idCedis;
    int idTipo;
    private List<TransferenciaModel> transferenciaModelList;
    private EditText txtBarcodeSearch;
    private String txtop;
    private EditText txtuaPallet;
    private EditText txtCodigo;
    private EditText txtProducto;
    private EditText txtLote;
    private EditText txtUm;
    private EditText txtCantidad;

    private TextView lblWarehouse;
    private ImageButton btnSearch;
    private Toolbar toolbar;
    private int idProducto;
    private  eMensaje mensaje;
    CubicoGlobal cg;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencias);
        cg=(CubicoGlobal)getApplication();
        userName=cg.getUsername();//(String)getIntent().getSerializableExtra("UserName");
        usuario=cg.getUsuario();//(String)getIntent().getSerializableExtra("usuario");
        idCedis=cg.getIdCedis();//(int)getIntent().getSerializableExtra("idCedis");
        idAlmacen=cg.getWareHouseId();//(int)getIntent().getSerializableExtra("idAlmacen");
        idTipo=(int)getIntent().getSerializableExtra("idTipo");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtBarcodeSearch= (EditText)findViewById(R.id.txtBarcode);
        txtCantidad=findViewById(R.id.txtCantidad);
        txtCodigo=findViewById(R.id.txtCodigo);
        txtLote=findViewById(R.id.txtLote);
        txtProducto=findViewById(R.id.txtProducto);
        txtuaPallet=findViewById(R.id.txtuaPallet);
        txtUm=findViewById(R.id.txtUm);

        lblWarehouse=findViewById(R.id.lblAlmacen);

        lblWarehouse.setText(cg.getWareHouseName() +" / " + (idTipo==1?"SALIDA":"RECIBO"));
       // lblUsename.setText(userName + " / " +  idTipo);
        btnSearch= findViewById(R.id.btnSearch);
        txtBarcodeSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                   getPallets();
                    return true;
                }
                return false;
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getPallets();
    }
});



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transferencia_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id==R.id.mnuBack){
            finish();
            return true;
        }
        if (id == R.id.mnuNew) {
            limparCampos();
            return true;
        }
        if (id == R.id.mnuSave) {
            //showToast("Registrando transacción....");
            Grabar();
            //showToast("Registrado....");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void getPallets(){
        if(txtBarcodeSearch.getText().length()>0) {
            progressDialog= new ProgressDialog(this);
            progressDialog.setTitle("CubicoWMS");
            progressDialog.setMessage("Buscando pallet......");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
            Call<List<TransferenciaModel>> call = CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().getListarDatosXPallet(txtBarcodeSearch.getText().toString()
                    , idAlmacen, idTipo);
            call.enqueue(new Callback<List<TransferenciaModel>>() {
                @Override
                public void onResponse(Call<List<TransferenciaModel>> call, Response<List<TransferenciaModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            transferenciaModelList = response.body();
                            if (transferenciaModelList.isEmpty()) {
                                showToast("Pallet/UA no registrado...");
                                txtBarcodeSearch.setText("");
                            } else {
                                for (TransferenciaModel t : transferenciaModelList
                                ) {
                                    txtop=t.getId_OP();
                                    txtuaPallet.setText(t.getPalletCodBarra());
                                    txtCodigo.setText(t.getCodigo());
                                    txtProducto.setText(t.getProducto());
                                    txtLote.setText(t.getLote());
                                    txtUm.setText(t.getUM());
                                    txtCantidad.setText(t.getCantidad().toString());
                                    idProducto=t.getId_Producto();
                                }
                            }

                        } else {
                            showToast("Código de barras no registrado...");
                        }
                    } else {
                        showToast(response.errorBody().toString());

                    }
                }

                @Override
                public void onFailure(Call<List<TransferenciaModel>> call, Throwable t) {

                }
            });
            progressDialog.dismiss();
        }
        else
        {
            showToast("Ingrese Pallet/UA");
        }
    }
    private void limparCampos(){
        txtop="";
        txtuaPallet.setText("");
        txtCodigo.setText("");
        txtProducto.setText("");
        txtLote.setText("");
        txtUm.setText("");
        txtCantidad.setText("");
        txtBarcodeSearch.setText("");
    }
    private void showToast(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    void Grabar(){

        if (txtop.length()>0) {
            try {
                progressDialog= new ProgressDialog(this);
                progressDialog.setTitle("CubicoWMS");
                progressDialog.setMessage("Registrando......");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
                TxRegistroMovimientoUAPalletModel tx = new TxRegistroMovimientoUAPalletModel();
                tx.setStrIdOP(txtop);
                tx.setStrUAPallet(txtuaPallet.getText().toString());
                tx.setIntIdProducto(idProducto);
                tx.setIntIdAlmacen(idAlmacen);
                tx.setStrUser(usuario);
                tx.setStrLote(txtLote.getText().toString());
                tx.setDecCantidad(Double.parseDouble(txtCantidad.getText().toString()));
                tx.setIntTipo(idTipo);
               // showToast("Registrando transacción....");

try {


                //Call<eMensaje> call = CubicoWSClient.getInstance().getApiCubico().postRegistrarPaller(tx);
                Call<eMensaje> call = CubicoWebApiCliente.getInstance(cg.getWebApi()).getApiCubicoWebApi().postRegistrarPallerAPI(tx,"cipsa");
                call.enqueue(new Callback<eMensaje>() {
                    @Override
                    public void onResponse(Call<eMensaje> call, Response<eMensaje> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Log.i("body",response.message());
                                mensaje = response.body();

                                showToast(mensaje.getMessage());
                                progressDialog.setMessage(mensaje.getMessage());
                                limparCampos();
                            } else {

                                Log.i("ACTIVO_SAVE_ON_EMPTY", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                            }


                        } else {
                            showToast("Not is successful:"+ response.errorBody().toString());
                            Log.i("Not is successful:", response.errorBody().toString());
                            Log.i("Not is successful:", response.message());
                            Log.i("Not is successful:", ""+ response.code());
                        }

                    }

                    @Override
                    public void onFailure(Call<eMensaje> call, Throwable t) {
                        Log.i("Onfailure ", "error");
                        showToast("Error de red, intente ottravez!!");
                    }
                });
                }
                catch (Exception sex)
                     { Log.i("Onfailure ", sex.getMessage());
                         showToast(sex.getMessage());}
            } catch (Exception ex) {
               //progressDialog.dismiss();
                showToast(ex.getMessage());
            }
            progressDialog.dismiss();
        }
        else{
            progressDialog.dismiss();
            showToast("Busque un Pallet/UA..");
        }

    }
   /* private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }*/

}
