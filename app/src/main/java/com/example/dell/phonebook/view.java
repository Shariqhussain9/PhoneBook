package com.example.dell.phonebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.widget.TextView;
import android.widget.Toast;

public class view extends AppCompatActivity {
    TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        t1=findViewById(R.id.v_et1);
        t2=findViewById(R.id.v_et2);
        Intent in=getIntent();

        try {
            SQLiteDatabase db = openOrCreateDatabase("PB", MODE_PRIVATE, null);
            Cursor c = db.rawQuery("select * from PB where name like '"+in.getStringExtra("temp1")+"'",null);
            
           if(c.moveToFirst()) {
                    do {
                        t1.setText(c.getString(0));
                        t2.setText(c.getString(1));
                    }while(c.moveToNext());
           }
           else
               Toast.makeText(this, "Data not acccesed", Toast.LENGTH_SHORT).show();

        }catch(Exception e)
        {
            Toast.makeText(this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
