package com.example.zwierzaki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ZabHigienActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zab_higien);
        Toast.makeText(this, "Higien", Toast.LENGTH_SHORT).show();
    }
}
