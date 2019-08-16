package com.example.ex_1.Entity;


public class TrenierEntity {
    private String idtrainers;
    private String nameTener;
    private String dateObirth;
    private String aboutTrener;
    private String grafikZanjatiy;
    private String urlFotoTrener;
    private String adminOrUser;
    private String idEnterUser;

    public TrenierEntity(String idtrainers, String nameTener, String dateObirth, String aboutTrener, String grafikZanjatiy, String urlFotoTrener, String adminOrUser, String idEnterUser) {
        this.idtrainers = idtrainers;
        this.nameTener = nameTener;
        this.dateObirth = dateObirth;
        this.aboutTrener = aboutTrener;
        this.grafikZanjatiy = grafikZanjatiy;
        this.urlFotoTrener = urlFotoTrener;
        this.adminOrUser = adminOrUser;
        this.idEnterUser = idEnterUser;

    }

    public TrenierEntity() {
    }

    public String getIdtrainers() {
        return idtrainers;
    }

    public void setIdtrainers(String idtrainers) {
        this.idtrainers = idtrainers;
    }

    public String getNameTener() {
        return nameTener;
    }

    public void setNameTener(String nameTener) {
        this.nameTener = nameTener;
    }

    public String getDateObirth() {
        return dateObirth;
    }

    public void setDateObirth(String dateObirth) {
        this.dateObirth = dateObirth;
    }

    public String getAboutTrener() {
        return aboutTrener;
    }

    public void setAboutTrener(String aboutTrener) {
        this.aboutTrener = aboutTrener;
    }

    public String getGrafikZanjatiy() {
        return grafikZanjatiy;
    }

    public void setGrafikZanjatiy(String grafikZanjatiy) {
        this.grafikZanjatiy = grafikZanjatiy;
    }

    public String getUrlFotoTrener() {
        return urlFotoTrener;
    }

    public void setUrlFotoTrener(String urlFotoTrener) {
        this.urlFotoTrener = urlFotoTrener;
    }

    public String getAdminOrUser() {
        return adminOrUser;
    }

    public void setAdminOrUser(String adminOrUser) {
        this.adminOrUser = adminOrUser;
    }

    public String getIdEnterUser() {
        return idEnterUser;
    }

    public void setIdEnterUser(String idEnterUser) {
        this.idEnterUser = idEnterUser;
    }
}
