package com.example.vidalac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DecimalFormat;

import static android.R.color.white;

public class Consulta extends AppCompatActivity {
    GraphView grafica;
    Cursor dataPointCursor;
    DbManager manager;
    int i,y,proyecto, tiempoEstimado, tiempoRealizado, r, g, b, z;
    private  TableLayout table;
    float d;
    DecimalFormat formato2 = new DecimalFormat("#.##");
    DecimalFormat df = new DecimalFormat("00");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        TextView tv1=new TextView(this);TextView tv2=new TextView(this);TextView tv3=new TextView(this);TextView tv4=new TextView(this);
        TableRow row=new TableRow(this);
        grafica = findViewById(R.id.grafica);
        String consulta = "select * from pedidos order by fecha asc";
        manager = new DbManager(this);
        dataPointCursor = manager.getCursor(consulta);
        DataPoint[] dp;
        TableRow.LayoutParams lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.6f);
        table=(TableLayout) findViewById(R.id.tablelayout);
        //table.setBackgroundColor(16777215);
        tv1.setText("Posición");
        tv2.setText("Proyecto");
        tv3.setText("Porcentaje");
        tv4.setText("Tiempo");
        tv1.setLayoutParams(lp);tv2.setLayoutParams(lp);tv3.setLayoutParams(lp);tv4.setLayoutParams(lp);
        row.addView(tv1);row.addView(tv2);row.addView(tv3);row.addView(tv4);
        table.addView(row);

        if (dataPointCursor.moveToLast()) {

            int h;int x=5;
            i= dataPointCursor.getCount();
            if (i<5) x=dataPointCursor.getCount();

            dp = new DataPoint[x];

            //Recorremos el cursor hasta que no haya más registros
            for (h=x;h>0;h--){

                String posicion = String.valueOf(h);
                proyecto = dataPointCursor.getInt(dataPointCursor.getColumnIndex("pedido"));
                tiempoEstimado = dataPointCursor.getInt(dataPointCursor.getColumnIndex("t_pedido"));
                tiempoRealizado = dataPointCursor.getInt(dataPointCursor.getColumnIndex("t_registro"));
                y = tiempoRealizado * 100 / tiempoEstimado;
                int e=tiempoEstimado-tiempoRealizado;
                //d= (float) (e/60);
                int hour =Math.abs(e/60);
                int minutes =Math.abs(e%60) ;
                String temp=df.format(hour) + ":" + df.format(minutes);
                DataPoint v = new DataPoint((double) h, (double) y);
                tv1=new TextView(this);tv2=new TextView(this);tv3=new TextView(this);tv4=new TextView(this);
                row=new TableRow(this);
                dp[h-1] = v;
                tv1.setText(String.valueOf(posicion));tv2.setText(String.valueOf(proyecto) );tv3.setText(String.valueOf(y));tv4.setText(temp);
                tv1.setLayoutParams(lp);tv2.setLayoutParams(lp);tv3.setLayoutParams(lp);tv4.setLayoutParams(lp);
                setColor(y, row);

                row.addView(tv1);row.addView(tv2);row.addView(tv3);row.addView(tv4);
                table.addView(row);
                dataPointCursor.moveToPrevious();
            }
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dp);
            grafica.addSeries(series);
            grafica.getViewport().setMinX(0);
            grafica.getViewport().setMaxX(5);
            grafica.getViewport().setMinY(0);
            //grafica.getViewport().setMaxY(250);

            grafica.getViewport().setYAxisBoundsManual(true);
            grafica.getViewport().setXAxisBoundsManual(true);


            // DATOS DE LAS BARRAS


            // ESTILO
            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {

                    r = 0;
                    g = 0;
                    b = 0;
                    z = (int) (data.getY());
                    if (z < 101) g = 255;
                    if (z > 100 & z < 111) {
                        r = 255;
                        g = 255;
                    }
                    if (z > 110) r = 255;


                    //return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);

                    return Color.rgb(r, g, b);
                }
            });

            series.setSpacing(20);

            // DIBUJANDO LOS VALORES
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.RED);
        }
    }

    public  void btnProyecto (View view) {
        Intent i = new Intent(this, WriteDb.class);
        startActivity(i);

    }

    public  void btnMin (View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
    public void setColor(int valor, TableRow mirow) {

        if (valor < 101) mirow.setBackgroundColor(0xFF00FF00);
        if (valor > 100 & valor < 111) {
            mirow.setBackgroundColor(0xFFFFFF00);

        }
        if (valor > 110) mirow.setBackgroundColor(0xFFFF0000);

    }




}

