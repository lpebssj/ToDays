package com.example.todays.Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.todays.ViewModel.AccountViewModel;
import com.example.todays.ViewModel.EventViewModel;
import com.example.todays.R;
import com.example.todays.ToDays;
import com.example.todays.Util.CommonUtil;
import com.example.todays.database.Account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * User profile page
 */
public class UserProfileFragment extends Fragment {

    private Button buttonUP2, buttonHome2, buttonTIH2;
    private TextView eventNum, beenUsedNum, shareNum, textViewAccountName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init
        buttonUP2 = requireActivity().findViewById(R.id.buttonUP2);
        buttonHome2 = requireActivity().findViewById(R.id.buttonHome2);
        buttonTIH2 = requireActivity().findViewById(R.id.buttonTIH2);
        eventNum = requireActivity().findViewById(R.id.event_num);
        beenUsedNum = requireActivity().findViewById(R.id.beenUsed_num);
        shareNum = requireActivity().findViewById(R.id.share_num);
        textViewAccountName = requireActivity().findViewById(R.id.textViewAccountName);
        ToDays toDays = (ToDays) getActivity().getApplicationContext();
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        AccountViewModel accountViewModel = viewModelProvider.get(AccountViewModel.class);
        EventViewModel eventViewModel = viewModelProvider.get(EventViewModel.class);
        LiveData<Account> account = accountViewModel.findAccountById(toDays.getAccountId());
        LiveData<Integer> eventNumWithAccount = eventViewModel.getEventNum(toDays.getAccountId());

        //calculate and display number of days since the account had been registered
        account.observe(getViewLifecycleOwner(), account1 -> {
            textViewAccountName.setText(account1.getUsername());  //display username
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CANADA);
            long between = ChronoUnit.DAYS.between(LocalDate.parse(account1.getCreateTime(),dateTimeFormatter), LocalDate.now());
            beenUsedNum.setText(String.valueOf(between));
            shareNum.setText(account1.getNumShare());
        });
        eventNumWithAccount.observe(getViewLifecycleOwner(), integer -> eventNum.setText(String.valueOf(integer)));

        //change the appearances of buttons when pressed & set on click events of them
        buttonUP2.setOnTouchListener(new View.OnTouchListener() {
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

        buttonHome2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.button_round_corner);
                    NavController navController = CommonUtil.getNavController((AppCompatActivity) requireActivity());
                    navController.navigate(R.id.action_userProfile2_to_home);
                }
                return false;
            }
        });

        buttonTIH2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.button_round_corner);
                    NavController navController = CommonUtil.getNavController((AppCompatActivity) requireActivity());
                    navController.navigate(R.id.action_userProfile_to_TIHFragment);
                }
                return false;
            }
        });


/*
        //there will be no login button anymore (temporarily)
        buttonLogin.setOnTouchListener(new View.OnTouchListener() {
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
        buttonLogin.setOnClickListener(v -> {
            NavController navController = CommonUtil.getNavController((AppCompatActivity) requireActivity());
            navController.navigate(R.id.action_userProfile_to_loginFragment);
        });*/
    }
}