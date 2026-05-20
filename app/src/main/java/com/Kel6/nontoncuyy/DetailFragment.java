package com.kel6.nontoncuyy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kel6.nontoncuyy.MainActivity;
import com.kel6.nontoncuyy.R;

import java.util.ArrayList;
import java.util.List;

import coil.Coil;
import coil.request.ImageRequest;

public class DetailFragment extends Fragment {

    private static final String ARG_FILM = "arg_film";
    private Film movie;
    private MovieViewModel viewModel;
    private MovieAdapter similarAdapter;

    public static DetailFragment newInstance(Film film) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILM, film);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Film) getArguments().getSerializable(ARG_FILM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel(); // Init VM first
        initViews(view);
        populateData(view);
        updateWatchlistButton(view.findViewById(R.id.btnWatchlist));
    }

    private void updateWatchlistButton(TextView btn) {
        if (movie == null || viewModel == null) return;
        boolean inWatchlist = viewModel.isInWatchlist(movie.getId());
        btn.setText(inWatchlist ? "In Watchlist" : "Watchlist");
    }

    private void initViews(View view) {
        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        
        TextView btnWatchlist = view.findViewById(R.id.btnWatchlist);
        btnWatchlist.setOnClickListener(v -> {
            if (movie != null) {
                viewModel.toggleWatchlist(movie);
                updateWatchlistButton(btnWatchlist);
            }
        });

        // Similar Movies Setup
        View sectionSimilar = view.findViewById(R.id.sectionSimilar);
        ((TextView) sectionSimilar.findViewById(R.id.tvSectionTitle)).setText("Similar Movies");
        RecyclerView rvSimilar = sectionSimilar.findViewById(R.id.rvMovies);
        rvSimilar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        similarAdapter = new MovieAdapter(new ArrayList<>(), film -> {
            ((MainActivity) requireActivity()).openDetail(film);
            return kotlin.Unit.INSTANCE;
        });
        rvSimilar.setAdapter(similarAdapter);

        // Cast Setup
        RecyclerView rvCast = view.findViewById(R.id.rvCast);
        rvCast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<Cast> dummyCast = new ArrayList<>();
        dummyCast.add(new Cast("Marcus Chen", "ELIAS THORNE", null));
        dummyCast.add(new Cast("Elena Vance", "AURA-7", null));
        dummyCast.add(new Cast("Victor S.", "DIRECTOR", null));
        rvCast.setAdapter(new CastAdapter(dummyCast));
    }

    private void populateData(View view) {
        if (movie == null) return;

        ImageView ivBanner = view.findViewById(R.id.ivDetailBanner);
        TextView tvTitle = view.findViewById(R.id.tvDetailTitle);
        TextView tvGenre = view.findViewById(R.id.tvDetailGenre);
        TextView tvRating = view.findViewById(R.id.tvDetailRating);
        TextView tvSynopsis = view.findViewById(R.id.tvDetailSynopsis);
        TextView tvMeta = view.findViewById(R.id.tvDetailMeta);

        tvTitle.setText(movie.getJudul());
        tvGenre.setText(movie.getKategori().toUpperCase());
        tvRating.setText("★ " + movie.getSkorRating());
        tvSynopsis.setText(movie.getRingkasan());
        tvMeta.setText(movie.getTanggalRilis() + " • 2h 14m • PG-13");

        ImageRequest request = new ImageRequest.Builder(requireContext())
                .data(movie.getGambarSampul())
                .target(ivBanner)
                .build();
        Coil.imageLoader(requireContext()).enqueue(request);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);
        viewModel.getMovies().observe(getViewLifecycleOwner(), films -> {
            if (films != null) {
                similarAdapter.updateData(films);
            }
        });
    }
}
