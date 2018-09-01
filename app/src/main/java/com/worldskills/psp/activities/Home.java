package com.worldskills.psp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.worldskills.psp.R;
import com.worldskills.psp.activities.DefectLog;
import com.worldskills.psp.activities.PlanSumary;
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
    private int itemPrecionado;
    public static final String KEYID="KEYID";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        cargaListado();
    }

    public void findViews(){
        gridView=findViewById(R.id.gridview_lista);
    }

    public void cargaListado(){
        try{
            proyectos=new ArrayList<>();
            adapter=new AdapterProyectos(this,R.layout.item_proyecto_list,proyectos);
            gridView.setAdapter(adapter);
        }catch (Exception e){}

        cartaDatosProyectos();
        adapter.notifyDataSetChanged();
        clickItem();
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
    public void clickItem(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemPrecionado=position;
                abreMenuFuncions(proyectos.get(itemPrecionado));
            }
        });
    }

    public void crearProyecto(View v){
        final Dialog dialogCrearP=new Dialog(this);
        dialogCrearP.setContentView(R.layout.dialog_crear_proyecto);
        dialogCrearP.setCanceledOnTouchOutside(false);
        dialogCrearP.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText escribirNombre=dialogCrearP.findViewById(R.id.crear_proyecto_edit_text);
        ImageButton botonGuardar=dialogCrearP.findViewById(R.id.crear_proyecto_boton_guardar);
        ImageButton botonAtras=dialogCrearP.findViewById(R.id.crear_proyecto_atras);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom=escribirNombre.getText().toString();

                if (nom.equalsIgnoreCase("")){
                    Toast.makeText(Home.this, "El campo del nombre esta vacio", Toast.LENGTH_SHORT).show();
                }else{
                    guardarProyecto(nom);
                }
                dialogCrearP.dismiss();
                cargaListado();
            }
        });

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCrearP.dismiss();
            }
        });

        dialogCrearP.show();
    }
    public void guardarProyecto(String nom){
        DataBaseTSP db=new DataBaseTSP(this);
        db.tablaProyectos(DataBaseTSP.GUARDAR,0,nom,0);
    }


    public void abreMenuFuncions(ItemProyecto proyecto){
        final Dialog menuFuncions=new Dialog(this);
        menuFuncions.setContentView(R.layout.diaglo_menu_funciones);
        menuFuncions.setCanceledOnTouchOutside(false);
        menuFuncions.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton btonAtras=menuFuncions.findViewById(R.id.funciones_atras);
        TextView nombreP=menuFuncions.findViewById(R.id.funciones_nombre_proyecto);

        btonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFuncions.dismiss();
            }
        });
        nombreP.setText(proyecto.getNombreProyecto());

        menuFuncions.show();
    }
    public void menuFuncioens(View v){
        ItemProyecto proyecto=proyectos.get(itemPrecionado);
        Intent intent;
            switch (v.getId()){
                case R.id.funt_bton_time:
                    intent=new Intent(this,TimeLog.class);
                    intent.putExtra(KEYID,proyecto.getIdProyecto());
                    startActivity(intent);
                    break;
                case R.id.fun_bton_defec:
                    intent=new Intent(this,DefectLog.class);
                    intent.putExtra(KEYID,proyecto.getIdProyecto());
                    startActivity(intent);
                    break;
                case R.id.funt_bton_sumary:
                    intent=new Intent(this,PlanSumary.class);
                    intent.putExtra(KEYID,proyecto.getIdProyecto());
                    startActivity(intent);
                    break;
            }


    }
    public void onBackPressed(){

    }


}
