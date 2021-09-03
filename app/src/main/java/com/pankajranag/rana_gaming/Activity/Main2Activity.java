package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.StorageReference;


import com.pankajranag.rana_gaming.AllNew.DuoActivity;
import com.pankajranag.rana_gaming.AllNew.SoloActivity;
import com.pankajranag.rana_gaming.AllNew.SquadActivity;
import com.pankajranag.rana_gaming.FreeFire_Activity.Activity.MainFreeFire_Activity;
import com.pankajranag.rana_gaming.kotlin.Notification;
import com.pankajranag.rana_gaming.Lite_Activity.MainPubg_Lite;
import com.pankajranag.rana_gaming.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.net.URLEncoder;
import java.util.HashMap;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RewardedVideoAdListener {
    public static final String VERSION_CODE_KEY = "latest_app_version";
    private static final int REQUEST_INVITE = 100;
    private static final String TAG = "Loading Data ...";
    private static final int PICKFILE_REQUEST_CODE = 1;
    private static final int RC_APP_UPDATE = 9;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String mobile, st_share, st_dialog, st_group, st_telegram, st_instagram;
    ImageView first, second, third, four, five, six, seven, eight;
    LinearLayout header, image_header;
    TextView name, nox, txt_coins;
    String dupdate, st_mobileno, main_msg, pos_msg, wt_msg;
    FirebaseUser user;
    ImageView tol_image;
    ProgressBar progressBar;
    String uri = "https://www.youtube.com/watch?v=566uQctPFrw";
    boolean doublePressed = true;
    RewardedVideoAd rewardedVideoAd;
    LinearLayout linearLayout, linearLayout2;
    InterstitialAd mInterstitialAd;
    ;
    AdRequest initial_ad;
    RecyclerView home_recycler;
    ImageView pubg_Image, lite_image, freeImage, squadImage;
    boolean doubleBackToExitPressedOnce = false;
    private ProgressDialog progressdialog;
    private DatabaseReference databaseReferencee;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference dbrefrence = databaseReference.child("main2activity").child("image1");
    private DatabaseReference dbrefrence2 = databaseReference.child("main2activity").child("image2");
    private DatabaseReference dbrefrence3 = databaseReference.child("main2activity").child("image3");
    private DatabaseReference dialog = databaseReference.child("main2activity").child("dialog");
    private DatabaseReference dialog_db;
    private DatabaseReference Post;
    private FirebaseAuth firebaseAuth;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private HashMap<String, Object> firebaseDefaultMap;
    private AppUpdateManager mAppUpdateManager;
    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED) {
                        //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                        popupSnackbarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED) {
                        if (mAppUpdateManager != null) {
                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        }
        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/))
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, Main2Activity.this, RC_APP_UPDATE);

                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                    Main2Activity.this.popupSnackbarForCompleteUpdate();
                } else {
                    Log.e(TAG, "checkForAppUpdateAvailability: something else");
                }
            }
        });
        home_recycler = findViewById(R.id.home_recycler);

        nox = findViewById(R.id.nox);
        nox.setVisibility(View.VISIBLE);
        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_add));
        initial_ad = new AdRequest.Builder().build();

        header = findViewById(R.id.header);
        firebaseAuth = FirebaseAuth.getInstance();

        linearLayout = findViewById(R.id.linear_first);
        linearLayout2 = findViewById(R.id.linear_two);

        linearLayout2.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);

        first = (ImageView) findViewById(R.id.hor_one_pubg);
        second = (ImageView) findViewById(R.id.hor_two_lite);
        third = (ImageView) findViewById(R.id.hor_three_freefire);
        four = (ImageView) findViewById(R.id.image4);
        five = (ImageView) findViewById(R.id.imageone5);
        six = (ImageView) findViewById(R.id.image6);
        seven = (ImageView) findViewById(R.id.image7);
        eight = (ImageView) findViewById(R.id.image8);

        first.setVisibility(View.VISIBLE);
        second.setVisibility(View.VISIBLE);
        third.setVisibility(View.VISIBLE);

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
        txt_coins = headerview.findViewById(R.id.coins);

        TextView phoneno = headerview.findViewById(R.id.phoneno);
        tol_image = headerview.findViewById(R.id.header_profileimage);
        image_header = headerview.findViewById(R.id.header);

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
                Toast.makeText(Main2Activity.this, "1 coins = 1 Rs. Send Mail for Reddem and send msg in Whatsapp Group", Toast.LENGTH_SHORT).show();

            }
        });


        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MyProfile_Activity.class);
