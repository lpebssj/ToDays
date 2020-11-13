package com.example.todays.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todays.EventListAdapter;
import com.example.todays.ToDays;
import com.example.todays.ViewModel.EventViewModel;
import com.example.todays.R;
import com.example.todays.Util.CommonUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ViewModelProvider viewModelProvider;
    private EventViewModel eventViewModel;
    private EventListAdapter eventListAdapter;
    private FloatingActionButton floatingActionButton;
    private Button buttonUP, buttonHome, buttonTIH;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init
        recyclerView = requireActivity().findViewById(R.id.recyclerView);
        viewModelProvider = new ViewModelProvider(requireActivity());
        eventViewModel = viewModelProvider.get(EventViewModel.class);
        eventListAdapter = new EventListAdapter(eventViewModel);
        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        buttonUP = requireActivity().findViewById(R.id.buttonUP);
        buttonHome = requireActivity().findViewById(R.id.buttonHome);
        buttonTIH = requireActivity().findViewById(R.id.buttonTIH);

        ToDays toDays = (ToDays) getActivity().getApplicationContext();

        recyclerView.setAdapter(eventListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //set fixed size of recycler view
        recyclerView.setHasFixedSize(true);
        eventViewModel.findEventByAccountId(toDays.getAccountId()).observe(getViewLifecycleOwner(), events -> {
            eventListAdapter.submitList(events);
        });

        floatingActionButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_home_to_addEvent2);
        });

        //change the appearances of buttons when pressed & set on click events of them
        buttonUP.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.button_round_corner);
                    NavController navController = CommonUtil.getNavController((AppCompatActivity) requireActivity());
                    navController.navigate(R.id.action_home_to_userProfile2);
                }
                return false;
            }
        });


        buttonHome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.button_round_corner);
                }
                return false;
            }
        });

        buttonTIH.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.button_round_corner);
                    NavController navController = CommonUtil.getNavController((AppCompatActivity) requireActivity());
                    navController.navigate(R.id.action_home_to_TIHFragment);

                }
                return false;
            }
        });


    }
}