package com.temas.selectos.eefi.Central;

import android.app.AlarmManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.temas.selectos.eefi.Central.ListaParaMostrar;
import com.temas.selectos.eefi.Central.TodoslosEventos;
import com.temas.selectos.eefi.Central.adaptadorEvento;
import com.temas.selectos.eefi.MainActivity;
import com.temas.selectos.eefi.NavegcionActivity;
import com.temas.selectos.eefi.R;
import com.temas.selectos.eefi.clases.Evento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class ActivityCentral extends AppCompatActivity {

    RecyclerView rcEventosPrincipalesJ;
    private ArrayList<Evento> listaEventos= new ArrayList<Evento>();
    ArrayList<Evento.Cedes> listaLugares;
    LinearLayout linearLayoutCedes;
    CalendarView calendarioEventosJ;
    ArrayList<String> arregloMostrar;
    static int [] dias;

    FirebaseDatabase basedatos;
    DatabaseReference databaseReference;

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

        iniciarFireBase();

        iniciarListaEventosFB();

        listaLugares = new ArrayList();iniciarLugares();
        arregloMostrar = new ArrayList();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ordenarEventos();
            }
        }, 2000);


        calendarioEventosJ.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                String date = year + "/" + (month+1) +"/" + dayOfMonth;
                mostrarEventosDia(date);
            }
        });

    }


    public void iniciarFireBase()
    {
        FirebaseApp.initializeApp(this);
        basedatos= FirebaseDatabase.getInstance();
        databaseReference=basedatos.getReference();
    }

    public void iniciarListaEventosFB()
    {
        databaseReference.child("Eventos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEventos.clear();
                for(DataSnapshot dsh: dataSnapshot.getChildren())
                {
                    Evento aux = dsh.getValue(Evento.class);
                    listaEventos.add(aux);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void iniciarListaEventos()
    {
        /*listaEventos.add(new Evento("Evento de prueba 1","Evento 1",R.drawable.primerposter,"2019/5/2","16:00"));
        listaEventos.add(new Evento("Evento de prueba 2","Evento 2",R.drawable.segundoposter,"2019/5/4","11:30"));
        listaEventos.add(new Evento("Evento de prueba 3","Evento 3",R.drawable.primerposter,"2019/5/4","9:00"));
        listaEventos.add(new Evento("Evento de prueba 4","Evento 4",R.drawable.segundoposter,"2019/5/4","10:00"));
        listaEventos.add(new Evento("Evento de prueba 5","Evento 5",R.drawable.segundoposter,"2019/5/22","11:00"));
        listaEventos.add(new Evento("Evento de prueba 6","Evento 6",R.drawable.primerposter,"2019/12/25","13:00"));
        listaEventos.add(new Evento("Evento de prueba 7","Evento 7",R.drawable.segundoposter,"2019/1/1","15:00"));*/
    }

    private void inicarAdaptador(int op,int [] indices)
    {
        ArrayList<Evento> auxMuestra = new ArrayList();
        if(op==0)
        {
            for (int i=0;i<listaEventos.size();i++)
            {
                auxMuestra.add(listaEventos.get(indices[i]));
            }
        }
        else if(op==1)
        {
            for (int i=0;i<5;i++)
            {
                auxMuestra.add(listaEventos.get(indices[i]));
            }
        }
        adaptadorEvento adaptador = new adaptadorEvento(auxMuestra, ActivityCentral.this);
        rcEventosPrincipalesJ.setAdapter(adaptador);
    }

    private void iniciarLugares()
    {
        listaLugares.add(new Evento.Cedes("Auditorio Javier Barros",100,100));
        listaLugares.add(new Evento.Cedes("Auditorio Marshal",200,100));
        listaLugares.add(new Evento.Cedes("Auditorio Del anexo",200,100));
        listaLugares.add(new Evento.Cedes("explanada del I",200,100));
        listaLugares.add(new Evento.Cedes("Aula magna",200,100));

        for(Evento.Cedes c:listaLugares)
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

        if (listaEventos.isEmpty())
        {
            Toast.makeText(this,"AAAAAAAAAAAAAAAAAAA",Toast.LENGTH_LONG).show();
        }

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

        if(!listaEventos.isEmpty())
        {
            int[] indices = new int[listaEventos.size()];

            for (int i = 0; i < listaEventos.size(); i++)
            {
                String[] parts = listaEventos.get(i).getFecha().split("/");
                dias[i] = (2019 - Integer.parseInt(parts[0]) * 365 + Integer.parseInt(parts[1]) * 30 + Integer.parseInt(parts[2]));
                indices[i] = i;
            }
            quicksort (indices,0 ,indices.length-1);

                if (listaEventos.size()>5)
                {
                    inicarAdaptador(1,indices);
                }
                else {
                    inicarAdaptador(0, indices);
                }
        }
        else
        {
            Toast.makeText(this,"No hay eventos regisrados",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itmMostrarTodo:
                mostrartodo();
                return true;

            case R.id.itmCabiarUsuario:
                super.onBackPressed();
                return true;

            case R.id.itmCerrar:
                finish();
                finishAffinity(); System.exit(0);
                return  true;

            case R.id.itmIniciar:
                ordenarEventos();
                return true;

            default:
                return false;
        }
    }

    public static void quicksort(int A[], int izq, int der)
    {

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

    public void mostrartodo()
    {
        if(!listaEventos.isEmpty())
        {
            RecyclerView eventosPrueba = findViewById(R.id.rcvTodosEventos);
            int[] indices = new int[listaEventos.size()];

            for (int i = 0; i < listaEventos.size(); i++)
            {
                String[] parts = listaEventos.get(i).getFecha().split("/");
                dias[i] = (2019 - Integer.parseInt(parts[0]) * 365 + Integer.parseInt(parts[1]) * 30 + Integer.parseInt(parts[2]));
                indices[i] = i;
            }
            quicksort (indices,0 ,indices.length-1);
            inicarAdaptador(1,indices);

            Intent intent = new Intent(this, TodoslosEventos.class);
            intent.putExtra("indices", indices);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"No hay eventos regisrados",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {


            }
}
