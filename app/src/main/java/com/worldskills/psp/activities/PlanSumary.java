package com.worldskills.psp.activities;

import android.app.Fragment;
import android.database.Cursor;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;


import com.worldskills.psp.R;
import com.worldskills.psp.adapters.AdapterInformacion;
import com.worldskills.psp.db.DataBaseTSP;
import com.worldskills.psp.fragments.Injected;
import com.worldskills.psp.fragments.Removed;
import com.worldskills.psp.fragments.TimeInPhase;
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

        compruebaTiempo();
        cargaFrament(0);
    }
    public void compruebaTiempo(){
        DataBaseTSP db=new DataBaseTSP(this);
        Cursor cursor=db.tablaProyectos(DataBaseTSP.CARGAR_UN_PROYECTO,idProyecto,null,0);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                cantidad=cursor.getInt(0);
                Toast.makeText(this, "proyecto"+cantidad, Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "proyecto null", Toast.LENGTH_SHORT).show();
        }

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

    public void cargaFrament(int fragAc){

        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        Fragment frag=getFragment(fragAc);

        Bundle datosE=new Bundle();
        datosE.putInt(Home.KEYID,idProyecto);
        datosE.putInt(PlanSumary.KEYTIEMPOTOTAL,cantidad);

        frag.setArguments(datosE);

        transaction.replace(R.id.contenedor_fragment,frag);
        transaction.commit();

    }
    public Fragment getFragment(int a) {
        switch (a) {
            case 0:
                return new TimeInPhase();
            case 1:
                return new Injected();
            default: return new Removed();
        }

    }


    public void botonesPlanSummary(View v){
        switch (v.getId()){
            case R.id.plan_b1:
                compruebaTiempo();
                cargaFrament(0);
                break;
            case R.id.plan_b2:
                cargarCantidadDefectos();
                cargaFrament(1);
                break;
            case R.id.plan_b3:
                cargarCantidadDefectos();
                cargaFrament(1);
                break;
        }
    }
}
