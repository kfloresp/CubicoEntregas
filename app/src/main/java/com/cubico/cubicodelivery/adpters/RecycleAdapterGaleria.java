package com.cubico.cubicodelivery.adpters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cubico.cubicodelivery.R;

import java.util.List;

public class RecycleAdapterGaleria extends RecyclerView.Adapter<RecycleAdapterGaleria.MyViewHolder> {
    private List<Bitmap> ListaGaleria;
    private OnclickRecycle listener;
    private int indexImage=0;

    public RecycleAdapterGaleria(List<Bitmap> listaGaleria, OnclickRecycle listener) {
        ListaGaleria = listaGaleria;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.imgcard_adaptador,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Bitmap imagen = ListaGaleria.get(position);
            indexImage=position;
            holder.bind(imagen,listener);
    }

    @Override
    public int getItemCount() {
        return ListaGaleria.size();
    }
    public int getIndexImage(){return indexImage;}



    public interface OnclickRecycle{
        void OnclickItemRecycle(Bitmap imagen);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imggaleria);
        }
        public void bind(final Bitmap imagen, final OnclickRecycle listener){
            imageView.setImageBitmap(imagen);

           // Glide.with(imageView.getContext()).load(imagen).into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnclickItemRecycle(imagen);
                }
            });

        }
    }
}