//                intent.putExtra("mobile_sign", mobile);
//                startActivity(intent);
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainPubg_Lite.class);
                startActivity(intent);
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MainFreeFire_Activity.class));
            }
        });


        hideMenuItem();
        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);
        headerData();

        profileImage();

        final SwitchCompat item = (SwitchCompat) navigationView.getMenu().findItem(R.id.nav_item1).getActionView();
        item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    item.setChecked(true);

                    presentActivity(buttonView);


                } else if (!isChecked) {

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });

        Post = FirebaseDatabase.getInstance().getReference().child("main2activity");
        readRealTime();


    }

    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.drawerlayout),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAppUpdateManager != null) {
                    mAppUpdateManager.completeUpdate();
                }
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
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

    private void readRealTime() {

        Post.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressdialog.dismiss();
                dupdate = dataSnapshot.child("update").getValue(String.class);
                st_share = dataSnapshot.child("share").getValue(String.class);
                st_dialog = dataSnapshot.child("disabled").getValue(String.class);
                st_group = dataSnapshot.child("group").getValue(String.class);
                st_telegram = dataSnapshot.child("telegram").getValue(String.class);
                st_instagram = dataSnapshot.child("Instagram").getValue(String.class);
                st_mobileno = dataSnapshot.child("number").getValue(String.class);
                main_msg = dataSnapshot.child("msg").getValue(String.class);
                wt_msg = dataSnapshot.child("wt_msg").getValue(String.class);
                pos_msg = dataSnapshot.child("btn_msg").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void profileImage() {
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        final StorageReference reference = FirebaseStorage.getInstance().getReference()
//                .child("profileImages")
//                .child(uid + ".jpeg");
//        getDownloadUrl(reference);
//
//        firebaseDefaultMap = new HashMap<>();
//        firebaseDefaultMap.put(VERSION_CODE_KEY, getCurrentVersionCode());
//        mFirebaseRemoteConfig.setDefaults(firebaseDefaultMap);
//
//        mFirebaseRemoteConfig.setConfigSettings(
//                new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG)
//                        .build());
//
//        mFirebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    mFirebaseRemoteConfig.activateFetched();
//                    Log.d(TAG, "Fetched value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));
//                    //calling function to check if new version is available or not
//                    // checkForUpdate();
//                } else {
//                    Toast.makeText(MainActivity.this, "Something went wrong please try again",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        Log.d(TAG, "Default value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));

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
            Toast.makeText(this, "This app is already up to date", Toast.LENGTH_SHORT).show();
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

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri);

                        setUserProfileUrl(uri);
                    }
                });


    }

    private void setUserProfileUrl(Uri uri) {
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
//                .setPhotoUri(uri)
//                .build();
//
//        user.updateProfile(request)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        if (user.getPhotoUrl() != null) {
//                            //    progressdialog.show();
//
////                            Glide.with(getApplicationContext())
////                                    .load(user.getPhotoUrl())
////                                    .into(tol_image);
//
//                            Picasso.get().load(user.getPhotoUrl()).into(tol_image, , new Callback() {
//                                @Override
//                                public void onSuccess() {
//                                }
//
//                                @Override
//                                public void onError(Exception e) {
//                                }
//                            });
//                            //  Toast.makeText(MainActivity.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Main2Activity.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
//                    }
//                });

//    private void setUserProfileUrl(Uri uri) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
//                .setPhotoUri(uri)
//                .build();
//
//        user.updateProfile(request)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(MainActivity.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wallet, menu);
        MenuItem notification = menu.findItem(R.id.notification);
        MenuItem notification2 = menu.findItem(R.id.refresh);
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
                Intent intent = new Intent(Main2Activity.this, AboutUs_Activity.class);
                startActivity(intent);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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
                Intent intent = new Intent(Main2Activity.this, MyProfile_Activity.class);
                intent.putExtra("mobile_sign", mobile);
                startActivity(intent);
                break;

            case R.id.logout:
                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
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
                                AuthUI.getInstance().signOut(Main2Activity.this);
                                Intent i = new Intent(Main2Activity.this, MixLogin.class);
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

                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody + st_share);
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
                startActivity(intentWhatsapp);
                break;
            case R.id.telegram:
                Intent telegram = new Intent(Intent.ACTION_VIEW);
                String url2 = st_telegram;
                telegram.setData(Uri.parse(url2));
                startActivity(telegram);
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
            case R.id.privacy:
//                mInterstitialAd.loadAd(initial_ad);
//                mInterstitialAd.setAdListener(new AdListener() {
//                    public void onAdLoaded() {
//                        showInterstitial();
//                    }
//                });
                startActivity(new Intent(this, Terms_ConditionActivity.class));
                break;
            case R.id.refund:
//                mInterstitialAd.loadAd(initial_ad);
//                mInterstitialAd.setAdListener(new AdListener() {
//                    public void onAdLoaded() {
//                        showInterstitial();
//                    }
//                });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }

        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e(TAG, "onActivityResult: app download failed");
            }
        }
    }


    public void sendMail() {
//        final Intent _Intent = new Intent(Intent.ACTION_SENDTO);
//        _Intent.setType("text/html");
//        _Intent.setData(Uri.parse("mailto:"));
//        _Intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        _Intent.putExtra(Intent.EXTRA_STREAM, "content://packagename.files/files/somefile.ext");
//        _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//                "Coins Amount send within 24 Hours");
//        _Intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ranap8445@gmail.com"});
//        _Intent.putExtra(android.content.Intent.EXTRA_TEXT,
//                "your detail write here");
//        startActivity(Intent.createChooser(_Intent, "Send via EMail"));


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
                        PackageManager packageManager = Main2Activity.this.getPackageManager();
                        Intent i = new Intent(Intent.ACTION_VIEW);

                        try {
                            String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(message, "UTF-8");
                            i.setPackage("com.whatsapp");
                            i.setData(Uri.parse(url));
                            if (i.resolveActivity(packageManager) != null) {
                                Main2Activity.this.startActivity(i);
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


        /*dbrefrence.addValueEventListener(new ValueEventListener() {
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
//                Glide.with(getApplicationContext())
//                        .load(firstimage)
//                        .into(third);
                Picasso.get().load(user.getPhotoUrl()).into(third, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        dialog.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstimage = dataSnapshot.getValue(String.class);


                if (!firstimage.isEmpty()) {
                    ImageView imageView, cut;
                    final Dialog settingsDialog = new Dialog(Main2Activity.this);
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

                    final Dialog settingsDialog = new Dialog(Main2Activity.this);
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
                            String st_coins = dataSnapshot.child("coins").getValue(String.class);
                            txt_coins.setText(st_coins);
                            name.setText(st_name);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
    public void onRewarded(RewardItem rewardItem) {

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

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


}