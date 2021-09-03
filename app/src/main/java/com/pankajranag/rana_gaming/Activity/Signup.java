package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.pankajranag.rana_gaming.JavaClass.StudentsRegistration;
import com.pankajranag.rana_gaming.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Signup extends AppCompatActivity implements LocationListener {

    TextInputLayout input_name, input_emailid, input_pubgname, input_pubgid,
            input_mobileno, input_coins, input_freecoins, input_litecoins;
    EditText edt_fname, edt_emailid, edt_mobileno, edt_pubgid, edt_paytmno,
            edt_pubgname, edt_lastname, edt_coins, litecoins, freecoins;
    Button login, register;
    private ProgressDialog progressdialog;
    private static final String TAG = "LoginRegisterActivity";
    int AUTHUI_REQUEST_CODE = 10001;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    int id = 0;
    ImageView progilepicker, profileimage;
    int TAKE_IMAGE_CODE = 10001;
    ProgressBar progressBar;
    String st_fullname, st_emailid, st_pubgname, st_pubgid, st_mobileno, st_address, st_date_picker, st_lastname, st_coins, st_freecoins, st_litecoins;
    private AdView mAdView;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText editText_location;
    LocationManager locationManager;
    TextView date_picker;
    private CountryCodePicker countryCodePicker;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        countryPopup();
        date_picker = findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        date_picker.setText(currentDateandTime);

        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");
        progilepicker = findViewById(R.id.iv_profile_picker);
        profileimage = findViewById(R.id.iv_profile_pic);
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        countryCodePicker = findViewById(R.id.ccp);
        editText_location = findViewById(R.id.location);
        if (ContextCompat.checkSelfPermission(Signup.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Signup.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION

            }, 100);

        }
        getLocation();
        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user").child("every_signup_user");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);

        // inputLayoutValidate();
        // edtTextValidate();

        register = findViewById(R.id.sign_signup);

        input_pubgname = findViewById(R.id.input_pubgname);
        input_pubgid = findViewById(R.id.input_pubgid);
        input_mobileno = findViewById(R.id.input_mobileno);
        input_coins = findViewById(R.id.input_coins);
        input_freecoins = findViewById(R.id.input_freecoins);
        input_litecoins = findViewById(R.id.input_litecoins);

        edt_fname = findViewById(R.id.fname);
        edt_lastname = findViewById(R.id.lastName);
        edt_emailid = findViewById(R.id.emailID);
        edt_pubgname = findViewById(R.id.pubgname);
        edt_mobileno = findViewById(R.id.mobileno);
        edt_pubgid = findViewById(R.id.pubgid);
        edt_coins = findViewById(R.id.coins);
        freecoins = findViewById(R.id.freecoins);
        litecoins = findViewById(R.id.litecoins);


        sharedPrefrence();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocation();
                if (TextUtils.isEmpty(edt_fname.getText().toString())) {
                    Toast.makeText(Signup.this, "Enter Your Name ....", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edt_lastname.getText().toString())) {
                    Toast.makeText(Signup.this, "Enter Last Name ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edt_emailid.getText().toString())) {
                    Toast.makeText(Signup.this, "Enter Your Email id ....", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edt_mobileno.getText().toString())) {
                    Toast.makeText(Signup.this, "Enter Your Mobile No ....", Toast.LENGTH_SHORT).show();
                } else {
                    //  submitForm();
                    alertDialog();


                }
            }
        });
        progilepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {

                    //   Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, TAKE_IMAGE_CODE);

                    }

                }
            }
        });


    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        progressdialog.show();

        try {
            progressdialog.dismiss();
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, Signup.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            Geocoder geocoder = new Geocoder(Signup.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            final String address = addresses.get(0).getAddressLine(0);

            editText_location.setText(address);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    private void register() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", edt_fname.getText().toString());
        map.put("emailid", edt_emailid.getText().toString());
        map.put("pubgname", edt_pubgname.getText().toString());
        map.put("pubgid", edt_pubgid.getText().toString());
        map.put("mobileno", edt_mobileno.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("signup user").push()
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        Log.i("jfbvkj", "onComplete: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("jfbvkj", "onFailure: " + e.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("jfbvkj", "onSuccess: ");
            }
        });


    }

    public void registerUser() {

        // Performing Validation by calling validation functions


        String phoneNo = edt_mobileno.getText().toString();


        //Call the next activity and pass phone no with it
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.putExtra("phoneNo", countryCodePicker.getFullNumberWithPlus() + phoneNo);
        intent.putExtra("phoneNo2", phoneNo);
        Toast.makeText(this, countryCodePicker.getFullNumberWithPlus() + " " + phoneNo, Toast.LENGTH_SHORT).show();

        startActivity(intent);
        finish();


    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(5);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    profileimage.setImageBitmap(bitmap);
                    handleUpload(bitmap);
            }
        } else if (requestCode == AUTHUI_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // We have signed in the user or we have a new user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "onActivityResult: " + user.toString());
                //Checking for User (New/Old)
                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                    //This is a New User
                    Toast.makeText(Signup.this, "Welcome to NoX Gaming", Toast.LENGTH_LONG).show();
                } else {
                    //This is a returning user
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        Toast.makeText(Signup.this, "You are Already Register plz Login", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(this, MainActivity.class));
//                        this.finish();
                    }
                }

