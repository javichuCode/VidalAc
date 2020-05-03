package com.example.vidalac;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.Date;

public class DbManager {
    private DbHelper dbHelperRead, dbHelperWriter ;
    private SQLiteDatabase dbRead, dbWrite;
    protected Cursor result,result1;
    private int i;
    public int [] proyectsArray;


    public DbManager(Context context) {

        dbHelperRead = new DbHelper(context, "dbVidal.db");
        dbRead = dbHelperRead.getWritableDatabase();

        dbHelperWriter = new DbHelper(context, "dbVidal.db");
        dbWrite = dbHelperWriter.getReadableDatabase();
    }

    public void cerrarDb(){
        dbRead.close();
        dbWrite.close();

    }

    public void insertBase (Context context, int pr, int tm) {

        String consulta="select t_pedido from pedidos where pedido ="+pr+"  ";
        result = dbRead.rawQuery(consulta , null);
        if (result.moveToFirst()) {
            String mesage = "Proyecto ya existe";
            Toast mensaje = Toast.makeText(context, mesage, Toast.LENGTH_LONG);
            mensaje.show();
        }else{
            try {
                dbWrite.execSQL("INSERT INTO pedidos (pedido, t_pedido,t_registro, fecha) VALUES ("+ pr + ", " + tm +",0,"+new Date().getTime()+")");
                cerrarDb();
                String mesage = "Proyecto guardado con exito.";
                Toast mensaje = Toast.makeText(context, mesage, Toast.LENGTH_LONG);
                mensaje.show();
            }catch (Exception exc){
                String mesage = ""+exc+"";
                Toast mensaje = Toast.makeText(context, mesage, Toast.LENGTH_LONG);
                mensaje.show();

            }
        }


    }

    public void updateBase (Context context, int pr, int tm) {
        //Long i = new Date().getTime();
        try {
            dbWrite.execSQL("update pedidos set t_registro = t_registro + "+tm+" where pedido="+pr+" ");
            dbWrite.execSQL("update pedidos set fecha = "+new Date().getTime()+" where pedido="+pr+" ");
            cerrarDb();
            String mesage = "Tiempo registrado con exito.";
            Toast mensaje = Toast.makeText(context, mesage, Toast.LENGTH_LONG);
            mensaje.show();
        }catch (Exception exc){
            String mesage = ""+exc+"";
            Toast mensaje = Toast.makeText(context, mesage, Toast.LENGTH_LONG);
            mensaje.show();
        }
    }


    protected Cursor getCursor(String consulta){
        result = dbRead.rawQuery(consulta , null);
        return result;
    }





}
