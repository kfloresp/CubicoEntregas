package com.cubico.cubicodelivery.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.api.CubicoWebApiCliente;
import com.cubico.cubicodelivery.model.InicioRutaModel;
import com.cubico.cubicodelivery.model.Mensajes;
import com.cubico.cubicodelivery.services.GPSTracker;
import com.cubico.cubicodelivery.services.GPS_service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cubico.cubicodelivery.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioDia extends AppCompatActivity {
    private TextView lblUsername,lblAddress;
    private CubicoGlobal cg;
    private ProgressDialog progressDialog;
    private CardView btnInicioDia,btnFinDia;
    private TextView txtIdPlanificacion,txtDayInit,lblLatitud,lblLongitud;
//    GPSTracker gps;
    private int idActividad=0;
    // private BroadcastReceiver broadcastReceiver;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private BroadcastReceiver broadcastReceiver;
    private Double latitud=0.0,longitud=0.0;

   /* @Override
    protected void onResume() {
        super.onResume();
        Log.i("ONRESUME", "resume");

        getLocation();
    }*/
   @Override
   protected void onDestroy() {
       super.onDestroy();
       BroadcastReceiver broadcastReceiver1= this.broadcastReceiver;
       if (broadcastReceiver1!=null){
           unregisterReceiver(broadcastReceiver1);
       }
   }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.broadcastReceiver==null) {
            broadcastReceiver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    longitud=(Double)intent.getExtras().get("Longitud");
                    latitud=(Double)intent.getExtras().get("Latitud");
                    lblLongitud.setText(""+longitud);
                    lblLatitud.setText(""+latitud);
                    if (longitud!=0 && latitud!=0){
                        lblAddress.setText(getCompleteAddressString(latitud,longitud));
                    }
                }
            };
        }
        registerReceiver(this.broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_dia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cg=(CubicoGlobal)getApplication();
        lblUsername=findViewById(R.id.lblUsuario);
        lblUsername.setText(cg.getUsername());
        lblAddress=findViewById(R.id.lblAddress);
        lblAddress.setText("");
        txtIdPlanificacion=findViewById(R.id.txtRutaPlanificion);
        txtDayInit=findViewById(R.id.txtDayInit);
        lblLatitud=findViewById(R.id.txtLatitud);
        lblLongitud=findViewById(R.id.txtLongitud);
        txtIdPlanificacion.setText("");
        txtDayInit.setText("Inicio de día");
        btnInicioDia= findViewById(R.id.btnInicioDia);
        btnFinDia= findViewById(R.id.btnFinDia);
        lblLatitud.setText("0");
        lblLongitud.setText("0");
        //gps= new GPSTracker(InicioDia.this);
        btnInicioDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                if (latitud==0.0 && longitud==0.0){
                    Snackbar.make(v, "Obteniendo GPS/GPS Deshabilitado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    RegistroActividad();
                }

            }
        });

        btnFinDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtIdPlanificacion.getText().toString().length()==0){
                    Snackbar.make(v, "Inicie día, por favor", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InicioDia.this);
                alertDialogBuilder.setTitle(R.string.alert_title_aviso);
                alertDialogBuilder.setIcon(R.drawable.flag_red);
                alertDialogBuilder.setMessage("¿Está seguro Cerrar actividad?");
                alertDialogBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //  Toast.makeText(Principal.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                        CerrarActividad();
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
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fab2 = findViewById(R.id.fabprev);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                if (txtIdPlanificacion.getText().toString().length()>0){
                    Intent intent= new Intent(getApplicationContext(), Ruta.class);
                    intent.putExtra("strIdTra",txtIdPlanificacion.getText());
                    intent.putExtra("idActividad",idActividad);
                    intent.putExtra("latitud",latitud);
                    intent.putExtra("longitud",longitud);

                    startActivity(intent);
                }
                else{
                    Snackbar.make(view, "Inicie día, por favor", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayAlertQuit();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (!runtime_permissions()){
            showMensaje("Tengo permiso GPS");
            enabled_service();
            return;
        }
        showMensaje("No tengo permiso GPS....");

    }
    private void enabled_service(){
        startService(new Intent(getApplicationContext(), GPS_service.class));
    }
    private void disabled_service(){
        stopService(new Intent(getApplicationContext(), GPS_service.class));
    }
    private boolean runtime_permissions(){
        if(Build.VERSION.SDK_INT>=23){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
                    return true;
                }
            }
        }
        return false;
    }

    void showMensaje(String msj){
        Toast.makeText(InicioDia.this, msj, Toast.LENGTH_SHORT).show();
    }

    void getLocation(){
      /*  if(gps.canGetLocation()){
            Log.i("ONRESUME", "OnGPS" + gps.getLatitude());
            lblLatitud.setText(""+gps.getLatitude());
            lblLongitud.setText(""+ gps.getLongitude());
        }
        else{
            gps.showSettingsAlert();
        }*/
    }

    @Override
    public void onBackPressed() {
        DisplayAlertQuit();
    }

    private void RegistroActividad() {
        progressDialog = new ProgressDialog(InicioDia.this);
        progressDialog.setTitle("Cubico Delivery");
        progressDialog.setMessage("Buscando rutas....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Double lat=Double.parseDouble(lblLatitud.getText().toString());
        //Double lon=Double.parseDouble(lblLongitud.getText().toString());
        try {
            Call<List<InicioRutaModel>> call= CubicoWebApiCliente.getInstance(cg.getWebUrl()).getApiCubicoWebApi().Post_RegistrarActividad(cg.getUsuario(),latitud,longitud,Integer.parseInt(cg.getTerminalId()) ,2);
            call.enqueue(new Callback<List<InicioRutaModel>>() {
                @Override
                public void onResponse(Call<List<InicioRutaModel>> call, Response<List<InicioRutaModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                          //  Log.i("RegActividad_body_succ", ""+ response.code());
                            txtIdPlanificacion.setText("");
                            idActividad=-1;
                            for(InicioRutaModel inicioRutaModel: response.body()){
                              //  Log.i("RegActividad_body_for", ""+ response.code());
                                txtIdPlanificacion.setText(inicioRutaModel.getId_Tra());
                                idActividad=inicioRutaModel.getId_Actividad();
                                //Log.i("FECHA:",inicioRutaModel.getInicioActividad());
                                String  jsonFecha= inicioRutaModel.getInicioActividad().replace("/Date(","").replace("-0500)/","");
                                long time= Long.parseLong(jsonFecha);
                                Date d = new Date(time);
                                txtDayInit.setText( new SimpleDateFormat("dd/MM/yyyy hh:mm").format(d));
                                //  Log.i("FECHAss:", new SimpleDateFormat("MM/dd/yyyy hh:mm").format(d));
                            }
                            if (idActividad==-1){
                                showMensaje("No hay ruta asignadas...");
                            }
                        }
                        else{
                            Log.i("RegActividad_body", ""+ response.code());
                        }

                    }
                    else{
                        Log.i("RegActividad_onResponse", ""+ response.code());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<InicioRutaModel>> call, Throwable t) {
                    Log.i("RegActividad_onFailure", "falla: " + t.getMessage());
                    progressDialog.dismiss();
                }
            });

        }
        catch (Exception ex){
            Log.i("RegActividad_Ex", ""+ ex.getMessage());
            progressDialog.dismiss();
        }
    }

    private void CerrarActividad(){
        progressDialog = new ProgressDialog(InicioDia.this);
        progressDialog.setTitle("Cubico Delivery");
        progressDialog.setMessage("Cerrando ruta...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
       // Double lat=Double.parseDouble(lblLatitud.getText().toString());
     //   Double lon=Double.parseDouble(lblLongitud.getText().toString());
        try {
            Call<List<Mensajes>> call= CubicoWebApiCliente.getInstance(cg.getWebUrl()).getApiCubicoWebApi().Post_CerrarActividad(
                    idActividad,
                    latitud,longitud);
            call.enqueue(new Callback<List<Mensajes>>() {
                @Override
                public void onResponse(Call<List<Mensajes>> call, Response<List<Mensajes>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            for(final Mensajes finruta: response.body()){

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InicioDia.this);
                                alertDialogBuilder.setTitle(R.string.alert_title_aviso);

                                alertDialogBuilder.setMessage(finruta.getMENSAGE());
                                alertDialogBuilder.setNegativeButton("Cerrar",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (finruta.getERROR()==0){
                                            txtIdPlanificacion.setText("");
                                            idActividad=0;
                                        }

                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            }
                        }
                        else{
                            Log.i("CerrarActividad_body", ""+ response.code());
                        }

                    }
                    else{
                        Log.i("CerrarAct_onResponse", ""+ response.code());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<Mensajes>> call, Throwable t) {
                    Log.i("CActividad_onFailure", "falla: " + t.getMessage());
                    progressDialog.dismiss();
                }
            });

        }
        catch (Exception ex){
            Log.i("CActividad_Ex", ""+ ex.getMessage());
            progressDialog.dismiss();
        }
    }

    void DisplayAlertQuit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InicioDia.this);
        alertDialogBuilder.setTitle(R.string.alert_title_aviso);

        alertDialogBuilder.setMessage("¿Está seguro salir de CÚBICO WMS Delivery?");
        alertDialogBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //  Toast.makeText(Principal.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                disabled_service();
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

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("GetLocation", strReturnedAddress.toString());
            } else {
                Log.w("GetLocation", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("GetLocation", "Canont get Address!");
        }
        return strAdd;
    }

}
