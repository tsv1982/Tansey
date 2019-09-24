package com.example.ex_1.Entity;

public class StudentСardEntity {
    private String idstudent;
    private String idEnterStudent;
    private String userOrAdmin;
    private String nameStudent;
    private String groupName;
    private String ves;
    private String titul;
    private String date;
    private String seson;
    private String turnir;
    private String boiov;
    private String pobed;
    private String poragenii;
    private String ochkov;
    private String pressNorma;
    private String pressFact;
    private String otgimaniyNorma;
    private String otgimaniyFact;
    private String podtjagNorma;
    private String podtjagFact;
    private String roznogkaNorma;
    private String roznogkaFact;
    private String prugkiNorma;
    private String prugkiFact;
    private String urlStudentFotoCard;
    private String reiting;

    private String dataPosicheniy;
    private boolean isChecket;

    public StudentСardEntity() {
    }

    public StudentСardEntity(String idstudent, String idEnterStudent, String userOrAdmin, String nameStudent, String groupName, String ves, String titul, String date, String seson, String turnir, String boiov, String pobed, String poragenii, String ochkov, String pressNorma, String pressFact, String otgimaniyNorma, String otgimaniyFact, String podtjagNorma, String podtjagFact, String roznogkaNorma, String roznogkaFact, String prugkiNorma, String prugkiFact, String urlStudentFotoCard, String reiting) {
        this.idstudent = idstudent;
        this.idEnterStudent = idEnterStudent;
        this.userOrAdmin = userOrAdmin;
        this.nameStudent = nameStudent;
        this.groupName = groupName;
        this.ves = ves;
        this.titul = titul;
        this.date = date;
        this.seson = seson;
        this.turnir = turnir;
        this.boiov = boiov;
        this.pobed = pobed;
        this.poragenii = poragenii;
        this.ochkov = ochkov;
        this.pressNorma = pressNorma;
        this.pressFact = pressFact;
        this.otgimaniyNorma = otgimaniyNorma;
        this.otgimaniyFact = otgimaniyFact;
        this.podtjagNorma = podtjagNorma;
        this.podtjagFact = podtjagFact;
        this.roznogkaNorma = roznogkaNorma;
        this.roznogkaFact = roznogkaFact;
        this.prugkiNorma = prugkiNorma;
        this.prugkiFact = prugkiFact;
        this.urlStudentFotoCard = urlStudentFotoCard;
        this.reiting = reiting;
    }

    public StudentСardEntity(String idstudent, String idEnterStudent, String userOrAdmin,
                             String nameStudent, String groupName, String ves, String titul,
                             String date, String seson, String turnir, String boiov, String pobed,
                             String poragenii, String ochkov, String pressNorma, String pressFact,
                             String otgimaniyNorma, String otgimaniyFact, String podtjagNorma,
                             String podtjagFact, String roznogkaNorma, String roznogkaFact,
                             String prugkiNorma, String prugkiFact, String urlStudentFotoCard,
                             String reiting, String dataPosicheniy) {
        this.idstudent = idstudent;
        this.idEnterStudent = idEnterStudent;
        this.userOrAdmin = userOrAdmin;
        this.nameStudent = nameStudent;
        this.groupName = groupName;
        this.ves = ves;
        this.titul = titul;
        this.date = date;
        this.seson = seson;
        this.turnir = turnir;
        this.boiov = boiov;
        this.pobed = pobed;
        this.poragenii = poragenii;
        this.ochkov = ochkov;
        this.pressNorma = pressNorma;
        this.pressFact = pressFact;
        this.otgimaniyNorma = otgimaniyNorma;
        this.otgimaniyFact = otgimaniyFact;
        this.podtjagNorma = podtjagNorma;
        this.podtjagFact = podtjagFact;
        this.roznogkaNorma = roznogkaNorma;
        this.roznogkaFact = roznogkaFact;
        this.prugkiNorma = prugkiNorma;
        this.prugkiFact = prugkiFact;
        this.urlStudentFotoCard = urlStudentFotoCard;
        this.reiting = reiting;
        this.dataPosicheniy = dataPosicheniy;
    }


    public boolean getIsChecket() {
        return isChecket;
    }

    public void setChecket(boolean checket) {
        isChecket = checket;
    }

    public String getDataPosicheniy() {
        return dataPosicheniy;
    }

    public void setDataPosicheniy(String dataPosicheniy) {
        this.dataPosicheniy = dataPosicheniy;
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

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getTitul() {
        return titul;
    }

    public void setTitul(String titul) {
        this.titul = titul;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeson() {
        return seson;
    }

    public void setSeson(String seson) {
        this.seson = seson;
    }

    public String getTurnir() {
        return turnir;
    }

    public void setTurnir(String turnir) {
        this.turnir = turnir;
    }

    public String getBoiov() {
        return boiov;
    }

    public void setBoiov(String boiov) {
        this.boiov = boiov;
    }

    public String getPobed() {
        return pobed;
    }

    public void setPobed(String pobed) {
        this.pobed = pobed;
    }

    public String getPoragenii() {
        return poragenii;
    }

    public void setPoragenii(String poragenii) {
        this.poragenii = poragenii;
    }

    public String getOchkov() {
        return ochkov;
    }

    public void setOchkov(String ochkov) {
        this.ochkov = ochkov;
    }

    public String getPressNorma() {
        return pressNorma;
    }

    public void setPressNorma(String pressNorma) {
        this.pressNorma = pressNorma;
    }

    public String getPressFact() {
        return pressFact;
    }

    public void setPressFact(String pressFact) {
        this.pressFact = pressFact;
    }

    public String getOtgimaniyNorma() {
        return otgimaniyNorma;
    }

    public void setOtgimaniyNorma(String otgimaniyNorma) {
        this.otgimaniyNorma = otgimaniyNorma;
    }

    public String getOtgimaniyFact() {
        return otgimaniyFact;
    }

    public void setOtgimaniyFact(String otgimaniyFact) {
        this.otgimaniyFact = otgimaniyFact;
    }

    public String getPodtjagNorma() {
        return podtjagNorma;
    }

    public void setPodtjagNorma(String podtjagNorma) {
        this.podtjagNorma = podtjagNorma;
    }

    public String getPodtjagFact() {
        return podtjagFact;
    }

    public void setPodtjagFact(String podtjagFact) {
        this.podtjagFact = podtjagFact;
    }

    public String getRoznogkaNorma() {
        return roznogkaNorma;
    }

    public void setRoznogkaNorma(String roznogkaNorma) {
        this.roznogkaNorma = roznogkaNorma;
    }

    public String getRoznogkaFact() {
        return roznogkaFact;
    }

    public void setRoznogkaFact(String roznogkaFact) {
        this.roznogkaFact = roznogkaFact;
    }

    public String getPrugkiNorma() {
        return prugkiNorma;
    }

    public void setPrugkiNorma(String prugkiNorma) {
        this.prugkiNorma = prugkiNorma;
    }

    public String getPrugkiFact() {
        return prugkiFact;
    }

    public void setPrugkiFact(String prugkiFact) {
        this.prugkiFact = prugkiFact;
    }

    public String getUrlStudentFotoCard() {
        return urlStudentFotoCard;
    }

    public void setUrlStudentFotoCard(String urlStudentFotoCard) {
        this.urlStudentFotoCard = urlStudentFotoCard;
    }

    public String getReiting() {
        return reiting;
    }

    public void setReiting(String reiting) {
        this.reiting = reiting;
    }
}
