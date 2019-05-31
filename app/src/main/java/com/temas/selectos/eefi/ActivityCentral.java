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
    ArrayList<String> arregloMostrar;
    static int [] dias;

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
        arregloMostrar = new ArrayList();

        ordenarEventos();

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
        listaEventos.add(new Evento("Evento de prueba 1","Evento 1",R.drawable.primerposter,"2019/5/2"));
        listaEventos.add(new Evento("Evento de prueba 2","Evento 2",R.drawable.segundoposter,"2019/5/4"));
        listaEventos.add(new Evento("Evento de prueba 3","Evento 3",R.drawable.primerposter,"2019/5/4"));
        listaEventos.add(new Evento("Evento de prueba 4","Evento 4",R.drawable.segundoposter,"2019/5/4"));
        listaEventos.add(new Evento("Evento de prueba 5","Evento 5",R.drawable.segundoposter,"2019/5/22"));
        listaEventos.add(new Evento("Evento de prueba 6","Evento 6",R.drawable.primerposter,"2019/12/25"));
        listaEventos.add(new Evento("Evento de prueba 7","Evento 7",R.drawable.segundoposter,"2019/1/1"));
    }

    private void inicarAdaptador(int op,int [] indices)
    {
        if(op==0) {
            adaptadorEvento adaptador = new adaptadorEvento(listaEventos, this);
            rcEventosPrincipalesJ.setAdapter(adaptador);
        }
        else if(op==1)
        {
            ArrayList<Evento> auxMuestra = new ArrayList();
            for (int i=0;i<5;i++)
            {
                auxMuestra.add(listaEventos.get(indices[i]));
            }
            adaptadorEvento adaptador = new adaptadorEvento(auxMuestra, this);
            rcEventosPrincipalesJ.setAdapter(adaptador);
        }
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
        arregloMostrar.clear();

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

    public void ordenarEventos()
    {

        dias = new int[listaEventos.size()];

        if(listaEventos.size()>5)
        {
            int [] indices = new int[listaEventos.size()];

            for(int i=0; i<listaEventos.size();i++)
            {
                String[] parts = listaEventos.get(i).getFecha().split("/");
                dias[i] = (2019 - Integer.parseInt(parts[0]) * 365 + Integer.parseInt(parts[1])*30 + Integer.parseInt(parts[2]));
                indices[i] = i;
            }

            quicksort (indices,0 ,indices.length-1);
            inicarAdaptador(1,indices);
        }
        else
        {
            int [] as=new int[1];
            inicarAdaptador(0,as);
        }

    }

    public static void quicksort(int A[], int izq, int der) {

        int pivote= A[izq]; // tomamos primer elemento como pivote
        int i=izq; // i realiza la búsqueda de izquierda a derecha
        int j=der; // j realiza la búsqueda de derecha a izquierda
        int aux;

        while(i<j){            // mientras no se crucen las búsquedas
            while(dias[A[i]]<=dias[pivote] && i<j) i++; // busca elemento mayor que pivote
            while(dias[A[j]]>dias[pivote]) j--;         // busca elemento menor que pivote
            if (i<j) {                      // si no se han cruzado
                aux= A[i];                  // los intercambia
                A[i]=A[j];
                A[j]=aux;
            }
        }
        A[izq]=A[j]; // se coloca el pivote en su lugar de forma que tendremos
        A[j]=pivote; // los menores a su izquierda y los mayores a su derecha
        if(izq<j-1)
            quicksort(A,izq,j-1); // ordenamos subarray izquierdo
        if(j+1 <der)
            quicksort(A,j+1,der); // ordenamos subarray derecho
    }
}
