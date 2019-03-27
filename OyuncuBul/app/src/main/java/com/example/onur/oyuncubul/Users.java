package com.example.onur.oyuncubul;

/**
 * Created by Onur on 9.03.2018.
 */

public class Users {

    private String userIsım;
    private String userYas;
    private String userTel;

    public Users() {

    }

    public Users(String userIsım, String userYas, String userTel) {

        this.userIsım = userIsım;
        this.userYas = userYas;
        this.userTel = userTel;
    }



    public String getUserIsım() {
        return userIsım;
    }

    public void setUserIsım(String userIsım) {
        this.userIsım = userIsım;
    }

    public String getUserYas() {
        return userYas;
    }

    public void setUserYas(String userYas) {
        this.userYas = userYas;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
}



   /* public String getUserIsım(){
        return userIsım;
    }

    public String getUserYas(){
        return userYas;
    }

    public String getUserTel() {
        return userTel;
    }*/
