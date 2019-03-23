package com.example.festivalapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.festivalapp.R;
import com.example.festivalapp.dummy.DummyContent;

import java.util.List;

public class FestivalItemRecyclerAdapter extends RecyclerView.Adapter<FestivalItemRecyclerAdapter.FestivalItemViewHolder> {

    private List<DummyContent.DummyItem> list;
    private OnFestivalItemClickListener listener;

    public FestivalItemRecyclerAdapter(List<DummyContent.DummyItem> list, OnFestivalItemClickListener listener)
    {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FestivalItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.festival_item_text_view_layout, parent, false);

        FestivalItemViewHolder festivalItemViewHolder = new FestivalItemViewHolder(textView, listener);

        return festivalItemViewHolder;
    }

    @Override
    public void onBindViewHolder(FestivalItemViewHolder holder, int i) {
        holder.FestivalItem.setText(list.get(i).content);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class FestivalItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView FestivalItem;
        OnFestivalItemClickListener listener;
        public FestivalItemViewHolder(@NonNull TextView itemView, OnFestivalItemClickListener listener) {
            super(itemView);
            FestivalItem = itemView;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnFestivalItemClickListener {
        void onItemClick(int position);
    }
}
