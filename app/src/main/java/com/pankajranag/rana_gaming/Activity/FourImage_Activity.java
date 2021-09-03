package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.pankajranag.rana_gaming.JavaClass.CompleteEntry;
import com.pankajranag.rana_gaming.BuildConfig;
import com.pankajranag.rana_gaming.R;

import java.util.Arrays;
import java.util.HashMap;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class FourImage_Activity extends AppCompatActivity implements RewardedVideoAdListener {
    String[] country = {"PayTm", "GooglePay", "PhonePay"};
    private static final int REQ_UPIPAYMENT = 1;
    private ProgressDialog progressdialog;
    private AdView mAdView, mAdView2, mAdView3;
    String st_name;
    String mobile;
    String st_pubgid;
    Button fab;
    TextView enteryfee, type, mode, map;
    TextView rank1, rank2, rank3, rank4, rank5, rank6, rank7, rank8, roomid, password;
    TextView matchno, date, time, status, perkill;
    private DatabaseReference Post;
    TextView information;
    String dinfo, dstatus, dupid, dpaidentry, dwatchnow, dupdate, droomid, dpass;
    Button info, watchnow;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference dbrefrence = databaseReference.child("mainactivity").child("image4");
    ImageView bigimage;
    private DatabaseReference pubg_dialog_database;
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private HashMap<String, Object> firebaseDefaultMap;
    public static final String VERSION_CODE_KEY = "latest_app_version";
    private static final String TAG = "MainActivity";
    RewardedVideoAd rewardedVideoAd;
    EditText pubg_name, mobileno;
    private String st_payment = "";
    private DatabaseReference dbrefrence_join_player;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_image_);
        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        mAdView = findViewById(R.id.adView);
        mAdView2 = findViewById(R.id.adView2);
        mAdView3 = findViewById(R.id.adView3);


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView2.loadAd(adRequest);
        mAdView3.loadAd(adRequest);

        bigimage = findViewById(R.id.big_image);
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);
        init();
        readRealTime();

        firebaseDefaultMap = new HashMap<>();
        firebaseDefaultMap.put(VERSION_CODE_KEY, getCurrentVersionCode());
        mFirebaseRemoteConfig.setDefaults(firebaseDefaultMap);
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
                    Toast.makeText(FourImage_Activity.this, "Something went wrong please try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        dbrefrence_join_player = FirebaseDatabase.getInstance().getReference("Tdm_join_player").child("EntryComplete_player");

        SharedPreferences preferences = getSharedPreferences("signup", MODE_PRIVATE);
        String value = preferences.getString("number", " ");
        mobile = value;
        pubg_dialog_database = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);
        readReal();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);
        fab = findViewById(R.id.joinnow);
        info = findViewById(R.id.info);
        watchnow = findViewById(R.id.watchnow);
        fab.isAttachedToWindow();
        joinnow();joinnow();
    }

    private void readReal() {
        progressdialog.show();
//        Handler handler2 = new Handler();
//        handler2.postDelayed(
//                new Runnable() {
//                    public void run() {
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
//                    }
//                }, 1000L);


    }

    private void checkForUpdate() {
        int latestAppVersion = (int) mFirebaseRemoteConfig.getDouble(VERSION_CODE_KEY);
        if (latestAppVersion > getCurrentVersionCode()) {

            Intent intent = new Intent(FourImage_Activity.this, AppUpdaterActivity.class);
            startActivity(intent);
            finish();
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
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (null != chooser.resolveActivity(getPackageManager())) {
            Log.d("First", "UPI Payment resolved to activity");
            startActivityForResult(chooser, REQ_UPIPAYMENT);
        } else {
            Log.d("First", "No activity found to handle UPI Payment");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQ_UPIPAYMENT == requestCode) {
            if (RESULT_OK == resultCode) {
            } else if (resultCode == Activity.RESULT_OK) {

                String txnId = "txnId=null&Status=FAILURE";
                String Status = "txnId=&responseCode=ZD&Status=FAILURE&txnRef=";
                String transId = data.getStringExtra("response");
                Log.d("akjjd", "transId: " + transId);
                if (transId.equals("txnId=null&Status=FAILURE")) {
                    Toast.makeText(this, "Payment fail", Toast.LENGTH_SHORT).show();
                } else if (transId.equals("txnId=&responseCode=ZD&Status=FAILURE&txnRef=")) {
                    Toast.makeText(this, "Payment fail", Toast.LENGTH_SHORT).show();
                } else if (transId != txnId && transId != Status) {
                    joinPlayer();

                }
            }
        }
    }

    private void joinPlayer() {

        final String pubgname = pubg_name.getText().toString();
        final String mobile_no = mobileno.getText().toString();
        final String type = st_payment;


        firebaseAuth.createUserWithEmailAndPassword(pubgname, mobile_no)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        CompleteEntry completeEntry = new CompleteEntry(pubgname, mobile_no, type);
                        dbrefrence_join_player.child(pubgname).setValue(completeEntry)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            progressdialog.show();
                                            notification();
                                            Toast.makeText(FourImage_Activity.this, "Player Join Successfully", Toast.LENGTH_SHORT).show();
                                            progressdialog.dismiss();
                                        }
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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

        status = findViewById(R.id.status);
        roomid = findViewById(R.id.room_id);
        password = findViewById(R.id.room_password);
        matchno = findViewById(R.id.matchno);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        perkill = findViewById(R.id.perkill);
        info = findViewById(R.id.info);


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }
                ImageView cut;
                final Dialog dialog = new Dialog(FourImage_Activity.this);
                dialog.setContentView(R.layout.wrongno_dialog);
                Window window = dialog.getWindow();
                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo);

                dialog.show();
            }
        });

        Post = FirebaseDatabase.getInstance().getReference().child("deathmatch");
    }

    private void readRealTime() {
        progressdialog.show();
//        Handler handler2 = new Handler();
//        handler2.postDelayed(
//                new Runnable() {
//                    public void run() {
                        progressdialog.show();
                        Post.child("fouractivity")
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
                                        // String dperkill = dataSnapshot.child("perkill").getValue(String.class);
//                                        String drank6 = dataSnapshot.child("rank6").getValue(String.class);
//                                        String drank7 = dataSnapshot.child("rank7").getValue(String.class);
//                                        String drank8 = dataSnapshot.child("rank8").getValue(String.class);
//                                        String drank9 = dataSnapshot.child("rank9").getValue(String.class);
//                                        String drank10 = dataSnapshot.child("rank10").getValue(String.class);
                                        String dtime = dataSnapshot.child("time").getValue(String.class);
                                        String dmatchno = dataSnapshot.child("matchno").getValue(String.class);
                                        String ddate = dataSnapshot.child("date").getValue(String.class);
                                        String dperkill = dataSnapshot.child("perkill").getValue(String.class);
                                        dinfo = dataSnapshot.child("information").getValue(String.class);
                                        dstatus = dataSnapshot.child("status").getValue(String.class);
                                        dupid = dataSnapshot.child("upiid").getValue(String.class);
                                        dpaidentry = dataSnapshot.child("paidentry").getValue(String.class);
                                        dwatchnow = dataSnapshot.child("disabled").getValue(String.class);
                                        dupdate = dataSnapshot.child("update").getValue(String.class);
                                        final String st_watchnow = dataSnapshot.child("watchnow").getValue(String.class);
                                        droomid = dataSnapshot.child("roomid").getValue(String.class);
                                        dpass = dataSnapshot.child("password").getValue(String.class);
                                        enteryfee.setText(dentryfee);
                                        type.setText(dtype);
                                        mode.setText(dmode);
                                        map.setText(dmap);
                                        rank1.setText(drank1);
                                        rank2.setText(drank2);
                                        rank3.setText(drank3);
                                        rank4.setText(drank4);
                                        rank5.setText(drank5);
//                                        rank6.setText(drank6);
//                                        rank7.setText(drank7);
//                                        rank8.setText(drank8);
//                                        rank9.setText(drank9);
//                                        rank10.setText(drank10);
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
                                        Toast.makeText(FourImage_Activity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });
