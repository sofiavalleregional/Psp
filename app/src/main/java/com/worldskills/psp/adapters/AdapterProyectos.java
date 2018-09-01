package com.worldskills.psp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.worldskills.psp.R;
import com.worldskills.psp.modelos.ItemProyecto;

import java.util.ArrayList;

public class AdapterProyectos extends BaseAdapter{

    private Context context;
    private int itemLayout;
    private ArrayList<ItemProyecto> proyectos;

    /*Metodo que me inicializa el constructor del adaptador*/
    public AdapterProyectos(Context context, int itemLayout, ArrayList<ItemProyecto> proyectos) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.proyectos = proyectos;
    }

    @Override
    public int getCount() {
        return proyectos.size();
    }

    @Override
    public Object getItem(int position) {
        return proyectos.get(position);
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
            row=inflater.inflate(itemLayout,null);

            holder.viewId=row.findViewById(R.id.item_proyecto_id);
            holder.viewNombre=row.findViewById(R.id.item_proyecto_nombre);
            holder.viewTiempo=row.findViewById(R.id.item_proyecto_tiempo);
            holder.viewFondo=row.findViewById(R.id.item_proyecto_fondo);

            row.setTag(holder);
        }else{
            holder=(Holder)row.getTag();
        }
        ItemProyecto proyecto=proyectos.get(position);

        holder.viewId.setText(proyecto.getIdProyecto()+"");
        holder.viewId.setBackgroundResource(R.drawable.fondo_circular_degradado_1);

        holder.viewNombre.setText(proyecto.getNombreProyecto());

        if (proyecto.getTiempoTotal()<=0){
            holder.viewTiempo.setText("Sin tiempo estimado");
        }else{
            holder.viewTiempo.setText(proyecto.getTiempoTotal()+"");
        }

        holder.viewFondo.setBackgroundResource(R.drawable.fondo_item_proyecto);

        return row;
    }

    class Holder{
        TextView viewId, viewNombre, viewTiempo;
        LinearLayout viewFondo;
    }
}
