package com.example.zwierzaki;

import java.util.Date;

public class Zabieg {
    private String Opis;
    private String numer_metryki;
    private String uid;
    private Date date;
    private String typ;

    public Zabieg() {
        this.typ="Zabieg";
    }

    public Zabieg( String opis, Date datez,String uidy,String nrmerr,String typez) {
        this.numer_metryki=nrmerr;
        this.uid=uid;
        this.Opis = opis;
        this.date = datez;
        this.typ=typez;
    }


    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumer_metryki() {
        return numer_metryki;
    }

    public void setNumer_metryki(String numer_metryki) {
        this.numer_metryki = numer_metryki;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTyp() {
        return typ;
    }


}
