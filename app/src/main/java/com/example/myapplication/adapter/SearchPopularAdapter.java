package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.pojo.ProductsItem;
import com.example.myapplication.pojo.SearchResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchPopularAdapter extends RecyclerView.Adapter<SearchPopularAdapter.ViewHolder> {

    List<ProductsItem> productsItems;
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<SearchResponse> arraylist;
    List<SearchResponse> searchResponses;

    private OnCardListener onCardListener;

    public SearchPopularAdapter(Context context, List<SearchResponse> searchResponses) {
        mContext = context;
        this.searchResponses = searchResponses;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SearchResponse>();
        this.arraylist.addAll(this.searchResponses);
    }

    @NonNull
    @Override
    public SearchPopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false),onCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchPopularAdapter.ViewHolder holder, int position) {

        final ProductsItem productsItem=productsItems.get(position);
        Glide.with(holder.imageView.getContext()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).load(productsItem.getImage()).into(holder.imageView);
        holder.productName.setText(productsItem.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCardListener.onCardClick(productsItems.get(holder.getAdapterPosition()).getId());

            }
        });



    }

    @Override
    public int getItemCount() {
        return productsItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView productName,productPrice;
        public ImageView imageView;
        public LinearLayout cardView;

        public ViewHolder(@NonNull View itemView, final OnCardListener onCardListener) {
            super(itemView);

            productName = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);
            cardView=itemView.findViewById(R.id.home_popular_products);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCardListener.onCardClick(productsItems.get(getAdapterPosition()).getId());
                }
            });

        }

    }


    public  interface OnCardListener
    {

        void onCardClick(String id);

    }





}

