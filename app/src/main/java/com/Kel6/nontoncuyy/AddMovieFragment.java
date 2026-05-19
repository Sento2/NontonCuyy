package com.Kel6.nontoncuyy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class AddMovieFragment extends Fragment {

    private MovieViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);

        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etCategory = view.findViewById(R.id.etCategory);
        EditText etRating = view.findViewById(R.id.etRating);
        EditText etPoster = view.findViewById(R.id.etPoster);
        EditText etTrailer = view.findViewById(R.id.etTrailer);
        EditText etSummary = view.findViewById(R.id.etSummary);

        view.findViewById(R.id.btnCancel).setOnClickListener(v -> {
            ((MainActivity) requireActivity()).selectHomeTab();
        });

        view.findViewById(R.id.btnSave).setOnClickListener(v -> {
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
                    // Go back to Home
                    ((MainActivity) requireActivity()).selectHomeTab();
                } else {
                    Toast.makeText(getContext(), "Failed to add movie", Toast.LENGTH_SHORT).show();
                }
                return kotlin.Unit.INSTANCE;
            });
        });
    }
}