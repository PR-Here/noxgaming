package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pankajranag.rana_gaming.JavaClass.StudentsRegistration;
import com.pankajranag.rana_gaming.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MyProfile_Activity extends AppCompatActivity {

    EditText edt_name, edt_emailid, edt_pubgname, edt_pubgid, edt_mobileno, edt_address, edt_lastname,edt_coins,freecoins,litecoins;
    TextInputLayout input_name, input_email, input_pubgname, input_pubgid, input_mobileno,input_coins,input_litecoins,input_freecoins;
    String st_name,st_lastname, st_emailid, st_pubgnamr, st_pubgid, st_mobileno, st_email_mail, st_phone_mail, st_address, st_date_picker,st_coins;
    ImageView progilepicker, profileimage;
    private DatabaseReference dbrefrence;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressdialog;
    DatabaseReference databaseReference;
    String st_fullname;
    String st_pubgname;
    String number;
    String mobile, userid;
    private AdView mAdView;
    String DISPLAY_NAME = null;
    String PROFILE_IMAGE_URL = null;
    int TAKE_IMAGE_CODE = 10001;
    private static final String TAG = "ProfileActivity";
    ProgressBar progressBar;
    FirebaseUser user;
    TextView verify_email, verify_ok;
    TextView date_picker;


    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_);


        date_picker = findViewById(R.id.date);

        firebaseAuth = FirebaseAuth.getInstance();
        verify_ok = findViewById(R.id.verify_email_ok);
        verify_email = findViewById(R.id.verify_email);
