package com.pankajranag.rana_gaming.Lite_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.pankajranag.rana_gaming.BuildConfig;
import com.pankajranag.rana_gaming.R;

import org.json.JSONObject;

import java.util.HashMap;

public class Golden_Woods extends AppCompatActivity implements RewardedVideoAdListener {

    private static final int REQ_UPIPAYMENT = 1;
    TextView enteryfee, type, mode, map;
    TextView rank1, rank2, rank3, rank4, rank5, rank6, rank7, rank8, rank9, rank10;
    TextView matchno, date, time, perkill, status, text_view,roomid,password;
    private DatabaseReference Post;
    private ProgressDialog progressdialog;
    Button info, watchnow;
    TextView information;
    String dinfo, dstatus, dpaidentry, dupid, dwatchnow, dupdate,droomid,dpass;
    private String st_joinplayer;
    private String No = "no";
    private AdView mAdView, mAdView2, mAdview3, adView4;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference dbrefrence = databaseReference.child("pubglite").child("image1");
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private HashMap<String, Object> firebaseDefaultMap;
    public static final String VERSION_CODE_KEY = "latest_app_version";
    private static final String TAG = "Loading Data ...";
    ImageView bigimage, smallimage;
    SeekBar seek_bar;
    FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbrefrence_join_player;
    RewardedVideoAd rewardedVideoAd;
    private DatabaseReference pubg_dialog_database;
    String mobile;
    String st_name;
    String st_pubgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golden__woods);
        ScrollView scrollView = findViewById(R.id.scrool);
        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        mAdView = findViewById(R.id.adView);
        mAdView2 = findViewById(R.id.adView2);
        mAdview3 = findViewById(R.id.adView3);
        adView4 = findViewById(R.id.adView4);

        AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest2 = new AdRequest.Builder().build();
        AdRequest adRequest3 = new AdRequest.Builder().build();
        AdRequest adRequest4 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView2.loadAd(adRequest2);
        mAdview3.loadAd(adRequest3);
        adView4.loadAd(adRequest4);

        firebaseDefaultMap = new HashMap<>();
        firebaseDefaultMap.put(VERSION_CODE_KEY, getCurrentVersionCode());
        mFirebaseRemoteConfig.setDefaults(firebaseDefaultMap);
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);
        init();
        readRealTime();
        bigimage = findViewById(R.id.big_image);
        smallimage = findViewById(R.id.small_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);

        dbrefrence_join_player = FirebaseDatabase.getInstance().getReference("join_player").child("paid_player");

        // for join now dialog
        SharedPreferences preferences = getSharedPreferences("signup", MODE_PRIVATE);
        String value = preferences.getString("number", " ");
        mobile = value;
        pubg_dialog_database = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);

        readReal();

        Button fab = findViewById(R.id.joinnow);
        info = findViewById(R.id.info);
        watchnow = findViewById(R.id.watchnow);

        fab.isAttachedToWindow();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Golden_Woods.this)
                        .setTitle("Important Notice")
                        .setCancelable(false)
                        .setMessage("If there no Payment option Show in your mobile..Then send message with Screenshot in whatsapp group")
                        //  .setMessage("https://t.me/NOXGAMING11")


                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final CheckBox checkbox;
                                final Button cancel, join_d;
                                TextView  pubg_id;
                                EditText pubg_name;
                                final Dialog dialog1 = new Dialog(Golden_Woods.this);
                                dialog1.setContentView(R.layout.rewarded_video_layout);
                                dialog1.setCancelable(false);
                                Window window = dialog1.getWindow();
                                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);

                                checkbox = dialog1.findViewById(R.id.checkbox_terms);
                                cancel = dialog1.findViewById(R.id.cancel_button);
                                join_d = dialog1.findViewById(R.id.join_button);
                                pubg_id = dialog1.findViewById(R.id.pubg_id_dialog);
                                pubg_name = dialog1.findViewById(R.id.pubg_name_dialog);
                                pubg_name.setText(st_name);
                                pubg_id.setText(st_pubgid);

                                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (checkbox.isChecked()) {

                                            join_d.setVisibility(View.VISIBLE);

                                        } else {
                                            // your code to  no checked checkbox
                                            join_d.setVisibility(View.GONE);


                                        }
                                    }
                                });
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog1.dismiss();
                                    }
                                });
                                join_d.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        launchUPI();
                                    }
                                });
                                dialog1.show();
//
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


