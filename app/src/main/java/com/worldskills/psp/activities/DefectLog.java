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

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefectLog extends AppCompatActivity {

    private boolean p1, p2, p3, p4,p5, p6, p7, p8;
    private int tiempo, idproyect;
    private String fecha, tipo, removido, inyectada;
    TextView textFecha;
    EditText solucion;
    Spinner type, injectec, removed;
    Dialog saved, alert;
    Chronometer defectcrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_log);


        Bundle bun= getIntent().getExtras();
        idproyect= bun.getInt(Home.KEYID);

        textFecha= findViewById(R.id.defect_date);
        type= findViewById(R.id.defect_spinner);
        injectec= findViewById(R.id.defect_spinner_inject);
        removed= findViewById(R.id.defect_spinner_removed);
        saved= new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen);
        saved.setContentView(R.layout.dialog_saved);
        saved.setCanceledOnTouchOutside(false);

        alert= new Dialog(this);
        alert.setContentView(R.layout.dialog_saved);
        alert.setCanceledOnTouchOutside(false);


        defectcrono= findViewById(R.id.defect_crono);
        solucion= findViewById(R.id.defect_edit);
    }

    public void iniciodefect(View view) {
        p1=true;
        SimpleDateFormat formato= new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date= new Date();
        fecha= formato.format(date);

        textFecha.setText(fecha);
    }

    public void ingresotipo(){
        p2=true;
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tipo= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void ingresoremovida(){
        p3=true;
        injectec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                removido= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void ingresoinyectado(){
        p4=true;
        injectec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inyectada= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void fixtime(View v){

        switch (v.getId()){

            case R.id.defect_inicio_time:
                p5= true;
                defectcrono.start();
                break;
            case R.id.defect_parar_time:
                p6=true;
                defectcrono.stop();
                tiempo= (int) ((defectcrono.getBase()- SystemClock.elapsedRealtime()/1000)/60);
                break;
            case R.id.defect_reinicio_time:
                p7=true;
                defectcrono.setBase(SystemClock.elapsedRealtime());
                defectcrono.start();
                break;
        }
    }


    public void guardardefecto(View v){
        ingresoinyectado();
        ingresotipo();
        ingresoremovida();
        String solution = solucion.getText().toString();
        if(solution.equalsIgnoreCase("")) p8=false;
                else p8=true;

        if(p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8){
            DataBaseTSP db = new DataBaseTSP(this);
            db.saveDefecLog(idproyect, tipo, fecha, inyectada, removido, tiempo, solution);
            Button volver =saved.findViewById(R.id.dialog_saved_botton);

            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getApplicationContext(), DefectLog.class);
                    intent.putExtra(Home.KEYID, idproyect);
                    startActivity(intent);
                    saved.dismiss();
                }
            });

            saved.show();

        } else {
            Button salir = alert.findViewById(R.id.alert_button);
            salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                }
            });

            alert.show();
        }
        }

}
