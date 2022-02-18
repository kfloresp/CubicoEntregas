package com.cubico.cubicodelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.cubico.cubicodelivery.activities.InicioDia;
import com.cubico.cubicodelivery.activities.MenuEntregas;
import com.cubico.cubicodelivery.api.CubicoWSClient;
import com.cubico.cubicodelivery.model.UsuarioModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MainActivity extends AppCompatActivity {
    private TextInputLayout tilUser;
    private TextInputLayout tilPass;
    private View mProgressView;
    private List<UsuarioModel> usuarioModel;
    private CubicoGlobal cg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilUser= findViewById(R.id.til_user);
        tilPass=findViewById(R.id.til_pass);
        //   mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });
        //carga variables globales
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String url = settings.getString("url", "");
        String urlWebApi=settings.getString("urlwebApi", "");
        String idTerminal =settings.getString("TerminalId", "");
        String ApiKeyMaps =settings.getString("KeyAPIMaps", "");
      //  ApiKeyMaps=getResources().getString(R.string.google_maps_api_key);
        cg = (CubicoGlobal)getApplication();
        cg.setWebUrl(url);
        cg.setWebApi(urlWebApi);
        cg.setTerminalId(idTerminal);
        cg.setAPiKeyMaps(ApiKeyMaps);


    }


    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        //mLogoView.setVisibility(visibility);
        // mLoginFormView.setVisibility(visibility);
    }

    private void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z0-9. ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            tilUser.setError("Usuario inválido");
            return false;
        } else {
            tilUser.setError(null);
        }

        return true;
    }

    private boolean esPassValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z0-9 ]+$");
        if (nombre.length() == 0) {
            tilPass.setError("Contraseña inválida");
            return false;
        } else {
            tilPass.setError(null);
        }

        return true;
    }
    private void validarDatos() {
        final String users = tilUser.getEditText().getText().toString();
        final String pass = tilPass.getEditText().getText().toString();
        boolean a = esNombreValido(users);
        boolean b = esPassValido(pass);
        if (a && b) {
            showProgress(true);

            Call<List<UsuarioModel>> call = CubicoWSClient.getInstance(cg.getWebUrl()).getApiCubico().userLogin(users,pass,Integer.parseInt(cg.getTerminalId()));

            call.enqueue(new Callback<List<UsuarioModel>>() {
                @Override
                public void onResponse(Call<List<UsuarioModel>> call, Response<List<UsuarioModel>> response) {
                    Log.i("onResponse","en Onresponse");
                    showProgress(false);
                    if(response.isSuccessful()){
                        if (response.body() != null) {
                            usuarioModel= response.body();
                            Log.i("RESPONSE BODY:" ,"" + response.body().size());
                            Log.i("USUARIO MODEL",""+usuarioModel.size() + " / " + users + " / " + pass);
                            if (usuarioModel.isEmpty()){
                                Toast.makeText(MainActivity.this, "¡Usuario/Contraseña no registrado!", Toast.LENGTH_LONG).show();
                            }
                            else {
                                UsuarioModel usuarioData= usuarioModel.get(0);
                                cg.setUsername(usuarioData.getApeNom());
                                cg.setUsuario(usuarioData.getUsuario());
                                Toast.makeText(MainActivity.this, "¡Bienvenido " + usuarioData.getApeNom() + "!", Toast.LENGTH_LONG).show();
                                Intent intent= new Intent(getApplicationContext(), InicioDia.class);
//                                intent.putExtra("UserName",usuarioData.getApeNom());
//                                intent.putExtra("user",usuarioData.getUsuario());
                                tilUser.getEditText().setText("");
                                tilPass.getEditText().setText("");
                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "¡Usuario no registrado!", Toast.LENGTH_LONG).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "LOGIN_NOT_SUCCESSFUL:" + response.code() + " " +  response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<UsuarioModel>> call, Throwable t) {
                    showProgress(false);
                    Log.i("ON_FAILURE",t.getLocalizedMessage());
                    Log.i("ON_FAILURE",t.getMessage());
                    Toast.makeText(MainActivity.this, "ON_FAILURE: "+ t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });



        }


    }


    public void settingCLick(View view) {
        Intent si= new Intent(this, SettingsActivity.class);
        startActivity(si);
    }
}
