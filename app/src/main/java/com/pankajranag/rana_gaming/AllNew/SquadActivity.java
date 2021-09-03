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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pankajranag.rana_gaming.Activity.MainActivity;
import com.pankajranag.rana_gaming.Activity.SquadLoadPlayer_Activity;
import com.pankajranag.rana_gaming.JavaClass.CompleteEntry;
import com.pankajranag.rana_gaming.R;

public class SquadActivity extends AppCompatActivity {
    String dinfo3, dstatus3, dupid3, dpaidentry3, dwatchnow3, dupdate3, droomid3, dpass3;
    TextView matchno3, date3, time3, registration3, roonid3, password3, prizepool3, perkill3, entryfee3, type3, mode3, map3;
    TextView prizepool_anim1, prizepool_anim2, prizepool_anim3, prizepool_anim4, prizepool_anim5;
    String dinfo, dstatus, dpaidentry, dupid, dwatchnow, droomid, dpass, dmatchno, drank1, drank2, drank3, drank4, dwinner3;
    Button fab, joinnow2, joinnow3, joinnow4, joinnow5;
    TextView dialog_txt, text_view3;
    String st_instagram, both_layout, st_coins, main_msg, pos_msg, wt_msg, st_dialogmsg;
    EditText pubg_name, mobileno, pubg_name_squad;
    String mobile, st_payment = "";
    DatabaseReference solo_seekbar_database, duo_seekbar_database, squad_seekbar_database, arcade_seekbar_database, tdm_seekbar_database;
    SeekBar seek_bar, seekBar2, seekBar3, seekBar4, seekBar5;
    Button info, watchnow, info3, info4, info5;
    TextView information;
    private ProgressDialog progressDialog;
    private AdView mAdView2;
    private DatabaseReference Post, post_classic, post_arcade, post_tdm, classic_solo, classic_duo;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference dbrefrence3 = databaseReference.child("mainactivity").child("image3");
    private DatabaseReference dbrefrence_join_player, classic_solo_join, classic_duo_join, arcade_join, tdm_join;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squad);
        //dialog progress
        progressDialog = new ProgressDialog(SquadActivity.this);
        progressDialog.setMessage("Data is loading...");
        progressDialog.setTitle("please wait");
        progressDialog.setCancelable(false);
        //admod
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
                SquadActivity.super.onBackPressed();
            }
        });
        //database refrence
        firebaseAuth = FirebaseAuth.getInstance();
        post_classic = FirebaseDatabase.getInstance().getReference().child("classicmode");
        SharedPreferences preferences = getSharedPreferences("signup", MODE_PRIVATE);
        String value = preferences.getString("number", " ");
        mobile = value;
        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);
        dbrefrence_join_player = FirebaseDatabase.getInstance().getReference("Classic_join_player").child("EntryComplete_player");
        squad_seekbar_database = FirebaseDatabase.getInstance().getReference("Classic_join_player")
                .child("EntryComplete_player");
        Post = FirebaseDatabase.getInstance().getReference().child("mainactivity");
        //id of textview
        matchno3 = findViewById(R.id.matchno3);
        date3 = findViewById(R.id.date3);
        time3 = findViewById(R.id.time3);
        registration3 = findViewById(R.id.registration3);
        roonid3 = findViewById(R.id.id3);
        password3 = findViewById(R.id.password3);
        prizepool3 = findViewById(R.id.prizepool3);
        perkill3 = findViewById(R.id.perkill3);
        entryfee3 = findViewById(R.id.entryfee3);
        type3 = findViewById(R.id.type3);
        mode3 = findViewById(R.id.mode3);
        info3 = findViewById(R.id.info3);
        map3 = findViewById(R.id.map3);
        joinnow3 = findViewById(R.id.joinnow3);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        prizepool_anim3 = findViewById(R.id.watchnow3);
        joinnow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinnow3();
            }
        });
        info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView cut;
                final Dialog dialog = new Dialog(SquadActivity.this);
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
        prizepool_anim3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SquadActivity.this, SquadLoadPlayer_Activity.class));
            }
        });

        classicMode();
        seebbarr3();
        headerData();
        readRealTime();
    }

    private void classicMode() {

        progressDialog.show();
        post_classic.child("firstactivity")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        String dentryfee = dataSnapshot.child("entryfee").getValue(String.class);
                        String dtype = dataSnapshot.child("type").getValue(String.class);
                        String dmode = dataSnapshot.child("mode").getValue(String.class);
                        String dmap = dataSnapshot.child("map").getValue(String.class);
                        drank1 = dataSnapshot.child("rank1").getValue(String.class);
                        drank2 = dataSnapshot.child("rank2").getValue(String.class);
                        drank3 = dataSnapshot.child("rank3").getValue(String.class);
                        drank4 = dataSnapshot.child("rank4").getValue(String.class);
                        String drank5 = dataSnapshot.child("rank5").getValue(String.class);
                        String drank6 = dataSnapshot.child("rank6").getValue(String.class);
                        String drank7 = dataSnapshot.child("rank7").getValue(String.class);
                        String drank8 = dataSnapshot.child("rank8").getValue(String.class);
                        String drank9 = dataSnapshot.child("rank9").getValue(String.class);
                        String drank10 = dataSnapshot.child("rank10").getValue(String.class);
                        String dtime = dataSnapshot.child("time").getValue(String.class);
                        dmatchno = dataSnapshot.child("matchno").getValue(String.class);
                        String ddate = dataSnapshot.child("date").getValue(String.class);
                        String dperkill = dataSnapshot.child("perkill").getValue(String.class);
                        dinfo3 = dataSnapshot.child("information").getValue(String.class);
                        dstatus3 = dataSnapshot.child("status").getValue(String.class);
                        dpaidentry3 = dataSnapshot.child("paidentry").getValue(String.class);
                        dupid3 = dataSnapshot.child("upiid").getValue(String.class);
                        dwatchnow3 = dataSnapshot.child("disabled").getValue(String.class);
                        dupdate3 = dataSnapshot.child("update").getValue(String.class);
                        droomid3 = dataSnapshot.child("roomid").getValue(String.class);
                        dpass3 = dataSnapshot.child("password").getValue(String.class);
                        String dprizepool = dataSnapshot.child("prizepool").getValue(String.class);
                        dwinner3 = dataSnapshot.child("winner").getValue(String.class);

                        matchno3.setText(dmatchno);
                        date3.setText(ddate);
                        time3.setText(dtime);
                        roonid3.setText(droomid3);
                        password3.setText(dpass3);
                        registration3.setText(dstatus3);
                        perkill3.setText(dperkill);
                        entryfee3.setText(dentryfee);
                        type3.setText(dtype);
                        mode3.setText(dmode);
                        map3.setText(dmap);
                        prizepool3.setText(dprizepool);
                        if (!dwatchnow3.isEmpty()) {
                            joinnow3.setVisibility(View.GONE);
                        } else {
                            joinnow3.setVisibility(View.VISIBLE);

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    public void joinnow3() {

        final Spinner under_spin, under_coose_spin;
        final Button cancl, join;
        final CheckBox checkBoxx;
        final Dialog dialog = new Dialog(SquadActivity.this);
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
                    Toast.makeText(SquadActivity.this, "Please enter your PUBG Name to complete your entry.", Toast.LENGTH_SHORT).show();
                } else {
                    checkCoins3();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();


    }

    private void checkCoins3() {

        int coin = Integer.parseInt(st_coins);
        int paidentry = Integer.parseInt(dpaidentry3);

        if (coin >= paidentry) {
            joinSquadPlayer();

            final int total = coin - paidentry;
            Log.d("TAG", "totalll " + total);
            final String st_total = String.valueOf(total);
            //   txt_coins.setText(st_total);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        progressDialog.dismiss();

                        String st_coins = st_total;


                        databaseReference.child("coins").setValue(st_coins);
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

    private void joinSquadPlayer() {

        final String pubgname = pubg_name.getText().toString();
        final String mobile_no = mobileno.getText().toString();
        final String type = st_payment;


        firebaseAuth.createUserWithEmailAndPassword(pubgname, mobile_no)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        CompleteEntry completeEntry = new CompleteEntry(pubgname, mobile_no, type);
                        dbrefrence_join_player.child(pubgname).setValue(completeEntry)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            progressDialog.show();
                                            notification();
                                            Toast.makeText(SquadActivity.this, "Player Join Successfully", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SquadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void readRealTime() {
        progressDialog.show();
        Handler handler2 = new Handler();
        handler2.postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        Post
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        progressDialog.dismiss();

                                        main_msg = dataSnapshot.child("msg").getValue(String.class);
                                        wt_msg = dataSnapshot.child("wt_msg").getValue(String.class);
                                        pos_msg = dataSnapshot.child("btn_msg").getValue(String.class);
                                        st_dialogmsg = dataSnapshot.child("dialog_msg").getValue(String.class);

                                        both_layout = dataSnapshot.child("both").getValue(String.class);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(SquadActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }, 1000L);


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

    public void seebbarr3() {
        squad_seekbar_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("TAG", "Join Player : " + dataSnapshot.getChildrenCount());
                    int count = 0;


                    seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
                    seekBar3.setVisibility(View.GONE);

                    text_view3 = (TextView) findViewById(R.id.textView3);

                    text_view3.setText("Join Player : " + dataSnapshot.getChildrenCount() + " / " + seekBar3.getMax());
                    seekBar3.setVisibility(View.GONE);

                    if (dataSnapshot.getChildrenCount() == 100) {
                        seekBar3.setVisibility(View.GONE);

                        fab.setText("MATCH FULL");
                        joinnow3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SquadActivity.this, "match already full please try after some time!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        seekBar3.setVisibility(View.GONE);

                        joinnow3.setText("JOIN NOW");
                        joinnow3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                joinnow3();
                            }
                        });

                    }
                    seekBar3.setVisibility(View.GONE);

                    seekBar3.setOnSeekBarChangeListener(
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

}