//                    }
//                }, 1000L);

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
                Glide.with(FourImage_Activity.this)
                        .load(firstimage)
                        .into(bigimage);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    public void joinnow() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CheckBox checkbox;
                final Button cancel, join_d;
                TextView pubg_id;
                ImageView about;
                final Spinner spin;

                final Dialog dialog1 = new Dialog(FourImage_Activity.this);
                dialog1.setContentView(R.layout.rewarded_video_layout);
                dialog1.setCancelable(false);
                Window window = dialog1.getWindow();
                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                spin = (Spinner) dialog1.findViewById(R.id.spinner);

                final ArrayAdapter aa = new ArrayAdapter(FourImage_Activity.this, android.R.layout.simple_spinner_item, country);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(aa);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // your code here
                        Toast.makeText(FourImage_Activity.this, "spin", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });

                HintSpinner<String> hintSpinner2 = new HintSpinner<>(
                        spin,

                        new HintAdapter<String>(FourImage_Activity.this, R.string.hints, Arrays.asList(country)),
                        new HintSpinner.Callback<String>() {
                            @Override
                            public void onItemSelected(int position, String itemAtPosition) {

                            }
                        });
                hintSpinner2.init();
                about = dialog1.findViewById(R.id.about);
                checkbox = dialog1.findViewById(R.id.checkbox_terms);
                cancel = dialog1.findViewById(R.id.cancel_button);
                join_d = dialog1.findViewById(R.id.join_button);
                pubg_id = dialog1.findViewById(R.id.pubg_id_dialog);
                pubg_name = dialog1.findViewById(R.id.pubg_name_dialog);
                mobileno = dialog1.findViewById(R.id.edt_mobileno);
                pubg_name.setText(st_name);
                pubg_id.setText(st_pubgid);
                about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog2 = new Dialog(FourImage_Activity.this);
                        dialog2.setContentView(R.layout.about_dialog);
                        Window window = dialog1.getWindow();
                        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                        dialog2.show();
                    }
                });

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
                        if (TextUtils.isEmpty(pubg_name.getText().toString())) {
                            Toast.makeText(FourImage_Activity.this, "PUBG Name can not be Empty", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(mobileno.getText().toString())) {
                            Toast.makeText(FourImage_Activity.this, "Mobile No. can not be Empty", Toast.LENGTH_SHORT).show();
                        } else if (mobileno.getText().toString().length() != 10) {
                            Toast.makeText(FourImage_Activity.this, "Enter valid Mobile no.", Toast.LENGTH_SHORT).show();
                        } else if (!(mobileno.getText().toString().startsWith("7") ||
                                mobileno.getText().toString().startsWith("8") ||
                                mobileno.getText().toString().startsWith("9"))) {
                            mobileno.setError("Please Enter valid  Mobile Number");
                        } else {
                            launchUPI();
                        }
                    }
                });
                dialog1.show();


            }
        });
    }

    private void notification() {

        String name = pubg_name.getText().toString();

        String message = " Your Entry for TDM Match are Complete ,Room Id and Password share before 10 Minutes to Match Start. Check in info button ";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText("Code Sphere")
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .setAutoCancel(false)
                .setContentText(name + message);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

}

