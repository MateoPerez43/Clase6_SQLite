package com.example.clase6_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nombre,cedula,telefono,contrasena;
    Button guardar,consultar,limpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guardar = (Button) findViewById(R.id.guardar);
        consultar = (Button) findViewById(R.id.consultar);
        limpiar = (Button) findViewById(R.id.limpiar);

        nombre = (EditText) findViewById(R.id.nombreUsuario);
        cedula = (EditText) findViewById(R.id.cedula);
        telefono = (EditText) findViewById(R.id.telefono);
        contrasena = (EditText) findViewById(R.id.contrasena);

    }

    @Override
    protected void onResume() {
        super.onResume();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cedula.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty() &&!contrasena.getText().toString().isEmpty() && !telefono.getText().toString().isEmpty()) {
                    registrar();
                }
                else {
                    Toast.makeText(MainActivity.this,"Datos invalidos o incompletos",Toast.LENGTH_LONG).show();
                }
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cedula.getText().toString().isEmpty()) {
                    consultar();
                }
                else {
                    Toast.makeText(MainActivity.this,"Ingrese valor a consultar",Toast.LENGTH_LONG).show();
                }
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });
    }

    public void registrar(){
        AdminDB admin = new AdminDB(this,"dataBase",null,1);
        //Abrir base de datos(Instancias base de datos)
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        int document = Integer.parseInt(cedula.getText().toString());
        String fullName = nombre.getText().toString();
        int phone = Integer.parseInt(telefono.getText().toString());
        String password = contrasena.getText().toString();

        if (!cedula.getText().toString().isEmpty()){
            Cursor fila= baseDatos.rawQuery("select cedula,nombreusuario,telefono,contrasena from usuario where cedula="+document,null);
            if(fila.moveToFirst()){
                String idPersona = fila.getString(0);
                if (!idPersona.isEmpty()){
                    Toast.makeText(this,"El Registro ya existe",Toast.LENGTH_LONG).show();
                }
        } else
            if(!(String.valueOf(document).isEmpty()) && !(String.valueOf(phone).isEmpty()) &&!fullName.isEmpty() && !password.isEmpty()){

                ContentValues registro = new ContentValues();
                registro.put("cedula",document);
                registro.put("nombreusuario",fullName);
                registro.put("telefono",phone);
                registro.put("contrasena",password);
                baseDatos.insert("usuario",null,registro);
                baseDatos.close();
                cedula.setText("");
                nombre.setText("");
                telefono.setText("");
                contrasena.setText("");

                Toast.makeText(this,"Registro guardado exitosamente",Toast.LENGTH_LONG).show();
            }
            else   {
                Toast.makeText(MainActivity.this,"Datos invalidos o incompletos",Toast.LENGTH_LONG).show();

            }
        }
    }

    public void consultar(){
        AdminDB admin = new AdminDB(this,"dataBase",null,1);
        //Abrir base de datos(Instancias base de datos)
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        int document = Integer.parseInt(cedula.getText().toString());

        if (!cedula.getText().toString().isEmpty()){
            Cursor fila= baseDatos.rawQuery("select cedula,nombreusuario,telefono,contrasena from usuario where cedula="+document,null);
            if(fila.moveToFirst()){
                cedula.setText(fila.getString(0));
                nombre.setText(fila.getString(1));
                telefono.setText(fila.getString(2));
                contrasena.setText(fila.getString(3));
                baseDatos.close();

                Toast.makeText(this,"Consulta exitosa",Toast.LENGTH_LONG).show();
            }
            else   {
                Toast.makeText(MainActivity.this,"Ingrese valor a consultar",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void limpiar(){
        cedula.setText("");
        nombre.setText("");
        telefono.setText("");
        contrasena.setText("");
    }
}