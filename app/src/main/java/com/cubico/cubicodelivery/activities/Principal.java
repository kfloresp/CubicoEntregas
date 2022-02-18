package com.cubico.cubicodelivery.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.cardview.widget.CardView;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.cubico.cubicodelivery.R;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String userName;
    String usuario;
    int idAlmacen;
    int idCedis;
    TextView txtUserName,txtWarehouse;

    private CardView mnuRecibo,mnuTrPrIn;
    private CardView mnuTrPrOut,mnuMovePallet;
    CubicoGlobal cg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cg=(CubicoGlobal)getApplication();
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName=cg.getUsername();//(String)getIntent().getSerializableExtra("UserName");
        usuario=cg.getUsuario();//(String)getIntent().getSerializableExtra("usuario");
        idCedis=cg.getIdCedis();//(int)getIntent().getSerializableExtra("idCedis");
        idAlmacen=cg.getWareHouseId();//(int)getIntent().getSerializableExtra("idAlmacen");
        //cg.setWareHouseName((String)getIntent().getSerializableExtra("Warehouse"));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        txtUserName=headerView.findViewById(R.id.txtUserName);
        txtUserName.setText(userName);
        txtWarehouse=headerView.findViewById(R.id.txtWarehouse);
        txtWarehouse.setText(cg.getWareHouseName());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mnuRecibo= findViewById(R.id.mnuRecibo);
        mnuTrPrIn= findViewById(R.id.mnuTrProdIn);
        mnuTrPrOut= findViewById(R.id.mnuTrProdOut);
        mnuMovePallet=findViewById(R.id.mnuPickApilador);
        mnuTrPrIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTransferencias(2);
            }
        });
        mnuTrPrOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTransferencias(1);
            }
        });
        mnuMovePallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMovePalletPicking();
            }
        });
        mnuRecibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // gotoRecibo();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            DisplayAlertQuit();
        //} else if (id == R.id.nav_download) {
            //gotoTransferencias();
        //} else if (id == R.id.nav_upload) {

//        } else if (id == R.id.nav_tools) {
           // gotoSettingsCubico();
  //          return true;
        } else if (id == R.id.nav_report) {
            DisplayAlertQuit();
        } else if (id == R.id.nav_exit) {
            DisplayAlertQuit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/*
    void gotoSettingsCubico(){
        Intent si= new Intent(this, SettingsActivity.class);
        startActivity(si);

     //   startActivity(new Intent(getApplicationContext(), SettingsCubicoActiviy.class));

    }*/

    void DisplayAlertQuit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Principal.this);
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

    void gotoTransferencias(int idtipo){
         Intent i = new Intent(getApplicationContext(), Transferencias.class);
        i.putExtra("idCedis", idCedis);
        i.putExtra("idAlmacen", idAlmacen);
        i.putExtra("usuario", usuario);
        i.putExtra("UserName", userName);
        i.putExtra("idTipo",idtipo);

        startActivity(i);
    }

  void gotoMovePalletPicking(){
      Intent i = new Intent(getApplicationContext(), MoverPalletPicking.class);
      startActivity(i);
  }

    void gotoRecibo(){
        Intent i = new Intent(getApplicationContext(), Recibo.class);
        startActivity(i);
    }
}