//                            }
//                        }, 2000L);


            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }
                ImageView cut;

                final Dialog dialog = new Dialog(Golden_Woods.this);
                dialog.setContentView(R.layout.wrongno_dialog);
                Window window = dialog.getWindow();
                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo);

                dialog.show();
            }

        });
        mFirebaseRemoteConfig.setConfigSettings(
                new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG)
                        .build());

        mFirebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mFirebaseRemoteConfig.activateFetched();
                    Log.d(TAG, "Fetched value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));
                    //calling function to check if new version is available or not
                    checkForUpdate();
                } else {
                    Toast.makeText(Golden_Woods.this, "Something went wrong please try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.d(TAG, "Default value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));
        seebbarr();


    }


    private void readReal() {
        progressdialog.show();
        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {
                        progressdialog.show();

                        pubg_dialog_database
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        progressdialog.dismiss();
                                        if (dataSnapshot.exists()) {
                                            st_name = dataSnapshot.child("pubgname").getValue(String.class);
                                            String st_emailid = dataSnapshot.child("email").getValue(String.class);
                                            String st_pubgname = dataSnapshot.child("name").getValue(String.class);
                                            st_pubgid = dataSnapshot.child("pubgid").getValue(String.class);
                                            String st_mobileno = dataSnapshot.child("mobileno").getValue(String.class);
                                            String st_address = dataSnapshot.child("address").getValue(String.class);


                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                }, 1000L);


    }

    public void seebbarr() {
        seek_bar = (SeekBar) findViewById(R.id.seekBar);
        seek_bar.setVisibility(View.GONE);
        text_view = (TextView) findViewById(R.id.textView);
        text_view.setVisibility(View.GONE);
        text_view.setText("Join Player : " + seek_bar.getProgress() + " / " + seek_bar.getMax());

        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser == true) {


                            progress_value = progress;
                            text_view.setText("Join Player : " + progress + " / " + seek_bar.getMax());
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Toast.makeText(FirstImage_Activity.this,"SeekBar in StartTracking",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        text_view.setText("Join Player : " + progress_value + " / " + seek_bar.getMax());
                        //    Toast.makeText(FirstImage_Activity.this,"SeekBar in StopTracking",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    private void checkForUpdate() {
        int latestAppVersion = (int) mFirebaseRemoteConfig.getDouble(VERSION_CODE_KEY);
        if (latestAppVersion > getCurrentVersionCode()) {
            new AlertDialog.Builder(this).setTitle("Please Update the App")
                    .setMessage("A new version of this app is available. Please update it").setPositiveButton(
                    "UPDATE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = dupdate;

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }).setNegativeButton("LATER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
                    .setCancelable(false).show();
        } else {
            //  Toast.makeText(this, "This app is already up to date", Toast.LENGTH_SHORT).show();
        }
    }

    private int getCurrentVersionCode() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void launchUPI() {
        // look below for a reference to these parameters
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", dupid)
                .appendQueryParameter("pn", "Nox Gaming")
                .appendQueryParameter("tn", "Custum room entry fee")
                .appendQueryParameter("am", dpaidentry)
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (REQ_UPIPAYMENT == requestCode) {
                if (RESULT_OK != resultCode) {
//                   String transId = data.getStringExtra("response");
//                String transIdd = data.getStringExtra("error");
                    Log.d("bhb", "UPI Payment successfull");
                    String payment_response = data.getStringExtra("payment_response");

                    Toast.makeText(this, "payment cancel: " + payment_response, Toast.LENGTH_SHORT).show();

                } else if (resultCode == Activity.RESULT_OK) {
//                    String payment_response = data.getStringExtra("payment_response");
                    String payment_response = data.getStringExtra("payment_response");
                    Toast.makeText(this, "payment Failed", Toast.LENGTH_SHORT).show();

                    try {

                        firebaseAuth = FirebaseAuth.getInstance();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        st_joinplayer = user.toString();
                        joinPlayer();
                        JSONObject jsonObject = new JSONObject(payment_response);

                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                            Toast.makeText(this, "NOT COMPLETE", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "COMPLETE", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        // Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            } else if (REQ_UPIPAYMENT != requestCode && data == null) {
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
                Log.d("hb", "UPI Payment failed");
            }
        }
    }

    private void init() {
        enteryfee = findViewById(R.id.entryfee);
        type = findViewById(R.id.type);
        mode = findViewById(R.id.mode);
        map = findViewById(R.id.map);

        rank1 = findViewById(R.id.rank1);
        rank2 = findViewById(R.id.rank2);
        rank3 = findViewById(R.id.rank3);
        rank4 = findViewById(R.id.rank4);
        rank5 = findViewById(R.id.rank5);
        rank6 = findViewById(R.id.rank6);
        rank7 = findViewById(R.id.rank7);
        rank8 = findViewById(R.id.rank8);
        rank9 = findViewById(R.id.rank9);
        rank10 = findViewById(R.id.rank10);
        matchno = findViewById(R.id.matchno);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        perkill = findViewById(R.id.perkill);
        status = findViewById(R.id.status);
        roomid=findViewById(R.id.room_id);
        password=findViewById(R.id.room_password);


        Post = FirebaseDatabase.getInstance().getReference().child("goldenlite");
    }

    private void readRealTime() {
        progressdialog.show();
        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {
                        progressdialog.show();
                        Post.child("firstactivity")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        progressdialog.dismiss();
                                        String dentryfee = dataSnapshot.child("entryfee").getValue(String.class);
                                        String dtype = dataSnapshot.child("type").getValue(String.class);
                                        String dmode = dataSnapshot.child("mode").getValue(String.class);
                                        String dmap = dataSnapshot.child("map").getValue(String.class);
                                        String drank1 = dataSnapshot.child("rank1").getValue(String.class);
                                        String drank2 = dataSnapshot.child("rank2").getValue(String.class);
                                        String drank3 = dataSnapshot.child("rank3").getValue(String.class);
                                        String drank4 = dataSnapshot.child("rank4").getValue(String.class);
                                        String drank5 = dataSnapshot.child("rank5").getValue(String.class);
                                        String drank6 = dataSnapshot.child("rank6").getValue(String.class);
                                        String drank7 = dataSnapshot.child("rank7").getValue(String.class);
                                        String drank8 = dataSnapshot.child("rank8").getValue(String.class);
                                        String drank9 = dataSnapshot.child("rank9").getValue(String.class);
                                        String drank10 = dataSnapshot.child("rank10").getValue(String.class);
                                        String dtime = dataSnapshot.child("time").getValue(String.class);
                                        String dmatchno = dataSnapshot.child("matchno").getValue(String.class);
                                        String ddate = dataSnapshot.child("date").getValue(String.class);
                                        String dperkill = dataSnapshot.child("perkill").getValue(String.class);
                                        dinfo = dataSnapshot.child("information").getValue(String.class);
                                        dstatus = dataSnapshot.child("status").getValue(String.class);
                                        dpaidentry = dataSnapshot.child("paidentry").getValue(String.class);
                                        dupid = dataSnapshot.child("upiid").getValue(String.class);
                                        dwatchnow = dataSnapshot.child("disabled").getValue(String.class);
                                        dupdate = dataSnapshot.child("update").getValue(String.class);
                                        droomid = dataSnapshot.child("roomid").getValue(String.class);
                                        dpass = dataSnapshot.child("password").getValue(String.class);
                                        final String st_watchnow = dataSnapshot.child("watchnow").getValue(String.class);

                                        enteryfee.setText(dentryfee);
                                        type.setText(dtype);
                                        mode.setText(dmode);
                                        map.setText(dmap);
                                        rank1.setText(drank1);
                                        rank2.setText(drank2);
                                        rank3.setText(drank3);
                                        rank4.setText(drank4);
                                        rank5.setText(drank5);
                                        rank6.setText(drank6);
                                        rank7.setText(drank7);
                                        rank8.setText(drank8);
                                        rank9.setText(drank9);
                                        rank10.setText(drank10);
                                        matchno.setText(dmatchno);
                                        date.setText(ddate);
                                        time.setText(dtime);
                                        perkill.setText(dperkill);
                                        roomid.setText(droomid);
                                        password.setText(dpass);
                                        status.setText(dstatus);
                                        if (!dwatchnow.isEmpty()) {
                                            watchnow.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (rewardedVideoAd.isLoaded()) {
                                                        rewardedVideoAd.show();
                                                    }
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCBcDJNPOEUw5pzePoqYKsgA"));
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.setPackage("com.google.android.youtube");
                                                    startActivity(intent);
                                                }
                                            });
                                        } else if (dwatchnow.isEmpty()) {
                                            watchnow.setVisibility(View.GONE);
                                        }
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

    @Override
    protected void onStart() {
        super.onStart();

        dbrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstimage = dataSnapshot.getValue(String.class);
                Glide.with(Golden_Woods.this)
                        .load(firstimage)
                        .into(bigimage);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void joinPlayer() {

//        firebaseAuth.createUserWithEmailAndPassword(st_joinplayer,st_joinplayer )
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        playerjoin playernjoi=new playerjoin(st_joinplayer);
//                    dbrefrence_join_player.child(st_joinplayer).setValue(playernjoi).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            if (task.isSuccessful()) {
//                                progressdialog.show();
//
//                                Toast.makeText(FirstImage_Activity.this, "COMPLETE", Toast.LENGTH_SHORT).show();
//                            } else {
//                            }
//                        }
//                    });
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });

    }


    @Override
    public void onRewarded(RewardItem reward) {
//        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
//                reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
    }

    public void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd(getString(R.string.Ad_mob_unit_id),
                new AdRequest.Builder().build());
    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    protected void onPause() {
        rewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        rewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
