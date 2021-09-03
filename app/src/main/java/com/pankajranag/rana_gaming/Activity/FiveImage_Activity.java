package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.pankajranag.rana_gaming.BuildConfig;
import com.pankajranag.rana_gaming.R;
import com.paytm.pgsdk.PaytmPGService;

import java.util.HashMap;

public class FiveImage_Activity extends AppCompatActivity implements RewardedVideoAdListener {

    private static final int REQ_UPIPAYMENT = 1;
    TextView enteryfee, type, mode, map;
    TextView rank1, rank2, rank3, rank4, rank5, rank6, rank7, rank8, rank9, rank10;
    TextView matchno, date, time, perkill, status;
    private DatabaseReference Post;
    private ProgressDialog progressdialog;
    Button info, watchnow;
    TextView information;
    String dinfo, dstatus, dpaidentry, dupid, dwatchnow,dupdate;
    private String yes = "yes";
    private String No = "no";
    private AdView mAdView, mAdView2, mAdview3, adView4;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference dbrefrence = databaseReference.child("mainactivity").child("image5");
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private HashMap<String, Object> firebaseDefaultMap;
    public static final String VERSION_CODE_KEY = "latest_app_version";
    private static final String TAG = "Loading Data ...";
    ImageView bigimage, smallimage;
    RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_image_);
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

        Button fab = findViewById(R.id.joinnow);
        info = findViewById(R.id.info);
        watchnow = findViewById(R.id.watchnow);

        fab.isAttachedToWindow();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "please wait...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Handler handler2 = new Handler();
                handler2.postDelayed(
                        new Runnable() {
                            public void run() {

                                new AlertDialog.Builder(FiveImage_Activity.this)
                                        .setTitle("Important Notice")
                                        .setMessage("If there no Payment option Show in your mobile..Then send message with Screenshot in whatsapp group")
                                        //  .setMessage("https://t.me/NOXGAMING11")


                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                launchUPI();
                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton(android.R.string.no, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();


                            }
                        }, 2000L);


            }
        });

        PaytmPGService Service = PaytmPGService.getProductionService();

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }
                ImageView cut;

                final Dialog dialog = new Dialog(FiveImage_Activity.this);
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
                    Toast.makeText(FiveImage_Activity.this, "Something went wrong please try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.d(TAG, "Default value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));

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
            //Toast.makeText(this, "This app is already up to date", Toast.LENGTH_SHORT).show();
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
                //   String transId = data.getStringExtra("response");
                Log.d("bhb", "UPI Payment successfull");
            } else {
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


        Post = FirebaseDatabase.getInstance().getReference().child("hackermatch");
    }

    private void readRealTime() {
        progressdialog.show();
        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {
                        progressdialog.show();
                        Post.child("fiveactivity")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        progressdialog.dismiss();
                                        String dentryfee = dataSnapshot.child("enteryfee").getValue(String.class);
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
                Glide.with(FiveImage_Activity.this)
                        .load(firstimage)
                        .into(bigimage);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void initiatePayment(final String packageId, final String packageAmount) {

//        progressdialog.show();
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.INITIATE_PAYMENT,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressdialog.dismiss();
//                        // Do something with response string
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONObject jsonObjectPayInfo = jsonObject.getJSONObject("data");
//
//                            if (jsonObject.getString(Constants.STATUS).equals("200")) {
//
//                                Intent intentProceed = new Intent(FirstImage_Activity.this, PWECouponsActivity.class);
//
//                                intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);     // This is mandatory flag
//                                intentProceed.putExtra("txnid", jsonObjectPayInfo.getString("txn_id"));
//                                intentProceed.putExtra("amount", Double.valueOf(jsonObjectPayInfo.getString("amount")));
//                                intentProceed.putExtra("productinfo", jsonObjectPayInfo.getString("product_info"));
//                                intentProceed.putExtra("firstname", jsonObjectPayInfo.getString("user_name"));
//                                intentProceed.putExtra("email", jsonObjectPayInfo.getString("email"));
//                                intentProceed.putExtra("phone", jsonObjectPayInfo.getString("mobile"));
//                                intentProceed.putExtra("key", "IBC19TXDBO");  //Key
//                                intentProceed.putExtra("udf1", "");
//                                intentProceed.putExtra("udf2", "");
//                                intentProceed.putExtra("udf3", "");
//                                intentProceed.putExtra("udf4", "");
//                                intentProceed.putExtra("udf5", "");
//                                //    intentProceed.putExtra("unique_id",jsonObjectPayInfo.getString("user_id"));
//                                intentProceed.putExtra("hash", jsonObjectPayInfo.getString("pay_hash"));
//                                intentProceed.putExtra("pay_mode", "production");
//
//                                startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);
//                                Log.d("start", "data: " + jsonObjectPayInfo);
//
//                            } else {
//
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            progressdialog.dismiss();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressdialog.dismiss();
//                        Log.d("onResponceerror", "onResponse: " + error);
//                        Log.d("onResponceerror", "onResponceerror" + error.getMessage());
//                        if (error instanceof NetworkError) {   //use in end
//                            Toast.makeText(FirstImage_Activity.this, "Network Error.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof ServerError) {
//                            Toast.makeText(FirstImage_Activity.this, "Server Error.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof AuthFailureError) {
//                            Toast.makeText(FirstImage_Activity.this, "Authantication Failure.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof ParseError) {
//                            Toast.makeText(FirstImage_Activity.this, "Parse Error.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof TimeoutError) {
//                            Toast.makeText(FirstImage_Activity.this, "TimeOut Error.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        ) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                HashMap<String, String> params2 = new HashMap<String, String>();
//
//                params2.put(Constants.USER_ID, "882145");
//                params2.put(Constants.PACKAGE_ID, "1");
//                params2.put(Constants.PACKAGE_AMOUNT, String.valueOf(packageAmount));
//                params2.put(Constants.PRODUCTINFO, "Registration Fees");
//                params2.put(Constants.USER_NAME, "pankaj rana");
//                params2.put(Constants.USER_EMAIL, "pankaj@gmail.com");
//                params2.put(Constants.USER_MOBILE, "8445611760");
//                Log.d("abc", "request: " + params2);
//
//                return new JSONObject(params2).toString().getBytes();
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
//        };
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null) {
//            if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
//                String result = data.getStringExtra("result");
//                String payment_response = data.getStringExtra("payment_response");
//                Log.d("payment_response", payment_response);
//                try {
//                    JSONObject jsonObject = new JSONObject(payment_response);
//                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                        paymentSucessFailure(payment_response);
//                    } else {
//                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                    // Handle response here
//                } catch (Exception e) {
//                    // Handle exception here
//                }
//            } else {
//                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
//            }
//
//        } else {
//            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void paymentSucessFailure(final String paymentResponse) {

//        progressdialog.show();
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.PAYMENT_SUCESS_FAILURE,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressdialog.dismiss();
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.getString("status").equalsIgnoreCase("200")) {
//                                Toast.makeText(FirstImage_Activity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(FirstImage_Activity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            progressdialog.dismiss();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressdialog.dismiss();
//                        Log.d("gfd", "onResponse: " + error);
//                        Log.d("hf", "onResponceerror" + error.getMessage());
//                        if (error instanceof NetworkError) {   //use in end
//                            Toast.makeText(FirstImage_Activity.this, "Network Error.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof ServerError) {
//                            Toast.makeText(FirstImage_Activity.this, "Server Error.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof AuthFailureError) {
//                            Toast.makeText(FirstImage_Activity.this, "Authantication Failure.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof ParseError) {
//                            Toast.makeText(FirstImage_Activity.this, "Parse Error.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof TimeoutError) {
//
//                            Toast.makeText(FirstImage_Activity.this, "TimeOut Error.Please Try Again!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        ) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                HashMap<String, String> params2 = new HashMap<String, String>();
//                try {
//                    JSONObject jsonObject = new JSONObject(paymentResponse);
//                    params2.put("name_on_card", jsonObject.getString("name_on_card"));
//                    params2.put("bank_ref_num", jsonObject.getString("bank_ref_num"));
//                    params2.put("udf3", jsonObject.getString("udf3"));
//                    params2.put("hash", jsonObject.getString("hash"));
//                    params2.put("firstname", jsonObject.getString("firstname"));
//                    params2.put("net_amount_debit", jsonObject.getString("net_amount_debit"));
//                    params2.put("payment_source", jsonObject.getString("payment_source"));
//                    params2.put("surl", jsonObject.getString("surl"));
//                    params2.put("error_Message", jsonObject.getString("error_Message"));
//                    params2.put("issuing_bank", jsonObject.getString("issuing_bank"));
//                    params2.put("cardCategory", jsonObject.getString("cardCategory"));
//                    params2.put("phone", jsonObject.getString("phone"));
//                    params2.put("easepayid", jsonObject.getString("easepayid    "));
//                    params2.put("cardnum", jsonObject.getString("cardnum"));
//                    params2.put("key", jsonObject.getString("key"));
//                    params2.put("udf8", jsonObject.getString("udf8"));
//                    params2.put("unmappedstatus", jsonObject.getString("unmappedstatus"));
//                    params2.put("PG_TYPE", jsonObject.getString("PG_TYPE"));
//                    params2.put("addedon", jsonObject.getString("addedon"));
//                    params2.put("cash_back_percentage", jsonObject.getString("cash_back_percentage"));
//                    params2.put("status", jsonObject.getString("status"));
//                    params2.put("udf1", jsonObject.getString("udf1"));
//                    params2.put("merchant_logo", jsonObject.getString("merchant_logo"));
//                    params2.put("udf6", jsonObject.getString("udf6"));
//                    params2.put("udf10", jsonObject.getString("udf10"));
//                    params2.put("txnid", jsonObject.getString("txnid"));
//                    params2.put("productinfo", jsonObject.getString("productinfo"));
//                    params2.put("furl", jsonObject.getString("furl"));
//                    params2.put("card_type", jsonObject.getString("card_type"));
//                    params2.put("amount", jsonObject.getString("amount"));
//                    params2.put("udf2", jsonObject.getString("udf2"));
//                    params2.put("udf5", jsonObject.getString("udf5"));
//                    params2.put("mode", jsonObject.getString("mode"));
//                    params2.put("udf7", jsonObject.getString("udf7"));
//                    params2.put("error", jsonObject.getString("error"));
//                    params2.put("udf9", jsonObject.getString("udf9"));
//                    params2.put("bankcode", jsonObject.getString("bankcode"));
//                    params2.put("deduction_percentage", jsonObject.getString("deduction_percentage"));
//                    params2.put("email", jsonObject.getString("email"));
//                    params2.put("udf4", jsonObject.getString("udf4"));
//                    Log.d("abc", "requestparameter: " + params2);
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    progressdialog.dismiss();
//                }
//                return new JSONObject(params2).toString().getBytes();
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
//        };
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
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
