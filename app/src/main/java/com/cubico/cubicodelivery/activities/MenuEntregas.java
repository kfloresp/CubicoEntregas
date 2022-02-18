package com.cubico.cubicodelivery.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.services.GPS_service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class MenuEntregas extends AppCompatActivity {
   // private BroadcastReceiver broadcastReceiver;
    private AppBarConfiguration mAppBarConfiguration;
    TextView lblUsername;
    CubicoGlobal cg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cg=(CubicoGlobal)getApplication();
        setContentView(R.layout.activity_menu_entregas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayAlertQuit();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        lblUsername=headerView.findViewById(R.id.lblUsuario);
        lblUsername.setText(cg.getUsername());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery,
                 R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

      /*  String str = "GPS INFO";
        if (!runtime_permissions()) {
            Log.i(str, "Tengo permiso GPS");
            enable_services();
            return;
        }
        Log.i(str, "Nooooo tengo permiso GPS");*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entregas, menu);
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void DisplayAlertQuit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuEntregas.this);
        alertDialogBuilder.setTitle(R.string.alert_title_aviso);

        alertDialogBuilder.setMessage("¿Está seguro salir de CÚBICO WMS?");
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

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadcastReceiver broadcastReceiver2 = this.broadcastReceiver;
        if (broadcastReceiver2 != null) {
            unregisterReceiver(broadcastReceiver2);
        }
    }
*/
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i("MAIN_ONRESUME", "onesumen void");
//        if (this.broadcastReceiver == null) {
//            broadcastReceiver=new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    cg.setLongitud((Double)intent.getExtras().get("Longitud"));
//                    cg.setLatitud((Double) intent.getExtras().get("Latitud"));
//                    Log.i("MAIN_ONRESUME", cg.getLatitud().toString());
//
//                   // StringBuilder sb = new StringBuilder();
//                  /*  sb.append("Lat:");
//                    sb.append(latitud);
//                    StringBuilder sb2 = new StringBuilder();
//                    sb2.append("/Lon:");
//                    sb2.append(longitud);
//                    lblLatitud.setText(sb.toString());
//                    lblLongitud.setText(sb2.toString());*/
//                }
//            };
//        }
//        registerReceiver(this.broadcastReceiver, new IntentFilter("location_update"));
//    }
  /*  private void enable_services() {
        startService(new Intent(getApplicationContext(), GPS_service.class));
    }

    private void disable_services() {
        stopService(new Intent(getApplicationContext(), GPS_service.class));
    }
    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            String str = "android.permission.ACCESS_FINE_LOCATION";
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                String str2 = "android.permission.ACCESS_COARSE_LOCATION";
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                    return true;
                }
            }
        }
        return false;
    }*/
}
