package com.example.actividadpersona;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarPersona();
            }
        });
        listaPersonas = new ArrayList<>();
    }
    private void registrarPersona() {
        if (validarCampos()){
            // Obtener valores ingresados por el usuario
            String nombres = txtNombres.getText().toString();
            String apellidos = txtApellidos.getText().toString();
            String dni = txtDNI.getText().toString();
            int edad = Integer.valueOf(txtEdad.getText().toString());
            double peso = Double.valueOf(txtPeso.getText().toString());
            double altura = Double.valueOf(txtAltura.getText().toString());
            // Crear objeto persona y validar DNI
            persona oPersona = new persona(nombres, apellidos, edad, dni, peso, altura);

            if (oPersona.validarDNI()) {
                Toast.makeText(this, "Registro Correcto", Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(this)
                        .setTitle("Datos Registrados")
                        .setMessage(oPersona.toString()) // Llamamos a toString() para mostrar los datos
                        .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss()) // Botón de cierre
                        .show();
                listaPersonas.add(oPersona);
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error: DNI incorrecto (Debe contener 8 dígitos numéricos)", Toast.LENGTH_LONG).show();
                txtDNI.requestFocus();
            }
        }
    }
    private boolean validarCampos() {
        String nombres = txtNombres.getText().toString().trim();
        String apellidos = txtApellidos.getText().toString().trim();
        String edadStr = txtEdad.getText().toString().trim();
        String dni = txtDNI.getText().toString().trim();
        String pesoStr = txtPeso.getText().toString().trim();
        String alturaStr = txtAltura.getText().toString().trim();

        // Validar que no estén vacíos
        if (nombres.isEmpty()) {
            txtNombres.setError("Ingrese nombres");
            return false;
        }
        if (apellidos.isEmpty()) {
            txtApellidos.setError("Ingrese apellidos");
            return false;
        }
        if (edadStr.isEmpty()) {
            txtEdad.setError("Ingrese edad");
            return false;
        }
        if (dni.isEmpty()) {
            txtDNI.setError("Ingrese DNI");
            return false;
        }
        if (pesoStr.isEmpty()) {
            txtPeso.setError("Ingrese peso");
            return false;
        }
        if (alturaStr.isEmpty()) {
            txtAltura.setError("Ingrese altura");
            return false;
        }
        return true;
    }

    private void listarPersona() {
        if (listaPersonas.isEmpty()) {
            Toast.makeText(this, "No hay personas registradas", Toast.LENGTH_SHORT).show();
            return;
        }
        // Convertir lista de personas a lista de Strings
        ArrayList<String> listaPersonasString = new ArrayList<>();
        for (persona p : listaPersonas) {
            listaPersonasString.add(p.toString());
        }

        //Invocar la Actividad Respuesta redirige a vista Actividad resultado
        //Define un contexto explicito
        Intent oIntento = new Intent(this,ActividadLista.class);
        //Definir los parametros a enviar a la siguiente actividad
        oIntento.putExtra("pResultado", new ArrayList<>(listaPersonasString));
        //Ejecutar el objeto intento
        startActivity(oIntento);
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
