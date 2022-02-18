package com.cubico.cubicodelivery.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/*import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Area;
import com.anychart.core.ui.Crosshair;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.MarkerType;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.graphics.vector.Stroke;*/
import com.cubico.cubicodelivery.CubicoGlobal;
import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.api.CubicoWSClient;
import com.cubico.cubicodelivery.api.CubicoWebApiCliente;
import com.cubico.cubicodelivery.helper.DayAxisValueFormatter;
import com.cubico.cubicodelivery.helper.MyValueFormatter;
import com.cubico.cubicodelivery.helper.MyXAxisFormatter;
import com.cubico.cubicodelivery.helper.XYMarkerView;
import com.cubico.cubicodelivery.model.ResumenPedidosModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils.*;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GraphFragment extends DialogFragment {

    //private onClickDialogListener listener;
    ImageButton btnBackDialog;
    private BarChart chart;
    View view;
    ProgressDialog progressDialog;
    CubicoGlobal cg;
    private int idRuta;
    ResumenPedidosModel resumenPedidosModel;
    public GraphFragment(int idRuta) {
        this.idRuta=idRuta;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            //this.activity=(Activity)context;
         //   listener=(GraphFragment.onClickDialogListener)context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " implementar onfragmentInteraccionlistener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      //  return super.onCreateDialog(savedInstanceState);
        cg=(CubicoGlobal)getActivity().getApplication();
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        view=inflater.inflate(R.layout.query_graph_layout,null);
        builder.setView(view);

        chart  = view.findViewById(R.id.chart1);
        //chart.setOnChartValueSelectedListener(this);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        //ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
        ValueFormatter xAxisFormatter = new MyXAxisFormatter();

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
      //  xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        ValueFormatter custom = new MyValueFormatter(" P.");

        YAxis leftAxis = chart.getAxisLeft();
      //  leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        btnBackDialog= view.findViewById(R.id.btnClose);

        btnBackDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        LoadResumen();

        return builder.create();


    }

    private void setData() {
       ArrayList<BarEntry> values = new ArrayList<>();
       values.add(new BarEntry(0,resumenPedidosModel.getPendientes() ));
       values.add(new BarEntry(1,resumenPedidosModel.getConfirmados() ));
       values.add(new BarEntry(2,resumenPedidosModel.getMotivados()));
       BarDataSet set1;
       int totalPedidos=resumenPedidosModel.getPendientes()+resumenPedidosModel.getConfirmados()+resumenPedidosModel.getMotivados();
       set1 = new BarDataSet(values, " Total Pedidos: " + totalPedidos);
       set1.setColors(new int[] {R.color.colorYellow, R.color.colorGreen, R.color.colorRed  },getContext());
        BarData data = new BarData(set1);
        data.setBarWidth(0.9f);
        data.setValueTextSize(10f);
        //  data.setValueTypeface(tfLight);
        chart.setData(data);
        chart.setFitBars(true);
        chart.invalidate();

    }

    private void LoadResumen(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Cubico WMS");
        progressDialog.setMessage("Cargando datos....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            String urlApi=cg.getWebApi();
            Call<ResumenPedidosModel> call = CubicoWebApiCliente.getInstance(urlApi).getApiCubicoWebApi().Get_ResumenPedido(idRuta);
            call.enqueue(new Callback<ResumenPedidosModel>() {
                @Override
                public void onResponse(Call<ResumenPedidosModel> call, Response<ResumenPedidosModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            resumenPedidosModel= response.body();
                            setData();
                        }
                    }
                    else{
                        resumenPedidosModel= new ResumenPedidosModel();
                        setData();
                    }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResumenPedidosModel> call, Throwable t) {
                    Log.i("Throwable",t.getMessage());
                    progressDialog.dismiss();
                }
            });


          /*  Call<ResumenPedidosModel> call= CubicoWSClient.getInstance(cg.getWebApi()).getApiCubico().Get_ResumenPedido(idRuta);
            call.enqueue(new Callback<ResumenPedidosModel>() {
                @Override
                public void onResponse(Call<ResumenPedidosModel> call, Response<ResumenPedidosModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            resumenPedidosModel= response.body();
                            setData();
                        }
                    }
                    else{
                        resumenPedidosModel= new ResumenPedidosModel();
                        setData();
                    }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResumenPedidosModel> call, Throwable t) {
                    Log.i("Throwable",t.getMessage());
                    progressDialog.dismiss();
                }
            });*/



        }
        catch (Exception ex){
            Log.i("Exception",ex.getMessage());
            progressDialog.dismiss();
        }


    }



}
