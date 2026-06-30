package com.linces.app.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.linces.app.database.DatabaseHelper;
import com.linces.app.models.Atleta;
import java.util.ArrayList;
import java.util.List;

public class AtletaRepository {

    private final DatabaseHelper dbHelper;

    public AtletaRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insertar(Atleta atleta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = valoresDesde(atleta);
        return db.insert("atletas", null, values);
    }

    public int actualizar(Atleta atleta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = valoresDesde(atleta);
        return db.update("atletas", values, "id = ?", new String[]{String.valueOf(atleta.getId())});
    }

    public List<Atleta> listarActivos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
            "SELECT id, nombre, apellido, fecha_nacimiento, disciplina, categoria, grupo, foto_uri, activo " +
            "FROM atletas WHERE activo = 1 ORDER BY apellido, nombre",
            null
        );
        List<Atleta> lista = new ArrayList<>();
        while (cursor.moveToNext()) {
            lista.add(desdeCursor(cursor));
        }
        cursor.close();
        return lista;
    }

    public Atleta obtenerPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
            "SELECT id, nombre, apellido, fecha_nacimiento, disciplina, categoria, grupo, foto_uri, activo " +
            "FROM atletas WHERE id = ?",
            new String[]{String.valueOf(id)}
        );
        Atleta atleta = null;
        if (cursor.moveToFirst()) {
            atleta = desdeCursor(cursor);
        }
        cursor.close();
        return atleta;
    }

    public int desactivar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("activo", 0);
        return db.update("atletas", values, "id = ?", new String[]{String.valueOf(id)});
    }

    private ContentValues valoresDesde(Atleta atleta) {
        ContentValues values = new ContentValues();
        values.put("nombre", atleta.getNombre());
        values.put("apellido", atleta.getApellido());
        values.put("fecha_nacimiento", atleta.getFechaNacimiento());
        values.put("disciplina", atleta.getDisciplina());
        values.put("categoria", atleta.getCategoria());
        values.put("grupo", atleta.getGrupo());
        values.put("foto_uri", atleta.getFotoUri());
        values.put("activo", atleta.isActivo() ? 1 : 0);
        return values;
    }

    private Atleta desdeCursor(Cursor cursor) {
        Atleta atleta = new Atleta();
        atleta.setId(cursor.getInt(0));
        atleta.setNombre(cursor.getString(1));
        atleta.setApellido(cursor.getString(2));
        atleta.setFechaNacimiento(cursor.getString(3));
        atleta.setDisciplina(cursor.getString(4));
        atleta.setCategoria(cursor.getString(5));
        atleta.setGrupo(cursor.getString(6));
        atleta.setFotoUri(cursor.getString(7));
        atleta.setActivo(cursor.getInt(8) == 1);
        return atleta;
    }
}
