package com.example.zwierzaki;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddAnimal extends AppCompatActivity {



    private static final String TAG = "AddAnimal";
    //private EditText editTextDatUr;
    private TextView textViewBlad;
    private Spinner SpinnerPlec;
    private EditText editTextNrMetryki;
    private EditText editTextNrMetrykiMatki;
    private EditText editTextNrMetrykiOjca;
    private EditText editTextImieZwierzecia;
    private Button btnAdd;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String currentUI;
    private Button btnKalendarz;
    private TextView textKalendarz;
    DatePickerDialog datePickerDialog;
    private int year;
    private int month;
    private int dayOfMonth;
    private Calendar calendar;
    private Button btnChoose, btnUpload;
    private ImageView imageView;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 10;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        currentUI=currentUser.getUid();
        //editTextDatUr = findViewById(R.id.editText_data_urodzenia);
        SpinnerPlec = findViewById(R.id.spinner_plec);
        editTextImieZwierzecia = findViewById(R.id.editText_imie_zwierze);
        editTextNrMetryki = findViewById(R.id.editText_numer_metryki);
        editTextNrMetrykiMatki = findViewById(R.id.editText_metryka_matki);
        editTextNrMetrykiOjca = findViewById(R.id.editText_metryka_ojca);
        textViewBlad=findViewById(R.id.TextViewBlad);
        btnAdd = findViewById(R.id.buttonDodaj_zwierze);
        btnKalendarz = findViewById(R.id.buttonKalendarz);
        textKalendarz = findViewById(R.id.textViewKalendarz);


        btnKalendarz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddAnimal.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                textKalendarz.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Initialize Views
        btnChoose =  findViewById(R.id.btnChoose);
        imageView =  findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        /*btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(currentUI, NrMetryki);
            }
        });*/

    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(String currentUI, String NrMetryki) {

        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child(NrMetryki+ "/" + "Zdjecie1");//(currentUI+ "/" + "Zdjecie" + NrMetryki);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddAnimal.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddAnimal.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


        /*private static final String KEYDatUr = "DataUrodzenia";
        private static final String KEYPlec = "Plec";
        private static final String KEYNrMetryki = "NumerMetryki";
        private static final String KEYNrMetrykiMatki = "NumerMetrykiMatki";
        private static final String KEYNrMetrykiOjca = "NumerMetrykiOjca";
        private static final String KEYImieZwierzecia = "ImieZwierzecia";
        private static final String KEYUID = "UIDwlasciciela";*/

    public void Dodaj(View v) {
        boolean czy_wszystko_ok = true;
        final String data = textKalendarz.getText().toString();
        //Date data=new SimpleDateFormat("dd/MM/yyyy").parse(DatUr);

        //Date data = textKalendarz;
        //Date data = new SimpleDateFormat("dd/MM/yyyy").parse(textKalendarz.toString());

            /*if (!checkDate(data)) {
                czy_wszystko_ok = false;
                textViewBlad.setVisibility(View.VISIBLE);
            }*/
        String Plec = SpinnerPlec.getSelectedItem().toString();
        final String NrMetryki = editTextNrMetryki.getText().toString();
        String toscik="";
        if (!checkMetryka(NrMetryki)) {
            czy_wszystko_ok = false;
            textViewBlad.setVisibility(View.VISIBLE);
            toscik+="Nieprawidlowe imie zwierzecia!\n";
        }
        String NrMetrykiMatki = editTextNrMetrykiMatki.getText().toString();
        if (!checkMetryka(NrMetrykiMatki)) {
            czy_wszystko_ok = false;
            textViewBlad.setVisibility(View.VISIBLE);
            toscik+="Nieprawidlowy numer metryki matki!\n";
        }
        String NrMetrykiOjca = editTextNrMetrykiOjca.getText().toString();
        if (!checkMetryka(NrMetrykiOjca)) {
            czy_wszystko_ok = false;
            textViewBlad.setVisibility(View.VISIBLE);
            toscik+="Nieprawidlowy numer metryki ojca!\n";
        }
        String ImieZwierzecia = editTextImieZwierzecia.getText().toString();
        if (!checkImie(ImieZwierzecia)) {
            czy_wszystko_ok = false;
            textViewBlad.setVisibility(View.VISIBLE);
            toscik+="Nieprawidlowe imie!\n";
        }
        if(toscik.length()>1)
        {
            toscik=toscik.substring(0,toscik.length()-1);
            Toast.makeText(AddAnimal.this, toscik, Toast.LENGTH_SHORT).show();
            toscik="";
        }


        if (czy_wszystko_ok) {
            uploadImage(currentUI, NrMetryki);
            final Zwierze zwierzak=new Zwierze(data,Plec,NrMetryki,NrMetrykiMatki,NrMetrykiOjca,ImieZwierzecia,currentUI,"Zdjecie" + NrMetryki);
            DocumentReference Zwierz = db.collection("Zwierzeta").document(NrMetryki);
            Zwierz.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Toast.makeText(AddAnimal.this, "Pies z taką metryka istnieje", Toast.LENGTH_SHORT).show();
                        } else {
                            db.collection("Zwierzeta").document(NrMetryki).set(zwierzak)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AddAnimal.this, "Dodano pomyślnie!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });
        }
        else
        {
           // Toast.makeText(AddAnimal.this, "nieprawidlowe dane!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkDate(String date) {
        boolean checkFormat = false;
        if (!date.isEmpty()) {
            if (date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
                checkFormat = true;
            }
        }
        return checkFormat;
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
        else
        {
            checkFormat = false;
        }
        return checkFormat;
    }

}
