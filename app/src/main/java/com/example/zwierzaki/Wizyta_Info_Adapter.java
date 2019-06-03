package com.example.zwierzaki;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Wizyta_Info_Adapter extends RecyclerView.Adapter<Wizyta_Info_Adapter.ViewHolder> {

    private ArrayList<Wizyta> mNotes = new ArrayList<>();
    private  OnWizytaListener mOnWizytaListener;

    public Wizyta_Info_Adapter(ArrayList<Wizyta> notes, OnWizytaListener onWizytaListener) {
        this.mNotes = notes;
        this.mOnWizytaListener = onWizytaListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_wizyty, parent, false);
        return new ViewHolder(view, mOnWizytaListener);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String string = mNotes.get(position).getDate().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(string, formatter);
       // Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        holder.timestamp.setText(date.toString());
        holder.typ.setText(mNotes.get(position).getTyp());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView typ, timestamp;
        OnWizytaListener onWizytaListener;

        public ViewHolder(@NonNull View itemView, OnWizytaListener onWizytaListener) {
            super(itemView);
            typ = itemView.findViewById(R.id.vNazwaWizyty);
            //String data=new SimpleDateFormat("yyyy-MM-dd").format(date);
            timestamp = itemView.findViewById(R.id.vData);
            this.onWizytaListener = onWizytaListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {onWizytaListener.onWizytaClick(getAdapterPosition()); }
    }

    public interface OnWizytaListener{
        void onWizytaClick(int position);
    }
}

