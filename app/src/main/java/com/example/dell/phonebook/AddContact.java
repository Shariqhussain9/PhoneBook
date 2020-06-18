package com.example.dell.phonebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {
    EditText t1,t2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        t1=findViewById(R.id.ad_et1);
        t2=findViewById(R.id.ad_et2);
        b1=findViewById(R.id.ad_b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                SQLiteDatabase db=openOrCreateDatabase("PB",MODE_PRIVATE,null);
                db.execSQL("create table if not exists PB(name varchar,pno varchar)");
                db.execSQL("insert into PB values('"+t1.getText().toString()+"','"+t2.getText().toString()+"')");
                db.close();
                    Toast.makeText(AddContact.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    t1.setText("");
                    t2.setText("");
                }catch(Exception e)
                {
                    Toast.makeText(AddContact.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
