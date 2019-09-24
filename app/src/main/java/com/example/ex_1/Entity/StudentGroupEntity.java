package com.example.ex_1.Entity;

public class StudentGroupEntity {

    String idGroupBD;
    String idGroup;
    String idTrenerEnter;
    String nameGroup;
    String nameTrener;

    public StudentGroupEntity() {
    }

    public StudentGroupEntity(String idGroupBD, String idGroup, String idTrenerEnter, String nameGroup, String nameTrener) {
        this.idGroupBD = idGroupBD;
        this.idGroup = idGroup;
        this.idTrenerEnter = idTrenerEnter;
        this.nameGroup = nameGroup;
        this.nameTrener = nameTrener;
    }

    public String getIdGroupBD() {
        return idGroupBD;
    }

    public void setIdGroupBD(String idGroupBD) {
        this.idGroupBD = idGroupBD;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public String getIdTrenerEnter() {
        return idTrenerEnter;
    }

    public void setIdTrenerEnter(String idTrenerEnter) {
        this.idTrenerEnter = idTrenerEnter;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getNameTrener() {
        return nameTrener;
    }

    public void setNameTrener(String nameTrener) {
        this.nameTrener = nameTrener;
    }
}
