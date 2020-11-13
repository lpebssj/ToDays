package com.example.todays;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class ThemeListAdapter extends ListAdapter<Theme, ThemeListAdapter.ThemeViewHolder> {
    private int checkedPosition = -1;
    public String checkedPictureName = "";
    public String savedPictureName = "";


    public ThemeListAdapter() {
        super(new DiffUtil.ItemCallback<Theme>() {

            @Override
            public boolean areItemsTheSame(@NonNull Theme oldItem, @NonNull Theme newItem) {
                return oldItem.getName().equals(newItem.getName());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Theme oldItem, @NonNull Theme newItem) {
                return (Arrays.equals(oldItem.getTheme(), newItem.getTheme()))
                        && ((oldItem.getAccountId()==null && newItem.getAccountId()==null)
                        ||(oldItem.getAccountId().equals(newItem.getAccountId())));
            }
        });
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.theme_card, parent, false);
        ThemeViewHolder holder = new ThemeViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedPosition = holder.getAdapterPosition();  //send checked position
                holder.checked.setVisibility(View.VISIBLE);  //write setVisibility(View.VISIBLE) in onCLick() to avoid the check mark blinking
                notifyDataSetChanged(); //notifyDataSetChanged here will cause a rerun of onBindViewHolder()
            }
        });

        return holder;
    }

    //show the image list
    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        Theme item = getItem(position);
        byte[] miniTheme = item.getMiniTheme();  //haha this is the thumbnail
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(miniTheme, 0, miniTheme.length));

        if (position == checkedPosition || item.getName().equals(savedPictureName)) {
            holder.checked.setVisibility(View.VISIBLE);
            checkedPictureName = item.getName();
            savedPictureName = "";
        } else {
            holder.checked.setVisibility(View.INVISIBLE);
        }

    }


    static class ThemeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, checked;

        public ThemeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewTheme2);
            checked = itemView.findViewById(R.id.checked);
        }
    }
}
