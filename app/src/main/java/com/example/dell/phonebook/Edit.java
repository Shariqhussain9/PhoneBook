package com.example.dell.phonebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.*;

public class Edit extends AppCompatActivity {
    EditText et1,et2;
    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent in = getIntent();
        et1=findViewById(R.id.e_t1);
        et2=findViewById(R.id.e_t2);
        bt=findViewById(R.id.e_b1);
        final String st=in.getStringExtra("temp2");


        try {
            SQLiteDatabase db = openOrCreateDatabase("PB", MODE_PRIVATE, null);

            Cursor c=db.rawQuery("select * from PB where name like '"+in.getStringExtra("temp2")+"'",null);

            if(c.moveToFirst()) {
                do {
                    et1.setText(c.getString(0));
                    et2.setText(c.getString(1));

                }while(c.moveToNext());
            }
            else
                Toast.makeText(this, "NO Data Found", Toast.LENGTH_SHORT).show();
        }catch(Exception e)
        {
            Toast.makeText(this, "0"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db= openOrCreateDatabase("PB", MODE_PRIVATE, null);

                db.execSQL("update PB set name='"+et1.getText().toString()+"',pno='"+et2.getText().toString()+"' where name like '"+st+"'");
                Toast.makeText(Edit.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
