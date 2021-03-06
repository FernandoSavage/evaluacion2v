package com.example.evaluacion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView correo, contraseña;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.gmail);
        contraseña = findViewById(R.id.contraseña);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioActual = mAuth.getCurrentUser();

        if (usuarioActual != null){
            Intent intent = new Intent(MainActivity.this, crearNoticia.class);
            startActivity(intent);
        }
    }

    public void iniciarSesion(View view){

        if (correo.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()){
            camposVacios();
        }
        else if (correo.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty()){
            camposVacios();
        }
        else{
            mAuth.signInWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();

                        Toast.makeText(MainActivity.this, "¡Inicio de sesión exitosa!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, crearNoticia.class);
                        startActivity(intent);

                        correo.setText("");
                        contraseña.setText("");
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Las credenciales ingresadas son incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void registrarse(View view){
        correo.setText("");
        contraseña.setText("");
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }

    public void camposVacios(){
        Toast.makeText(MainActivity.this, "No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
    }


}