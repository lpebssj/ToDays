package com.example.todays.Fragment;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Build;
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

import com.example.todays.ViewModel.EventViewModel;
import com.example.todays.database.Account;
import com.example.todays.ViewModel.AccountViewModel;
import com.example.todays.R;
import com.example.todays.database.Event;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegisterFragment extends Fragment {


    private EditText editTextUsernameR, editTextPasswordR, editTextPasswordR3;
    private Button buttonRegister;
    private AccountViewModel accountViewModel;
    private View view;
    private InputMethodManager inputMethodManager ;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.register_fragment, container, false);
        return view;    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editTextUsernameR = requireActivity().findViewById(R.id.editTextUsernameR);
        editTextPasswordR = requireActivity().findViewById(R.id.editTextPasswordR);
        editTextPasswordR3 = requireActivity().findViewById(R.id.editTextPasswordR3);
        buttonRegister = requireActivity().findViewById(R.id.buttonRegister1);
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        accountViewModel = viewModelProvider.get(AccountViewModel.class);
        EventViewModel eventViewModel = viewModelProvider.get(EventViewModel.class);
        inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


        buttonRegister.setOnClickListener(v -> {
            String userName = editTextUsernameR.getText().toString();
            String passWord = editTextPasswordR.getText().toString();
            String passWord2 = editTextPasswordR3.getText().toString();
            if (passWord.equals(passWord2)){
                LiveData<Account> accountsWithPattern = accountViewModel.findAccountsWithPattern(userName);
                accountsWithPattern.observe(getViewLifecycleOwner(), account -> {
                    if (account == null){
                        Map<String , Integer> map = new HashMap<>();
                        new Thread(()->{
                            List<Account> allAccounts = accountViewModel.getAllAccounts();
                            if (allAccounts!=null){
                                map.put("id",allAccounts.get(0).getId()+1);
                            } else {
                                map.put("id", 1);
                            }
                            LocalDate now = LocalDate.now();
                            String year = String.valueOf(now.getYear());
                            String month = String.valueOf(now.getMonthValue());
                            String day = String.valueOf(now.getDayOfMonth());
                            String date = year + "–" + month + "–" + day;
                            Event event = new Event("First time use", date,"No reminder", "Once", null);
                            event.setAccountId(String.valueOf(map.get("id")));
                            eventViewModel.insertEvents(event);
                        }).start();

                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.CANADA);
                        account = new Account(userName, passWord, LocalDate.now().format(dateTimeFormatter));
                        accountViewModel.insertAccounts(account);


                        Toast.makeText(getContext(),"Registered successfully!",Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_registerFragment_to_loginFragment);

                    } else {
                        Toast.makeText(getContext(),"There's already an existing username",Toast.LENGTH_SHORT).show();
                    }
                });


            } else {
                Toast.makeText(getContext(),"Two passwords do not match",Toast.LENGTH_SHORT).show();
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
        if (v.hasFocus() && v.getWindowToken() != null) {
            v.clearFocus();
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



}