package com.example.todays.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.todays.MainActivity;
import com.example.todays.R;
import com.example.todays.ToDays;
import com.example.todays.Util.CommonUtil;
import com.example.todays.Util.ImageUtils;
import com.example.todays.ViewModel.EventViewModel;
import com.example.todays.database.Event;

import java.io.File;

public class ComposeFragment extends Fragment {

    private TextView textView2, textView3, textView4, textView5;
    private ImageButton imageButton4, imageButton5, imageButton6, imageButton7;
    View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_compose, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView2 = requireActivity().findViewById(R.id.textView2);
        textView3 = requireActivity().findViewById(R.id.textView3);
        textView4 = requireActivity().findViewById(R.id.textView4);
        textView5 = requireActivity().findViewById(R.id.textView5);
        imageButton4 = requireActivity().findViewById(R.id.imageButton4);
        imageButton5 = requireActivity().findViewById(R.id.imageButton5);
        imageButton6 = requireActivity().findViewById(R.id.imageButton6);
        imageButton7 = requireActivity().findViewById(R.id.imageButton7);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        EventViewModel eventViewModel = viewModelProvider.get(EventViewModel.class);

        ToDays toDays = (ToDays) getActivity().getApplicationContext();
        String accountId = toDays.getAccountId();

        String eventId;
        //show content of the page
        if (getArguments()!=null){
            eventId = (String) getArguments().get("id");
            LiveData<Event> event = eventViewModel.findEventById(eventId);
            event.observe(getViewLifecycleOwner(), event1 -> {
                long days = CommonUtil.calculateDays(event1.getDate());
                textView2.setText(event1.getName());
                textView3.setText(String.valueOf(Math.abs(days)));
                //change text depending on the event time
                if (days < 0){
                    textView4.setText("DAYS BEFORE");
                } else {
                    textView4.setText("DAYS AFTER");
                }
                textView5.setText(event1.getDate());  //event date
                String pictureName = event1.getThemeId();

                //set background image

                toDays.picturePool.observe(getViewLifecycleOwner(), themes
                        -> themes.forEach(theme -> {
                   if (theme.getName().equals(pictureName)){
                       Bitmap bitmap = BitmapFactory.decodeByteArray(theme.getTheme(), 0, theme.getTheme().length);
                       BitmapDrawable bd= new BitmapDrawable(getResources(), bitmap);
                       getView().setBackground(bd);
                   }
                }));

                DisplayMetrics metric = new DisplayMetrics();
                Point outSize = new Point();
                requireActivity().getDisplay().getRealSize(outSize);


                ImageButton button =  requireActivity().findViewById(R.id.imageButton5);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(()->{
                            String picPath = ImageUtils.viewSaveToImage(view,"ToDays");

                            Uri imageUri = FileProvider.getUriForFile(requireContext(), requireContext().getApplicationContext().getPackageName() + ".provider", new File(picPath));
                            Intent intent =  new Intent("com.example.todays.provider").setData(imageUri);
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                            intent.setType("image/*");
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            startActivity(Intent.createChooser(intent,"Share to "));
                        }).start();
                    }
                });

            });
        }
    }

}