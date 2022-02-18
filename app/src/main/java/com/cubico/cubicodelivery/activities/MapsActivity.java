package com.cubico.cubicodelivery.activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.model.RutaModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public Double latitud=0.0,longitud=0.0;
    private  List<RutaModel> rutaMap;
    CubicoGlobal cg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        cg=(CubicoGlobal)getApplication() ;


                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        latitud=(Double) getIntent().getSerializableExtra("latitud");
        longitud=(Double) getIntent().getSerializableExtra("longitud");

        Bundle extra = getIntent().getBundleExtra("extra");
        rutaMap = cg.getRutaModelList();

        Log.i("lat",""+latitud);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        //mapFragment.s
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.i("lat",""+latitud);
        // Add a marker in Sydney and move the camera
        LatLng inicioRuta = new LatLng(latitud, longitud);
        //MarkerOptions mo=
        mMap.addMarker(new MarkerOptions()
                .position(inicioRuta)
                .title("INICIO DE RUTA")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicioRuta,11));
        try{
            for(RutaModel rm : rutaMap){
                if (rm.getLongitud()!=0 && rm.getLatitud()!=0){
                    LatLng varMap=new LatLng(rm.getLatitud(),rm.getLongitud());
                    mMap.addMarker(new MarkerOptions().position(varMap)
                            .title(rm.getCliente())
                            .snippet(rm.getDireccion()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(varMap,11));
                }

            }
        }
       catch (Exception ex){
            Log.i("Exception Maps",ex.getMessage());
       }
       // mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }
}
