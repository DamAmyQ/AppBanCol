package com.example.bancolombia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;



public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "banahorro.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table usuarios(usuario text primary key," +
                "fechanacimiento text,numerodocumento text,correo text," +
                "nombre text,telefono text,contrasena text,balance text)");
        db.execSQL("create table recarga(cod integer primary key autoincrement, " +
                "operador text ,telefono text,valor text,saldo text,hora text, " +
                "usuarioUsuario text references usuarios(usuario) on delete cascade on update cascade)");
        db.execSQL("insert into usuarios (usuario,fechanacimiento,numerodocumento," +
                "correo,nombre,telefono,contrasena,balance) values ('Yon','10-10-10'," +
                "'1234567895','yon@gmail.com','yon seun','3008026000','mmmpoawo123','1000000')");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS recarga");
        onCreate(db);
    }
    public boolean registrar(String fechanacimiento, String numerodocumento,
                             String nombre, String correo, String telefono,
                             String usuario, String contrasena ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("fechanacimiento",fechanacimiento);
        registro.put("numerodocumento", numerodocumento);
        registro.put("nombre", nombre);
        registro.put("correo", correo);
        registro.put("telefono", telefono);
        registro.put("usuario", usuario);
        registro.put("contrasena", contrasena);
        registro.put("balance", "1000000");

        Long results = db.insert("usuarios", null, registro);

        if (results==-1){
            return false;
        }else
            return true;
    }

    public boolean registrarRecargas(String operador,String telefono,
                                     String valor,String saldo,String hora,
                                     String usuarioUsuario)  {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registrorecarga = new ContentValues();
        registrorecarga.put("operador", operador);
        registrorecarga.put("telefono", telefono);
        registrorecarga.put("valor", valor);
        registrorecarga.put("saldo",saldo);
        registrorecarga.put("hora",hora);
        registrorecarga.put("usuarioUsuario", usuarioUsuario);

        Long resultado2 = db.insert("recarga", null, registrorecarga);
        if (resultado2==-1){
            return false;
        }else{
            return true;
        }
    }
    public boolean actualizarSaldo(String balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newupdate = new ContentValues();
        newupdate.put("balance", balance);
        try {
            db.execSQL("UPDATE usuarios SET balance='" + balance + "' WHERE usuario= '" + InicioSesion.nombreusuario + "'");
            return true;
        }catch (Exception e){
            e.toString();
            return false;
        }
    }


    public boolean verificarUsuario(String usuario) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios where usuario=?", new String[]{usuario});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


        public boolean verificarIdentidad(String numerodocumento){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from usuarios where numerodocumento=?",new String[]{numerodocumento});
            if (cursor.getCount()>0){
                return true;
            }else{
                return false;
            }
    }


    public boolean verificarTel(String telefono){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios where telefono=?",new String[]{telefono});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }


    public boolean verificarCorreo(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios where correo=?",new String[]{correo});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }



    public boolean validarRegistros(String usuario, String contrasena){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios where usuario=? and contrasena=?",
                new String[]{usuario,contrasena});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }




}