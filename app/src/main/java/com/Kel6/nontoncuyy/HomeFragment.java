package com.Kel6.nontoncuyy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

import coil.Coil;
import coil.request.ImageRequest;

public class HomeFragment extends Fragment {

    private MovieViewModel viewModel;
    private MovieAdapter trendingAdapter;
    private MovieAdapter popularAdapter;
    private MovieAdapter topRatedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initViewModel();
        setupAddMovie(view);
    }

    private void initViews(View view) {
        // Trending
        View sectionTrending = view.findViewById(R.id.sectionTrending);
        ((TextView) sectionTrending.findViewById(R.id.tvSectionTitle)).setText("Trending Now");
        sectionTrending.findViewById(R.id.tvViewAll).setOnClickListener(v -> {
            if (viewModel.getMovies().getValue() != null) {
                ((MainActivity) requireActivity()).openViewAll("Trending Now", viewModel.getMovies().getValue());
            }
        });
        RecyclerView rvTrending = sectionTrending.findViewById(R.id.rvMovies);
        rvTrending.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingAdapter = new MovieAdapter(new ArrayList<>(), film -> {
            ((MainActivity) requireActivity()).openDetail(film);
            return kotlin.Unit.INSTANCE;
        });
        rvTrending.setAdapter(trendingAdapter);

        // Popular
        View sectionPopular = view.findViewById(R.id.sectionPopular);
        ((TextView) sectionPopular.findViewById(R.id.tvSectionTitle)).setText("Popular Movies");
        sectionPopular.findViewById(R.id.tvViewAll).setOnClickListener(v -> {
            if (viewModel.getMovies().getValue() != null) {
                ((MainActivity) requireActivity()).openViewAll("Popular Movies", viewModel.getMovies().getValue());
            }
        });
        RecyclerView rvPopular = sectionPopular.findViewById(R.id.rvMovies);
        rvPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularAdapter = new MovieAdapter(new ArrayList<>(), film -> {
            ((MainActivity) requireActivity()).openDetail(film);
            return kotlin.Unit.INSTANCE;
        });
        rvPopular.setAdapter(popularAdapter);

        // Top Rated
        View sectionTopRated = view.findViewById(R.id.sectionTopRated);
        ((TextView) sectionTopRated.findViewById(R.id.tvSectionTitle)).setText("Top Rated");
        sectionTopRated.findViewById(R.id.tvViewAll).setOnClickListener(v -> {
            if (viewModel.getMovies().getValue() != null) {
                ((MainActivity) requireActivity()).openViewAll("Top Rated", viewModel.getMovies().getValue());
            }
        });
        RecyclerView rvTopRated = sectionTopRated.findViewById(R.id.rvMovies);
        rvTopRated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedAdapter = new MovieAdapter(new ArrayList<>(), film -> {
            ((MainActivity) requireActivity()).openDetail(film);
            return kotlin.Unit.INSTANCE;
        });
        rvTopRated.setAdapter(topRatedAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);
        viewModel.getMovies().observe(getViewLifecycleOwner(), films -> {
            if (films != null && !films.isEmpty()) {
                trendingAdapter.updateData(films);
                popularAdapter.updateData(films);
                topRatedAdapter.updateData(films);
                updateFeaturedMovie(films.get(0));
            }
        });
        viewModel.fetchMovies();
    }

    private void updateFeaturedMovie(Film film) {
        if (getView() == null) return;
        ImageView ivFeatured = getView().findViewById(R.id.ivFeatured);
        TextView tvTitle = getView().findViewById(R.id.tvFeaturedTitle);
        TextView tvDesc = getView().findViewById(R.id.tvFeaturedDesc);

        tvTitle.setText(film.getJudul());
        tvDesc.setText(film.getRingkasan());

        ImageRequest request = new ImageRequest.Builder(requireContext())
                .data(film.getGambarSampul())
                .target(ivFeatured)
                .build();
        Coil.imageLoader(requireContext()).enqueue(request);
    }

    private void setupAddMovie(View view) {
        ExtendedFloatingActionButton fabAddMovie = view.findViewById(R.id.fabAddMovie);
        fabAddMovie.setOnClickListener(v -> showAddMovieDialog());
    }

    private void showAddMovieDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_movie, null);
        EditText etTitle = dialogView.findViewById(R.id.etTitle);
        EditText etCategory = dialogView.findViewById(R.id.etCategory);
        EditText etRating = dialogView.findViewById(R.id.etRating);
        EditText etPoster = dialogView.findViewById(R.id.etPoster);
        EditText etTrailer = dialogView.findViewById(R.id.etTrailer);
        EditText etSummary = dialogView.findViewById(R.id.etSummary);

        new AlertDialog.Builder(requireContext(), R.style.Theme_NontonCuyy)
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = etTitle.getText().toString();
                    String category = etCategory.getText().toString();
                    String rating = etRating.getText().toString();
                    String poster = etPoster.getText().toString();
                    String trailer = etTrailer.getText().toString();
                    String summary = etSummary.getText().toString();

                    if (title.isEmpty() || category.isEmpty()) {
                        Toast.makeText(getContext(), "Title and Category are required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Film newFilm = new Film(
                            "0",
                            title,
                            summary,
                            poster,
                            poster,
                            "2024",
                            rating,
                            category,
                            trailer
                    );

                    viewModel.addFilm(newFilm, success -> {
                        if (success) {
                            Toast.makeText(getContext(), "Movie added successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to add movie", Toast.LENGTH_SHORT).show();
                        }
                        return kotlin.Unit.INSTANCE;
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
