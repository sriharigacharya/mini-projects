package com.example.expensetracker;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterPageResusableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterPageResusableFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_LAYOUT_MANAGER = "layout_manager";
    private static final String ARG_SPAN_COUNT = "span_count";

    private String title;
    private String layoutManagerType;
    private int spanCount;
    private String backFragmentTag;
    String curr_tag;

    private RecyclerView.Adapter<?> adapter;

    public static FilterPageResusableFragment newInstance(String title, String layoutManagerType, int spanCount) {
        FilterPageResusableFragment fragment = new FilterPageResusableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_LAYOUT_MANAGER, layoutManagerType);
        args.putInt(ARG_SPAN_COUNT, spanCount);
        fragment.setArguments(args);
        return fragment;
    }

    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            layoutManagerType = getArguments().getString(ARG_LAYOUT_MANAGER);
            spanCount = getArguments().getInt(ARG_SPAN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_page_resusable, container, false);
        curr_tag=getTag();
        TextView titleView = view.findViewById(R.id.pagetitlereusable);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewfilterreusable);

        titleView.setText(title);

        if ("grid".equals(layoutManagerType)) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        recyclerView.setAdapter(adapter);




        return view;
    }
}
