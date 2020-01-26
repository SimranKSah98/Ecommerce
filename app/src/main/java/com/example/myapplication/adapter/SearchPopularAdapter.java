package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.pojo.SearchResponse;

import java.util.List;

public class SearchPopularAdapter extends RecyclerView.Adapter<SearchPopularAdapter.ViewHolder> {

    List<SearchResponse> searchResponses;
    OnCardListener onCardListener;

    public SearchPopularAdapter(List<SearchResponse> searchResponses, OnCardListener onCardListener) {
        this.searchResponses = searchResponses;
        this.onCardListener = onCardListener;
    }

    @NonNull
    @Override
    public SearchPopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false), onCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchPopularAdapter.ViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).load(searchResponses.get(holder.getAdapterPosition()).getProductImage()).into(holder.imageView);
        holder.productName.setText(searchResponses.get(holder.getAdapterPosition()).getProductName());
        holder.productPrice.setText(String.valueOf(searchResponses.get(holder.getAdapterPosition()).getProductPrice()));
    }

    @Override
    public int getItemCount() {
        return searchResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView productName, productPrice;
        public ImageView imageView;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView, final OnCardListener onCardListener) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_view);
            productPrice = itemView.findViewById(R.id.home_product_price);
            imageView = itemView.findViewById(R.id.image_view);
            cardView = itemView.findViewById(R.id.home_popular_products);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCardListener.onCardClick(searchResponses.get(getAdapterPosition()).getProductId());
                }
            });
        }
    }

    public interface OnCardListener {
        void onCardClick(String id);
    }
}

