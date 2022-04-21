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

public class Perfil extends AppCompatActivity {
    private TextView correo;
    private TextView numId;
    private TextView telefono;
    private TextView nombre;
    Button volinicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        correo = findViewById(R.id.correo);
        numId= findViewById(R.id.identidad);
        telefono = findViewById(R.id.telefono);
        nombre = findViewById(R.id.nombre);
        volinicio = findViewById(R.id.volvinicior);

        volinicio.setOnClickListener(view -> {
 startActivity(new Intent(this, UsuariosMain.class));
 finish();
        });


        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT correo,numerodocumento,nombre,telefono FROM usuarios WHERE usuario='" + InicioSesion.nombreusuario + "'", null);
        if (c != null) {
            c.moveToFirst();
            do {
                correo.setText(" "+" "+c.getString(0));
                numId.setText(c.getString(1));
                nombre.setText(c.getString(2));
                telefono.setText(c.getString(3));
            } while (c.moveToNext());
        }
    }


}