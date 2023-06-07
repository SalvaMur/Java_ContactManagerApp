package com.example.hw2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.net.Uri;
import android.widget.Toast;

public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void toMain(View view) {
        startActivity(new Intent("com.example.hw2.MainActivity"));
        finish();
    }
    
    public void enterContact(View view) {
        Intent data = new Intent();
        Bundle extras = new Bundle();
        
        EditText name = (EditText) findViewById(R.id.entry0);
        EditText phone = (EditText) findViewById(R.id.entry1);
        EditText email = (EditText) findViewById(R.id.entry2);
        EditText address = (EditText) findViewById(R.id.entry3);
        EditText relation = (EditText) findViewById(R.id.entry4);
        
        extras.putString("name", name.getText().toString());
        extras.putString("phone", phone.getText().toString());
        extras.putString("email", email.getText().toString());
        extras.putString("address", address.getText().toString());
        extras.putString("relation", relation.getText().toString());
        
        data.putExtras(extras);
        
        setResult(RESULT_OK, data);
        finish();
    }
}