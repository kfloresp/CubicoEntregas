package com.cubico.cubicodelivery.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.adpters.ItemAdapter;
import com.cubico.cubicodelivery.api.CubicoWebApiCliente;
import com.cubico.cubicodelivery.helper.ItemData;
import com.cubico.cubicodelivery.model.ListarSolicitudesXPicking;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.cubico.cubicodelivery.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoverPalletPicking extends AppCompatActivity {
    CubicoGlobal cg;
    ProgressDialog progressDialog;
    TextView lblWarehouse;
    SearchView searchView;
    ListView listView;
    private Toolbar toolbar;
    private List<ListarSolicitudesXPicking> listarSolicitudesXPickingList;

    private List<ItemData> tempoList= new ArrayList<ItemData>();
    private List<ItemData> items;
    private ArrayList<ItemData> arrayList;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cg=(CubicoGlobal)getApplication();
        setContentView(R.layout.activity_mover_pallet_picking);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        searchView=findViewById(R.id.search);
        listView=findViewById(R.id.lstSolicitudes);
        lblWarehouse=findViewById(R.id.lblAlmacen);
        lblWarehouse.setText(cg.getWareHouseName());
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadSolicitudes();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewId = view.getId();
                if(viewId==R.id.btnListEdit){
                    ItemData itemData=(ItemData) listView.getItemAtPosition(position);
                    Log.i("Lista Data",itemData.getTexto1() + "   id: "+ itemData.getTextoId());

                    ListarSolicitudesXPicking list = new ListarSolicitudesXPicking();
                    list.setCodigo( itemData.getTexto2());
                    list.setProducto( itemData.getTexto1());
                    list.setSaldo(itemData.getDoubleData2());
                    list.setId_Producto(itemData.getIntData1());
                    list.setIdSolicitudPicking(itemData.getIntData2());
                    cg.setListarSolicitudesXPicking(list);

                    gotoDetail();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //  String text=s;
                tempoList.clear();
                tempoList.addAll(arrayList);
                items.clear();
                if(s.length()==0){
                    items.addAll(arrayList);
                }
                else{

                    for(ItemData p : tempoList){
                        if(p.getTexto1().toLowerCase(Locale.getDefault()).contains(s.toLowerCase(Locale.getDefault()))
                                || p.getTexto2().toLowerCase(Locale.getDefault()).contains(s.toLowerCase(Locale.getDefault()))
                        ){
                            items.add(p);
                        }
                    }
                }
                itemAdapter.notifyDataSetChanged();

                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mnumovpicking, menu);
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

        if (id == R.id.mnuRefresh) {
            loadSolicitudes();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void loadSolicitudes(){

            progressDialog = new ProgressDialog(MoverPalletPicking.this);
            progressDialog.setTitle("Cubico WMS");
            progressDialog.setMessage("Cargando datos....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
        try {
            Log.i("Gloabl",cg.getWebApi());
            Call<List<ListarSolicitudesXPicking>> call = CubicoWebApiCliente.getInstance(cg.getWebApi()).getApiCubicoWebApi().Get_ListarSolicitudesXPicking(cg.getWareHouseId());
            call.enqueue(new Callback<List<ListarSolicitudesXPicking>>() {
                @Override
                public void onResponse(Call<List<ListarSolicitudesXPicking>> call, Response<List<ListarSolicitudesXPicking>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.i("SOLICITUDES_ONSUCCESS", response.body().toString());

                            listarSolicitudesXPickingList = response.body();
                            items = new ArrayList<ItemData>();

                            for (ListarSolicitudesXPicking c : listarSolicitudesXPickingList) {
                                ItemData item = new ItemData();
                               // item.setTextoId("" + c.getIdActivo());
                                item.setTexto1(c.getProducto());
                                item.setTexto2("Código:"+c.getCodigo());
                                item.setTexto3("Cantidad:" + c.getCantidadSolicitada());
                                item.setTextoId(""+ c.getIdSolicitudPicking());
                                item.setTxtTempo(c.getEstado());

                                item.setIntData1(c.getId_Producto());
                                item.setIntData2(c.getIdSolicitudPicking());
                                item.setDoubleData1(c.getCantidadSolicitada());
                                item.setDoubleData2(c.getSaldo());
                               // item.setColoBack(Color.YELLOW);
                                switch (c.getId_Estado())
                                {
                                    case 10:
                                        item.setColoBack(Color.WHITE);
                                        break;
                                    case 3:
                                        item.setColoBack(Color.YELLOW);
                                        break;
                                    case 5:
                                        item.setColoBack(Color.GREEN);
                                        break;
                                }
                                //item.setUrlImage(c.getFoto());

                                items.add(item);
                            }
                            arrayList = new ArrayList<ItemData>();
                            arrayList.addAll(items);
//                    tempoList.addAll(arrayList);
                            itemAdapter = new ItemAdapter(MoverPalletPicking.this, items);

                            listView.setAdapter(itemAdapter);

                        } else {
                            Log.i("SOLICITUDES_ON_EMPTY", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<ListarSolicitudesXPicking>> call, Throwable t) {
                    Log.e("onFailure:", "Error On failure, caraga de datos solicutud");
                    progressDialog.dismiss();
                }
            });

        }
        catch (Exception ex){
            Log.e("ERR_LOAD_SOLICITUD", ex.getMessage());
        }

    }
    void gotoDetail(){
        Intent i = new Intent(getApplicationContext(), MoverPickingDetail.class);
        startActivity(i);
    }
}
