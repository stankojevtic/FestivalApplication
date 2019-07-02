package com.example.festivalapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.festivalapp.Helpers.BitmapConvertor;
import com.example.festivalapp.Models.Festival;
import com.example.festivalapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminFestivalItemRecyclerAdapter extends RecyclerView.Adapter<AdminFestivalItemRecyclerAdapter.AdminFestivalItemViewHolder> implements Filterable {

    private List<Festival> list;
    private List<Festival> listFull;
    private OnFestivalItemClickListener listener;

    public AdminFestivalItemRecyclerAdapter(List<Festival> list, OnFestivalItemClickListener listener)
    {
        this.list = list;
        listFull = new ArrayList<>(list);
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminFestivalItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_festival_item_view_layout, parent, false);

        AdminFestivalItemViewHolder festivalItemViewHolder = new AdminFestivalItemViewHolder(view, listener);

        return festivalItemViewHolder;
    }

    @Override
    public void onBindViewHolder(AdminFestivalItemViewHolder holder, int i) {
        holder.feistlvaItemName.setText(list.get(i).getName());
        holder.feistlvaItemImage.setImageBitmap(BitmapConvertor.StringToBitMap(list.get(i).getImage()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AdminFestivalItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView feistlvaItemName;
        CircleImageView feistlvaItemImage;
        RelativeLayout feistlvaItemLayout;
        OnFestivalItemClickListener listener;
        ImageView festivalItemDeleteImage;

        public AdminFestivalItemViewHolder(@NonNull View itemView, final OnFestivalItemClickListener listener) {
            super(itemView);
            feistlvaItemImage = itemView.findViewById(R.id.admin_festival_item_rv_image);
            feistlvaItemName = itemView.findViewById(R.id.admin_festival_item_rv_username);
            feistlvaItemLayout = itemView.findViewById(R.id.admin_festival_item_rv_layout);
            festivalItemDeleteImage = itemView.findViewById(R.id.admin_festival_item_rv_delete);
            this.listener = listener;
            itemView.setOnClickListener(this);
            festivalItemDeleteImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnFestivalItemClickListener {
        void onItemClick(int position);
        void onDeleteClick (int position);
    }

    @Override
    public Filter getFilter() {
        return festivalItemsFilter;
    }

    private Filter festivalItemsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Festival> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(listFull);
            } else {
                String searchFilter = constraint.toString().toLowerCase().trim();
                for(Festival festival : listFull)
                {
                    if(festival.getName().toLowerCase().contains(searchFilter) ||
                            festival.getDescription().toLowerCase().contains(searchFilter) ||
                            festival.getAddress().toLowerCase().contains(searchFilter)){
                        filteredList.add(festival);
                    }
                }
            }
            FilterResults result = new FilterResults();
            result.values = filteredList;
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }
}
