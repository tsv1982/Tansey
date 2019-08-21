package com.example.ex_1.Entity;

public class DateEntity {
    int date;
    int monts;
    int year;

    public DateEntity() {
    }

    public DateEntity(int date, int monts, int year) {
        this.date = date;
        this.monts = monts;
        this.year = year;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonts() {
        return monts;
    }

    public void setMonts(int monts) {
        this.monts = monts;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
