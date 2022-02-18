package com.cubico.cubicodelivery.activities.ui.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//import android.support.v7.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.api.CubicoWebApiCliente;
import com.cubico.cubicodelivery.model.InicioRutaModel;
import com.cubico.cubicodelivery.services.GPSTracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import com.cubico.cubicodelivery.activities.R;

public class HomeFragment extends Fragment {
    CubicoGlobal cg;
    private ProgressDialog progressDialog;
    private HomeViewModel homeViewModel;
    View root;
    private CardView btnInicioDia,btnFinDia;
    private TextView txtIdPlanificacion,txtDayInit,lblLatitud,lblLongitud;
    GPSTracker gps;
   // private BroadcastReceiver broadcastReceiver;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        cg=(CubicoGlobal)root.getContext().getApplicationContext();
        txtIdPlanificacion=root.findViewById(R.id.txtRutaPlanificion);
        txtDayInit=root.findViewById(R.id.txtDayInit);
        lblLatitud=root.findViewById(R.id.txtLatitud);
        lblLongitud=root.findViewById(R.id.txtLongitud);
        txtIdPlanificacion.setText("");
        txtDayInit.setText("Inicio de día");
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        btnInicioDia= root.findViewById(R.id.btnInicioDia);
        lblLatitud.setText("");
        lblLongitud.setText("");
        btnInicioDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                RegistroActividad();
            }
        });

        gps= new GPSTracker(root.getContext());
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* try {
            if (ActivityCompat.checkSelfPermission(root.getContext(), mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(root.getParent(), new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ONRESUME", "resume");

        getLocation();
        /*if (cg.getLatitud()!=null ) {
            Log.i("ONRESUME", cg.getLatitud().toString());
            lblLatitud.setText(cg.getLatitud().toString());
            lblLongitud.setText(cg.getLongitud().toString());
        }
        else{
            Log.i("ONRESUME", "Non Data");
        }*/
    }

    void getLocation(){
        if(gps.canGetLocation()){
            Log.i("ONRESUME", "OnGPS" + gps.getLatitude());
            lblLatitud.setText(""+gps.getLatitude());
            lblLongitud.setText(""+ gps.getLongitude());
        }
        else{
            gps.showSettingsAlert();
        }
    }
    //public View onResumeView()
    private void RegistroActividad() {
        progressDialog = new ProgressDialog(root.getContext());
        progressDialog.setTitle("Cubico Delivery");
        progressDialog.setMessage("Buscando rutas....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        Double lat=Double.parseDouble(lblLatitud.getText().toString());
        Double lon=Double.parseDouble(lblLongitud.getText().toString());
        try {
            Call<List<InicioRutaModel>> call= CubicoWebApiCliente.getInstance(cg.getWebUrl()).getApiCubicoWebApi().Post_RegistrarActividad(cg.getUsuario(),lat,lon,Integer.parseInt(cg.getTerminalId()) ,2);
            call.enqueue(new Callback<List<InicioRutaModel>>() {
                @Override
                public void onResponse(Call<List<InicioRutaModel>> call, Response<List<InicioRutaModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            for(InicioRutaModel inicioRutaModel: response.body()){
                                txtIdPlanificacion.setText(inicioRutaModel.getId_Tra());
                                //Log.i("FECHA:",inicioRutaModel.getInicioActividad());
                                String  jsonFecha= inicioRutaModel.getInicioActividad().replace("/Date(","").replace("-0500)/","");
                                long time= Long.parseLong(jsonFecha);
                                Date d = new Date(time);
                                txtDayInit.setText( new SimpleDateFormat("dd/MM/yyyy hh:mm").format(d));
                              //  Log.i("FECHAss:", new SimpleDateFormat("MM/dd/yyyy hh:mm").format(d));
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



}