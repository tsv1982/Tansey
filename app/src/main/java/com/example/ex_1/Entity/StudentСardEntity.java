package com.example.ex_1.Entity;

public class StudentСardEntity {
    private String idstudent;
    private String idEnterStudent;
    private String userOrAdmin;
    private String nameStudent;
    private String groupName;
    private String rang;
    private String date;
    private String dost1;
    private String dost2;
    private String dost3;
    private String posechenie;
    private String urlStudentFotoCard;

    public StudentСardEntity() {
    }

    public StudentСardEntity(String idstudent, String idEnterStudent, String userOrAdmin, String nameStudent, String groupName, String rang, String date, String dost1, String dost2, String dost3, String posechenie, String urlStudentFotoCard) {
        this.idstudent = idstudent;
        this.idEnterStudent = idEnterStudent;
        this.userOrAdmin = userOrAdmin;
        this.nameStudent = nameStudent;
        this.groupName = groupName;
        this.rang = rang;
        this.date = date;
        this.dost1 = dost1;
        this.dost2 = dost2;
        this.dost3 = dost3;
        this.posechenie = posechenie;
        this.urlStudentFotoCard = urlStudentFotoCard;
    }

    public String getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(String idstudent) {
        this.idstudent = idstudent;
    }

    public String getIdEnterStudent() {
        return idEnterStudent;
    }

    public void setIdEnterStudent(String idEnterStudent) {
        this.idEnterStudent = idEnterStudent;
    }

    public String getUserOrAdmin() {
        return userOrAdmin;
    }

    public void setUserOrAdmin(String userOrAdmin) {
        this.userOrAdmin = userOrAdmin;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDost1() {
        return dost1;
    }

    public void setDost1(String dost1) {
        this.dost1 = dost1;
    }

    public String getDost2() {
        return dost2;
    }

    public void setDost2(String dost2) {
        this.dost2 = dost2;
    }

    public String getDost3() {
        return dost3;
    }

    public void setDost3(String dost3) {
        this.dost3 = dost3;
    }

    public String getPosechenie() {
        return posechenie;
    }

    public void setPosechenie(String posechenie) {
        this.posechenie = posechenie;
    }

    public String getUrlStudentFotoCard() {
        return urlStudentFotoCard;
    }

    public void setUrlStudentFotoCard(String urlStudentFotoCard) {
        this.urlStudentFotoCard = urlStudentFotoCard;
    }
}
