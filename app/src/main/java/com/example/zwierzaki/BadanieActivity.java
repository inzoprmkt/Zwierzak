package com.example.zwierzaki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BadanieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badanie);
        Toast.makeText(this, "badanie", Toast.LENGTH_SHORT).show();
    }
}
