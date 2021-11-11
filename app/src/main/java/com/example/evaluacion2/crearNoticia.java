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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class crearNoticia extends AppCompatActivity {

    ArrayList<Noticia> noticias;
    TextView titulo;
    TextView descripcion;
    ImageView foto;
    Bitmap bitmap;
    CheckBox aricaq, iquiqueq, santiagoq;
    TextView nombre;

    String[] permisos = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_noticia);

        bitmap = null;
        titulo = findViewById(R.id.tituloInput);
        descripcion = findViewById(R.id.descripcionInput);
        foto = findViewById(R.id.fotoInput);
        aricaq = findViewById(R.id.aricaCheck);
        iquiqueq = findViewById(R.id.iquiqueCheck);
        santiagoq = findViewById(R.id.santiagoCheck);
        nombre = findViewById(R.id.saludo);

        String saludoUsuario = getIntent().getStringExtra("nombre");
        nombre.setText(saludoUsuario);

        noticias = new ArrayList<Noticia>();

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestPermissions(permisos, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean cerrarApp = false;

        if(requestCode == 100){
            if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this,"Se requiere permiso de cámara", Toast.LENGTH_SHORT).show();
                cerrarApp = true;
            }
            if(!(grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this,"Se requiere permiso de escritura", Toast.LENGTH_SHORT).show();
                cerrarApp = true;
            }
            if(!(grantResults[2] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this,"Se requiere permiso de lectura", Toast.LENGTH_SHORT).show();
                cerrarApp = true;
            }
        }

        if(cerrarApp){
            Toast.makeText(this,"Tiene que otorgar los permisos necesarios", Toast.LENGTH_SHORT);
            finishAffinity();
            System.exit(0);
        }
    }

    public void tomarFoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 21);
    }

    public void guardarFotoEnMemoria(View view){
        OutputStream streamSalida = null;
        File archivoFoto = null;
        String nombreArchivo = "";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ContentResolver resolver = getContentResolver();
            ContentValues valores = new ContentValues();

            nombreArchivo = System.currentTimeMillis()+"_fotonoticia";

            valores.put(MediaStore.Images.Media.DISPLAY_NAME, nombreArchivo);
            valores.put(MediaStore.Images.Media.MIME_TYPE,"Image/jpg");
            valores.put(MediaStore.Images.Media.RELATIVE_PATH, "Fotos/MiApp");
            valores.put(MediaStore.Images.Media.IS_PENDING, 1);

            Uri coleccion = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri fotoUri = resolver.insert(coleccion, valores);

            try{
                streamSalida = resolver.openOutputStream(fotoUri);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }

            valores.clear();
            valores.put(MediaStore.Images.Media.IS_PENDING, 0);
            resolver.update(fotoUri,valores,null,null);

        }
        else{
            String ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            nombreArchivo = System.currentTimeMillis()+"_fotonoticia.jpg";
            archivoFoto = new File(ruta, nombreArchivo);

            try{
                streamSalida = new FileOutputStream(archivoFoto);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }

        if(bitmap!=null){
            boolean fotoGuardada = bitmap.compress(Bitmap.CompressFormat.JPEG,100, streamSalida);

            if(fotoGuardada){
                Toast.makeText(this, "Foto guardada exitosamente",Toast.LENGTH_SHORT).show();
            }

            if(streamSalida != null){
                try{
                    streamSalida.flush();
                    streamSalida.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(archivoFoto != null){
                MediaScannerConnection.scanFile(this, new String[]{archivoFoto.toString()}, null,null);

            }
        }
        else{
            Toast.makeText(this, "Primero debe tomar una foto", Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperarDeGaleria(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 300);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 21:
                if(resultCode == RESULT_OK){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    foto.setImageBitmap(bitmap);
                }
                break;

            case 300:
                if(resultCode == RESULT_OK){
                    Uri ruta = data.getData();
                    foto.setImageURI(ruta);
                }
                break;
            default:
                System.out.println("xd");
        }

    }

    public void guardarNoticia(View view){
        Noticia noticia = new Noticia();
        noticia.setFoto(R.drawable.ic_launcher_background);
        noticia.setTitulo(titulo.getText().toString());
        noticia.setDescripcion(descripcion.getText().toString());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String tiempo = simpleDateFormat.format(new Date());
        noticia.setFecha(tiempo);

        if(iquiqueq.isChecked()){
            Toast.makeText(this,"NXDD campos vacíos", Toast.LENGTH_SHORT).show();
        }

        if(titulo.getText().toString().equals("") || descripcion.getText().toString().equals("")){
            Toast.makeText(this,"No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
        }
        else if(titulo.getText().toString().equals("") && descripcion.getText().toString().equals("")){
            Toast.makeText(this,"No debe dejar campos vacíos", Toast.LENGTH_SHORT).show();
        }

        else{
            noticias.add(noticia);
            Intent intento = new Intent(this, VisorNoticias.class);
            intento.putExtra("noticia", noticias);
            Toast.makeText(this,"¡Registro de noticia exitoso!", Toast.LENGTH_SHORT).show();
        }
    }

    public void verNoticias(View view){
        Intent intent = new Intent(this, VisorNoticias.class);
        intent.putExtra("noticia", noticias);
        startActivity(intent);
    }

    public void cerrarSesion(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void verUbicacion(View view){

        if(aricaq.isChecked()){
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("ciudad", "arica");
            startActivity(intent);
        }

        if(iquiqueq.isChecked()){
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("ciudad", "iquique");
            startActivity(intent);
        }

        if(santiagoq.isChecked()){
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("ciudad", "santiago");
            startActivity(intent);
        }
    }
}