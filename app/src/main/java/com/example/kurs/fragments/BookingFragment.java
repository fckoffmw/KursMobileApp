package com.example.kurs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kurs.R;

public class BookingFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";
    private static final String ARG_MOVIE_TITLE = "movie_title";

    private int movieId;
    private String movieTitle;

    public static BookingFragment newInstance(int movieId, String movieTitle) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        args.putString(ARG_MOVIE_TITLE, movieTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public BookingFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_MOVIE_ID);
            movieTitle = getArguments().getString(ARG_MOVIE_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        TextView textMovieTitle = view.findViewById(R.id.textMovieTitle);
        Spinner spinnerTime = view.findViewById(R.id.spinnerTime);
        Spinner spinnerHall = view.findViewById(R.id.spinnerHall);
        Button buttonBook = view.findViewById(R.id.buttonBook);

        // Устанавливаем настоящее название фильма
        textMovieTitle.setText(movieTitle);

        String[] times = {"12:00", "15:00", "18:00", "21:00"};
        String[] halls = {"Зал 1", "Зал 2", "Зал 3"};

        spinnerTime.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, times));
        spinnerHall.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, halls));

        buttonBook.setText("Купить билет"); // изменяем текст кнопки

        buttonBook.setOnClickListener(v -> {
            String selectedTime = spinnerTime.getSelectedItem().toString();
            String selectedHall = spinnerHall.getSelectedItem().toString();

            PurchaseFragment purchaseFragment = PurchaseFragment.newInstance(
                    movieId, movieTitle, selectedTime, selectedHall);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, purchaseFragment)
                    .addToBackStack(null)
                    .commit();
        });

    }
}
