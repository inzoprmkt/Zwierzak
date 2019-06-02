package com.example.zwierzaki;

import java.util.Date;

public class Wizyta {
    private Date date;
    private String typ;
    private String numer_metryki;
    private String id;

    public Wizyta(Date date, String typ, String numer_metryki,String Id) {
        this.date = date;
        this.typ = typ;
        this.numer_metryki = numer_metryki;
        this.id=Id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getNumer_metryki() {
        return numer_metryki;
    }

    public void setNumer_metryki(String numer_metryki) {
        this.numer_metryki = numer_metryki;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
