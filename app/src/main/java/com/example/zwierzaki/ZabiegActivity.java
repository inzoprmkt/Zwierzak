package com.example.zwierzaki;

import android.content.Intent;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ZabiegActivity extends AppCompatActivity {

    TextView data;
    TextView opis;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button btnUsun;
    String id;
    String nrmetr;
    private CollectionReference kolekcja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zabieg);
        btnUsun=findViewById(R.id.buttonUsunzabieg);
        if (getIntent().hasExtra("selected_wizyta")) {
             id = getIntent().getStringExtra("selected_wizyta");
            kolekcja = db.collection("Wizyta");
            data = findViewById(R.id.textViewData);
            opis = findViewById(R.id.textViewOpis);
            kolekcja.document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String date = documentSnapshot.get("date").toString();
                    nrmetr=documentSnapshot.get("numer_metryki").toString();
                   // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    //LocalDate date = LocalDate.parse(string, formatter);
                    data.setText(date.toString());
                    opis.setText(documentSnapshot.get("opis").toString());
                }
            });

        }
        btnUsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kolekcja.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ZabiegActivity.this, "Usunięto pomyślnie", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent=new Intent(ZabiegActivity.this,WyswietlHistorie.class);
                        intent.putExtra("selected_zwierze",nrmetr);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ZabiegActivity.this,WyswietlHistorie.class);
        intent.putExtra("selected_zwierze",nrmetr);
        startActivity(intent);
    }
}
