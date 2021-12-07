package com.example.evaluacion2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class crearNoticia extends AppCompatActivity {

    ArrayList<Noticia> noticias;

    TextView titulo, descripcion, saludoo;

    ImageView foto;

    Bitmap bitmap;

    CheckBox aricaq, iquiqueq, coquimboq, valparaisoq, concepcionq, puntaarenasq, santiagoq;

    boolean hayPermiso;

    FirebaseAuth mAuth;
    FirebaseUser usuarioActual;
    FirebaseDatabase dataBase;
    DatabaseReference reference;

    String[] permisos = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_noticia);

        hayPermiso = false;
        bitmap = null;
        titulo = findViewById(R.id.tituloInput);
        descripcion = findViewById(R.id.descripcionInput);
        foto = findViewById(R.id.fotoInput);
        aricaq = findViewById(R.id.aricaCheck);
        iquiqueq = findViewById(R.id.iquiqueCheck);
        coquimboq = findViewById(R.id.coquimboCheck);
        valparaisoq = findViewById(R.id.valparaisoCheck);
        concepcionq = findViewById(R.id.concepcionCheck);
        puntaarenasq = findViewById(R.id.puntaarenasCheck);
        santiagoq = findViewById(R.id.santiagoCheck);
        saludoo = findViewById(R.id.saludo);

        mAuth = FirebaseAuth.getInstance();
        usuarioActual = mAuth.getCurrentUser();

        dataBase = FirebaseDatabase.getInstance();
        reference = dataBase.getReference();

        noticias = new ArrayList<Noticia>();

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestPermissions(permisos, 100);
        }

        String id = mAuth.getCurrentUser().getUid();
        reference.child("Usuario").child(id).child("Datos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();

                    saludoo.setText("Saludos, " + nombre);

                }
                else {
                    Toast.makeText(crearNoticia.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(crearNoticia.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void comprobarPermisos(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                hayPermiso = true;
            }
            else{
                solicitarPermisos();
            }
        }
    }

    private void solicitarPermisos(){
        new AlertDialog.Builder(this)
                .setTitle("Se requiere permisos de cámara")
                .setMessage("Esta aplicación hace uso de la cámara. Por favor, otorge este permiso para que la aplicación funcione correctamente")
                .setPositiveButton("Ver permiso", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermissions(permisos,100);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                hayPermiso = true;
            }
            else {
                Toast.makeText(this,"Se requieren permisos de cámara para funcionar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void tomarFoto(View view){
        comprobarPermisos();

        if(hayPermiso){
            Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intento, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            Bitmap b = (Bitmap) data.getExtras().get("data");
            foto.setImageBitmap(b);
        }
    }


    public void guardarNoticia(View view){

        String uniqueID = UUID.randomUUID().toString();
        Noticia noticia = new Noticia();
        noticia.setFoto(R.drawable.ic_launcher_background);
        noticia.setTitulo(titulo.getText().toString());
        noticia.setDescripcion(descripcion.getText().toString());


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String tiempo = simpleDateFormat.format(new Date());
        noticia.setFecha(tiempo);

        if(iquiqueq.isChecked()){
            noticia.setUbicacion("Iquique");
        }
        if(aricaq.isChecked()){
            noticia.setUbicacion("Arica");
        }
        if(coquimboq.isChecked()){
            noticia.setUbicacion("Coquimbo");
        }
        if(valparaisoq.isChecked()){
            noticia.setUbicacion("Valparaíso");
        }
        if(santiagoq.isChecked()){
            noticia.setUbicacion("Santiago");
        }
        if(concepcionq.isChecked()){
            noticia.setUbicacion("Concepción");
        }
        if(puntaarenasq.isChecked()){
            noticia.setUbicacion("Punta Arenas");
        }


        reference.child("noticias").child(usuarioActual.getUid()).child("noticia_"+uniqueID).setValue(noticia);

        if(titulo.getText().toString().equals("") || descripcion.getText().toString().equals("")){
            camposVacios();
        }
        else if(titulo.getText().toString().equals("") && descripcion.getText().toString().equals("")){
            camposVacios();
        }
        else if(iquiqueq.isChecked() == false && aricaq.isChecked() == false && coquimboq.isChecked() == false && valparaisoq.isChecked() == false && santiagoq.isChecked() == false
                && concepcionq.isChecked() == false && puntaarenasq.isChecked() == false){
            camposVacios();
        }
        else if(iquiqueq.isChecked() == true && aricaq.isChecked() == true && coquimboq.isChecked() == true && valparaisoq.isChecked() == true && santiagoq.isChecked() == true
                && concepcionq.isChecked() == true && puntaarenasq.isChecked() == true){
            alertaUbicacion();
        }
        else{
            noticias.add(noticia);
            Intent intento = new Intent(this, VisorNoticias.class);
            intento.putExtra("noticia", noticias);
            Toast.makeText(this,"El siniestro se ha guardado correctamente", Toast.LENGTH_SHORT).show();

            titulo.setText("");
            descripcion.setText("");
            aricaq.setChecked(false);
            iquiqueq.setChecked(false);
            coquimboq.setChecked(false);
            valparaisoq.setChecked(false);
            santiagoq.setChecked(false);
            concepcionq.setChecked(false);
            puntaarenasq.setChecked(false);
        }
    }

    public void verInfo(View view){
        Intent intent = new Intent(this, DatosPersonales.class);
        startActivity(intent);
    }

    public void verNoticias(View view){
        Intent intent = new Intent(this, VisorNoticias.class);
        intent.putExtra("noticia", noticias);
        startActivity(intent);
    }

    public void cerrarSesion(View view){
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void alertaUbicacion(){
        Toast.makeText(this, "Debe seleccionar solo una ubicación", Toast.LENGTH_SHORT).show();
    }

    public void camposVacios(){
        Toast.makeText(this, "No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
    }
}