package com.pankajranag.rana_gaming.Lite_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.StorageReference;
import com.pankajranag.rana_gaming.Activity.ArcadeLoadPlayer_Activity;
import com.pankajranag.rana_gaming.Activity.ForAllCoins_Activity;
import com.pankajranag.rana_gaming.Activity.refund;
import com.pankajranag.rana_gaming.JavaClass.CompleteEntry;
import com.pankajranag.rana_gaming.kotlin.Notification;
import com.pankajranag.rana_gaming.Activity.Announcment_Activity;
import com.pankajranag.rana_gaming.Activity.AppUpdaterActivity;
import com.pankajranag.rana_gaming.Activity.MixLogin;
import com.pankajranag.rana_gaming.Activity.MyProfile_Activity;
import com.pankajranag.rana_gaming.Activity.Terms_ConditionActivity;
import com.pankajranag.rana_gaming.BuildConfig;

import com.pankajranag.rana_gaming.R;

import java.net.URLEncoder;
import java.util.HashMap;

public class MainPubg_Lite extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RewardedVideoAdListener {

    private static final int REQUEST_INVITE = 100;
    private static final String TAG = "Loading Data ...";
    private static final int PICKFILE_REQUEST_CODE = 1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private ProgressDialog progressdialog;
    String mobile, st_share, st_dialog, st_group, st_telegram, st_instagram;
    ImageView first, second, third, four, five, six, seven, eight;
    LinearLayout header, firstlayout, secondlayout, image_header;
    private DatabaseReference databaseReferencee;
    TextView name;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference dbrefrence = databaseReference.child("pubglite").child("image1");
    private DatabaseReference dbrefrence2 = databaseReference.child("pubglite").child("image2");
    private DatabaseReference dbrefrence3 = databaseReference.child("pubglite").child("image3");
    private DatabaseReference dbrefrence4 = databaseReference.child("pubglite").child("image4");
    private DatabaseReference dbrefrence5 = databaseReference.child("pubglite").child("image5");
    private DatabaseReference dbrefrence6 = databaseReference.child("pubglite").child("image6");
    private DatabaseReference dbrefrence7 = databaseReference.child("pubglite").child("image7");
    private DatabaseReference dbrefrence8 = databaseReference.child("pubglite").child("image8");
    private DatabaseReference dialog = databaseReference.child("pubglite").child("dialog");
    private DatabaseReference dialog_db;
    String dupdate, st_mobileno,st_dialogmsg;
    FirebaseUser user;
    private DatabaseReference Post, post_classic, post_arcade, post_tdm, classic_solo, classic_duo;
    DatabaseReference db_load_solo;
    private FirebaseAuth firebaseAuth;
    ImageView tol_image;
    ProgressBar progressBar;
    String uri = "https://www.youtube.com/watch?v=566uQctPFrw";
    boolean doublePressed = true;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private HashMap<String, Object> firebaseDefaultMap;
    public static final String VERSION_CODE_KEY = "latest_app_version";
    RewardedVideoAd rewardedVideoAd;
    private TextView pubg, txt_coins;
    String[] chose = {"Cash", "Coins"};
    private AdView mAdView, mAdView2, mAdview3, mAdview4, mAdview5;
    String dinfo, dstatus, dpaidentry, dupid, dwatchnow, droomid, dpass, dmatchno, drank1, drank2, drank3, drank4, dwinner3;
    String dinfo2, dstatus2, dupid2, dpaidentry2, dwatchnow2, dupdate2, st_watchnow, droomid2, dpass2;
    String dinfo5, dstatus5, dupid5, dpaidentry5, dwatchnow5, dupdate5, droomid5, dpass5;
    String dinfo3, dstatus3, dupid3, dpaidentry3, dwatchnow3, dupdate3, droomid3, dpass3;
    String dinfo4, dstatus4, dupid4, dpaidentry4, dwatchnow4, dupdate4, droomid4, dpass4;
    String st_solo1, st_solo2, st_solo3, st_solo4, dwinner1, dmatchno1;
    String st_duo1, st_duo2, st_duo3, st_duo4, dwinner2, dmatchno2;
    String st_arca1, st_arca2, st_arca3, st_arca4, dwinner4, dmatchno4, st_coins;
    String st_tdm1, st_tdm2, st_tdm3, st_tdm4, dwinner5, dmatchno5, st_prizepool5,main_msg, pos_msg, wt_msg;
    private DatabaseReference dbrefrence_join_player, classic_solo_join, classic_duo_join, arcade_join, tdm_join;
    Button fab, joinnow2, joinnow3, joinnow4, joinnow5;
    TextView information;
    Button info, watchnow, info3, info4, info5;
    Button info2, watchnow2;
    Button joinplayer_btn, joinplayer2_btn, joinplayer3_btn, joinplayer4_btn, joinplayer5_btn;

    String[] country = {"PayTm", "GooglePay", "PhonePay"};
    EditText pubg_name, mobileno;
    private String st_payment = "";
    private static final int REQ_UPIPAYMENT = 1;
    private static final int REQ_UPIPAYMENT2 = 1;
    private static final int REQ_UPIPAYMENT3 = 1;
    private static final int REQ_UPIPAYMENT4 = 1;
    private static final int REQ_UPIPAYMENT5 = 1;
    SeekBar seek_bar, seekBar2, seekBar3, seekBar4, seekBar5;
    TextView matchno, date, time, registration, roonid, password, prizepool, perkill, entryfee, type, mode, map, text_view, text_view2, text_view3, text_view4, text_view5;
    TextView matchno2, date2, time2, registration2, roonid2, password2, prizepool2, perkill2, entryfee2, type2, mode2, map2;
    TextView matchno3, date3, time3, registration3, roonid3, password3, prizepool3, perkill3, entryfee3, type3, mode3, map3;
    TextView matchno4, date4, time4, registration4, roonid4, password4, prizepool4, perkill4, entryfee4, type4, mode4, map4;
    TextView matchno5, date5, time5, registration5, roonid5, password5, prizepool5, perkill5, entryfee5, type5, mode5, map5;
    TextView prizepool_anim1, prizepool_anim2, prizepool_anim3, prizepool_anim4, prizepool_anim5;
    int solo_btn_click;
    DatabaseReference solo_seekbar_database, duo_seekbar_database, squad_seekbar_database, arcade_seekbar_database, tdm_seekbar_database;
    private boolean two_time = true;
    InterstitialAd mInterstitialAd;
    AdRequest initial_ad;
    TextView dialog_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pubg__lite);
        firstlayout = findViewById(R.id.first_linearlayout);
        secondlayout = findViewById(R.id.second_linearlayout);

        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_add));
        initial_ad = new AdRequest.Builder().build();

        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        pubg = findViewById(R.id.pubg);
        pubg.setVisibility(View.GONE);
        header = findViewById(R.id.header);
        firebaseAuth = FirebaseAuth.getInstance();

        first = (ImageView) findViewById(R.id.imageone);
        second = (ImageView) findViewById(R.id.imageone2);
        third = (ImageView) findViewById(R.id.image3);
        four = (ImageView) findViewById(R.id.image4);
        five = (ImageView) findViewById(R.id.imageone5);
        six = (ImageView) findViewById(R.id.image6);
        seven = (ImageView) findViewById(R.id.image7);
        eight = (ImageView) findViewById(R.id.image8);
        prizepool_anim1 = findViewById(R.id.first_prizepoolanim);
        prizepool_anim2 = findViewById(R.id.second_prizepoolanim);
        prizepool_anim3 = findViewById(R.id.third_prizepoolanim);
        prizepool_anim4 = findViewById(R.id.four_prizepoolanim);
        prizepool_anim5 = findViewById(R.id.five_prizepoolanim);

        prizepool_anim3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prizePoolMatchno3();
            }
        });
        prizepool_anim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prizePoolMatchno1();
            }
        });
        prizepool_anim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prizePoolMatchno2();
            }
        });
        prizepool_anim4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prizePoolMatchno4();
            }
        });
        prizepool_anim5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prizePoolMatchno5();
            }
        });

        joinplayer_btn = findViewById(R.id.watchnow);
        joinplayer2_btn = findViewById(R.id.watchnow2);
        joinplayer3_btn = findViewById(R.id.watchnow3);
        joinplayer4_btn = findViewById(R.id.watchnow4);
        joinplayer5_btn = findViewById(R.id.watchnow5);

        joinplayer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlayer();
            }
        });
        joinplayer2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlayer2();
            }
        });
        joinplayer3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlayer3();
            }
        });
        joinplayer4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlayer4();
            }
        });
        joinplayer5_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlayer5();
            }
        });

