package com.temas.selectos.eefi.Central;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.temas.selectos.eefi.R;
import com.temas.selectos.eefi.clases.Evento;

import java.util.ArrayList;

public class TodoslosEventos extends AppCompatActivity {

    ArrayList<Evento> listaEventos=new ArrayList<Evento>();
    RecyclerView rcvTodosEventos;
    ArrayList<Evento> auxMuestra = new ArrayList<Evento>();

    static int [] dias;


    FirebaseDatabase basedatos;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todoslos_eventos);
        rcvTodosEventos = findViewById(R.id.rcvTodosEventos);

        final LinearLayoutManager Eventos = new LinearLayoutManager(this);
        Eventos.setOrientation(LinearLayout.VERTICAL);
        rcvTodosEventos.setLayoutManager(Eventos);

        iniciarFireBase();
        iniciarListaEventosFB();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() { adaptador();
            }
        }, 1000);

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

    public void adaptador()
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



            for (int i=0;i<listaEventos.size();i++)
            {
                auxMuestra.add(listaEventos.get(indices[i]));
            }

        }
        adaptadorEvento adaptador = new adaptadorEvento(auxMuestra, this);
        rcvTodosEventos.setAdapter(adaptador);
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


}
