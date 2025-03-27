package com.example.actividadpersona;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import AccesoDatos.DAOPersona;
import models.persona;

public class ActividadRegistrarPersonas extends AppCompatActivity {
    EditText txtNombres, txtApellidos, txtEdad, txtDNI, txtPeso, txtAltura;
    Button btnRegistrar, btnListar;
    RadioGroup rgSexo;
    Spinner spCiudad;
    ImageView imgFoto;
    DAOPersona oDAOPersona = new DAOPersona();
    String[] ciudades={"Seleccionar Ciudad","Cajamarca","Trujillo","Lima","Chiclayo","Arequipa"};
    byte[] imgSelecionado=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_registrar_personas);
        //codigo toolbar
        Toolbar oBarra= findViewById(R.id.tbRegistrarPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        rgSexo = findViewById(R.id.rgSexo);
        spCiudad = findViewById(R.id.spCiudad);
        //cargar los itemd para spCiudad(lista)
        spCiudad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ciudades));
        txtEdad = findViewById(R.id.txtEdad);
        txtDNI = findViewById(R.id.txtDNI);
        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        imgFoto = findViewById(R.id.imgFoto);
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFoto();
            }
        });
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

    }

    private void seleccionarFoto() {
        Intent oIntento = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //filtrar archivos de tipo imagen
        oIntento.setType("image/*");
        startActivityIfNeeded(Intent.createChooser(oIntento,"Seleccionar Imagen"), 100);
    }
    //Metodo(siperclase) que recoje la imagen seleccionada
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent oInteto) {
        super.onActivityResult(requestCode, resultCode, oInteto);
        if (requestCode==100){
            if(resultCode==RESULT_OK){
                Uri foto= oInteto.getData();
                imgFoto.setImageURI(foto);
                imgFoto.buildDrawingCache();
                Bitmap oBitMap=imgFoto.getDrawingCache();
                ByteArrayOutputStream oFlujoSalida=new ByteArrayOutputStream();
                oBitMap.compress(Bitmap.CompressFormat.PNG,0,oFlujoSalida);
                imgSelecionado= oFlujoSalida.toByteArray();
            }
        }
    }

    private void registrarPersona() {
            if (validarCampos())
                return;
            // Obtener valores ingresados por el usuario
            String nombres = txtNombres.getText().toString();
            String apellidos = txtApellidos.getText().toString();
            String sexo= obtenerSexo();
            String ciudad = spCiudad.getSelectedItem().toString();
            String dni = txtDNI.getText().toString();
            int edad = Integer.valueOf(txtEdad.getText().toString());
            double peso = Double.valueOf(txtPeso.getText().toString());
            double altura = Double.valueOf(txtAltura.getText().toString());

            // Crear objeto persona y validar DNI
            persona oPersona = new persona(nombres, apellidos, sexo, ciudad, edad, dni, peso, altura,imgSelecionado);

            if (oPersona.validarDNI()) {

                /*new AlertDialog.Builder(this)
                        .setTitle("Datos Registrados")
                        .setMessage(oPersona.toString()) // Llamamos a toString() para mostrar los datos
                        .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss()) // Botón de cierre
                        .show();*/
                //ActividadPrincipal.listaPersona.add(oPersona);
                DAOPersona oDAOPersona = new DAOPersona();
                if (oDAOPersona.Agregar(this,oPersona)){
                    Toast.makeText(this, "Registro Correcto", Toast.LENGTH_LONG).show();
                    cuadroDialogo();
                }
                else
                    Toast.makeText(this, "No se registro", Toast.LENGTH_LONG).show();
                //limpiarCampos();
            } else {
                Toast.makeText(this, "Error: DNI incorrecto (Debe contener 8 dígitos numéricos)", Toast.LENGTH_LONG).show();
                txtDNI.requestFocus();
            }
    }

    private void cuadroDialogo() {
        AlertDialog.Builder oDialogo=new AlertDialog.Builder(this);
        oDialogo.setTitle("Aviso");
        oDialogo.setMessage("¿Desea seguir registrando?");
        oDialogo.setIcon(R.drawable.baseline_add_alert_24);
        oDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ActividadRegistrarPersonas.this.finish();
            }
        });
        oDialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                limpiarCampos();
            }
        });
        oDialogo.show();
    }

    private String obtenerSexo() {
        int ident= rgSexo.getCheckedRadioButtonId();
        if(ident==R.id.rbFemenino) return  "Femenino";
        if(ident==R.id.rbMasculino) return  "Masculino";
        return "";
    }

    private boolean validarCampos() {

        if (comprobarCampo(txtNombres,"Nombres"))return true;
        if (comprobarCampo(txtApellidos,"Apellidos"))return true;
        if(obtenerSexo().isEmpty()){
            mostrarError("Seleccionar Tipo de Sexo");
            return true;
        }
        if (spCiudad.getSelectedItemPosition()==0){
            mostrarError("Seleccionar Ciudad");
            return true;
        }
        if (comprobarCampo(txtEdad,"Edad"))return true;
        if (comprobarCampo(txtDNI,"DNI"))return true;
        if (comprobarCampo(txtPeso,"Peso"))return true;
        if (comprobarCampo(txtAltura,"Altura"))return true;
        if (imgSelecionado==null){
            mostrarError("Seleccionar Foto");
            return true;
        }
        return false;
    }

    private void mostrarError(String mensaje) {
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
    private boolean comprobarCampo(EditText campo, String mensaje){
        if (campo.getText().toString().trim().isEmpty()) {
            campo.setError("Ingrese "+ mensaje);
            campo.requestFocus();
            return true;
        }
        return false;
    }
    private void listarPersona() {
        if (oDAOPersona.getSize()==0) {
            Toast.makeText(this, "No hay personas registradas", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent oIntento = new Intent(this, ActividadListarPersonas.class);
        startActivity(oIntento);
    }
    private void limpiarCampos() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtDNI.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        rgSexo.check(R.id.rbFemenino);
        spCiudad.setSelection(0);
        imgFoto.setImageResource(R.drawable.click);
        imgSelecionado=null;
        txtNombres.requestFocus();
    }
}
