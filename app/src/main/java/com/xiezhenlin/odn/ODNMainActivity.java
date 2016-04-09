package com.xiezhenlin.odn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.xiezhenlin.odn.dao.ODNDao;
import com.xiezhenlin.odn.domain.NoteDomain;

import java.util.ArrayList;

public class ODNMainActivity extends AppCompatActivity {
    private static final String TAG="ODNMainActivity";
    private static final String ODN_ABOUT_ACTION = "com.xiezhenlin.odn.about";
    private static final String ODN_SETTINGS_ACTION = "com.xiezhenlin.odn.settings";
    private static final String ODN_ADD_ACTION = "com.xiezhenlin.odn.add";

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
        initODNs();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUI() {
        odnlist=(ListView)findViewById(R.id.odnlist);
        result_list=(TextView)findViewById(R.id.result_list);
    }

    private void initODNs() {
        mODNDao = new ODNDao(this);
        noteDomains = mODNDao.getODNs();
        StringBuffer result_str=new StringBuffer();
        if(noteDomains.size()!=0){
            for(NoteDomain noteDomain :noteDomains){
            result_str.append("ID:"+noteDomain.getOdn_id()+" "+"Comment:"+noteDomain.getOdn_comment()+" "+"Date:"+noteDomain.getOdn_date()+"\n");
            }
        }
        result_list.setText("Size is:" + String.valueOf(noteDomains.size() + "\n" + result_str.toString()));
        Log.i(TAG, "Size is:" + String.valueOf(noteDomains.size() + "\n" + result_str.toString()));
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
            initODNs();
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
        }

        return super.onOptionsItemSelected(item);
    }
}
