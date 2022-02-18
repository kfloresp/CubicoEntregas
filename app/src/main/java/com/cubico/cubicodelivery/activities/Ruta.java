package com.cubico.cubicodelivery.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.cubico.cubicodelivery.CubicoGlobal;

import com.cubico.cubicodelivery.adpters.RecycleViewAdapter;
import com.cubico.cubicodelivery.api.CubicoWSClient;
import com.cubico.cubicodelivery.dialogs.GraphFragment;
import com.cubico.cubicodelivery.helper.ItemData;
import com.cubico.cubicodelivery.model.RutaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Toast;

import com.cubico.cubicodelivery.R;



import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ruta extends AppCompatActivity {
  //  private TextView lblUsuario;
    private RecyclerView lstRutas;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private List<ItemData> items;
    private List<RutaModel> rutaModels;
    private ArrayList<ItemData> arrayList;
    CubicoGlobal cg;
    ProgressDialog progressDialog;
    String strIDtx;
    int idActividad;
    View ChildView ;
    int RecyclerViewItemPosition ;
    private Double latitud,longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta);
        cg=(CubicoGlobal)getApplication() ;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* lblUsuario=findViewById(R.id.lblUsuario);
        lblUsuario.setText(cg.getUsername());*/
        lstRutas=findViewById(R.id.lstRutas);
        manager= new LinearLayoutManager(Ruta.this);
        lstRutas.setLayoutManager(manager);
        strIDtx=(String)getIntent().getSerializableExtra("strIdTra");
        idActividad= (int)getIntent().getSerializableExtra("idActividad");
        latitud=(Double) getIntent().getSerializableExtra("latitud");
        longitud=(Double) getIntent().getSerializableExtra("longitud");
        LoadRutas();
      //  Log.i("strIdTra",strIDtx);
        setTitle("Ruta:" + strIDtx + "/" + idActividad);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Saliendo de Rutas", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                finish();

            }
        });

        FloatingActionButton fabMap = findViewById(R.id.fabMap);
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  ArrayList<RutaModel> rm = new ArrayList<RutaModel>();
                rm.addAll(rutaModels);
                Bundle extra = new Bundle();
                extra.putSerializable("ruta", rm);*/
              cg.setRutaModelList(rutaModels);
                Intent x = new Intent(getApplicationContext(),MapsActivity.class);

                x.putExtra("latitud",latitud);
                x.putExtra("longitud",longitud);
              //  x.putExtra("extra",extra);
                startActivity(x);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        lstRutas.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(Ruta.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                     return true;
                }
            });
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                ChildView =rv.findChildViewUnder(e.getX(), e.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(e)) {

                    RecyclerViewItemPosition = rv.getChildAdapterPosition(ChildView);
                    RecycleViewAdapter it=(RecycleViewAdapter)rv.getAdapter();
                   // Toast.makeText(Ruta.this, it.getItem(RecyclerViewItemPosition).getTextoId(), Toast.LENGTH_LONG).show();
                    gotoPedido(strIDtx,
                            it.getItem(RecyclerViewItemPosition).getTextoId(),
                            it.getItem(RecyclerViewItemPosition).getTexto1(),
                            it.getItem(RecyclerViewItemPosition).getTexto2());
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_recibo, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id==R.id.mnuBack){
            finish();
            return true;
        }*/

        if (id == R.id.mnuRefresh) {
            LoadRutas();
            return true;
        }
        if (id == R.id.mnuConsultas) {
            LoadQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadQuery() {
        GraphFragment graphFragment= new GraphFragment(idActividad);
        graphFragment.show(getSupportFragmentManager(),"GraphFragment");
    }

    @Override
    public void onBackPressed() {
        showToast("Saliendo...");
        finish();
        //super.onBackPressed();
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    private void LoadRutas(){
        progressDialog = new ProgressDialog(Ruta.this);
        progressDialog.setTitle("Cubico WMS");
        progressDialog.setMessage("Cargando datos....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            Call<List<RutaModel>> call= CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().Get_NumeroPedidoxCliente(cg.getUsuario(),strIDtx,idActividad);
            call.enqueue(new Callback<List<RutaModel>>() {
                @Override
                public void onResponse(Call<List<RutaModel>> call, Response<List<RutaModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            rutaModels= response.body();
                            items = new ArrayList<ItemData>();
                            for(RutaModel rm: rutaModels){
                                ItemData i= new ItemData();
                                i.setTexto1(rm.getCliente());
                                i.setTexto2(rm.getDireccion());
                                i.setIntData1(rm.getRuta());
                                i.setIntData2(rm.getCantidad_Pedidos());
                                i.setTextoId(""+rm.getId_Proveedor());
                                i.setTexto3(rm.getId_Tra());
                                //rm.setId_EstadoCourier(rm.getId_EstadoCourier()==null?0:rm.getId_EstadoCourier());
                                Log.i("ROUTESTA",""+rm.getId_EstadoCourier());
                               /* Log.i("LAT",""+rm.getLatitud());
                                Log.i("LON",""+rm.getLongitud());*/
                               // Log.i("ROUTEGPS",""+rm.g);
                                switch (rm.getId_EstadoCourier())
                                {
                                    case 10:
                                        i.setColoBack(Color.WHITE);
                                        break;
                                    case 3:
                                        i.setColoBack(Color.YELLOW);
                                        break;
                                    case 22: // devolucion
                                        i.setColoBack(Color.LTGRAY);
                                        break;

                                    case 23: // motivado parcial
                                        i.setColoBack(Color.CYAN);
                                        break;
                                    case 25:
                                        i.setColoBack(Color.GREEN);
                                        break;
                                    case 15:
                                        i.setColoBack(Color.parseColor("#FADBD8"));
                                        break;
                                    default:
                                        i.setColoBack(Color.WHITE);
                                        break;
                                }
                                items.add(i);
                            }
                            arrayList = new ArrayList<ItemData>();
                            arrayList.addAll(items);
                            adapter= new RecycleViewAdapter(Ruta.this,arrayList);
                            lstRutas.setAdapter(adapter);
                        }
                    }
                    else{


                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<RutaModel>> call, Throwable t) {
                    Log.i("Throwable",t.getMessage());
                    progressDialog.dismiss();
                }
            });



        }
        catch (Exception ex){
            Log.i("Exception",ex.getMessage());
            progressDialog.dismiss();
        }


    }
    void gotoPedido(String strIDtx, String idProveedor,String Cliente, String Direccion){
        Log.i("ID TX" ,strIDtx);
        Log.i("ID PROV" ,idProveedor);
        Intent intent = new Intent(getApplicationContext(), Pedidos.class);
        intent.putExtra("strIDtx",strIDtx);
        intent.putExtra("idProveedor",idProveedor);
        intent.putExtra("Cliente",Cliente);
        intent.putExtra("Direccion",Direccion);

        startActivityForResult(intent,2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            LoadRutas();
        }
    }
}
