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
    OrderHistoryAdapter.OnCardListener onCardListener;

    public OrderHistoryAdapter(List<OrderHistory> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHistoryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_recycleview, parent, false), onCardListener);


    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, int position) {
        holder.orderId.setText(orderHistoryList.get(holder.getAdapterPosition()).getOrderId());
        Glide.with(holder.imageView.getContext()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).load(orderHistoryList.get(holder.getAdapterPosition()).getProductImage()).into(holder.imageView);
        holder.productName.setText(orderHistoryList.get(holder.getAdapterPosition()).getProductName());
        holder.productPrice.setText(String.valueOf(orderHistoryList.get(holder.getAdapterPosition()).getProductPrice()));
        holder.orderdate.setText(orderHistoryList.get(holder.getAdapterPosition()).getOrderDate());



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView productName, productPrice,orderId,orderdate;
        public ImageView imageView;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView, final OrderHistoryAdapter.OnCardListener onCardListener) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_view);
            productPrice = itemView.findViewById(R.id.home_product_price);
            orderId=itemView.findViewById(R.id.orderid);
            orderdate=itemView.findViewById(R.id.orderdate);
            imageView = itemView.findViewById(R.id.image_view);
            cardView = itemView.findViewById(R.id.home_popular_products);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCardListener.onCardClick(orderHistoryList.get(getAdapterPosition()).getProductId());
                }
            });
        }
    }

    public interface OnCardListener {
        void onCardClick(String id);
    }
}
