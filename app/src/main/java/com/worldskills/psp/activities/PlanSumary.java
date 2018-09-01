package com.worldskills.psp.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.worldskills.psp.Home;
import com.worldskills.psp.R;
import com.worldskills.psp.adapters.AdapterInformacion;
import com.worldskills.psp.db.DataBaseTSP;
import com.worldskills.psp.modelos.ItemInformacion;

import java.util.ArrayList;

public class PlanSumary extends AppCompatActivity {

    private int cantidad, idProyecto;
    public static final String KEYTIEMPOTOTAL="KEYTIEMPO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_sumary);

        Bundle datos=getIntent().getExtras();
        if (datos!=null){
            idProyecto=datos.getInt(Home.KEYID);
        }

        cantidad=0;
    }

    public void cargarCantidadDefectos(){
        DataBaseTSP db=new DataBaseTSP(this);

        Cursor cursor=db.cargarDefecLog(idProyecto+"",null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                do{
                    cantidad++;
                }while (cursor.moveToNext());
            }
        }else{
            Toast.makeText(this, "timeLog no carga", Toast.LENGTH_SHORT).show();
        }
    }
}
