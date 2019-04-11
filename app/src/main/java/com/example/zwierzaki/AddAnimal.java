package com.example.zwierzaki;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_animal);
            mAuth = FirebaseAuth.getInstance();
            currentUser=mAuth.getCurrentUser();
            currentUI=currentUser.getUid();
            //editTextDatUr = findViewById(R.id.editText_data_urodzenia);
            SpinnerPlec = findViewById(R.id.spinner_plec);
            editTextNrMetryki = findViewById(R.id.editText_numer_metryki);
            editTextNrMetrykiMatki = findViewById(R.id.editText_metryka_matki);
            editTextNrMetrykiOjca = findViewById(R.id.editText_metryka_ojca);
            editTextImieZwierzecia = findViewById(R.id.editText_imie_zwierze);
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
                    //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.show();


                }
            });

        }

        /*private static final String KEYDatUr = "DataUrodzenia";
        private static final String KEYPlec = "Plec";
        private static final String KEYNrMetryki = "NumerMetryki";
        private static final String KEYNrMetrykiMatki = "NumerMetrykiMatki";
        private static final String KEYNrMetrykiOjca = "NumerMetrykiOjca";
        private static final String KEYImieZwierzecia = "ImieZwierzecia";
        private static final String KEYUID = "UIDwlasciciela";*/

        public void Dodaj(View v) throws ParseException {
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
            if (!checkMetryka(NrMetryki)) {
                czy_wszystko_ok = false;
                textViewBlad.setVisibility(View.VISIBLE);
            }
            String NrMetrykiMatki = editTextNrMetrykiMatki.getText().toString();
            if (!checkMetryka(NrMetrykiMatki)) {
                czy_wszystko_ok = false;
                textViewBlad.setVisibility(View.VISIBLE);
            }
            String NrMetrykiOjca = editTextNrMetrykiOjca.getText().toString();
            if (!checkMetryka(NrMetrykiOjca)) {
                czy_wszystko_ok = false;
                textViewBlad.setVisibility(View.VISIBLE);
            }
            String ImieZwierzecia = editTextImieZwierzecia.getText().toString();
            if (!checkImie(ImieZwierzecia)) {
                czy_wszystko_ok = false;
                textViewBlad.setVisibility(View.VISIBLE);
            }
            if (czy_wszystko_ok == true) {
                final Zwierze zwierzak=new Zwierze(data,Plec,NrMetryki,NrMetrykiMatki,NrMetrykiOjca,ImieZwierzecia,currentUI);
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
                Toast.makeText(AddAnimal.this, "nieprawidlowe dane!", Toast.LENGTH_SHORT).show();
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
                if (!imie.matches("[A-Z][a-z]*")) {
                    checkFormat = false;
                }
            }
            return checkFormat;
        }

    }
