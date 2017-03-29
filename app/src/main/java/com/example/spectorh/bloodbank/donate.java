package com.example.spectorh.bloodbank;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class donate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name,uid,phno,add;
    myDBmanager dbm;
    Spinner spinner;
    String bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        dbm=new myDBmanager(this,null,null,1);
        name=(EditText)findViewById(R.id.editText);
        uid=(EditText)findViewById(R.id.editText2);
        phno=(EditText)findViewById(R.id.editText3);
        add=(EditText)findViewById(R.id.editText4);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)


    public void submitdonate(View v)
    {
        if(     isempty(uid.getText().toString()) ||
                isempty(name.getText().toString()) || isempty(phno.getText().toString()) ||
                isempty(add.getText().toString()) )
        {
            Toast.makeText(getApplicationContext(),"Please Fill All Feilds ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isUID(uid.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Valid UID ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isphno(phno.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Valid Phone Number ", Toast.LENGTH_SHORT).show();
            return;
        }
        bg=spinner.getSelectedItem().toString();


        donors d=new donors(dbm.countDonor(),name.getText().toString(),
                uid.getText().toString(),phno.getText().toString(),add.getText().toString(),bg);
        dbm.addDonor(d);
        dbm.updateBank(bg);
        Toast.makeText(getApplicationContext(),"Successful Donation! Thanks :) ", Toast.LENGTH_LONG).show();
        Intent i=new Intent(this,home.class);
        startActivity(i);

    }
    private boolean isempty(String s)
    {
        return s.length()==0;
    }
    private boolean isUID(String s)   { return s.length()==16; }
    private boolean isphno(String s)   { return s.length()==10; }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        bg=parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(),"Please Enter Valid Blood Group", Toast.LENGTH_SHORT).show();
    }
}
