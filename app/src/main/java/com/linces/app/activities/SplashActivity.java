package com.linces.app.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.linces.app.R;
import com.linces.app.database.DatabaseHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        boolean hayEntrenador = existeEntrenador(db.getReadableDatabase());

        Intent intent;
        if (hayEntrenador) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, RegistroActivity.class);
        }
        startActivity(intent);
        finish();
    }

    private boolean existeEntrenador(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM entrenador", null);
        boolean existe = false;
        if (cursor.moveToFirst()) {
            existe = cursor.getInt(0) > 0;
        }
        cursor.close();
        return existe;
    }
}
