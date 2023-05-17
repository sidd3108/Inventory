package com.example.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class profile extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView = findViewById(R.id.gmail_profile_text);

        String gmail = getIntent().getStringExtra("ns");
//        String gmail1 = getIntent().getStringExtra("ns1");
        textView.setText(gmail);
//        textView.setText(gmail1);


    }
}