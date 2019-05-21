package com.example.zwierzaki;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ZwierzeActivity extends AppCompatActivity {

    TextView tNrMetryki;
    TextView tNrMetrykiMatki;
    TextView tNrMetrykiOjca;
    TextView tImieZwierzecia;
    TextView tPlec;
    TextView tDatUr;
    ImageView tZdjecie;
    Button tEdytuj;
    Button tWizyty;
    String tnrMetryki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwierze);

        if(getIntent().hasExtra("selected_zwierze")){
            Zwierze zwierze = getIntent().getParcelableExtra("selected_zwierze");
            tNrMetryki = (TextView)findViewById(R.id.textNrMetryki);
            tNrMetryki.setText(zwierze.getNrMetryki());

            tImieZwierzecia = (TextView)findViewById(R.id.textImie);
            tImieZwierzecia.setText(zwierze.getImieZwierzecia());

            tNrMetrykiMatki = (TextView)findViewById(R.id.textNrMetrykiMatki);
            tNrMetrykiOjca = (TextView)findViewById(R.id.textNrMetrykiOjca);
            tPlec = (TextView)findViewById(R.id.textPlec);
            tDatUr = (TextView)findViewById(R.id.textData);
            tZdjecie = (ImageView) findViewById(R.id.imageViewZwierze);
            tEdytuj = (Button)findViewById(R.id.Edytuj);
            tWizyty =(Button)findViewById(R.id.Leczenie);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Zwierzeta").whereEqualTo("nrMetryki", zwierze.getNrMetryki()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {

                        tNrMetrykiMatki.setText(document.getString("nrMetrykiMatki"));

                        tNrMetrykiOjca.setText(document.getString("nrMetrykiOjca"));

                        tPlec.setText(document.getString("plec"));

                        tDatUr.setText(document.getString("datUr"));//NrMetryki+ "/" + "Zdjecie1"

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference islandRef = storageRef.child(document.getString("nrMetryki")+ "/" + "Zdjecie1");//("uid")+ "/" + "Zdjecie" + document.getString("nrMetryki"));

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
            tnrMetryki = zwierze.getNrMetryki();
        }

        tZdjecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //int position) {
                //NOWE WIDOKI !!! NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!NOWE WIDOKI !!!
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
        });
    }
}
