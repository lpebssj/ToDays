package com.example.todays;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todays.Util.CommonUtil;
import com.example.todays.Util.ItemSlideHelper;
import com.example.todays.ViewModel.EventViewModel;
import com.example.todays.database.Event;

public class EventListAdapter extends ListAdapter<Event, EventListAdapter.EventListViewHolder> implements ItemSlideHelper.Callback{

    private RecyclerView mRecyclerView;
    private EventViewModel eventViewModel;


    public EventListAdapter(EventViewModel eventViewModel) {
        super(new DiffUtil.ItemCallback<Event>() {
            //compare id of old event with id of new event to see if they are the same
            @Override
            public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                return oldItem.getId() == newItem.getId();
            }

            //compare contents of old and new events to see if they are the same
            @Override
            public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                return (oldItem.getDate().equals(newItem.getDate())
                        && oldItem.getName().equals(newItem.getName())
                        && oldItem.getRemind().equals(newItem.getRemind())
                        && oldItem.getRepeat().equals(newItem.getRepeat())
//                        && oldItem.getTheme().equals(newItem.getTheme())
                );
            }

        });

        this.eventViewModel = eventViewModel;
    }

    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.event_card, parent, false);

        EventListViewHolder holder = new EventListViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //navigate to image composing page if an event is selected
            @Override
            public void onClick(View v) {
                Event event = (Event) holder.itemView.getTag(R.id.event_for_view_holder);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(event.getId()));

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_home_to_composeFragment,bundle);
            }
        });

        //navigate to event editing page
        holder.imageButtonEdit.setOnClickListener(v -> {
            Event event = (Event) holder.itemView.getTag(R.id.event_for_view_holder);
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(event.getId()));
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_home_to_addEvent2, bundle);

        });

        //delete event
        holder.imageButtonDelete.setOnClickListener(v -> {
            Event event = (Event) holder.itemView.getTag(R.id.event_for_view_holder);
            eventViewModel.deleteEvents(event);
            notifyItemChanged(holder.getAdapterPosition());
        });

        return holder;

    }

    //display events on the homepage
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull EventListViewHolder holder, int position) {
        Event event = getItem(position);
        holder.itemView.setTag(R.id.event_for_view_holder, event);
        holder.textViewEventName.setText(event.getName());
        holder.textViewEventDate.setText(event.getDate());
        long days = CommonUtil.calculateDays(event.getDate());
        holder.textViewNumDay.setText(Long.toString(Math.abs(days)));
        if (days < 0){
            holder.textViewDaysToEvent.setText("DAYS BEFORE");
        } else {
            holder.textViewDaysToEvent.setText("DAYS AFTER");
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
    }

    //decide the horizontal moving range for the event bar
    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            //viewGroup contains 3 widgets (main item, edit, delete), return width of edit button + width of delete button
            return viewGroup.getChildAt(1).getLayoutParams().width
                    + viewGroup.getChildAt(2).getLayoutParams().width;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);

    }


    static class EventListViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEventName, textViewEventDate, textViewNumDay, textViewDaysToEvent;
        ImageButton imageButtonEdit, imageButtonDelete;

        public EventListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEventName = itemView.findViewById(R.id.textViewEventName);
            textViewEventDate = itemView.findViewById(R.id.textViewEventDate);
            textViewNumDay = itemView.findViewById(R.id.textViewNumDay);
            textViewDaysToEvent = itemView.findViewById(R.id.textViewDaysToEvent);
            imageButtonEdit = itemView.findViewById(R.id.imageButtonEdit);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);
        }
    }


}
