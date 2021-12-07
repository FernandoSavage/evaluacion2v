package com.example.evaluacion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VisorNoticias extends AppCompatActivity {

    ArrayList<Noticia> noticias;
    RecyclerView recycler;
    AdaptadorNoticia adaptadorNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_noticias);

        /*
        noticias = (ArrayList<Noticia>) getIntent().getSerializableExtra("noticia");

         */
        recycler = findViewById(R.id.recyclerHistorial);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        noticias = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuarioActual = mAuth.getCurrentUser();
        adaptadorNoticia = new AdaptadorNoticia(noticias);
        recycler.setAdapter(adaptadorNoticia);

        database.getReference().child("noticias").child(usuarioActual.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noticias.removeAll(noticias);
                for (DataSnapshot s :
                        snapshot.getChildren()){
                    Noticia noticia = s.getValue(Noticia.class);
                    noticias.add(noticia);
                }
                adaptadorNoticia.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}