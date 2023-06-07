package com.example.hw2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThirdActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        
        Bundle extras = getIntent().getExtras();
        TextView text = findViewById(R.id.contact0);
        text.setText(extras.getString("data"));
    }
}
