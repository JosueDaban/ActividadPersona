package com.example.actividadpersona;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import AccesoDatos.DAOLlamadas;
import AccesoDatos.DAOPersona;

public class ActividadLlamadas extends AppCompatActivity {

    Spinner spTipoLlamadas;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_llamadas);

        spTipoLlamadas = findViewById(R.id.spTipoLlamada);
        String[] opciones = {"Todas", "Entrante", "Saliente", "Perdida", "Rechazada", "Bloqueada"};
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoLlamadas.setAdapter(spAdapter);

        ListView tvRespuesta = findViewById(R.id.lvListaLlamadas);
        DAOLlamadas oDAOLlamadas = new DAOLlamadas();

        spTipoLlamadas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int tipoFiltro = position == 0 ? 0 : position;
                tvRespuesta.setAdapter(new ArrayAdapter<>(ActividadLlamadas.this, android.R.layout.simple_list_item_1, oDAOLlamadas.ListarLlamada(ActividadLlamadas.this, tipoFiltro)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}