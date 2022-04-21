package com.example.bancolombia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InicioSesion extends AppCompatActivity {

    Button registrarse2;
    Button registrarinicio;
    Button retrocederprincipal;
    Button continuar;

    private EditText usuarioinicio;
    private EditText contrasenainicio;
    Bundle bundle;
    String irA;



    DbHelper db;
    public static  String nombreusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bundle = getIntent().getExtras();
        irA = bundle.getString("ir");



        usuarioinicio = findViewById(R.id.inicio_usuario);
        contrasenainicio = findViewById(R.id.inicio_pass);
        retrocederprincipal = findViewById(R.id.page_principal);
        registrarinicio = findViewById(R.id.registroiniciosesion);
        registrarse2 = findViewById(R.id.registrarse2);
        continuar = findViewById(R.id.button_continuar);
        registrarse2.setOnClickListener(view -> registro());
        registrarinicio.setOnClickListener(view -> registroTwo());
        retrocederprincipal.setOnClickListener(view -> retro());
        continuar.setOnClickListener(view -> ingresar());

    }


    public void ingresar(){

        db = new DbHelper(this);
        String usuario= usuarioinicio.getText().toString();
        String contrasena= contrasenainicio.getText().toString();
        if (usuario.equals("") || contrasena.equals("")) {
            Toast.makeText(this, "Llene los campos para poder continuar", Toast.LENGTH_SHORT).show();
        }else{
            Boolean validarExistencia = db.validarRegistros(usuario,contrasena);
            if (validarExistencia==true){
                nombreusuario=usuario;
                pasarIntent();

            }else{
                Toast.makeText(this, "Usuario o contraseña no validas, intente de nuevo", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void pasarIntent() {

        if (irA.equals("tran")){
            startActivity(new Intent(this, Transferir.class));
            usuarioinicio.setText("");
            contrasenainicio.setText("");
            finish();
        } else if (irA.equals("mov")){
            startActivity(new Intent(this, Movimientos.class));
            usuarioinicio.setText("");
            contrasenainicio.setText("");
            finish();
        }else if (irA.equals("per")) {
            startActivity(new Intent(this, Perfil.class));
            usuarioinicio.setText("");
            contrasenainicio.setText("");
            finish();
        }else if (irA.equals("nor")) {
            startActivity(new Intent(this, UsuariosMain.class));
            usuarioinicio.setText("");
            contrasenainicio.setText("");
            finish();
        }
    }


    private void retro() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void registroTwo() {
        Intent intent = new Intent(this,PersonalData.class);
        startActivity(intent);
        finish();
    }

    private void registro() {
        Intent intent = new Intent(this,PersonalData.class);
        startActivity(intent);
        finish();
    }
}