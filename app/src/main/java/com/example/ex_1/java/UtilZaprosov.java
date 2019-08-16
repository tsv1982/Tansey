package com.example.ex_1.java;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.ex_1.ZaprosF;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UtilZaprosov {       // проверка пользователя

    private boolean userFalseOrTrue; // ответ сервера прошел ли User проверку
    private ZaprosF zaprosF = ZaprosF.getInstance();

    public boolean checkUser(String userId) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("nameId", userId)    // отправляет запрос с именем nameId и введенными данными
                .build();
        Request request = new Request.Builder()
                .url(zaprosF.getURL_User_Id())
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            userFalseOrTrue = Boolean.parseBoolean(response.body().string());
        } catch (Exception e) {
        }
//        return userFalseOrTrue;
        return true;
    }

    public void adminOrUser(String userOrAdmin) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("nameIdAdminOrUser", userOrAdmin)    // отправляет запрос с именем nameId и введенными данными
                .build();
        Request request = new Request.Builder()
                .url(zaprosF.getURL_ADMIN_OR_USER())
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            zaprosF.setAdminOrUser((response.body().string()));
        } catch (Exception e) {
        }

    }

    public boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
//            Toast.makeText(context, "интернет есть WiFi", Toast.LENGTH_LONG).show();
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
//            Toast.makeText(context, "интернет есть Mo,", Toast.LENGTH_LONG).show();
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
//            Toast.makeText(context, "Интернете нет", Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(context, "Интернете нет", Toast.LENGTH_LONG).show();
        return false;
    }

}







