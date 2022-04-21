package com.example.bancolombia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class PersonalData extends AppCompatActivity {

private DatePickerDialog datePickerDialog;


    EditText etBirthday;
    EditText nameUser;
    EditText numId;
    Button bsn1;
    Button bsn2;
    Button bsp3;
    String fechaN;
    String name;
    String idN;
    DbHelper db;
    PersonalData validar1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initDatePicker();

        etBirthday = findViewById(R.id.etPlannetDate1);
        nameUser = findViewById(R.id.etPlannetDate2);
        numId = findViewById(R.id.numId);
        bsn1 = findViewById(R.id.button1Next1);
        bsn2 = findViewById(R.id.button1Next2);
        bsp3 = findViewById(R.id.button1Prev);
        validar1 = new PersonalData();

        bsn1.setOnClickListener(view -> {
            enviardatosslide1();

        });

        bsn2.setOnClickListener(view -> {
            enviardatosslide1();


        });

        bsp3.setOnClickListener(view -> {
            retrocederiniciosecion();

        });

        etBirthday.setText(getTodayDater());
        etBirthday.setOnClickListener(view -> {

               openDatePicker();
        });


    }

    private String getTodayDater() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return MakeDateStrink(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener= (datePicker, year, month, day) -> {
            month = month + 1;
            String date = MakeDateStrink(day, month, year);
            etBirthday.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String MakeDateStrink(int day, int month, int year) {
        return  getMonthFormat(month)+ " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1){
            return "Enero";
        }
        if (month == 2){
            return "Febrero";
        }
        if (month == 3){
            return "Marzo";
        }
        if (month == 4){
            return "Abril";
        }
        if (month == 5){
            return "Mayo";
        }
        if (month == 6){
            return "Junio";
        }
        if (month == 7){
            return "Julio";
        }
        if (month == 8){
            return "Agosto";
        }
        if (month == 9){
            return "Septiembre";
        }
        if (month == 10){
            return "Octubre";
        }
        if (month == 11){
            return "Noviembre";
        }
        if (month == 12){
            return "Diciembre";
        }
        return "Enero";
    }

    public void openDatePicker(){
        datePickerDialog.show();
    }

    private void retrocederiniciosecion() {
        Intent intent = new Intent(this, InicioSesion.class);
        String irdonde="nor";
        intent.putExtra("ir",irdonde);
        startActivity(intent);
        finish();

    }


    private void enviardatosslide1() {

        db = new DbHelper(this);
        fechaN = etBirthday.getText().toString();
        name = nameUser.getText().toString();
        idN = numId.getText().toString();


        if ((fechaN.isEmpty() || name.trim().isEmpty() || idN.isEmpty())) {
            Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
        } else {
            if (validarFecha(fechaN) == false ) {
                Toast.makeText(this, "Debe ser mayor de edad para poder registrarse ", Toast.LENGTH_SHORT).show();
            } else {
                if (validarNombre(name) == false) {
                    Toast.makeText(this, "Nombre no valido",
                            Toast.LENGTH_SHORT).show();
                }else {
                        if (idN.length() < 9) {
                            Toast.makeText(this, "el documento no es valido",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Boolean validarIdn= db.verificarIdentidad(idN);
                            if (validarIdn == false) {

                                Intent datos = new Intent(this,PersonalData2.class);
                                datos.putExtra("fecha", fechaN);
                                datos.putExtra("nombre", name);
                                datos.putExtra("identidad", idN);
                                startActivity(datos);
                                finish();

                            } else {



                                Toast toast= Toast.makeText(this, "el documento ya esta registrado",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                }
            }
        }


    private boolean validarFecha(String fechaN) {
        String[] parts = fechaN.split(" ");
        String part1 = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];
        int intpart3 = Integer.parseInt(part3);
        if (intpart3 > 2004){
            return false;
        }
        return true;
    }

    private boolean validarNombre(String name) {
        for (int x = 0; x < name.length(); x++) {
            char c = name.charAt(x);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z' || (c == 'ñ') || (c == 'Ñ')) || (c == ' '))) {
                return false;
            }
        }
        return true;
    }
}