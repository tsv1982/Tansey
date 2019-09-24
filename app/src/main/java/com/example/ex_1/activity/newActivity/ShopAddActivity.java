package com.example.ex_1.activity.newActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.ShopEntity;
import com.example.ex_1.R;
import com.example.ex_1.ZaprosF;
import com.example.ex_1.activity.ShopActivity1;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ShopAddActivity extends AppCompatActivity implements View.OnClickListener {

    ShopEntity shopEntity;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("shop");

    private StorageReference mStorageRef;

    EditText editTextIDShop;
    EditText editTextNameShop;
    EditText editTextSizeShop;
    EditText editTextPriceShop;
    EditText editTextURLPictureShop;
    EditText editTextAbautShop;


    Button btnAddShop;
    Button btnOpenGAlery;
    TextView XXXXX;
    String filePut;

    int waitAdd = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        editTextIDShop = findViewById(R.id.ET_ID_Shop);
        editTextNameShop = findViewById(R.id.ET_Shop_Name);
        editTextSizeShop = findViewById(R.id.ET_Shop_Size);
        editTextPriceShop = findViewById(R.id.ET_Shop_Price);
        editTextURLPictureShop = findViewById(R.id.ET_Url_Shop);
        editTextAbautShop = findViewById(R.id.ET_abaut_Shop);

        btnAddShop = findViewById(R.id.btn_ADD_Shop);
        btnAddShop.setOnClickListener(this);

        XXXXX = findViewById(R.id.XXXXXX_Shop);

        btnOpenGAlery = findViewById(R.id.btn_xxxxxx_Shop);
        btnOpenGAlery.setOnClickListener(this);


    }

//    private void addJson(ShopEntity trenierEntity) {   //  c EDIT TEXT парсим JSON
//        String jsonAdd = new Gson().toJson(new ShopEntity(
//
//                trenierEntity.getIdtrainers(),
//                trenierEntity.getNameTener(),
//                trenierEntity.getDateObirth(),
//                trenierEntity.getAboutTrener(),
//                trenierEntity.getGrafikZanjatiy(),
//                trenierEntity.getUrlFotoTrener(),
//                trenierEntity.getAdminOrUser(),
//                trenierEntity.getIdEnterUser()));
//        databaseReference.push().setValue(jsonAdd);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 101 && resultCode == RESULT_OK && null != data) {
//
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            XXXXX.setText(picturePath);
//            System.out.println("FFFFFFFFFFFFF    " + picturePath);
//            filePut = picturePath;
//            cursor.close();
//        }
//    }
//
    @Override
    public void onClick(View view) {
//
//        switch (view.getId()) {
//            case R.id.btn_ADD_Shop: {
//                shopEntity = new ShopEntity();
//
//                shopEntity.setIdshop(String.valueOf(editTextIDShop.getText()));
//
//
//                shopEntity.setNameShop(String.valueOf(editTextNameShop.getText()));
//
//
//                shopEntity.setSizeShop(String.valueOf(editTextPriceShop.getText()));
//
//
//                shopEntity.setPriseShop(String.valueOf(editTextPriceShop.getText()));
//
//
//                shopEntity.setUrlFotoShop(String.valueOf(editTextURLPictureShop.getText()));
//
//                shopEntity.setAbautShop(String.valueOf(editTextAbautShop.getText()));
//
//
//                loadAsyncTaskAdd();
//
//
//                while (waitAdd == 1) {
//                }
//
//                Toast.makeText(view.getContext(), "добавлен товар \n" + editTextNameShop.getText(), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(this, ShopActivity1.class);
//                startActivity(intent);
//                finish();
//                break;
//            }
//
//            case R.id.btn_xxxxxx_Shop: {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent, 101);
//                break;
//            }
//        }
//
    }

//    private void addJson(ShopEntity shopEntity) {   //  c EDIT TEXT парсим JSON
//
//        String jsonAdd = new Gson().toJson(new ShopEntity(
//                shopEntity.getIdshop(),
//                shopEntity.getNameShop(),
//                shopEntity.getSizeShop(),
//                shopEntity.getPriseShop(),
//                shopEntity.getUrlFotoShop(),
//                shopEntity.getAbautShop()));
//
//        conneckToServerADD(jsonAdd);
//    }
//
//    private void conneckToServerADD(String jsonAdd) {   // делает запрос на сервер для add trenera
//        waitAdd = 1;
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        OkHttpClient client = new OkHttpClient();
//
//        RequestBody body = RequestBody.create(JSON, jsonAdd);
//        Request request = new Request.Builder()
//                .addHeader("AddShop", "dskjdskdfs")
//                .url(ZaprosF.getInstance().getURL_SHOP_ALL())
//                .post(body)
//                .build();
//        try {
//            client.newCall(request).execute();
//            waitAdd = 2;
//        } catch (Exception e) {
//        }
//    }
//
//    private void loadAsyncTaskAdd() {              // assyngron адача для удаления
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                addJson(shopEntity);
//                return null;                                   // передаем id тренера для удаления
//            }
//        }.execute();
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {   // для удаления фокуса при клике на пустое место
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (v instanceof EditText) {
//                v.clearFocus();
//                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}

