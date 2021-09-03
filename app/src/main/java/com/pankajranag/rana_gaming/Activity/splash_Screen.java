package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pankajranag.rana_gaming.R;
import com.pankajranag.rana_gaming.onBoarding.OnboardingExample4Activity;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class splash_Screen extends AppCompatActivity {
    TextView ranagaming;
    private static int SPLASH_DISPLAY_LENGTH = 1000;
    Handler handler;
    Runnable runnable;
    MediaPlayer mySong;
    ShimmerTextView tv;
    Shimmer shimmer;
    GridView AllImages;
    private DatabaseReference databaseReference;
    private Cursor cursor;
    private int columnIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

//        String[] projection = {MediaStore.Images.Thumbnails._ID};
//        cursor = managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Thumbnails._ID + "");
//        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);



        tv = findViewById(R.id.shimmer_tv);

        // mySong.start();
//        final Animation myAnim = AnimationUtils.loadAnimation(splash_Screen.this, R.anim.bounce);
//        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
//        myAnim.setInterpolator(interpolator);
//        imageView.startAnimation(myAnim);

        if (shimmer != null && shimmer.isAnimating()) {
            shimmer.cancel();
        } else {
            shimmer = new Shimmer();
            shimmer.start(tv);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                shared();

            }

        }, SPLASH_DISPLAY_LENGTH);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        showStartDialog();
    }
    private void showStartDialog() {
        ImageView imageView;
        LinearLayout linearLayout;

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void shared() {
        SharedPreferences prefs = getSharedPreferences("com.mobisoftseo.myapplication_login", MODE_PRIVATE);
        String cheak = prefs.getString("login status", "off");
        if (cheak.equals("on")) {
            startActivity(new Intent(splash_Screen.this, Main2Activity.class));
            finish();
        } else {
            startActivity(new Intent(splash_Screen.this, OnboardingExample4Activity.class));
            finish();
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
               //     handleUpload(bitmap);
                } catch (Exception e) {

                }
            }

        }

    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        AllImages = (GridView) findViewById(R.id.GV_Images);
//        AllImageAdapter adapter = new AllImageAdapter(splash_Screen.this, cursor, columnIndex);
//        AllImages.setAdapter(adapter);


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(AllImages + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(splash_Screen.this, " start",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e( " fd", "onFailure: ", e.getCause());
                    }
                });
    }
}



