package com.cubico.cubicodelivery.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.cubico.cubicodelivery.R;
import com.cubico.cubicodelivery.adpters.lv_boxbarcodeAdapter;
import com.cubico.cubicodelivery.helper.ItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailogMotivosFragment extends DialogFragment {
    Activity activity;
    //IComuni
    ImageButton btnBackDialog,btnDialogReturn;
    LinearLayout barraSuperior;
    //RecyclerView lstMotivos;
    ListView lstMotivos;
    CheckBox chkDevolucion;
    private ArrayList<ItemData> causalModelArrayList;
    lv_boxbarcodeAdapter lvitemAdapter;
    private returnDialogListener listener;
    //private RecyclerView.Adapter lvitemAdapter;
    //private RecyclerView.LayoutManager manager;
    public DailogMotivosFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return createDialogMotivo();
    }

    private Dialog createDialogMotivo() {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.fragment_dailog_motivos,null);
        builder.setView(view);
        barraSuperior=view.findViewById(R.id.barraSuperior);
        btnBackDialog=view.findViewById(R.id.btnBackDialog);
        btnDialogReturn=view.findViewById(R.id.btnDialogReturn);
        lstMotivos=view.findViewById(R.id.lstMotivos);
        chkDevolucion=view.findViewById(R.id.chkDevolucion);

        btnBackDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDialog(false,"",0,25);
                dismiss();
            }
        });
        btnDialogReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDialog(false,"",0,25);
                dismiss();
            }
        });

        Bundle bundle= getArguments();
        causalModelArrayList= (ArrayList<ItemData>) bundle.getSerializable("lstmotivos") ;

        lvitemAdapter = new lv_boxbarcodeAdapter(getContext(),causalModelArrayList);
        lstMotivos.setAdapter(lvitemAdapter);
        lstMotivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemData cm= (ItemData) lvitemAdapter.getItem(position);

                listener.onClickDialog(chkDevolucion.isChecked(),cm.getTexto1().toUpperCase(),cm.getIntData3(),15);
                dismiss();

            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof  Activity){
            //this.activity=(Activity)context;
            listener=(returnDialogListener)context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " implementar onfragmentInteraccionlistener");
        }
    }


    public interface returnDialogListener{
        void onClickDialog(boolean esDovolucion, String motivo,int idMotivo,int idEstado);
    }


}
