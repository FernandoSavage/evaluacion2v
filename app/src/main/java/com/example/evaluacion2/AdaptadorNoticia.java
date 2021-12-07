package com.example.evaluacion2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorNoticia extends RecyclerView.Adapter<AdaptadorNoticia.ViewHolder> {
    private ArrayList<Noticia> noticias;

    public AdaptadorNoticia(ArrayList<Noticia> noticias) {
        this.noticias = noticias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla_noticias, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.foto.setImageResource(noticias.get(position).getFoto());
        holder.titulo.setText("Título: "+noticias.get(position).getTitulo());
        holder.fecha.setText("Fecha: "+noticias.get(position).getFecha());
        holder.descripcion.setText("Descripción: "+noticias.get(position).getDescripcion());
        holder.ubicacion.setText("Ciudad: "+noticias.get(position).getUbicacion());
        holder.verMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ciudad = holder.ubicacion.getText().toString();
                String arica = "Ciudad: Arica";
                String santiago = "Ciudad: Santiago";
                String iquique = "Ciudad: Iquique";
                String coquimbo = "Ciudad: Coquimbo";
                String valparaiso = "Ciudad: Valparaíso";
                String concepcion = "Ciudad: Concepción";
                String puntaarenas = "Ciudad: Punta Arenas";

                if (arica.equals(ciudad)){
                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    intent.putExtra("ciudad", "arica");
                    view.getContext().startActivity(intent);
                }
                if (iquique.equals(ciudad)){
                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    intent.putExtra("ciudad", "iquique");
                    view.getContext().startActivity(intent);
                }
                if (santiago.equals(ciudad)){
                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    intent.putExtra("ciudad", "santiago");
                    view.getContext().startActivity(intent);
                }
                if (coquimbo.equals(ciudad)){
                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    intent.putExtra("ciudad", "coquimbo");
                    view.getContext().startActivity(intent);
                }
                if (valparaiso.equals(ciudad)){
                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    intent.putExtra("ciudad", "valparaíso");
                    view.getContext().startActivity(intent);
                }
                if (concepcion.equals(ciudad)){
                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    intent.putExtra("ciudad", "concepción");
                    view.getContext().startActivity(intent);
                }
                if (puntaarenas.equals(ciudad)){
                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    intent.putExtra("ciudad", "punta arenas");
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView foto;
        private TextView titulo;
        private TextView fecha;
        private TextView descripcion;
        private TextView ubicacion;
        private Button verMapa;

        public ViewHolder(View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.fotito);
            titulo = itemView.findViewById(R.id.tituloNoticia);
            fecha = itemView.findViewById(R.id.fechaNoticia);
            descripcion = itemView.findViewById(R.id.descripcionNoticia);
            ubicacion = itemView.findViewById(R.id.ubicacionNoticia);
            verMapa = itemView.findViewById(R.id.verUbicacion);
        }

    }
}
