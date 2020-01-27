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
import com.example.myapplication.pojo.OrderHistory;
import com.example.myapplication.pojo.SearchResponse;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    List<OrderHistory> orderHistoryList;
    OnCardListener onCardListener;

    public OrderHistoryAdapter(List<OrderHistory> orderHistoryList, OnCardListener onCardListener) {
        this.orderHistoryList = orderHistoryList;
        this.onCardListener=onCardListener;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHistoryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_recycleview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHistoryAdapter.ViewHolder holder, int position) {
        holder.orderId.setText(orderHistoryList.get(holder.getAdapterPosition()).getOrderId());
        Glide.with(holder.imageView.getContext()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).load(orderHistoryList.get(holder.getAdapterPosition()).getProductImage()).into(holder.imageView);
        holder.productName.setText(orderHistoryList.get(holder.getAdapterPosition()).getProductName());
        holder.productPrice.setText(String.valueOf(orderHistoryList.get(holder.getAdapterPosition()).getProductPrice()));
        holder.orderdate.setText(orderHistoryList.get(holder.getAdapterPosition()).getOrderDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardListener.onCardClick(orderHistoryList.get(holder.getAdapterPosition()).getProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice, orderId, orderdate;
        public ImageView imageView;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_view);
            productPrice = itemView.findViewById(R.id.home_product_price);
            orderId = itemView.findViewById(R.id.orderid);
            orderdate = itemView.findViewById(R.id.orderdate);
            imageView = itemView.findViewById(R.id.image_view);
            cardView = itemView.findViewById(R.id.home_popular_products);
        }
    }
    public interface OnCardListener {
        void onCardClick(String id);
    }
}
