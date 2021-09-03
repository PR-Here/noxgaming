package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.pankajranag.rana_gaming.PrefrenceHelper;
import com.pankajranag.rana_gaming.R;
import com.sanojpunchihewa.updatemanager.UpdateManager;
import com.sanojpunchihewa.updatemanager.UpdateManagerConstant;

public class MixLogin extends AppCompatActivity {
    private static final String TAG = "LoginRegisterActivity";
    int AUTHUI_REQUEST_CODE = 10001;
    private CountryCodePicker countryCodePicker;
    Button subscribe;
    Button register;
    Button login;
    String st_mobileno;
    private ProgressDialog progressdialog;
    UpdateManager mUpdateManager;
    EditText username;
    SharedPreferences myPrefs;
    TextInputLayout input_no;
    TextView terms, check;
    private AdView mAdView;

    private PrefrenceHelper prefrenceHelper;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_login);

        countryPopup();
        if (ContextCompat.checkSelfPermission(MixLogin.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MixLogin.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION

            }, 100);
        }

        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");

        mUpdateManager = UpdateManager.Builder(this).mode(UpdateManagerConstant.FLEXIBLE);
        // Call start() to check for updates and install them
        mUpdateManager.start();

        input_no = findViewById(R.id.input_mobilenumber);
        countryCodePicker = findViewById(R.id.ccp);
        check = findViewById(R.id.check);
        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {
                        check.setVisibility(View.GONE);

                    }
                }, 10000L);
        //prefrenceHelper=new PrefrenceHelper(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user").child("every_signup_user");
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);
        terms = findViewById(R.id.terms);

        subscribe = findViewById(R.id.subscribe);
        username = findViewById(R.id.mix_username);


        login = findViewById(R.id.mix_login);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MixLogin.this, Terms_ConditionActivity.class);
                startActivity(intent);
            }
        });
        register = findViewById(R.id.mix_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MixLogin.this, Signup.class);
                startActivity(intent);

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        login = findViewById(R.id.mix_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(username.getText().toString())) {
                    Toast.makeText(MixLogin.this, "Enter No ....", Toast.LENGTH_SHORT).show();
                } else if (username.getText().toString().replace(" ", "").length() != 10) {
                    Toast.makeText(MixLogin.this, "please enter correct mobile no ...", Toast.LENGTH_SHORT).show();
                } else {
                    st_mobileno = username.getText().toString();
                    Handler handler2 = new Handler();
                    handler2.postDelayed(
                            new Runnable() {
                                public void run() {
                                    progressdialog.show();

                                    databaseReference.child(st_mobileno).addListenerForSingleValueEvent(valueEventListener);
                                    Log.d("loginotp", "mobleno: " + databaseReference);

                                    progressdialog.dismiss();
                                }
                            }, 1000);

                }
            }

        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            progressdialog.show();
            if (dataSnapshot.exists()) {
                final String mobileno = dataSnapshot.child("mobileno").getValue(String.class);

                if (mobileno.equals(st_mobileno)) {
                    progressdialog.dismiss();
                    Intent intent = new Intent(MixLogin.this, AccountSetting_Activity.class);
                    intent.putExtra("number2", countryCodePicker.getFullNumberWithPlus() + username.getText().toString());
                    intent.putExtra("number", username.getText().toString());
                    Log.d("tag", "detail: " + countryCodePicker.getFullNumberWithPlus() + username.getText().toString());
                    startActivity(intent);
                    finish();


                }
            } else {
                Toast.makeText(MixLogin.this, "No User Found ! please signup ", Toast.LENGTH_LONG).show();
                progressdialog.dismiss();

            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };

    public void mybutton(View v) {
        if (v.getId() == R.id.login) {
            //handle the click here and make whatever you want
            Toast.makeText(MixLogin.this, "click", Toast.LENGTH_SHORT).show();
            username.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(username.getText().toString())) {
                Toast.makeText(MixLogin.this, "Enter No ....", Toast.LENGTH_SHORT).show();
            } else if (username.getText().toString().replace(" ", "").length() != 10) {
                Toast.makeText(MixLogin.this, "please enter correct mobile no ...", Toast.LENGTH_SHORT).show();
            } else {
                st_mobileno = username.getText().toString();
                Handler handler2 = new Handler();
                handler2.postDelayed(
                        new Runnable() {
                            public void run() {
                                progressdialog.show();

                                databaseReference.child(st_mobileno).addListenerForSingleValueEvent(valueEventListener);
                                Log.d("loginotp", "mobleno: " + databaseReference);

                                progressdialog.dismiss();
                            }
                        }, 1000);

            }
        }
    }

    public void oneTime() {

        SharedPreferences sharedPreferences = getSharedPreferences("com.mobisoftseo.myapplication_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login status", "on");
        editor.commit();

    }

    private boolean validate() {
        if (username.getText().toString().equalsIgnoreCase("")) {
            username.setError("please enter Mobile no.");
        } else if (username.getText().toString().trim().length() != 10) {
            username.setError("Please Enter valid  Mobile Number");

        } else {


        }

        return true;
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


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


    private void showStartDialog() {
        ImageView imageView;
        LinearLayout linearLayout;
        Button sub_dialog;
        final Dialog dialog = new Dialog(MixLogin.this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

        dialog.setContentView(R.layout.dialog_subscribe);
        dialog.setCancelable(false);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        sub_dialog = dialog.findViewById(R.id.subscribe);

        sub_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCBcDJNPOEUw5pzePoqYKsgA"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);

            }
        });
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

    public void registerUser() {
        // Performing Validation by calling validation functions
        if (!validate()) {
            return;
        }

        //Get the Phone No from phone no field in String
        String phoneNo = username.getText().toString();

        //Call the next activity and pass phone no with it
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.putExtra("mobilenum", phoneNo);
        startActivity(intent);
        finish();

    }

    private void countryPopup() {
        TextView ok_txt, msg_txt;
        final Dialog dialog = new Dialog(MixLogin.this);
        dialog.setContentView(R.layout.signup_popup);
        dialog.setCancelable(false);
        msg_txt = dialog.findViewById(R.id.msg);
        ok_txt = dialog.findViewById(R.id.ok_dialog);
        ok_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        msg_txt.setText("Please Check your country code before Login");
        dialog.show();
    }

}