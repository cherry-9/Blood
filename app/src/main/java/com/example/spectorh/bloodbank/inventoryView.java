package com.example.spectorh.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class inventoryView extends AppCompatActivity {

    TextView bg,pint,head;
    myDBmanager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_view);
        bg=(TextView)findViewById(R.id.textView7);
        db=new myDBmanager(this,null,null,1);
        pint=(TextView)findViewById(R.id.textView6);
        head=(TextView)findViewById(R.id.textView4);
        head.setText("INVENTORY");

        bg.setText(db.BGToString());
        pint.setText(db.PintToString());
    }
    public void home(View v)
    {
        Intent i=new Intent(this,home.class);
        startActivity(i);
    }

}
