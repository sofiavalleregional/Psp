package com.worldskills.psp.activities;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;

import com.worldskills.psp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefectLog extends AppCompatActivity {

    private boolean p1, p2, p3, p4,p5;
    private int tiempo;
    private String fecha, tipo, removido, inyectada;
    TextView textFecha;
    Spinner type, injectec, removed;
    Chronometer defectcrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_log);


        textFecha= findViewById(R.id.defect_date);
        type= findViewById(R.id.defect_spinner);
        injectec= findViewById(R.id.defect_spinner_inject);
        removed= findViewById(R.id.defect_spinner_removed);

        defectcrono= findViewById(R.id.defect_crono);
    }

    public void iniciodefect(View view) {
        p1=true;
        SimpleDateFormat formato= new SimpleDateFormat("HH:mm:ss dd/mm/yyyy");
        Date date= new Date();
        fecha= formato.format(date);
    }

    public void ingresotipo(){
        p2=true;
        type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tipo= (String) parent.getItemAtPosition(position);
            }
        });
    }

    public void ingresoremovida(){
        p3=true;
        injectec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removido= (String) parent.getItemAtPosition(position);
            }
        });
    }

    public void ingresoinyectado(){
        p4=true;
        injectec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inyectada= (String) parent.getItemAtPosition(position);
            }
        });
    }

    public void fixtime(View v){

        switch (v.getId()){
            case R.id.defect_inicio_time:
                defectcrono.start();
                break;
            case R.id.defect_parar_time:
                defectcrono.stop();
                tiempo= (int) ((defectcrono.getBase()- SystemClock.elapsedRealtime()/1000)/60);
                break;
            case R.id.defect_reinicio_time:
                defectcrono= null;
                defectcrono.setBase(0000);
        }
    }
}
