package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ex_1.Entity.LentaNewsEntity;
import com.example.ex_1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LentaAddNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private LentaNewsEntity lentaNewsEntity = new LentaNewsEntity();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("LentaNews");
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    private EditText nameNews;
    private EditText text;
    private EditText authorNews;

    private ImageView imageViewFotoNews1;
    private ImageView imageViewFotoNews2;
    private ImageView imageViewFotoNews3;
    private ImageView imageViewFotoNews4;
    private ImageView imageViewFotoNews5;

    private Button btnAddTrener;
    private Button btnGetFotoTrener1;

    private ArrayList<String> arrayListPaht = new ArrayList<>();
    private ArrayList<String> arrayListUrl = new ArrayList<>();

    private int count = 0;
    private volatile int ii = 1;
    private String s1ListFotoUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lenta_news_add_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        btnAddTrener = findViewById(R.id.btn_ADD_Lenta_News11);
        btnAddTrener.setOnClickListener(this);

        btnGetFotoTrener1 = findViewById(R.id.btn_getPahsFotoLentaNews);
        btnGetFotoTrener1.setOnClickListener(this);

        imageViewFotoNews1 = findViewById(R.id.IV_Lenta_News_image_Add_1);
        imageViewFotoNews2 = findViewById(R.id.IV_Lenta_News_image_Add_2);
        imageViewFotoNews3 = findViewById(R.id.IV_Lenta_News_image_Add_3);
        imageViewFotoNews4 = findViewById(R.id.IV_Lenta_News_image_Add_4);
        imageViewFotoNews5 = findViewById(R.id.IV_Lenta_News_image_Add_5);

        nameNews = findViewById(R.id.ET_Lenta_News_Name1);
        text = findViewById(R.id.ET_Text_Lenta_News1);
        authorNews = findViewById(R.id.ET_Autor_Lenta_News1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            if (count == 0) {
                arrayListPaht.add(picturePath);
                File imgFile = new File(arrayListPaht.get(0));
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageViewFotoNews1.setImageBitmap(myBitmap);
                count++;
            } else {
                if (count == 1) {
                    arrayListPaht.add(picturePath);
                    File imgFile = new File(arrayListPaht.get(1));
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageViewFotoNews2.setImageBitmap(myBitmap);
                    count++;
                } else {
                    if (count == 2) {
                        arrayListPaht.add(picturePath);
                        File imgFile = new File(arrayListPaht.get(2));
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imageViewFotoNews3.setImageBitmap(myBitmap);
                        count++;
                    } else {
                        if (count == 3) {
                            arrayListPaht.add(picturePath);
                            File imgFile = new File(arrayListPaht.get(3));
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            imageViewFotoNews4.setImageBitmap(myBitmap);
                            count++;
                        } else {
                            if (count == 4) {
                                arrayListPaht.add(picturePath);
                                File imgFile = new File(arrayListPaht.get(4));
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imageViewFotoNews5.setImageBitmap(myBitmap);
                                count++;
                            } else {
                                Toast.makeText(this, "лимит 5 изображений", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
            cursor.close();
        }
    }

    private void addJson(LentaNewsEntity lentaNewsEntity) {   //  c EDIT TEXT парсим JSON

        String jsonAdd = new Gson().toJson(new LentaNewsEntity(
                lentaNewsEntity.getIdlentaNews(),
                lentaNewsEntity.getNameNews(),
                lentaNewsEntity.getData(),
                lentaNewsEntity.getTime(),
                lentaNewsEntity.getText(),
                lentaNewsEntity.getAuthorNews(),
                lentaNewsEntity.getUrlPictureNews1(),
                lentaNewsEntity.getUrlPictureNews2(),
                lentaNewsEntity.getUrlPictureNews3(),
                lentaNewsEntity.getUrlPictureNews4(),
                lentaNewsEntity.getUrlPictureNews5()));

        databaseReference.push().setValue(jsonAdd);
    }

    private void addFoto(String s, String nameF) {
        File destinationDirectory = new File(this.getCacheDir().getAbsolutePath());
        String filePath = SiliCompressor.with(this).compress(s, destinationDirectory);
        Uri file = Uri.fromFile(new File(filePath));
        StorageReference riversRef = mStorageRef.child("LentaNewsFoto/" + nameNews.getText() + nameF + ".jpg");
        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                arrayListUrl.add(String.valueOf(uri));
                                if (ii == arrayListPaht.size()) {
                                    for (int i = 0; i < arrayListUrl.size(); i++) {
                                        s1ListFotoUrl = s1ListFotoUrl + "," + arrayListUrl.get(i);
                                    }
                                    lentaNewsEntity.setUrlPictureNews1(s1ListFotoUrl);
                                    addJson(lentaNewsEntity);
                                }
                                ii++;
                            }

                        });
                        finish();
                        return;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void slep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ADD_Lenta_News11: {

                if (arrayListPaht.size() == 0) {
                    Toast.makeText(view.getContext(), "выберите изображение", Toast.LENGTH_LONG).show();
                } else {

                    SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy  HH:mm");
                    String currentDateandTime = sdf.format(new Date());

                    lentaNewsEntity.setIdlentaNews("255");
                    lentaNewsEntity.setNameNews(String.valueOf(nameNews.getText()));
                    lentaNewsEntity.setData(currentDateandTime);
                    lentaNewsEntity.setTime("111");
                    lentaNewsEntity.setText(String.valueOf(text.getText()));
                    lentaNewsEntity.setAuthorNews(String.valueOf(authorNews.getText()));

                    for (int i = 0; i < arrayListPaht.size(); i++) {
                        addFoto(arrayListPaht.get(i), String.valueOf(i));
                        slep();
                    }

                    Toast.makeText(view.getContext(), "добавлен тренер \n" + nameNews.getText(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, LentaNewsActivity.class);
                    finish();
                    startActivity(intent);
                }
                break;
            }
            case R.id.btn_getPahsFotoLentaNews: {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 101);
                break;
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {   // для удаления фокуса при клике на пустое место
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}