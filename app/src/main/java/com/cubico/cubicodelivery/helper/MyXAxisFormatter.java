package com.cubico.cubicodelivery.helper;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MyXAxisFormatter extends ValueFormatter {
    final String[] pedidos = new String[] {  "Pendiente","Confirmados", "Motivados"};


    @Override
    public String getAxisLabel(float value, AxisBase axis) {
       // return super.getAxisLabel(value, axis);
        return pedidos [ (int) value];
    }
}
