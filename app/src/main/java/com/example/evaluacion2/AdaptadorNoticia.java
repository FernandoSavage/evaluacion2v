package com.example.evaluacion2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        public ViewHolder(View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.fotito);
            titulo = itemView.findViewById(R.id.tituloNoticia);
            fecha = itemView.findViewById(R.id.fechaNoticia);
            descripcion = itemView.findViewById(R.id.descripcionNoticia);
        }
    }
}
