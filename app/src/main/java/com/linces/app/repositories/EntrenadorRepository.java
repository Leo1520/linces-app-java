package com.linces.app.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.linces.app.database.DatabaseHelper;
import com.linces.app.models.Entrenador;

public class EntrenadorRepository {

    private final DatabaseHelper dbHelper;

    public EntrenadorRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean existeEntrenador() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM entrenador", null);
        boolean existe = false;
        if (cursor.moveToFirst()) {
            existe = cursor.getInt(0) > 0;
        }
        cursor.close();
        return existe;
    }

    public long registrar(String correo, String contrasenaHash,
                          String preguntaSeguridad, String respuestaHash) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("correo", correo);
        values.put("contrasena", contrasenaHash);
        values.put("pregunta_seguridad", preguntaSeguridad);
        values.put("respuesta_seguridad", respuestaHash);
        return db.insert("entrenador", null, values);
    }

    public Entrenador obtenerPorCorreo(String correo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
            "SELECT id, correo, contrasena, pregunta_seguridad, respuesta_seguridad " +
            "FROM entrenador WHERE correo = ?",
            new String[]{correo}
        );
        Entrenador entrenador = null;
        if (cursor.moveToFirst()) {
            entrenador = new Entrenador();
            entrenador.setId(cursor.getInt(0));
            entrenador.setCorreo(cursor.getString(1));
            entrenador.setContrasena(cursor.getString(2));
            entrenador.setPreguntaSeguridad(cursor.getString(3));
            entrenador.setRespuestaSeguridad(cursor.getString(4));
        }
        cursor.close();
        return entrenador;
    }
}
