package com.temas.selectos.eefi.Central;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.temas.selectos.eefi.MainActivity;
import com.temas.selectos.eefi.NavegcionActivity;
import com.temas.selectos.eefi.R;
import com.temas.selectos.eefi.clases.Evento;
import java.util.ArrayList;

public class adaptadorEvento extends RecyclerView.Adapter <adaptadorEvento.eventoViewHolder> {

    ArrayList<Evento> Eventos;
    Activity activity;

    public adaptadorEvento(ArrayList<Evento> eventos, Activity activit) {
        this.Eventos = eventos;
        this.activity = activit;
    }

    @NonNull
    @Override
    public eventoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carview_eventos,viewGroup,false);
        return new eventoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final eventoViewHolder eventoViewHolder, int pos) {
        final Evento eventoAux= Eventos.get(pos);

        eventoViewHolder.poster.setImageResource(eventoAux.getIdPoster());
        eventoViewHolder.titulo.setText(eventoAux.getNombre());
        eventoViewHolder.descripcion.setText(eventoAux.getDescripcion());

        eventoViewHolder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu pop = new PopupMenu(activity.getApplicationContext(),v);
                pop.inflate(R.menu.menu_popup);
                pop.show();


                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId())
                        {
                            case R.id.btnIrPagina:
                                Toast.makeText(activity.getApplicationContext(),"ir a pagina de " + eventoAux.getUrl(),Toast.LENGTH_LONG).show();

                                Intent intentNavegacion = new Intent(v.getContext(), NavegcionActivity.class);
                                intentNavegacion.putExtra("link",eventoAux.getUrl());
                                activity.startActivity(intentNavegacion);


                                return true;


                            case R.id.btnRecordatorio:
                                Toast.makeText(activity.getApplicationContext(),"inserte recordatorio aqui",Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }

        });


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
