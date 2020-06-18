package com.example.dell.phonebook;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SMS extends AppCompatActivity {
    EditText t1;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        t1=findViewById(R.id.sms_et1);
        b1=findViewById(R.id.sms_b1);
        b2=findViewById(R.id.sms_b2);
        final Intent in=getIntent();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage(in.getStringExtra("temp1").toString(), null, t1.getText().toString(), null, null);
                    Toast.makeText(SMS.this, "Message Delivered", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(SMS.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                in.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Now");
                startActivityForResult(in,0);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ArrayList<String> al=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        for(String str:al){
            t1.setText(t1.getText()+""+str);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
