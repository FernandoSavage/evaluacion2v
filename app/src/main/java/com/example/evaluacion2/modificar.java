package com.example.evaluacion2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class modificar extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Usuario> usuarios;
    TextView nombre, nacimiento;
    CheckBox femeninoo, masculinoo;
    Button bcalendario;

    FirebaseAuth mAuth;
    FirebaseUser usuarioActual;

    FirebaseDatabase dataBase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        nombre = findViewById(R.id.modificarNombre);
        nacimiento = findViewById(R.id.modificarFecha);
        femeninoo = findViewById(R.id.idfemenino);
        masculinoo = findViewById(R.id.idmasculino);
        bcalendario = findViewById(R.id.modificarCalendario);

        bcalendario.setOnClickListener(this);

        usuarios = new ArrayList<Usuario>();

        mAuth = FirebaseAuth.getInstance();
        usuarioActual = mAuth.getCurrentUser();

        dataBase = FirebaseDatabase.getInstance();
        reference = dataBase.getReference();
    }

    public void modificacion(View view){
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre.getText().toString());
        usuario.setNacimiento(nacimiento.getText().toString());

        if(femeninoo.isChecked()){
            usuario.setGenero("Femenino");
        }
        if(masculinoo.isChecked()){
            usuario.setGenero("Masculino");
        }
        if(masculinoo.isChecked() == false && femeninoo.isChecked() == false){
            usuario.setGenero("No especifica/omite");
        }

        if(nombre.getText().toString().isEmpty() && nacimiento.getText().toString().isEmpty()){
            avisos();
        }
        else if(nombre.getText().toString().isEmpty() || nacimiento.getText().toString().isEmpty()){
            avisos();
        }
        else if(femeninoo.isChecked() == true &&masculinoo.isChecked() == true) {
            aviso2();
        }
        else{
            reference.child("Usuario").child(usuarioActual.getUid()).child("Datos").setValue(usuario);
            Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DatosPersonales.class);
            startActivity(intent);
        }
    }

    public void avisos(){
        Toast.makeText(this, "No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
    }

    public void aviso2(){
        Toast.makeText(this, "Debe elegir solo una opción", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Calendar c = Calendar.getInstance();
        int año = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(modificar.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String fecha = i2 + "/" + (i1 + 1) + "/" + i;
                nacimiento.setText(fecha);
            }
        }, año, mes, dia);
        datePickerDialog.show();
    }
}