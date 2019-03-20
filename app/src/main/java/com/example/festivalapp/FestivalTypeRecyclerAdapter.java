package com.example.festivalapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.festivalapp.dummy.DummyContent;

import org.w3c.dom.Text;

import java.util.List;

public class FestivalTypeRecyclerAdapter extends RecyclerView.Adapter<FestivalTypeRecyclerAdapter.FestivalTypeViewHolder> {

    private List<DummyContent.DummyItem> list;

    public FestivalTypeRecyclerAdapter(List<DummyContent.DummyItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public FestivalTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.festival_type_text_view_layout, parent, false);

            FestivalTypeViewHolder festivalTypeViewHolder = new FestivalTypeViewHolder(textView);

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

    public static class FestivalTypeViewHolder extends RecyclerView.ViewHolder {
        TextView FestivalType;
        public FestivalTypeViewHolder(@NonNull TextView itemView) {
            super(itemView);
            FestivalType = itemView;
        }
    }
}
