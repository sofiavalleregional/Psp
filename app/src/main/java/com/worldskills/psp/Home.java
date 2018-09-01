package com.worldskills.psp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.worldskills.psp.activities.DefectLog;
import com.worldskills.psp.activities.TimeLog;
import com.worldskills.psp.adapters.AdapterProyectos;
import com.worldskills.psp.db.DataBaseTSP;
import com.worldskills.psp.modelos.ItemProyecto;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Home extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<ItemProyecto> proyectos;
    private AdapterProyectos adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void findViews(){
        gridView=findViewById(R.id.gridview_lista);
    }

    public void cargaListado(){
        try{
            proyectos=new ArrayList<>();
            adapter=new AdapterProyectos(this,R.drawable.fondo_item_proyecto,proyectos);
            gridView.setAdapter(adapter);
        }catch (Exception e){}

        cartaDatosProyectos();
        adapter.notifyDataSetChanged();
    }
    public void cartaDatosProyectos(){
        DataBaseTSP db=new DataBaseTSP(this);
        Cursor cursor=db.tablaProyectos(DataBaseTSP.CARGAR_PROYECTOS,0,null,0);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                do{
                    proyectos.add(new ItemProyecto(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
                }while (cursor.moveToNext());
            }
        }else{
            Toast.makeText(this, "Sin ningun proyecto creaado actualmente", Toast.LENGTH_SHORT).show();
        }
    }

    public void crearProyecto(View v){

    }


}
