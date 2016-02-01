package com.xiezhenlin.odn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.xiezhenlin.odn.domain.NoteDomain;

public class ODNADDActivity extends AppCompatActivity {

    private EditText odnTitle;

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
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * submit odn content to database
     */
    private void odnSubmit() {
        NoteDomain submitODNDomain=new NoteDomain();
        submitODNDomain.setOdn_id(1);
        submitODNDomain.setOdn_tile(odnTitle.getText().toString());
        submitODNDomain.setOdn_date(152);
    }

    /**
     * init layout view
     */
    private void initlayout() {
        odnTitle=(EditText)findViewById(R.id.odn_context);
    }
}