//        final View anim1 = (View) findViewById(R.id.anim1);
//        final View anim2 = (View) findViewById(R.id.anim2);
//        final View anim3 = (View) findViewById(R.id.anim3);
//        final View anim4 = (View) findViewById(R.id.anim4);
//
//        anim1.setVisibility(View.GONE);
//        anim2.setVisibility(View.GONE);
//        anim3.setVisibility(View.GONE);
//        anim4.setVisibility(View.GONE);
//
//        final Animation animation1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bc_bubble_scaling);
//        final Animation animation2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bc_bubble_scaling);
//        final Animation animation3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bc_bubble_scaling);
//        final Animation animation4 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bc_bubble_scaling);
//
//        animation4.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                anim4.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//        animation3.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                anim3.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//        animation2.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                anim2.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        animation1.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation)
//            {
//                anim1.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        everyId();


        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        name = headerview.findViewById(R.id.name);
        TextView phoneno = headerview.findViewById(R.id.phoneno);
        tol_image = headerview.findViewById(R.id.header_profileimage);
        image_header = headerview.findViewById(R.id.header);
        txt_coins = headerview.findViewById(R.id.coins);
        SharedPreferences preferences = getSharedPreferences("signup", MODE_PRIVATE);
        String value = preferences.getString("number", " ");
        mobile = value;
        phoneno.setText(mobile);
        progressBar = findViewById(R.id.pb_profile_loader);
        user = firebaseAuth.getCurrentUser();
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(tol_image);

            }
        }
        image_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainPubg_Lite.this, "1 coins = 1 Rs. Send Whatsapp message to Reddem Coins", Toast.LENGTH_LONG).show();

            }
        });


        hideMenuItem();
        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);
        headerData();


        final SwitchCompat item = (SwitchCompat) navigationView.getMenu().findItem(R.id.nav_item1).getActionView();
        item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    item.setChecked(true);

                    presentActivity(buttonView);


                } else if (!isChecked) {
                    seek_bar.setVisibility(View.GONE);
                    seekBar2.setVisibility(View.GONE);
                    seekBar3.setVisibility(View.GONE);
                    seekBar4.setVisibility(View.GONE);
                    seekBar5.setVisibility(View.GONE);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });

        Post = FirebaseDatabase.getInstance().getReference().child("pubglite");
        post_classic = FirebaseDatabase.getInstance().getReference().child("varengalite");
        post_arcade = FirebaseDatabase.getInstance().getReference().child("arcademode");
        post_tdm = FirebaseDatabase.getInstance().getReference().child("LiteTdm");
        classic_solo = FirebaseDatabase.getInstance().getReference().child("LiteClassicSolo");
        classic_duo = FirebaseDatabase.getInstance().getReference().child("LiteClassicDuo");

        dbrefrence_join_player = FirebaseDatabase.getInstance().getReference("LiteSquad_join_player").child("EntryComplete_player");
        classic_solo_join = FirebaseDatabase.getInstance().getReference("LiteSolo_join_player").child("EntryComplete_player");
        classic_duo_join = FirebaseDatabase.getInstance().getReference("LiteDuo_join_player").child("EntryComplete_player");
        arcade_join = FirebaseDatabase.getInstance().getReference("LiteArcade_join_player").child("EntryComplete_player");
        tdm_join = FirebaseDatabase.getInstance().getReference("LiteTDM_join_player").child("EntryComplete_player");

        db_load_solo = FirebaseDatabase.getInstance().getReference("LiteSquad_join_player");

        solo_seekbar_database = FirebaseDatabase.getInstance().getReference("LiteSolo_join_player")
                .child("EntryComplete_player");

        duo_seekbar_database = FirebaseDatabase.getInstance().getReference("LiteDuo_join_player")
                .child("EntryComplete_player");

        squad_seekbar_database = FirebaseDatabase.getInstance().getReference("LiteSquad_join_player")
                .child("EntryComplete_player");

        arcade_seekbar_database = FirebaseDatabase.getInstance().getReference("LiteArcade_join_player")
                .child("EntryComplete_player");

        tdm_seekbar_database = FirebaseDatabase.getInstance().getReference("LiteTDM_join_player")
                .child("EntryComplete_player");

        readRealTime();



        mAdView2 = findViewById(R.id.adView2);
        firebaseAuth = FirebaseAuth.getInstance();


        AdRequest adRequest2 = new AdRequest.Builder().build();


        mAdView2.loadAd(adRequest2);


        firebaseDefaultMap = new HashMap<>();
        firebaseDefaultMap.put(VERSION_CODE_KEY, getCurrentVersionCode());
        mFirebaseRemoteConfig.setDefaults(firebaseDefaultMap);

        fab = findViewById(R.id.joinnow);
        joinnow2 = findViewById(R.id.joinnow2);
        joinnow3 = findViewById(R.id.joinnow3);
        joinnow4 = findViewById(R.id.joinnow4);
        joinnow5 = findViewById(R.id.joinnow5);
        info = findViewById(R.id.info);
        info2 = findViewById(R.id.info2);
        info3 = findViewById(R.id.info3);
        info4 = findViewById(R.id.info4);
        info5 = findViewById(R.id.info5);
        watchnow = findViewById(R.id.watchnow);

        joinnow();
        joinnow2();
        joinnow3();
        joinnow4();
        joinnow5();

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                ImageView cut;

                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.wrongno_dialog);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo);

                dialog.show();
            }

        });

        seebbarr();
        seebbarr2();
        seebbarr3();
        seebbarr4();
        seebbarr5();
        classicMode();
        classicSolo();
        classicDuo();
        arcadeMOde();
        tdmMode();

        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                ImageView cut;
                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.wrongno_dialog);
                Window window = dialog.getWindow();
                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo2);

                dialog.show();
            }
        });
        info5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                ImageView cut;
                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.wrongno_dialog);
                Window window = dialog.getWindow();
                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo5);

                dialog.show();
            }
        });

        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                ImageView cut;

                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.wrongno_dialog);
                Window window = dialog.getWindow();
                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo2);

                dialog.show();
            }

        });

        info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                ImageView cut;

                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.wrongno_dialog);
                Window window = dialog.getWindow();
                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo3);

                dialog.show();
            }

        });
        info4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                ImageView cut;

                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.wrongno_dialog);
                Window window = dialog.getWindow();
                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo4);

                dialog.show();
            }

        });

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
                    Toast.makeText(MainPubg_Lite.this, "Something went wrong please try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        //  ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wallet, menu);
        MenuItem notification = menu.findItem(R.id.refresh);
        MenuItem notification2 = menu.findItem(R.id.notification);
        notification2.setVisible(false);

        notification.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                Intent intent = new Intent(MainPubg_Lite.this, MainPubg_Lite.class);
                startActivity(intent);
                finish();

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void readRealTime() {
        progressdialog.show();
        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {
                        progressdialog.dismiss();
                        Post
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        progressdialog.dismiss();
                                        dupdate = dataSnapshot.child("update").getValue(String.class);
                                        st_share = dataSnapshot.child("share").getValue(String.class);
                                        st_dialog = dataSnapshot.child("disabled").getValue(String.class);
                                        st_group = dataSnapshot.child("group").getValue(String.class);
                                        st_telegram = dataSnapshot.child("telegram").getValue(String.class);
                                        st_instagram = dataSnapshot.child("Instagram").getValue(String.class);
                                        String st_both = dataSnapshot.child("solo").getValue(String.class);
                                        main_msg = dataSnapshot.child("msg").getValue(String.class);
                                        wt_msg = dataSnapshot.child("wt_msg").getValue(String.class);
                                        pos_msg = dataSnapshot.child("btn_msg").getValue(String.class);
                                        String duo = dataSnapshot.child("duo").getValue(String.class);
                                        st_dialogmsg = dataSnapshot.child("dialog_msg").getValue(String.class);
                                        st_mobileno = dataSnapshot.child("number").getValue(String.class);
                                        if (!st_both.isEmpty()) {
                                            firstlayout.setVisibility(View.VISIBLE);

                                        }
                                        if (!duo.isEmpty()) {
                                            secondlayout.setVisibility(View.VISIBLE);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                }, 1000L);


    }

    private void checkForUpdate() {
        int latestAppVersion = (int) mFirebaseRemoteConfig.getDouble(VERSION_CODE_KEY);
        if (latestAppVersion > getCurrentVersionCode()) {

            Intent intent = new Intent(MainPubg_Lite.this, AppUpdaterActivity.class);
            startActivity(intent);
            finish();
        } else {
            //   Toast.makeText(this, "This app is already up to date", Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
//        if (int id= navigationView.getMenu().findItem(R.id.nav_item1)) {
//            SpannableString s = new SpannableString(menuItem.getTitle());
//            s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance), 0, s.length(), 0);
//        }
        switch (id) {
            case R.id.home:
                DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.notification:
                startActivity(new Intent(this, Notification.class));
                break;
            case R.id.profile:
                Intent intent = new Intent(MainPubg_Lite.this, MyProfile_Activity.class);
                intent.putExtra("mobile_sign", mobile);
                startActivity(intent);
                break;

            case R.id.logout:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Alert!!");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setMessage("Do you want to logout?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                SharedPreferences sharedPreferences = getSharedPreferences("com.mobisoftseo.myapplication_login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("login status", "off");
                                editor.clear();
                                editor.apply();
                                editor.commit();
                                AuthUI.getInstance().signOut(MainPubg_Lite.this);
                                Intent i = new Intent(MainPubg_Lite.this, MixLogin.class);
                                startActivity(i);
                                finish();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // alertDialogBuilder.setCancelable(true);

                            }
                        });
                alertDialogBuilder.show();

                break;
            case R.id.refer:
                //  onInviteClicked();
                Uri imageUri;
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");

                String shareBody = "Hey! Take a Look Nox Gaming Application, Play Daily PUBG Mobile ,PUBG Lite and FreeFire Tournament and Win Cash Reward. ";

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody + st_share);
                startActivity(shareIntent);
                break;

            case R.id.help:
                sendMail();
                break;

            case R.id.room:
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=566uQctPFrw"));
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.setPackage("com.google.android.youtube");
                startActivity(intent2);

//                Intent intent1=new Intent(MainActivity.this,PiP_Activity.class);
//                intent1.putExtra("videoUrl",uri);
//                startActivity(intent1);
                break;

            case R.id.support:
                Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
                String url = st_group;
                intentWhatsapp.setData(Uri.parse(url));
                intentWhatsapp.setPackage("com.whatsapp");
                startActivity(intentWhatsapp);
                break;
            case R.id.instagram:
                Uri uri = Uri.parse(st_instagram);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(st_instagram)));
                }
                break;
            case R.id.telegram:
                Intent telegram = new Intent(Intent.ACTION_VIEW);
                String url2 = st_telegram;
                telegram.setData(Uri.parse(url2));
                startActivity(telegram);
                break;
            case R.id.privacy:
                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });

                startActivity(new Intent(this, Terms_ConditionActivity.class));
                break;
            case R.id.refund:
                mInterstitialAd.loadAd(initial_ad);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                startActivity(new Intent(this, refund.class));
                break;
            case R.id.top:

                startActivity(new Intent(this, Announcment_Activity.class));
                break;
//            case R.id.player:
//                startActivity(new Intent(this, SupportChat_Activity.class));
//                break;
            case R.id.player:
                startActivity(new Intent(this, ForAllCoins_Activity.class));
                break;

            case R.id.nav_item1:
                // progressdialog.show();

//                Handler handler2 = new Handler();
//                handler2.postDelayed(
//                        new Runnable() {
//                            public void run() {
//                                progressdialog.dismiss();
//
//                            }
//                        }, 1000L);


                break;
