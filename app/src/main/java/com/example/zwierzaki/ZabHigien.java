package com.example.zwierzaki;

import java.util.Date;

public class ZabHigien {
     String uidzh;
     Date datezh;
     boolean c_zebow;
     boolean kleszcz;
     boolean c_uszu;
     boolean strzyzenie;
     boolean usuw_kamienia;
     String inne;

    public ZabHigien() {
        c_zebow=false;
        kleszcz=false;
        c_uszu=false;
        strzyzenie=false;
        usuw_kamienia=false;
    }

    public ZabHigien(String uidzh, Date datezh, boolean c_zebow, boolean kleszcz, boolean c_uszu, boolean strzyzenie, boolean usuw_kamienia, String inne) {
        this.uidzh = uidzh;
        this.datezh = datezh;
        this.c_zebow = c_zebow;
        this.kleszcz = kleszcz;
        this.c_uszu = c_uszu;
        this.strzyzenie = strzyzenie;
        this.usuw_kamienia = usuw_kamienia;
        this.inne = inne;
    }

    public String getUidzh() {
        return uidzh;
    }

    public void setUidzh(String uidzh) {
        this.uidzh = uidzh;
    }

    public Date getDatezh() {
        return datezh;
    }

    public void setDatezh(Date datezh) {
        this.datezh = datezh;
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
}