package com.example.evaluacion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatosPersonales extends AppCompatActivity {

    TextView nombree, generoo, nacimientoo;

    FirebaseAuth mAuth;
    FirebaseUser usuarioActual;

    FirebaseDatabase dataBase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        nombree = findViewById(R.id.nombreText);
        generoo = findViewById(R.id.generoText);
        nacimientoo = findViewById(R.id.nacimientoText);

        mAuth = FirebaseAuth.getInstance();
        usuarioActual = mAuth.getCurrentUser();

        dataBase = FirebaseDatabase.getInstance();
        reference = dataBase.getReference();

        String id = mAuth.getCurrentUser().getUid();
        reference.child("Usuario").child(id).child("Datos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String genero = snapshot.child("genero").getValue().toString();
                    String nacimiento = snapshot.child("nacimiento").getValue().toString();

                    nombree.setText(nombre);
                    generoo.setText(genero);
                    nacimientoo.setText(nacimiento);
                }
                else {
                    Toast.makeText(DatosPersonales.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DatosPersonales.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void volver(View view){
        Intent intent = new Intent(this, crearNoticia.class);
        startActivity(intent);
    }

    public void modificar(View view){
        Intent intent = new Intent(this, modificar.class);
        startActivity(intent);
    }
}