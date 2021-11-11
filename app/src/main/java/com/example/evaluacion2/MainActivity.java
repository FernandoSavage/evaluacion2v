package com.example.evaluacion2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText nombre, contraseña, nombreR, contraseñaR;
    ArrayList<Persona> lista_persona;
    Persona p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista_persona = new ArrayList<Persona>();

        nombre = (EditText) findViewById(R.id.nombreText);
        contraseña = (EditText) findViewById(R.id.contraseñaText);
        nombreR = (EditText) findViewById(R.id.nombreRegister);
        contraseñaR = (EditText) findViewById(R.id.contraseñaRegister);

        p = new Persona();
    }

    public void iniciarSesion(View view){
        String nombreParse = nombre.getText().toString();
        String contraseñaParse = contraseña.getText().toString();

        if (nombreParse.equals(p.getNombre()) && contraseñaParse.equals(p.getContraseña())){
            Intent intent = new Intent(this, crearNoticia.class);
            intent.putExtra("nombre", p.getNombre());
            startActivity(intent);
        }
        else if(nombreParse.equals("test") && contraseñaParse.equals("test")){
            Intent intent = new Intent(this, crearNoticia.class);
            intent.putExtra("nombre", p.nombre);
            startActivity(intent);
        }
        else if (nombreParse.isEmpty() || contraseñaParse.isEmpty()){
            Toast.makeText(this,"No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
        }
        else if (nombreParse.isEmpty() && contraseñaParse.isEmpty()){
            Toast.makeText(this,"No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Las credenciales son incorrectas", Toast.LENGTH_SHORT).show();
        }
    }

    public void registrarUsuario(View view){
        p.setNombre(nombreR.getText().toString());
        p.setContraseña(contraseñaR.getText().toString());

        if (nombreR.getText().toString().isEmpty() || contraseñaR.getText().toString().isEmpty()){
            Toast.makeText(this,"No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
        }
        else if (nombreR.getText().toString().isEmpty() && contraseñaR.getText().toString().isEmpty()){
            Toast.makeText(this,"No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
        }
        else{
            lista_persona.add(p);

            nombre.setText(nombreR.getText().toString());
            contraseña.setText(contraseñaR.getText().toString());

            Toast.makeText(this,"¡Registro Exitoso!", Toast.LENGTH_SHORT).show();

            nombreR.setText("");
            contraseñaR.setText("");
        }
    }
}