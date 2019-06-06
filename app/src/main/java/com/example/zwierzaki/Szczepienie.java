package com.example.zwierzaki;

import java.util.Date;

public class Szczepienie {
    private String uid;
    private String date;
    private String numer_metryki;
    private boolean wscieklizna;
    private boolean parwowiroza;
    private boolean nosowka;
    private boolean leptospiroza;
    private boolean rubarth;
    private String typ;
    private String inne;

    public Szczepienie() {
        wscieklizna=false;
        parwowiroza=false;
        nosowka=false;
        leptospiroza=false;
        rubarth=false;
        typ="Szczepienie";
    }

    public Szczepienie(String uid, String date, boolean wscieklizna, boolean parwowiroza, boolean nosowka, boolean leptospiroza, boolean rubarth, String inne,String nrMetr,String type) {
        this.uid = uid;
        this.date = date;
        this.wscieklizna = wscieklizna;
        this.parwowiroza = parwowiroza;
        this.nosowka = nosowka;
        this.leptospiroza = leptospiroza;
        this.rubarth = rubarth;
        this.inne = inne;
        this.numer_metryki=nrMetr;
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

    public boolean isWscieklizna() {
        return wscieklizna;
    }

    public void setWscieklizna(boolean wscieklizna) {
        this.wscieklizna = wscieklizna;
    }

    public boolean isParwowiroza() {
        return parwowiroza;
    }

    public void setParwowiroza(boolean parwowiroza) {
        this.parwowiroza = parwowiroza;
    }

    public boolean isNosowka() {
        return nosowka;
    }

    public void setNosowka(boolean nosowka) {
        this.nosowka = nosowka;
    }

    public boolean isLeptospiroza() {
        return leptospiroza;
    }

    public void setLeptospiroza(boolean leptospiroza) {
        this.leptospiroza = leptospiroza;
    }

    public boolean isRubarth() {
        return rubarth;
    }

    public void setRubarth(boolean rubarth) {
        this.rubarth = rubarth;
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
