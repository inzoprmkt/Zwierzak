package com.example.zwierzaki;

import java.util.Date;

public class ZabHigien {
    private String uid;
    private Date date;
    private boolean c_zebow;
    private boolean kleszcz;
    private boolean c_uszu;
    private boolean strzyzenie;
    private boolean usuw_kamienia;
    private String typ;
    private String inne;
    private String numer_metryki;

    public ZabHigien() {
        typ="Zabieg Higieniczny";
        c_zebow=false;
        kleszcz=false;
        c_uszu=false;
        strzyzenie=false;
        usuw_kamienia=false;
    }

    public ZabHigien(String uidzh, Date date, boolean c_zebow, boolean kleszcz, boolean c_uszu, boolean strzyzenie, boolean usuw_kamienia, String inne,String nrmetr,String type) {
        this.uid = uidzh;
        this.date = date;
        this.c_zebow = c_zebow;
        this.kleszcz = kleszcz;
        this.c_uszu = c_uszu;
        this.strzyzenie = strzyzenie;
        this.usuw_kamienia = usuw_kamienia;
        this.inne = inne;
        this.typ="Zabieg Higieniczny";
        this.numer_metryki=nrmetr;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getdate() {
        return date;
    }

    public void setdate(Date date) {
        this.date = date;
    }

    public boolean isC_zebow() {
        return c_zebow;
    }

    public void setC_zebow(boolean c_zebow) {
        this.c_zebow = c_zebow;
    }

    public boolean isKleszcz() {
        return kleszcz;
    }

    public void setKleszcz(boolean kleszcz) {
        this.kleszcz = kleszcz;
    }

    public boolean isC_uszu() {
        return c_uszu;
    }

    public void setC_uszu(boolean c_uszu) {
        this.c_uszu = c_uszu;
    }

    public boolean isStrzyzenie() {
        return strzyzenie;
    }

    public void setStrzyzenie(boolean strzyzenie) {
        this.strzyzenie = strzyzenie;
    }

    public boolean isUsuw_kamienia() {
        return usuw_kamienia;
    }

    public void setUsuw_kamienia(boolean usuw_kamienia) {
        this.usuw_kamienia = usuw_kamienia;
    }

    public String getInne() {
        return inne;
    }

    public void setInne(String inne) {
        this.inne = inne;
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
