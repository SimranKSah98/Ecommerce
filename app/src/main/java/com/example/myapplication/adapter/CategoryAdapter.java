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
import com.example.myapplication.pojo.CategoriesItem;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoriesItem> categoryList;
    private OnClickListener onClickListener;

    public CategoryAdapter(List<CategoriesItem> categoryList, OnClickListener onClickListener) {
        this.categoryList = categoryList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false), onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(categoryList.get(position).getName());
        Glide.with(holder.imageView.getContext()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)).load(categoryList.get(holder.getAdapterPosition()).getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private CardView linearLayout;

        ViewHolder(@NonNull View itemView, final OnClickListener onClickListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.categoryLayoutTextView);
            imageView = itemView.findViewById(R.id.categoryLayoutImageView);
            linearLayout = itemView.findViewById(R.id.categoryLayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(categoryList.get(getAdapterPosition()).getId(), categoryList.get(getAdapterPosition()).getName());
                }
            });
        }
    }

    public interface OnClickListener {
        void onClick(String categoryId, String categoryName);
    }
}