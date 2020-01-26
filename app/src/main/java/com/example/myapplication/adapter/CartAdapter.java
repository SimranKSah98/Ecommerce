package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.controller.APIInterface;
import com.example.myapplication.pojo.ProductsBoughtItem;
import com.example.myapplication.pojo.ProductsItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    List<ProductsBoughtItem> cartList;
    Cartpostion cartpostion;

    public CartAdapter(List<ProductsBoughtItem> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, int position) {
        final ProductsBoughtItem productsItem = cartList.get(position);
        Glide.with(holder.imageView.getContext()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_launcher_background)).load(productsItem.getImage()).into(holder.imageView);
        holder.name.setText(productsItem.getName());
        String price = String.valueOf(productsItem.getPrice());
        holder.price.setText(price);
        holder.quantity.setNumber(String.valueOf(productsItem.getQuantity()));
        holder.quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                cartpostion.onCardclick(cartList.get(holder.getAdapterPosition()), newValue, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name;
        public TextView price;
        public ElegantNumberButton quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cart_image_view);
            name = itemView.findViewById(R.id.cart_product_name);
            price = itemView.findViewById(R.id.cart_product_price);
            quantity = itemView.findViewById(R.id.cart_product_quantity);
        }
    }

    public interface Cartpostion {
        void onCardclick(ProductsBoughtItem item, int newvalue, int postion);
    }
}
