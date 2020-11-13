package com.example.todays;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todays.ViewModel.TIHViewModel;
import com.example.todays.database.TIH;

public class TIHListAdapter extends ListAdapter<TIH, TIHListAdapter.TIHViewHolder> {


    public TIHListAdapter() {
        super(new DiffUtil.ItemCallback<TIH>() {
            //compare id of old event with id of new event to see if they are the same
            @Override
            public boolean areItemsTheSame(@NonNull TIH oldItem, @NonNull TIH newItem) {
                return oldItem.getId() == newItem.getId();
            }

            //compare contents of old and new events to see if they are the same
            @Override
            public boolean areContentsTheSame(@NonNull TIH oldItem, @NonNull TIH newItem) {
                return (oldItem.getDate().equals(newItem.getDate())
                        && oldItem.getContent().equals(newItem.getContent())
                        && oldItem.getYear().equals(newItem.getYear())
                );
            }
        });
    }


    @NonNull
    @Override
    public TIHViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.tih_event, parent, false);
        TIHViewHolder holder = new TIHViewHolder(itemView);
        return holder;
    }

    //display the events
    @Override
    public void onBindViewHolder(@NonNull TIHViewHolder holder, int position) {
        TIH tih = getItem(position);
        holder.itemView.setTag(R.id.tih_for_view_holder, tih);
        holder.textViewMatter.setText(tih.getContent());
        holder.textViewPremise.setText(tih.getYear());
    }


    static class TIHViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPremise, textViewMatter;
        ImageButton buttonShare;

        public TIHViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPremise = itemView.findViewById(R.id.textViewPremise);
            textViewMatter = itemView.findViewById(R.id.textViewMatter);
            buttonShare = itemView.findViewById(R.id.buttonShare);
        }
    }


}
