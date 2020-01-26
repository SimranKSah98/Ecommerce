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
import com.example.myapplication.pojo.ProductsItem;

import java.util.List;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.ViewHolder> {

    List<ProductsItem> productsItems;
    private CategoryProductAdapter.OnCardListener onCardListener;


    public CategoryProductAdapter(List<ProductsItem> productsItems, CategoryProductAdapter.OnCardListener onCardListener) {
        this.productsItems = productsItems;
        this.onCardListener = onCardListener;
    }

    @NonNull
    @Override
    public CategoryProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryProductAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false), onCardListener);

    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryProductAdapter.ViewHolder holder, int position) {
        final ProductsItem productsItem = productsItems.get(position);
        Glide.with(holder.imageView.getContext()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).load(productsItem.getImage()).into(holder.imageView);
        holder.productName.setText(productsItem.getName());
        holder.productPrice.setText(String.valueOf(productsItem.getPrice()));
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public ImageView imageView;
        public TextView productPrice;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView,final CategoryProductAdapter.OnCardListener onCardListener) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);
            productPrice=itemView.findViewById(R.id.home_product_price);
            cardView = itemView.findViewById(R.id.home_popular_products);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCardListener.onCardClick(productsItems.get(getAdapterPosition()).getId());
                }
            });
        }
    }
    public interface OnCardListener {
        void onCardClick(String id);
    }
}