//
            } else {
                // Signing in failed
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null) {
                    Log.d(TAG, "onActivityResult: the user has cancelled the sign in request");
                } else {
                    Log.e(TAG, "onActivityResult: ", response.getError());
                }
            }
        }


    }

    private void handleUpload(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e.getCause());
                    }
                });
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Signup.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.edt_emailid.setError("please enter valid Email id");
            requestFocus(edt_emailid);
            return false;
        }
        this.edt_emailid.setEnabled(false);
        return true;
    }

    private boolean validatePubgId(String pubg) {
//        if (pubg.isEmpty()) {
//            this.input_pubgid.setError("please enter Pubg id");
//            requestFocus(edt_pubgid);
//            return false;
//        } else if (pubg.length() < 9) {
//            this.input_pubgid.setError("please enter valid Pubg id");
//            requestFocus(edt_pubgid);
//            return false;
//        }
//        this.input_pubgid.setErrorEnabled(false);
        return true;
    }

    private boolean validatePubgName(String name) {
//        if (name.isEmpty()) {
//            this.input_pubgname.setError("please enter your Pubg name");
//            requestFocus(edt_pubgname);
//            return false;
//        }
//        this.input_pubgname.setErrorEnabled(false);
        return true;
    }

   /* private void submitForm() {
        String st_name = edt_fname.getText().toString().trim();
        String st_email = edt_emailid.getText().toString().trim();
        String st_pubgname = edt_pubgname.getText().toString().trim();
        String st_pubgid = edt_pubgid.getText().toString().trim();
        String st_mobileno = edt_mobileno.getText().toString().trim();


        if (!validateEmail(st_email)) {
            return;
        }
        if (!validatePubgName(st_pubgname)) {
            return;
        }
        if (!validatePubgId(st_pubgid)) {
            return;
        }
        if (!validateMobileNumber(st_mobileno)) {
            return;
        }
        if ((edt_pubgid.getText().toString().startsWith("1") ||
                edt_pubgid.getText().toString().startsWith("2") ||
                edt_pubgid.getText().toString().startsWith("3") ||
                edt_pubgid.getText().toString().startsWith("4") ||
                edt_pubgid.getText().toString().startsWith("6") ||
                edt_pubgid.getText().toString().startsWith("7") ||
                edt_pubgid.getText().toString().startsWith("8") ||
                edt_pubgid.getText().toString().startsWith("9")
        )) {
            edt_pubgid.setError("Please Enter valid Pubg id");

        } else {


//            register();
//            registerUser();


        }

    }*/

    private void messageSend() {

        FirebaseMessaging.getInstance().subscribeToTopic("register")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(Signup.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void alertDialog() {
        new AlertDialog.Builder(Signup.this)
                .setTitle("Make Sure")
                .setMessage("All Details are correct?")
                //  .setMessage("https://t.me/NOXGAMING11")


                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        progressdialog.show();
                        int i = 0;
                        int j = 0;
                        int k = 0;
                        st_fullname = edt_fname.getText().toString();
                        st_lastname = edt_lastname.getText().toString();
                        st_emailid = edt_emailid.getText().toString();
                        st_pubgname = edt_pubgname.getText().toString();
                        st_pubgid = edt_pubgid.getText().toString();
                        st_mobileno = edt_mobileno.getText().toString();
                        st_address = editText_location.getText().toString();
                        st_date_picker = date_picker.getText().toString();
                        st_coins = String.valueOf(i);
                        st_freecoins = String.valueOf(k);
                        st_litecoins = String.valueOf(j);
                        //  submitForm();
                        firebaseAuth.createUserWithEmailAndPassword(st_emailid, st_mobileno)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            progressdialog.dismiss();

                                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                            firebaseUser.sendEmailVerification()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(Signup.this, "Verification Email has been sent! Check Your Email Account.. ", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(Signup.this, "something wrong", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });

                                            StudentsRegistration s1 = new StudentsRegistration(st_fullname, st_emailid, st_pubgname, st_pubgid, st_mobileno, st_address, st_date_picker, st_lastname, st_coins, st_litecoins, st_freecoins);
                                            databaseReference.child(st_mobileno).setValue(s1)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                progressdialog.show();
                                                                registerUser();
                                                                notification();
                                                                messageSend();
                                                                //     Toast.makeText(Signup.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                                                                progressdialog.dismiss();
                                                            } else {
                                                                Toast.makeText(Signup.this, "Something wrong", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                        }

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        progressdialog.dismiss();

                                    }
                                });

                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void notification() {

        String name = edt_fname.getText().toString();
        String email = edt_emailid.getText().toString();
        String id = edt_pubgid.getText().toString();
        String pname = edt_pubgname.getText().toString();
        String mno = edt_mobileno.getText().toString();
        String message = " Welcome to NOX Gaming ! Win Real Cash Reward ! Good Luck... ";
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
                .setContentText(name + message);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    private void sharedPrefrence() {
        String st_no = edt_fname.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("number", st_no);
        editor.apply();

    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_GRANTED);
        }

        int result = ContextCompat.checkSelfPermission(Signup.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void countryPopup() {
        TextView ok_txt,msg_txt;
        final Dialog dialog = new Dialog(Signup.this);
        dialog.setContentView(R.layout.signup_popup);
        dialog.setCancelable(false);
        msg_txt=dialog.findViewById(R.id.msg);
        ok_txt = dialog.findViewById(R.id.ok_dialog);
        ok_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        msg_txt.setText("Please Check your country code before Signup");
        dialog.show();
    }

}



