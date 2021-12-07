package com.example.evaluacion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistroUsuario extends AppCompatActivity {

    private TextView correo, contraseña;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        correo = findViewById(R.id.inputGmail);
        contraseña = findViewById(R.id.inputContraseña);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioActual = mAuth.getCurrentUser();
    }

    public void crearCuenta(View view){

        if (correo.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()){
            camposVacios();
        }
        else if (correo.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty()){
            camposVacios();
        }
        else{
            mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser usuarioNuevo = mAuth.getCurrentUser();
                        mensajeExito(usuarioNuevo.getUid());
                        Intent intent = new Intent(RegistroUsuario.this, DatosUsuario.class);
                        startActivity(intent);
                    }
                    else{
                        mensajeError();
                    }
                }
            });
        }
    }

    public void retornoInicioSesion(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void mensajeExito(String idUsuario){
        Toast.makeText(this,"¡Registro exitoso! Se ha iniciado sesión automáticamente", Toast.LENGTH_SHORT).show();
    }

    private void mensajeError(){
        Toast.makeText(this,"Ups... Algo salió mal, vuelve a intentar", Toast.LENGTH_SHORT).show();
    }

    private void camposVacios(){
        Toast.makeText(this,"No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
    }
}