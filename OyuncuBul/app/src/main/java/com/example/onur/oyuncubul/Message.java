package com.example.onur.oyuncubul;

/**
 * Created by Onur on 23.03.2018.
 */

public class Message {
    String messageId;
    String messageId2;
    String messageId3;
    String messageIsım;
    String messageYas;
    String messageTel;
    String messageOnay;


    public Message(){

    }

    public Message(String messageId,String messageId2,String messageId3,String messageIsım,String messageYas,String messageTel,String messageOnay){
        this.messageId=messageId;
        this.messageId2=messageId2;
        this.messageId3=messageId3;
        this.messageIsım=messageIsım;
        this.messageYas=messageYas;
        this.messageTel=messageTel;
        this.messageOnay=messageOnay;

    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessageId2() {
        return messageId2;
    }

    public String getMessageId3() {
        return messageId3;
    }

    public String getMessageIsım() {
        return messageIsım;
    }

    public String getMessageYas() {
        return messageYas;
    }

    public String getMessageTel() {
        return messageTel;
    }

    public String getMessageOnay() {
        return messageOnay;
    }
}


