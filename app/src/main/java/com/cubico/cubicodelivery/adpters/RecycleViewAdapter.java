package com.cubico.cubicodelivery.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.helper.ItemData;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<ItemData> lstItems;

    public RecycleViewAdapter(Context context, ArrayList<ItemData> lstItems) {
        this.context = context;
        this.lstItems = lstItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View contenView= LayoutInflater.from(context).inflate(R.layout.ruta_data,null);
        return new Holder(contenView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemData data= lstItems.get(position);
        Holder holder1=(Holder)holder;
        holder1.txtCliente.setText(data.getTexto1());
        holder1.txtDireccion.setText(data.getTexto2());
        holder1.txtRuta.setText(""+data.getIntData1());
        holder1.txtPedidos.setText(""+data.getIntData2());
        holder1.cwRutas.setBackgroundColor(data.getColoBack());

    }

    @Override
    public int getItemCount() {
        return lstItems.size();
    }
    public ItemData getItem(int position){
            return lstItems.get(position);
    }

    public static class Holder extends RecyclerView.ViewHolder{
        TextView txtCliente,txtDireccion,txtRuta,txtPedidos;
        CardView cwRutas;
     //   ImageButton btnNext;
     //   View divider;
        public Holder(@NonNull View itemView) {
            super(itemView);
          //  divider=itemView.findViewById(R.id.divider);
            txtCliente=itemView.findViewById(R.id.txtCliente);
            txtDireccion=itemView.findViewById(R.id.txtDireccion);
            txtRuta=itemView.findViewById(R.id.txtRuta);
            txtPedidos=itemView.findViewById(R.id.txtCantPedidos);
            cwRutas=itemView.findViewById(R.id.cwRutas);
           // btnNext=itemView.findViewById(R.id.btnSiguiente);
        }
    }
}
