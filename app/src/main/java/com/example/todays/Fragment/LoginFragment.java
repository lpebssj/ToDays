package com.example.todays.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todays.ToDays;
import com.example.todays.database.Account;
import com.example.todays.ViewModel.AccountViewModel;
import com.example.todays.R;

/*
 *Login page
 */
public class LoginFragment extends Fragment {

    private View view;
    private InputMethodManager inputMethodManager ;
    private EditText editTextUsername, editTextPassword;
    private AccountViewModel accountViewModel;



    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //init
        editTextUsername = requireActivity().findViewById(R.id.editTextUsername);
        editTextPassword = requireActivity().findViewById(R.id.editTextPassword);

        Button buttonLogin2 = requireActivity().findViewById(R.id.buttonLogin2);
        buttonLogin2.setBackgroundResource(R.drawable.button_round_corner);

        Button buttonRegister = requireActivity().findViewById(R.id.buttonRegister);
        buttonRegister.setBackgroundResource(R.drawable.button_round_corner);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        accountViewModel = viewModelProvider.get(AccountViewModel.class);



        //change the appearance of buttonLogin2 when pressed
        buttonLogin2.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideInput(view);
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.setBackgroundResource(R.drawable.button_round_corner);
                    String username = editTextUsername.getText().toString();
                    String password = editTextPassword.getText().toString();
                    //try to login, search for correspond account information
                    LiveData<Account> account = accountViewModel.findAccount(username, password);
                    account.observe(getViewLifecycleOwner(), account1 -> {
                        if (account1 == null) {
                            //the toast information doesn't show on some phone types (such as Pixel 3a API 30), couldn't figure out why
                            Toast.makeText(getContext(),"Incorrect username or password",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"You're now logged in!",Toast.LENGTH_SHORT).show();
                            ToDays toDays = (ToDays) getActivity().getApplicationContext();
                            toDays.setAccountId(String.valueOf(account1.getId()));
                            NavController navController = Navigation.findNavController(v);
                            navController.navigate(R.id.action_loginFragment_to_home);
                        }
                    });

                }
                return false;
            }
        });

        //change the appearance of buttonRegister when pressed
        buttonRegister.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundResource(R.drawable.button_round_corner);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_loginFragment_to_registerFragment);
                }
                return false;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideInput(v);
                }
                return false;
            }
        });
    }


    protected void hideInput(View v) {
        //if the view gets focus and is called
        if (v.hasFocus() && v.getWindowToken() != null) {
            v.clearFocus();  //clear its focus
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  //hide soft keyboard
        }
    }

}