package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import com.example.myapplication.pojo.SearchResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<SearchResponse> searchResponses = null;
    private ArrayList<SearchResponse> arraylist;

    public ListViewAdapter(Context context, List<SearchResponse> searchResponses) {
        mContext = context;
        this.searchResponses = searchResponses;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SearchResponse>();
        this.arraylist.addAll(this.searchResponses);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return searchResponses.size();
    }

    @Override
    public SearchResponse getItem(int position) {
        return searchResponses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(searchResponses.get(position).getProductName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchResponses.clear();
        if (charText.length() == 0) {
            searchResponses.addAll(arraylist);
        } else {
            for (SearchResponse wp : arraylist) {
                if (wp.getProductName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchResponses.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
