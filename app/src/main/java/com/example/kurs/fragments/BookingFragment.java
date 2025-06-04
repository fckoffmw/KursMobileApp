package com.example.kurs.fragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kurs.R;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";
    private static final String ARG_MOVIE_TITLE = "movie_title";

    private int movieId;
    private String movieTitle;

    private final int SEAT_PRICE = 350;
    private final int ROWS = 5;
    private final int COLUMNS = 6;

    private final List<String> selectedSeats = new ArrayList<>();
    private TextView textSummary;
    private Spinner spinnerTime, spinnerHall;

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
        TextView textMovieTitle = view.findViewById(R.id.textMovieTitle);
        spinnerTime = view.findViewById(R.id.spinnerTime);
        spinnerHall = view.findViewById(R.id.spinnerHall);
        GridLayout seatGrid = view.findViewById(R.id.seatGrid);
        Button buttonBook = view.findViewById(R.id.buttonBook);
        textSummary = view.findViewById(R.id.textSummary);

        textMovieTitle.setText(movieTitle);

        String[] times = {"12:00", "15:00", "18:00", "21:00"};
        String[] halls = {"Зал 1", "Зал 2", "Зал 3"};

        spinnerTime.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, times));
        spinnerHall.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, halls));

        generateSeatGrid(seatGrid);

        buttonBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        buttonBook.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(getContext(), "Выберите хотя бы одно место", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedTime = spinnerTime.getSelectedItem().toString();
            String selectedHall = spinnerHall.getSelectedItem().toString();
            int totalPrice = selectedSeats.size() * SEAT_PRICE;

            PurchaseFragment purchaseFragment = PurchaseFragment.newInstance(
                    movieId, movieTitle, selectedTime, selectedHall, totalPrice);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, purchaseFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void generateSeatGrid(GridLayout gridLayout) {
        int gray = Color.GRAY;
        gridLayout.removeAllViews();
        for (int i = 0; i < ROWS * COLUMNS; i++) {
            final int row = i / COLUMNS + 1;
            final int col = i % COLUMNS + 1;
            final String seatId = "Ряд " + row + ", Место " + col;

            TextView seat = new TextView(getContext());
            seat.setText("■");
            seat.setTextSize(24f);
            seat.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            seat.setTextColor(gray);
            seat.setPadding(8, 8, 8, 8);

            GradientDrawable border = new GradientDrawable();
            border.setColor(Color.TRANSPARENT);
            border.setStroke(2, gray);
            seat.setBackground(border);

            seat.setOnClickListener(v -> {
                if (selectedSeats.contains(seatId)) {
                    selectedSeats.remove(seatId);
                    seat.setTextColor(gray);
                    border.setStroke(2, gray);
                } else {
                    selectedSeats.add(seatId);
                    seat.setTextColor(Color.RED);
                    border.setStroke(3, Color.RED);
                }
                seat.setBackground(border);
                updateSummary();
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            seat.setLayoutParams(params);

            gridLayout.addView(seat);
        }
    }

    private void updateSummary() {
        int total = selectedSeats.size() * SEAT_PRICE;
        textSummary.setText("Вы выбрали " + selectedSeats.size() + " мест(а)\nИтого: " + total + " ₽");
    }
}
