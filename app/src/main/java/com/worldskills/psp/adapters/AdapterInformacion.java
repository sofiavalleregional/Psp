package com.worldskills.psp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.worldskills.psp.R;
import com.worldskills.psp.modelos.ItemInformacion;

import java.util.ArrayList;

public class AdapterInformacion extends BaseAdapter {

    private Context context;
    private int itemLayout;
    private ArrayList<ItemInformacion> infos;

    public AdapterInformacion(Context context, int itemLayout, ArrayList<ItemInformacion> infos) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.infos = infos;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        Holder holder=new Holder();

        if (row==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(itemLayout, null);

            holder.viewPorcentaje=row.findViewById(R.id.item_info_porcentaje);
            holder.viewCantidad=row.findViewById(R.id.item_info_cantidad);
            holder.viewFase=row.findViewById(R.id.item_info_fase);
            holder.layoutFondo=row.findViewById(R.id.item_info_fondo);

            row.setTag(holder);

        }else{
            holder=(Holder)row.getTag();
        }
        ItemInformacion informacion=infos.get(position);

        holder.viewPorcentaje.setText(String.format("%.2f",informacion.getPorcentaje()));
        holder.viewFase.setText(informacion.getFase());
        holder.viewCantidad.setText(informacion.getCantidad());

        return row;
    }

    class Holder{
        TextView viewPorcentaje, viewFase, viewCantidad;
        LinearLayout layoutFondo;
    }
}
