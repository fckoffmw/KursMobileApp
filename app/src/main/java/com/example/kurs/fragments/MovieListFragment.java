package com.example.kurs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kurs.R;
import com.example.kurs.adapters.MovieAdapter;
import com.example.kurs.models.Movie;
import com.example.kurs.models.MovieResponse;
import com.example.kurs.network.ApiClient;
import com.example.kurs.network.MovieApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private final String API_KEY = "6abc24ab7cb2c77d1ca39bb8b127f655";

    public MovieListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadMovies();
    }

    private void loadMovies() {
        MovieApi movieApi = ApiClient.getRetrofit().create(MovieApi.class);
        movieApi.getPopularMovies(API_KEY, "ru-RU", 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();

                    movieAdapter = new MovieAdapter(movies, movie -> {
                        Toast.makeText(getContext(), "Нажато: " + movie.getTitle(), Toast.LENGTH_SHORT).show();

                        // Переход к MovieDetailFragment с передачей выбранного фильма
                        FragmentMovieDetail detailFragment = FragmentMovieDetail.newInstance(movie.getId());

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, detailFragment)
                                .addToBackStack(null)
                                .commit();
                    });

                    recyclerView.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Ошибка загрузки фильмов", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
