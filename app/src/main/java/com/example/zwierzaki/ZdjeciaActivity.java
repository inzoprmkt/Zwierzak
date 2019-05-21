package com.example.zwierzaki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class ZdjeciaActivity extends AppCompatActivity {

    Button tDodajZdjecie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zdjecia);
        final String idZwierzaka;
        if(getIntent().hasExtra("selected_zwierze")){
            idZwierzaka = getIntent().getStringExtra("selected_zwierze");
            Toast.makeText(ZdjeciaActivity.this, idZwierzaka + "...", Toast.LENGTH_SHORT).show();

           /* StorageReference storageRef = FirebaseStorage.getInstance().getReference(idZwierzaka);
           // StorageReference islandRef = storageRef.child(idZwierzaka+ "/" + "Zdjecie1");//("uid")+ "/" + "Zdjecie" + document.getString("nrMetryki"));

            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                     StorageReference islandRef = storageRef.child(idZwierzaka+ "/" + "Zdjecie1");//("uid")+ "/" + "Zdjecie" + document.getString("nrMetryki"));

                    final long ONE_MEGABYTE = 1024 * 1024;
                    storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            // tZdjecie.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });*/
        }
    }
}

