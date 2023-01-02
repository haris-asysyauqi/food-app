package com.haris.food_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    private Context context;
    private List<Stock> list;
    private CardView cardView;

    public StockAdapter(Context context, List<Stock> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull StockAdapter.ViewHolder holder, int position) {
        Stock stock = list.get(position);
//        holder.textId.setText(produk.getId());
        holder.titleHistory.setText(stock.getTitle());
        holder.totalHistory.setText(stock.getStock());
//        holder.textHarga.setText(produk.getHarga());
//        holder.textStock.setText(produk.getStock());
//        holder.textUrlGambar.setText(produk.getUrlGambar());
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView totalHistory, titleHistory, textDeskripsi, textHarga, textStock, textUrlGambar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleHistory = itemView.findViewById(R.id.titleHistory);
            totalHistory = itemView.findViewById(R.id.totalHistory);

        }

    }
}