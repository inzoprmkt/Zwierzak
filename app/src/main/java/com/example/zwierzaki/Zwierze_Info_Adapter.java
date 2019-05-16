package com.example.zwierzaki;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

public class Zwierze_Info_Adapter extends RecyclerView.Adapter<Zwierze_Info_Adapter.ViewHolder> {

    private ArrayList<Zwierze> mNotes = new ArrayList<>();

    public Zwierze_Info_Adapter(ArrayList<Zwierze> notes) {
        this.mNotes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_zwierzaki, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.timestamp.setText(mNotes.get(position).getNrMetryki());
        holder.title.setText(mNotes.get(position).getImieZwierzecia());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.vImieZwierzecia);
            timestamp = itemView.findViewById(R.id.vNrMetryki);
        }
    }
}

