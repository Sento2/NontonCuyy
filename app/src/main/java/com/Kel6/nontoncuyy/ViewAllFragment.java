package com.kel6.nontoncuyy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllFragment extends Fragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_MOVIES = "arg_movies";

    private String title;
    private List<Film> movies;

    public static ViewAllFragment newInstance(String title, List<Film> movies) {
        ViewAllFragment fragment = new ViewAllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putSerializable(ARG_MOVIES, new ArrayList<>(movies));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            movies = (List<Film>) getArguments().getSerializable(ARG_MOVIES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        TextView tvTitle = view.findViewById(R.id.tvViewAllTitle);
        tvTitle.setText(title);

        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        RecyclerView rv = view.findViewById(R.id.rvViewAll);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        
        MovieAdapter adapter = new MovieAdapter(movies, film -> {
            ((MainActivity) requireActivity()).openDetail(film);
            return kotlin.Unit.INSTANCE;
        });
        rv.setAdapter(adapter);
    }
}
