package com.example.actividadpersona;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import models.persona;

public class MainActivity extends AppCompatActivity {
    EditText txtNombres, txtApellidos, txtEdad, txtDNI, txtPeso, txtAltura;
    Button btnRegistrar, btnListar;
    List<persona> listaPersonas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtDNI = findViewById(R.id.txtDNI);
        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarPersona();
            }
        });
        listaPersonas = new ArrayList<>();
    }
    private void registrarPersona() {

        // Obtener valores ingresados por el usuario
        String nombres = txtNombres.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String dni = txtDNI.getText().toString();
        int edad = Integer.valueOf(txtEdad.getText().toString().trim());
        double peso = Double.valueOf(txtPeso.getText().toString());
        double altura = Double.valueOf(txtAltura.getText().toString());
        // Validaciones básicas
        if (nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        } try {
            edad = Integer.parseInt(txtEdad.getText().toString().trim());
            peso = Double.parseDouble(txtPeso.getText().toString().trim());
            altura = Double.parseDouble(txtAltura.getText().toString().trim());

            if (edad <= 0 || peso <= 0 || altura <= 0) {
                Toast.makeText(this, "Edad, peso y altura deben ser valores positivos", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Edad, peso y altura deben ser valores numéricos válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto persona y validar DNI
        persona oPersona = new persona(nombres, apellidos, edad, dni, peso, altura);

        if (oPersona.validarDNI()) {
            Toast.makeText(this, "Registro Correcto", Toast.LENGTH_LONG).show();
            listaPersonas.add(oPersona);
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error: DNI incorrecto (Debe contener 8 dígitos numéricos)", Toast.LENGTH_LONG).show();
            txtDNI.requestFocus();
        }
    }

    private void listarPersonas() {
        if (listaPersonas.isEmpty()) {
            Toast.makeText(this, "No hay personas registradas", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder lista = new StringBuilder("Personas registradas:\n");
        for (persona p : listaPersonas) {
            lista.append(p.toString()).append("\n--------------------\n");
        }

        Toast.makeText(this, lista.toString(), Toast.LENGTH_LONG).show();
    }

    private void limpiarCampos() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtDNI.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
    }
}
