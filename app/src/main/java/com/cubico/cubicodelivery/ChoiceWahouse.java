package com.cubico.cubicodelivery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.cubico.cubicodelivery.activities.Principal;
import com.cubico.cubicodelivery.adpters.ComboAdapter;
import com.cubico.cubicodelivery.adpters.ComboBoxItem;
import com.cubico.cubicodelivery.api.CubicoWSClient;
import com.cubico.cubicodelivery.model.AlmacenModel;
import com.cubico.cubicodelivery.model.CedisModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoiceWahouse extends AppCompatActivity {
    String userName;
    String usuario;
    int idCedis;
    int idAlmacen;
    String Warehouse;

    List<CedisModel> cedisModelList;
    List<AlmacenModel> almacenModelList;
    private Spinner cboCedis;
    private Spinner cboAlmacen;
    private Button btnNext;
    CubicoGlobal cg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_wahouse);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        userName=(String)getIntent().getSerializableExtra("UserName");
//        usuario=(String)getIntent().getSerializableExtra("user");
        cboCedis= findViewById(R.id.cboCedis);
        cboAlmacen=findViewById(R.id.cboAlmacen);
        btnNext= findViewById(R.id.btnNext);
        cg=(CubicoGlobal)getApplication();
        userName=cg.getUsername();
        usuario=cg.getUsuario();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        loadCedis();
        cboCedis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    ComboBoxItem item=(ComboBoxItem)cboCedis.getSelectedItem();
                    Log.i("COMBO_CEDIS" ,item.getValue_data() + ' '+ item.getDisplay_data());
                    idCedis = Integer.parseInt(item.getValue_data());
                    loadAlmacenes(idCedis);

                }
                catch (Exception e){
                    Log.e("ERR_COMBO",e.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cboAlmacen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ComboBoxItem item = (ComboBoxItem) cboAlmacen.getSelectedItem();
                    idAlmacen = Integer.parseInt(item.getValue_data());
                    Warehouse=item.getDisplay_data();
                }
                  catch (Exception e){
                        Log.e("ERR_COMBO_ALMACEN",e.getMessage());
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CHOICE_WAREHOUSE","DENTRO DEL CLICK");
                gotoMenu();
            }
        });
    }

    public void loadCedis(){
        Call<List<CedisModel>> call= CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().getCedis(usuario);

        call.enqueue(new Callback<List<CedisModel>>() {
            @Override
            public void onResponse(Call<List<CedisModel>> call, Response<List<CedisModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        cedisModelList=response.body();
                        List<ComboBoxItem> items = new ArrayList<ComboBoxItem>();

                        for (CedisModel c: cedisModelList) {
                            String idData= ""+ c.getId_Centro();
                            Log.i("ItemData", String.valueOf(c.getId_Centro()));

                            items.add(new ComboBoxItem(idData,c.getCentro()));
                        }



                        ComboAdapter cboCedisadapter= new ComboAdapter(ChoiceWahouse.this,items);
                        cboCedisadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboCedis.setAdapter(cboCedisadapter);
                        //Log.i("onSuccess", ((String) clientesData.size()));

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CedisModel>> call, Throwable t) {

            }
        });


    }

    public void loadAlmacenes(int idCedis){
        Call<List<AlmacenModel>> call=CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().getAlmacenes(usuario,idCedis);
        call.enqueue(new Callback<List<AlmacenModel>>() {
            @Override
            public void onResponse(Call<List<AlmacenModel>> call, Response<List<AlmacenModel>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("SEDE_ONSUCCESS", response.body().toString());

                        almacenModelList=response.body();
                        List<ComboBoxItem> items = new ArrayList<ComboBoxItem>();

                        for (AlmacenModel c: almacenModelList) {
                            String idData= ""+c.getId_Almacen();

                            items.add(new ComboBoxItem(idData,c.getAlmacen()));
                        }



                        ComboAdapter cboAlmacenadapter= new ComboAdapter(ChoiceWahouse.this,items);
                        cboAlmacenadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboAlmacen.setAdapter(cboAlmacenadapter);
                        //Log.i("onSuccess", ((String) clientesData.size()));

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<AlmacenModel>> call, Throwable t) {

            }
        });

    }

    void gotoMenu(){

        Log.i("CHOICE_CLIENTE","INICIO DE INTENT");
        Intent i = new Intent(getApplicationContext(), Principal.class);
       // i.putExtra("idCedis", idCedis);
//        i.putExtra("idAlmacen", idAlmacen);
//        i.putExtra("usuario", usuario);
//        i.putExtra("UserName", userName);
//        i.putExtra("Warehouse", Warehouse);
        cg.setIdCedis(idCedis);
        cg.setWareHouseId(idAlmacen);
        cg.setWareHouseName(Warehouse);
        startActivity(i);
    }
    void DisplayAlertQuit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChoiceWahouse.this);
        alertDialogBuilder.setMessage("¿Está seguro de salir de la aplicación?");
        alertDialogBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //  Toast.makeText(Principal.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }
   /* private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }*/
}
