package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.pankajranag.rana_gaming.PrefrenceHelper;
import com.pankajranag.rana_gaming.R;

import java.util.concurrent.TimeUnit;



public class Login extends AppCompatActivity {

    TextView signup;
    Button login, resend;
    EditText username, password;
    private ProgressDialog progressdialog;
    String number;
    PrefrenceHelper prefrenceHelper;
    TextView txtno;
    String mobile;
    public static final int timeInMillis = 6000;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    String verificationCodeBySystem;
    String phoneNo,no2;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);
        Button submit;
        mAuth = FirebaseAuth.getInstance();
        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//
//        prefrenceHelper=new PrefrenceHelper();
//        number=prefrenceHelper.getString(PrefrenceHelper.USER_CONTACT);


        mobile = getIntent().getStringExtra("mobileno");
        Log.d("tag", "mobileno: " + mobile);

        txtno = findViewById(R.id.txt_no);


        //  signup = (TextView) findViewById(R.id.signup);

        username = findViewById(R.id.username);
        //  password=findViewById(R.id.password);

        login = findViewById(R.id.login);
        resend = findViewById(R.id.resend);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.getText().toString().equalsIgnoreCase("")) {
                    username.setError("please enter OTP");
                } else {
                    // otpVerify();
                }

            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  cheakStatus();
            }
        });
        no2 = getIntent().getStringExtra("phoneNo2");
         phoneNo = getIntent().getStringExtra("phoneNo");
        txtno.setText(phoneNo);
        sharedPrefrence();


        sendVerificationCodeToUser(phoneNo);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString())) {
                    Toast.makeText(Login.this, "Enter Otp", Toast.LENGTH_SHORT).show();
                } else if (username.getText().toString().replace(" ", "").length() != 6) {
                    Toast.makeText(Login.this, "Enter right otp", Toast.LENGTH_SHORT).show();
                } else {
                    Handler handler2 = new Handler();
                    handler2.postDelayed(
                            new Runnable() {
                                public void run() {
                                    progressdialog.show();
                                    oneTime();
                                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, username.getText().toString().replace(" ", ""));
                                    signInWithPhoneAuthCredential(credential);

                                }
                            }, 1000);
                }

            }
        });

    }


    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+" + phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,   // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }


    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            Intent intent = new Intent(Login.this, Main2Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationCodeBySystem = s;

                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                        String code = phoneAuthCredential.getSmsCode();
                        if (code != null) {
                            progressdialog.show();
                            verifyCode(code);
                            username.setText(code);
                            oneTime();

                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                }
            };


    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            oneTime();
                            Toast.makeText(Login.this, "Your Account has been created successfully!", Toast.LENGTH_LONG).show();

                            //Perform Your required action here to either let the user sign In or do something required
                            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("sign_mobileno",phoneNo);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void otpVerify() {
        progressdialog.show();
//        JSONObject object1 = new JSONObject();
//
//        try {
//            object1.put("member_mobile", mobile);
//            object1.put("otp_code", username.getText().toString());
//
//
//            Log.d("mytag", "object1" + object1);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Volley.newRequestQueue(this).add(new JsonObjectRequest(Request.Method.POST, Constants.OTP_VERIFY, object1,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Mytag", "Response: " + response);
//                        progressdialog.dismiss();
//                        try {
//                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
//                            if (response.getString("status").equals("200")) {
//                                progressdialog.dismiss();
//
//                                String id = response.getString(Constants.MIMBER_ID);
//                                //    Log.d("mytag","memberidvalue"+id);
//
//                                oneTime();
//                                Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(Login.this, MainActivity.class);
//                                intent.putExtra(Constants.MIMBER_ID, id);
//                                startActivity(intent);
//                                finish();
//
//
//                            } else {
//
//                                Toast.makeText(Login.this, response.getString(OTP_Match_MSG), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressdialog.dismiss();
//                Log.d("Mytag", "onErrorResponse: " + error.getMessage());
//                if (error instanceof NoConnectionError) {
//                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }));
    }

    public void oneTime() {

        SharedPreferences sharedPreferences = getSharedPreferences("com.mobisoftseo.myapplication_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login status", "on");
        editor.commit();
    }

    private void cheakStatus() {

//        progressdialog.show();
//
//        JSONObject object1 = new JSONObject();
//
//        try {
//            object1.put("member_mobile", mobile);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Volley.newRequestQueue(this).add(new JsonObjectRequest(Request.Method.POST, Constants.ALL_MOBILENO, object1,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Mytag", "onResponse: " + response);
//                        progressdialog.dismiss();
//                        try {
//                            if (response.getString("status").equals("200")) {
//
//                                progressdialog.dismiss();
//                                Toast.makeText(Login.this, response.getString(Constants.OTP), Toast.LENGTH_LONG).show();
//
//
//                            } else {
//
//                                Toast.makeText(Login.this, response.getString(OTP_MSG), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressdialog.dismiss();
//                Log.d("Mytag", "onErrorResponse: " + error.getMessage());
//                if (error instanceof NoConnectionError) {
//                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }));
    }

    public void dialogshow() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                final ImageView imgv1;
                final Dialog dialog = new Dialog(Login.this);
                dialog.setContentView(R.layout.wrongno_dialog);
                dialog.setCancelable(false);
                imgv1 = dialog.findViewById(R.id.cut);
                imgv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Login.super.onBackPressed();
                        finish();
                    }
                });
                dialog.show();

            }
        }, timeInMillis);
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
    private void sharedPrefrence(){
        String st_no=no2.toString();
        SharedPreferences sharedPreferences=getSharedPreferences("signup",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("number",st_no);
        editor.apply();

    }
}
