package com.temas.selectos.eefi.Central;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

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

    ArrayList<Evento> listaEventos;
    //RecyclerView rcvTodosEventos;
    ListView listavista;

    FirebaseDatabase basedatos;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todoslos_eventos);
        //rcvTodosEventos = findViewById(R.id.rcvTodosEventos);
        listavista = findViewById(R.id.lstv);


    }


    public void iniciarFireBase()
    {
        FirebaseApp.initializeApp(this);
        basedatos= FirebaseDatabase.getInstance();
        databaseReference=basedatos.getReference();
    }
    public void iniciarListaEventosFB()
    {
        final ArrayList<Evento> ass = new ArrayList();
        databaseReference.child("Eventos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsh: dataSnapshot.getChildren())
                {
                    Evento aux = dsh.getValue(Evento.class);
                    //Toast.makeText(ActivityCentral.this,aux.getNombre(),Toast.LENGTH_LONG).show();

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
        Bundle extras=getIntent().getExtras();
        int [] indices = extras.getIntArray ("indices");

        ArrayList<Evento> auxMuestra = new ArrayList();
        for (int i=0;i<listaEventos.size();i++)
        {
            auxMuestra.add(listaEventos.get(indices[i]));
        }
        adaptadorEvento adaptador = new adaptadorEvento(auxMuestra, this);
        //rcvTodosEventos.setAdapter(adaptador);
    }
}
