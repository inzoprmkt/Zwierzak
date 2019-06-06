package com.example.zwierzaki;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;




public class BadanieActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference kolekcja = db.collection("Wizyta");
    private TableLayout layout;
    Button btnUsun;
    String id;
    String nrmetr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badanie);
        layout = findViewById(R.id.TableLayoutBadanie);
        btnUsun=findViewById(R.id.buttonUsunbadanie);
        final TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT, 30);

        if (getIntent().hasExtra("selected_wizyta")) {
             id = getIntent().getStringExtra("selected_wizyta");
            kolekcja.document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint("WrongConstant")
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String date = documentSnapshot.get("date").toString();
                    nrmetr=documentSnapshot.get("numer_metryki").toString();
                    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    //LocalDate date = LocalDate.parse(string, formatter);
                    final String[] badania = {"biochem", "ekg", "krew", "mocz", "morfologia", "rtg", "usg", "inne"};
                    String tekstpoczatkowy = "W dniu ";
                    TextView t = new TextView(BadanieActivity.this);
                    t.setText(tekstpoczatkowy);
                    t.setGravity(Gravity.CENTER);
                    TableRow r1 = new TableRow(BadanieActivity.this);
                    r1.setGravity(Gravity.CENTER_HORIZONTAL);
                    r1.addView(t);
                    layout.addView(r1, rowLp);

                    tekstpoczatkowy = date.toString();
                    t = new TextView(BadanieActivity.this);
                    t.setGravity(Gravity.CENTER);
                    t.setTypeface(null, Typeface.BOLD);
                    t.setText(tekstpoczatkowy);
                    r1 = new TableRow(BadanieActivity.this);
                    r1.setGravity(Gravity.CENTER_HORIZONTAL);
                    r1.addView(t);
                    layout.addView(r1, rowLp);

                    tekstpoczatkowy = "zostały wykonane następujące";
                    t = new TextView(BadanieActivity.this);
                    t.setGravity(Gravity.CENTER);
                    t.setText(tekstpoczatkowy);
                    r1 = new TableRow(BadanieActivity.this);
                    r1.setGravity(Gravity.CENTER_HORIZONTAL);
                    r1.addView(t);
                    layout.addView(r1, rowLp);

                    tekstpoczatkowy = "badania:";
                    t = new TextView(BadanieActivity.this);
                    t.setGravity(Gravity.CENTER);
                    t.setText(tekstpoczatkowy);
                    r1 = new TableRow(BadanieActivity.this);
                    r1.setGravity(Gravity.CENTER_HORIZONTAL);
                    r1.addView(t);
                    layout.addView(r1, rowLp);
                    for (String s : badania) {
                        if (documentSnapshot.get(s) != null) {
                            if (documentSnapshot.get(s).toString().equals("true")) {
                                TextView textView = null;


                                switch (s) {
                                    case "biochem":
                                        textView = new TextView(BadanieActivity.this);
                                        textView.setText("Badania biochemiczne");
                                        break;
                                    case "ekg":
                                        textView = new TextView(BadanieActivity.this);
                                        textView.setText("EKG");
                                        break;
                                    case "krew":
                                        textView = new TextView(BadanieActivity.this);
                                        textView.setText("Rozmaz krwi");
                                        break;
                                    case "mocz":
                                        textView = new TextView(BadanieActivity.this);
                                        textView.setText("Badanie moczu");
                                        break;
                                    case "morfologia":
                                        textView = new TextView(BadanieActivity.this);
                                        textView.setText("Morfologia");
                                        break;
                                    case "rtg":
                                        textView = new TextView(BadanieActivity.this);
                                        textView.setText("RTG");
                                        break;

                                    case "usg":
                                        textView = new TextView(BadanieActivity.this);
                                        textView.setText("USG");
                                        break;
                                    case "inne":
                                        textView = new TextView(BadanieActivity.this);
                                        textView.setText(documentSnapshot.get("inne").toString());
                                        break;
                                }
                                textView.setHeight(70);
                                textView.setTextSize(20);
                                textView.setGravity(Gravity.CENTER);
                                TableRow r = new TableRow(BadanieActivity.this);
                                r.setGravity(Gravity.CENTER_HORIZONTAL);
                                r.addView(textView);
                                r.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.FILL_PARENT));

                                layout.addView(r, rowLp);
                            }
                        }


                    }
                }
            });
        }
        btnUsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kolekcja.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(BadanieActivity.this, "Usunięto pomyślnie", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent=new Intent(BadanieActivity.this,WyswietlHistorie.class);
                        intent.putExtra("selected_zwierze",nrmetr);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}

