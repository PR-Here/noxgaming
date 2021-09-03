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
import com.pankajranag.rana_gaming.Activity.DuoLoadPlayer_Activity;
import com.pankajranag.rana_gaming.Activity.MainActivity;
import com.pankajranag.rana_gaming.JavaClass.CompleteEntry;
import com.pankajranag.rana_gaming.R;

public class DuoActivity extends AppCompatActivity {

    TextView matchno2, date2, time2, registration2, roonid2, password2, prizepool2, perkill2, entryfee2, type2, mode2, map2;
    private ProgressDialog progressDialog;
    private DatabaseReference classic_duo;
    String st_duo1, st_duo2, st_duo3, st_duo4, dwinner2, dmatchno2;
    String dinfo2, dstatus2, dupid2, dpaidentry2, dwatchnow2, dupdate2, st_watchnow, droomid2, dpass2;
    Button joinnow2;
    DatabaseReference  duo_seekbar_database,classic_duo_join;
    SeekBar seekBar2;
    TextView text_view2;
    TextView dialog_txt;
    String st_instagram, both_layout, st_coins, main_msg, pos_msg, wt_msg, st_dialogmsg;
    EditText pubg_name, mobileno, pubg_name_squad;
    String mobile;
    private String st_payment = "";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbrefrence2 = databaseReference.child("mainactivity").child("image2");
    Button info2,checkJoinPlayer;
    TextView information,prizepool_anim2;
    private AdView mAdView2;
    ImageView second;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duo);
        progressDialog = new ProgressDialog(DuoActivity.this);
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
                DuoActivity.super.onBackPressed();
            }
        });
        classic_duo = FirebaseDatabase.getInstance().getReference().child("classicDuo");
        duo_seekbar_database = FirebaseDatabase.getInstance().getReference("ClassicDuo_join_player")
                .child("EntryComplete_player");

        SharedPreferences preferences = getSharedPreferences("signup", MODE_PRIVATE);
        String value = preferences.getString("number", " ");
        mobile = value;
        databaseReference = FirebaseDatabase.getInstance().getReference("signup_user")
                .child("every_signup_user").child(mobile);
        classic_duo_join = FirebaseDatabase.getInstance().getReference("ClassicDuo_join_player").child("EntryComplete_player");

        firebaseAuth = FirebaseAuth.getInstance();
        second = (ImageView) findViewById(R.id.imageone2);
        checkJoinPlayer = findViewById(R.id.watchnow2);
        joinnow2 = findViewById(R.id.joinnow2);
        matchno2 = findViewById(R.id.matchno2);
        date2 = findViewById(R.id.date2);
        time2 = findViewById(R.id.time2);
        registration2 = findViewById(R.id.registration2);
        roonid2 = findViewById(R.id.id2);
        password2 = findViewById(R.id.password2);
        prizepool2 = findViewById(R.id.prizepool2);
        perkill2 = findViewById(R.id.perkill2);
        entryfee2 = findViewById(R.id.entryfee2);
        type2 = findViewById(R.id.type2);
        mode2 = findViewById(R.id.mode2);
        map2 = findViewById(R.id.map2);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        info2=findViewById(R.id.info2);
        prizepool_anim2 = findViewById(R.id.second_prizepoolanim);

        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView cut;
                final Dialog dialog = new Dialog(DuoActivity.this);
                dialog.setContentView(R.layout.match_rule_popup);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                cut = dialog.findViewById(R.id.cut_dialog);
                information = dialog.findViewById(R.id.dialog_info);
                information.setText(dinfo2);

                dialog.show();
            }

        });
        checkJoinPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlayer2();
            }
        });
        prizepool_anim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prizePoolMatchno2();
            }
        });
        headerData();
        seebbarr2();
        classicDuo();
    }
    public void seebbarr2() {
        duo_seekbar_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("TAG", "Join Player : " + dataSnapshot.getChildrenCount());
                    int count = 0;


                    seekBar2.setVisibility(View.GONE);

                    text_view2 = (TextView) findViewById(R.id.textView2);
                    seekBar2.setVisibility(View.GONE);

                    text_view2.setText("Join Player : " + dataSnapshot.getChildrenCount() + " / " + seekBar2.getMax());
                    seekBar2.setVisibility(View.GONE);

                    if (dataSnapshot.getChildrenCount() == 100) {
                        seekBar2.setVisibility(View.GONE);

                        joinnow2.setText("MATCH FULL");
                        joinnow2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(DuoActivity.this, "match already full please try after some time!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        seekBar2.setVisibility(View.GONE);

                        joinnow2.setText("JOIN NOW");
                        joinnow2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                joinnow2();
                            }
                        });

                    }
                    seekBar2.setVisibility(View.GONE);

                    seekBar2.setOnSeekBarChangeListener(
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
    private void classicDuo() {

        progressDialog.show();
        classic_duo.child("DuoActivity")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        String dentryfee = dataSnapshot.child("entryfee").getValue(String.class);
                        String dtype = dataSnapshot.child("type").getValue(String.class);
                        String dmode = dataSnapshot.child("mode").getValue(String.class);
                        String dmap = dataSnapshot.child("map").getValue(String.class);
                        st_duo1 = dataSnapshot.child("rank1").getValue(String.class);
                        st_duo2 = dataSnapshot.child("rank2").getValue(String.class);
                        st_duo3 = dataSnapshot.child("rank3").getValue(String.class);
                        st_duo4 = dataSnapshot.child("rank4").getValue(String.class);
                        String drank5 = dataSnapshot.child("rank5").getValue(String.class);
                        String drank6 = dataSnapshot.child("rank6").getValue(String.class);
                        String drank7 = dataSnapshot.child("rank7").getValue(String.class);
                        String drank8 = dataSnapshot.child("rank8").getValue(String.class);
                        String drank9 = dataSnapshot.child("rank9").getValue(String.class);
                        String drank10 = dataSnapshot.child("rank10").getValue(String.class);
                        String dtime = dataSnapshot.child("time").getValue(String.class);
                        dmatchno2 = dataSnapshot.child("matchno").getValue(String.class);
                        String ddate = dataSnapshot.child("date").getValue(String.class);
                        String dperkill = dataSnapshot.child("perkill").getValue(String.class);
                        dinfo2 = dataSnapshot.child("information").getValue(String.class);
                        dstatus2 = dataSnapshot.child("status").getValue(String.class);
                        dpaidentry2 = dataSnapshot.child("paidentry").getValue(String.class);
                        dupid2 = dataSnapshot.child("upiid").getValue(String.class);
                        dwatchnow2 = dataSnapshot.child("disabled").getValue(String.class);
                        dupdate2 = dataSnapshot.child("update").getValue(String.class);
                        droomid2 = dataSnapshot.child("roomid").getValue(String.class);
                        dpass2 = dataSnapshot.child("password").getValue(String.class);
                        String dprizepool = dataSnapshot.child("prizepool").getValue(String.class);
                        dwinner2 = dataSnapshot.child("winner").getValue(String.class);


                        matchno2.setText(dmatchno2);
                        date2.setText(ddate);
                        time2.setText(dtime);
                        roonid2.setText(droomid2);
                        password2.setText(dpass2);
                        registration2.setText(dstatus2);
                        perkill2.setText(dperkill);
                        entryfee2.setText(dentryfee);
                        type2.setText(dtype);
                        mode2.setText(dmode);
                        map2.setText(dmap);
                        prizepool2.setText(dprizepool);
                        if (!dwatchnow2.isEmpty()) {
                            joinnow2.setVisibility(View.GONE);
                        } else if (dwatchnow2.isEmpty()) {
                            joinnow2.setVisibility(View.VISIBLE);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }
    public void joinnow2() {


        final Spinner under_spin, under_coose_spin;
        final Button cancl, join;
        final CheckBox checkBoxx;
        final Dialog dialog = new Dialog(DuoActivity.this);
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
                    Toast.makeText(DuoActivity.this, "Please enter your PUBG Name to complete your entry.", Toast.LENGTH_SHORT).show();
                } else {
                    checkCoins2();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();



    }
    private void checkCoins2() {

        int coin = Integer.parseInt(st_coins);
        int paidentry = Integer.parseInt(dpaidentry2);

        if (coin >= paidentry) {
            joinDuoPlayer();

            int total = coin - paidentry;
            Log.d("TAG", "totalll " + total);
            final String st_total = String.valueOf(total);
            //this is for header view
           // txt_coins.setText(st_total);

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
    private void joinDuoPlayer() {

        final String pubgname = pubg_name.getText().toString();
        final String mobile_no = mobileno.getText().toString();
        final String type = st_payment;


        firebaseAuth.createUserWithEmailAndPassword(pubgname, mobile_no)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        CompleteEntry completeEntry = new CompleteEntry(pubgname, mobile_no, type);
                        classic_duo_join.child(pubgname).setValue(completeEntry)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            progressDialog.show();
                                            notification();
                                            Toast.makeText(DuoActivity.this, "Player Join Successfully", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void prizePoolMatchno2() {
        TextView winner, matchno, first, second, third, four;
        final BottomSheetDialog dialog = new BottomSheetDialog(DuoActivity.this);
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

        matchno.setText(dmatchno2);
        first.setText(st_duo1);
        second.setText(st_duo2);
        third.setText(st_duo3);
        four.setText(st_duo4);
        winner.setText(dwinner2);
        dialog.show();


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
    private void loadPlayer2() {
        Intent intent = new Intent(DuoActivity.this, DuoLoadPlayer_Activity.class);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbrefrence2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstimage = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext())
                        .load(firstimage)
                        .into(second);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}