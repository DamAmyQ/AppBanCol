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

public class UsuariosMain extends AppCompatActivity {

    Button transmoney;
    Button movimientos;
    Button datosPersonales;
    Button salir;
    TextView hola;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        transmoney = findViewById(R.id.enviar_d);
        movimientos = findViewById(R.id.movements);
        datosPersonales = findViewById(R.id.datos_personales);
        salir = findViewById(R.id.salir);
        hola = findViewById(R.id.Hola);

        salir.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT nombre FROM usuarios WHERE usuario='" + InicioSesion.nombreusuario + "'", null);
        if (c != null) {
            c.moveToFirst();
            do {

                hola.setText("¡Hola "+c.getString(0)+"! ");
            } while (c.moveToNext());
        }

        transmoney.setOnClickListener(view -> {
           enviarMoney();
        });

        movimientos.setOnClickListener(view -> {
            saldosMovimients();
        });
        datosPersonales.setOnClickListener(view -> {
            datosPerfil();
        });
    }

    private void datosPerfil() {
        startActivity(new Intent(this, Perfil.class));
        finish();
    }

    private void saldosMovimients() {
        startActivity(new Intent(this, Movimientos.class));
        finish();
    }

    private void enviarMoney() {
        startActivity(new Intent(this, Transferir.class));
        finish();
    }
}