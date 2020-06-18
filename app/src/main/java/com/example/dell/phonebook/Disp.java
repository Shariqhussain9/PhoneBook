package com.example.dell.phonebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Disp extends AppCompatActivity {
    LinearLayout l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp);
        l1 = findViewById(R.id.l1);
        try {
            SQLiteDatabase db = openOrCreateDatabase("PB", MODE_PRIVATE, null);
            Cursor c = db.rawQuery("select * from PB", null);
            if (c.moveToFirst()) {
                do {
                    TextView tv = new TextView(Disp.this);
                    tv.setText(c.getString(0) + "\t \t \t" + c.getString(1));
                    l1.addView(tv);
                    tv.setTextSize(28);
                } while (c.moveToNext());
            } else
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
