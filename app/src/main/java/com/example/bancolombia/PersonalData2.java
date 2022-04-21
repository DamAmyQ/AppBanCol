package com.example.bancolombia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalData2 extends AppCompatActivity {

    EditText intemail;
    EditText confirmemail;
    EditText confirmtel;
    EditText telefono;

    String email;
    String email2;
    String tel;
    String tel2;

    Button bs2n1;
    Button bs2n2;
    Button bs2p3;

    DbHelper db;
    PersonalData2 verificar2;

    Bundle bundelpersonal;

    TextView dato1;
    TextView dato2;
    TextView dato3;

    String fecha;
    String nombre;
    String idnumero;


    String fecha1;
    String name1;
    String identidad1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data2);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        intemail = findViewById(R.id.intemail);
        confirmemail = findViewById(R.id.email_confirm);
        confirmtel = findViewById(R.id.confirm_tel);
        telefono = findViewById(R.id.tel);
        bs2n1 =findViewById(R.id.button2Next1);
        bs2n2 =findViewById(R.id.button2Next2);
        bs2p3 =findViewById(R.id.button2Prev);
        dato1 =findViewById(R.id.prueba1);
        dato2 =findViewById(R.id.prueba2);
        dato3 =findViewById(R.id.prueba3);
        bundelpersonal = getIntent().getExtras();
        fecha = bundelpersonal.getString("fecha");
        nombre = bundelpersonal.getString("nombre");
        idnumero = bundelpersonal.getString("identidad");


        dato1.setText(fecha);
        dato2.setText(nombre);
        dato3.setText(idnumero);

        verificar2 = new PersonalData2();



        bs2n1.setOnClickListener(view -> {
            enviardatosslide3();
        });

        bs2n2.setOnClickListener(view -> {
            enviardatosslide3();
        });

        bs2p3.setOnClickListener(view -> {
           retrocederslideone();
        });
    }

    private void retrocederslideone() {
        startActivity(new Intent(this, PersonalData.class));
    }

    private void enviardatosslide3() {
        db = new DbHelper(this);

        email = intemail.getText().toString();
        email2 = confirmemail.getText().toString();
        tel = telefono.getText().toString();
        tel2 = confirmtel.getText().toString();


        fecha1 = dato1.getText().toString();
        name1 = dato2.getText().toString();
        identidad1 = dato3.getText().toString();


        if (email.isEmpty() || email2.isEmpty() || tel.isEmpty() || tel2.isEmpty()) {
            Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
        }else {
            if (validarCorreo(email) == false) {
                Toast.makeText(this, "El correo "+email+"no es valido ",
                        Toast.LENGTH_SHORT).show();
            }else {
                if (validarCorreo2(email) == false){
                    Toast.makeText(this, "Use un dominio de correo distinto ",
                            Toast.LENGTH_SHORT).show();
                }else{
                if (email.equals(email2) == false){
                    Toast.makeText(this, "los correos no son iguales",
                            Toast.LENGTH_SHORT).show();
                }else{
                    if (tel.charAt(0) != '3' || tel.equals(tel2) == false || tel.length()<9) {
                        Toast.makeText(this, "El numero debe ser Colombiano y debe coincidir con el " +
                                        " campo de confirmación",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Boolean validarTelefono= db.verificarTel(tel);
                        if (validarTelefono == true) {
                            Toast.makeText(this, "El telefono ya existe en el sistema",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Boolean validarEmail = db.verificarCorreo(email);
                            if (validarEmail == false) {

                                Intent datos2 = new Intent(this, PersonalData3.class);
                                datos2.putExtra("telb", tel);
                                datos2.putExtra("emailb", email);
                                datos2.putExtra("fechab", fecha1);
                                datos2.putExtra("nombreb", name1);
                                datos2.putExtra("identidadb", identidad1);


                                startActivity(datos2);
                                finish();

                            } else {
                                Toast.makeText(this, "El correo ya existe en el sistema",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        }
                    }
                }
            }
        }
    }

    private boolean validarCorreo2(String email) {
        String[] parts = email.split("@");
        String part1 = parts[0];
        String part2 = parts[1];

        if (part2.equals("gmail.com") || part2.equals("hotmail.com")|| part2.equals("outlook.com")){
            return true;
        }
        return false;
    }

    private boolean validarCorreo(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        if (mather.find()) {
            return  true;
        }
        return false;
    }
}