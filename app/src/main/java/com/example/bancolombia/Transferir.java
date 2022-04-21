package com.example.bancolombia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Transferir extends AppCompatActivity {
    private EditText insertnum;
    private EditText insertvalor;
    private TextView saldoactual;
    private Button transferir;
    private Button historial;
    private Button volver;
    private Spinner spinnertel;
    public TextView spinn;
    DbHelper db;
    private String retorno;
    String registrarO;

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferir);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        db = new DbHelper(this);

        spinnertel = findViewById(R.id.spinner);
        saldoactual = findViewById(R.id.saldoactual);
        insertnum = findViewById(R.id.numero_tel);
        insertvalor = findViewById(R.id.valor_transferencia);
        transferir = findViewById(R.id.button_transfer);
        volver = findViewById(R.id.volvmain);
        historial = findViewById(R.id.historial);
        spinn = findViewById(R.id.spin);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        Timer timer = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                saldoactual.setText("1000000");
            }
        };

        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 00);
        date.set(Calendar.MINUTE, 00);
        date.set(Calendar.SECOND, 00);
        timer.schedule(tarea, date.getTime());

        if (obtenerSaldo() == false) {
            saldoactual.setText("1000000");
        } else {
            String resultado = consultarSaldo();
            saldoactual.setText(resultado);
        }
        String[] operadorasMoviles = {"Seleccione un operador de telefonia", "Movistar", "Wom",
                "Virgin Mobile", "Claro", "TigoUne", "Movil Exito", "Avantel", "Flash Mobile"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.spinner, operadorasMoviles);
        spinnertel.setAdapter(adapter);
        spinnertel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                    setRetorno(parent.getItemAtPosition(i).toString());
                    registrarO = getRetorno();
                    spinn.setText(registrarO);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        volver.setOnClickListener(view -> {
                startActivity(new Intent(this, UsuariosMain.class));
                finish();
        });
        historial.setOnClickListener(view -> {
            startActivity(new Intent(this, Movimientos.class));
            finish();
        });

        transferir.setOnClickListener(view -> {

            String registrarOperador = spinn.getText().toString();
            String registrarTelefono = insertnum.getText().toString();
            String registrarDinero = insertvalor.getText().toString();
            String nombreUser = InicioSesion.nombreusuario;


            if (registrarTelefono.equals("") || registrarDinero.equals("")) {
                Toast.makeText(Transferir.this, "Llene los campos", Toast.LENGTH_SHORT).show();
            } else {
                if (registrarOperador.equals("Seleccione un operador de telefonia")) {
                    Toast.makeText(Transferir.this, "Seleccione un operador de telefonia", Toast.LENGTH_SHORT).show();
                } else {
                    if (registrarTelefono.length() == 10 || registrarTelefono.charAt(0) == '3') {
                        double saldo = Double.parseDouble(saldoactual.getText().toString());
                        saldo = saldo - (Double.parseDouble(insertvalor.getText().toString()))-500;
                        String saldo2 = String.valueOf(saldo);
                        if (saldo >= 0) {
                            Boolean insertarRecarga = db.registrarRecargas(registrarOperador,
                                    registrarTelefono, registrarDinero, saldo2, currentDateTimeString,
                                    nombreUser);
                            Boolean updateuser = db.actualizarSaldo(saldo2);
                            saldoactual.setText(saldo2);
                            if (insertarRecarga == true && updateuser ==true) {
                                Toast.makeText(Transferir.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                insertnum.setText("");
                                insertvalor.setText("");
                            } else {
                                Toast.makeText(Transferir.this, "No se pudo realizar la Recarga", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Transferir.this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Transferir.this, "Ingrese un numero de telefono valido",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

    }

    public boolean obtenerSaldo() {
        DbHelper admin = new DbHelper(getApplicationContext());
        String code = "";
        SQLiteDatabase sqLiteDatabase = admin.getWritableDatabase();
        Cursor fila = sqLiteDatabase.rawQuery(
                "select saldo from recarga where usuarioUsuario='" + InicioSesion.nombreusuario + "'", null);

        if (fila.moveToFirst()) {
            do {
                code = fila.getString(0);

            } while (fila.moveToNext());
        }
        if (code.equals("")) {
            return false;
        } else
            return true;
    }

    public String consultarSaldo() {
        DbHelper adminconsultar = new DbHelper(getApplicationContext());
        String code = "";
        SQLiteDatabase sqLiteDatabase = adminconsultar.getWritableDatabase();
        Cursor fila = sqLiteDatabase.rawQuery(
                "select saldo from recarga where usuarioUsuario='" + InicioSesion.nombreusuario + "'", null);
        if (fila.moveToFirst()) {
            do {
                code = fila.getString(0);

            } while (fila.moveToNext());
        }
        return code;
    }
}