package com.Kel6.nontoncuyy;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private MovieViewModel viewModel;
    private MovieAdapter recommendedAdapter;
    private EditText etSearch;
    private final List<TextView> genreButtons = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initViewModel();
        setupSearch();
        setupGenres(view);
    }

    private void initViews(View view) {
        etSearch = view.findViewById(R.id.etSearch);
        
        View sectionRecommended = view.findViewById(R.id.sectionRecommended);
        ((TextView) sectionRecommended.findViewById(R.id.tvSectionTitle)).setText("Recommended for You");
        RecyclerView rvRecommended = sectionRecommended.findViewById(R.id.rvMovies);
        rvRecommended.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendedAdapter = new MovieAdapter(new ArrayList<>(), film -> {
            ((MainActivity) requireActivity()).openDetail(film);
            return kotlin.Unit.INSTANCE;
        });
        rvRecommended.setAdapter(recommendedAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);
        viewModel.getFilteredMovies().observe(getViewLifecycleOwner(), films -> {
            if (films != null) {
                recommendedAdapter.updateData(films);
            }
        });
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.searchMovies(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupGenres(View view) {
        genreButtons.clear();
        TextView btnAll = view.findViewById(R.id.btnGenreAll);
        TextView btnAction = view.findViewById(R.id.btnGenreAction);
        TextView btnSciFi = view.findViewById(R.id.btnGenreSciFi);
        TextView btnDrama = view.findViewById(R.id.btnGenreDrama);

        genreButtons.add(btnAll);
        genreButtons.add(btnAction);
        genreButtons.add(btnSciFi);
        genreButtons.add(btnDrama);

        btnAll.setOnClickListener(v -> handleGenreClick(btnAll, "All"));
        btnAction.setOnClickListener(v -> handleGenreClick(btnAction, "Action"));
        btnSciFi.setOnClickListener(v -> handleGenreClick(btnSciFi, "Sci-Fi"));
        btnDrama.setOnClickListener(v -> handleGenreClick(btnDrama, "Drama"));
    }

    private void handleGenreClick(TextView clickedButton, String category) {
        // Colors
        int colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary);
        int colorBlack = ContextCompat.getColor(requireContext(), R.color.black);
        int colorNeutral = ContextCompat.getColor(requireContext(), R.color.neutral);

        for (TextView btn : genreButtons) {
            if (btn == clickedButton) {
                btn.setBackgroundTintList(ColorStateList.valueOf(colorPrimary));
                btn.setTextColor(colorBlack);
            } else {
                btn.setBackgroundTintList(null);
                btn.setTextColor(colorNeutral);
            }
        }
        
        viewModel.filterByGenre(category);
    }


}
