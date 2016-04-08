package com.xiezhenlin.odn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by xiezhenlin on 2016/4/8.
 */
public class ODNWelcomeActivity extends Activity {

    private InterstitialAd mInterstitialAd;
    private static Handler mHandler;
    private static final int TIMER_EVENT_TICK = 1000;
    private boolean AdIsLoaded=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odn_welcome);
        //Load AD
        loadAd();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!AdIsLoaded){
            mHandler.sendEmptyMessageDelayed(TIMER_EVENT_TICK, 5000);
        }else{
            mHandler.removeMessages(TIMER_EVENT_TICK);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(TIMER_EVENT_TICK);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeMessages(TIMER_EVENT_TICK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(TIMER_EVENT_TICK);
    }
    private boolean loadAd() {
        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TIMER_EVENT_TICK:
                        showInterstitial();
                        // send new TIMER_EVENT_TICK message
                        sendEmptyMessageDelayed(TIMER_EVENT_TICK, 5000);
                        break;
                }
            }
        };
        return AdIsLoaded;
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level
                mHandler.removeMessages(TIMER_EVENT_TICK);
                finish();
                startActivity(new Intent("com.xiezhenlin.odn.main"));
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            Toast.makeText(this, "Ad loaded", Toast.LENGTH_SHORT).show();
            mInterstitialAd.show();
            AdIsLoaded=true;
            mHandler.removeMessages(TIMER_EVENT_TICK);
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }
}
