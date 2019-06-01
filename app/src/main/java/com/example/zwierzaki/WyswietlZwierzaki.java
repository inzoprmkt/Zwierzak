package com.example.zwierzaki;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class WyswietlZwierzaki extends AppCompatActivity implements Zwierze_Info_Adapter.OnZwierzeListener{

    private static final String TAG = "AddAnimal";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String currentUI = currentUser.getUid();
    private CollectionReference listaZwierzat = db.collection("Zwierzeta");
    private RecyclerView mRecyclerView;
    private ArrayList<Zwierze> mZwierze = new ArrayList<>();
    private Zwierze_Info_Adapter mZwierze_Info_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyswietl_zwierzaki);
        mRecyclerView = findViewById(R.id.recyclerView);

        listaZwierzat.whereEqualTo("uid",currentUI).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                initRecyclerView();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                        Zwierze zwierze = new Zwierze();
                        documentSnapshots.get("imieZwierzecia");
                        //Toast.makeText(WyswietlZwierzaki.this, documentSnapshots.get("imieZwierzecia").toString()+" ", Toast.LENGTH_SHORT).show();

                        zwierze.setNrMetryki(documentSnapshots.get("nrMetryki").toString());
                        zwierze.setImieZwierzecia(documentSnapshots.get("imieZwierzecia").toString());
                       /* zwierze.setDatUr(documentSnapshots.get("datUr").toString());
                        zwierze.setNrMetrykiMatki(documentSnapshots.get("nrMetrykiMatki").toString());
                        zwierze.setNrMetrykiOjca(documentSnapshots.get("nrMetrykiOjca").toString());
                        zwierze.setPlec(documentSnapshots.get("plec").toString());
                        zwierze.setZdjecie(documentSnapshots.get("zdjecie").toString());*/
                        mZwierze.add(zwierze);
                    }
                }
            }
        });
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mZwierze_Info_Adapter = new Zwierze_Info_Adapter(mZwierze, this);
        mRecyclerView.setAdapter(mZwierze_Info_Adapter);
    }

    @Override
    public void onZwierzeClick(int position) {
        Log.d(TAG, "onZwierzeClick: clicked." + position);
        //mZwierze.get(position);
        Intent intent = new Intent(this, ZwierzeActivity.class);
        intent.putExtra("selected_zwierze", mZwierze.get(position));
        startActivity(intent);
    }
}