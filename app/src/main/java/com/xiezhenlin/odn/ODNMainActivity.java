package com.xiezhenlin.odn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.xiezhenlin.odn.dao.ODNDao;
import com.xiezhenlin.odn.domain.NoteDomain;

import java.util.ArrayList;

public class ODNMainActivity extends AppCompatActivity {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";
    private static final String ODN_ABOUT_ACTION = "com.xiezhenlin.odn.about";
    private static final String ODN_SETTINGS_ACTION = "com.xiezhenlin.odn.settings";
    private static final String ODN_ADD_ACTION = "com.xiezhenlin.odn.add";
    private InterstitialAd mInterstitialAd;

    private ODNDao mODNDao;
    private ArrayList<NoteDomain> noteDomains;
    private ListView odnlist;
    private TextView result_list;
    private static  final int REQUEST_CODE_ADD_ODN=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odn_main);
        initUI();
        // get ODN list
        initODNs(this);
        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    private void initUI() {
        odnlist=(ListView)findViewById(R.id.odnlist);
        result_list=(TextView)findViewById(R.id.result_list);
    }

    private void initODNs(Context mContext) {
        mODNDao = new ODNDao(this);
        noteDomains = mODNDao.getODNs();
        StringBuffer result_str=new StringBuffer();
        if(noteDomains.size()!=0){
            for(NoteDomain noteDomain :noteDomains){
            result_str.append("ID:"+noteDomain.getOdn_id()+" "+"Comment:"+noteDomain.getOdn_comment()+" "+"Date:"+noteDomain.getOdn_date()+"\n");
            }
        }
        result_list.setText("Size is:"+String.valueOf(noteDomains.size()+"\n"+result_str.toString()));
        Toast.makeText(this,"Size is:"+String.valueOf(noteDomains.size()+"\n"+result_str.toString()),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_ADD_ODN&&resultCode==1010){
            initODNs(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(ODN_SETTINGS_ACTION);
            startActivity(intent);
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(ODN_ABOUT_ACTION);
            startActivity(intent);
        } else if (id == R.id.action_odn_add) {
            Intent intent = new Intent(ODN_ADD_ACTION);
            startActivityForResult(intent, REQUEST_CODE_ADD_ODN);
        } else if (id == R.id.action_test) {
            showInterstitial();
        }

        return super.onOptionsItemSelected(item);
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
                // Proceed to the next level.
                goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            Toast.makeText(this, "Ad loaded", Toast.LENGTH_SHORT).show();
            mInterstitialAd.show();
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
