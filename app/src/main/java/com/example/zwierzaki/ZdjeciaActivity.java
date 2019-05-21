package com.example.zwierzaki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ZdjeciaActivity extends AppCompatActivity {

    Button tDodajZdjecie;
    private RecyclerView mRecyclerView;

    private List<byte[]> mUploads;
    private Zdjecie_Info_Adapter mAdapter;

    //private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
   // private DatabaseReference mDatabaseRef;
   // private ValueEventListener mDBListener;

    ImageView tImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zdjecia);
        final String idZwierzaka;

        tImageView = (ImageView) findViewById(R.id.imageView2);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        if(getIntent().hasExtra("selected_zwierze")){
            idZwierzaka = getIntent().getStringExtra("selected_zwierze");
            Toast.makeText(ZdjeciaActivity.this, idZwierzaka + "...", Toast.LENGTH_SHORT).show();

            StorageReference storageRef = FirebaseStorage.getInstance().getReference();//idZwierzaka);
            StorageReference islandRef = storageRef.child(idZwierzaka+ "/" + "Zdjecie1");//("uid")+ "/" + "Zdjecie" + document.getString("nrMetryki"));

            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    tImageView.setImageBitmap(bitmap);
                   /* mUploads = new ArrayList<>();

                    mAdapter = new Zdjecie_Info_Adapter(ZdjeciaActivity.this, mUploads);

                    mRecyclerView.setAdapter(mAdapter);*/


                   // StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                 //   StorageReference islandRef = storageRef.child(idZwierzaka+ "/" + "Zdjecie1");//("uid")+ "/" + "Zdjecie" + document.getString("nrMetryki"));

                    /*final long ONE_MEGABYTE = 1024 * 1024;
                    storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            //mProgressCircle = findViewById(R.id.progress_circle);

                           // mUploads.add(bytes);
                           // ImageView zdjecie = new ImageView(ZdjeciaActivity.this);
                          //  zdjecie.setImageBitmap(bitmap);
                            tImageView.setImageBitmap(bitmap);

                           // mRecyclerView.addItemDecoration(zdjecie);

                            // tZdjecie.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });*/
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
    }
}

