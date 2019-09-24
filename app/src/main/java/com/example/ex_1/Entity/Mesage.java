package com.example.ex_1.Entity;

// TODO: 17.07.19 удалить класс 

public class Mesage {
    String keyMessage;
    String date;
    String text;

    public Mesage(String name, String date, String text) {
        keyMessage = name;
        this.date = date;
        this.text = text;
    }

    public String getName() {
        return keyMessage;
    }

    public void setName(String name) {
        keyMessage = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getNameMesage() {
        return keyMessage;
    }

    public String getTextMesage() {
        return text;
    }

    public String getDateMesage() {
        return date;
    }
}
