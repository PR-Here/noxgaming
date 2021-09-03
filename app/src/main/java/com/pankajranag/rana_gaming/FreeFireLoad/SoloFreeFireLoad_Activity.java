package com.pankajranag.rana_gaming.FreeFireLoad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pankajranag.rana_gaming.Model.ListData;
import com.pankajranag.rana_gaming.R;
import com.pankajranag.rana_gaming.ViewHolder.CategoryViewHolder2;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class SoloFreeFireLoad_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<ListData, CategoryViewHolder2> recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_free_fire_load_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);
        linearLayout=findViewById(R.id.noplayer_join);


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("FreeFireSolo_join_player").child("EntryComplete_player");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_load);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        nodata();

    }

    private void loadData() {
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<ListData>()
                        .setQuery(databaseReference, ListData.class)
                        .build();

        recyclerAdapter = new FirebaseRecyclerAdapter<ListData, CategoryViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder2 holder, int position, @NonNull ListData model) {

                holder.pubgname.setText(model.getPubgname());
                holder.paymentType.setText(model.getPaymentType());
                holder.mobileno.setText(model.getMobileno());

            }

            @NonNull
            @Override
            public CategoryViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.load_player_dialog, viewGroup, false);
                return new CategoryViewHolder2(view);
            }
        };
        recyclerAdapter.notifyDataSetChanged();
        recyclerAdapter.startListening();
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void nodata(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    linearLayout.setVisibility(View.GONE);
                    loadData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

}
