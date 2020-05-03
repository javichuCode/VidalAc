package com.example.vidalac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class WriteDb extends AppCompatActivity {
    int proyecto, decimas, horas, tiempo;
    private EditText proyectoEdt, decimasEdt,horasEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_db);
        proyectoEdt = (EditText) findViewById(R.id.itProyecto);
        decimasEdt = (EditText) findViewById(R.id.edtMinutos);
        horasEdt = (EditText) findViewById(R.id.edtHoras);

    }

    public void ejecutar(View view) {
        String x=proyectoEdt.getText().toString();
        String y=decimasEdt.getText().toString();
        String z=horasEdt.getText().toString();
        if (y.isEmpty()){
            decimas=0;
        }else {
            decimas=Integer.parseInt(y);
        }
        if (z.isEmpty()){horas=0;}else{ horas=Integer.parseInt(z);}
        tiempo=(decimas*60/100)+(horas*60);


        if (x.isEmpty()){
            Toast.makeText(getApplicationContext(), "Proyecto no puede estar vacio. Revisar", Toast.LENGTH_LONG).show();
        }else{
            proyecto=Integer.parseInt(proyectoEdt.getText().toString());
            if(y.isEmpty()&z.isEmpty()){
                Toast.makeText(getApplicationContext(), "Tiempo no puede estar vacio. Revisar", Toast.LENGTH_LONG).show();
            }else{

                if (proyecto<190000||proyecto>209999){
                    Toast.makeText(getApplicationContext(), "Numero de proyecto no valido. Revisar", Toast.LENGTH_LONG).show();
                }else{
                    if (horas>500){
                        Toast.makeText(getApplicationContext(), "Max. 500 horas ( 3 meses ). Revisar", Toast.LENGTH_LONG).show();
                    } else{
                        if(decimas>99){
                            Toast.makeText(getApplicationContext(), "Max. 99 decimas. Revisar", Toast.LENGTH_LONG).show();
                        }else{
                            DbManager manager= new DbManager(this);
                            manager.insertBase(this,proyecto, tiempo);
                            proyectoEdt.setText("");decimasEdt.setText("");horasEdt.setText("");
                        }
                    }
                }
            }
        }
    }

    public  void btnMain (View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    public  void btnConsulta (View view) {
        Intent i = new Intent(this, Consulta.class);
        startActivity(i);

    }
}
