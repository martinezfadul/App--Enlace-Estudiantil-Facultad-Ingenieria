package com.temas.selectos.eefi;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityCentral extends AppCompatActivity {

    RecyclerView rcEventosPrincipalesJ;
    ArrayList<Evento> listaEventos;
    ArrayList<Cedes> listaLugares;
    LinearLayout linearLayoutCedes;
    CalendarView calendarioEventosJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);

        linearLayoutCedes= findViewById(R.id.llCedes);
        rcEventosPrincipalesJ= findViewById(R.id.rcEventosPricnipales);
        calendarioEventosJ = findViewById(R.id.cvCalendario);

        final LinearLayoutManager Eventos = new LinearLayoutManager(this);
        Eventos.setOrientation(LinearLayout.HORIZONTAL);
        rcEventosPrincipalesJ.setLayoutManager(Eventos);

        listaEventos = new ArrayList(); iniciarListaEventos();
        listaLugares = new ArrayList();iniciarLugares();

        inicarAdaptador();

        calendarioEventosJ.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "/" + (month+1) +"/" + dayOfMonth;
                mostrarEventosDia(date);
            }
        });

    }


    private void iniciarListaEventos()
    {
        listaEventos.add(new Evento("Evento de prueba 2","Evento 2",R.drawable.segundoposter,"2019/5/18"));
        listaEventos.add(new Evento("Evento de prueba 1","Evento 1",R.drawable.primerposter,"2019/5/12"));
        listaEventos.add(new Evento("Evento de prueba 3","Evento 3",R.drawable.segundoposter,"2019/5/12"));
        listaEventos.add(new Evento("Evento de prueba 5","Evento 5",R.drawable.segundoposter,"2019/7/22"));
        listaEventos.add(new Evento("Evento de prueba 4","Evento 4",R.drawable.primerposter,"2019/12/25"));
        listaEventos.add(new Evento("Evento de prueba 6","Evento 6",R.drawable.segundoposter,"2020/1/1"));
    }

    private void inicarAdaptador()
    {
        adaptadorEvento adaptador = new adaptadorEvento(listaEventos, this);
        rcEventosPrincipalesJ.setAdapter(adaptador);
    }

    private void iniciarLugares()
    {
        listaLugares.add(new Cedes("Auditorio Javier Barros",100,100));
        listaLugares.add(new Cedes("Auditorio Marshal",200,100));
        listaLugares.add(new Cedes("Auditorio Del anexo",200,100));
        listaLugares.add(new Cedes("explanada del I",200,100));
        listaLugares.add(new Cedes("Aula magna",200,100));

        for(Cedes c:listaLugares)
        {
            TextView txtvTemp = new TextView(this);
            txtvTemp.setText(c.getNombre());
            txtvTemp.setTextSize(20);
            txtvTemp.setGravity(1);
            linearLayoutCedes.addView(txtvTemp);
        }
    }

    public void mostrarEventosDia(String date)
    {
        ArrayList<String> arregloMostrar = new ArrayList<>();

        Evento aux = listaEventos.get(0);

        if(aux.getFecha().equals(date))
        {
            arregloMostrar.add(aux.getNombre());
        }

        for(Evento e:listaEventos)
        {
            if(e.getFecha().equals(date))
            {
                arregloMostrar.add(e.getNombre());
            }
        }


        if(arregloMostrar.isEmpty())
        {
            Toast.makeText(this,"No hay eventos ese dia",Toast.LENGTH_LONG).show();
        }
        else {
            Intent intentLista = new Intent(this, ListaParaMostrar.class);
            intentLista.putExtra("arreglo", arregloMostrar);
            startActivity(intentLista);
        }
    }
}
