package com.temas.selectos.eefi.Central;

import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.temas.selectos.eefi.NavegcionActivity;
import com.temas.selectos.eefi.R;
import com.temas.selectos.eefi.clases.Evento;

import java.util.ArrayList;

public class ActivityCentral extends AppCompatActivity {

    RecyclerView rcEventosPrincipalesJ;
    private ArrayList<Evento> listaEventos= new ArrayList<Evento>();
    ArrayList<Evento.Cedes> listaLugares;
    ListView lstvCedes;
    CalendarView calendarioEventosJ;
    ArrayList<Evento> arregloMostrar;
    static int [] dias;



    FirebaseDatabase basedatos;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);

        lstvCedes = findViewById(R.id.lstvCedes);

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
            {   //obtiene la fcha que se selecciona para buscar coincidencia en la lista de eventos
                String date = year + "/" + (month+1) +"/" + dayOfMonth;
                mostrarEventosDia(date);
            }
        });


        lstvCedes.setOnItemClickListener(new AdapterView.OnItemClickListener()  //Seleciona la opcion de la lista de lugares para abrir en mapa correpspondinte
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentNavegacion = new Intent(ActivityCentral.this, NavegcionActivity.class);
                intentNavegacion.putExtra("link",listaLugares.get(position).getMapa());
                startActivity(intentNavegacion);
            }
        });

    }


    public void iniciarFireBase()
    {
        FirebaseApp.initializeApp(this);
        basedatos= FirebaseDatabase.getInstance();
        databaseReference=basedatos.getReference();
    }

    public void iniciarListaEventosFB()  //Extrae los datos de firebase
    {
        databaseReference.child("Eventos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEventos.clear();
                try {
                    for (DataSnapshot dsh : dataSnapshot.getChildren()) {
                        Evento aux = dsh.getValue(Evento.class);
                        listaEventos.add(aux);
                    }
                }
                catch (Resources.NotFoundException Err)
                {

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void inicarAdaptador(int op,int [] indices)  //llena el recyvlerView con la lista que extrajo de la bd
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

    private void iniciarLugares() //Extrae la lista de lugares y llena la LisView
    {
        listaLugares.add(new Evento.Cedes("Auditorio Javier Barros Sierra","https://www.google.com.mx/maps/place/Auditorio+Javier+Barros+Sierra/@19.3310717,-99.1862236,17z/data=!3m1!4b1!4m5!3m4!1s0x85ce0003feeabbbb:0xa6649c92297beb62!8m2!3d19.3310717!4d-99.1840349"));
        listaLugares.add(new Evento.Cedes("Auditorio Raul J. Marshal","https://www.google.com.mx/maps/place/Auditorio+Ra%C3%BAl+J.+Marsal/@19.3284988,-99.1833487,17z/data=!3m1!4b1!4m5!3m4!1s0x85ce0004fa5e2251:0x5b7f2df6637d23ab!8m2!3d19.3284988!4d-99.18116"));
        listaLugares.add(new Evento.Cedes("Auditorio Sotero Prieto","https://www.google.com.mx/maps/place/Auditorio+Sotero+Prieto/@19.325933,-99.1845793,17z/data=!3m1!4b1!4m5!3m4!1s0x85ce0005973236f5:0xe464ae212819486b!8m2!3d19.325933!4d-99.1823906"));
        listaLugares.add(new Evento.Cedes("Sala Nezahualcoyotl","https://www.google.com.mx/maps/place/Sala+Nezahualc%C3%B3yotl/@19.3143742,-99.186643,17z/data=!3m1!4b1!4m5!3m4!1s0x85ce000b42dcb0f3:0x332d337be253c646!8m2!3d19.3143742!4d-99.1844543"));
        listaLugares.add(new Evento.Cedes("Aula magna","https://www.google.com.mx/maps/place/Aula+Magna,+Facultad+de+Ingenier%C3%ADa,+Unam/@19.3315216,-99.1872566,17z/data=!3m1!4b1!4m5!3m4!1s0x85ce0001452d1ccd:0xf424ab0bba8f85d2!8m2!3d19.3315216!4d-99.1850679"));

        String [] vals = new String[listaLugares.size()];

        for(int i=0;i<listaLugares.size();i++)
        {
            vals[i]=listaLugares.get(i).getNombre();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,vals);
        lstvCedes.setAdapter(adapter);
    }

    public void mostrarEventosDia(String date)
    {
        arregloMostrar.clear();

        int [] muestra = new int[listaEventos.size()];

        for(int i=0;i<listaEventos.size();i++)
        {
            if(listaEventos.get(i).getFecha().equals(date))
            {
                arregloMostrar.add(listaEventos.get(i));
                muestra[arregloMostrar.size()]=i;
            }
        }
        int [] muestra2 = new int[arregloMostrar.size()];


        if(arregloMostrar.isEmpty())
        {
            Toast.makeText(this,"No hay eventos ese dia",Toast.LENGTH_LONG).show();
        }
        else {
            for(int i=0;i<arregloMostrar.size();i++)
            {
                muestra2[i]=muestra[i];
            }

            Intent intentLista = new Intent(this, TodoslosEventos.class);
            intentLista.putExtra("indi", muestra2);
            startActivity(intentLista);
        }
    }

    public void ordenarEventos()   //ordena los evenots cronologicamente
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
    }   //selecciona las opciones del menu del toolbal

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

    public static void quicksort(int A[], int izq, int der)  //metdo de ordenamiento
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

    public void mostrartodo()   //te lleva al activity donde se muestrar todos los eventos ya ordenados
    {
        if(!listaEventos.isEmpty())
        {

            Intent intent = new Intent(this, TodoslosEventos.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"No hay eventos regisrados",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {    //Esto es para que no se salga cuando le presionas el boton de atras


            }
}
