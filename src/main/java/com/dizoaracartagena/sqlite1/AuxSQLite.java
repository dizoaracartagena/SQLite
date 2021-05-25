package com.dizoaracartagena.sqlite1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AuxSQLite extends SQLiteOpenHelper {
    public AuxSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creación del modelo de datos
        String query = "CREATE TABLE 'Alumnos'(" +
                "'AlumnoID' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "'AlumnoNombre' TEXT NOT NULL" +
                ");";
        db.execSQL(query);

        //Insercion de datos iniciales
        query = "INSERT INTO Alumnos VALUES (NULL,'Roberto Gonzalez');";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Toda la lógica de actualización

    }
}
