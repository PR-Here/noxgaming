package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.pankajranag.rana_gaming.R;

import java.util.concurrent.TimeUnit;

public class AccountSetting_Activity extends AppCompatActivity {

    TextView mobileno;
    String no, countrycode;
    private EditText otp;
    private Button submit;
    private TextView resend;
    private ProgressDialog progressdialog;
    private String number, id;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);

        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");
        mobileno = findViewById(R.id.mobileno);
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);
        otp = findViewById(R.id.otp);
        submit = findViewById(R.id.submit);
        resend = findViewById(R.id.resend);
        mobileno = findViewById(R.id.mobileno);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAuth = FirebaseAuth.getInstance();
        number = getIntent().getStringExtra("number");
        no = getIntent().getStringExtra("number2");
        sharedPrefrence();


        mobileno.setText(no);
        sendVerificationCode();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(otp.getText().toString())) {
                    Toast.makeText(AccountSetting_Activity.this, "Enter Otp", Toast.LENGTH_SHORT).show();
                } else if (otp.getText().toString().replace(" ", "").length() != 6) {
                    Toast.makeText(AccountSetting_Activity.this, "Enter right otp", Toast.LENGTH_SHORT).show();
                } else {
                    Handler handler2 = new Handler();
                    handler2.postDelayed(
                            new Runnable() {
                                public void run() {
                                    progressdialog.show();
                                    oneTime();
                                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otp.getText().toString().replace(" ", ""));
                                    signInWithPhoneAuthCredential(credential);
                                    //otp.setText(id);

                                }
                            }, 1000);
                }
            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });


    }

    private void sendVerificationCode() {

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                resend.setText("" + l / 1000);
                resend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resend.setText(" Resend");
                resend.setEnabled(true);
            }
        }.start();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+" + no,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        AccountSetting_Activity.this.id = id;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        String code = phoneAuthCredential.getSmsCode();
                        if (code != null) {
                            progressdialog.show();
                            signInWithPhoneAuthCredential(phoneAuthCredential);
                            otp.setText(code);
                            notification();
                        }

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(AccountSetting_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            oneTime();
                            startActivity(new Intent(AccountSetting_Activity.this, Main2Activity.class));
                            finish();
                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            Toast.makeText(AccountSetting_Activity.this, "Verification Filed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void oneTime() {

        SharedPreferences sharedPreferences = getSharedPreferences("com.mobisoftseo.myapplication_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login status", "on");
        editor.commit();
    }

    private void sharedPrefrence() {
        String st_no = number.toString();
        SharedPreferences sharedPreferences = getSharedPreferences("signup", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("number", st_no);
        editor.apply();

    }

    private void notification() {


        String message = "Welcome Back in NOX Gaming";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText("Code Sphere")
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .setAutoCancel(true)
                .setContentText(message);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}
