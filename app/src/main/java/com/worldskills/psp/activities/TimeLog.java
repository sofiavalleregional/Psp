package com.worldskills.psp.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.worldskills.psp.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeLog extends AppCompatActivity {

    private String fechaI, fechaF, comentarios, fase;
    private int delta, totalinter, tiempo;
    Chronometer timecronometro;
    Spinner timefase;
    TextView dateini, datefinal,  textdelta;
    EditText interr, descripcion;
    Boolean part1, part2, part3, part4, part5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        timecronometro= new Chronometer(this);

        dateini= findViewById(R.id.time_inicio);
        timefase= findViewById(R.id.time_spinner_fase);
        interr= findViewById(R.id.time_edit_interrup);
        descripcion= findViewById(R.id.time_edit_comments);

        spinneronclick();

        totalinter= 0;
    }



    public void iniciarfase(View view) {
        part1=true;
        timecronometro.start();
        SimpleDateFormat format= new SimpleDateFormat("HH:mm:ss dd/mm/yyyy");
        Date date= new Date();

        fechaI= format.format(date);
        dateini.setText(fechaI);
    }


    public void spinneronclick(){
        part2=true;
        timefase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fase =(String) parent.getItemAtPosition(position);
            }
        });
    }

    public void agregar(View view) {
        part3=true;
        totalinter = totalinter + (Integer.parseInt(interr.getText().toString()));
        interr.setText("0");
        Toast.makeText(this, "Tiempo acumulado" +totalinter, Toast.LENGTH_SHORT).show();
    }


    public void detener(View view) {
        part4=true;
        timecronometro.stop();
        delta= (int) ((timecronometro.getBase()- SystemClock.currentThreadTimeMillis()/1000)/60)-totalinter;
        textdelta.setText("Delta " +delta);

        SimpleDateFormat format= new SimpleDateFormat("HH:mm:ss dd/mm/yyyy");
        Date date = new Date();

        fechaF= format.format(date);
        datefinal.setText(fechaF);
        part4=true;
    }

    public void comentar(){
        comentarios= descripcion.getText().toString();
        part5=true;
    }


    public void guardar(View V){
        comentar();
        if(part1 && part2 && part3 && part4 && part5){
            // BASDE DE DATOS

            // Dialogo de información exitosa

            Intent volver= new Intent(this, TimeLog.class);
            startActivity(volver);
        } else {
            // ALerta
        }
    }
}
