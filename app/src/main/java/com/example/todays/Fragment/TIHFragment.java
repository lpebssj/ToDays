package com.example.todays.Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.todays.R;
import com.example.todays.TIHListAdapter;
import com.example.todays.ViewModel.TIHViewModel;
import com.example.todays.Util.CommonUtil;

import java.time.Year;

public class TIHFragment extends Fragment {

    private Button buttonUP3, buttonHome3, buttonTIH3;
    private TIHViewModel tihViewModel;
    private TIHListAdapter tihListAdapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.t_i_h_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init
        buttonUP3 = requireActivity().findViewById(R.id.buttonUP3);
        buttonHome3 = requireActivity().findViewById(R.id.buttonHome3);
        buttonTIH3 = requireActivity().findViewById(R.id.buttonTIH3);
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        tihViewModel = viewModelProvider.get(TIHViewModel.class);
        tihListAdapter = new TIHListAdapter();
        recyclerView = requireActivity().findViewById(R.id.recyclerView2);
        recyclerView.setAdapter(tihListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        tihViewModel.getAllEventsLive(Year.now().toString()).observe(getViewLifecycleOwner(),
                tihs -> tihListAdapter.submitList(tihs));
        //change the appearances of buttons when pressed & set on click events of them
        buttonUP3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.button_round_corner);
                    NavController navController = CommonUtil.getNavController((AppCompatActivity) requireActivity());
                    navController.navigate(R.id.action_TIHFragment_to_userProfile);
                }
                return false;
            }
        });


        buttonHome3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.button_round_corner);
                    NavController navController = CommonUtil.getNavController((AppCompatActivity) requireActivity());
                    navController.navigate(R.id.action_TIHFragment_to_home);
                }
                return false;
            }
        });


        buttonTIH3.setOnTouchListener(new View.OnTouchListener() {
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
    }

}