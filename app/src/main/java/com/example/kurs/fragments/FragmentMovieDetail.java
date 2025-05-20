package com.example.kurs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.kurs.R;
import com.example.kurs.models.MovieDetail;
import com.example.kurs.network.ApiClient;
import com.example.kurs.network.MovieApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMovieDetail extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";
    private int movieId;

    public static FragmentMovieDetail newInstance(int movieId) {
        FragmentMovieDetail fragment = new FragmentMovieDetail();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMovieDetail() {
        // пустой конструктор
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_MOVIE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        ImageView imagePoster = view.findViewById(R.id.imagePoster);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textGenre = view.findViewById(R.id.textGenre);
        TextView textDuration = view.findViewById(R.id.textDuration);
        TextView textDescription = view.findViewById(R.id.textDescription);
        Button buttonSelectSession = view.findViewById(R.id.buttonSelectSession);

        MovieApi movieApi = ApiClient.getRetrofit().create(MovieApi.class);
        final String API_KEY = "6abc24ab7cb2c77d1ca39bb8b127f655";

        movieApi.getMovieDetails(movieId, API_KEY, "ru-RU").enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieDetail detail = response.body();

                    textTitle.setText(detail.getTitle());
                    textDescription.setText(detail.getOverview());

                    String imageUrl = "https://image.tmdb.org/t/p/w500" + detail.getPosterPath();
                    Glide.with(requireContext()).load(imageUrl).into(imagePoster);

                    List<MovieDetail.Genre> genres = detail.getGenres();
                    if (genres != null && !genres.isEmpty()) {
                        StringBuilder genreStr = new StringBuilder("Жанр: ");
                        for (int i = 0; i < genres.size(); i++) {
                            genreStr.append(genres.get(i).getName());
                            if (i != genres.size() - 1) genreStr.append(", ");
                        }
                        textGenre.setText(genreStr.toString());
                    } else {
                        textGenre.setText("Жанр не указан");
                    }

                    if (detail.getRuntime() > 0) {
                        textDuration.setText("Длительность: " + detail.getRuntime() + " мин");
                    } else {
                        textDuration.setText("Длительность не указана");
                    }
                } else {
                    Toast.makeText(requireContext(), "Ошибка загрузки деталей фильма", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(requireContext(), "Ошибка сети", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSelectSession.setOnClickListener(v -> {
            BookingFragment bookingFragment = BookingFragment.newInstance(movieId, textTitle.getText().toString());
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, bookingFragment)
                    .addToBackStack(null)
                    .commit();
        });


    }
}
