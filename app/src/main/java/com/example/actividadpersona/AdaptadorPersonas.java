package com.example.actividadpersona;

import android.app.Person;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import AccesoDatos.DAOPersona;
import models.persona;

public class AdaptadorPersonas extends BaseAdapter {
    private DAOPersona oDAOPersona;
    //private List<persona> listaPersonas;
    private Context contexto;
    LayoutInflater inflater;
    public AdaptadorPersonas(DAOPersona oDAOPersona, Context contexto) {
        this.oDAOPersona = oDAOPersona;
        this.contexto = contexto;
        inflater= (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //crea un numero especifica de tarjetas
    @Override
    public int getCount() {
        return oDAOPersona.getSize();
    }

    //este metodo se utiliza si quieres optener algo de alguna de las tarjetas
    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista= inflater.inflate(R.layout.item_lista_persona,null);
        ImageView imgFoto = vista.findViewById(R.id.imgFoto);
        ImageView imgSexo = vista.findViewById(R.id.imgSexo);
        TextView nombre= vista.findViewById(R.id.lbNombreCompleto);
        TextView peso= vista.findViewById(R.id.lbTipoPeso);
        TextView mayorEdad= vista.findViewById(R.id.lbMayorMenorEdad);
        TextView procedencia= vista.findViewById(R.id.lbProcedencia);
        //imgFoto.setImageURI(oDAOPersona.getObjetoPersona(i).getFoto());
        //se pone un metodo para convertir la imagen a bitmap
        imgFoto.setImageBitmap(convertirBitMap(oDAOPersona.getObjetoPersona(i).getFoto()));
        nombre.setText(oDAOPersona.getObjetoPersona(i).getNombreCompleto());
        peso.setText(oDAOPersona.getObjetoPersona(i).getTipoPeso());
        mayorEdad.setText(oDAOPersona.getObjetoPersona(i).getTipoPersona());
        if (oDAOPersona.getObjetoPersona(i).getSexo().equals("Femenino"))
            imgSexo.setImageResource(R.drawable.hembra);
        else
            imgSexo.setImageResource(R.drawable.masculino);
        procedencia.setText(oDAOPersona.getObjetoPersona(i).getCiudad());


        return vista;
    }

    private Bitmap convertirBitMap(byte[] foto) {
        return BitmapFactory.decodeByteArray(foto,0,foto.length);
    }
}
