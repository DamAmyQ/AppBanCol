package com.example.bancolombia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Movimientos extends AppCompatActivity {
    ListView lista;
    Button volver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        lista = findViewById(R.id.lista);
        volver = findViewById(R.id.volveruser);
        ArrayList<String> items = new ArrayList<>();
        volver.setOnClickListener(view -> {
            startActivity(new Intent(this, UsuariosMain.class));
            finish();
        });

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        Cursor fila= db.rawQuery("select hora,operador,telefono,valor from recarga where usuarioUsuario='" + InicioSesion.nombreusuario + "'",null);
        if(fila.moveToFirst()){
            do{
                items.add("\n"+fila.getString(0)+"\n \n" +
                        "Telefono: "+fila.getString(2)+"\n \n"+
                        "Telefonica: "+fila.getString(1)+"\n \n"+
                        "Valor transferido: $ "+fila.getString(3)+"\n \n"+
                        "Comisión: $500 "+"\n");
            }while (fila.moveToNext());
            Collections.reverse(items);
        }

        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lista.setAdapter(adaptador);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                final int posicion=i;
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Movimientos.this);
                dialogo1.setTitle("¡Advertencia!");
                dialogo1.setMessage("¿Deseas eliminar este fragmento del historial de transferencia?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        deleteItemDB();
                        Toast.makeText(Movimientos.this, "Fragmento eliminado", Toast.LENGTH_SHORT).show();
                        items.remove(i);
                        adaptador.notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                return false;
            }
        });
    }

    public void deleteItemDB(){
        DbHelper admindbHelper = new DbHelper(this);
        SQLiteDatabase db = admindbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM recarga WHERE usuarioUsuario='" + InicioSesion.nombreusuario + "'");
        db.close();


    }
}