//
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        return false;
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }


    public void sendMail() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Make Sure!!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(main_msg);
        alertDialogBuilder.setPositiveButton(pos_msg,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        String phone = st_mobileno;
                        String message = wt_msg;
                        PackageManager packageManager = MainPubg_Lite.this.getPackageManager();
                        Intent i = new Intent(Intent.ACTION_VIEW);

                        try {
                            String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(message, "UTF-8");
                            i.setPackage("com.whatsapp");
                            i.setData(Uri.parse(url));
                            if (i.resolveActivity(packageManager) != null) {
                                MainPubg_Lite.this.startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Dont have",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // alertDialogBuilder.setCancelable(true);

                    }
                });
        alertDialogBuilder.show();


    }

    @Override
    protected void onStart() {
        super.onStart();

        dbrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstimage = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext())
                        .load(firstimage)
                        .into(first);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbrefrence2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstimage = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext())
                        .load(firstimage)
                        .into(second);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbrefrence3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstimage = dataSnapshot.getValue(String.class);

                Glide.with(getApplicationContext())
                        .load(firstimage)
                        .into(third);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbrefrence4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                final String firstimage = dataSnapshot.getValue(String.class);
//                progressdialog.show();

                Handler handler2 = new Handler();
                handler2.postDelayed(
                        new Runnable() {
                            public void run() {
                                progressdialog.dismiss();
                                Glide.with(getApplicationContext())
                                        .load(firstimage)
                                        .into(four);
                            }
                        }, 1000L);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbrefrence5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstimage = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext())
                        .load(firstimage)
                        .into(five);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbrefrence6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstimage = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext())
                        .load(firstimage)
                        .into(six);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbrefrence7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstimage = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext())
                        .load(firstimage)
                        .into(seven);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbrefrence8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstimage = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext())
                        .load(firstimage)
                        .into(eight);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dialog.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstimage = dataSnapshot.getValue(String.class);


                if (!firstimage.isEmpty()) {
                    ImageView imageView, cut;
                    final Dialog settingsDialog = new Dialog(MainPubg_Lite.this);
                    settingsDialog.setContentView(R.layout.header_image_dialog);
                    settingsDialog.setCancelable(false);
                    settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    settingsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    settingsDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

                    PhotoView photoView = (PhotoView) settingsDialog.findViewById(R.id.photo_view);
                    settingsDialog.setCancelable(true);


                    Glide.with(getApplicationContext())
                            .load(firstimage)
                            .into(photoView);
                    settingsDialog.show();
                } else {

                    final Dialog settingsDialog = new Dialog(MainPubg_Lite.this);
                    settingsDialog.setContentView(R.layout.header_image_dialog);
                    settingsDialog.setCancelable(false);
                    settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    settingsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    settingsDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    settingsDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void hideMenuItem() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.notification).setVisible(false);
        nav_Menu.findItem(R.id.profile).setVisible(true);
        nav_Menu.findItem(R.id.nav_item2).setVisible(false);
    }

    private void hideMenuItem2() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.notification).setVisible(false);
        nav_Menu.findItem(R.id.profile).setVisible(true);
        nav_Menu.findItem(R.id.nav_item1).setVisible(false);
    }

    private void Dark_mode() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_item2).setVisible(true);
        menu.findItem(R.id.nav_item1).setVisible(false);
    }

    private void light_Mode() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_item1).setVisible(false);
        menu.findItem(R.id.nav_item2).setVisible(true);
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
                            st_coins = dataSnapshot.child("litecoins").getValue(String.class);

                            txt_coins.setText(st_coins);

                            name.setText(st_name);

                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void showStartDialog() {
        ImageView imageView, dialog_fo;
        LinearLayout linearLayout;
        Button sub_dialog;
        final Dialog dialog = new Dialog(MainPubg_Lite.this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        Window window = dialog.getWindow();
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dialog_subscribe);
        dialog.setCancelable(false);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog_fo = dialog.findViewById(R.id.dialog_image);

        imageView = dialog.findViewById(R.id.cut);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();


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

    public void joinnow() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Spinner under_spin, under_coose_spin;
                final Button cancl, join;
                final CheckBox checkBoxx;
                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.coinstwo_dialog);
                dialog.setCancelable(false);
                Window window = dialog.getWindow();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog_text=dialog.findViewById(R.id.dialog_txt);
                dialog_text.setText(st_dialogmsg);
                pubg_name = dialog.findViewById(R.id.pubg_name_dialog);
                cancl = dialog.findViewById(R.id.cancel_button);
                join = dialog.findViewById(R.id.join_button);
                checkBoxx = dialog.findViewById(R.id.checkbox_terms);
                mobileno = dialog.findViewById(R.id.edt_mobileno);
                mobileno.setText(mobile);
                st_payment = "Coins";

                join.setEnabled(false);
                join.setBackgroundResource(R.color.light_gray);
                join.setTextColor(getResources().getColor(R.color.medium_gray));
                checkBoxx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checkBoxx.isChecked()) {

                            join.setVisibility(View.VISIBLE);
                            join.setEnabled(true);
                            checkBoxx.setChecked(true);
                            join.setBackgroundResource(R.color.light_green);
                            join.setTextColor(getResources().getColor(R.color.white));

                        } else {
                            // your code to  no checked checkbox
                            join.setEnabled(false);
                            checkBoxx.setChecked(false);
                            join.setBackgroundResource(R.color.light_gray);
                            join.setTextColor(getResources().getColor(R.color.medium_gray));

                        }
                    }
                });
                cancl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(pubg_name.getText().toString())) {
                            Toast.makeText(MainPubg_Lite.this, "PUBG Lite Name can not be Empty", Toast.LENGTH_SHORT).show();
                        } else {
                            solo_btn_click = 1;

                            checkCoins1();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();

            }
        });
    }

    public void joinnow2() {


        joinnow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Spinner under_spin, under_coose_spin;
                final Button cancl, join;
                final CheckBox checkBoxx;
                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.coinstwo_dialog);
                dialog.setCancelable(false);
                Window window = dialog.getWindow();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog_text=dialog.findViewById(R.id.dialog_txt);
                dialog_text.setText(st_dialogmsg);
                pubg_name = dialog.findViewById(R.id.pubg_name_dialog);
                cancl = dialog.findViewById(R.id.cancel_button);
                join = dialog.findViewById(R.id.join_button);
                checkBoxx = dialog.findViewById(R.id.checkbox_terms);
                mobileno = dialog.findViewById(R.id.edt_mobileno);
                mobileno.setText(mobile);
                st_payment = "Coins";

                join.setEnabled(false);
                join.setBackgroundResource(R.color.light_gray);
                join.setTextColor(getResources().getColor(R.color.medium_gray));
                checkBoxx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checkBoxx.isChecked()) {

                            join.setVisibility(View.VISIBLE);
                            join.setEnabled(true);
                            checkBoxx.setChecked(true);
                            join.setBackgroundResource(R.color.light_green);
                            join.setTextColor(getResources().getColor(R.color.white));

                        } else {
                            // your code to  no checked checkbox
                            join.setEnabled(false);
                            checkBoxx.setChecked(false);
                            join.setBackgroundResource(R.color.light_gray);
                            join.setTextColor(getResources().getColor(R.color.medium_gray));

                        }
                    }
                });
                cancl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(pubg_name.getText().toString())) {
                            Toast.makeText(MainPubg_Lite.this, "PUBG Lite Name can not be Empty", Toast.LENGTH_SHORT).show();
                        } else {
                            solo_btn_click = 2;

                            checkCoins2();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();

            }
        });
    }

    public void joinnow3() {

        joinnow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Spinner under_spin, under_coose_spin;
                final Button cancl, join;
                final CheckBox checkBoxx;
                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.coinstwo_dialog);
                dialog.setCancelable(false);
                Window window = dialog.getWindow();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog_text=dialog.findViewById(R.id.dialog_txt);
                dialog_text.setText(st_dialogmsg);
                pubg_name = dialog.findViewById(R.id.pubg_name_dialog);
                cancl = dialog.findViewById(R.id.cancel_button);
                join = dialog.findViewById(R.id.join_button);
                checkBoxx = dialog.findViewById(R.id.checkbox_terms);
                mobileno = dialog.findViewById(R.id.edt_mobileno);
                mobileno.setText(mobile);
                st_payment = "Coins";

                join.setEnabled(false);
                join.setBackgroundResource(R.color.light_gray);
                join.setTextColor(getResources().getColor(R.color.medium_gray));
                checkBoxx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checkBoxx.isChecked()) {

                            join.setVisibility(View.VISIBLE);
                            join.setEnabled(true);
                            checkBoxx.setChecked(true);
                            join.setBackgroundResource(R.color.light_green);
                            join.setTextColor(getResources().getColor(R.color.white));

                        } else {
                            // your code to  no checked checkbox
                            join.setEnabled(false);
                            checkBoxx.setChecked(false);
                            join.setBackgroundResource(R.color.light_gray);
                            join.setTextColor(getResources().getColor(R.color.medium_gray));

                        }
                    }
                });
                cancl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(pubg_name.getText().toString())) {
                            Toast.makeText(MainPubg_Lite.this, "PUBG Lite Name can not be Empty", Toast.LENGTH_SHORT).show();
                        } else {
                            solo_btn_click = 3;

                            checkCoins3();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();


            }
        });
    }

    public void joinnow4() {

        joinnow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Spinner under_spin, under_coose_spin;
                final Button cancl, join;
                final CheckBox checkBoxx;
                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.coinstwo_dialog);
                dialog.setCancelable(false);
                Window window = dialog.getWindow();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog_text=dialog.findViewById(R.id.dialog_txt);
                dialog_text.setText(st_dialogmsg);
                pubg_name = dialog.findViewById(R.id.pubg_name_dialog);
                cancl = dialog.findViewById(R.id.cancel_button);
                join = dialog.findViewById(R.id.join_button);
                checkBoxx = dialog.findViewById(R.id.checkbox_terms);
                mobileno = dialog.findViewById(R.id.edt_mobileno);
                mobileno.setText(mobile);
                st_payment = "Coins";

                join.setEnabled(false);
                join.setBackgroundResource(R.color.light_gray);
                join.setTextColor(getResources().getColor(R.color.medium_gray));
                checkBoxx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checkBoxx.isChecked()) {

                            join.setVisibility(View.VISIBLE);
                            join.setEnabled(true);
                            checkBoxx.setChecked(true);
                            join.setBackgroundResource(R.color.light_green);
                            join.setTextColor(getResources().getColor(R.color.white));

                        } else {
                            // your code to  no checked checkbox
                            join.setEnabled(false);
                            checkBoxx.setChecked(false);
                            join.setBackgroundResource(R.color.light_gray);
                            join.setTextColor(getResources().getColor(R.color.medium_gray));

                        }
                    }
                });
                cancl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(pubg_name.getText().toString())) {
                            Toast.makeText(MainPubg_Lite.this, "PUBG Lite Name can not be Empty", Toast.LENGTH_SHORT).show();
                        } else {
                            solo_btn_click = 4;

                            checkCoins4();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();


            }
        });
    }

    public void joinnow5() {

        joinnow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Spinner under_spin, under_coose_spin;
                final Button cancl, join;
                final CheckBox checkBoxx;
                final Dialog dialog = new Dialog(MainPubg_Lite.this);
                dialog.setContentView(R.layout.coinstwo_dialog);
                dialog.setCancelable(false);
                Window window = dialog.getWindow();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog_text=dialog.findViewById(R.id.dialog_txt);
                dialog_text.setText(st_dialogmsg);
                pubg_name = dialog.findViewById(R.id.pubg_name_dialog);
                cancl = dialog.findViewById(R.id.cancel_button);
                join = dialog.findViewById(R.id.join_button);
                checkBoxx = dialog.findViewById(R.id.checkbox_terms);
                mobileno = dialog.findViewById(R.id.edt_mobileno);
                mobileno.setText(mobile);
                st_payment = "Coins";

                join.setEnabled(false);
                join.setBackgroundResource(R.color.light_gray);
                join.setTextColor(getResources().getColor(R.color.medium_gray));
                checkBoxx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checkBoxx.isChecked()) {

                            join.setVisibility(View.VISIBLE);
                            join.setEnabled(true);
                            checkBoxx.setChecked(true);
                            join.setBackgroundResource(R.color.light_green);
                            join.setTextColor(getResources().getColor(R.color.white));

                        } else {
                            // your code to  no checked checkbox
                            join.setEnabled(false);
                            checkBoxx.setChecked(false);
                            join.setBackgroundResource(R.color.light_gray);
                            join.setTextColor(getResources().getColor(R.color.medium_gray));

                        }
                    }
                });
                cancl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(pubg_name.getText().toString())) {
                            Toast.makeText(MainPubg_Lite.this, "PUBG Lite Name can not be Empty", Toast.LENGTH_SHORT).show();
                        } else {
                            solo_btn_click = 5;

                            checkCoins5();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();

//                final CheckBox checkbox;
//                final Button cancel, join_d;
//                TextView pubg_id;
//                ImageView about;
//                final Spinner spin, choose_spin;
//                ScaleAnimation fade_in = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                fade_in.setDuration(1000);     // animation duration in milliseconds
//                fade_in.setFillAfter(true);
//                fade_in.setRepeatMode(5);
//                final Dialog dialog1 = new Dialog(MainPubg_Lite.this);
//                dialog1.setContentView(R.layout.pubglite_dialog);
//                dialog1.setCancelable(false);
//                Window window = dialog1.getWindow();
//                window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
//                spin = (Spinner) dialog1.findViewById(R.id.spinner);
//                choose_spin = dialog1.findViewById(R.id.top_spinner);
//
//                final ArrayAdapter aa = new ArrayAdapter(MainPubg_Lite.this, android.R.layout.simple_spinner_item, country);
//                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spin.setAdapter(aa);
//
//                final ArrayAdapter chooser = new ArrayAdapter(MainPubg_Lite.this, android.R.layout.simple_spinner_item, chose);
//                chooser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                choose_spin.setAdapter(chooser);
//
//                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                        st_payment = spin.getSelectedItem().toString();
//                        Toast.makeText(MainPubg_Lite.this, st_payment, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parentView) {
//                        // your code here
//                    }
//
//                });
//
//                choose_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                        if (choose_spin.getSelectedItem().equals("Coins")) {
//                            dialog1.dismiss();
//                            String[] item = {"Select", "Cash",};
//
//                            final Spinner under_spin, under_coose_spin;
//                            final Button cancl, join;
//                            final CheckBox checkBoxx;
//                            final Dialog dialog = new Dialog(MainPubg_Lite.this);
//                            dialog.setContentView(R.layout.coinstwo_dialog);
//                            dialog.setCancelable(false);
//                            Window window = dialog1.getWindow();
//                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            under_spin = (Spinner) dialog.findViewById(R.id.spinner);
//                            under_coose_spin = dialog.findViewById(R.id.top_spinner);
//                            pubg_name = dialog.findViewById(R.id.pubg_name_dialog);
//                            cancl = dialog.findViewById(R.id.cancel_button);
//                            join = dialog.findViewById(R.id.join_button);
//                            checkBoxx = dialog.findViewById(R.id.checkbox_terms);
//                            mobileno = dialog.findViewById(R.id.edt_mobileno);
//                            mobileno.setText(mobile);
//                            st_payment = "Coins";
//                            final ArrayAdapter chooser = new ArrayAdapter(MainPubg_Lite.this, android.R.layout.simple_spinner_item, item);
//                            chooser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            under_coose_spin.setAdapter(chooser);
//
//                            under_coose_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    if (under_coose_spin.getSelectedItem().equals("Cash")) {
//                                        dialog.dismiss();
//                                        dialog1.show();
//
//                                    }
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
//
//
//                            join.setEnabled(false);
//                            join.setBackgroundResource(R.color.light_gray);
//                            join.setTextColor(getResources().getColor(R.color.medium_gray));
//                            checkBoxx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                                @Override
//                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                    if (checkBoxx.isChecked()) {
//
//                                        join.setVisibility(View.VISIBLE);
//                                        join.setEnabled(true);
//                                        checkBoxx.setChecked(true);
//                                        join.setBackgroundResource(R.color.light_green);
//                                        join.setTextColor(getResources().getColor(R.color.white));
//
//                                    } else {
//                                        // your code to  no checked checkbox
//                                        join.setEnabled(false);
//                                        checkBoxx.setChecked(false);
//                                        join.setBackgroundResource(R.color.light_gray);
//                                        join.setTextColor(getResources().getColor(R.color.medium_gray));
//
//                                    }
//                                }
//                            });
//                            cancl.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                }
//                            });
//
//                            join.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (TextUtils.isEmpty(pubg_name.getText().toString())) {
//                                        Toast.makeText(MainPubg_Lite.this, "PUBG Name can not be Empty", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        solo_btn_click = 5;
//                                        checkCoins5();
//                                        dialog.dismiss();
//                                    }
//                                }
//                            });
//
//                            dialog.show();
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//
//                about = dialog1.findViewById(R.id.about);
//                checkbox = dialog1.findViewById(R.id.checkbox_terms);
//                cancel = dialog1.findViewById(R.id.cancel_button);
//                join_d = dialog1.findViewById(R.id.join_button);
//                pubg_id = dialog1.findViewById(R.id.pubg_id_dialog);
//                pubg_name = dialog1.findViewById(R.id.pubg_name_dialog);
//                mobileno = dialog1.findViewById(R.id.edt_mobileno);
//                about.startAnimation(fade_in);
//                about.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final Dialog dialog2 = new Dialog(MainPubg_Lite.this);
//                        dialog2.setContentView(R.layout.about_dialog);
//                        Window window = dialog1.getWindow();
//                        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
//                        dialog2.show();
//                    }
//                });
//
//                join_d.setEnabled(false);
//                join_d.setBackgroundResource(R.color.light_gray);
//                join_d.setTextColor(getResources().getColor(R.color.medium_gray));
//                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (checkbox.isChecked()) {
//
//                            join_d.setVisibility(View.VISIBLE);
//                            join_d.setEnabled(true);
//                            checkbox.setChecked(true);
//                            join_d.setBackgroundResource(R.color.light_green);
//                            join_d.setTextColor(getResources().getColor(R.color.white));
//
//                        } else {
//                            // your code to  no checked checkbox
//                            join_d.setEnabled(false);
//                            checkbox.setChecked(false);
//                            join_d.setBackgroundResource(R.color.light_gray);
//                            join_d.setTextColor(getResources().getColor(R.color.medium_gray));
//
//                        }
//                    }
//                });
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog1.dismiss();
//                    }
//                });
//                join_d.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (TextUtils.isEmpty(pubg_name.getText().toString())) {
//                            Toast.makeText(MainPubg_Lite.this, "PUBG Name can not be Empty", Toast.LENGTH_SHORT).show();
//                        } else if (TextUtils.isEmpty(mobileno.getText().toString())) {
//                            Toast.makeText(MainPubg_Lite.this, "Mobile No. can not be Empty", Toast.LENGTH_SHORT).show();
//                        } else if (mobileno.getText().toString().length() != 10) {
//                            Toast.makeText(MainPubg_Lite.this, "Enter valid Mobile no.", Toast.LENGTH_SHORT).show();
//                        } else if (!(mobileno.getText().toString().startsWith("7") ||
//                                mobileno.getText().toString().startsWith("8") ||
//                                mobileno.getText().toString().startsWith("9"))) {
//                            mobileno.setError("Please Enter valid  Mobile Number");
//                        } else if (st_payment.equals("") && st_payment.equals("PaymentType")) {
//                            Toast.makeText(MainPubg_Lite.this, "please select a payment type", Toast.LENGTH_SHORT).show();
//                        } else {
//                            solo_btn_click = 5;
//                            launchUPI5();
//                            dialog1.dismiss();
            }
