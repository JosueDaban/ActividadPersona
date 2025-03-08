package com.example.actividadpersona;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import models.persona;

public class ActividadLista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actividad_lista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView tvRespuesta= findViewById(R.id.txtLista);
        // Obtener la lista enviada desde el Intent
        ArrayList<persona> listaPersonas = (ArrayList<persona>) getIntent().getSerializableExtra("pResultado");
        // Crear un adaptador y asignarlo al ListView
        ArrayAdapter<persona> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPersonas);
        tvRespuesta.setAdapter(adapter);

    }

}