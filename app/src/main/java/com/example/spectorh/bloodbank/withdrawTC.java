package com.example.spectorh.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class withdrawTC extends AppCompatActivity {

    EditText et;
    myDBmanager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_tc);
        et=(EditText)findViewById(R.id.editText2);
        db=new myDBmanager(this,null,null,1);
    }
    public void cont(View v)
    {
        if(!isUID(et.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Valid UID ", Toast.LENGTH_SHORT).show();
            return;
        }
        int p;
        if(db.checkUID(et.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"You are a registered member." +
                    "\nYou can withdraw 5 pints of blood. ", Toast.LENGTH_LONG).show();
            p=5;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"You are not a registered member." +
                    "\nYou can withdraw 3 pints of blood. ", Toast.LENGTH_LONG).show();
            p=3;
        }
        Intent i=new Intent(this,withdraw.class);
        i.putExtra("pints",p);
        i.putExtra("uid",et.getText().toString());
        startActivity(i);
    }
    private boolean isUID(String s)   { return s.length()==16; }

}
