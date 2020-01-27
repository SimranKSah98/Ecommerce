package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.activity.LoginHistoryActivity;
import com.example.myapplication.pojo.LoginHistory;
import com.example.myapplication.pojo.OrderHistory;

import java.util.List;

public class LoginHistoryAdapter extends RecyclerView.Adapter<LoginHistoryAdapter.ViewHolder> {
    List<LoginHistory> loginHistoryList;

    public LoginHistoryAdapter(List<LoginHistory> loginHistoryList) {
        this.loginHistoryList = loginHistoryList;
    }

    @NonNull
    @Override
    public LoginHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new LoginHistoryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loginhistory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.time.setText(loginHistoryList.get(holder.getAdapterPosition()).getTimeStamp());
    }


    @Override
    public int getItemCount() {
        return loginHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.textView2);
            linearLayout=itemView.findViewById(R.id.linearlogin);

        }
    }
}
