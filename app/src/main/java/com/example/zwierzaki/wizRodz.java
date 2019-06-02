package com.example.zwierzaki;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class wizRodz extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String[] szczepItems;
    boolean[] checkedSzczep;
    ArrayList<Integer> szczepUserItem = new ArrayList<>();
    String[] badItems;
    boolean[] checkedBad;
    ArrayList<Integer> badUserItem = new ArrayList<>();
    String[] higienItems;
    boolean[] checkedHigien;
    ArrayList<Integer> higienUserItem = new ArrayList<>();
    FirebaseUser currentUser;
    private String currentUI;
    private Button btnKalendarz;
    private TextView textKalendarz;
    private int year;
    private int month;
    private int dayOfMonth;
    private Calendar calendar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private Spinner spinner;
    private String textKalendarzText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiz_rodz);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUI = currentUser.getUid();

        mOrder = findViewById(R.id.btnOrder);
        mItemSelected = findViewById(R.id.tvItemSelected);
        btnKalendarz = findViewById(R.id.buttonKalendarz2);
        textKalendarz = findViewById(R.id.textViewKalendarz2);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        textKalendarzText = dayOfMonth + "/" + (month + 1) + "/" + year;
        textKalendarz.setText(textKalendarzText);
        btnKalendarz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(wizRodz.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                textKalendarzText = dayOfMonth + "/" + (month + 1) + "/" + year;
                                textKalendarz.setText(textKalendarzText);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];
        spinner = findViewById(R.id.spinnerZwierzaki);
        final List<String> subjects = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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

    }

    public void btnlistaOgolna(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(wizRodz.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected.setText(item);
                        szczep(0);
                    }
                });
                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }

        });
    }

    protected void szczep(int id) {
        if (!mUserItems.isEmpty()) {
            String tekst = spinner.getSelectedItem().toString();
            String[] czesci = tekst.split(" ");
            final String piesnr = czesci[czesci.length - 1];
            Toast.makeText(wizRodz.this, piesnr, Toast.LENGTH_SHORT).show();
            LayoutInflater inflater = LayoutInflater.from(wizRodz.this);
            String strData = textKalendarz.getText().toString();
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = formatter1.parse(strData);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            final Date data = date;
            Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < mUserItems.size(); i++) {
                switch (listItems[mUserItems.get(i)]) {
                    case "Szczepienie":
                        szczepItems = getResources().getStringArray(R.array.szczep_menu);
                        checkedSzczep = new boolean[szczepItems.length];
                        View subViewszczep = inflater.inflate(R.layout.dialog_layout, null);
                        final EditText subEditText = subViewszczep.findViewById(R.id.dialogEditText);
                        AlertDialog.Builder szczep = new AlertDialog.Builder(wizRodz.this);
                        szczep.setTitle("Wybierz szczepionki");
                        szczep.setMultiChoiceItems(szczepItems, checkedSzczep, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                                if (isChecked) {
                                    szczepUserItem.add(position);
                                } else {
                                    szczepUserItem.remove((Integer.valueOf(position)));
                                }
                            }
                        });
                        szczep.setView(subViewszczep);
                        szczep.setCancelable(false);
                        szczep.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Szczepienie szczepienie = new Szczepienie();
                                szczepienie.setNumer_metryki(piesnr);
                                szczepienie.setdate(data);
                                szczepienie.setUid(currentUI);
                                for (int i = 0; i < szczepUserItem.size(); i++) {
                                    if (checkedSzczep[szczepUserItem.get(i)]) {
                                        switch (szczepItems[szczepUserItem.get(i)]) {
                                            case "Wścieklizna":
                                                szczepienie.setWscieklizna(true);
                                                break;
                                            case "Parwowiroza":
                                                szczepienie.setParwowiroza(true);
                                                break;
                                            case "Nosówka":
                                                szczepienie.setNosowka(true);
                                                break;
                                            case "Leptospiroza":
                                                szczepienie.setLeptospiroza(true);
                                                break;
                                            case "Choroba Rubartha":
                                                szczepienie.setRubarth(true);
                                                break;
                                        }
                                        //Toast.makeText(wizRodz.this, "Zaznaczono" + szczepItems[i], Toast.LENGTH_SHORT).show();

                                    }
                                    if (szczepUserItem.get(i) == 5 && !subEditText.getText().toString().matches("")) {
                                        // Toast.makeText(wizRodz.this, szczepItems[5], Toast.LENGTH_SHORT).show();
                                        szczepienie.setInne(subEditText.getText().toString());
                                    }
                                }

                                dodaj(szczepienie);
                            }

                            private void dodaj(Szczepienie szczepienie) {
                                db.collection("Wizyta").add(szczepienie).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(wizRodz.this, "Dodano pomyślnie!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                        AlertDialog szczepOkno = szczep.create();
                        szczepOkno.show();
                        break;
                    case "Wszczepienie chipa":
                        Chip chip = new Chip();
                        chip.setNumer_metryki(piesnr);
                        chip.setdate(data);
                        chip.setUid(currentUI);
                        db.collection("Wizyta").add(chip).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(wizRodz.this, "Dodano pomyślnie!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Badania":
                        badItems = getResources().getStringArray(R.array.badania);
                        checkedBad = new boolean[badItems.length];
                        inflater = LayoutInflater.from(wizRodz.this);
                        View subViewBad = inflater.inflate(R.layout.dialog_layout, null);
                        final EditText subEditText1 = subViewBad.findViewById(R.id.dialogEditText);
                        AlertDialog.Builder badanie = new AlertDialog.Builder(wizRodz.this);
                        badanie.setTitle("wybierz badania");
                        badanie.setMultiChoiceItems(badItems, checkedBad, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                                if (isChecked) {
                                    badUserItem.add(position);
                                } else {
                                    badUserItem.remove((Integer.valueOf(position)));
                                }
                            }
                        });
                        badanie.setView(subViewBad);
                        badanie.setCancelable(false);
                        badanie.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Badanie badaniee = new Badanie();
                                badaniee.setNumer_metryki(piesnr);
                                badaniee.setdate(data);
                                badaniee.setUid(currentUI);
                                for (int i = 0; i < badUserItem.size(); i++) {
                                    if (checkedBad[badUserItem.get(i)]) {
                                        switch (badItems[badUserItem.get(i)]) {
                                            case "Morfologia":
                                                badaniee.setMorfologia(true);
                                                break;
                                            case "Rozmaz krwi":
                                                badaniee.setKrew(true);
                                                break;
                                            case "Badanie moczu":
                                                badaniee.setMocz(true);
                                                break;
                                            case "Badania biochemiczne":
                                                badaniee.setBiochem(true);
                                                break;
                                            case "RTG":
                                                badaniee.setRTG(true);
                                                break;
                                            case "EKG":
                                                badaniee.setEKG(true);
                                                break;
                                            case "USG":
                                                badaniee.setUSG(true);
                                                break;
                                        }
                                    }
                                    if (badUserItem.get(i) == 7 && !subEditText1.getText().toString().matches("")) {
                                        badaniee.setInne(subEditText1.getText().toString());
                                    }
                                }
                                dodaj(badaniee);
                            }

                            private void dodaj(Badanie badnie) {
                                db.collection("Wizyta").add(badnie).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(wizRodz.this, "Dodano pomyślnie!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        AlertDialog badOkno = badanie.create();
                        badOkno.show();
                        break;
                    case "Zabieg":
                        inflater = LayoutInflater.from(wizRodz.this);
                        View subViewZab = inflater.inflate(R.layout.dialog_layout, null);
                        final EditText subEditTextZab = subViewZab.findViewById(R.id.dialogEditText);
                        AlertDialog.Builder builder = new AlertDialog.Builder(wizRodz.this);
                        builder.setMessage("Opisz zabieg");
                        builder.setView(subViewZab);
                        builder.setPositiveButton("Dodaj",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Boolean wantToCloseDialog = false;
                                if (!subEditTextZab.getText().toString().equals("")) {
                                    wantToCloseDialog = true;
                                }
                                if (wantToCloseDialog) {
                                    Zabieg zabieg = new Zabieg();
                                    zabieg.setNumer_metryki(piesnr);
                                    zabieg.setDate(data);
                                    zabieg.setUid(currentUI);
                                    zabieg.setOpis(subEditTextZab.getText().toString());
                                    db.collection("Wizyta").add(zabieg).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(wizRodz.this, "Dodano pomyślnie!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(wizRodz.this, "Wprowadź opis!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case "Zabieg higieniczny":
                        higienItems = getResources().getStringArray(R.array.zabHignien);
                        checkedHigien = new boolean[higienItems.length];
                        inflater = LayoutInflater.from(wizRodz.this);
                        View subViewZabHig = inflater.inflate(R.layout.dialog_layout, null);
                        final EditText subEditText2 = subViewZabHig.findViewById(R.id.dialogEditText);
                        AlertDialog.Builder higien = new AlertDialog.Builder(wizRodz.this);
                        higien.setTitle("wybierz zabiegi");
                        higien.setMultiChoiceItems(higienItems, checkedHigien, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                                if (isChecked) {
                                    higienUserItem.add(position);
                                } else {
                                    higienUserItem.remove((Integer.valueOf(position)));
                                }
                            }
                        });
                        higien.setView(subViewZabHig);
                        higien.setCancelable(false);
                        higien.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ZabHigien zabHigien = new ZabHigien();
                                zabHigien.setNumer_metryki(piesnr);
                                zabHigien.setdate(data);
                                zabHigien.setUid(currentUI);
                                for (int i = 0; i < higienUserItem.size(); i++) {
                                    if (checkedHigien[higienUserItem.get(i)]) {
                                        switch (higienItems[higienUserItem.get(i)]) {
                                            case "Czyszczenie zębów":
                                                zabHigien.setC_zebow(true);
                                                break;
                                            case "Wyciąganie kleszczy":
                                                zabHigien.setKleszcz(true);
                                                break;
                                            case "Czyszczenie uszu":
                                                zabHigien.setC_uszu(true);
                                                break;
                                            case "Strzyżenie sierści":
                                                zabHigien.setStrzyzenie(true);
                                                break;
                                            case "Usuwanie kamienia nazębnego":
                                                zabHigien.setUsuw_kamienia(true);
                                                break;
                                        }
                                    }
                                    if (higienUserItem.get(i) == 5 && !subEditText2.getText().toString().matches("")) {
                                        zabHigien.setInne(subEditText2.getText().toString());
                                    }
                                }
                                dodaj(zabHigien);
                            }

                            private void dodaj(ZabHigien zabHigien) {
                                db.collection("Wizyta").add(zabHigien).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(wizRodz.this, "Dodano pomyślnie!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        AlertDialog higienOkno = higien.create();
                        higienOkno.show();
                        break;
                }
            }
        }
    }


}
