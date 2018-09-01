package com.worldskills.psp.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.worldskills.psp.Home;
import com.worldskills.psp.R;
import com.worldskills.psp.activities.PlanSumary;
import com.worldskills.psp.adapters.AdapterInformacion;
import com.worldskills.psp.db.DataBaseTSP;
import com.worldskills.psp.modelos.ItemInformacion;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Removed extends Fragment {
    private AdapterInformacion adapterInfo;
    private ArrayList<ItemInformacion> infos;
    private GridView gridView;
    private int catidadTotal;
    private int idProyecto;
    private int contador;

    public Removed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View row=inflater.inflate(R.layout.fragment_removed, container, false);
        gridView=row.findViewById(R.id.gridview_info);

        if (getArguments()!=null){
            catidadTotal=getArguments().getInt(PlanSumary.KEYTIEMPOTOTAL);
            idProyecto=getArguments().getInt(Home.KEYID);
        }
        contador=0;
        gridView=row.findViewById(R.id.gridview_info);


        if (getActivity()!=null && isAdded())adapterContenido();
        return row;
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
            Cursor cursor=db.cargarDefecLog(idProyecto+"",null,fases[i]);
            if (cursor!=null){
                if (cursor.moveToFirst()){
                    do{
                        contador++;
                    }while (cursor.moveToNext());
                }
            }

            double porcentaje=0.00;
            try{
                porcentaje=contador*100/catidadTotal;
            }catch (Exception e){}

            infos.add(new ItemInformacion(porcentaje,fases[i],"Cantidad defectos:\n "+contador));

        }
    }


}
