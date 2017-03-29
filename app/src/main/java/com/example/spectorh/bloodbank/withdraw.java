package com.example.spectorh.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class withdraw extends AppCompatActivity {

    EditText pin, name,phno,add;
    myDBmanager dbm;
    Spinner spinner;
    String bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        name=(EditText)findViewById(R.id.editText);
        phno=(EditText)findViewById(R.id.editText3);
        add=(EditText)findViewById(R.id.editText4);
        pin=(EditText)findViewById(R.id.editText5);
        spinner = (Spinner) findViewById(R.id.spinner);
        dbm=new myDBmanager(this,null,null,1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void submitdonate(View v)
    {
        if(     isempty(name.getText().toString()) || isempty(phno.getText().toString()) ||
                isempty(add.getText().toString()) ||  isempty(pin.getText().toString())  )
        {
            Toast.makeText(getApplicationContext(),"Please Fill All Feilds ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isphno(phno.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Valid Phone Number ", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i=getIntent();
        int p=i.getIntExtra("pints",3);

        int r=Integer.valueOf(pin.getText().toString());
        if(r>p || r==0)
        {
            Toast.makeText(getApplicationContext(),"Please Enter Valid Value of Pints ", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            bg=spinner.getSelectedItem().toString();
            if(dbm.isBloodAvailable(bg,r)>=r)
            {
                String uid=i.getStringExtra("uid");
                recipient rec=new recipient(dbm.countR(),name.getText().toString(),uid,phno.getText().toString(),
                        add.getText().toString(),bg,r);
                dbm.addR(rec);
                dbm.updateBank(bg,r);
                Toast.makeText(getApplicationContext(),"Blood Successfully Withdrawn ! :) ", Toast.LENGTH_LONG).show();
                Intent in=new Intent(this,home.class);
                startActivity(in);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Sorry Enough Blood Not Available ", Toast.LENGTH_LONG).show();
            }

        }
    }
    private boolean isempty(String s)
    {
         return s.length()==0;
    }
    private boolean isphno(String s)   { return s.length()==10; }
}
