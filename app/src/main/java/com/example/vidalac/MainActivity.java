package com.example.vidalac;




import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    String pedidotStr;
    Cursor cursor;
    String [] proyectsArray= new String[] {"No hay pedidos"};
    String consultaProyectos="SELECT pedido FROM pedidos  ORDER BY pedido ASC";
    int pedido, tiempo;
    private EditText itMinutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itMinutos = (EditText) findViewById(R.id.edtTiempo);
        Spinner spin = (Spinner) findViewById(R.id.spinner);

        obtenerProyectos();
        //proyectsArray= getResources().getStringArray(R.array.valores_array);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, proyectsArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                pedidotStr= (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }

        });

    }

    public  void btnProyecto (View view) {
        Intent i = new Intent(this, WriteDb.class);
        startActivity(i);

    }

    public  void btnConsulta (View view) {
        Intent i = new Intent(this, Consulta.class);
        startActivity(i);

    }

    public void ejecutar(View view) {
        String y=itMinutos.getText().toString();
        if (pedidotStr.equals("No hay pedidos")){
            Toast.makeText(getApplicationContext(), "Agregar pedidos", Toast.LENGTH_LONG).show();

        }else{
            pedido=Integer.parseInt(pedidotStr);
            if ( y.isEmpty()){
                Toast.makeText(getApplicationContext(), "Introducir tiempo. Revisar", Toast.LENGTH_LONG).show();
            }else{
                tiempo=Integer.parseInt(y);
                if (tiempo>1000){
                    Toast.makeText(getApplicationContext(), "Max. 1000 minutos. Revisar", Toast.LENGTH_LONG).show();
                }else{
                    DbManager manager= new DbManager(this);
                    manager.updateBase(this,pedido, tiempo);
                    itMinutos.setText("");
                }

            }
        }




    }

   public void obtenerProyectos() {
        DbManager manager = new DbManager(this);
        cursor = manager.getCursor(consultaProyectos);
        if (cursor.moveToFirst()) {
            proyectsArray = new String[cursor.getCount()] ;
            //Recorremos el cursor hasta que no haya más registros
            do {
                int proyect = cursor.getInt(cursor.getColumnIndex("pedido"));
                proyectsArray[cursor.getPosition()] = cursor.getString(cursor.getColumnIndex("pedido"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        manager.cerrarDb();


    }




}
