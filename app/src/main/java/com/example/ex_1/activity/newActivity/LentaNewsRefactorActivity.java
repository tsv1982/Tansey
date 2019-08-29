package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.iceteck.silicompressorr.SiliCompressor;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LentaNewsRefactorActivity extends AppCompatActivity implements View.OnClickListener {

    private LentaNewsEntity lentaNewsEntity = new LentaNewsEntity();

    private SharedPreferences sharedPreferences;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("LentaNews");
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    private ArrayList<String> arrayListPaht = new ArrayList<>();
    private ArrayList<String> arrayListUrl = new ArrayList<>();

    private EditText nameNewsRefactor;
    private EditText textRefactor;
    private EditText authorNewsRefactor;

    private ImageView imageViewFotoNews1Refactor;
    private ImageView imageViewFotoNews2Refactor;
    private ImageView imageViewFotoNews3Refactor;
    private ImageView imageViewFotoNews4Refactor;
    private ImageView imageViewFotoNews5Refactor;

    private Button btnAddTrenerRefactor;
    private Button btnGetFotoTrener1Refactor;

    private String saveIdLentaNews;
    private Context context;
    private int count = 0;
    private volatile int ii = 1;
    private String s1ListFotoUrl = "";
    boolean aBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lenta_news_refactor_activity);
        context = this;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);   // вытаскиваем переменную
        saveIdLentaNews = sharedPreferences.getString("saveIdLentaNews", "");

        btnAddTrenerRefactor = findViewById(R.id.btn_ADD_Lenta_News11_Refactor);
        btnAddTrenerRefactor.setOnClickListener(this);

        btnGetFotoTrener1Refactor = findViewById(R.id.btn_getPahsFotoLentaNews_Refactor);
        btnGetFotoTrener1Refactor.setOnClickListener(this);

        imageViewFotoNews1Refactor = findViewById(R.id.IV_Lenta_News_image_Add_1_Refactor);
        imageViewFotoNews2Refactor = findViewById(R.id.IV_Lenta_News_image_Add_2_Refactor);
        imageViewFotoNews3Refactor = findViewById(R.id.IV_Lenta_News_image_Add_3_Refactor);
        imageViewFotoNews4Refactor = findViewById(R.id.IV_Lenta_News_image_Add_4_Refactor);
        imageViewFotoNews5Refactor = findViewById(R.id.IV_Lenta_News_image_Add_5_Refactor);

        nameNewsRefactor = findViewById(R.id.ET_Lenta_News_Name1_Refactor);
        textRefactor = findViewById(R.id.ET_Text_Lenta_News1_Refactor);
        authorNewsRefactor = findViewById(R.id.ET_Autor_Lenta_News1_Refactor);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (saveIdLentaNews.equals(dataSnapshot.getKey())) {

                    String s1 = String.valueOf(dataSnapshot.getValue());

                    lentaNewsEntity = new Gson().fromJson(s1, LentaNewsEntity.class);
                    lentaNewsEntity.setIdlentaNews(dataSnapshot.getKey());

                    String[] arr = lentaNewsEntity.getUrlPictureNews1().split(",");
                    lentaNewsEntity.setTime(String.valueOf(arr.length - 1));  // количество фото

                    if (arr.length > 1) {
                        lentaNewsEntity.setUrlPictureNews1(arr[1]);
                        Picasso.with(context)
                                .load(lentaNewsEntity.getUrlPictureNews1())
                                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                                .error(R.drawable.loading_error)  // если еррор
                                .into(imageViewFotoNews1Refactor);
                    }

                    if (arr.length > 2) {
                        lentaNewsEntity.setUrlPictureNews2(arr[2]);
                        Picasso.with(context)
                                .load(lentaNewsEntity.getUrlPictureNews2())
                                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                                .error(R.drawable.loading_error)  // если еррор
                                .into(imageViewFotoNews2Refactor);
                    }

                    if (arr.length > 3) {
                        lentaNewsEntity.setUrlPictureNews3(arr[3]);
                        Picasso.with(context)
                                .load(lentaNewsEntity.getUrlPictureNews3())
                                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                                .error(R.drawable.loading_error)  // если еррор
                                .into(imageViewFotoNews3Refactor);
                    }

                    if (arr.length > 4) {
                        lentaNewsEntity.setUrlPictureNews4(arr[4]);
                        Picasso.with(context)
                                .load(lentaNewsEntity.getUrlPictureNews4())
                                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                                .error(R.drawable.loading_error)  // если еррор
                                .into(imageViewFotoNews4Refactor);
                    }

                    if (arr.length > 5) {
                        lentaNewsEntity.setUrlPictureNews5(arr[5]);
                        Picasso.with(context)
                                .load(lentaNewsEntity.getUrlPictureNews5())
                                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                                .error(R.drawable.loading_error)  // если еррор
                                .into(imageViewFotoNews5Refactor);
                    }

                    nameNewsRefactor.setText(lentaNewsEntity.getNameNews());
                    textRefactor.setText(lentaNewsEntity.getText());
                    authorNewsRefactor.setText(lentaNewsEntity.getAuthorNews());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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

            if (aBoolean) {

                imageViewFotoNews1Refactor.setImageDrawable(null);
                if (lentaNewsEntity.getUrlPictureNews1() != null) {
                    deleteFoto(lentaNewsEntity.getUrlPictureNews1());
                }
                lentaNewsEntity.setUrlPictureNews1(null);

                imageViewFotoNews2Refactor.setImageDrawable(null);
                if (lentaNewsEntity.getUrlPictureNews2() != null) {
                    deleteFoto(lentaNewsEntity.getUrlPictureNews2());
                }
                lentaNewsEntity.setUrlPictureNews2(null);

                imageViewFotoNews3Refactor.setImageDrawable(null);
                if (lentaNewsEntity.getUrlPictureNews3() != null) {
                    deleteFoto(lentaNewsEntity.getUrlPictureNews3());
                }
                lentaNewsEntity.setUrlPictureNews3(null);

                imageViewFotoNews4Refactor.setImageDrawable(null);
                if (lentaNewsEntity.getUrlPictureNews4() != null) {
                    deleteFoto(lentaNewsEntity.getUrlPictureNews4());
                }
                lentaNewsEntity.setUrlPictureNews4(null);

                imageViewFotoNews5Refactor.setImageDrawable(null);
                if (lentaNewsEntity.getUrlPictureNews5() != null) {
                    deleteFoto(lentaNewsEntity.getUrlPictureNews5());
                }
                lentaNewsEntity.setUrlPictureNews5(null);
                aBoolean = false;
            }

            if (count == 0) {
                arrayListPaht.add(picturePath);
                File imgFile = new File(arrayListPaht.get(0));
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageViewFotoNews1Refactor.setImageBitmap(myBitmap);
                count++;
            } else {
                if (count == 1) {
                    arrayListPaht.add(picturePath);
                    File imgFile = new File(arrayListPaht.get(1));
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageViewFotoNews2Refactor.setImageBitmap(myBitmap);
                    count++;
                } else {
                    if (count == 2) {
                        arrayListPaht.add(picturePath);
                        File imgFile = new File(arrayListPaht.get(2));
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imageViewFotoNews3Refactor.setImageBitmap(myBitmap);
                        count++;
                    } else {
                        if (count == 3) {
                            arrayListPaht.add(picturePath);
                            File imgFile = new File(arrayListPaht.get(3));
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            imageViewFotoNews4Refactor.setImageBitmap(myBitmap);
                            count++;
                        } else {
                            if (count == 4) {
                                arrayListPaht.add(picturePath);
                                File imgFile = new File(arrayListPaht.get(4));
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imageViewFotoNews5Refactor.setImageBitmap(myBitmap);
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

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(saveIdLentaNews, jsonAdd);
        databaseReference.updateChildren(childUpdates);
    }

    private void deleteFoto(String url) {

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        mStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });

    }

    private void addFoto(String s, String nameF) {
        File destinationDirectory = new File(this.getCacheDir().getAbsolutePath());
        String filePath = SiliCompressor.with(this).compress(s, destinationDirectory);
        Uri file = Uri.fromFile(new File(filePath));
        StorageReference riversRef = mStorageRef.child("LentaNewsFoto/" + nameNewsRefactor.getText() + nameF + ".jpg");
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

    private void slep() {              // assyngron адача
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btn_ADD_Lenta_News11_Refactor: {

                lentaNewsEntity.setIdlentaNews("255");
                lentaNewsEntity.setNameNews(String.valueOf(nameNewsRefactor.getText()));
                lentaNewsEntity.setData(lentaNewsEntity.getData());
                lentaNewsEntity.setTime("111");
                lentaNewsEntity.setText(String.valueOf(textRefactor.getText()));
                lentaNewsEntity.setAuthorNews(String.valueOf(authorNewsRefactor.getText()));

                String s = "";
                if (arrayListPaht.size() == 0) {
                    if (lentaNewsEntity.getUrlPictureNews1() != null) {
                        s = s + "," + lentaNewsEntity.getUrlPictureNews1();
                    }
                    if (lentaNewsEntity.getUrlPictureNews2() != null) {
                        s = s + "," + lentaNewsEntity.getUrlPictureNews2();
                    }
                    if (lentaNewsEntity.getUrlPictureNews3() != null) {
                        s = s + "," + lentaNewsEntity.getUrlPictureNews3();
                    }
                    if (lentaNewsEntity.getUrlPictureNews4() != null) {
                        s = s + "," + lentaNewsEntity.getUrlPictureNews4();
                    }
                    if (lentaNewsEntity.getUrlPictureNews5() != null) {
                        s = s + "," + lentaNewsEntity.getUrlPictureNews5();
                    }

                    lentaNewsEntity.setUrlPictureNews1(s);

                    addJson(lentaNewsEntity);
                } else {
                    for (int i = 0; i < arrayListPaht.size(); i++) {
                        addFoto(arrayListPaht.get(i), String.valueOf(i));
                        slep();
                    }
                }

                Toast.makeText(view.getContext(), "добавлен тренер \n" + nameNewsRefactor.getText(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();

                break;
            }
            case R.id.btn_getPahsFotoLentaNews_Refactor: {
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