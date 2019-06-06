package com.example.zwierzaki;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoriaWizyt extends AppCompatActivity {
    private Spinner spiner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button wyswietlWizyty;
    private String currentUI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_wizyt);
        spiner = findViewById(R.id.spinnerZwierzeta);
        wyswietlWizyty = findViewById(R.id.buttonWyswietl);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUI = currentUser.getUid();

        final List<String> subjects = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(adapter);
        db.collection("Zwierzeta").whereEqualTo("uid", currentUI).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : queryDocumentSnapshots) {
                    String wiersz = document.getString("imieZwierzecia") + "   " + document.getString("nrMetryki");
                    subjects.add(wiersz);
                }
                adapter.notifyDataSetChanged();
            }
        });

        wyswietlWizyty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spiner.getSelectedItem() != null) {
                    Intent i = new Intent(HistoriaWizyt.this, WyswietlHistorie.class);
                    i.putExtra("selected_spinner", spiner.getSelectedItem().toString());
                    startActivity(i);
                }
            }
        });
    }

   /* @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
    @Override
    public void onResume() {
        super.onResume();
        recreate();
    }*/

    /*private void showVisit() {

        String tekst = spiner.getSelectedItem().toString();
        String[] czesci = tekst.split(" ");
        final String piesnr = czesci[czesci.length - 1];


        db.collection("Wizyta").whereEqualTo("numer_metryki", piesnr).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : queryDocumentSnapshots) {
                    TextView a;
                    TableRow row;

                    a=new TextView(getApplicationContext());
                    a.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    String wiersz = document.getString("typ") + "   " + document.get("date").toString();
                    a.setText(wiersz);
                    Toast.makeText(HistoriaWizyt.this, wiersz, Toast.LENGTH_SHORT).show();
                    row =new TableRow(getApplicationContext());
                    row.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    row.addView(a);
                    tab.addView(row);
                }

            }
        });
    }*/


}
