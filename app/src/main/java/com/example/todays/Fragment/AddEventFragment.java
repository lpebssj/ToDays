package com.example.todays.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todays.R;
import com.example.todays.ThemeListAdapter;
import com.example.todays.ToDays;
import com.example.todays.Util.CommonUtil;
import com.example.todays.ViewModel.EventViewModel;
import com.example.todays.database.Event;


/**
 * Event creating page
 */
public class AddEventFragment extends Fragment {

    private EditText editTextEventName, editTextDate;
    private View view;
    private InputMethodManager inputMethodManager;
    private DatePickerDialog datePickerDialog;
    private Button buttonSave, buttonSave2, buttonDeleteEvent;
    private ImageButton imageButton, imageButton2;
    private TextView textViewRepeat2, textViewRemind2, textViewCreateEvent;
    private EventViewModel eventViewModel;
    private RecyclerView recyclerView3;
    private ThemeListAdapter themeListAdapter;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_event, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init
        editTextDate = requireActivity().findViewById(R.id.editTextDate);
        editTextEventName = requireActivity().findViewById(R.id.editTextEventName);
        textViewCreateEvent = requireActivity().findViewById(R.id.textViewCreateEvent);
        buttonSave2 = requireActivity().findViewById(R.id.buttonSave2);
        buttonDeleteEvent = requireActivity().findViewById(R.id.buttonDeleteEvent);
        inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        textViewRepeat2 = requireActivity().findViewById(R.id.textViewRepeat2);
        textViewRemind2 = requireActivity().findViewById(R.id.textViewRemind2);
        imageButton = requireActivity().findViewById(R.id.imageButton);
        imageButton2 = requireActivity().findViewById(R.id.imageButton2);
        ImageButton buttonBack = requireActivity().findViewById(R.id.buttonBack);
        buttonBack.setBackgroundResource(R.drawable.ic_baseline_arrow_back_released);
        buttonSave = requireActivity().findViewById(R.id.buttonSave);
        buttonSave.setBackgroundResource(R.drawable.button_round_corner);

        ToDays toDays = (ToDays) getActivity().getApplicationContext();


        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        eventViewModel = viewModelProvider.get(EventViewModel.class);


        recyclerView3 = requireActivity().findViewById(R.id.recyclerView3);
        themeListAdapter = new ThemeListAdapter();
        recyclerView3.setAdapter(themeListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView3.setLayoutManager(linearLayoutManager);

        //change event creating page to event editing page
        String eventId = null;

        if (getArguments() != null) {
            eventId = (String) getArguments().get("id");
            LiveData<Event> event = eventViewModel.findEventById(eventId);
            event.observe(getViewLifecycleOwner(), event1 -> {
                textViewCreateEvent.setText("Edit Event");
                editTextDate.setText(event1.getDate());
                editTextEventName.setText(event1.getName());
                textViewRepeat2.setText(event1.getRepeat());
                textViewRemind2.setText(event1.getRemind());
                themeListAdapter.savedPictureName = event1.getThemeId();
                themeListAdapter.notifyDataSetChanged();  //update the theme list
            });
        }

        //list default background images
        toDays.picturePool.observe(getViewLifecycleOwner(), themes1 -> {
            themeListAdapter.submitList(themes1);
        });

        //back to home
        buttonBack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigateUp();
        });

        //change the appearance of buttonBack when pressed (using switch)
        buttonBack.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundResource(R.drawable.ic_baseline_arrow_back_pressed);
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    v.setBackgroundResource(R.drawable.ic_baseline_arrow_back_released);
                    break;
                default:
                    break;
            }
            return true;
        });

        //change the appearance of buttonSave when pressed (using if)
        String finalId = eventId;
        buttonSave.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.button_round_corner_pressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.button_round_corner);
                    if (finalId == null) {
                        saveEvent(v);
                    }else{
                        updateEvent(v, finalId);
                    }
                }
                return false;
            }
        });

        //save event info to ViewModel (compile both 2 save buttons)
        buttonSave2.setOnClickListener(v -> {
            if (finalId == null){
                saveEvent(v);
            }else{
                updateEvent(v, finalId);
            }

        });

        //hide keyboard and the blinking cursor when not using EditText
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideInput(v);
                }
                return false;
            }
        });

        //edit date of the event
        editTextDate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                hideInput(v);  //hide keyboard
                showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //show the date picked on editText
                        editTextDate.setText(year + "–" + (month + 1) + "–" + day + "");
                    }
                }, editTextDate.getText().toString());

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            return false;
        });



        //set period of repetition
        imageButton.setOnClickListener(v -> {
            String[] repeat = {"Once", "Every year", "Every season", "Every month"};
            showAlertDialog(repeat, textViewRepeat2);
        });

        //set reminder
        imageButton2.setOnClickListener(v -> {
            String[] remind = {"No reminder", "On that day", "1 day before", "2 days before", "1 week before", "1 month before"};
            showAlertDialog(remind, textViewRemind2);
        });


    }

    //set date picker
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDatePickDialog(DatePickerDialog.OnDateSetListener listener, String curDate) {
        Calendar calendar = Calendar.getInstance();
        int year, month, day;
        try {
            //get user input date and give value to variables
            year = Integer.parseInt(curDate.substring(0, curDate.indexOf("–")));
            month = Integer.parseInt(curDate.substring(curDate.indexOf("–") + 1, curDate.lastIndexOf("–"))) - 1;
            day = Integer.parseInt(curDate.substring(curDate.lastIndexOf("–") + 1));
        } catch (Exception e) {
            e.printStackTrace();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(requireContext(), DatePickerDialog.THEME_HOLO_DARK,
                    listener, year, month, day);
        }
        datePickerDialog.show();
    }

    protected void hideInput(View v) {
        if (v.hasFocus() && v.getWindowToken() != null) {
            v.clearFocus();
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //set pop-up selection bar
    private void showAlertDialog(String[] items, TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), AlertDialog.THEME_HOLO_DARK);
        final String[] str = {null};
        builder.setSingleChoiceItems(items, 0, (dialog, which) -> {
            ListView lw = ((AlertDialog) dialog).getListView();
            Object checkedItem = lw.getAdapter().getItem(which);
            // make dialog disappear after choosing an item
            textView.setText(checkedItem.toString());
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //save event when method is called
    private void saveEvent(View v) {
        String eventName = editTextEventName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        Event event = new Event(eventName, date, textViewRepeat2.getText().toString(), textViewRemind2.getText().toString(), null);
        ToDays toDays = (ToDays) getActivity().getApplicationContext();
        event.setAccountId(toDays.getAccountId());
        event.setThemeId(themeListAdapter.checkedPictureName);
        eventViewModel.insertEvents(event);
        NavController navController = CommonUtil.getNavController((AppCompatActivity) requireActivity());
        navController.navigateUp();
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    //update event when method is called
    private void updateEvent(View v, String id) {
        String eventName = editTextEventName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        Event event = new Event(eventName, date, textViewRepeat2.getText().toString(), textViewRemind2.getText().toString(), null);
        event.setId(Integer.parseInt(id));
        event.setThemeId(themeListAdapter.checkedPictureName);
        ToDays toDays = (ToDays) getActivity().getApplicationContext();
        event.setAccountId(toDays.getAccountId());
        eventViewModel.updateEvents(event);
        NavController navController = Navigation.findNavController(v);
        navController.navigateUp();
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}