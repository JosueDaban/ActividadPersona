package com.example.actividadpersona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import models.persona;

public class ActividadPrincipal extends AppCompatActivity {

    //public static List<persona> listaPersona = new ArrayList<>();
    TextView lbContar;
    int contador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_principal);
        Toolbar oBarra= findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(oBarra);
        lbContar = findViewById(R.id.lbContar);
        //Archivo de preferencia
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        contador = oFlujo.getInt("contar", 1);
        String ultimaFecha = oFlujo.getString("ultimaFecha", "Nunca");

        lbContar.setText("N° Ingresos: " + contador + "\nÚltima salida: " + ultimaFecha);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        SharedPreferences.Editor oEditar=oFlujo.edit();
        // Incrementar el contador
        contador++;
        oEditar.putInt("contar", contador);

        // Guardar la fecha actual
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        formatoFecha.setTimeZone(TimeZone.getTimeZone("America/Lima"));
        String fechaActual = formatoFecha.format(new Date());
        oEditar.putString("ultimaFecha", fechaActual);

        oEditar.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent oIntento=null;
        if(item.getItemId()==R.id.itemSalir){
            finish();
        }
        if (item.getItemId()==R.id.itemRegistrarPersonas){
            oIntento= new Intent(this, ActividadRegistrarPersonas.class);
            startActivity(oIntento);
        }
        if (item.getItemId()==R.id.itemListar){
            oIntento= new Intent(this, ActividadListarPersonas.class);
            startActivity(oIntento);
        }
        if (item.getItemId()==R.id.itemLlamadas){
            oIntento= new Intent(this, ActividadLlamadas.class);
            startActivity(oIntento);
        }
        return super.onOptionsItemSelected(item);
    }
}