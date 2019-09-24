package com.example.ex_1.java;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class PostMultipart {
    /**
     * The imgur client ID for OkHttp recipes. If you're using imgur for anything other than running
     * these examples, please request your own client ID! https://api.imgur.com/oauth2
     */
    private static final String IMGUR_CLIENT_ID = "9199fdef135c122";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.get("image/png");

    private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("image", "logo-square.png",
                        RequestBody.create(
                                new File("/home/tsv/AndroidStudioProjects/ex_1/app/src/main/java/com/example/ex_1/image/pic.jpg"),
                                MEDIA_TYPE_PNG))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("http://109.251.148.67:8046/serv_ex1_bd_war_exploded/trener")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());

    }

    public static void main(String... args) throws Exception {
        new PostMultipart().run();
    }
}