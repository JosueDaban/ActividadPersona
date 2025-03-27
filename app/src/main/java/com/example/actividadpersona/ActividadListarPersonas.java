package com.example.actividadpersona;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import AccesoDatos.DAOPersona;
import models.persona;

public class ActividadListarPersonas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_listar_personas);
        //codigo toolbar
        Toolbar oBarra= findViewById(R.id.tbListarPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView tvRespuesta= findViewById(R.id.txtLista);
        //tvRespuesta.setAdapter(new ArrayAdapter<persona>(this, android.R.layout.simple_list_item_1, ActividadRegistrarPersonas.listaPersonas));
        DAOPersona oDAOPersona = new DAOPersona();
        oDAOPersona.cargarLista(this);
        tvRespuesta.setAdapter(new AdaptadorPersonas(oDAOPersona,this));
    }

}