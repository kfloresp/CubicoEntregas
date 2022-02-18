package com.cubico.cubicodelivery.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.helper.ItemData;

import java.util.ArrayList;

public class PedidoViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<ItemData> lstItems;

    public PedidoViewAdapter(Context context, ArrayList<ItemData> lstItems) {
        this.context = context;
        this.lstItems = lstItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View contenView= LayoutInflater.from(context).inflate(R.layout.pedidos,null);
        return new PedidoViewAdapter.Holder(contenView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemData data= lstItems.get(position);
        PedidoViewAdapter.Holder holder1=(PedidoViewAdapter.Holder)holder;
        holder1.txTx.setText(data.getTexto1());
        holder1.txtIdtx.setText(data.getTexto2());
        holder1.txtCantidadBultos.setText(""+data.getIntData1());
        holder1.txtSaldos.setText(""+data.getIntData2());
        holder1.txtFecha.setText(data.getTexto3());
        holder1.lblState.setText(data.getTxtTempo());
        holder1.cvPedidos.setBackgroundColor(data.getColoBack());
        // holder1.divider.setBackgroundColor(data.getColoBack());

    }

    @Override
    public int getItemCount() {
        return lstItems.size();
    }
    public ItemData getItem(int position){
        return lstItems.get(position);
    }

    public static class Holder extends RecyclerView.ViewHolder{
        TextView txTx,txtIdtx,txtCantidadBultos,txtSaldos,txtFecha,lblState;
        CardView cvPedidos;
        //   ImageButton btnNext;
        //   View divider;
        public Holder(@NonNull View itemView) {
            super(itemView);
            //  divider=itemView.findViewById(R.id.divider);
            txTx=itemView.findViewById(R.id.txtTx);
            txtIdtx=itemView.findViewById(R.id.txtIdtx);
            txtCantidadBultos=itemView.findViewById(R.id.txtCantidadBultos);
            txtSaldos=itemView.findViewById(R.id.txtSaldoBultos);
            txtFecha=itemView.findViewById(R.id.txtFechadoc);
            lblState=itemView.findViewById(R.id.lblState);
            cvPedidos=itemView.findViewById(R.id.cwPedidos);
            // btnNext=itemView.findViewById(R.id.btnSiguiente);
        }
    }
}
