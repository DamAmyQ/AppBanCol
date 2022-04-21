package com.example.bancolombia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button registrarse;
    Button ingresar;
    Button ayuda;
    TextView fecha;
    Button perfild;
    Button enviard;
    Button movimientosd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        enviard = findViewById(R.id.enviardinero);
        perfild = findViewById(R.id.perusuario);
        movimientosd = findViewById(R.id.saldos_y_movimientos);

        fecha = findViewById(R.id.fecha_hoy);
        registrarse = findViewById(R.id.registrarse);
        ingresar = findViewById(R.id.ingresar);
        ayuda = findViewById(R.id.ayuda);

        registrarse.setOnClickListener(view -> {
            registro();
        });

        ingresar.setOnClickListener(view -> {
            ingreso();
        });

        perfild.setOnClickListener(view -> {
            enviaralperfil();
        });
        movimientosd.setOnClickListener(view -> {
            enviaramovimientos();
        });
        enviard.setOnClickListener(view -> {
           enviaratransferir();
        });

    }

    private void enviaratransferir() {
        Intent intent = new Intent(this, InicioSesion.class);
        String irdonde="tran";
        intent.putExtra("ir",irdonde);
        startActivity(intent);
        finish();
    }

    private void enviaramovimientos() {
        Intent intent = new Intent(this, InicioSesion.class);
        String irdonde="mov";
        intent.putExtra("ir",irdonde);
        startActivity(intent);
        finish();

    }

    private void enviaralperfil() {
        Intent intent = new Intent(this, InicioSesion.class);
        String irdonde="per";
        intent.putExtra("ir",irdonde);
        startActivity(intent);
        finish();

    }


    private void ingreso() {
        Intent intent = new Intent(this, InicioSesion.class);
        String irdonde="nor";
        intent.putExtra("ir",irdonde);
        startActivity(intent);
        finish();
    }

    private void registro() {
        startActivity(new Intent(this, PersonalData.class));
        finish();
    }


}