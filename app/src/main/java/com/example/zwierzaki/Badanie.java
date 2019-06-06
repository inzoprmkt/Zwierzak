package com.example.zwierzaki;

import java.util.Date;

public class Badanie {
    private String uid;
    private String numer_metryki;
    private String date;
    private boolean Morfologia;
    private boolean krew;
    private boolean mocz;
    private boolean biochem;
    private boolean RTG;
    private boolean EKG;
    private boolean USG;
    private String typ;
    private String inne;

    public Badanie() {
        Morfologia=false;
        krew=false;
        mocz=false;
        biochem=false;
        RTG=false;
        EKG=false;
        USG=false;
        typ="Badanie";

    }

    public Badanie(String uid, String date, boolean morfologia, boolean krew, boolean mocz, boolean biochem, boolean RTG, boolean EKG, boolean USG, String inne,String nrmetr,String type) {
        this.uid = uid;
        this.date = date;
        Morfologia = morfologia;
        this.krew = krew;
        this.mocz = mocz;
        this.biochem = biochem;
        this.RTG = RTG;
        this.EKG = EKG;
        this.USG = USG;
        this.inne = inne;
        this.numer_metryki=nrmetr;
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

    public boolean isMorfologia() {
        return Morfologia;
    }

    public void setMorfologia(boolean morfologia) {
        Morfologia = morfologia;
    }

    public boolean isKrew() {
        return krew;
    }

    public void setKrew(boolean krew) {
        this.krew = krew;
    }

    public boolean isMocz() {
        return mocz;
    }

    public void setMocz(boolean mocz) {
        this.mocz = mocz;
    }

    public boolean isBiochem() {
        return biochem;
    }

    public void setBiochem(boolean biochem) {
        this.biochem = biochem;
    }

    public boolean isRTG() {
        return RTG;
    }

    public void setRTG(boolean RTG) {
        this.RTG = RTG;
    }

    public boolean isEKG() {
        return EKG;
    }

    public void setEKG(boolean EKG) {
        this.EKG = EKG;
    }

    public boolean isUSG() {
        return USG;
    }

    public void setUSG(boolean USG) {
        this.USG = USG;
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
