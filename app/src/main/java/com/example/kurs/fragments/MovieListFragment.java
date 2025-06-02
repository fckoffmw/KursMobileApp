package com.example.kurs.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kurs.BuildConfig;
import com.example.kurs.R;
import com.example.kurs.adapters.MovieAdapter;
import com.example.kurs.models.Movie;
import com.example.kurs.models.MovieResponse;
import com.example.kurs.network.ApiClient;
import com.example.kurs.network.MovieApi;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private final String API_KEY = BuildConfig.API_KEY;

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

        String userEmail = getArguments() != null ? getArguments().getString("email") : null;

        if (userEmail != null) {
            FirebaseFirestore.getInstance().collection("base1")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (!snapshot.isEmpty()) {
                            String username = snapshot.getDocuments().get(0).getString("username");
                            Toast.makeText(getContext(), "Здравствуйте, " + username, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        TextView textLogout = view.findViewById(R.id.textLogout);
        textLogout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    new android.view.ContextThemeWrapper(requireContext(), R.style.AlertDialogCustom)
            );

            builder.setTitle("Выход")
                    .setMessage("Вы действительно хотите выйти?")
                    .setPositiveButton("Выйти", (dialog, which) -> {
                        navigateToLogin();
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        });
    }

    private void navigateToLogin() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }

    private void loadMovies() {
        MovieApi movieApi = ApiClient.getRetrofit().create(MovieApi.class);
        movieApi.getPopularMovies(API_KEY, "ru-RU", 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();

                    movieAdapter = new MovieAdapter(movies, movie -> {
                        Toast.makeText(getContext(), "Выбрано: " + movie.getTitle(), Toast.LENGTH_SHORT).show();

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
