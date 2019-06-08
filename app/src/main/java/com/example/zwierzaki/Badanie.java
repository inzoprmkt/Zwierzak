package com.example.zwierzaki;

import java.util.Date;

public class Badanie {
    private String uid;
    private String numer_metryki;
    private String date;
    private boolean Morfologia;
    private boolean Morfologiaczypoprawne;
    private String Morfologiaopis;
    private boolean krew;
    private boolean krewczypoprawne;
    private String krewopis;
    private boolean mocz;
    private boolean moczczypoprawne;
    private String moczopis;
    private boolean biochem;
    private boolean biochemczypoprawne;
    private String biochemopis;
    private boolean RTG;
    private boolean RTGczypoprawne;
    private String RTGopis;
    private boolean EKG;
    private boolean EKGczypoprawne;
    private String EKGopis;
    private boolean USG;
    private boolean USGczypoprawne;
    private String USGopis;
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

    public boolean isMorfologiaczypoprawne() {
        return Morfologiaczypoprawne;
    }

    public void setMorfologiaczypoprawne(boolean morfologiaczypoprawne) {
        Morfologiaczypoprawne = morfologiaczypoprawne;
    }

    public String getMorfologiaopis() {
        return Morfologiaopis;
    }

    public void setMorfologiaopis(String morfologiaopis) {
        Morfologiaopis = morfologiaopis;
    }

    public boolean isKrewczypoprawne() {
        return krewczypoprawne;
    }

    public void setKrewczypoprawne(boolean krewczypoprawne) {
        this.krewczypoprawne = krewczypoprawne;
    }

    public String getKrewopis() {
        return krewopis;
    }

    public void setKrewopis(String krewopis) {
        this.krewopis = krewopis;
    }

    public boolean isMoczczypoprawne() {
        return moczczypoprawne;
    }

    public void setMoczczypoprawne(boolean moczczypoprawne) {
        this.moczczypoprawne = moczczypoprawne;
    }

    public String getMoczopis() {
        return moczopis;
    }

    public void setMoczopis(String moczopis) {
        this.moczopis = moczopis;
    }

    public boolean isBiochemczypoprawne() {
        return biochemczypoprawne;
    }

    public void setBiochemczypoprawne(boolean biochemczypoprawne) {
        this.biochemczypoprawne = biochemczypoprawne;
    }

    public String getBiochemopis() {
        return biochemopis;
    }

    public void setBiochemopis(String biochemopis) {
        this.biochemopis = biochemopis;
    }

    public boolean isRTGczypoprawne() {
        return RTGczypoprawne;
    }

    public void setRTGczypoprawne(boolean RTGczypoprawne) {
        this.RTGczypoprawne = RTGczypoprawne;
    }

    public String getRTGopis() {
        return RTGopis;
    }

    public void setRTGopis(String RTGopis) {
        this.RTGopis = RTGopis;
    }

    public boolean isEKGczypoprawne() {
        return EKGczypoprawne;
    }

    public void setEKGczypoprawne(boolean EKGczypoprawne) {
        this.EKGczypoprawne = EKGczypoprawne;
    }

    public String getEKGopis() {
        return EKGopis;
    }

    public void setEKGopis(String EKGopis) {
        this.EKGopis = EKGopis;
    }

    public boolean isUSGczypoprawne() {
        return USGczypoprawne;
    }

    public void setUSGczypoprawne(boolean USGczypoprawne) {
        this.USGczypoprawne = USGczypoprawne;
    }

    public String getUSGopis() {
        return USGopis;
    }

    public void setUSGopis(String USGopis) {
        this.USGopis = USGopis;
    }
}
