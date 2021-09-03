package com.pankajranag.rana_gaming.Mode;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pankajranag.rana_gaming.Model.classic_top;
import com.pankajranag.rana_gaming.R;
import com.pankajranag.rana_gaming.ViewHolder.CategoryViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArcadeMode extends Fragment {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<classic_top, CategoryViewHolder> recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference databaseReference;


    public ArcadeMode() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_friend, container, false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("arcade_top");

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        loadData();
        return v;
    }
    private void loadData()
    {
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<classic_top>()
                        .setQuery(databaseReference,classic_top.class)
                        .build();

        recyclerAdapter = new FirebaseRecyclerAdapter<classic_top, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull classic_top model) {

                holder.id.setText(model.getId());
                holder.name.setText(model.getName());
                holder.amount.setText(model.getAmount());

            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.top_player_layout,viewGroup,false);
                return new CategoryViewHolder(view);
            }
        };
        recyclerAdapter.notifyDataSetChanged();
        recyclerAdapter.startListening();
        recyclerView.setAdapter(recyclerAdapter);
    }
}
