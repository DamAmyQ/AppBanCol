package com.example.bancolombia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.BoringLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonalData3 extends AppCompatActivity {
    EditText usuario_create;
    EditText claveInt;
    EditText claveConfirm;

    DbHelper db;
    PersonalData3 validar3;

    public String dataString1;
    public String nameString2;
    public String numIdString;
    public String intEmails;

    public String telS;



    public String passConfirmCreate;
    public String passCreate;
    String userCreate;

    Button bs3n1;
    Button bs3n2;
    Button bs3p3;

    Bundle bundle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data3);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        claveConfirm = findViewById(R.id.validarpassword);
        claveInt = findViewById(R.id.crearpassword);
        usuario_create = findViewById(R.id.create_user);
        bundle = getIntent().getExtras();

        dataString1 = bundle.getString("fechab");
        numIdString = bundle.getString("identidadb");
        nameString2 = bundle.getString("nombreb");
        intEmails = bundle.getString("emailb");
        telS = bundle.getString("telb");




        validar3 = new PersonalData3();


        bs3n1 =findViewById(R.id.button3Next1);
        bs3n2 =findViewById(R.id.button3Next2);
        bs3p3 =findViewById(R.id.button3Prev);

        bs3n1.setOnClickListener(view -> {
            registrarUsuario();
        });

        bs3n2.setOnClickListener(view -> {
            registrarUsuario();
        });

        bs3p3.setOnClickListener(view -> {
            retrocederslidetwo();
        });

    }

    private void retrocederslidetwo() {
        startActivity(new Intent(this, PersonalData.class));
    }

    public void registrarUsuario() {
        db = new DbHelper(this);

        passConfirmCreate = claveConfirm.getText().toString();
        passCreate = claveInt.getText().toString();
        userCreate = usuario_create.getText().toString();




        if (passConfirmCreate.isEmpty() || passCreate.isEmpty() || userCreate.trim().isEmpty()) {
            Toast.makeText(this, "Llene todos los campos para poder continuar",
                    Toast.LENGTH_SHORT).show();

        } else {
            if (dataString1.isEmpty() || numIdString.isEmpty() || nameString2.isEmpty()|| telS.isEmpty()|| intEmails.isEmpty()){
                Toast.makeText(this, "faltan datos",
                        Toast.LENGTH_SHORT).show();
            }else{
            if (contieneSoloLetras(userCreate) == false || userCreate.length() <= 8) {
                Toast.makeText(this, "No se admiten caracteres vacios",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (passCreate.equals(passConfirmCreate) == false) {
                    Toast.makeText(this, "las contraseñas no coinciden",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (passCreate.length() <= 7) {
                        Toast.makeText(this, "la contraseña es muy devil",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (validarContraseña(passCreate) == false) {
                            Toast.makeText(this, "Debe contener numeros y letras",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Boolean validarUsuario = db.verificarUsuario(userCreate);
                            if (validarUsuario == false) {
                                Boolean insertarUsuario = db.registrar(dataString1, numIdString, nameString2,
                                        intEmails, telS, userCreate, passCreate);
                                if (insertarUsuario == true) {
                                    Intent intent = new Intent(this, InicioSesion.class);
                                    String irdonde="nor";
                                    intent.putExtra("ir",irdonde);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(this, "No se pudo realizar el registro", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(this, "usuario ya existe", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                        }
                    }
                }
            }
        }

    private boolean contieneSoloLetras(String userCreate) {
        for (int x = 0; x < userCreate.length(); x++) {
            char c = userCreate.charAt(x);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z' )|| (c == 'ñ') || (c == 'Ñ'))) {
                return false;
            }
        }
        return true;
    }

    private boolean validarContraseña(String passCreate) {
        boolean letras = false;
        boolean numeros = false;
        for (int x = 0; x < passCreate.length(); x++) {
            char c = passCreate.charAt(x);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z' )|| (c == 'ñ') || (c == 'Ñ')) {
                letras = true;
            }
            if (c >= '0' && c <= '9') {
                numeros = true;
            }
        }
        if (numeros == true && letras == true) {
            return true;
        }
        return false;
    }


}