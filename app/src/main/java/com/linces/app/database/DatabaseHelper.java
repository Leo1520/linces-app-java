package com.linces.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "linces.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instancia;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instancia == null) {
            instancia = new DatabaseHelper(context.getApplicationContext());
        }
        return instancia;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS entrenador (" +
            "  id                  INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  correo              TEXT    NOT NULL UNIQUE," +
            "  contrasena          TEXT    NOT NULL," +
            "  pregunta_seguridad  TEXT," +
            "  respuesta_seguridad TEXT" +
            ");"
        );

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS atletas (" +
            "  id               INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nombre           TEXT    NOT NULL," +
            "  apellido         TEXT    NOT NULL," +
            "  fecha_nacimiento TEXT    NOT NULL," +
            "  disciplina       TEXT    NOT NULL," +
            "  categoria        TEXT    NOT NULL," +
            "  grupo            TEXT," +
            "  foto_uri         TEXT," +
            "  activo           INTEGER NOT NULL DEFAULT 1" +
            ");"
        );

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS sesiones (" +
            "  id                 INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  fecha              TEXT    NOT NULL," +
            "  hora_inicio        TEXT    NOT NULL," +
            "  hora_fin           TEXT," +
            "  descripcion        TEXT    NOT NULL," +
            "  disciplina         TEXT    NOT NULL," +
            "  lugar              TEXT," +
            "  grupo              TEXT," +
            "  estado             TEXT    NOT NULL DEFAULT 'activa'," +
            "  motivo_cancelacion TEXT" +
            ");"
        );

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS asistencia (" +
            "  id        INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  atleta_id INTEGER NOT NULL," +
            "  sesion_id INTEGER NOT NULL," +
            "  estado    TEXT    NOT NULL DEFAULT 'A'," +
            "  UNIQUE (atleta_id, sesion_id)," +
            "  FOREIGN KEY (atleta_id) REFERENCES atletas(id)," +
            "  FOREIGN KEY (sesion_id) REFERENCES sesiones(id)" +
            ");"
        );

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS marcas (" +
            "  id                INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  atleta_id         INTEGER NOT NULL," +
            "  sesion_id         INTEGER," +
            "  tipo              TEXT    NOT NULL," +
            "  valor             REAL    NOT NULL," +
            "  unidad            TEXT    NOT NULL," +
            "  fecha             TEXT    NOT NULL," +
            "  notas             TEXT," +
            "  es_marca_personal INTEGER NOT NULL DEFAULT 0," +
            "  FOREIGN KEY (atleta_id) REFERENCES atletas(id)," +
            "  FOREIGN KEY (sesion_id) REFERENCES sesiones(id)" +
            ");"
        );

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS competencias (" +
            "  id          INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nombre      TEXT NOT NULL," +
            "  fecha       TEXT NOT NULL," +
            "  lugar       TEXT NOT NULL," +
            "  descripcion TEXT" +
            ");"
        );

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS competencia_atleta (" +
            "  id             INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  competencia_id INTEGER NOT NULL," +
            "  atleta_id      INTEGER NOT NULL," +
            "  posicion       INTEGER," +
            "  marca_obtenida REAL," +
            "  FOREIGN KEY (competencia_id) REFERENCES competencias(id)," +
            "  FOREIGN KEY (atleta_id)      REFERENCES atletas(id)" +
            ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS competencia_atleta");
        db.execSQL("DROP TABLE IF EXISTS marcas");
        db.execSQL("DROP TABLE IF EXISTS asistencia");
        db.execSQL("DROP TABLE IF EXISTS sesiones");
        db.execSQL("DROP TABLE IF EXISTS atletas");
        db.execSQL("DROP TABLE IF EXISTS entrenador");
        db.execSQL("DROP TABLE IF EXISTS competencias");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }
}