//                    }
//                });
//                dialog1.show();

            //      }
        });
    }

    private void everyId() {

        matchno = findViewById(R.id.matchno);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        registration = findViewById(R.id.registration);
        roonid = findViewById(R.id.id);
        password = findViewById(R.id.password);
        prizepool = findViewById(R.id.prizepool);
        perkill = findViewById(R.id.perkill);
        entryfee = findViewById(R.id.entryfee);
        type = findViewById(R.id.type);
        mode = findViewById(R.id.mode);
        map = findViewById(R.id.map);

        matchno2 = findViewById(R.id.matchno2);
        date2 = findViewById(R.id.date2);
        time2 = findViewById(R.id.time2);
        registration2 = findViewById(R.id.registration2);
        roonid2 = findViewById(R.id.id2);
        password2 = findViewById(R.id.password2);
        prizepool2 = findViewById(R.id.prizepool2);
        perkill2 = findViewById(R.id.perkill2);
        entryfee2 = findViewById(R.id.entryfee2);
        type2 = findViewById(R.id.type2);
        mode2 = findViewById(R.id.mode2);
        map2 = findViewById(R.id.map2);

        matchno3 = findViewById(R.id.matchno3);
        date3 = findViewById(R.id.date3);
        time3 = findViewById(R.id.time3);
        registration3 = findViewById(R.id.registration3);
        roonid3 = findViewById(R.id.id3);
        password3 = findViewById(R.id.password3);
        prizepool3 = findViewById(R.id.prizepool3);
        perkill3 = findViewById(R.id.perkill3);
        entryfee3 = findViewById(R.id.entryfee3);
        type3 = findViewById(R.id.type3);
        mode3 = findViewById(R.id.mode3);
        map3 = findViewById(R.id.map3);

        matchno4 = findViewById(R.id.matchno4);
        date4 = findViewById(R.id.date4);
        time4 = findViewById(R.id.time4);
        registration4 = findViewById(R.id.registration4);
        roonid4 = findViewById(R.id.id4);
        password4 = findViewById(R.id.password4);
        prizepool4 = findViewById(R.id.prizepool4);
        perkill4 = findViewById(R.id.perkill4);
        entryfee4 = findViewById(R.id.entryfee4);
        type4 = findViewById(R.id.type4);
        mode4 = findViewById(R.id.mode4);
        map4 = findViewById(R.id.map4);

        matchno5 = findViewById(R.id.matchno5);
        date5 = findViewById(R.id.date5);
        time5 = findViewById(R.id.time5);
        registration5 = findViewById(R.id.registration5);
        roonid5 = findViewById(R.id.id5);
        password5 = findViewById(R.id.password5);
        prizepool5 = findViewById(R.id.prizepool5);
        perkill5 = findViewById(R.id.perkill5);
        entryfee5 = findViewById(R.id.entryfee5);
        type5 = findViewById(R.id.type5);
        mode5 = findViewById(R.id.mode5);
        map5 = findViewById(R.id.map5);

        seek_bar = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
        seekBar5 = (SeekBar) findViewById(R.id.seekBar5);

        password.setVisibility(View.VISIBLE);
        password2.setVisibility(View.VISIBLE);
        password3.setVisibility(View.VISIBLE);
        password4.setVisibility(View.VISIBLE);
        password5.setVisibility(View.VISIBLE);

        roonid.setVisibility(View.VISIBLE);
        roonid2.setVisibility(View.VISIBLE);
        roonid3.setVisibility(View.VISIBLE);
        roonid4.setVisibility(View.VISIBLE);
        roonid5.setVisibility(View.VISIBLE);

    }

    private void notification() {

        String name = pubg_name.getText().toString();

        String message = " Your entry are complete, room id and password share before 10 minutes to match start.";
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

    private void launchUPI2() {
        // look below for a reference to these parameters
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", dupid2)
                .appendQueryParameter("pn", "Nox Gaming")
                .appendQueryParameter("tn", "Custum room entry fee")
                .appendQueryParameter("am", dpaidentry2)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("tn", "Welcome, Win Cash Reward Good Luck")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (null != chooser.resolveActivity(getPackageManager())) {
            Log.d("First", "UPI Payment resolved to activity");
            startActivityForResult(chooser, REQ_UPIPAYMENT2);
        } else {
            Log.d("First", "No activity found to handle UPI Payment");
            Toast.makeText(this, "No application available to handle this request! Only UPI payment accept ", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchUPI3() {
        // look below for a reference to these parameters
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", dupid3)
                .appendQueryParameter("pn", "Nox Gaming")
                .appendQueryParameter("tn", "Custum room entry fee")
                .appendQueryParameter("am", dpaidentry3)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("tn", "Welcome, Win Cash Reward Good Luck")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (null != chooser.resolveActivity(getPackageManager())) {
            Log.d("First", "UPI Payment resolved to activity");
            startActivityForResult(chooser, REQ_UPIPAYMENT3);
        } else {
            Log.d("First", "No activity found to handle UPI Payment");
            Toast.makeText(this, "No application available to handle this request! Only UPI payment accept ", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchUPI4() {
        // look below for a reference to these parameters
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", dupid4)
                .appendQueryParameter("pn", "Nox Gaming")
                .appendQueryParameter("tn", "Custum room entry fee")
                .appendQueryParameter("am", dpaidentry4)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("tn", "Welcome, Win Cash Reward Good Luck")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (null != chooser.resolveActivity(getPackageManager())) {
            Log.d("First", "UPI Payment resolved to activity");
            startActivityForResult(chooser, REQ_UPIPAYMENT4);
        } else {
            Log.d("First", "No activity found to handle UPI Payment");
            Toast.makeText(this, "No application available to handle this request! Only UPI payment accept ", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchUPI5() {
        // look below for a reference to these parameters
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", dupid5)
                .appendQueryParameter("pn", "Nox Gaming")
                .appendQueryParameter("tn", "Custum room entry fee")
                .appendQueryParameter("am", dpaidentry5)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("tn", "Welcome, Win Cash Reward Good Luck")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (null != chooser.resolveActivity(getPackageManager())) {
            Log.d("First", "UPI Payment resolved to activity");
            startActivityForResult(chooser, REQ_UPIPAYMENT5);
        } else {
            Log.d("First", "No activity found to handle UPI Payment");
            Toast.makeText(this, "No application available to handle this request! Only UPI payment accept ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (solo_btn_click == 1) {
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

                        Toast.makeText(this, "payment failed", Toast.LENGTH_SHORT).show();
                    } else if (res_status.equals("SUCCESS")) {

                        joinSoloPlayer();

                    }


                }
            }
        }

        // for 2
        else if (solo_btn_click == 2) {
            if (REQ_UPIPAYMENT2 == requestCode) {

                if (resultCode == Activity.RESULT_OK) {

                    String txnId = "txnId=null&Status=FAILURE";
                    String Status = "txnId=&responseCode=ZD&Status=FAILURE&txnRef=";
                    String wrong = "txnId=&responseCode=&Status=FAILURE&txnRef=";
                    String transId = data.getStringExtra("response");
                    String res_status = data.getStringExtra("Status");

                    Log.d("akjjd", "statuss: " + res_status);
                    Log.d("akjjd", "transId: " + transId);

                    if (res_status.equals("FAILURE")) {

                        Toast.makeText(this, "payment failed", Toast.LENGTH_SHORT).show();
                    } else if (res_status.equals("SUCCESS")) {
                        joinDuoPlayer();
                    }

                    // txnId
                    //responseCode=0
                    // ApprovalRefNo
                    //Status
// txnRef

                }
            }
        }

        //for 3
        else if (solo_btn_click == 3) {
            if (REQ_UPIPAYMENT3 == requestCode) {
                if (resultCode == Activity.RESULT_OK) {

                    String txnId = "txnId=null&Status=FAILURE";
                    String Status = "txnId=&responseCode=ZD&Status=FAILURE&txnRef=";
                    String wrong = "txnId=&responseCode=&Status=FAILURE&txnRef=";
                    String transId = data.getStringExtra("response");
                    String res_status = data.getStringExtra("Status");

                    Log.d("akjjd", "statuss: " + res_status);
                    Log.d("akjjd", "transId: " + transId);

                    if (res_status.equals("FAILURE")) {

                        Toast.makeText(this, "payment failed", Toast.LENGTH_SHORT).show();
                    } else if (res_status.equals("SUCCESS")) {
                        joinSquadPlayer();
                    }

                    // txnId
                    //responseCode=0
                    // ApprovalRefNo
                    //Status
// txnRef

                }
            }
        }

        //for 4
        else if (solo_btn_click == 4) {
            if (REQ_UPIPAYMENT4 == requestCode) {


                if (resultCode == Activity.RESULT_OK) {

                    String txnId = "txnId=null&Status=FAILURE";
                    String Status = "txnId=&responseCode=ZD&Status=FAILURE&txnRef=";
                    String wrong = "txnId=&responseCode=&Status=FAILURE&txnRef=";
                    String transId = data.getStringExtra("response");
                    String res_status = data.getStringExtra("Status");

                    Log.d("akjjd", "statuss: " + res_status);
                    Log.d("akjjd", "transId: " + transId);

                    if (res_status.equals("FAILURE")) {
                        roonid4.setVisibility(View.VISIBLE);

                        Toast.makeText(this, "payment failed", Toast.LENGTH_SHORT).show();
                    } else if (res_status.equals("SUCCESS")) {
                        joinArcadePlayer();
                    }

                    // txnId
                    //responseCode=0
                    // ApprovalRefNo
                    //Status
// txnRef
                }

            }
        }

        // for 5
        else if (solo_btn_click == 5) {

            if (two_time == true && resultCode == Activity.RESULT_OK) {


                String txnId = "txnId=null&Status=FAILURE";
                String Status = "txnId=&responseCode=ZD&Status=FAILURE&txnRef=";
                String wrong = "txnId=&responseCode=&Status=FAILURE&txnRef=";
                String transId = data.getStringExtra("response");
                String res_status = data.getStringExtra("Status");

                Log.d("akjjd", "statuss: " + res_status);
                Log.d("akjjd", "transId: " + transId);

                if (res_status.equals("FAILURE")) {

                    Toast.makeText(this, "payment failed", Toast.LENGTH_SHORT).show();
                } else if (res_status.equals("SUCCESS")) {
                    joinTdmPlayer();
                }

            }

        }
    }

    private void joinSquadPlayer() {

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
                                            Toast.makeText(MainPubg_Lite.this, "Player Join Successfully", Toast.LENGTH_LONG).show();
                                            progressdialog.dismiss();
                                        }
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainPubg_Lite.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void joinSoloPlayer() {

        final String pubgname = pubg_name.getText().toString();
        final String mobile_no = mobileno.getText().toString();
        final String type = st_payment;


        firebaseAuth.createUserWithEmailAndPassword(pubgname, mobile_no)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        CompleteEntry completeEntry = new CompleteEntry(pubgname, mobile_no, type);
                        classic_solo_join.child(pubgname).setValue(completeEntry)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            progressdialog.show();
                                            notification();
                                            Toast.makeText(MainPubg_Lite.this, "Player Join Successfully", Toast.LENGTH_LONG).show();
                                            progressdialog.dismiss();
                                        }
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainPubg_Lite.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void joinDuoPlayer() {

        final String pubgname = pubg_name.getText().toString();
        final String mobile_no = mobileno.getText().toString();
        final String type = st_payment;


        firebaseAuth.createUserWithEmailAndPassword(pubgname, mobile_no)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        CompleteEntry completeEntry = new CompleteEntry(pubgname, mobile_no, type);
                        classic_duo_join.child(pubgname).setValue(completeEntry)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            progressdialog.show();
                                            notification();
                                            Toast.makeText(MainPubg_Lite.this, "Player Join Successfully", Toast.LENGTH_LONG).show();
                                            progressdialog.dismiss();
                                        }
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainPubg_Lite.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void joinArcadePlayer() {

        final String pubgname = pubg_name.getText().toString();
        final String mobile_no = mobileno.getText().toString();
        final String type = st_payment;


        firebaseAuth.createUserWithEmailAndPassword(pubgname, mobile_no)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        CompleteEntry completeEntry = new CompleteEntry(pubgname, mobile_no, type);
                        arcade_join.child(pubgname).setValue(completeEntry)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            progressdialog.show();
                                            notification();
                                            Toast.makeText(MainPubg_Lite.this, "Player Join Successfully", Toast.LENGTH_LONG).show();
                                            progressdialog.dismiss();
                                        }
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainPubg_Lite.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void joinTdmPlayer() {

        final String pubgname = pubg_name.getText().toString();
        final String mobile_no = mobileno.getText().toString();
        final String type = st_payment;


        firebaseAuth.createUserWithEmailAndPassword(pubgname, mobile_no)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        CompleteEntry completeEntry = new CompleteEntry(pubgname, mobile_no, type);
                        tdm_join.child(pubgname).setValue(completeEntry)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            progressdialog.show();
                                            notification();
                                            Toast.makeText(MainPubg_Lite.this, "Player Join Successfully", Toast.LENGTH_LONG).show();
                                            progressdialog.dismiss();
                                        }
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainPubg_Lite.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void seebbarr() {

        solo_seekbar_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "Join Player : " + dataSnapshot.getChildrenCount());
                    int count = 0;


                    seek_bar.setVisibility(View.GONE);

                    text_view = (TextView) findViewById(R.id.textView);

                    text_view.setText("Join Player : " + dataSnapshot.getChildrenCount() + " / " + seek_bar.getMax());
                    if (dataSnapshot.getChildrenCount() == 60) {
                        seek_bar.setVisibility(View.GONE);
                        fab.setText("MATCH FULL");
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainPubg_Lite.this, "match already full please try after some time!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        seek_bar.setVisibility(View.GONE);
                        fab.setText("JOIN NOW");
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                joinnow();
                            }
                        });

                    }
                    seek_bar.setVisibility(View.GONE);
                    seek_bar.setOnSeekBarChangeListener(
                            new SeekBar.OnSeekBarChangeListener() {

                                int progress_value;

                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    progress_value = progress;
                                    if (fromUser == false) {
                                        seekBar.setProgress(progress_value);
                                    }


                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            }
                    );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void seebbarr2() {
        duo_seekbar_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "Join Player : " + dataSnapshot.getChildrenCount());
                    int count = 0;


                    seekBar2.setVisibility(View.GONE);

                    text_view2 = (TextView) findViewById(R.id.textView2);
                    seekBar2.setVisibility(View.GONE);

                    text_view2.setText("Join Player : " + dataSnapshot.getChildrenCount() + " / " + seekBar2.getMax());
                    seekBar2.setVisibility(View.GONE);

                    if (dataSnapshot.getChildrenCount() == 60) {
                        seekBar2.setVisibility(View.GONE);

                        joinnow2.setText("MATCH FULL");
                        joinnow2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainPubg_Lite.this, "match already full please try after some time!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        seekBar2.setVisibility(View.GONE);

                        joinnow2.setText("JOIN NOW");
                        joinnow2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                joinnow2();
                            }
                        });

                    }
                    seekBar2.setVisibility(View.GONE);

                    seekBar2.setOnSeekBarChangeListener(
                            new SeekBar.OnSeekBarChangeListener() {

                                int progress_value;

                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    progress_value = progress;
                                    if (fromUser == false) {
                                        seekBar.setProgress(progress_value);
                                    }


                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            }
                    );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void seebbarr3() {
        squad_seekbar_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "Join Player : " + dataSnapshot.getChildrenCount());
                    int count = 0;


                    seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
                    seekBar3.setVisibility(View.GONE);

                    text_view3 = (TextView) findViewById(R.id.textView3);

                    text_view3.setText("Join Player : " + dataSnapshot.getChildrenCount() + " / " + seekBar3.getMax());
                    seekBar3.setVisibility(View.GONE);

                    if (dataSnapshot.getChildrenCount() == 60) {
                        seekBar3.setVisibility(View.GONE);

                        joinnow3.setText("MATCH FULL");
                        joinnow3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainPubg_Lite.this, "match already full please try after some time!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        seekBar3.setVisibility(View.GONE);

                        joinnow3.setText("JOIN NOW");
                        joinnow3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                joinnow3();
                            }
                        });

                    }
                    seekBar3.setVisibility(View.GONE);

                    seekBar3.setOnSeekBarChangeListener(
                            new SeekBar.OnSeekBarChangeListener() {

                                int progress_value;

                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    progress_value = progress;
                                    if (fromUser == false) {
                                        seekBar.setProgress(progress_value);
                                    }


                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            }
                    );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void seebbarr4() {
        seekBar4.setVisibility(View.GONE);
        arcade_seekbar_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "Join Player : " + dataSnapshot.getChildrenCount());
                    int count = 0;


                    seekBar4.setVisibility(View.GONE);

                    text_view4 = (TextView) findViewById(R.id.textView4);

                    text_view4.setText("Join Player : " + dataSnapshot.getChildrenCount() + " / " + seekBar4.getMax());
                    if (dataSnapshot.getChildrenCount() == 28) {
                        seekBar4.setVisibility(View.GONE);
                        joinnow4.setText("MATCH FULL");
                        joinnow4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainPubg_Lite.this, "match already full please try after some time!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        joinnow4.setText("JOIN NOW");
                        joinnow4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                joinnow4();
                            }
                        });

                    }
                    seekBar4.setOnSeekBarChangeListener(
                            new SeekBar.OnSeekBarChangeListener() {

                                int progress_value;

                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    progress_value = progress;
                                    if (fromUser == false) {
                                        seekBar.setProgress(progress_value);
                                    }


                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            }
                    );
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void seebbarr5() {
        seekBar5.setVisibility(View.GONE);

        tdm_seekbar_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "Join Player : " + dataSnapshot.getChildrenCount());
                    int count = 0;


                    seekBar5 = (SeekBar) findViewById(R.id.seekBar5);
                    seekBar5.setVisibility(View.GONE);

                    text_view5 = (TextView) findViewById(R.id.textView5);

                    text_view5.setText("Join Player : " + dataSnapshot.getChildrenCount() + " / " + seekBar5.getMax());
                    seekBar5.setVisibility(View.GONE);

                    if (dataSnapshot.getChildrenCount() == 8) {
                        seekBar5.setVisibility(View.GONE);

                        joinnow5.setText("MATCH FULL");
                        joinnow5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainPubg_Lite.this, "match already full please try after some time!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        joinnow5.setText("JOIN NOW");
                        joinnow5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                joinnow5();
                            }
                        });

                    }
                    seekBar5.setOnSeekBarChangeListener(
                            new SeekBar.OnSeekBarChangeListener() {

                                int progress_value;

                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    progress_value = progress;
                                    if (fromUser == false) {
                                        seekBar.setProgress(progress_value);
                                    }


                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            }
                    );
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void classicMode() {

        progressdialog.show();
        post_classic.child("secondactivity")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressdialog.dismiss();
                        String dentryfee = dataSnapshot.child("entryfee").getValue(String.class);
                        String dtype = dataSnapshot.child("type").getValue(String.class);
                        String dmode = dataSnapshot.child("mode").getValue(String.class);
                        String dmap = dataSnapshot.child("map").getValue(String.class);
                        drank1 = dataSnapshot.child("rank1").getValue(String.class);
                        drank2 = dataSnapshot.child("rank2").getValue(String.class);
                        drank3 = dataSnapshot.child("rank3").getValue(String.class);
                        drank4 = dataSnapshot.child("rank4").getValue(String.class);
                        String drank5 = dataSnapshot.child("rank5").getValue(String.class);
                        String drank6 = dataSnapshot.child("rank6").getValue(String.class);
                        String drank7 = dataSnapshot.child("rank7").getValue(String.class);
                        String drank8 = dataSnapshot.child("rank8").getValue(String.class);
                        String drank9 = dataSnapshot.child("rank9").getValue(String.class);
                        String drank10 = dataSnapshot.child("rank10").getValue(String.class);
                        String dtime = dataSnapshot.child("time").getValue(String.class);
                        dmatchno = dataSnapshot.child("matchno").getValue(String.class);
                        String ddate = dataSnapshot.child("date").getValue(String.class);
                        String dperkill = dataSnapshot.child("perkill").getValue(String.class);
                        dinfo3 = dataSnapshot.child("information").getValue(String.class);
                        dstatus3 = dataSnapshot.child("status").getValue(String.class);
                        dpaidentry3 = dataSnapshot.child("paidentry").getValue(String.class);
                        dupid3 = dataSnapshot.child("upiid").getValue(String.class);
                        dwatchnow3 = dataSnapshot.child("disabled").getValue(String.class);
                        dupdate3 = dataSnapshot.child("update").getValue(String.class);
                        droomid3 = dataSnapshot.child("roomid").getValue(String.class);
                        dpass3 = dataSnapshot.child("password").getValue(String.class);
                        String dprizepool = dataSnapshot.child("prizepool").getValue(String.class);
                        dwinner3 = dataSnapshot.child("winner").getValue(String.class);

                        matchno3.setText(dmatchno);
                        date3.setText(ddate);
                        time3.setText(dtime);
                        roonid3.setText(droomid3);
                        password3.setText(dpass3);
                        registration3.setText(dstatus3);
                        perkill3.setText(dperkill);
                        entryfee3.setText(dentryfee);
                        type3.setText(dtype);
                        mode3.setText(dmode);
                        map3.setText(dmap);
                        prizepool3.setText(dprizepool);
                        if (!dwatchnow3.isEmpty()) {
                            joinnow3.setVisibility(View.GONE);
                        } else {
                            joinnow3.setVisibility(View.VISIBLE);

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    private void classicSolo() {

        progressdialog.show();
        classic_solo.child("SoloMatch")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressdialog.dismiss();
                        String dentryfee = dataSnapshot.child("entryfee").getValue(String.class);
                        String dtype = dataSnapshot.child("type").getValue(String.class);
                        String dmode = dataSnapshot.child("mode").getValue(String.class);
                        String dmap = dataSnapshot.child("map").getValue(String.class);
                        st_solo1 = dataSnapshot.child("rank1").getValue(String.class);
                        st_solo2 = dataSnapshot.child("rank2").getValue(String.class);
                        st_solo3 = dataSnapshot.child("rank3").getValue(String.class);
                        st_solo4 = dataSnapshot.child("rank4").getValue(String.class);
                        String drank5 = dataSnapshot.child("rank5").getValue(String.class);
                        String drank6 = dataSnapshot.child("rank6").getValue(String.class);
                        String drank7 = dataSnapshot.child("rank7").getValue(String.class);
                        String drank8 = dataSnapshot.child("rank8").getValue(String.class);
                        String drank9 = dataSnapshot.child("rank9").getValue(String.class);
                        String drank10 = dataSnapshot.child("rank10").getValue(String.class);
                        String dtime = dataSnapshot.child("time").getValue(String.class);
                        dmatchno1 = dataSnapshot.child("matchno").getValue(String.class);
                        String ddate = dataSnapshot.child("date").getValue(String.class);
                        String dperkill = dataSnapshot.child("perkill").getValue(String.class);
                        dinfo = dataSnapshot.child("information").getValue(String.class);
                        dstatus = dataSnapshot.child("registration").getValue(String.class);
                        dpaidentry = dataSnapshot.child("paidentry").getValue(String.class);
                        dupid = dataSnapshot.child("upiid").getValue(String.class);
                        dwatchnow = dataSnapshot.child("disabled").getValue(String.class);
                        dupdate = dataSnapshot.child("update").getValue(String.class);
                        droomid = dataSnapshot.child("id").getValue(String.class);
                        dpass = dataSnapshot.child("password").getValue(String.class);
                        String dprizepool = dataSnapshot.child("prizepool").getValue(String.class);
                        dwinner1 = dataSnapshot.child("winner").getValue(String.class);

                        matchno.setText(dmatchno1);
                        date.setText(ddate);
                        time.setText(dtime);
                        roonid.setText(droomid);
                        password.setText(dpass);
                        registration.setText(dstatus);
                        perkill.setText(dperkill);
                        entryfee.setText(dentryfee);
                        type.setText(dtype);
                        mode.setText(dmode);
                        map.setText(dmap);
                        prizepool.setText(dprizepool);
                        if (!dwatchnow.isEmpty()) {
                            fab.setVisibility(View.GONE);
                        } else if (dwatchnow.isEmpty()) {
                            fab.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    private void classicDuo() {

        progressdialog.show();
        classic_duo.child("DuoMatch")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressdialog.dismiss();
                        String dentryfee = dataSnapshot.child("entryfee").getValue(String.class);
                        String dtype = dataSnapshot.child("type").getValue(String.class);
                        String dmode = dataSnapshot.child("mode").getValue(String.class);
                        String dmap = dataSnapshot.child("map").getValue(String.class);
                        st_duo1 = dataSnapshot.child("rank1").getValue(String.class);
                        st_duo2 = dataSnapshot.child("rank2").getValue(String.class);
                        st_duo3 = dataSnapshot.child("rank3").getValue(String.class);
                        st_duo4 = dataSnapshot.child("rank4").getValue(String.class);
                        String drank5 = dataSnapshot.child("rank5").getValue(String.class);
                        String drank6 = dataSnapshot.child("rank6").getValue(String.class);
                        String drank7 = dataSnapshot.child("rank7").getValue(String.class);
                        String drank8 = dataSnapshot.child("rank8").getValue(String.class);
                        String drank9 = dataSnapshot.child("rank9").getValue(String.class);
                        String drank10 = dataSnapshot.child("rank10").getValue(String.class);
                        String dtime = dataSnapshot.child("time").getValue(String.class);
                        dmatchno2 = dataSnapshot.child("matchno").getValue(String.class);
                        String ddate = dataSnapshot.child("date").getValue(String.class);
                        String dperkill = dataSnapshot.child("perkill").getValue(String.class);
                        dinfo2 = dataSnapshot.child("information").getValue(String.class);
                        dstatus2 = dataSnapshot.child("registration").getValue(String.class);
                        dpaidentry2 = dataSnapshot.child("paidentry").getValue(String.class);
                        dupid2 = dataSnapshot.child("upiid").getValue(String.class);
                        dwatchnow2 = dataSnapshot.child("disabled").getValue(String.class);
                        dupdate2 = dataSnapshot.child("update").getValue(String.class);
                        droomid2 = dataSnapshot.child("id").getValue(String.class);
                        dpass2 = dataSnapshot.child("password").getValue(String.class);
                        String dprizepool = dataSnapshot.child("prizepool").getValue(String.class);
                        dwinner2 = dataSnapshot.child("winner").getValue(String.class);


                        matchno2.setText(dmatchno2);
                        date2.setText(ddate);
                        time2.setText(dtime);
                        roonid2.setText(droomid2);
                        password2.setText(dpass2);
                        registration2.setText(dstatus2);
                        perkill2.setText(dperkill);
                        entryfee2.setText(dentryfee);
                        type2.setText(dtype);
                        mode2.setText(dmode);
                        map2.setText(dmap);
                        prizepool2.setText(dprizepool);
                        if (!dwatchnow2.isEmpty()) {
                            joinnow2.setVisibility(View.GONE);
                        } else if (dwatchnow2.isEmpty()) {
                            joinnow2.setVisibility(View.VISIBLE);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    private void arcadeMOde() {
        progressdialog.show();

        progressdialog.show();
        post_arcade.child("secondactivity")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressdialog.dismiss();
                        String dentryfee = dataSnapshot.child("entryfee").getValue(String.class);
                        String dtype = dataSnapshot.child("type").getValue(String.class);
                        String dmode = dataSnapshot.child("mode").getValue(String.class);
                        String dmap = dataSnapshot.child("map").getValue(String.class);
                        st_arca1 = dataSnapshot.child("rank1").getValue(String.class);
                        st_arca2 = dataSnapshot.child("rank2").getValue(String.class);
                        st_arca3 = dataSnapshot.child("rank3").getValue(String.class);
                        st_arca4 = dataSnapshot.child("rank4").getValue(String.class);
                        String rank5 = dataSnapshot.child("rank5").getValue(String.class);

                        String dtime = dataSnapshot.child("time").getValue(String.class);
                        dmatchno4 = dataSnapshot.child("matchno").getValue(String.class);
                        String ddate = dataSnapshot.child("date").getValue(String.class);
                        String dperkill = dataSnapshot.child("perkill").getValue(String.class);
                        dwinner4 = dataSnapshot.child("winner").getValue(String.class);
                        dinfo4 = dataSnapshot.child("information").getValue(String.class);
                        dstatus4 = dataSnapshot.child("status").getValue(String.class);
                        dupid4 = dataSnapshot.child("upiid").getValue(String.class);
                        dpaidentry4 = dataSnapshot.child("paidentry").getValue(String.class);
                        dwatchnow4 = dataSnapshot.child("disabled").getValue(String.class);
                        dupdate4 = dataSnapshot.child("update").getValue(String.class);
                        st_watchnow = dataSnapshot.child("watchnow").getValue(String.class);
                        droomid4 = dataSnapshot.child("roomid").getValue(String.class);
                        dpass4 = dataSnapshot.child("password").getValue(String.class);
                        entryfee4.setText(dentryfee);
                        type4.setText(dtype);
                        mode4.setText(dmode);
                        map4.setText(dmap);

//                                        rank6.setText(drank6);
//                                        rank7.setText(drank7);
//                                        rank8.setText(drank8);
//                                        rank9.setText(drank9);
//                                        rank10.setText(drank10);
                        matchno4.setText(dmatchno);
                        roonid4.setText(droomid4);
                        password4.setText(dpass4);
                        date4.setText(ddate);
                        time4.setText(dtime);
                        perkill4.setText(dperkill);
                        registration4.setText(dstatus4);
                        if (!dwatchnow4.isEmpty()) {
                            joinnow4.setVisibility(View.GONE);
                        } else if (dwatchnow4.isEmpty()) {
                            joinnow4.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainPubg_Lite.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

    }

    private void tdmMode() {
        progressdialog.show();

        progressdialog.show();
        post_tdm.child("fouractivity")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressdialog.dismiss();
                        String dentryfee = dataSnapshot.child("entryfee").getValue(String.class);
                        String dtype = dataSnapshot.child("type").getValue(String.class);
                        String dmode = dataSnapshot.child("mode").getValue(String.class);
                        String dmap = dataSnapshot.child("map").getValue(String.class);
                        st_tdm1 = dataSnapshot.child("rank1").getValue(String.class);
                        st_tdm2 = dataSnapshot.child("rank2").getValue(String.class);
                        st_tdm3 = dataSnapshot.child("rank3").getValue(String.class);
                        st_tdm4 = dataSnapshot.child("rank4").getValue(String.class);
                        String drank5 = dataSnapshot.child("rank5").getValue(String.class);

                        String dtime = dataSnapshot.child("time").getValue(String.class);
                        dmatchno5 = dataSnapshot.child("matchno").getValue(String.class);
                        String ddate = dataSnapshot.child("date").getValue(String.class);
                        String dperkill = dataSnapshot.child("perkill").getValue(String.class);
                        dinfo5 = dataSnapshot.child("information").getValue(String.class);
                        dstatus5 = dataSnapshot.child("status").getValue(String.class);
                        dupid5 = dataSnapshot.child("upiid").getValue(String.class);
                        dpaidentry5 = dataSnapshot.child("paidentry").getValue(String.class);
                        dwatchnow5 = dataSnapshot.child("disabled").getValue(String.class);
                        dupdate5 = dataSnapshot.child("update").getValue(String.class);
                        final String st_watchnow = dataSnapshot.child("watchnow").getValue(String.class);
                        droomid5 = dataSnapshot.child("roomid").getValue(String.class);
                        dpass5 = dataSnapshot.child("password").getValue(String.class);
                        final String dprizepol = dataSnapshot.child("prizepool").getValue(String.class);
                        st_prizepool5 = dataSnapshot.child("prizepool").getValue(String.class);
                        dwinner5 = dataSnapshot.child("winner").getValue(String.class);
                        entryfee5.setText(dentryfee);
                        type5.setText(dtype);
                        mode5.setText(dmode);
                        map5.setText(dmap);

                        prizepool5.setText(dprizepol);
                        matchno5.setText(dmatchno5);
                        date5.setText(ddate);
                        time5.setText(dtime);
                        perkill5.setText(dperkill);
                        roonid5.setText(droomid5);
                        password5.setText(dpass5);
                        registration5.setText(dstatus5);
                        if (!dwatchnow5.isEmpty()) {
                            joinnow5.setVisibility(View.GONE);
                        } else {
                            joinnow5.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainPubg_Lite.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
//                    }
//                }, 1000L);

    }

    private void loadPlayer() {
        Intent intent = new Intent(MainPubg_Lite.this, LiteSoloLoadPlayer_Activity.class);
        startActivity(intent);

    }

    private void loadPlayer2() {
        Intent intent = new Intent(MainPubg_Lite.this, LiteDuoLoadPlayer_Activity.class);
        startActivity(intent);

    }

    private void loadPlayer3() {
        Intent intent = new Intent(MainPubg_Lite.this, LiteSquadLoadPlayer_Activity.class);
        startActivity(intent);

    }

    private void loadPlayer4() {
        Intent intent = new Intent(MainPubg_Lite.this, ArcadeLoadPlayer_Activity.class);
        startActivity(intent);

    }

    private void loadPlayer5() {
        Intent intent = new Intent(MainPubg_Lite.this, LiteTdmLoadPlayer_Activity.class);
        startActivity(intent);

    }

    private void prizePoolMatchno3() {
        TextView winner, matchno, first, second, third, four;
        final BottomSheetDialog dialog = new BottomSheetDialog(MainPubg_Lite.this);
        dialog.setContentView(R.layout.prizepool_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        winner = dialog.findViewById(R.id.winner_prizepool);
        matchno = dialog.findViewById(R.id.matchno_prizepool);
        first = dialog.findViewById(R.id.runnerup1_prizepool_result);
        second = dialog.findViewById(R.id.runnerup2_prizepool_result);
        third = dialog.findViewById(R.id.runnerup3_prizepool_result);
        four = dialog.findViewById(R.id.runnerup4_prizepool_result);

        matchno.setText(dmatchno);
        first.setText(drank1);
        second.setText(drank2);
        third.setText(drank3);
        four.setText(drank4);
        winner.setText(dwinner3);
        dialog.show();


    }

    private void prizePoolMatchno2() {
        TextView winner, matchno, first, second, third, four;
        final BottomSheetDialog dialog = new BottomSheetDialog(MainPubg_Lite.this);
        dialog.setContentView(R.layout.prizepool_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        winner = dialog.findViewById(R.id.winner_prizepool);
        matchno = dialog.findViewById(R.id.matchno_prizepool);
        first = dialog.findViewById(R.id.runnerup1_prizepool_result);
        second = dialog.findViewById(R.id.runnerup2_prizepool_result);
        third = dialog.findViewById(R.id.runnerup3_prizepool_result);
        four = dialog.findViewById(R.id.runnerup4_prizepool_result);

        matchno.setText(dmatchno2);
        first.setText(st_duo1);
        second.setText(st_duo2);
        third.setText(st_duo3);
        four.setText(st_duo4);
        winner.setText(dwinner2);
        dialog.show();


    }

    private void prizePoolMatchno1() {
        TextView winner, matchno, first, second, third, four;
        final BottomSheetDialog dialog = new BottomSheetDialog(MainPubg_Lite.this);
        dialog.setContentView(R.layout.prizepool_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        winner = dialog.findViewById(R.id.winner_prizepool);
        matchno = dialog.findViewById(R.id.matchno_prizepool);
        first = dialog.findViewById(R.id.runnerup1_prizepool_result);
        second = dialog.findViewById(R.id.runnerup2_prizepool_result);
        third = dialog.findViewById(R.id.runnerup3_prizepool_result);
        four = dialog.findViewById(R.id.runnerup4_prizepool_result);

        matchno.setText(dmatchno1);
        first.setText(st_solo1);
        second.setText(st_solo2);
        third.setText(st_solo3);
        four.setText(st_solo4);
        winner.setText(dwinner1);
        dialog.show();


    }

    private void prizePoolMatchno4() {
        TextView winner, matchno, first, second, third, four;
        final BottomSheetDialog dialog = new BottomSheetDialog(MainPubg_Lite.this);
        dialog.setContentView(R.layout.prizepool_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        winner = dialog.findViewById(R.id.winner_prizepool);
        matchno = dialog.findViewById(R.id.matchno_prizepool);
        first = dialog.findViewById(R.id.runnerup1_prizepool_result);
        second = dialog.findViewById(R.id.runnerup2_prizepool_result);
        third = dialog.findViewById(R.id.runnerup3_prizepool_result);
        four = dialog.findViewById(R.id.runnerup4_prizepool_result);

        matchno.setText(dmatchno4);
        first.setText(st_arca1);
        second.setText(st_arca2);
        third.setText(st_arca3);
        four.setText(st_arca4);
        winner.setText(dwinner4);
        dialog.show();


    }

    private void prizePoolMatchno5() {
        TextView winner, matchno, first, second, third, four;
        final BottomSheetDialog dialog = new BottomSheetDialog(MainPubg_Lite.this);
        dialog.setContentView(R.layout.prizepool_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        winner = dialog.findViewById(R.id.winner_prizepool);
        matchno = dialog.findViewById(R.id.matchno_prizepool);
        first = dialog.findViewById(R.id.runnerup1_prizepool_result);
        second = dialog.findViewById(R.id.runnerup2_prizepool_result);
        third = dialog.findViewById(R.id.runnerup3_prizepool_result);
        four = dialog.findViewById(R.id.runnerup4_prizepool_result);

        matchno.setText(dmatchno5);
        first.setText(st_tdm1);
        second.setText(st_tdm2);
        third.setText(st_tdm3);
        four.setText(st_tdm4);
        winner.setText(dwinner5);
        dialog.show();


    }

    private void paymentSuccessDialog() {
        Button close;
        final Dialog dialog = new Dialog(MainPubg_Lite.this);
        dialog.setContentView(R.layout.paymentsuccess_dialog);
        Window window = dialog.getWindow();
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCancelable(false);
        close = dialog.findViewById(R.id.close_dialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    Runnable runnable1;

    private void startAnimationBolttCard() {
        ;
        try {
            // if (isBolttCardAnim) {
            new Handler().postDelayed(runnable1, 100);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkCoins1() {

        int coin = Integer.parseInt(st_coins);
        int paidentry = Integer.parseInt(dpaidentry);

        if (coin >= paidentry) {
            joinSoloPlayer();

            int total = coin - paidentry;
            Log.d(TAG, "totalll " + total);
            final String st_total = String.valueOf(total);
            txt_coins.setText(st_total);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        progressdialog.dismiss();


                        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                                .child("every_signup_user").child(mobile);


                        databaseReference.child("litecoins").setValue(st_total);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {
            Toast.makeText(this, "Not enough coins in your wallet", Toast.LENGTH_LONG).show();
        }

    }

    private void checkCoins2() {

        int coin = Integer.parseInt(st_coins);
        int paidentry = Integer.parseInt(dpaidentry2);

        if (coin >= paidentry) {
            joinDuoPlayer();

            int total = coin - paidentry;
            Log.d(TAG, "totalll " + total);
            final String st_total = String.valueOf(total);
            txt_coins.setText(st_total);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        progressdialog.dismiss();


                        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                                .child("every_signup_user").child(mobile);


                        databaseReference.child("litecoins").setValue(st_total);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {
            Toast.makeText(this, "Not enough coins in your wallet", Toast.LENGTH_LONG).show();
        }

    }

    private void checkCoins3() {

        int coin = Integer.parseInt(st_coins);
        int paidentry = Integer.parseInt(dpaidentry3);

        if (coin >= paidentry) {
            joinSquadPlayer();

            final int total = coin - paidentry;
            Log.d(TAG, "totalll " + total);
            final String st_total = String.valueOf(total);
            txt_coins.setText(st_total);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        progressdialog.dismiss();

                        String st_coins = st_total;


                        databaseReference.child("litecoins").setValue(st_coins);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {
            Toast.makeText(this, "Not enough coins in your wallet", Toast.LENGTH_LONG).show();
        }

    }

    private void checkCoins4() {

        int coin = Integer.parseInt(st_coins);
        int paidentry = Integer.parseInt(dpaidentry4);

        if (coin >= paidentry) {
            joinArcadePlayer();

            int total = coin - paidentry;
            Log.d(TAG, "totalll " + total);
            final String st_total = String.valueOf(total);
            txt_coins.setText(st_total);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        progressdialog.dismiss();

//                        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
//                                .child("every_signup_user").child(mobile);

                        databaseReference.child("litecoins").setValue(st_total);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {
            Toast.makeText(this, "Not enough coins in your wallet", Toast.LENGTH_LONG).show();
        }
    }

    private void checkCoins5() {

        int coin = Integer.parseInt(st_coins);
        int paidentry = Integer.parseInt(dpaidentry5);

        if (coin >= paidentry) {
            joinTdmPlayer();

            int total = coin - paidentry;
            Log.d(TAG, "totalll " + total);
            final String st_total = String.valueOf(total);
            txt_coins.setText(st_total);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        progressdialog.dismiss();


                        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                                .child("every_signup_user").child(mobile);


                        databaseReference.child("litecoins").setValue(st_total);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {
            Toast.makeText(this, "Not enough coins in your wallet", Toast.LENGTH_LONG).show();
        }

    }
}
