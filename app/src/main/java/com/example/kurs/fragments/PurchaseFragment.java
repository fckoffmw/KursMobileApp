package com.example.kurs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kurs.R;


public class PurchaseFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";
    private static final String ARG_MOVIE_TITLE = "movie_title";
    private static final String ARG_TIME = "time";
    private static final String ARG_HALL = "hall";

    public static PurchaseFragment newInstance(int movieId, String movieTitle, String time, String hall) {
        PurchaseFragment fragment = new PurchaseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        args.putString(ARG_MOVIE_TITLE, movieTitle);
        args.putString(ARG_TIME, time);
        args.putString(ARG_HALL, hall);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        TextView textMovie = view.findViewById(R.id.textMovie);
        TextView textSession = view.findViewById(R.id.textSession);
        NumberPicker ticketPicker = view.findViewById(R.id.ticketPicker);
        Button buttonBuy = view.findViewById(R.id.buttonBuyTicket);

        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString(ARG_MOVIE_TITLE);
            String time = args.getString(ARG_TIME);
            String hall = args.getString(ARG_HALL);

            textMovie.setText("Фильм: " + title);
            textSession.setText("Сеанс: " + time + " — " + hall);
        }

        ticketPicker.setMinValue(1);
        ticketPicker.setMaxValue(10);


        buttonBuy.setOnClickListener(v -> {
            int count = ticketPicker.getValue();
            Toast.makeText(getContext(),
                    "Покупка завершена: " + count + " билет(ов)",
                    Toast.LENGTH_LONG).show();
        });
    }
}
