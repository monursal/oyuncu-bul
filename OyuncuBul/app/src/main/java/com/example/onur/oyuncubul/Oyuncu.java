package com.example.onur.oyuncubul;

import android.os.Bundle;

/**
 * Created by Onur on 1.02.2018.
 */


public class Oyuncu {

    String oyunId;
    String oyunId2;
    String oyunIsım;
    String oyunMevki;
    String oyunYas;
    String oyunSaha;
    String oyunSaat;
    String oyunImage;
    String oyunTel;

    public Oyuncu() {

    }

    public Oyuncu(String oyunId,String oyunId2,String oyunIsım, String oyunMevki, String oyunYas, String oyunSaha, String oyunSaat,String oyunImage,String oyunTel) {
        this.oyunId = oyunId;
        this.oyunId2=oyunId2;
        this.oyunIsım = oyunIsım;
        this.oyunMevki = oyunMevki;
        this.oyunYas = oyunYas;
        this.oyunSaha = oyunSaha;
        this.oyunSaat = oyunSaat;
        this.oyunImage = oyunImage;
        this.oyunTel = oyunTel;
    }

    public String getOyunTel() {
        return oyunTel;
    }

    public String getOyunImage() {
        return oyunImage;
    }

    public String getOyunId() {
        return oyunId;
    }

    public String getOyunId2() {
        return oyunId2;
    }

    public String getOyunIsım() {
        return oyunIsım;
    }

    public String getOyunMevki() {
        return oyunMevki;
    }

    public String getOyunYas() {
        return oyunYas;
    }

    public String getOyunSaha() {
        return oyunSaha;
    }

    public String getOyunSaat() {
        return oyunSaat;
    }


}

