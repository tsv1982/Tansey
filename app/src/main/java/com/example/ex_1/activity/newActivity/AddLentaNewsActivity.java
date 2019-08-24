package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.LentaNewsEntity;
import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.example.ex_1.ZaprosF;
import com.example.ex_1.activity.newActivity.TrenerActivity;
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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class AddLentaNewsActivity extends AppCompatActivity implements View.OnClickListener {

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


    private String[] filePut = {"", "", "", "", ""};
    private String[] photoLink = {"", "", "", "", ""};
    int aa = 0;
    int wait = 0;


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


            if (filePut[0].equals("")) {
                filePut[0] = picturePath;
                File imgFile = new File(filePut[0]);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageViewFotoNews1.setImageBitmap(myBitmap);
            } else {
                if (filePut[1].equals("")) {
                    filePut[1] = picturePath;
                    File imgFile = new File(filePut[1]);
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageViewFotoNews2.setImageBitmap(myBitmap);
                } else {
                    if (filePut[2].equals("")) {
                        filePut[2] = picturePath;
                        File imgFile = new File(filePut[2]);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imageViewFotoNews3.setImageBitmap(myBitmap);
                    } else {
                        if (filePut[3].equals("")) {
                            filePut[3] = picturePath;
                            File imgFile = new File(filePut[3]);
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            imageViewFotoNews4.setImageBitmap(myBitmap);
                        } else {
                            if (filePut[4].equals("")) {
                                filePut[4] = picturePath;
                                File imgFile = new File(filePut[4]);
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imageViewFotoNews5.setImageBitmap(myBitmap);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ADD_Lenta_News11: {

                if (filePut[0].equals("")) {
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

                    for (int i = 0; i < 5; i++) {

                        if (!filePut[i].equals("")) {
                            wait++;
                            File destinationDirectory = new File(this.getCacheDir().getAbsolutePath());
                            String filePath = SiliCompressor.with(this).compress(filePut[i], destinationDirectory);

                            Uri file = Uri.fromFile(new File(filePath));
                            StorageReference riversRef = mStorageRef.child("LentaNewsFoto/" + nameNews.getText() + i + ".jpg");

                            riversRef.putFile(file)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    System.out.println("kkkkkkkkkkyyyyyy  " + uri);

                                                    photoLink[aa] = uri.toString();
                                                    System.out.println("sdfsdf  " + photoLink[aa]);

                                                    aa++;


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
                    }

                    while (wait == aa) {

                    }

                    System.out.println("ggggggggggggggggggg  " + photoLink[0]);
                    System.out.println("ggggggggggggggggggg  " + photoLink[1]);
                    System.out.println("ggggggggggggggggggg  " + photoLink[2]);
                    if (!photoLink[0].equals("")) {
                        System.out.println("ssssssssssssssssssss   " + "1");
                        lentaNewsEntity.setUrlPictureNews1(photoLink[0]);
                    }
                    if (!photoLink[1].equals("")) {
                        System.out.println("ssssssssssssssssssss   " + "2");
                        lentaNewsEntity.setUrlPictureNews1(photoLink[1]);
                    }
                    if (!photoLink[2].equals("")) {
                        System.out.println("ssssssssssssssssssss   " + "3");
                        lentaNewsEntity.setUrlPictureNews1(photoLink[2]);
                    }
                    if (!photoLink[3].equals("")) {
                        System.out.println("ssssssssssssssssssss   " + "4");
                        lentaNewsEntity.setUrlPictureNews1(photoLink[3]);
                    }
                    if (!photoLink[4].equals("")) {
                        System.out.println("ssssssssssssssssssss   " + "5");
                        lentaNewsEntity.setUrlPictureNews1(photoLink[4]);
                    }

                    addJson(lentaNewsEntity);


                    Toast.makeText(view.getContext(), "добавлен тренер \n" + nameNews.getText(), Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(this, TrenerActivity.class);
//                    startActivity(intent);
//                    finish();
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