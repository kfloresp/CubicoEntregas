package com.cubico.cubicodelivery.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.helper.ItemData;
import com.cubico.cubicodelivery.model.TransferenciaModel;

import java.util.List;

public class txDataAdapter extends ArrayAdapter{

    public txDataAdapter(@NonNull Context context,  List<TransferenciaModel> objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        TransferenciaModel currentItem = (TransferenciaModel) getItem(position);
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null)
        {
            convertView=inflater.inflate(R.layout.itemsdata,parent,false);
        }

        TextView lblProducto=(TextView)convertView.findViewById(R.id.lblCodProducto);
        TextView lblDescripcion=(TextView)convertView.findViewById(R.id.lblDescripcion);
        TextView lblCantidadPedida=(TextView)convertView.findViewById(R.id.lblCantidadPedida);
        TextView txtCantidadRecibida=(TextView)convertView.findViewById(R.id.txtCantidadRecibida);
        TextView lblUmed=(TextView)convertView.findViewById(R.id.lblUmed);
        TextView lblSaldo=(TextView)convertView.findViewById(R.id.lblSaldo);

        // valueData.setWidth(0);
        ImageButton btnNext = (ImageButton)convertView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });

        return convertView;
    }
}
