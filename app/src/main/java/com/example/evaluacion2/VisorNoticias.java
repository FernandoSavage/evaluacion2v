package com.example.evaluacion2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class VisorNoticias extends AppCompatActivity {

    ArrayList<Noticia> noticias;
    RecyclerView recycler;
    AdaptadorNoticia adaptadorNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_noticias);

        noticias = (ArrayList<Noticia>) getIntent().getSerializableExtra("noticia");
        recycler = findViewById(R.id.recyclerHistorial);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adaptadorNoticia = new AdaptadorNoticia(noticias);

        recycler.setAdapter(adaptadorNoticia);

    }
}