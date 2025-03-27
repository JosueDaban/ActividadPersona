package AccesoDatos;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class DAOLlamadas {
    Spinner spTipoLlamadas;
    private Uri oTable;
    private String[] oColumna;

    public DAOLlamadas() {
        oTable = CallLog.Calls.CONTENT_URI;
        oColumna = new String[]{CallLog.Calls.TYPE,CallLog.Calls.NUMBER};
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> ListarLlamada(Activity contexto, int tipoFiltro) {
        List<String> lista = new ArrayList<>();
        String seleccion = null;
        String[] argumentos = null;

        if (tipoFiltro > 0) {
            seleccion = CallLog.Calls.TYPE + " = ?";
            argumentos = new String[]{String.valueOf(tipoFiltro)};
        }

        try {
            ContentResolver oCR = contexto.getContentResolver();
            Cursor oRegistro = oCR.query(oTable, oColumna, seleccion, argumentos, null);
            if (oRegistro != null && oRegistro.moveToFirst()) {
                do {
                    int iTipo = oRegistro.getInt(0);
                    String numero = oRegistro.getString(1);
                    String tipoLlamada[] = {"Entrante", "Saliente", "Perdida", "Rechazada", "Bloqueada"};
                    lista.add("Llamada " + tipoLlamada[iTipo - 1] + " numero telf: " + numero);
                } while (oRegistro.moveToNext());
                oRegistro.close();
            }
        } catch (Exception ex) {
            Toast.makeText(contexto, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return lista;
    }
}
