package com.haris.food_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {
    private Context context;
    private List<produk> list;
    private CardView cardView;

    public ProdukAdapter(Context context, List<produk> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull ProdukAdapter.ViewHolder holder, int position) {
        produk produk = list.get(position);
//        holder.textId.setText(produk.getId());
        holder.textTitle.setText(produk.getTitle());
        holder.textDeskripsi.setText(produk.getDeskripsi());
//        holder.textHarga.setText(produk.getHarga());
//        holder.textStock.setText(produk.getStock());
//        holder.textUrlGambar.setText(produk.getUrlGambar());
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textId, textTitle, textDeskripsi, textHarga, textStock, textUrlGambar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.titleProdukCard);
            textDeskripsi = itemView.findViewById(R.id.deskripsiProdukCard);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailProduk.class);
                    int position = getBindingAdapterPosition();
                    produk newObject = list.get(position);   // get clicked new object from news(news is an ArrayList)
                    intent.putExtra("id", newObject.id);
                    Context context = view.getContext();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            });
        }

    }
}