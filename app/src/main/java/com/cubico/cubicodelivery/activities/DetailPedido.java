package com.cubico.cubicodelivery.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.cubico.cubicodelivery.BarcodeActivityVertical;
import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.adpters.RecycleAdapterGaleria;
import com.cubico.cubicodelivery.adpters.lv_boxbarcodeAdapter;
import com.cubico.cubicodelivery.api.CubicoWSClient;
import com.cubico.cubicodelivery.api.CubicoWebApiCliente;
import com.cubico.cubicodelivery.dialogs.DailogMotivosFragment;
import com.cubico.cubicodelivery.helper.ItemData;
import com.cubico.cubicodelivery.model.BultoDetail;
import com.cubico.cubicodelivery.model.BultoModel;
import com.cubico.cubicodelivery.model.CausalModel;
import com.cubico.cubicodelivery.model.ConfirmarPedidoModel;
import com.cubico.cubicodelivery.model.Mensajes;
import com.cubico.cubicodelivery.model.eMensaje;
import com.cubico.cubicodelivery.services.GPS_service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.cubico.cubicodelivery.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPedido extends AppCompatActivity  implements DailogMotivosFragment.returnDialogListener{
    private  final int CAMERA_REQUEST = 100;
    Uri file;
    //private final int START_CAMERA_REQUEST_CODE = 1;
   // private ImageView imageView;
    RecyclerView recyclerViewGaleria;
    private List<Bitmap> galeria;
    RecycleAdapterGaleria adapterGaleria;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private BroadcastReceiver broadcastReceiver;
    private Double latitud,longitud;
    private TextInputEditText  txtBoxBarcode,txtObs;
    ProgressDialog progressDialog;
    private Switch swMasivo;
    private ImageButton btnCloseEntrega,btnSing,btnListBultos,btnCamera;
    private List<CausalModel> causalModels;
    private ArrayList<ItemData> causalModelArrayList;
    private TextView lblNroBultos,lblEntregados,lblSaldos,lblLatitud,lblLongitud,lblAddressPedido;
    MaterialTextView lblCausal;
    private List<ItemData> items;
    private ArrayList<String> arrayList;
    lv_boxbarcodeAdapter lvitemAdapter;
    ArrayList selectedItems;
    String msg="";
    CubicoGlobal cg;
    String strIdTran,strIDtx,nroOrden;
    int counter=0, idEstado=25,idCausal=0,indexWhich=-1;
    int intIdActividad,cantBultos,cantBultosSaldo,intEstado,indexImagen;
    boolean isDevolucion=false;

    private static final String FOLDER_PRINCIPAL="cubicoImgApp/";
    private static final String FOLDER_IMG="cubicoImg";
    private static final String DIR_IMAGEN=FOLDER_PRINCIPAL;
    private String path;
    File fileImagen;
    Bitmap bitmap;

    /* camara */
    Uri outputFileUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;

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
                        lblAddressPedido.setText(getCompleteAddressString(latitud,longitud));
                    }
                }
            };
        }
        registerReceiver(this.broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedidos, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==R.id.action_delete){
            final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(DetailPedido.this);
            alertDialogBuilder.setTitle("Confirmación");
            alertDialogBuilder.setIcon(R.drawable.box);

            alertDialogBuilder.setMessage("¿Está seguro eliminar todos los bultos?");
            alertDialogBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                   delete_masivo();

                }
            });

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            android.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void delete_masivo() {
                ArrayList<String> tmp= arrayList;
                int conta=1;
                for (String i : tmp){
                    ElimarBulto(i);
                    conta++;

                }
                showMensaje("Se eliminaron " + tmp.size()+ " bultos");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cg=(CubicoGlobal)getApplication() ;
        setContentView(R.layout.activity_detail_pedido);
        LinearLayout lyPedidosDetail= findViewById(R.id.lyPedidosDetail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swMasivo= findViewById(R.id.swMasivo);
        btnCamera=findViewById(R.id.btnCamera);
        btnCloseEntrega=findViewById(R.id.btnCloseEntrega);
        btnSing=findViewById(R.id.btnSign);
        btnListBultos=findViewById(R.id.btnListBultos);
        txtBoxBarcode= findViewById(R.id.txtBoxBarcode);
        txtObs= findViewById(R.id.txtObs);
        lblNroBultos=findViewById(R.id.lblNroBultos);
        lblEntregados=findViewById(R.id.lblEntregados);
        lblSaldos=findViewById(R.id.lblSaldos);
        lblLatitud= findViewById(R.id.lblLatitudGps);
        lblLongitud=findViewById(R.id.lblLongitudGps);
        lblAddressPedido=findViewById(R.id.lblAddressPedido);
       // imageView = findViewById(R.id.imgPreview);
        recyclerViewGaleria= findViewById(R.id.rwgaleria);
        recyclerViewGaleria.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        lblCausal=findViewById(R.id.lblCausal);
        lblCausal.setText("");
        swMasivo.setChecked(false);
        /* obtener parametros pedidos*/
        strIDtx=(String)getIntent().getSerializableExtra("strIdTx");
        intIdActividad=Integer.parseInt((String)getIntent().getSerializableExtra("intIdActividad"));
        cantBultos=Integer.parseInt((String)getIntent().getSerializableExtra("cantBultos"));
        cantBultosSaldo=Integer.parseInt((String)getIntent().getSerializableExtra("cantBultosSaldo"));
        strIdTran=(String)getIntent().getSerializableExtra("strIdTran");
        nroOrden=(String)getIntent().getSerializableExtra("nroOrden");
        intEstado=Integer.parseInt((String)getIntent().getSerializableExtra("Estado"));
       lblNroBultos.setText(""+cantBultos);
       lblSaldos.setText(""+cantBultosSaldo);
        setTitle("Pedido Nro:" + nroOrden);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(2);
                finish();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        items = new ArrayList<ItemData>();
        arrayList=new ArrayList<String>();
        galeria= new ArrayList<Bitmap>();
        get_Bultos();

        get_Lista_causales();
        if (intEstado==25){
            btnCloseEntrega.setEnabled(false);
            btnSing.setEnabled(false);
            btnListBultos.setEnabled(false);
            btnCamera.setEnabled(false);
        }
        switch (intEstado)
        {
            case 10: //Pendiente
                lyPedidosDetail.setBackgroundColor(Color.WHITE);

                break;
            case 3: // en proceso
                lyPedidosDetail.setBackgroundColor(Color.YELLOW);
                break;
            case 15: // motivado
                lyPedidosDetail.setBackgroundColor(Color.parseColor("#C0392B"));
                break;
            case 22: // devolucion
                lyPedidosDetail.setBackgroundColor(Color.LTGRAY);
                break;

            case 23: // motivado parcial
                lyPedidosDetail.setBackgroundColor(Color.CYAN);
                break;
            case 25: // entreagdo
                lyPedidosDetail.setBackgroundColor(Color.GREEN);
                break;
            default:
                lyPedidosDetail.setBackgroundColor(Color.WHITE);
                break;
        }

        txtBoxBarcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Log.i("KEY",""+keyCode);

                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_UP ) {
                    // do stuff
                   counter++;
                    if (counter==2){
                        counter=0;
                        return true;
                    }
                    Log.i("KEY 3",""+keyCode);
                    String data=txtBoxBarcode.getEditableText().toString();
                   // showMensaje(data);
                    int isMasivo=1;
                    if (swMasivo.isChecked()){
                        isMasivo=3;
                    }

                    ConfirmarBulto(data,isMasivo);
                     return true;
                }

                return false;
            }
        });
        if (!runtime_permissions()){
           // showMensaje("Tengo permiso GPS");
            enabled_service();
            return;
        }
        //showMensaje("No tengo permiso GPS....");
      //  ln.addView(createTableLayout(36, 3));
        //createTableLayout(36, 5)
    }

    void get_Bultos(){
        final ProgressDialog   progressDialog = new ProgressDialog(DetailPedido.this);
        progressDialog.setTitle("Cubico WMS");
        progressDialog.setMessage("Cargando bultos entregados....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        try{
            Call<List<BultoDetail>> call= CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().Get_Listar_bultos(intIdActividad,strIDtx);
            call.enqueue(new Callback<List<BultoDetail>>() {
                @Override
                public void onResponse(Call<List<BultoDetail>> call, Response<List<BultoDetail>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<BultoDetail> bultoDetails= response.body();
                            arrayList.clear();
                            for(BultoDetail c:bultoDetails){
                                arrayList.add(c.getBulto());
                            }
                            refreshCountBultos(false);

                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<BultoDetail>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
        catch (Exception e){
            Log.i("ERROR BULTOS", e.getMessage());
            progressDialog.dismiss();
        }
    }

    void get_Lista_causales(){
      final ProgressDialog progressDialog = new ProgressDialog(DetailPedido.this);
        progressDialog.setTitle("Cubico WMS");
        progressDialog.setMessage("Cargando causales....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        try{
            Call<List<CausalModel>> call= CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().Get_ListaCausales(1,9);
            call.enqueue(new Callback<List<CausalModel>>() {
                @Override
                public void onResponse(Call<List<CausalModel>> call, Response<List<CausalModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            causalModels= response.body();
                           // showMensaje("causales en:" + causalModels.size());
                            Log.i("causales","causales en:" + causalModels.size());
                            items= new ArrayList<ItemData>();

                            for(CausalModel c:causalModels){
                                ItemData i = new ItemData();
                                i.setIntData3(c.getId_Causal());
                                i.setTexto1(c.getCausal());
                                items.add(i);
                            }

                            causalModelArrayList = new ArrayList<ItemData>();
                            causalModelArrayList.addAll(items);
                            lvitemAdapter = new lv_boxbarcodeAdapter(DetailPedido.this,causalModelArrayList);
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<CausalModel>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
        catch (Exception e){
            Log.i("Errorcausales", e.getMessage());
            progressDialog.dismiss();
        }
    }

    void showMensaje(String msj){
       // Toast.makeText(DetailPedido.this, msj, Toast.LENGTH_SHORT).show();
        Toast toast= Toast.makeText(getApplicationContext(),
                msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
        //   toast.getView().setBackgroundColor(R.color.colorYellow);
        toast.show();


    }

    void refreshCountBultos(boolean isDelete){
        int bultos= Integer.parseInt(lblNroBultos.getText().toString());
        int entregados=swMasivo.isChecked()?bultos: arrayList.size();
        if (isDelete){
            entregados= arrayList.size();
        }
        cantBultosSaldo= bultos-entregados;
        lblEntregados.setText(""+entregados);
        lblSaldos.setText(""+ cantBultosSaldo);
    }

    void addData(String boxBarcode){
        arrayList.add(boxBarcode);
        refreshCountBultos(false);

    }

    void delBulto(String boxBarcode){
        ArrayList<String> delArray = new ArrayList<String>();
        delArray.add(boxBarcode);
        arrayList.removeAll(delArray);
        refreshCountBultos(true);


    }

    public void btnListarBultos(View view) {
        final ArrayList<Integer> selList=new ArrayList();
        CharSequence[] lista= arrayList.toArray(new CharSequence[arrayList.size()]);
        boolean bl[] = new boolean[lista.length];
         selectedItems = new ArrayList();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title
        builder.setTitle("Bultos entregados");

      /*  builder.setMultiChoiceItems(lista, bl, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                {
                    // If user select a item then add it in selected items
                    selList.add(which);
                }
                else if (selList.contains(which))
                {
                    // if the item is already selected then remove it
                    selList.remove(Integer.valueOf(which));
                }
            }
        });*/

        builder.setSingleChoiceItems(lista, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("INDEX SELECT","Index:" + which);
                indexWhich=which;
            }
        });
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
               // Log.i("INDEX SELECT oncli","Index positivo:" + which);
                if (arrayList.size()>0) {
                    msg = "";
                    msg = arrayList.toArray()[indexWhich].toString();
                    ConfirmarBulto(msg, 2);
                }
                else{
                    showMensaje("No hay bultos para eliminar");
                }
             /*   for (int i = 0; i < selList.size(); i++) {

                    msg=arrayList.toArray()[selList.get(i)].toString();
                    ConfirmarBulto(msg,2);

                }*/



            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builder.show();



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

    public void btnTakePicture(View view) {

        // Checking Permission for Android M and above
        if (ActivityCompat.checkSelfPermission(DetailPedido.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(DetailPedido.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailPedido.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_FROM_CAMERA);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            outputFileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(captureIntent, PICK_FROM_CAMERA);
        } else {
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
            outputFileUri = Uri.fromFile(file);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(captureIntent, PICK_FROM_CAMERA);
        }



      /*  if(Build.VERSION.SDK_INT>=23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                takePicture(view);
           //     takeFoto();
               *//* Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFile());
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }*//*



            }
        }*/
    }
/* Metodos de camara*/
    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //file = Uri.fromFile(createImageFile());
        File photoFile=null;
        try {
            photoFile = createImageFile();
            Log.i("photoFile",photoFile.toString());
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.i("ErrIMG",ex.getMessage());
        }

        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
            file = Uri.parse(photoFile.toString());
        } else{
            file=Uri.fromFile(photoFile);
        }
        Log.i("getOutputMediaFile",file.toString());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, CAMERA_REQUEST);
    }
    private  File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "CUB_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        path = image.getAbsolutePath();
        Log.i("PATH" , path);
        return image;
    }

    private void dispatchTakePictureIntent() {
        //Toast.makeText(this, "camera dispatchTakePictureIntent", Toast.LENGTH_LONG).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI ;/*= FileProvider.getUriForFile(DetailPedido.this,
                        "com.cubico.cubicodelivery.fileprovider",
                        photoFile);*/
                if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
                    photoURI = Uri.parse(photoFile.toString());
                } else{
                    photoURI=Uri.fromFile(photoFile);
                }
                Log.i("photoURI",photoURI.toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    Uri getPhotoFile(){
        Uri photoURI=null;
        File photoFile = null;
        try {
            photoFile = File.createTempFile("temppic",".jpg",getApplicationContext().getCacheDir());//createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.i("ErrIMG",ex.getMessage());
        }
        if (photoFile != null) {
            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
                photoURI = Uri.parse(photoFile.toString());
            } else{
                photoURI=Uri.fromFile(photoFile);
            }
           /*  photoURI = FileProvider.getUriForFile(this,
                    "com.cubico.cubicodelivery.fileprovider",
                    photoFile);*/

            Log.i("PHOTO_URI",photoURI.toString());
        }
        return photoURI;
    }

    private void takeFoto() {
            //File file=createImageFile();//new File(Environment.getExternalStorageDirectory(),DIR_IMAGEN);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
       // if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("ErrIMG",ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

              /*  Uri photoURI = FileProvider.getUriForFile(this,
                        "com.cubico.cubicodelivery.fileprovider",
                        photoFile);*/
                Uri photoURI;
                if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
                    //ImageView.setImageURI(Uri.parse(new File("/sdcard/cats.jpg").toString()));
                    photoURI = Uri.parse(photoFile.toString());
                } else{
                    //ImageView.setImageURI(Uri.fromFile(new File("/sdcard/cats.jpg")));
                    photoURI=Uri.fromFile(photoFile);
                }

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
         //   }
        }


        /*boolean isCreate=file.exists();
        if (!isCreate){
            isCreate=file.mkdirs();

        }
        Log.i("TAKEFOTO","" + isCreate);

        if (isCreate){
            Long correlativo= System.currentTimeMillis()/1000;
            String imgName=correlativo.toString() + ".jpg";
            path=Environment.getExternalStorageDirectory()+ File.separator + DIR_IMAGEN + File.separator +imgName;
            fileImagen= new File(path);
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }*/

    }

    /* Metodos de camara*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFile());
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("ActivityResult","" + resultCode);
        Bitmap bitmap;
        switch (requestCode) {
            case PICK_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {

                    Uri selectedImage = outputFileUri;
                    ContentResolver cr = getContentResolver();
                    getContentResolver().notifyChange(selectedImage, null);
                    try {
                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
                        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                        bitmap = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                        galeria.add(bitmap);
                        adapterGaleria= new RecycleAdapterGaleria(galeria, new RecycleAdapterGaleria.OnclickRecycle() {
                            @Override
                            public void OnclickItemRecycle(Bitmap imagen) {
                                indexImagen=galeria.indexOf(imagen);
                                MostrarImagen(imagen);
                                //Glide.with(getApplicationContext()).load(imagen).into(imageView);
                            }
                        });
                        recyclerViewGaleria.setAdapter(adapterGaleria);
                       /* imgPreview.setImageBitmap(bitmap);
                        imgPath.setText(outputFileUri.getPath());*/
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                break;
            default:
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if(result != null)
                    if (result.getContents() != null){
                        String resultaBarcodeText=result.getContents();
                        txtBoxBarcode.setText( resultaBarcodeText);
                        int isMasivo=1;
                        if (swMasivo.isChecked()){
                            isMasivo=3;
                        }
                        ConfirmarBulto(resultaBarcodeText,isMasivo);
                    }else{
                        txtBoxBarcode.setText("");
                        showMensaje("Error al escanear el código de barras");
                    }
                break;

        }

      /*  if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            MediaScannerConnection.scanFile(this, new String[]{path}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("IMG_PATH",""+ path );
                        }
                    }
            );

           // Bitmap photo = BitmapFactory.decodeFile(path);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            galeria.add(photo);
            adapterGaleria= new RecycleAdapterGaleria(galeria, new RecycleAdapterGaleria.OnclickRecycle() {
                @Override
                public void OnclickItemRecycle(Bitmap imagen) {
                    indexImagen=galeria.indexOf(imagen);
                    MostrarImagen(imagen);
                    //Glide.with(getApplicationContext()).load(imagen).into(imageView);
                }
            });
            recyclerViewGaleria.setAdapter(adapterGaleria);
            *//*if (fileImagen.exists()){
                fileImagen.delete();
            }*//*
           // imageView.setImageBitmap(photo);
        }
        else{
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null)
                if (result.getContents() != null){
                    String resultaBarcodeText=result.getContents();
                    txtBoxBarcode.setText( resultaBarcodeText);
                    int isMasivo=1;
                    if (swMasivo.isChecked()){
                        isMasivo=3;
                    }
                    ConfirmarBulto(resultaBarcodeText,isMasivo);
                }else{
                    txtBoxBarcode.setText("");
                    showMensaje("Error al escanear el código de barras");
                }
        }
*/

    }

    public void btnMotivar(View view) {
        int entregados= Integer.parseInt( lblEntregados.getText().toString());
        if (entregados>0){
            showMensaje("TIENE BULTOS ENTREGADOS");
            //return;
        }
        Bundle args= new Bundle();
        //args.putParcelableArrayList("lstmotivos",(ArrayList<? extends Parcelable>) causalModelArrayList);

        args.putSerializable("lstmotivos",(Serializable) causalModelArrayList);
        DailogMotivosFragment dailogMotivosFragment= new DailogMotivosFragment();
        dailogMotivosFragment.setArguments(args);
        dailogMotivosFragment.show(getSupportFragmentManager(),"DialogMotivos");
    }

    void MostrarImagen(Bitmap imgEvidencia){
      final Bitmap imgev= imgEvidencia;
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(DetailPedido.this);
        builderSingle.setTitle("Evidencia : " + indexImagen+1);
       // builderSingle.setView(img);
        builderSingle.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
       builderSingle.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

                   galeria.remove(indexImagen);
                   adapterGaleria.notifyDataSetChanged();


               dialog.dismiss();
           }
       });

       final AlertDialog dialog = builderSingle.create();
        LayoutInflater inflater= getLayoutInflater();
        View dialogLayout=inflater.inflate(R.layout.imgviewer,null);
        ImageView img= dialogLayout.findViewById(R.id.img_evidencia);
        img.setImageBitmap(imgev);
        dialog.setView(dialogLayout);

        dialog.show();
       /* dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                //ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                ImageView img= dialog.findViewById(R.id.img_evidencia);
                img.setImageBitmap(imgev);
                float imageWidthInPX = (float)img.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)imgev.getHeight() / (float)imgev.getWidth()));
                img.setLayoutParams(layoutParams);


            }
        });*/
        //builderSingle.show();
    }

    void motivar(){

        int entregados= Integer.parseInt( lblEntregados.getText().toString());
        if (entregados>0){
            showMensaje("TIENE BULTOS ENTREGADOS, NO PUEDE MOTIVAR");
            return;
        }
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(DetailPedido.this);
        builderSingle.setIcon(R.drawable.risk50);
        builderSingle.setTitle("MOTIVOS DE NO ENTREGA");
        builderSingle.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idEstado=25;
                idCausal=0;
                lblCausal.setText("");
                dialog.dismiss();

            }
        });
        builderSingle.setAdapter( lvitemAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ItemData cm= (ItemData) lvitemAdapter.getItem(which);
                lblCausal.setText(cm.getTexto1());
                idEstado=15;
                idCausal=cm.getIntData3();
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    void ConfirmarBulto(final String bulto, int tipo){
        if (cantBultosSaldo==0 && tipo!=2){
            showMensaje("Todos los bultos entregados...");
            return;
        }
        if (bulto.length()>0){
            boolean dataFind=false;
            for (String i : arrayList){
                if (i.equals(bulto)){
                    dataFind=tipo==2?false:true ;

                    break;
                }
            }

            if (!dataFind) {
                progressDialog = new ProgressDialog(DetailPedido.this);
                progressDialog.setTitle("Cubico WMS");
                progressDialog.setMessage((tipo!=2?"Registrando bulto....":"Eliminado bulto"));
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
                // showMensaje("Registrando bulto....");
                try{
                    final BultoModel bultoModel= new BultoModel();
                    bultoModel.setStrBulto(bulto);
                    bultoModel.setIntIdActividad(intIdActividad);
                    bultoModel.setStrIdTran(strIdTran);
                    bultoModel.setStrIdTx(strIDtx);
                    bultoModel.setIntTipo(tipo);
                    bultoModel.setStrUser(cg.getUsuario());
                    Call<List<Mensajes>> call= CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().Post_ConfirmarEntregaBulto(
                            bultoModel.getStrBulto(),
                            bultoModel.getStrIdTran(),
                            ""+ bultoModel.getIntIdActividad(),
                            bultoModel.getStrIdTx(),
                            bultoModel.getStrUser(),
                            ""+ bultoModel.getIntTipo());

                    call.enqueue(new Callback<List<Mensajes>>() {
                        @Override
                        public void onResponse(Call<List<Mensajes>> call, Response<List<Mensajes>> response) {
                            //  eMensaje msnx= new eMensaje();
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    List<Mensajes> msL = response.body();
                                    for(Mensajes ms:msL){
                                        if (ms.getERROR()==0){
                                            showMensaje(ms.getMENSAGE());
                                            if (bultoModel.getIntTipo()!=2){
                                                if (bultoModel.getIntTipo()==3){
                                                    get_Bultos();
                                                }
                                                else{
                                                    addData(bulto);
                                                }

                                            }
                                            else{
                                                progressDialog.dismiss();
                                                delBulto(bulto);

                                            }

                                        }
                                        else{
                                            //Log.i("ERROR CONFIRM",ms.getMessage());
                                            showMensaje(ms.getMENSAGE());
                                        }
                                    }

                                    progressDialog.dismiss();
                                }
                                else{


                                    //  showMensaje("Body null");
                                    progressDialog.dismiss();
                                    Log.i("ERROR CONFIRM","Body null");
                                }
                            }
                            else{
                                Log.i("ERROR CONFIRM","Response:"+ response.code());
                                // progressDialog.dismiss();
                                showMensaje("Response:"+ response.code());
                                //
                            }
                            txtBoxBarcode.requestFocus();
                            txtBoxBarcode.selectAll();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<List<Mensajes>> call, Throwable t) {
                            Log.i("ERROR CONFIRM","onFailure---:" + t.getMessage());

                            txtBoxBarcode.requestFocus();
                            txtBoxBarcode.selectAll();
                            //showMensaje("onFailure---:" + t.getMessage());
                            progressDialog.dismiss();
                        }
                    });
                }
                catch (Exception ex){
                    progressDialog.dismiss();
                    Log.i("ERROR CONFIRM",ex.getMessage());
                    txtBoxBarcode.requestFocus();
                    txtBoxBarcode.selectAll();
                   // showMensaje(ex.getMessage());

                }


            }
            else{
                showMensaje("Bulto recibido...");
            }
        }
        else{
            showMensaje("Ingrese bulto");
        }



        //return msn;
    }

    void ElimarBulto(final String bulto){
        if (Integer.parseInt(lblEntregados.getText().toString())==0 ){
            showMensaje("No hay bultos entregados...");
            return;
        }
        if (bulto.length()>0){
            boolean dataFind=false;
            for (String i : arrayList){
                if (i.equals(bulto)){
                    dataFind=false ;

                    break;
                }
            }

            if (!dataFind) {
                // showMensaje("Registrando bulto....");
                try{
                    final BultoModel bultoModel= new BultoModel();
                    bultoModel.setStrBulto(bulto);
                    bultoModel.setIntIdActividad(intIdActividad);
                    bultoModel.setStrIdTran(strIdTran);
                    bultoModel.setStrIdTx(strIDtx);
                    bultoModel.setIntTipo(2);
                    bultoModel.setStrUser(cg.getUsuario());
                    Call<List<Mensajes>> call= CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().Post_ConfirmarEntregaBulto(
                            bultoModel.getStrBulto(),
                            bultoModel.getStrIdTran(),
                            ""+ bultoModel.getIntIdActividad(),
                            bultoModel.getStrIdTx(),
                            bultoModel.getStrUser(),
                            ""+ bultoModel.getIntTipo());

                    call.enqueue(new Callback<List<Mensajes>>() {
                        @Override
                        public void onResponse(Call<List<Mensajes>> call, Response<List<Mensajes>> response) {
                            //  eMensaje msnx= new eMensaje();
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    List<Mensajes> msL = response.body();
                                    for(Mensajes ms:msL){
                                        if (ms.getERROR()==0){
                                            showMensaje(ms.getMENSAGE());
                                                delBulto(bulto);
                                        }
                                        else{
                                            showMensaje(ms.getMENSAGE());
                                        }
                                    }
                                }
                                else{
                                    Log.i("ERROR CONFIRM","Body null");
                                }
                            }
                            else{
                                Log.i("ERROR CONFIRM","Response:"+ response.code());
                                // progressDialog.dismiss();
                                showMensaje("Response:"+ response.code());
                                //
                            }
                            txtBoxBarcode.requestFocus();
                            txtBoxBarcode.selectAll();
                        }

                        @Override
                        public void onFailure(Call<List<Mensajes>> call, Throwable t) {
                            Log.i("ERROR CONFIRM","onFailure---:" + t.getMessage());

                            txtBoxBarcode.requestFocus();
                            txtBoxBarcode.selectAll();
                        }
                    });
                }
                catch (Exception ex){
                    Log.i("ERROR CONFIRM",ex.getMessage());
                    txtBoxBarcode.requestFocus();
                    txtBoxBarcode.selectAll();

                }


            }
        }
        else{
            showMensaje("Ingrese bulto");
        }
    }

    public void btnScanCamera(View view) {

        IntentIntegrator i= new IntentIntegrator(DetailPedido.this);
        i.setCameraId(0);
        i.setOrientationLocked(false);
        i.setCaptureActivity(BarcodeActivityVertical.class);

        i.setPrompt("CÚBICO WMS:ESCANEE CÓDIGO DE BARRAS");
        i.initiateScan();
    }

    public void btnConfirmarEntrega(View view) {
        if (idCausal==0 && Integer.parseInt(lblSaldos.getText().toString())>0)
        {
            showMensaje("HAY BULTOS POR ENTREGAR O INGRESE UNA CAUSAL");
            return;

        }
        if (arrayList.size()==0 && swMasivo.isChecked()){
            showMensaje("REGISTRE AL MENOS UN BULTO PARA ENTREGA MASIVA");
            return;
        }

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(DetailPedido.this);
        alertDialogBuilder.setTitle("Confirmación");
        alertDialogBuilder.setIcon(R.drawable.box);

        alertDialogBuilder.setMessage("¿Está seguro cerrar entrega " + (swMasivo.isChecked()?"masiva?":"?"));
        alertDialogBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                confirmarEntrega();

            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void confirmarEntrega() {

        if (idCausal==0 && arrayList.size()==0 && !swMasivo.isChecked()){
            showMensaje("REGISTRE UN BULTO O INGRESE UNA CAUSAL");
            return;
        }
        final ProgressDialog   progressDialog = new ProgressDialog(DetailPedido.this);
        progressDialog.setTitle("Cubico WMS");
        progressDialog.setIcon(R.drawable.boxscan);
        progressDialog.setMessage("Confirmando pedido....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
          /*  @intIdEstado		int,	--25: Entregado y 15: Motivado
            @intIdCausal		int,	--0: en caso sea entregado correctamente*/
            // intIdActividad/strIdTx/strCx/strCy/intIdEstado/intIdCausal/strObservacion/imgRtFirma/imgRtPhoto
            ConfirmarPedidoModel cp= new ConfirmarPedidoModel();
            cp.setIntIdActividad(intIdActividad);
            cp.setStrIdTx(strIDtx);
            cp.setStrCx(lblLatitud.getText().toString());
            cp.setStrCy(lblLongitud.getText().toString());
            cp.setIntIdEstado(idEstado);
            cp.setIntIdCausal(idCausal);
            cp.setStrObservacion(txtObs.getEditableText().toString());
            cp.setImgRtFirma(Base64.encodeToString( convertImageToByte(getDrawable(R.drawable.noimage)),Base64.DEFAULT));
           cp.setImgRtPhoto(Base64.encodeToString( convertImageToByte(getDrawable(R.drawable.noimage)),Base64.DEFAULT));
            ArrayList<String> tempo = new ArrayList<>();
            for(Bitmap i : galeria){
                tempo.add(Base64.encodeToString( convertImageToByte(i),Base64.DEFAULT));
            }
            cp.setImgRtPhoto2(tempo);
            cp.setFlagDevolucion(isDevolucion);
           // Log.i("CP",cp.getStrCx());


            Call<eMensaje> call = CubicoWebApiCliente.getInstance(cg.getWebApi())
                    .getApiCubicoWebApi()
                    .Post_ConfirmarEntregaPedido(cp);

            call.enqueue(new Callback<eMensaje>() {
                @Override
                public void onResponse(Call<eMensaje> call, Response<eMensaje> response) {
                    if (response.code() != 200) {
                        showMensaje(response.message());
                        progressDialog.dismiss();
                    } else {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                eMensaje msn = response.body();
                                // Log.i("Count msn", "" + msn.size());
                                if (msn.getErrNumber()==0){

                                    idCausal=0;
                                    idEstado=0;
                                    lblCausal.setText("");
                                    showMensaje(msn.getMessage());
                                    setResult(2);
                                    finish();
                                }
                                showMensaje(msn.getMessage());
                               // Log.i("RespnseErr",msn.getMessage());

                            } else {
                                showMensaje("Body null");
                            }

                        } else {
                            showMensaje("Code:" + response.code());
                        }
                        progressDialog.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<eMensaje> call, Throwable t) {
                    Log.i("ERROR ONFAILURE", t.getMessage());
                    progressDialog.dismiss();
                }

            });



        }
        catch (Exception ex){
            Log.i("ERROR ", ex.getMessage());

            progressDialog.dismiss();
        }
    }

    private byte[] convertImageToByte(Drawable d){

       // Drawable d = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }
    private byte[] convertImageToByte(Bitmap d){

        // Drawable d = imageView.getDrawable();
        Bitmap bitmap = d;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }

    @Override
    public void onClickDialog(boolean esDovolucion, String motivo, int idMotivo, int idEstado) {
        lblCausal.setText(motivo);
        this.idEstado=idEstado;
        idCausal=idMotivo;
        isDevolucion=esDovolucion;
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