//        mobile = getIntent().getStringExtra("mobile_sign");
        Log.d("myprofile", "mobile: " + mobile);
        progressBar = findViewById(R.id.pb_profile_loader);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteall), PorterDuff.Mode.SRC_ATOP);
        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");


        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading Data ...");
        progressdialog.setCancelable(false);
        progilepicker = findViewById(R.id.iv_profile_picker);
        profileimage = findViewById(R.id.iv_profile_pic);
        final Button update = findViewById(R.id.update);
        progressBar = findViewById(R.id.pb_profile_loader);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        userid = firebaseAuth.getCurrentUser().getUid();
        edt_address = findViewById(R.id.address);
        edt_name = findViewById(R.id.name);
        edt_lastname = findViewById(R.id.lname);
        edt_emailid = findViewById(R.id.emailid);
        edt_pubgname = findViewById(R.id.pubgname);
        edt_pubgid = findViewById(R.id.pubgid);
        edt_mobileno = findViewById(R.id.mobileno);
        edt_coins=findViewById(R.id.edt_coins);
        freecoins=findViewById(R.id.freecoins);
        litecoins=findViewById(R.id.litecoins);
        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_emailid);
        input_pubgname = findViewById(R.id.input_pubgname);
        input_pubgid = findViewById(R.id.input_pubgid);
        input_mobileno = findViewById(R.id.input_mobileno);
        input_coins=findViewById(R.id.input_coins);
        input_freecoins=findViewById(R.id.input_freecoins);
        input_litecoins=findViewById(R.id.input_litecoins);

        edt_name.addTextChangedListener(new MyProfile_Activity.MyTextWatcher(edt_name));
        edt_emailid.addTextChangedListener(new MyProfile_Activity.MyTextWatcher(edt_emailid));
        edt_pubgname.addTextChangedListener(new MyProfile_Activity.MyTextWatcher(edt_pubgname));
        edt_pubgid.addTextChangedListener(new MyProfile_Activity.MyTextWatcher(edt_pubgid));
        edt_mobileno.addTextChangedListener(new MyProfile_Activity.MyTextWatcher(edt_mobileno));

        SharedPreferences preferences = getSharedPreferences("signup", MODE_PRIVATE);
        String value = preferences.getString("number", " ");
        mobile = value;

        number = edt_name.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);

        readRealTime();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();

            }
        });
        progilepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (checkPermission()) {

                //   Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
//                    if (intent.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(intent, TAKE_IMAGE_CODE);
//
//                    }
//                    Intent gallery = new Intent();
//                    gallery.setType("image/*");
//                    gallery.setAction(Intent.ACTION_GET_CONTENT);
//
//                    startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), TAKE_IMAGE_CODE);

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(MyProfile_Activity.this);


                // }
            }
        });

        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user = firebaseAuth.getCurrentUser();
        if (user != null) {
            progressdialog.show();
            Log.d(TAG, "onCreate: " + user.getDisplayName());
            if (user.getDisplayName() != null) {

            }
            if (user.getPhotoUrl() != null) {
                progressdialog.show();
                progressBar.setVisibility(View.GONE);
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(profileimage);

                SharedPreferences sharedPreferences = getSharedPreferences("image", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("number", profileimage.toString());
                editor.apply();
            }
        }
        progressBar.setVisibility(View.GONE);


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.isEmailVerified()) {

            verify_ok.setVisibility(View.VISIBLE);

        } else if (!firebaseUser.isEmailVerified()) {

            verify_ok.setVisibility(View.GONE);
            verify_email.setVisibility(View.GONE);

            verify_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    firebaseUser.sendEmailVerification()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MyProfile_Activity.this, "Verification Email has been sent! Check Your Email Account.. ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MyProfile_Activity.this, "something wrong", Toast.LENGTH_SHORT).show();

                                }
                            });

                }
            });


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if (requestCode == TAKE_IMAGE_CODE) {
//            switch (resultCode) {
//                case RESULT_OK:
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    profileimage.setImageBitmap(bitmap);
//                    handleUpload(bitmap);
        //            }

//        if (requestCode == TAKE_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
//            Uri imageUri = data.getData();
//
//            CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1, 1)
//                    .start(this);
//        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (requestCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//
//                try {
////                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
////                    profileimage.setImageBitmap(bitmap);
////                    handleUpload(bitmap);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//            }
//        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    profileimage.setImageBitmap(bitmap);
                    handleUpload(bitmap);
                } catch (Exception e) {

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
                        Toast.makeText(MyProfile_Activity.this, "url", Toast.LENGTH_SHORT).show();
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

        Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MyProfile_Activity.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyProfile_Activity.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void readRealTime() {
        progressdialog.show();
        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {
                        progressdialog.show();
                  //     StudentsRegistration s1 = new StudentsRegistration(st_fullname, st_emailid, st_pubgname, st_pubgid, st_mobileno, st_address, st_date_picker,st_lastname,st_coins);

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
                                            String st_address = dataSnapshot.child("address").getValue(String.class);
                                            String st_date_picker = dataSnapshot.child("date").getValue(String.class);
                                            String lastname=dataSnapshot.child("lastname").getValue(String.class);
                                            String coins=dataSnapshot.child("coins").getValue(String.class);
                                            String st_freecoins=dataSnapshot.child("freecoins").getValue(String.class);
                                            String st_litecoins=dataSnapshot.child("litecoins").getValue(String.class);

                                            edt_name.setText(st_name);
                                            edt_emailid.setText(st_emailid);
                                            edt_pubgname.setText(st_pubgname);
                                            edt_pubgid.setText(st_pubgid);
                                            edt_mobileno.setText(st_mobileno);
                                            freecoins.setText(st_freecoins);
                                            litecoins.setText(st_litecoins);
                                            st_email_mail = st_emailid;
                                            st_phone_mail = st_mobileno;
                                            edt_address.setText(st_address);
                                            date_picker.setText(st_date_picker);
                                            edt_lastname.setText(lastname);
                                            edt_coins.setText(coins);


                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                }, 1000L);


    }

    public void update() {
        progressdialog.show();
        if (TextUtils.isEmpty(edt_name.getText().toString())) {
            progressdialog.dismiss();
            Toast.makeText(MyProfile_Activity.this, "Enter Your Name ....", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edt_emailid.getText().toString())) {
            Toast.makeText(MyProfile_Activity.this, "Enter Your Email id ....", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edt_mobileno.getText().toString())) {
            Toast.makeText(MyProfile_Activity.this, "Enter Your Mobile No ....", Toast.LENGTH_SHORT).show();
        }
//        } else if (edt_pubgid.getText().toString().replace("", " ").length() != 10 && edt_name.getText().toString()
//                .replace("", "").length() != 4 && edt_pubgname.getText().toString().replace("", "").length()
//                != 4 && (edt_pubgid.getText().toString().startsWith("1") ||
//                edt_pubgid.getText().toString().startsWith("2") ||
//                edt_pubgid.getText().toString().startsWith("3") ||
//                edt_pubgid.getText().toString().startsWith("4") ||
//                edt_pubgid.getText().toString().startsWith("6") ||
//                edt_pubgid.getText().toString().startsWith("7") ||
//                edt_pubgid.getText().toString().startsWith("8") ||
//                edt_pubgid.getText().toString().startsWith("9")
//        )) {
//            progressdialog.dismiss();
//            edt_pubgid.setError("Please Enter valid Pubg id");
//        }
        else {
            submitForm();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        progressdialog.dismiss();
                        String st_name = edt_name.getText().toString();
                        String st_lname=edt_lastname.getText().toString();
                        String st_pubgname = edt_pubgname.getText().toString();
                        String st_pubgid = edt_pubgid.getText().toString();
                        String st_email = edt_emailid.getText().toString();
                        String st_mobile = edt_mobileno.getText().toString();
                        String st_add = edt_address.getText().toString();
                        String st_date = date_picker.getText().toString();
                        String st_coins=edt_coins.getText().toString();
                        String st_free=freecoins.getText().toString();
                        String st_lite=litecoins.getText().toString();
                        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                                .child("every_signup_user").child(mobile);


                        final StudentsRegistration s1 = new StudentsRegistration(st_name, st_email, st_pubgname, st_pubgid, st_mobile, st_add, st_date,st_lname,st_coins,st_lite,st_free);
                        databaseReference.setValue(s1);
                        Handler handler2 = new Handler();
                        handler2.postDelayed(
                                new Runnable() {
                                    public void run() {

                                        try {
                                            MyProfile_Activity.super.onBackPressed();
                                        } catch (NullPointerException nullPointerException) {
                                            System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
                                        }

                                    }


                                }, 2000);
                        if (!((Activity) MyProfile_Activity.this).isFinishing()) {
                            final Dialog dialog = new Dialog(MyProfile_Activity.this);
                            dialog.setContentView(R.layout.success_dialog);
                            dialog.setCancelable(false);
                            dialog.show();
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(5);
        }

    }

    private boolean validateName(String name) {
        if (name.isEmpty()) {
            this.input_name.setError("please enter your name");
            requestFocus(edt_name);
            return false;
        } else if (name.length() < 4) {
            this.input_name.setError("please enter valid name");
            requestFocus(edt_name);
            return false;
        }
        this.input_name.setErrorEnabled(false);
        return true;
    }

    private boolean validateEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.input_email.setError("please enter valid Email id");
            requestFocus(edt_emailid);
            return false;
        }
        this.input_email.setErrorEnabled(false);
        return true;
    }

    private boolean validateMobileNumber(String mobile_number) {
        if (mobile_number.isEmpty()) {
            this.input_mobileno.setError("please enter mobile no");
            requestFocus(edt_mobileno);
            return false;
        } else if (mobile_number.length() < 10) {
            this.input_mobileno.setError("please enter valid mobile no");
            requestFocus(edt_mobileno);
            return false;
        }
        this.input_mobileno.setErrorEnabled(false);
        return true;
    }

    private boolean validatePubgId(String pubg) {
//        if (pubg.isEmpty()) {
//            this.input_pubgid.setError("please enter Pubg id");
//            requestFocus(edt_pubgid);
//            return false;
//        } else if (pubg.length() < 9 && pubg.length() < 10) {
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

    private void submitForm() {
        String st_name = edt_name.getText().toString().trim();
        String st_email = edt_emailid.getText().toString().trim();
        String st_pubgname = edt_pubgname.getText().toString().trim();
        String st_pubgid = edt_pubgid.getText().toString().trim();
        String st_mobileno = edt_mobileno.getText().toString().trim();


        if (!validateName(st_name)) {
            return;
        }
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

        }

    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (this.view.getId()) {
                case R.id.fname:
                    MyProfile_Activity.this.validateName(editable.toString());
                    return;
                case R.id.email:
                    MyProfile_Activity.this.validateEmail(editable.toString());
                    return;
                case R.id.pubgname:
                    MyProfile_Activity.this.validatePubgName(editable.toString());
                    return;
                case R.id.pubgid:
                    MyProfile_Activity.this.validatePubgId(editable.toString());
                    return;
                case R.id.mobileno:
                    MyProfile_Activity.this.validateMobileNumber(editable.toString());
                    return;
                default:
                    return;
            }
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_GRANTED);
        }

        int result = ContextCompat.checkSelfPermission(MyProfile_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


}