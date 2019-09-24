package com.example.ex_1.Entity;

public class SobutieEntity {
    private String idBDSobutie;
    private String textSobutie;
    private String dataSobutie;
    private String idUserYas;

    public SobutieEntity() {
    }

    public SobutieEntity(String idBDSobutie, String textSobutie, String dataSobutie) {
        this.idBDSobutie = idBDSobutie;
        this.textSobutie = textSobutie;
        this.dataSobutie = dataSobutie;
    }

    public SobutieEntity(String idBDSobutie, String textSobutie, String dataSobutie, String idUserYas) {
        this.idBDSobutie = idBDSobutie;
        this.textSobutie = textSobutie;
        this.dataSobutie = dataSobutie;
        this.idUserYas = idUserYas;
    }

    public String getIdBDSobutie() {
        return idBDSobutie;
    }

    public void setIdBDSobutie(String idBDSobutie) {
        this.idBDSobutie = idBDSobutie;
    }

    public String getIdUserYas() {
        return idUserYas;
    }

    public void setIdUserYas(String idUserYas) {
        this.idUserYas = idUserYas;
    }

    public String getTextSobutie() {
        return textSobutie;
    }

    public void setTextSobutie(String textSobutie) {
        this.textSobutie = textSobutie;
    }

    public String getDataSobutie() {
        return dataSobutie;
    }

    public void setDataSobutie(String dataSobutie) {
        this.dataSobutie = dataSobutie;
    }
}
