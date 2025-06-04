package com.example.kurs.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kurs.R;

public class PurchaseFragment extends Fragment {

    private static final String ARG_MOVIE_TITLE = "movie_title";
    private static final String ARG_TIME = "time";
    private static final String ARG_HALL = "hall";
    private static final String ARG_TOTAL_PRICE = "total_price";

    private String movieTitle;
    private String time;
    private String hall;
    private int totalPrice;

    public static PurchaseFragment newInstance(int movieId, String movieTitle, String time, String hall, int totalPrice) {
        PurchaseFragment fragment = new PurchaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_TITLE, movieTitle);
        args.putString(ARG_TIME, time);
        args.putString(ARG_HALL, hall);
        args.putInt(ARG_TOTAL_PRICE, totalPrice);
        fragment.setArguments(args);
        return fragment;
    }

    public PurchaseFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieTitle = getArguments().getString(ARG_MOVIE_TITLE);
            time = getArguments().getString(ARG_TIME);
            hall = getArguments().getString(ARG_HALL);
            totalPrice = getArguments().getInt(ARG_TOTAL_PRICE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton buttonBack = view.findViewById(R.id.buttonBack);
        TextView textMovie = view.findViewById(R.id.textMovie);
        TextView textSession = view.findViewById(R.id.textSession);
        TextView textTotalPrice = view.findViewById(R.id.textTotalPrice);

        EditText editCardNumber = view.findViewById(R.id.editCardNumber);
        EditText editCardName = view.findViewById(R.id.editCardName);
        EditText editExpiry = view.findViewById(R.id.editExpiry);
        EditText editCVV = view.findViewById(R.id.editCVV);
        Button buttonBuyTicket = view.findViewById(R.id.buttonBuyTicket);

        buttonBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        textMovie.setText("Фильм: " + movieTitle);
        textSession.setText("Сеанс: " + time + " | " + hall);
        textTotalPrice.setText("Итого к оплате: " + totalPrice + " ₽");

        buttonBuyTicket.setOnClickListener(v -> {
            String cardNumber = editCardNumber.getText().toString().trim();
            String cardName = editCardName.getText().toString().trim();
            String expiry = editExpiry.getText().toString().trim();
            String cvv = editCVV.getText().toString().trim();

            if (TextUtils.isEmpty(cardNumber) || cardNumber.length() != 16) {
                editCardNumber.setError("Введите 16-значный номер карты");
                return;
            }
            if (TextUtils.isEmpty(cardName)) {
                editCardName.setError("Введите имя владельца");
                return;
            }
            if (TextUtils.isEmpty(expiry) || !expiry.matches("\\d{2}/\\d{2}")) {
                editExpiry.setError("Формат: MM/YY");
                return;
            }
            if (TextUtils.isEmpty(cvv) || cvv.length() != 3) {
                editCVV.setError("Введите 3-значный CVV");
                return;
            }

            Toast.makeText(getContext(), "Оплата прошла успешно!", Toast.LENGTH_LONG).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }
}
