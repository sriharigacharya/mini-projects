package com.example.expensetracker;

import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private MyRepository myRepository;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private EditText searchbar;
    private DatePickerDialog sortfromdate,sorttodate;
    private Button datepickerfrom,datepickerto;
    private ImageButton sortset;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        myRepository=new MyRepository(requireActivity().getApplication());
        recyclerView=view.findViewById(R.id.homelist);
        ArrayList<Expense> expenseArrayList=myRepository.getAllExpenses();
        searchbar=view.findViewById(R.id.homefragmentsearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        HomeListAdapter homeListAdapter=new HomeListAdapter(getContext(),expenseArrayList);
        recyclerView.setAdapter(homeListAdapter);
        floatingActionButton=view.findViewById(R.id.addexpensebutton);

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),NewExpenseActivity.class);
            startActivity(intent);
        });

//        datepickerfrom=view.findViewById(R.id.sortfromdate);
//        datepickerfrom.setText("From: ");
//        datepickerto=view.findViewById(R.id.sorttodate);
//        datepickerto.setText("To: ");
//        sortset=view.findViewById(R.id.sortset);

//        sortfromdate=DateUtil.initdatepicker(getContext(),datepickerfrom);
//        sorttodate=DateUtil.initdatepicker(getContext(),datepickerto);
//        sortset.setOnClickListener(v -> {
//            if (datepickerfrom.getText().toString().equals("From: ") || datepickerto.getText().toString().equals("To: ")){
//                Toast.makeText(getContext(),"Enter Range",Toast.LENGTH_LONG).show();
//                datepickerfrom.setText("From: ");
//                datepickerto.setText("To: ");
//            }
//            else{
//
//            }
//        });

//        datepickerfrom.setOnClickListener(v -> {
//            DateUtil.opendatepicker(sortfromdate);
//        });
//        datepickerto.setOnClickListener(v -> {
//            DateUtil.opendatepicker(sorttodate);
//        });




        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query=s.toString();
                List <Expense> filteredlist=myRepository.searchexpenses(query);
                homeListAdapter.updateList(filteredlist);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position=viewHolder.getAdapterPosition();
                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setTitle("Delete Expense")
                        .setMessage("Are you sure you want to delete this expense?")
                        .setPositiveButton("Delete",((dialog, which) -> {
                            Expense expense=expenseArrayList.get(position);
                            myRepository.delExpense(expense);
                            expenseArrayList.remove(expense);
                            recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                        }))
                        .setNegativeButton("Cancel",((dialog, which) -> {
                            recyclerView.getAdapter().notifyItemChanged(position);
                        }))
                        .setCancelable(false)
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }
}