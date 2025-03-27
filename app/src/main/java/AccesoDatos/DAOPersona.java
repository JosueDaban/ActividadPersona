package AccesoDatos;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import models.persona;

public class DAOPersona {
    private String nombreBD;
    private int versionBD;
    private List<persona> listaPersonas;
    public DAOPersona(){
        nombreBD="BDSalud";
        versionBD=1;
        listaPersonas = new ArrayList<>();
    }
    public boolean Agregar(Activity contexto, persona oP){
        boolean rpta=false;
        BDOpenHelper oOH = new BDOpenHelper(contexto,nombreBD,null,versionBD);
        SQLiteDatabase oBD=oOH.getWritableDatabase();
        if (oBD!=null){
            ContentValues oColumnas = new ContentValues();
            oColumnas.put("nombres", oP.getNombre());
            oColumnas.put("apellidos", oP.getApellidos());
            oColumnas.put("sexo", oP.getSexo());
            oColumnas.put("ciudad", oP.getCiudad());
            oColumnas.put("edad", oP.getEdad());
            oColumnas.put("dni", oP.getDNI());
            oColumnas.put("peso", oP.getPeso());
            oColumnas.put("altura", oP.getAltura());
            oColumnas.put("foto", oP.getFoto());
            int fila= (int)oBD.insert("Persona",null,oColumnas);
            if (fila>0){
                rpta=true;
            }
            oBD.close();
            oOH.close();
        }
        return rpta;
    }
    public boolean cargarLista(Activity contexto){
        boolean rpta=false;
        BDOpenHelper oOH = new BDOpenHelper(contexto,nombreBD,null,versionBD);
        SQLiteDatabase oBD=oOH.getReadableDatabase();
        String sql = "Select * from Persona";
        Cursor oRegistros= oBD.rawQuery(sql,null);
        if (oRegistros.moveToFirst()){
            rpta=true;
            do {
                String nombres = oRegistros.getString(1);
                String apellidos = oRegistros.getString(2);
                String sexo = oRegistros.getString(3);
                String ciudad = oRegistros.getString(4);
                int edad = oRegistros.getInt(5);
                String dni = oRegistros.getString(6);
                double peso = oRegistros.getDouble(7);
                double altura = oRegistros.getDouble(8);
                byte[] foto = oRegistros.getBlob(9);
                persona oP = new persona(nombres,apellidos,sexo,ciudad,edad,dni,peso,altura,foto);
                listaPersonas.add(oP);
            }while (oRegistros.moveToNext());
            oBD.close();
            oOH.close();
        }
        return rpta;
    }
    public List<persona>getListaPersonas(){
        return listaPersonas;
    }
    public int getSize(){
        return listaPersonas.size();
    }
    public persona getObjetoPersona(int indice){
        return listaPersonas.get(indice);
    }

}
