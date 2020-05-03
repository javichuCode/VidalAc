package com.example.vidalac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Usuarios
    public  static final  int DB_VERSION = 1;

    String sqlCreate = "CREATE TABLE pedidos (pedido INTEGER NOT NULL PRIMARY KEY," +
            "t_pedido INTEGER," +
            "t_registro INTEGER," +
            " fecha INTEGER)";




    public DbHelper(Context context, String nomDb ) {

        super(context, nomDb, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS PROYECTOS");
        db.execSQL("DROP TABLE IF EXISTS registros");
        db.execSQL("DROP TABLE IF EXISTS pedidos");


        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);

    }
}
