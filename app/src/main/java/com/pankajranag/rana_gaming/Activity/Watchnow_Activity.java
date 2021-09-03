package com.pankajranag.rana_gaming.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.webkit.WebView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pankajranag.rana_gaming.R;

public class Watchnow_Activity extends AppCompatActivity {

    private DatabaseReference Post;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchnow_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);

        webView=findViewById(R.id.webview);
        Post = FirebaseDatabase.getInstance().getReference().child("classicmode");
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;

    }
}
