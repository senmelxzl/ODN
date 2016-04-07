package com.xiezhenlin.odn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.xiezhenlin.odn.dao.ODNDao;
import com.xiezhenlin.odn.domain.NoteDomain;
import com.xiezhenlin.odn.utils.ODNDateTools;

public class ODNADDActivity extends AppCompatActivity {
    private static final String TAG="ODNADDActivity";
    private static final int ADD_ODN_RESULT_CODE=1010;
    private EditText odnTitle;
    private ODNDao mODNDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odn_add);
        mODNDao=new ODNDao(this);
        InitAddODN_UI();
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
            if(!odnTitle.getText().toString().equals("")) {
                odnSubmit();
            }else {
                Toast.makeText(this,R.string.add_odn_none,Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * submit odn content to database
     */
    private void odnSubmit() {
        NoteDomain submitODNDomain=new NoteDomain();
        submitODNDomain.setOdn_id(1);
        submitODNDomain.setOdn_comment(odnTitle.getText().toString());
        submitODNDomain.setOdn_date(ODNDateTools.getDate());
        if(mODNDao.addODN(submitODNDomain)){
            Toast.makeText(this,R.string.odn_add_successed,Toast.LENGTH_LONG).show();
            setResult(ADD_ODN_RESULT_CODE);
            finish();
        }else{
            Toast.makeText(this,R.string.odn_add_failed,Toast.LENGTH_LONG).show();
        }
    }

    /**
     * init layout view
     */
    private void InitAddODN_UI() {
        odnTitle=(EditText)findViewById(R.id.odn_context);
    }
}
