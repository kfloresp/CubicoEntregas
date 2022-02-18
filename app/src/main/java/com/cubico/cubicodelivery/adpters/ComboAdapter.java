package com.cubico.cubicodelivery.adpters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cubico.cubicodelivery.R;

import java.util.List;

public class ComboAdapter extends ArrayAdapter {
    private List<ComboBoxItem> lista;
    public ComboAdapter(Context context, List<ComboBoxItem> objects){
        super(context,0,objects);
        lista=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            ComboBoxItem currentItem = (ComboBoxItem) getItem(position);
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.combobox, parent, false);
            }

            TextView displayData = (TextView) convertView.findViewById(R.id.display);
            TextView valueData = (TextView) convertView.findViewById(R.id.value);
            displayData.setText(currentItem.getDisplay_data());
            valueData.setText(currentItem.getValue_data());
            // valueData.setWidth(0);
        }
        catch (Exception ex){
            Log.i("COMBOBOX",ex.getMessage());
        }
        return convertView;
        // return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public int getPosItemId(String id){
        int index=0;
        for(ComboBoxItem i:lista){
            if (i.getValue_data().equals(id)){
                Log.v("getPosItemId",""+index);
                return index;
            }
            index+=1;
        }
        return index;
    }
}
