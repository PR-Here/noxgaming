package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.pankajranag.rana_gaming.R;

import java.util.Arrays;
import java.util.List;

public class LoginOtpPhoneNo extends AppCompatActivity {
    private static final String TAG = "LoginOtpphoneNo";
    private CountryCodePicker countryCodePicker;
    private EditText number;
    private Button next;
    int AUTHUI_REQUEST_CODE = 10001;
    private ProgressDialog progressdialog;

    String st_mobileno;
    FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    TextView terms;

    public static final String USER_DETAAIL = "user_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp_phone_no);
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);
        terms=findViewById(R.id.terms);
        firebaseAuth = FirebaseAuth.getInstance();


        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user").child("every_signup_user");

        countryCodePicker = findViewById(R.id.ccp);
        number = findViewById(R.id.editText_carrierNumber);
        next = findViewById(R.id.next);
        countryCodePicker.registerCarrierNumberEditText(number);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(number.getText().toString())) {
                    Toast.makeText(LoginOtpPhoneNo.this, "Enter No ....", Toast.LENGTH_SHORT).show();
                } else if (number.getText().toString().replace(" ", "").length() != 10) {
                    Toast.makeText(LoginOtpPhoneNo.this, "Enter Correct No ...", Toast.LENGTH_SHORT).show();
                } else {
                    st_mobileno = number.getText().toString();
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
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginOtpPhoneNo.this,Terms_ConditionActivity.class);
                startActivity(intent);
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
                    Handler handler2 = new Handler();
                    handler2.postDelayed(
                            new Runnable() {
                                public void run() {

                                    Intent intent = new Intent(LoginOtpPhoneNo.this, AccountSetting_Activity.class);
                                  //  intent.putExtra("number2", number.getText().toString());
                                  //  intent.putExtra("number", countryCodePicker.getFullNumberWithPlus().replace(" ", ""));
                                    startActivity(intent);
                                    finish();

                                }
                            }, 1000);

                }
            } else {
                Toast.makeText(LoginOtpPhoneNo.this, "Mobile No. Not Registered! please Signup..", Toast.LENGTH_LONG).show();
                progressdialog.dismiss();

            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };

    public void handleLoginRegister() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                //     new AuthUI.IdpConfig.FacebookBuilder().build(),
                //  new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
                .setLogo(R.drawable.ic_launcher_background)
                .setAlwaysShowSignInMethodScreen(true)
                .setIsSmartLockEnabled(false)

                .build();

        startActivityForResult(intent, AUTHUI_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTHUI_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // We have signed in the user or we have a new user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "onActivityResult: " + user.toString());
                //Checking for User (New/Old)
                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                    //This is a New User
                    Toast.makeText(LoginOtpPhoneNo.this, "Welcome to NoX Gaming", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MainActivity.class));
                    this.finish();
                    oneTime();

                } else {
                    //This is a returning user
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        oneTime();

                        Toast.makeText(LoginOtpPhoneNo.this, "Welcome back", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, MainActivity.class));
                        this.finish();
                    }
                }

//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                this.finish();

            } else {
                // Signing in failed
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null) {
                    Toast.makeText(LoginOtpPhoneNo.this, "Login Failed!!", Toast.LENGTH_LONG).show();

                    Log.d(TAG, "onActivityResult: the user has cancelled the sign in request");
                } else {
                    Log.e(TAG, "onActivityResult: ", response.getError());
                }
            }
        }
    }

    public void oneTime() {

        SharedPreferences sharedPreferences = getSharedPreferences("com.mobisoftseo.myapplication_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login status", "on");
        editor.commit();

    }


}
