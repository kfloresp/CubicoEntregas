package com.cubico.cubicodelivery.adpters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.helper.ItemData;

import java.util.List;

public class ItemAdapter extends ArrayAdapter{

    public ItemAdapter(@NonNull Context context,  List<ItemData> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        ItemData currentItem = (ItemData) getItem(position);
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null)
        {
            convertView=inflater.inflate(R.layout.itemsdata,parent,false);
        }

        TextView texto1=(TextView)convertView.findViewById(R.id.texto1);
        TextView texto2=(TextView)convertView.findViewById(R.id.texto2);
        TextView texto3=(TextView)convertView.findViewById(R.id.texto3);
        TextView textoId=(TextView)convertView.findViewById(R.id.textoId);
        Button btnEstado= (Button)convertView.findViewById(R.id.btnState);
        texto1.setText(currentItem.getTexto1());
        texto2.setText(currentItem.getTexto2());
        texto3.setText(currentItem.getTexto3());
        textoId.setText(currentItem.getTextoId());
        btnEstado.setBackgroundColor(currentItem.getColoBack());
        btnEstado.setText("Estado:" + currentItem.getTxtTempo());
        // valueData.setWidth(0);
        ImageButton btnListEdit = (ImageButton)convertView.findViewById(R.id.btnListEdit);
        btnListEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });

        return convertView;
    }
}
