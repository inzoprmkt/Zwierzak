package com.example.zwierzaki;

import java.util.Date;

public class Chip {
    private String uid;
    private String date;
    private String numer_metryki;
    private String typ;

    public Chip() {
        typ="Chip";
    }

    public Chip(String uid, String date, String numer_metryki,String type) {
        this.uid = uid;
        this.date = date;
        this.numer_metryki = numer_metryki;
        this.typ=type;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getNumer_metryki() {
        return numer_metryki;
    }

    public void setNumer_metryki(String numer_metryki) {
        this.numer_metryki = numer_metryki;
    }

    public String getTyp() {
        return typ;
    }


}
