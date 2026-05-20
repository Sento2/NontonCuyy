package com.kel6.nontoncuyy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WatchlistFragment extends Fragment {

    private MovieViewModel viewModel;
    private WatchlistAdapter adapter;
    private TextView tvWatchlistCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watchlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvWatchlistCount = view.findViewById(R.id.tvWatchlistCount);
        
        RecyclerView rvWatchlist = view.findViewById(R.id.rvWatchlist);
        rvWatchlist.setLayoutManager(new GridLayoutManager(getContext(), 2));
        
        adapter = new WatchlistAdapter(new ArrayList<>(), film -> {
            ((MainActivity) requireActivity()).openDetail(film);
            return kotlin.Unit.INSTANCE;
        }, film -> {
            viewModel.toggleWatchlist(film);
            return kotlin.Unit.INSTANCE;
        });
        rvWatchlist.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);
        viewModel.getWatchlist().observe(getViewLifecycleOwner(), films -> {
            if (films != null) {
                adapter.updateData(films);
                tvWatchlistCount.setText(films.size() + " Saved Titles");
            }
        });
    }
}
