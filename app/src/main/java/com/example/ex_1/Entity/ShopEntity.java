package com.example.ex_1.Entity;

public class ShopEntity {
    private String idshop;
    private String nameShop;
    private String sizeShop;
    private String priseShop;
    private String urlFotoShop;
    private String abautShop;

    public ShopEntity() {
    }

    public ShopEntity(String idshop, String nameShop, String sizeShop, String priseShop, String urlFotoShop, String abautShop) {
        this.idshop = idshop;
        this.nameShop = nameShop;
        this.sizeShop = sizeShop;
        this.priseShop = priseShop;
        this.urlFotoShop = urlFotoShop;
        this.abautShop = abautShop;
    }

    public String getAbautShop() {
        return abautShop;
    }

    public void setAbautShop(String abautShop) {
        this.abautShop = abautShop;
    }

    public String getIdshop() {
        return idshop;
    }

    public void setIdshop(String idshop) {
        this.idshop = idshop;
    }

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public String getSizeShop() {
        return sizeShop;
    }

    public void setSizeShop(String sizeShop) {
        this.sizeShop = sizeShop;
    }

    public String getPriseShop() {
        return priseShop;
    }

    public void setPriseShop(String priseShop) {
        this.priseShop = priseShop;
    }

    public String getUrlFotoShop() {
        return urlFotoShop;
    }

    public void setUrlFotoShop(String urlFotoShop) {
        this.urlFotoShop = urlFotoShop;
    }

}
