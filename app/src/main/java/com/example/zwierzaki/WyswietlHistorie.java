package com.example.zwierzaki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WyswietlHistorie  extends AppCompatActivity implements Wizyta_Info_Adapter.OnWizytaListener {

    private static final String TAG = "WyswietlHistorie";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String currentUI = currentUser.getUid();
    private CollectionReference listaWizyt = db.collection("Wizyta");
    private RecyclerView mRecyclerView;
    private ArrayList<Wizyta> mZwierze = new ArrayList<>();
    private Wizyta_Info_Adapter mWizyta_Info_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyswietl_historie);
        mRecyclerView = findViewById(R.id.recycler);

        if (getIntent().hasExtra("selected_spinner")) {
            String ciag = getIntent().getStringExtra("selected_spinner");
            String[] parts = ciag.split(" ");
            String nrMetr = parts[parts.length - 1];

            Toast.makeText(WyswietlHistorie.this, "|"+nrMetr+"|", Toast.LENGTH_SHORT).show();
//.orderBy("date", Query.Direction.DESCENDING)

            listaWizyt.whereEqualTo("numer_metryki", nrMetr).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
                    if (!queryDocumentSnapshots.isEmpty()) {
                        initRecyclerView();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            System.out.println(date); // 2010-01-02
                            System.out.println(documentSnapshot.get("date"));
                            Wizyta nowa = new Wizyta(new Date(documentSnapshot.get("date").toString()), documentSnapshot.get("typ").toString(), documentSnapshot.get("numer_metryki").toString(),documentSnapshot.getId());
                            //Toast.makeText(WyswietlZwierzaki.this, documentSnapshots.get("imieZwierzecia").toString()+" ", Toast.LENGTH_SHORT).show();

                            //zwierze.setNrMetryki(documentSnapshot.get("nrMetryki").toString());
                            // zwierze.setImieZwierzecia(documentSnapshot.get("imieZwierzecia").toString());
                       /* zwierze.setDatUr(documentSnapshots.get("datUr").toString());
                        zwierze.setNrMetrykiMatki(documentSnapshots.get("nrMetrykiMatki").toString());
                        zwierze.setNrMetrykiOjca(documentSnapshots.get("nrMetrykiOjca").toString());
                        zwierze.setPlec(documentSnapshots.get("plec").toString());
                        zwierze.setZdjecie(documentSnapshots.get("zdjecie").toString());*/
                            mZwierze.add(nowa);
                        }
                    }
                }

            });
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
         mWizyta_Info_Adapter = new Wizyta_Info_Adapter(mZwierze, this);
         mRecyclerView.setAdapter(mWizyta_Info_Adapter);
    }

    @Override
    public void onWizytaClick(int position) {
        Log.d(TAG, "onWizytaClick: clicked." + position);
        System.out.println("Dziala");
        Wizyta wizyt =mZwierze.get(position);

        switch (wizyt.getTyp())
        {
            case"Zabieg":
                Intent intent = new Intent(this, ZabiegActivity.class);
                intent.putExtra("selected_wizyta", wizyt.getId());
                startActivity(intent);
            break;
            case"Chip":
                Intent intent1 = new Intent(this, ChipActivity.class);
                intent1.putExtra("selected_wizyta", wizyt.getId());
                startActivity(intent1);
            break;
            case"Zabieg Higieniczny":
                Intent intent2 = new Intent(this, ZabHigienActivity.class);
                intent2.putExtra("selected_wizyta", wizyt.getId());
                startActivity(intent2);
            break;
            case"Szczepienie":
                Intent  intent3 = new Intent(this, SzczepienieActivity.class);
                intent3.putExtra("selected_wizyta", wizyt.getId());
                startActivity(intent3);
            break;
            case"Badanie":
                Intent  intent4 = new Intent(this, BadanieActivity.class);
                intent4.putExtra("selected_wizyta", wizyt.getId());
                startActivity(intent4);
            break;
        }


    }


}

