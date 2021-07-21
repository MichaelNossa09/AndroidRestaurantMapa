package co.edu.unimagdalena.apmoviles.universidad.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class RestauranteDao {
    private BaseDatos bd;
    private Context context;

    public RestauranteDao(Context context) {
        this.bd = new BaseDatos(context, 1);
        this.context = context;
    }

    public void agregarRestaurante(RestauranteModel e) {
        try {
            SQLiteDatabase sql = bd.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DefBD.col_id, e.getId());
            values.put(DefBD.col_nombre, e.getNombre());
            values.put(DefBD.col_departamento, e.getDepartamento());
            values.put(DefBD.col_ciudad, e.getCiudad());
            values.put(DefBD.col_descripcion, e.getDescripcion());
            values.put(DefBD.col_latitud, e.getLatitud());
            values.put(DefBD.col_longitud, e.getLongitud());
            long id = sql.insert(DefBD.tabla_name, null, values);
            Toast.makeText(context, "Restaurante registrado", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context, "Error agregando restaurante " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean buscarRestaurante(RestauranteModel e) {
        String[] args = new String[]{"" + e.getId()};
        SQLiteDatabase sql = bd.getReadableDatabase();
        boolean exis;
        @SuppressLint("Recycle") Cursor c = sql.query(DefBD.tabla_name, null, "id=?", args, null, null, null);
        exis = c.getCount() > 0;
        bd.close();
        return exis;
    }

    public Cursor obtenerCursorRestaurantes() {
        try {
            SQLiteDatabase sql = bd.getReadableDatabase();
            Cursor cur = sql.rawQuery("select id as _id , nombre, departamento, ciudad, descripcion, latitud, longitud from restaurante", null);
            return cur;
        } catch (Exception ex) {
            Toast.makeText(context, "Error consulta Restaurantes " + ex.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void eliminarRestaurante(int id) {
        try {
            SQLiteDatabase sql = bd.getReadableDatabase();
            String[] args = {"" + id};
            sql.delete(DefBD.tabla_name, "id=?", args);
            Toast.makeText(context, "Restaurante eliminado", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context, "Error Eliminando el restaurante" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void actualizarRestaurante(RestauranteModel e) {
        try {
            SQLiteDatabase sql = bd.getReadableDatabase();
            String[] args = {"" + e.getId()};
            ContentValues valores = new ContentValues();
            valores.put(DefBD.col_nombre, e.getNombre());
            valores.put(DefBD.col_departamento, e.getDepartamento());
            valores.put(DefBD.col_ciudad, e.getCiudad());
            valores.put(DefBD.col_descripcion, e.getDescripcion());
            valores.put(DefBD.col_latitud, e.getLatitud());
            valores.put(DefBD.col_longitud, e.getLongitud());
            sql.update(DefBD.tabla_name, valores, "id=?", args);
            Toast.makeText(context, "Restaurante actualizado", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context, "Error actualizando Restaurante " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Cursor filtrarRestaurante(CharSequence filtro) {
        SQLiteDatabase sql = bd.getWritableDatabase();
        String query = "SELECT id as _id, nombre, departamento, ciudad, descripcion, latitud, longitud FROM hotel "
                + "where departamento like '%" + filtro + "%' or ciudad like '%" + filtro + "%' or descripcion like '%" + filtro + "%'"
                + "ORDER BY nombre ASC";
        return sql.rawQuery(query, null);
    }
}


