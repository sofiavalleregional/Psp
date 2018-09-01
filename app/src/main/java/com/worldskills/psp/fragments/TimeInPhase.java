package com.worldskills.psp.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.concurrent.ExecutionException;


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

    public TimeInPhase() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View row=inflater.inflate(R.layout.fragment_blank, container, false);

        if (getArguments()!=null){
            catidadTotal=getArguments().getInt(PlanSumary.KEYTIEMPOTOTAL);
            idProyecto=getArguments().getInt(Home.KEYID);
        }
        gridView=row.findViewById(R.id.gridview_info);




        return row;
    }
    public void findViews(){

    }
    public void adapterContenido(){
        try{
            infos=new ArrayList<>();
            adapterInfo=new AdapterInformacion(getActivity(),R.layout.item_informacion,infos);
            gridView.setAdapter(adapterInfo);

        }catch (Exception e){}

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

                    }while (cursor.moveToNext());
                }
            }

        }
    }

}
