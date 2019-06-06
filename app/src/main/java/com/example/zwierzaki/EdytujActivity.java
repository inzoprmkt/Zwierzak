package com.example.zwierzaki;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EdytujActivity extends AppCompatActivity {

    EditText tNrMetryki;
    EditText tNrMetrykiMatki;
    EditText tNrMetrykiOjca;
    EditText tImieZwierzecia;
    EditText tPlec;
    EditText tDatUr;
    ImageView tZdjecie;
    Button tZapisz;
    Button tUsun;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String currentUI;
    String tnrMetryki;
    String idZwierzaka;
    FirebaseFirestore db;
    String NrMetryki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj);
        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        currentUI=currentUser.getUid();
        if(getIntent().hasExtra("selected_zwierze")) {
            idZwierzaka = getIntent().getStringExtra("selected_zwierze");
        //    Toast.makeText(EdytujActivity.this, idZwierzaka + "...", Toast.LENGTH_SHORT).show();
            tNrMetryki = (EditText) findViewById(R.id.textNrMetryki);
            tNrMetryki.setText(idZwierzaka);

            tImieZwierzecia = (EditText) findViewById(R.id.textImie);
            //tImieZwierzecia.setText(zwierze.getImieZwierzecia());

            tNrMetrykiMatki = (EditText) findViewById(R.id.textNrMetrykiMatki);
            tNrMetrykiOjca = (EditText) findViewById(R.id.textNrMetrykiOjca);
            tPlec = (EditText) findViewById(R.id.textPlec);
            tDatUr = (EditText) findViewById(R.id.textData);
            tZdjecie = (ImageView) findViewById(R.id.imageViewZwierze);
            tZapisz = (Button) findViewById(R.id.bZapisz);
            tUsun = (Button) findViewById(R.id.bUsun);

             db = FirebaseFirestore.getInstance();
            db.collection("Zwierzeta").whereEqualTo("nrMetryki", idZwierzaka).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {

                        tImieZwierzecia.setText(document.getString("imieZwierzecia"));

                        tNrMetrykiMatki.setText(document.getString("nrMetrykiMatki"));

                        tNrMetrykiOjca.setText(document.getString("nrMetrykiOjca"));

                        tPlec.setText(document.getString("plec"));

                        tDatUr.setText(document.getString("datUr"));//NrMetryki+ "/" + "Zdjecie1"

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference islandRef = storageRef.child(document.getString("nrMetryki") + "/" + "Zdjecie1");//("uid")+ "/" + "Zdjecie" + document.getString("nrMetryki"));

                        final long ONE_MEGABYTE = 1024 * 1024;
                        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                // saveBitmap(couponName, bitmap);

                                tZdjecie.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                }
            });

        }
        tZapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean czy_wszystko_ok = true;
                final String data = tDatUr.getText().toString();
                //Date data=new SimpleDateFormat("dd/MM/yyyy").parse(DatUr);

                //Date data = textKalendarz;
                //Date data = new SimpleDateFormat("dd/MM/yyyy").parse(textKalendarz.toString());

            /*if (!checkDate(data)) {
                czy_wszystko_ok = false;
                textViewBlad.setVisibility(View.VISIBLE);
            }*/
                String Plec = tPlec.getText().toString();
                /*if(idZwierzaka!=tNrMetryki.getText().toString())
                {

                }*/
                 NrMetryki = tNrMetryki.getText().toString();
                if (!checkMetryka(NrMetryki)) {
                    czy_wszystko_ok = false;

                }
                String NrMetrykiMatki = tNrMetrykiMatki.getText().toString();
                if (!checkMetryka(NrMetrykiMatki)) {
                    czy_wszystko_ok = false;

                }
                String NrMetrykiOjca = tNrMetrykiOjca.getText().toString();
                if (!checkMetryka(NrMetrykiOjca)) {
                    czy_wszystko_ok = false;

                }
                String ImieZwierzecia = tImieZwierzecia.getText().toString();
                if (!checkImie(ImieZwierzecia)) {
                    czy_wszystko_ok = false;

                }
                if (czy_wszystko_ok == true) {
                   // Toast.makeText(EdytujActivity.this, idZwierzaka + ".jest..", Toast.LENGTH_SHORT).show();
                    final Zwierze zwierzak=new Zwierze(data,Plec,NrMetryki,NrMetrykiMatki,NrMetrykiOjca,ImieZwierzecia,currentUI,"Zdjecie" + NrMetryki);
                    final DocumentReference Zwierz = db.collection("Zwierzeta").document(tNrMetryki.getText().toString());
                    Zwierz.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                  //  Toast.makeText(EdytujActivity.this, tNrMetryki.getText().toString() + " .jest2.. ", Toast.LENGTH_SHORT).show();
                                  //  Toast.makeText(EdytujActivity.this, tNrMetryki.getText().length()+" == "+idZwierzaka.length(), Toast.LENGTH_SHORT).show();
                                    if(tNrMetryki.getText().toString().equals(idZwierzaka))
                                    {
                                     //   Toast.makeText(EdytujActivity.this,"hdfuktvjfrfhjxcvfb", Toast.LENGTH_SHORT).show();
                                        //EDYTUJEMY
                                        Toast.makeText(EdytujActivity.this, "Edytowano pomyślnie!", Toast.LENGTH_SHORT).show();

                                        Zwierz.update("nrMetrykiMatki",tNrMetrykiMatki.getText().toString());
                                        Zwierz.update("nrMetrykiOjca",tNrMetrykiOjca.getText().toString());
                                        Zwierz.update("imieZwierzecia",tImieZwierzecia.getText().toString());
                                        Zwierz.update("plec",tPlec.getText().toString());
                                        Zwierz.update("datUr",tDatUr.getText().toString());
                                    }
                                    else
                                    {
                                        Toast.makeText(EdytujActivity.this, "!!!!!Zwierze z taką metryką już istnieje!", Toast.LENGTH_SHORT).show();
                                    }
                                } else
                                    {
                                    //dadajemy nowego i usuwamy stareg
                                        db.collection("Zwierzeta").document(NrMetryki).set(zwierzak)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    //tutaj edycja
                                                    Toast.makeText(EdytujActivity.this, "Edytowano pomyślnie!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        db.collection("Zwierzeta").document(idZwierzaka)
                                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                       /// Toast.makeText(EdytujActivity.this, "DocumentSnapshot successfully deleted!", Toast.LENGTH_SHORT).show();
                                                        //Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                    }
                                                });

                                    }
                            } else {
                                Toast.makeText(EdytujActivity.this, "Wystąpił błąd...", Toast.LENGTH_SHORT).show();
                                //Log.d(TAG, "Failed with: ", task.getException());
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(EdytujActivity.this, "nieprawidlowe dane!", Toast.LENGTH_SHORT).show();
                }

            }

            /*public void refreshActivity() {
                Intent i = new Intent(EdytujActivity.this, ZwierzeActivity.class);
                i.putExtra("selected_zwierze", NrMetryki);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //finish();*/


        });
        tUsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //int position) {
                db.collection("Zwierzeta").document(idZwierzaka)
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                         Toast.makeText(EdytujActivity.this, "Document successfully deleted!", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        finish();
                        Intent intent=new Intent(EdytujActivity.this,WyswietlZwierzaki.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }



    public boolean checkMetryka(String metryka) {
        boolean checkFormat = false;
        if (!metryka.isEmpty()) {
            if (metryka.matches("[a-zA-z0-9/]*")) {
                checkFormat = true;
            }
        }
        return checkFormat;
    }

    public boolean checkImie(String imie) {
        boolean checkFormat = true;
        if (!imie.isEmpty()) {
            if (!imie.matches("[A-Za-z]*")) {
                checkFormat = false;
            }
        }
        return checkFormat;
    }
       /* tZdjecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //int position) {
                Intent intent = new Intent(ZwierzeActivity.this, ZdjeciaActivity.class);
                intent.putExtra("selected_zwierze", tnrMetryki);
                startActivity(intent);
            }
        });
        tEdytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NOWE WIDOKI !!! NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!
                Intent intent = new Intent(ZwierzeActivity.this, AddAnimal.class);
                //intent.putExtra("selected_zwierze", mZwierze.get(position));
                startActivity(intent);
            }
        });
        tWizyty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NOWE WIDOKI !!! NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!
                Intent intent = new Intent(ZwierzeActivity.this, AddAnimal.class);
                //intent.putExtra("selected_zwierze", mZwierze.get(position));
                startActivity(intent);
            }
        });*/
    }
