package com.example.zwierzaki;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
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

public class ZabHigienActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference kolekcja = db.collection("Wizyta");
    private TableLayout layout;
    Button btnUsun;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zab_higien);
        layout = findViewById(R.id.tableLayout);
        btnUsun=findViewById(R.id.buttonUsunzabhigien);
        final TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT, 30);

        final String[] zabiegi = {"c_uszu", "c_zebow", "kleszcz", "strzyzenie", "usuw_kamienia", "inne"};
        if (getIntent().hasExtra("selected_wizyta")) {
            id = getIntent().getStringExtra("selected_wizyta");
            kolekcja.document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint("WrongConstant")
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String string = documentSnapshot.get("date").toString();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(string, formatter);

                    String tekstpoczatkowy = "W dniu ";           //+date.toString() +"\nzostały wykonane następujące \nzabiegi higieniczne:";
                    TextView t = new TextView(ZabHigienActivity.this);
                    t.setText(tekstpoczatkowy);
                    t.setGravity(Gravity.CENTER);
                    TableRow r1 = new TableRow(ZabHigienActivity.this);
                    r1.setGravity(Gravity.CENTER_HORIZONTAL);
                    r1.addView(t);
                    layout.addView(r1, rowLp);

                    tekstpoczatkowy = date.toString();// +"\nzostały wykonane następujące \nzabiegi higieniczne:";
                    t = new TextView(ZabHigienActivity.this);
                    t.setGravity(Gravity.CENTER);
                    t.setTypeface(null, Typeface.BOLD);
                    t.setText(tekstpoczatkowy);
                    r1 = new TableRow(ZabHigienActivity.this);
                    r1.setGravity(Gravity.CENTER_HORIZONTAL);
                    r1.addView(t);
                    layout.addView(r1, rowLp);

                    tekstpoczatkowy = "zostały wykonane następujące";// \nzabiegi higieniczne:";
                    t = new TextView(ZabHigienActivity.this);
                    t.setGravity(Gravity.CENTER);
                    t.setText(tekstpoczatkowy);
                    r1 = new TableRow(ZabHigienActivity.this);
                    r1.setGravity(Gravity.CENTER_HORIZONTAL);
                    r1.addView(t);
                    layout.addView(r1, rowLp);

                    tekstpoczatkowy = "zabiegi higieniczne:";
                    t = new TextView(ZabHigienActivity.this);
                    t.setGravity(Gravity.CENTER);
                    t.setText(tekstpoczatkowy);
                    r1 = new TableRow(ZabHigienActivity.this);
                    r1.setGravity(Gravity.CENTER_HORIZONTAL);
                    r1.addView(t);
                    layout.addView(r1, rowLp);
                    for (String s : zabiegi) {
                        if (documentSnapshot.get(s) != null) {
                            if (documentSnapshot.get(s).toString().equals("true")) {
                                TextView textView = null;

                                switch (s) {
                                    case "c_uszu":
                                        textView = new TextView(ZabHigienActivity.this);
                                        textView.setText("Czyszczenie uszu");
                                        break;
                                    case "c_zebow":
                                        textView = new TextView(ZabHigienActivity.this);
                                        textView.setText("Czyszczenie zębów");
                                        break;
                                    case "kleszcz":
                                        textView = new TextView(ZabHigienActivity.this);
                                        textView.setText("Usuwanie kleszcza");
                                        break;
                                    case "strzyzenie":
                                        textView = new TextView(ZabHigienActivity.this);
                                        textView.setText("Strzyżenie");
                                        break;
                                    case "usuw_kamienia":
                                        textView = new TextView(ZabHigienActivity.this);
                                        textView.setText("Usuwanie kamienia nazębnego");
                                        break;
                                    case "inne":
                                        textView = new TextView(ZabHigienActivity.this);
                                        textView.setText(documentSnapshot.get("inne").toString());
                                        break;
                                }
                                textView.setHeight(70);
                                textView.setTextSize(20);
                                textView.setGravity(Gravity.CENTER);
                                TableRow r = new TableRow(ZabHigienActivity.this);
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
                        Toast.makeText(ZabHigienActivity.this, "Usunięto pomyślnie", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}
