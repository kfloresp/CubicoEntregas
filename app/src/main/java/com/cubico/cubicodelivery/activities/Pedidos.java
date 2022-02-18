package com.cubico.cubicodelivery.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.adpters.PedidoViewAdapter;
import com.cubico.cubicodelivery.adpters.RecycleViewAdapter;
import com.cubico.cubicodelivery.api.CubicoWSClient;
import com.cubico.cubicodelivery.helper.ItemData;
import com.cubico.cubicodelivery.model.PedidosModel;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.cubico.cubicodelivery.R;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pedidos extends AppCompatActivity {
    private String strIDtx,idProveedor,Cliente,Direccion;

    private TextView txtCliente,txtDireccion,txtIdTx;

    private RecyclerView lstPedidos;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private List<ItemData> items;
    private List<PedidosModel> pedidosModelList;
    private ArrayList<ItemData> arrayList;
    CubicoGlobal cg;
    ProgressDialog progressDialog;
    View ChildView ;
    int RecyclerViewItemPosition ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cg=(CubicoGlobal)getApplication() ;
        txtCliente=findViewById(R.id.txtCliente);
        txtDireccion=findViewById(R.id.txtDireccion);
        txtIdTx=findViewById(R.id.txtIdtx);
        lstPedidos= findViewById(R.id.lstPedidos);
        manager= new LinearLayoutManager(Pedidos.this);
        lstPedidos.setLayoutManager(manager);
        strIDtx=(String)getIntent().getSerializableExtra("strIDtx");
        idProveedor= (String)getIntent().getSerializableExtra("idProveedor");
        Cliente=(String)getIntent().getSerializableExtra("Cliente");
        Direccion=(String)getIntent().getSerializableExtra("Direccion");

        txtCliente.setText(Cliente);
        txtDireccion.setText(Direccion);
        txtIdTx.setText(strIDtx);
        LoadPedidos();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
               setResult(2);
               finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        lstPedidos.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(Pedidos.this, new GestureDetector.SimpleOnGestureListener() {
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
                    PedidoViewAdapter it=(PedidoViewAdapter)rv.getAdapter();
                  //  Toast.makeText(Pedidos.this, it.getItem(RecyclerViewItemPosition).getTextoId(), Toast.LENGTH_LONG).show();
                    gotoDetailPedido(it.getItem(RecyclerViewItemPosition));
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

        if (id == R.id.mnuRefresh) {
            LoadPedidos();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadPedidos() {

        progressDialog = new ProgressDialog(Pedidos.this);
        progressDialog.setTitle("Cubico WMS");
        progressDialog.setMessage("Cargando datos....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            Call<List<PedidosModel>> call= CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().Get_DetallePedidosxCliente(strIDtx,idProveedor);
            call.enqueue(new Callback<List<PedidosModel>>() {
                @Override
                public void onResponse(Call<List<PedidosModel>> call, Response<List<PedidosModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            pedidosModelList= response.body();

                            items = new ArrayList<ItemData>();
                            for(PedidosModel rm: pedidosModelList){
                                ItemData i= new ItemData();

                                i.setTexto1(rm.getId_Tx());
                                i.setTexto2(rm.getNumOrden());
                                i.setIntData1(rm.getCantidadBultos());
                                i.setIntData2(rm.getSaldoBultos());
                                i.setTextoId(rm.getId_Tra());
                                i.setTexto3(ConvertJSON2Date(rm.getFechaDocumento()));
                                i.setIntData3(rm.getId_Actividad());
                                i.setTxtTempo(""+rm.getId_EstadoCourier());
                                Log.i("EST",""+rm.getId_EstadoCourier());
                               /* 15: motivado
                                23: motivado parcial
                                22: devolucion pedido
                                25: entregado
                                */
                                switch (rm.getId_EstadoCourier())
                                {
                                    case 10: //Pendiente
                                        i.setColoBack(Color.WHITE);
                                        break;
                                    case 3: // en proceso
                                        i.setColoBack(Color.YELLOW);
                                        break;
                                    case 15: // motivado
                                        i.setColoBack(Color.parseColor("#FADBD8"));
                                        break;
                                    case 22: // devolucion
                                        i.setColoBack(Color.LTGRAY);
                                        break;

                                    case 23: // motivado parcial
                                        i.setColoBack(Color.CYAN);
                                        break;
                                    case 25: // entreagdo
                                        i.setColoBack(Color.GREEN);
                                        break;
                                    default:
                                        i.setColoBack(Color.WHITE);
                                       break;
                                }
                                items.add(i);

                            }
                            arrayList = new ArrayList<ItemData>();
                            arrayList.addAll(items);
                            adapter= new PedidoViewAdapter(Pedidos.this,arrayList);
                            lstPedidos.setAdapter(adapter);
                        }
                    }
                    else{


                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<PedidosModel>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });



        }
        catch (Exception ex){
            progressDialog.dismiss();
        }

    }
    void gotoDetailPedido(ItemData itemData){
        if (itemData.getIntData2()<0){
         //  Toast.makeText(Pedidos.this, "Bultos entregados...", Toast.LENGTH_SHORT).show();

            Toast toast= Toast.makeText(getApplicationContext(),
                    " TOTAL DE BULTOS ENTREGADOS... ", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
         //   toast.getView().setBackgroundColor(R.color.colorYellow);
            toast.show();
           /* Toast t = new Toast(Pedidos.this);

            t.setText("Bultos entregados...");
            t.setDuration(Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();*/
        }
        else{
            Intent intent = new Intent(getApplicationContext(), DetailPedido.class);
            intent.putExtra("strIdTran",itemData.getTextoId());
            intent.putExtra("intIdActividad",""+ itemData.getIntData3());
            intent.putExtra("cantBultos",""+ itemData.getIntData1());
            intent.putExtra("cantBultosSaldo",""+ itemData.getIntData2());
            intent.putExtra("strIdTx",itemData.getTexto1());
            intent.putExtra("nroOrden",itemData.getTexto2());
            intent.putExtra("Estado",itemData.getTxtTempo());
          //  startActivity(intent);
            startActivityForResult(intent,2);
        }


    }

    String ConvertJSON2Date(String data){
    String  jsonFecha= data.replace("/Date(","").replace("-0500)/","");
    long time= Long.parseLong(jsonFecha);
    Date d = new Date(time);
   return new SimpleDateFormat("dd/MM/yyyy").format(d);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            LoadPedidos();

        }
    }
}
