package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pankajranag.rana_gaming.R;

public class AboutUs_Activity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    TextView aboutus;
    String st_about;
    private DatabaseReference Post;
    private ProgressDialog progressdialog;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    AdRequest initial_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_);

      //  MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");

//        mInterstitialAd = new InterstitialAd(this);
//        // set the ad unit ID
//        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_add));
       // initial_ad = new AdRequest.Builder().build();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);
        aboutus = findViewById(R.id.aboutus);

        AdRequest adRequest = new AdRequest.Builder().build();

        Post = FirebaseDatabase.getInstance().getReference().child("aboutus");
        readRealTime();

//        mInterstitialAd.loadAd(initial_ad);
//        mInterstitialAd.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                showInterstitial();
//            }
//        });
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void readRealTime() {
        progressdialog.show();
        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {
                        progressdialog.show();
                        Post
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        progressdialog.dismiss();
                                        String dentryfee = dataSnapshot.child("about").getValue(String.class);
                                        aboutus.setText(dentryfee);


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                }, 1000L);


    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}

