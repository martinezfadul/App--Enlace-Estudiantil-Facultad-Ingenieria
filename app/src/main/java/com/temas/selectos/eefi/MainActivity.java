package com.temas.selectos.eefi;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.temas.selectos.eefi.Central.ActivityCentral;

public class MainActivity extends AppCompatActivity {

    EditText edtNoCuenta;
    EditText edtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtNoCuenta = findViewById(R.id.edtNoCuenta);
        edtContraseña = findViewById(R.id.edtContraseña);
        cargarPreferencias();
    }

    public void onClickGuardar(View view){
        guardarPreferencias();
    }

    public void onClickAcceder(View view){
        //verificarDatos();
        ingreso();

    }

    public void onClickRegistrarse(View view){
        AuxSQL auxSQL = new AuxSQL(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = auxSQL.getReadableDatabase();
        String codigo = edtNoCuenta.getText().toString();
        Cursor renglon = db.rawQuery("SELECT nocuenta, contraseña FROM Usuarios WHERE nocuenta = "+codigo, null);
        if (renglon.moveToFirst()){
            Toast.makeText(this, "Ya estás registrado con el número de cuenta "+codigo, Toast.LENGTH_LONG).show();
        }
        else {
            String nocuenta = edtNoCuenta.getText().toString();
            String contraseña = edtContraseña.getText().toString();
            ContentValues registro = new ContentValues();
            registro.put("nocuenta", nocuenta);
            registro.put("contraseña", contraseña);
            db.insert("Usuarios", null, registro);
            db.close();
            edtNoCuenta.setText("");
            edtContraseña.setText("");
            Toast.makeText(this, "Se ha registrado", Toast.LENGTH_LONG).show();
        }
    }

    private void guardarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credencial", Context.MODE_PRIVATE);
        String NoCuenta = edtNoCuenta.getText().toString();
        String Contraseña = edtContraseña.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nocuenta", NoCuenta);
        editor.putString("contraseña", Contraseña);
        editor.commit();
        Toast.makeText(this, "Datos guardados\n" + NoCuenta +"\n" + Contraseña, Toast.LENGTH_LONG).show();
    }

    private void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credencial", Context.MODE_PRIVATE);
        String NoCuenta = preferences.getString("nocuenta", "No existe información");
        String Contraseña = preferences.getString("contraseña", "No existe información");
        edtNoCuenta.setText(NoCuenta);
        edtContraseña.setText(Contraseña);
    }

    private void verificarDatos(){
        AuxSQL auxSQL = new AuxSQL(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = auxSQL.getReadableDatabase();
        String codigo = edtNoCuenta.getText().toString();
        String contra = edtContraseña.getText().toString();
        Cursor renglon = db.rawQuery("SELECT nocuenta, contraseña FROM Usuarios WHERE nocuenta = "+codigo+" AND contraseña = "+contra, null);
        if (renglon.moveToFirst()){
            Toast.makeText(this, "Accediste con el número de cuenta "+codigo, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "No estás registrado o tus datos son incorrectos", Toast.LENGTH_LONG).show();
        }
    }

    private void ingreso()
    {
        Intent intentIngreso = new Intent(this, ActivityCentral.class);
        startActivity(intentIngreso);
    }
}
