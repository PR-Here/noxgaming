package com.pankajranag.rana_gaming.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.pankajranag.rana_gaming.R;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PiP_Activity extends AppCompatActivity  implements RewardedVideoAdListener {

    RewardedVideoAd rewardedVideoAd;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi_p_);

// Show Dialog Code
        dialog = new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Example Message")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }
            }
        });

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PiP_Activity.super.onBackPressed();
            }
        });

        MobileAds.initialize(this, "ca-app-pub-5719685189848908~1107059425");
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
   }
    public void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd(getString(R.string.Ad_mob_unit_id),
                new AdRequest.Builder().build());
    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
        dialog.dismiss();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    protected void onPause() {
        rewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        rewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
