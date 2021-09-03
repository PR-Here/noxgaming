package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pankajranag.rana_gaming.R;

public class ForAllCoins_Activity extends AppCompatActivity {

    Button pubg_btn, lite_btn, free_btn;
    TextView pubg_txt, lite_txt, free_txt;
    private ProgressDialog progressdialog;
    String mobile,st_coins;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    InterstitialAd mInterstitialAd;
    AdRequest adRequest, adRequest2;
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_all_coins_);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest2);

        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_add));
        adRequest = new AdRequest.Builder().build();
        adRequest2 = new AdRequest.Builder().build();
        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);

        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);

        SharedPreferences preferences = getSharedPreferences("signup", MODE_PRIVATE);
        String value = preferences.getString("number", " ");
        mobile = value;

        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);
        headerData();

        pubg_btn = findViewById(R.id.pubgmobile_coins);
        lite_btn = findViewById(R.id.lite_coins_btn);
        free_btn = findViewById(R.id.freefire_coins_btn);

        pubg_txt = findViewById(R.id.pubg_coins_text);
        lite_txt = findViewById(R.id.lite_coins_text);
        free_txt = findViewById(R.id.freefire_coins_text);

        pubg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForAllCoins_Activity.this, AddCoins_Activity.class));
            }
        });

        lite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForAllCoins_Activity.this, PUBGLiteCoins_Activity.class));
            }
        });
        free_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForAllCoins_Activity.this, FreeFire_CoinsAdd_Activity.class));
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    private void headerData() {
        progressdialog.show();

        databaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressdialog.dismiss();
                        if (dataSnapshot.exists()) {
                            String st_name = dataSnapshot.child("name").getValue(String.class);
                            String st_emailid = dataSnapshot.child("email").getValue(String.class);
                            String st_pubgname = dataSnapshot.child("pubgname").getValue(String.class);
                            String st_pubgid = dataSnapshot.child("pubgid").getValue(String.class);
                            String st_mobileno = dataSnapshot.child("mobileno").getValue(String.class);
                            String lite_coins = dataSnapshot.child("litecoins").getValue(String.class);
                            String free = dataSnapshot.child("freecoins").getValue(String.class);

                            st_coins = dataSnapshot.child("coins").getValue(String.class);
                            pubg_txt.setText(st_coins);
                            lite_txt.setText(lite_coins);
                            free_txt.setText(free);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
