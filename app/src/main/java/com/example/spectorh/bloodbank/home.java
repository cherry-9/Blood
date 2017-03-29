package com.example.spectorh.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void donate(View v)
    {
        Intent i=new Intent(this, donate.class);
        //Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_LONG).show();
        startActivity(i);
    }
    public void viewall(View v)
    {
        Intent i=new Intent(this,inventoryView.class);
        startActivity(i);
    }
    public void withdraw(View v)
    {
        Intent i=new Intent(this, withdrawTC.class);
        //Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_LONG).show();
        startActivity(i);
    }

}
