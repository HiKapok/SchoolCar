package com.kapok.schoolcar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;


public class FeedBackPage extends ActionBarActivity {
    EditText edit1;
    EditText edit2;
    private final static String TAG = "FeedBackPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back_page);
        SetActivityBackground.set(this);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit1=(EditText)findViewById(R.id.feedback_content_edit);
        edit2=(EditText)findViewById(R.id.feedback_contact_edit);
        Button button=(Button)findViewById(R.id.submit_button);
        button.setOnClickListener(new submitLis());
    }
    class submitLis implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            boolean submit=true;

            String contact=edit2.getText().toString();
            if(submit==true) {
                if (edit1.getText().toString().equals("")) {
                    ToastHelper.makeText(getApplicationContext(), "反馈内容不能为空");
                    submit = false;
                }
            }
            if(submit==true) {
                if (contact.equals("")) {
                    ToastHelper.makeText(getApplicationContext(), "联系方式不能为空");
                    submit = false;
                }
            }
            if(submit==true) {
                if (!((contact.matches("^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$"))||(contact.matches("[0-9]{5,15}")))) {
                    ToastHelper.makeText(getApplicationContext(), "联系方式填写有误");
                    submit = false;
                }
            }

            if(submit==true) {
                Intent data = new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:wangchangan@yeah.net"));
                data.putExtra(Intent.EXTRA_SUBJECT, "校车查询APP的用户反馈");
                data.putExtra(Intent.EXTRA_TEXT, "反馈内容：" + edit1.getText().toString() + "\r\n联系方式：" + edit2.getText().toString()+"\r\n版本号："+getString(R.string.version));
                startActivity(data);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId()+"");
        switch(item.getItemId()) {
            case 0x0102002C:
                FeedBackPage.this.finish();
                break;
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true); android.R.id.home
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        SetActivityBackground.set(this);
    }

}

