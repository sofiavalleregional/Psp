package com.worldskills.psp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.worldskills.psp.R;
import com.worldskills.psp.db.DataBaseTSP;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeLog extends AppCompatActivity {

    private String fechaI, fechaF, comentarios, fase;
    private int delta, totalinter, tiempo;
    Chronometer timecronometro;
    Spinner timefase;
    TextView dateini, datefinal,  textdelta;
    Dialog alert, saved;
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
        textdelta= findViewById(R.id.time_delta);
        datefinal= findViewById(R.id.time_final);

        alert= new Dialog(this);
        alert.setContentView(R.layout.dialog_alert);
        alert.setCanceledOnTouchOutside(false);


        saved= new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen);
        saved.setContentView(R.layout.dialog_alert);
        saved.setCanceledOnTouchOutside(false);



        spinneronclick();

        totalinter= 0;
    }



    public void iniciarfase(View view) {
        part1 = true;
        timecronometro.start();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/mm/yyyy");
        Date date = new Date();


        fechaI= format.format(date);
        dateini.setText(fechaI);
    }


    public void spinneronclick(){
        part2=true;
        timefase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fase =(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void agregar(View view) {
        part3=true;
        int mas=(Integer.parseInt(interr.getText().toString()));
        totalinter+=mas;
        interr.setText("0");
        Toast.makeText(this, "Tiempo acumulado" +totalinter, Toast.LENGTH_SHORT).show();
    }


    public void detener(View view) {
        part4=true;
        timecronometro.stop();
        long cronotime= timecronometro.getBase()- SystemClock.elapsedRealtime();
        int secun= (int) cronotime/1000;
        int tiempo = secun/60;

        delta= totalinter-tiempo;
        textdelta.setText("Delta " +delta);

        SimpleDateFormat format= new SimpleDateFormat("HH:mm:ss dd/mm/yyyy");
        Date date = new Date();

        fechaF= format.format(date);
        datefinal.setText(fechaF);
        part4=true;
    }

    public void comentar(){
        comentarios= descripcion.getText().toString();
        if(comentarios.equalsIgnoreCase("")) part5=false;
        else part5=true;
    }


    public void guardar(View V){
        comentar();
        if(part1 && part2 && part3 && part4 && part5){

            DataBaseTSP db = new DataBaseTSP(this);
            db.saveTimeLog(0, fase,fechaI, fechaF, delta, comentarios);



        } else {
            Button volver = alert.findViewById(R.id.alert_button);
            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                }
            });

            alert.show();
        }
    }

    public void dialog(){
        Button cargar = saved.findViewById(R.id.dialog_saved_botton);
        saved.show();
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver= new Intent(getApplicationContext(), TimeLog.class);
                startActivity(volver);
                saved.dismiss();
            }
        });


    }
}
