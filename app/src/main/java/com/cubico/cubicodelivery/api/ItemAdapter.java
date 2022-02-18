package com.cubico.cubicodelivery.api;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cubico.cubicodelivery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends ArrayAdapter {

    public ItemAdapter(@NonNull Context context, List<ItemData> objects) {
        super(context, 0,objects);
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
//        TextView textoid=(TextView)convertView.findViewById(R.id.textoId);
//        ImageView imageView=(ImageView)convertView.findViewById(R.id.imgActivoList);

        texto1.setText(currentItem.getTexto1());
        texto2.setText(currentItem.getTexto2());
        texto3.setText(currentItem.getTexto3());
//        textoid.setText(currentItem.getTextoId());

       /* if (currentItem.getUrlImage().length()>0){
            try{
                if (currentItem.getUrlImage().equals("assets/noimage.png")) {
//                    imageView.setImageResource(R.drawable.noimage);

                }
                else
//                    Picasso.with(getContext()).load(currentItem.getUrlImage()).resize(100,100).into(imageView);
            }
            catch (Exception ex){
                imageView.setImageResource(R.drawable.baseline_brightness_auto_black_36);
            }
        }
        else{
            imageView.setImageResource(R.drawable.baseline_brightness_auto_black_36);
        }*/

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
