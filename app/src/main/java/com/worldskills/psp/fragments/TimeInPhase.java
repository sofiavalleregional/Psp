package com.worldskills.psp.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.worldskills.psp.R;
import com.worldskills.psp.activities.Home;
import com.worldskills.psp.activities.PlanSumary;
import com.worldskills.psp.adapters.AdapterInformacion;
import com.worldskills.psp.db.DataBaseTSP;
import com.worldskills.psp.modelos.ItemInformacion;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeInPhase extends Fragment {

    private AdapterInformacion adapterInfo;
    private ArrayList<ItemInformacion> infos;
    private GridView gridView;
    private int catidadTotal;
    private int idProyecto;
    private int contador;

    private LinearLayout layoutTiempo;
    private EditText agregaTime;
    private ImageButton botonTiempo;


    public TimeInPhase() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View row=inflater.inflate(R.layout.fragment_time_in_fase, container, false);

        if (getArguments()!=null){
            catidadTotal=getArguments().getInt(PlanSumary.KEYTIEMPOTOTAL);
            idProyecto=getArguments().getInt(Home.KEYID);
        }
        contador=0;
        gridView=row.findViewById(R.id.gridview_info);
        layoutTiempo=row.findViewById(R.id.plan_layout_tiempo);
        agregaTime=row.findViewById(R.id.plan_edit_text_tiempo);
        botonTiempo=row.findViewById(R.id.plan_guarda_tiepo);


        if (getActivity()!=null && isAdded()) {
            if (catidadTotal == 0) {
                layoutTiempo.getLayoutParams().height =getActivity().getResources().getDisplayMetrics().heightPixels/2;
                agregaTime();
            } else {
                layoutTiempo.getLayoutParams().height = 0;
                adapterContenido();
            }
        }





        return row;
    }
    public void agregaTime(){
        botonTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catidadTotal=0;
                if (!agregaTime.getText().toString().equalsIgnoreCase(""))
                    catidadTotal=Integer.parseInt(agregaTime.getText().toString());

                if (catidadTotal==0) {
                    Toast.makeText(getActivity(), "Agrega tiempo para mostrar informacion", Toast.LENGTH_SHORT).show();
                }else{
                    DataBaseTSP db = new DataBaseTSP(getActivity());
                    db.tablaProyectos(DataBaseTSP.ACTUALIZAR_PROYECTO, idProyecto, null,catidadTotal);
                    layoutTiempo.getLayoutParams().height = 0;
                    adapterContenido();

                }
            }
        });
    }

    public void adapterContenido(){
        try{
            infos=new ArrayList<>();
            adapterInfo=new AdapterInformacion(getActivity(),R.layout.item_informacion,infos);
            gridView.setAdapter(adapterInfo);

        }catch (Exception e){}

        cargarInfomacion();
        adapterInfo.notifyDataSetChanged();
    }

    private String[] fases;
    public void cargarInfomacion(){
        DataBaseTSP db=new DataBaseTSP(getActivity());
        fases=getActivity().getResources().getStringArray(R.array.fases);

        for (int i=0; i<fases.length;i++){
            Cursor cursor=db.cargarTimelog(idProyecto+"",fases[i]);
            if (cursor!=null){
                if (cursor.moveToFirst()){
                    do{
                        contador+=cursor.getInt(0);
                    }while (cursor.moveToNext());
                }
            }

            double porcentaje=0.00;
            try{
                porcentaje=contador*100/catidadTotal;
            }catch (Exception e){}

            infos.add(new ItemInformacion(porcentaje,fases[i],"Tiempo: "+contador));

        }
    }

}
