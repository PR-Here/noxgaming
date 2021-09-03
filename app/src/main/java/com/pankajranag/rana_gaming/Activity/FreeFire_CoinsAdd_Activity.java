package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;
import com.pankajranag.rana_gaming.R;

public class FreeFire_CoinsAdd_Activity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    String mobile, number, number2;
    EditText edt_coins;
    Button add_btn, transfer;
    private static final int REQ_UPIPAYMENT = 1;
    private DatabaseReference Post, post_about, db_confirmmno, realsecound_db;
    String st_share, st_share2, st_no, st_telegram, dupdate, dupiid;
    Button cancel, send;
    EditText no, cno, coins;
    TextView total_coin, text_name, text_name2;
    String mobileno;
    int redColor = Color.parseColor("#0162cd");
    InterstitialAd mInterstitialAd;
    AdRequest adRequest, adRequest2;
    TextView one_txt;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire__coins_add_);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);

        Post = FirebaseDatabase.getInstance().getReference().child("allUpiid");
        post_about = FirebaseDatabase.getInstance().getReference().child("aboutus");
        edt_coins = findViewById(R.id.edt_coinsss);
        add_btn = findViewById(R.id.addcoins_btn);

        transfer = findViewById(R.id.transfer_coins);


        SharedPreferences preferences = getSharedPreferences("signup", MODE_PRIVATE);
        String value = preferences.getString("number", " ");
        mobile = value;
        Log.d("Permissions", "mobileno: " + value);

        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);

        one_txt = findViewById(R.id.about_1);

        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_add));
        adRequest = new AdRequest.Builder().build();
        adRequest2 = new AdRequest.Builder().build();
        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coins();
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(FreeFire_CoinsAdd_Activity.this);
                dialog.setContentView(R.layout.rewarded_video_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                cancel = dialog.findViewById(R.id.cancel_button);
                send = dialog.findViewById(R.id.cancel_button2);
                total_coin = dialog.findViewById(R.id.total_coins_dialog_transfer);
                no = dialog.findViewById(R.id.mobileno_dialog_coins);
                cno = dialog.findViewById(R.id.confirmmobileno_dialog_coins);
                text_name = dialog.findViewById(R.id.name_dialog);
                text_name2 = dialog.findViewById(R.id.name_dialog_transfer);
                coins = dialog.findViewById(R.id.coins_dialog_coins);

                total_coin.setText(st_share);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (no.getText().toString().length() != 10 && no.getText().toString().equals("")) {
                            Toast.makeText(FreeFire_CoinsAdd_Activity.this, "please enter a correct mobile no.", Toast.LENGTH_SHORT).show();
                        } else if (!(no.getText().toString().startsWith("7") ||
                                no.getText().toString().startsWith("8") ||
                                no.getText().toString().startsWith("9") ||
                                no.getText().toString().startsWith("6"))) {
                            no.setError("Please Enter valid  Mobile Number");
                        } else if (!cno.getText().toString().equals(no.getText().toString())) {
                            Toast.makeText(FreeFire_CoinsAdd_Activity.this, "please check both mobile no.", Toast.LENGTH_SHORT).show();
                        } else if (coins.getText().toString().equals("")) {
                            Toast.makeText(FreeFire_CoinsAdd_Activity.this, "please enter coins", Toast.LENGTH_SHORT).show();
                        } else {
                            readRealTime2();
                            userInfo();


                        }

                    }
                });


                dialog.show();
            }
        });

        upiId();

        readRealTime();
        readRealTime3();
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void coins() {
        String i = "10";

        final String st_total = edt_coins.getText().toString();
        Log.d("Permissions", "length: " + st_total);
        if (!st_total.equals("")) {
            Log.d("Permissions", "lengthhh: " + i);
            launchUPI();

        } else {
            Toast.makeText(this, "please add your coins", Toast.LENGTH_SHORT).show();
        }


    }

    private void launchUPI() {
        String amount = edt_coins.getText().toString();
        // look below for a reference to these parameters
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", dupiid)
                .appendQueryParameter("pn", "Nox Gaming")
                .appendQueryParameter("tn", "Coins Add in Wallet")
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("tn", "Welcome, Win Cash Reward Good Luck")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (null != chooser.resolveActivity(getPackageManager())) {
            Log.d("First", "UPI Payment resolved to activity");
            startActivityForResult(chooser, REQ_UPIPAYMENT);
        } else {
            Log.d("First", "No activity found to handle UPI Payment");
            Toast.makeText(this, "No application available to handle this request! Only UPI payment accept ", Toast.LENGTH_SHORT).show();
        }
    }

    private void readRealTime() {

        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {

                        databaseReference
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("addCoins", "Dataaa " + dataSnapshot);

                                        st_share = dataSnapshot.child("freecoins").getValue(String.class);
                                        st_no = dataSnapshot.child("mobileno").getValue(String.class);
                                        Log.d("addCoins", "coinsss " + st_share);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(FreeFire_CoinsAdd_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }, 1000L);


    }

    private void readRealTime2() {

        number2 = cno.getText().toString();

        realsecound_db = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(number2);

        realsecound_db
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.d("addCoins", "Dataaabbb " + dataSnapshot);

                            st_share2 = dataSnapshot.child("freecoins").getValue(String.class);

                            Log.d("addCoins", "coinsssttt " + st_share2);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(FreeFire_CoinsAdd_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (REQ_UPIPAYMENT == requestCode) {


            if (resultCode == Activity.RESULT_OK) {

                String txnId = "txnId=null&Status=FAILURE";
                String Status = "txnId=&responseCode=ZD&Status=FAILURE&txnRef=";
                String wrong = "txnId=&responseCode=&Status=FAILURE&txnRef=";

                String transId = data.getStringExtra("response");
                String res_status = data.getStringExtra("Status");

                Log.d("akjjd", "statuss: " + res_status);
                Log.d("akjjd", "transId: " + transId);

                if (res_status.equals("FAILURE")) {
                   // addCoins();

                    Toast.makeText(this, "payment failed", Toast.LENGTH_SHORT).show();

                } else if (res_status.equals("SUCCESS")) {
                    mInterstitialAd.loadAd(adRequest);
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            showInterstitial();
                        }
                    });
                    addCoins();


                }


            }
        }
    }

    private void addCoins() {

        final String st_total = edt_coins.getText().toString();
        final int edtcoins = Integer.parseInt(st_total);
        int oldcoins = Integer.parseInt(st_share);

        final int all_Total = edtcoins + oldcoins;

        final String all = String.valueOf(all_Total);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {


                    databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                            .child("every_signup_user").child(mobile);


                    databaseReference.child("freecoins").setValue(all);
                    Toast.makeText(FreeFire_CoinsAdd_Activity.this, edtcoins + " coins added in your wallet. Total coins is " + all, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FreeFire_CoinsAdd_Activity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void upiId() {

        Post.child("firstactivity")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        dupiid = dataSnapshot.child("lite").getValue(String.class);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void userInfo() {

        number = cno.getText().toString();

        db_confirmmno = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user");


        db_confirmmno.child(number).addListenerForSingleValueEvent(valueEventListener);
        Log.d("Permissions", "mobilennnno: " + number);


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.d("Permissions", "dataaa: " + dataSnapshot);
            if (dataSnapshot.exists()) {
                mobileno = dataSnapshot.child("mobileno").getValue(String.class);
                final String name = dataSnapshot.child("name").getValue(String.class);

                if (mobileno.equals(number)) {

                    text_name.setVisibility(View.VISIBLE);
                    text_name2.setVisibility(View.VISIBLE);
                    text_name2.setText(name);

                    Log.d("Permissions", "noooo: " + mobileno);
                    Log.d("Permissions", "st_share: " + st_share);

                    validate();

                }

            } else {
                Toast.makeText(FreeFire_CoinsAdd_Activity.this, "No User Found !", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(FreeFire_CoinsAdd_Activity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

        }

    };

    private void validate() {

        if (!st_no.equals(cno.getText().toString())) {

            String oldUserCoins = st_share;
            String newCoins = coins.getText().toString();

            int oldUser = Integer.parseInt(oldUserCoins);
            int newUser = Integer.parseInt(newCoins);

            if (oldUser >= newUser) {

                String oldCoins = st_share;
                String EnterNewCoins = coins.getText().toString();

                int old = Integer.parseInt(oldCoins);
                int newEntry = Integer.parseInt(EnterNewCoins);

                int FirstResult = old - newEntry;

                final String firstEntry = String.valueOf(FirstResult);

                total_coin.setText(firstEntry);

                //old user
                databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                        .child("every_signup_user").child(mobile);


                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {


                            databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                                    .child("every_signup_user").child(mobile);


                            databaseReference.child("freecoins").setValue(firstEntry);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(FreeFire_CoinsAdd_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                Validate2();

            } else {
                Toast.makeText(this, "you dont have any coins in your wallet please add coins and then try it.", Toast.LENGTH_LONG).show();
            }
        } else {
            // Toast.makeText(this, "you cant send coins on your number ", Toast.LENGTH_LONG).show();
            new StyleableToast.Builder(this)
                    .text("you cant send coins on your number ")
                    .stroke(2, Color.BLACK)
                    .backgroundColor(Color.WHITE)
                    .solidBackground()
                    .textColor(redColor)

                    .font(R.font.patua_one)

                    .cornerRadius(12)
                    .textSize(13)
                    .show();
        }

    }

    private void Validate2() {

        String cnumber = cno.getText().toString();

        if (!st_no.equals(cnumber)) {

            String oldUserCoins = st_share2;
            String edt_Coins = coins.getText().toString();

            int Old = Integer.parseInt(oldUserCoins);
            final int edt = Integer.parseInt(edt_Coins);

            int allCoins = Old + edt;

            final String all = String.valueOf(allCoins);


            number = cno.getText().toString();

            db_confirmmno = FirebaseDatabase.getInstance().getReference("signup_user")
                    .child("every_signup_user").child(number);

            db_confirmmno.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {


                        db_confirmmno = FirebaseDatabase.getInstance().getReference("signup_user")
                                .child("every_signup_user").child(number);

                        db_confirmmno.child("freecoins").setValue(all);

                        Toast.makeText(FreeFire_CoinsAdd_Activity.this, edt + " Coins transfer Successfully to " + number, Toast.LENGTH_LONG).show();
                        mInterstitialAd.loadAd(adRequest);
                        mInterstitialAd.setAdListener(new AdListener() {
                            public void onAdLoaded() {
                                showInterstitial();
                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(FreeFire_CoinsAdd_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "you can not  be send coins on your own number", Toast.LENGTH_SHORT).show();
        }
    }

    private void readRealTime3() {
        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {
                        post_about
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String dentryfee = dataSnapshot.child("free").getValue(String.class);
                                        one_txt.setText(dentryfee);


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                }, 1000L);


    }

    @Override
    public boolean onSupportNavigateUp()  {
        super.onBackPressed();
        return true;
    }
}
