package com.example.ex_1;

public class ZaprosF {              // класс singelton

    private static ZaprosF instance;

    private String userIDEnter;            // ID пользоваделя
    private String adminOrUser;

    private String URL_User_Id = "http://109.251.148.67:8046/serv_ex1_bd_war_exploded/userID";
    private String URL_ADMIN_OR_USER = "http://109.251.148.67:8046/serv_ex1_bd_war_exploded/adminOrUser";
    private String URL_TRENER_ALL = "http://109.251.148.67:8046/serv_ex1_bd_war_exploded/trener";
    private String URL_SHOP_ALL = "http://109.251.148.67:8046/serv_ex1_bd_war_exploded/shop";
    private String URL_Lenta_News = "http://109.251.148.67:8046/serv_ex1_bd_war_exploded/lentaNews";
    private String URL_STUDENT = "http://109.251.148.67:8046/serv_ex1_bd_war_exploded/student";



    private String URL_DELETE_NEWS = "http://109.251.148.67:8046/serv_ex1_bd_war_exploded/deleteNews";  // удалить


    private ZaprosF() {
    }

    public String getURL_SHOP_ALL() {
        return URL_SHOP_ALL;
    }

    public String getURL_TRENER_ALL() {
        return URL_TRENER_ALL;
    }

    public String getURL_DELETE_NEWS() {
        return URL_DELETE_NEWS;
    }

    public String getURL_ADMIN_OR_USER() {
        return URL_ADMIN_OR_USER;
    }

    public void setURL_ADMIN_OR_USER(String URL_ADMIN_OR_USER) {
        this.URL_ADMIN_OR_USER = URL_ADMIN_OR_USER;
    }

    public String getURL_User_Id() {
        return URL_User_Id;
    }

    public String getURL_Lenta_News() {
        return URL_Lenta_News;
    }

    public String getAdminOrUser() {
        return adminOrUser;
    }

    public void setAdminOrUser(String adminOrUser) {
        this.adminOrUser = adminOrUser;
    }

    public static ZaprosF getInstance() {
        if (instance == null) {
            instance = new ZaprosF();
        }
        return instance;
    }

    public String getUserIDEnter() {
        return userIDEnter;
    }

    public void setUserIDEnter(String userIDEnter) {
        this.userIDEnter = userIDEnter;
    }

    public String getURL_STUDENT() {
        return URL_STUDENT;
    }
}
