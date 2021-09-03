package com.pankajranag.rana_gaming.AllNew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pankajranag.rana_gaming.Activity.LoadPlayer_Activity;
import com.pankajranag.rana_gaming.Activity.Main2Activity;
import com.pankajranag.rana_gaming.Activity.MainActivity;
import com.pankajranag.rana_gaming.Activity.SquadLoadPlayer_Activity;
import com.pankajranag.rana_gaming.JavaClass.CompleteEntry;
import com.pankajranag.rana_gaming.R;

public class SoloActivity extends AppCompatActivity {

    public static final String TAG = "soloActivity";
    String st_dialogmsg, mobile, dpaidentry, st_coins, dinfo, droomid, dpass;
    String dstatus, dupid, dwatchnow;
    String dupdate;
    String st_solo1, st_solo2, st_solo3, st_solo4, dwinner1, dmatchno1;
    TextView matchno, date, time, registration, roonid, password, prizepool, perkill, entryfee, type, mode, map, text_view, text_view2, text_view3, text_view4, text_view5;
    TextView prizepool_anim1;
    Button fab, info, checkJoinPlayer;
    TextView information;
    EditText pubg_name, mobileno;
    TextView dialog_txt;
    SeekBar seek_bar;
    DatabaseReference solo_seekbar_database;
    ImageView first;
    private DatabaseReference classic_solo;
    private ProgressDialog progressDialog;
    private String st_payment = "";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference dbrefrence = databaseReference.child("mainactivity").child("image1");
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbrefrence_join_player, classic_solo_join, post_classic, arcade_join, tdm_join;
    private AdView mAdView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo);
        progressDialog = new ProgressDialog(SoloActivity.this);
        progressDialog.setMessage("Data is loading...");
        progressDialog.setTitle("please wait");
        progressDialog.setCancelable(false);
        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                SoloActivity.super.onBackPressed();
            }
        });
        info = findViewById(R.id.info);
        prizepool_anim1 = findViewById(R.id.third_prizepoolanim);
        checkJoinPlayer = findViewById(R.id.watchnow);
        seek_bar = (SeekBar) findViewById(R.id.seekBar);
        matchno = findViewById(R.id.matchno);
        date = findViewById(R.id.date);
        first = (ImageView) findViewById(R.id.image);
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
        firebaseAuth = FirebaseAuth.getInstance();
        classic_solo = FirebaseDatabase.getInstance().getReference().child("classicSolo");
        classic_solo_join = FirebaseDatabase.getInstance().getReference("ClassicSolo_join_player").child("EntryComplete_player");
        post_classic = FirebaseDatabase.getInstance().getReference().child("classicmode");
        solo_seekbar_database = FirebaseDatabase.getInstance().getReference("ClassicSolo_join_player")
                .child("EntryComplete_player");
        SharedPreferences preferences = getSharedPreferences("signup", MODE_PRIVATE);
        String value = preferences.getString("number", " ");
        mobile = value;
        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);
        fab = findViewById(R.id.joinnow);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinnow();
            }
        });
        checkJoinPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlayer();
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView cut;
                final Dialog dialog = new Dialog(SoloActivity.this);
                dialog.setContentView(R.layout.match_rule_popup);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                Window window = dialog.getWindow();
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo);

                dialog.show();
            }

        });

        prizepool_anim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prizePoolMatchno1();
            }
        });
        headerData();

        seebbarr();
        classicSolo();

    }


    public void joinnow() {

        final Spinner under_spin, under_coose_spin;
        final Button cancl, join;
        final CheckBox checkBoxx;
        final Dialog dialog = new Dialog(SoloActivity.this);
        dialog.setContentView(R.layout.join_popup);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_txt = dialog.findViewById(R.id.txt_dialog);
        dialog_txt.setText(st_dialogmsg);
        pubg_name = dialog.findViewById(R.id.pubg_name_dialog);
        cancl = dialog.findViewById(R.id.cancel_button);
        join = dialog.findViewById(R.id.join_button);
        checkBoxx = dialog.findViewById(R.id.checkbox_terms);
        mobileno = dialog.findViewById(R.id.mobileno);
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
                    Toast.makeText(SoloActivity.this, "Please enter your PUBG Name to complete your entry.", Toast.LENGTH_SHORT).show();
                } else {
                    checkCoins1();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();


    }

    private void checkCoins1() {

        int coin = Integer.parseInt(st_coins);
        int paidentry = Integer.parseInt(dpaidentry);

        if (coin >= paidentry) {
            joinSoloPlayer();

            int total = coin - paidentry;
            Log.d(TAG, "totalll " + total);
            final String st_total = String.valueOf(total);
            //txt_coins.setText(st_total);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        progressDialog.dismiss();


                        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                                .child("every_signup_user").child(mobile);


                        databaseReference.child("coins").setValue(st_total);
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
                                            progressDialog.show();
                                            notification();
                                            Toast.makeText(SoloActivity.this, "Player Join Successfully", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //    Toast.makeText(SoloActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void notification() {

        String name = pubg_name.getText().toString();

        String message = " Your PUBG MOBILE SOLO MATCH Entry is complete, room id and password share before 10 minutes to match start.";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText("Code Sphere")
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .setAutoCancel(false)
                .setSound(uri)
                .setContentText(name + message);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    private void classicSolo() {

        progressDialog.show();
        classic_solo.child("SoloMatch")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
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
                        Toast.makeText(SoloActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private void prizePoolMatchno1() {
        TextView winner, matchno, first, second, third, four;
        final BottomSheetDialog dialog = new BottomSheetDialog(SoloActivity.this);
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

    private void loadPlayer() {
        Intent intent = new Intent(SoloActivity.this, LoadPlayer_Activity.class);
        startActivity(intent);

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
                    if (dataSnapshot.getChildrenCount() == 100) {
                        seek_bar.setVisibility(View.GONE);
                        fab.setText("MATCH FULL");
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SoloActivity.this, "match already full please try after some time!", Toast.LENGTH_SHORT).show();
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

    private void headerData() {
        progressDialog.show();
        databaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        if (dataSnapshot.exists()) {
                            String st_name = dataSnapshot.child("name").getValue(String.class);
                            String st_emailid = dataSnapshot.child("email").getValue(String.class);
                            String st_pubgname = dataSnapshot.child("pubgname").getValue(String.class);
                            String st_pubgid = dataSnapshot.child("pubgid").getValue(String.class);
                            String st_mobileno = dataSnapshot.child("mobileno").getValue(String.class);

                            st_coins = dataSnapshot.child("coins").getValue(String.class);
                            //they both are header
                            // name.setText(st_name);
                            //txt_coins.setText(st_coins);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
    }
}