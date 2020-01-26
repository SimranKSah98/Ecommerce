package com.example.myapplication.adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.pojo.MerchantListItem;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.ViewHolder> {

    List<MerchantListItem> merchantListItemList;
    private OtherMerchantListener otherMerchantListener;


    public MerchantAdapter(List<MerchantListItem> merchantListItemList,OtherMerchantListener otherMerchantListener) {
        this.merchantListItemList = merchantListItemList;
        this.otherMerchantListener=otherMerchantListener;
    }

    @NonNull
    @Override
    public MerchantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MerchantAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MerchantAdapter.ViewHolder holder, int position) {
        holder.name.setText(merchantListItemList.get(position).getMerchantName());
        holder.price.setText(merchantListItemList.get(position).getPrice());
        holder.rating.setText(merchantListItemList.get(position).getMerchantRating());

//        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.activity", MODE_PRIVATE);
//        String customerId=sharedPreferences.getString("customerId","");
//        otherMerchantListener.onMerchnatClick(merchantListItemList.get(holder.getAdapterPosition()).ge);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherMerchantListener.onMerchnatClick(merchantListItemList.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return merchantListItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, rating;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.other_merchant_name);
            price = itemView.findViewById(R.id.other_merchant_product_price);
            rating = itemView.findViewById(R.id.rating);
            cardView=itemView.findViewById(R.id.merchant_card_view);
        }
    }


    public interface OtherMerchantListener {
        void onMerchnatClick(MerchantListItem merchantListItem);
    }


}