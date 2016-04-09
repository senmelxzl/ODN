package com.xiezhenlin.odn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiezhenlin.odn.dao.ODNDao;
import com.xiezhenlin.odn.domain.NoteDomain;
import com.xiezhenlin.odn.utils.ODNTools;

public class ODNADDActivity extends AppCompatActivity {
    private static final String TAG="ODNADDActivity";
    private static final boolean DEBUG=false;
    private static final int ADD_ODN_RESULT_CODE=1010;
    private EditText odnComment;
    private TextView odn_comment_account;
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
            if(!odnComment.getText().toString().equals("")) {
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
        submitODNDomain.setOdn_comment(odnComment.getText().toString());
        submitODNDomain.setOdn_date(ODNTools.getDate());
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
        odnComment=(EditText)findViewById(R.id.odn_context);
        odnComment.addTextChangedListener(new ODNEditChangedListener());
        odn_comment_account=(TextView)findViewById(R.id.odn_comment_account);
    }
    class ODNEditChangedListener implements TextWatcher {
        private CharSequence temp;
        private int editStart;
        private int editEnd;
        private final int charMaxNum = 10;
        private android.support.v7.view.menu.ActionMenuItemView odn_action_MI;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
            odn_action_MI=(android.support.v7.view.menu.ActionMenuItemView)findViewById(R.id.action_odn_submit);
            odn_action_MI.setTitle(getResources().getString(R.string.add_odn_discard_menu));
            odn_comment_account.setVisibility(View.GONE);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(temp.length()>0){
                odn_action_MI=(android.support.v7.view.menu.ActionMenuItemView)findViewById(R.id.action_odn_submit);
                odn_action_MI.setTitle(getResources().getString(R.string.add_odn_done_menu));
                //odn_action_MI.setTitle(R.string.add_odn_done_menu);
                odn_comment_account.setText(String.valueOf(count));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = odnComment.getSelectionStart();
            editEnd = odnComment.getSelectionEnd();
            if (temp.length() > charMaxNum) {
                Toast.makeText(getApplicationContext(), R.string.odn_outof_limit, Toast.LENGTH_LONG).show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                odnComment.setText(s);
                odnComment.setSelection(tempSelection);
            }

        }
    };

}
