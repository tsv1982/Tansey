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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.TrenierEntity;
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

public class TrainerRefactorActivity extends AppCompatActivity implements View.OnClickListener {

    TrenierEntity trenierEntity;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("trener");

    private StorageReference mStorageRef;
    private TrenierEntity trenierEntityFromBD;

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private String saveIdTrenerRefactor;

    private EditText editTextAdminOrUserTrenerRefactor;
    private TextView editTextIdEnterUserTrener1Refactor;
    private EditText editTextTrenerNameRefactor;
    private EditText editTextTrenerDataBRefactor;
    private EditText editTextAbautTrnerRefactor;
    private EditText editTextGraficZanjatiyRefactor;
    private EditText editTextTrenerURLPictureRefactor;
    private ImageView imageViewFotoTrenerRefactor;

    private Button btnAddTrenerRefactor;
    private Button btnGetFotoTrener1Refactor;
    private TextView tvPahsFotoTrene1Refactor;
    private String photoLinkRefactor;
    private String filePutRefactor;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_refactor_activity);

        context = this;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        saveIdTrenerRefactor = sharedPreferences.getString("saveIdRefactorTrener", "2");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        editTextAdminOrUserTrenerRefactor = findViewById(R.id.ET_AdminOrUser_Trener1_Refactor);
        editTextIdEnterUserTrener1Refactor = findViewById(R.id.ET_idEnterUser_Trener1_Refactor);
        editTextTrenerNameRefactor = findViewById(R.id.ET_Trener_Name1_Refactor);
        editTextTrenerDataBRefactor = findViewById(R.id.ET_Trener_Data_B1_Refactor);
        editTextAbautTrnerRefactor = findViewById(R.id.ET_Abaut_Trner1_Refactor);
        editTextGraficZanjatiyRefactor = findViewById(R.id.ET_Grafic_Zanjatiy1_Refactor);
        imageViewFotoTrenerRefactor = findViewById(R.id.IV_trainer_image_Add_Refactor);

        editTextTrenerURLPictureRefactor = findViewById(R.id.ET_Trener_URL_Picture1_Refactor);
        editTextTrenerURLPictureRefactor.setVisibility(View.GONE);
        tvPahsFotoTrene1Refactor = findViewById(R.id.TV_Pahs_Foto_Trene1_Refactor);
        tvPahsFotoTrene1Refactor.setVisibility(View.GONE);

        btnAddTrenerRefactor = findViewById(R.id.btn_ADD_Trener1_Refactor);
        btnAddTrenerRefactor.setOnClickListener(this);

        btnGetFotoTrener1Refactor = findViewById(R.id.btn_getPahsFotoTrener1_Refactor);
        btnGetFotoTrener1Refactor.setOnClickListener(this);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                if (dataSnapshot.getKey().equals(saveIdTrenerRefactor)) {
                    String s1 = String.valueOf(dataSnapshot.getValue());
                    trenierEntityFromBD = new Gson().fromJson(s1, TrenierEntity.class);

                    editTextTrenerNameRefactor.setText(trenierEntityFromBD.getNameTener());
                    editTextTrenerDataBRefactor.setText(trenierEntityFromBD.getDateObirth());
                    editTextAbautTrnerRefactor.setText(trenierEntityFromBD.getAboutTrener());
                    editTextGraficZanjatiyRefactor.setText(trenierEntityFromBD.getGrafikZanjatiy());
                    editTextTrenerURLPictureRefactor.setText(trenierEntityFromBD.getUrlFotoTrener());
                    editTextAdminOrUserTrenerRefactor.setText(trenierEntityFromBD.getAdminOrUser());
                    editTextIdEnterUserTrener1Refactor.setText(trenierEntityFromBD.getIdEnterUser());

                    Picasso.with(context)
                            .load(trenierEntityFromBD.getUrlFotoTrener())
                            .placeholder(R.drawable.loading)   // заглушка во время загрузки
                            .error(R.drawable.loading_error)  // если еррор
                            .into(imageViewFotoTrenerRefactor);

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

    private void addJson(TrenierEntity trenierEntity) {   //  c EDIT TEXT парсим JSON

        String jsonAdd = new Gson().toJson(new TrenierEntity(
                trenierEntity.getIdtrainers(),
                trenierEntity.getNameTener(),
                trenierEntity.getDateObirth(),
                trenierEntity.getAboutTrener(),
                trenierEntity.getGrafikZanjatiy(),
                trenierEntity.getUrlFotoTrener(),
                trenierEntity.getAdminOrUser(),
                trenierEntity.getIdEnterUser()));
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

            filePutRefactor = picturePath;

            File imgFile = new File(filePutRefactor);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageViewFotoTrenerRefactor.setImageBitmap(myBitmap);

            tvPahsFotoTrene1Refactor.setText(filePutRefactor);

            cursor.close();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ADD_Trener1_Refactor: {

                databaseReference.child(saveIdTrenerRefactor).removeValue();

                trenierEntity = new TrenierEntity();
                trenierEntity.setIdtrainers("255");
                trenierEntity.setNameTener(String.valueOf(editTextTrenerNameRefactor.getText()));
                trenierEntity.setDateObirth(String.valueOf(editTextTrenerDataBRefactor.getText()));
                trenierEntity.setAboutTrener(String.valueOf(editTextAbautTrnerRefactor.getText()));
                trenierEntity.setGrafikZanjatiy(String.valueOf(editTextGraficZanjatiyRefactor.getText()));
                trenierEntity.setAdminOrUser(String.valueOf(editTextAdminOrUserTrenerRefactor.getText()));
                trenierEntity.setIdEnterUser(String.valueOf(editTextIdEnterUserTrener1Refactor.getText()));


                if (filePutRefactor != null) {
                    System.out.println("1111111111111111111111");
                    File destinationDirectory = new File(this.getCacheDir().getAbsolutePath());
                    String filePath = SiliCompressor.with(this).compress(filePutRefactor, destinationDirectory);

                    Uri file = Uri.fromFile(new File(filePath));
                    StorageReference riversRef = mStorageRef.child("trenerFoto/" + editTextTrenerNameRefactor.getText() + ".jpg");

                    riversRef.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            photoLinkRefactor = uri.toString();
                                            trenierEntity.setUrlFotoTrener(photoLinkRefactor);
                                            addJson(trenierEntity);
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
                } else {

                    trenierEntity.setUrlFotoTrener(trenierEntityFromBD.getUrlFotoTrener());
                    addJson(trenierEntity);
                }


                Toast.makeText(view.getContext(), "изменено \n" + editTextTrenerNameRefactor.getText(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, TrenerActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.btn_getPahsFotoTrener1_Refactor: {
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