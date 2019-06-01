package com.temas.selectos.eefi.Central;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.temas.selectos.eefi.R;

import java.util.ArrayList;

public class ListaParaMostrar extends AppCompatActivity {

    ListView listaVista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_para_mostrar);
        listaVista=findViewById(R.id.lvEventos);

        recibiDatos();

    }

    private void recibiDatos()
    {
        Bundle extras=getIntent().getExtras();
        ArrayList<String> recibido = extras.getStringArrayList ("arreglo");
        Toast.makeText(this,recibido.get(0),Toast.LENGTH_LONG).show();

        ArrayAdapter adatadorLista = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recibido);
        listaVista.setAdapter (adatadorLista);

    }
}
