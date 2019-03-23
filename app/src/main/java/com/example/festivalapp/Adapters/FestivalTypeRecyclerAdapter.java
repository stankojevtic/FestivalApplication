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

public class FestivalTypeRecyclerAdapter extends RecyclerView.Adapter<FestivalTypeRecyclerAdapter.FestivalTypeViewHolder> {

    private List<DummyContent.DummyItem> list;
    private OnFestivalTypeClickListener listener;

    public FestivalTypeRecyclerAdapter(List<DummyContent.DummyItem> list, OnFestivalTypeClickListener listener)
    {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FestivalTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.festival_type_text_view_layout, parent, false);

            FestivalTypeViewHolder festivalTypeViewHolder = new FestivalTypeViewHolder(textView, listener);

            return festivalTypeViewHolder;
    }

    @Override
    public void onBindViewHolder(FestivalTypeViewHolder holder, int i) {
            holder.FestivalType.setText(list.get(i).content);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class FestivalTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView FestivalType;
        OnFestivalTypeClickListener listener;
        public FestivalTypeViewHolder(@NonNull TextView itemView, OnFestivalTypeClickListener listener) {
            super(itemView);
            FestivalType = itemView;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnFestivalTypeClickListener {
        void onItemClick(int position);
    }
}
