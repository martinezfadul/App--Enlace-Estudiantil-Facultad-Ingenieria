package com.temas.selectos.eefi;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorEvento extends RecyclerView.Adapter <adaptadorEvento.eventoViewHolder>{

    ArrayList<Evento> Eventos;
    Activity activity;

    public adaptadorEvento(ArrayList<Evento> eventos, Activity activity) {
        Eventos = eventos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public eventoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carview_eventos,viewGroup,false);
        return new eventoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull eventoViewHolder eventoViewHolder, int pos) {
        final Evento eventoAux= Eventos.get(pos);

        eventoViewHolder.poster.setImageResource(eventoAux.getIdPoster());
        eventoViewHolder.titulo.setText(eventoAux.getNombre());
        eventoViewHolder.descripcion.setText(eventoAux.getDescripcion());

    }

    @Override
    public int getItemCount() {

        return Eventos.size();
    }

    public static class eventoViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView poster;
        private TextView titulo;
        private TextView descripcion;

        public eventoViewHolder(@NonNull View itemView) {

            super(itemView);
            poster = itemView.findViewById(R.id.imgvEvetno);
            titulo=itemView.findViewById(R.id.txtvTitulo);
            descripcion = itemView.findViewById(R.id.txtvDescripcion);
        }
    }
}
