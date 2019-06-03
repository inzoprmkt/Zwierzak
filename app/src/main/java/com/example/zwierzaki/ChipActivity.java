package com.example.zwierzaki;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ChipActivity extends AppCompatActivity {

    TextView data;
    Button btnUsun;
    String id;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference kolekcja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip);
        btnUsun=findViewById(R.id.buttonUsunchip);
        if (getIntent().hasExtra("selected_wizyta")) {
            id = getIntent().getStringExtra("selected_wizyta");
            kolekcja = db.collection("Wizyta");
            data = findViewById(R.id.textViewDatawszczep);

            kolekcja.document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String string = documentSnapshot.get("date").toString();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(string, formatter);
                    data.setText(date.toString());

                }
            });

        }
        btnUsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kolekcja.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChipActivity.this, "Usunięto pomyślnie", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}
