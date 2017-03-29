package com.example.spectorh.bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class myDBmanager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="BLOODBANK.db";

    public static final String TABLE_DONOR="donor";
    public static final String COLUMN_SNO="sno";
    public static final String COLUMN_UID="uid";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_PHNO="phno";
    public static final String COLUMN_ADD="addR";
    public static final String COLUMN_DATE="date";
    public static final String COLUMN_BG="bg";



    public static final String TABLE_RECIPIENTS="recipients";
    public static final String COLUMN_PINT="pint";


    public static final String TABLE_BANK="bank";

    public myDBmanager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String q2="CREATE TABLE "+TABLE_BANK+" ( "+ COLUMN_SNO +" INTEGER ,"
                + COLUMN_BG + " TEXT,"
                + COLUMN_PINT + " INTEGER,"
                +"PRIMARY KEY ("+COLUMN_SNO+","+COLUMN_BG+"));";

        db.execSQL(q2);
        db.execSQL("INSERT INTO "+TABLE_BANK + " VALUES ( 1 , 'O' , 0 );");
        db.execSQL("INSERT INTO "+TABLE_BANK + " VALUES ( 2 , 'A' , 0 );");
        db.execSQL("INSERT INTO "+TABLE_BANK + " VALUES ( 3 , 'B' , 0 );");
        db.execSQL("INSERT INTO "+TABLE_BANK + " VALUES ( 4 , 'AB' , 0 );");


        String q="CREATE TABLE "+TABLE_DONOR+" ( "+ COLUMN_SNO +" INTEGER ,"
                + COLUMN_NAME +" TEXT,"
                + COLUMN_UID + " INTEGER,"
                + COLUMN_PHNO +" INTEGER,"
                + COLUMN_ADD +" TEXT,"
                + COLUMN_BG + " TEXT,"
                + "FOREIGN KEY ("+ COLUMN_BG +") REFERENCES "+ TABLE_BANK + "("+COLUMN_BG+"),"
                +"PRIMARY KEY ("+COLUMN_UID+","+COLUMN_SNO+")"
                +");";
        db.execSQL(q);

        String q1="CREATE TABLE "+TABLE_RECIPIENTS+" ( "+ COLUMN_SNO +" INTEGER ,"
                + COLUMN_NAME +" TEXT,"
                + COLUMN_UID + " INTEGER,"
                + COLUMN_PHNO +" INTEGER,"
                + COLUMN_ADD +" TEXT,"
                + COLUMN_BG + " TEXT,"
                + COLUMN_PINT + " INTEGER,"
                + "FOREIGN KEY ("+ COLUMN_BG +" ) REFERENCES "+ TABLE_BANK + "("+COLUMN_BG+"),"
                + "PRIMARY KEY ("+ COLUMN_UID +","+COLUMN_SNO+")"
                +");";
        db.execSQL(q1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BANK+ " ;");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_RECIPIENTS+ " ;");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DONOR+ " ;");
        onCreate(db);
    }



    public void addDonor(donors d)
    {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SNO,d.get_sno());
        cv.put(COLUMN_NAME,d.get_name());
        cv.put(COLUMN_UID,d.get_uid());
        cv.put(COLUMN_PHNO,d.get_phno());
        cv.put(COLUMN_ADD,d.get_add());
        cv.put(COLUMN_BG,d.get_bg());

        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_DONOR , null, cv);
        db.close();

    }
    public int countDonor()
    {
        String q="SELECT * FROM "+TABLE_DONOR ;
        SQLiteDatabase db =getReadableDatabase();
        Cursor c=db.rawQuery(q,null);

        int cnt=c.getCount();
        c.close();
        db.close();
        return cnt;
    }
    public int countR()
    {
        String q="SELECT * FROM "+TABLE_RECIPIENTS ;
        SQLiteDatabase db =getReadableDatabase();
        Cursor c=db.rawQuery(q,null);

        int cnt=c.getCount();
        c.close();
        db.close();
        return cnt;
    }

    public String BGToString()
    {
        String output="BLOOD GROUP\n\n\n\t\t\t";
        String q="SELECT * FROM "+TABLE_BANK +" WHERE 1 ";

        SQLiteDatabase db =getReadableDatabase();
        Cursor c=db.rawQuery(q,null);
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_BG))!=null)
            {
                output+=c.getString(c.getColumnIndex(COLUMN_BG)) ;
                output+=" ";
                output+=" \n\n\t\t\t";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return output;
    }

    public String PintToString()
    {
        String output="\tPINTS\n\n\n\t\t";
        String q="SELECT * FROM "+TABLE_BANK +" WHERE 1 ";

        SQLiteDatabase db =getReadableDatabase();
        Cursor c=db.rawQuery(q,null);
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_PINT))!=null)
            {
                output+=c.getString(c.getColumnIndex(COLUMN_PINT)) ;
                output+=" ";
                output+=" \n\n\t\t";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return output;
    }

    public void updateBank(String bg) {
        SQLiteDatabase db =getWritableDatabase();
        String pint=getPint(bg);
        int p=Integer.parseInt(pint)+1;
        String newPint=Integer.toString(p);
        String s="UPDATE "+TABLE_BANK+" SET "+COLUMN_PINT+"= "+newPint+" WHERE "+COLUMN_BG+"=\"" + bg + "\";" ;
        db.execSQL(s);
        db.close();
    }
    public void updateBank(String bg,int by) {
        SQLiteDatabase db =getWritableDatabase();
        String pint=getPint(bg);
        int p=Integer.parseInt(pint)-by;
        if(p<0){by=-1*p;p=0;}else by=0;
        String newPint=Integer.toString(p);
        String s="UPDATE "+TABLE_BANK+" SET "+COLUMN_PINT+"= "+newPint+" WHERE "+COLUMN_BG+"=\"" + bg + "\";" ;
        db.execSQL(s);
        if(by==0) {
            db.close();
            return;
        }
        pint=getPint("O");
        p=Integer.parseInt(pint)-by;
        if(p<0){by=-1*p;p=0;}else by=0;
        newPint=Integer.toString(p);
        s="UPDATE "+TABLE_BANK+" SET "+COLUMN_PINT+"= "+newPint+" WHERE "+COLUMN_BG+"='O';" ;
        db.execSQL(s);
        if(by==0)
        {
            db.close();
            return;
        }
        pint=getPint("A");
        p=Integer.parseInt(pint)-by;
        if(p<0){by=-1*p;p=0;}else by=0;
        newPint=Integer.toString(p);
        s="UPDATE "+TABLE_BANK+" SET "+COLUMN_PINT+"= "+newPint+" WHERE "+COLUMN_BG+"='A';" ;
        db.execSQL(s);
        if(by==0)
        {
            db.close();
            return;
        }
        pint=getPint("B");
        p=Integer.parseInt(pint)-by;
        if(p<0){by=-1*p;p=0;}else by=0;
        newPint=Integer.toString(p);
        s="UPDATE "+TABLE_BANK+" SET "+COLUMN_PINT+"= "+newPint+" WHERE "+COLUMN_BG+"='B';" ;
        db.execSQL(s);
        if(by==0)
        {
            db.close();
            return;
        }
    }

    private String getPint(String bg) {
        SQLiteDatabase db =getReadableDatabase();
        String s="";
        String q="SELECT * FROM "+TABLE_BANK+" WHERE "+COLUMN_BG+"=\"" + bg.trim() + "\";" ;
        Cursor c=db.rawQuery(q,null);
        c.moveToFirst();
        s=c.getString(c.getColumnIndex(COLUMN_PINT));
        //db.close();
        c.close();
        return s;
    }

    public boolean checkUID(String s) {
        SQLiteDatabase db =getReadableDatabase();
        String q="SELECT * FROM "+TABLE_DONOR+" WHERE "+COLUMN_UID+"=\"" + s.trim() + "\";" ;
        Cursor c=db.rawQuery(q,null);
        if(c.getCount()<=0)
        {   db.close();
            c.close();
            return false;}
        else
        {   db.close();
            c.close();
            return true;}

    }

    public int isBloodAvailable(String bg,int r) {
        SQLiteDatabase db =getReadableDatabase();
        int find=-1;
        if(bg.trim().equals("O"))
        {
            String q="SELECT SUM( "+COLUMN_PINT+" ) FROM "+TABLE_BANK+" WHERE "+COLUMN_BG+"=\"" + bg.trim() + "\";" ;
            Cursor c=db.rawQuery(q,null);
            c.moveToFirst();
            find=c.getInt(0);
            c.close();
            db.close();
            return find;

        }
        else if(bg.trim().equals("AB"))
        {
            String q="SELECT SUM( "+COLUMN_PINT+" ) FROM "+TABLE_BANK+ " WHERE 1;" ;
            Cursor c=db.rawQuery(q,null);
            c.moveToFirst();
            find=c.getInt(0);
            c.close();
            db.close();
            return find;

        }
        else {
            String q = "SELECT SUM( " + COLUMN_PINT + " ) FROM " + TABLE_BANK + " WHERE "+COLUMN_BG+ "=\"" + bg.trim()+ "\" OR "
                    +COLUMN_BG+" = \"O\";";
            Cursor c = db.rawQuery(q, null);
            c.moveToFirst();
            find = c.getInt(0);
            c.close();
            db.close();
            return find;
        }

    }
    public void addR(recipient d)
    {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SNO,d.get_sno());
        cv.put(COLUMN_NAME,d.get_name());
        cv.put(COLUMN_UID,d.get_uid());
        cv.put(COLUMN_PHNO,d.get_phno());
        cv.put(COLUMN_ADD,d.get_add());
        cv.put(COLUMN_BG,d.get_bg());
        cv.put(COLUMN_PINT,d.get_pint());

        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_RECIPIENTS , null, cv);
        db.close();

    }
}
