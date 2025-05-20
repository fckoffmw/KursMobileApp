package com.example.kurs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kurs.R;
import com.example.kurs.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private OnItemClickListener listener;

    // Интерфейс для обработки кликов по элементам списка
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    // Конструктор с передачей списка фильмов и слушателя кликов
    public MovieAdapter(List<Movie> movieList, OnItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie, listener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title, overview, date;
        ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            overview = itemView.findViewById(R.id.textOverview);
            date = itemView.findViewById(R.id.textReleaseDate);
            poster = itemView.findViewById(R.id.imagePoster);
        }

        // Метод bind связывает данные фильма с View и добавляет обработчик клика
        public void bind(final Movie movie, final OnItemClickListener listener) {
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
            date.setText(movie.getReleaseDate());

            String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
            Glide.with(itemView.getContext()).load(imageUrl).into(poster);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(movie);
                }
            });
        }
    }
}
