package com.cubico.cubicodelivery.adpters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.helper.ItemData;

import java.util.List;

public class lv_boxbarcodeAdapter extends ArrayAdapter {

    public lv_boxbarcodeAdapter(@NonNull Context context,  @NonNull List<ItemData> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        ItemData currentItem = (ItemData) getItem(position);
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null)
        {
            convertView=inflater.inflate(R.layout.lv_boxbarcode,parent,false);
        }

        TextView lblBoxBarcode=(TextView)convertView.findViewById(R.id.lvBarcodeBox);
        lblBoxBarcode.setText(currentItem.getTexto1());
        // valueData.setWidth(0);
       /* ImageButton btnDelete = (ImageButton)convertView.findViewById(R.id.bntDelRow);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v,position,0);
            }
        });*/
        return convertView;



       // return super.getView(position, convertView, parent);
    }
}
