package com.temas.selectos.eefi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class NavegcionActivity extends AppCompatActivity {

    WebView wbNavegacion;
    String Enlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegcion);

        obtenerExtra();
        wbNavegacion=findViewById(R.id.wvNavegcion);

        wbNavegacion.setWebViewClient(new WebViewClient());
        wbNavegacion.getSettings().setJavaScriptEnabled(true);

        wbNavegacion.loadUrl(Enlace);

    }

    public void obtenerExtra()
    {
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            Enlace= "https://www.google.com.mx/";
        } else {
            Enlace = extras.getString("link");
        }
    }
}
