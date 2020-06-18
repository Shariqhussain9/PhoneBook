package com.example.dell.phonebook;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    EditText t1;
    TextView temp, tv;
    LinearLayout ll;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        t1 = findViewById(R.id.m_et1);
        b1 = findViewById(R.id.m_b1);
        ll = findViewById(R.id.ll);
        t1.addTextChangedListener(new TextWatcher() {
            SQLiteDatabase db = openOrCreateDatabase("PB", MODE_PRIVATE, null);

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ll.removeAllViews();
                Cursor c = db.rawQuery("select * from PB where name like '" + t1.getText().toString() + "%'", null);
                if (c.moveToFirst()) {
                    do {
                        tv = new TextView(MainActivity.this);
                        tv.setText("" + c.getString(0));
                        ll.addView(tv);
                        tv.setTextSize(28);
                        registerForContextMenu(tv);
                    } while (c.moveToNext());
                } else
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ll.removeAllViews();
                    SQLiteDatabase db = openOrCreateDatabase("PB", MODE_PRIVATE, null);
                    Cursor c = db.rawQuery("select name from PB where name like '" + t1.getText().toString() + "%'", null);
                    if (c.moveToFirst()) {
                        do {
                            TextView tv = new TextView(MainActivity.this);
                            tv.setText("" + c.getString(0));
                            ll.addView(tv);
                            tv.setTextSize(28);
                            registerForContextMenu(tv);
                        } while (c.moveToNext());
                    } else
                        Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mm_it1) {
            Intent in = new Intent(MainActivity.this, AddContact.class);
            startActivity(in);

        }
        if (item.getItemId() == R.id.mm_it2) {
            Intent in = new Intent(MainActivity.this, Disp.class);
            startActivity(in);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
        temp = (TextView) v;

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cm_it1) {
            try {
                Intent in = new Intent(MainActivity.this, view.class);
                in.putExtra("temp1", temp.getText().toString());
                startActivity(in);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        if (item.getItemId() == R.id.cm_it2) {
            Intent in = new Intent(MainActivity.this, Edit.class);
            in.putExtra("temp2", temp.getText().toString());
            startActivity(in);
        }
        if (item.getItemId() == R.id.cm_it3) {
            SQLiteDatabase db = openOrCreateDatabase("PB", MODE_PRIVATE, null);
            db.execSQL("delete from PB where name like '" + temp.getText() + "'");
        }
        if (item.getItemId() == R.id.cm_it5) {
            try {
                SQLiteDatabase db = openOrCreateDatabase("PB", MODE_PRIVATE, null);
                Cursor c = db.rawQuery("select pno from PB where name like '" + temp.getText().toString() + "'", null);
                if (c.moveToFirst()) {

                    Intent in = new Intent(Intent.ACTION_CALL);
                    in.setData(Uri.parse("tel:" + c.getString(0)));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                       // return TODO;
                    }
                    startActivity(in);
                }
            } catch(Exception e){
                Toast.makeText(this, "Call not make bcz"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        if(item.getItemId()==R.id.cm_it4){

            try {
                SQLiteDatabase db = openOrCreateDatabase("PB", MODE_PRIVATE, null);
                Cursor c = db.rawQuery("select pno from PB where name like '" + temp.getText().toString() + "'", null);
                if (c.moveToFirst()) {
                    Intent in=new Intent(this,SMS.class);
                    in.putExtra("temp1", c.getString(0));
                    startActivity(in);

                }

            }
            catch(Exception e) {
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }


        return super.onContextItemSelected(item);
    }

}
