package com.xiezhenlin.odn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiezhenlin.odn.domain.NoteDomain;
import com.xiezhenlin.odn.utils.ODNDBH;
import com.xiezhenlin.odn.utils.ODNDateTools;

public class ODNADDActivity extends AppCompatActivity {

    private EditText odnTitle;
    private ODNDBH mODNDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odn_add);
        initlayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_odn_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_odn_submit) {
            odnSubmit();
            this.finish();
            Toast.makeText(this,R.string.add_odn_done_tip,Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * submit odn content to database
     */
    private void odnSubmit() {
        mODNDBH=new ODNDBH(this);
        NoteDomain submitODNDomain=new NoteDomain();
        submitODNDomain.setOdn_id(1);
        submitODNDomain.setOdn_comment(odnTitle.getText().toString());
        submitODNDomain.setOdn_date(ODNDateTools.getDate());
        mODNDBH.addODN(submitODNDomain);
    }

    /**
     * init layout view
     */
    private void initlayout() {
        odnTitle=(EditText)findViewById(R.id.odn_context);
    }
}
