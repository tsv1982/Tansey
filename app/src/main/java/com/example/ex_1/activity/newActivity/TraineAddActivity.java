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

import com.example.ex_1.Entity.TrenierEntity;
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

public class TraineAddActivity extends AppCompatActivity implements View.OnClickListener {

    private TrenierEntity trenierEntity;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("trener");

    private StorageReference mStorageRef;

//    private EditText editTextAdminOrUserTrener;
    private EditText editTextIdEnterUserTrener1;
    private EditText editTextTrenerName;
    private EditText editTextTrenerDataB;
    private EditText editTextAbautTrner;
    private EditText editTextGraficZanjatiy;
    private EditText editTextTrenerURLPicture;
    private ImageView imageViewFotoTrener;

    String spinerAdmonOrUserArray[] = {"user", "admin"};
    private ArrayAdapter<String> adapterSpinerUserOrAdmin;
    private Spinner spinerTextUserOrAdmin;

    private Button btnAddTrener;
    private Button btnGetFotoTrener1;
    private TextView tvPahsFotoTrene1;
    private String photoLink;
    private String filePut;

    private int positionSpinerAdminOrUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_add_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        mStorageRef = FirebaseStorage.getInstance().getReference();

        adapterSpinerUserOrAdmin = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinerAdmonOrUserArray);
        adapterSpinerUserOrAdmin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerTextUserOrAdmin = findViewById(R.id.ET_AdminOrUser_Trener1);
        spinerTextUserOrAdmin.getBackground().setColorFilter(getResources().getColor(R.color.colorText), PorterDuff.Mode.SRC_ATOP);
        spinerTextUserOrAdmin.setAdapter(adapterSpinerUserOrAdmin);
        spinerTextUserOrAdmin.setSelection(0);

        spinerTextUserOrAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                positionSpinerAdminOrUser = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

//        editTextAdminOrUserTrener = findViewById(R.id.ET_AdminOrUser_Trener1);
        editTextIdEnterUserTrener1 = findViewById(R.id.ET_idEnterUser_Trener1);
        editTextTrenerName = findViewById(R.id.ET_Trener_Name1);
        editTextTrenerDataB = findViewById(R.id.ET_Trener_Data_B1);
        editTextAbautTrner = findViewById(R.id.ET_Abaut_Trner1);
        editTextGraficZanjatiy = findViewById(R.id.ET_Grafic_Zanjatiy1);

        editTextTrenerURLPicture = findViewById(R.id.ET_Trener_URL_Picture1);
        editTextTrenerURLPicture.setVisibility(View.GONE);
        tvPahsFotoTrene1 = findViewById(R.id.TV_Pahs_Foto_Trene1);
        tvPahsFotoTrene1.setVisibility(View.GONE);

        imageViewFotoTrener = findViewById(R.id.IV_trainer_image_Add);

        btnAddTrener = findViewById(R.id.btn_ADD_Trener1);
        btnAddTrener.setOnClickListener(this);

        btnGetFotoTrener1 = findViewById(R.id.btn_getPahsFotoTrener1);
        btnGetFotoTrener1.setOnClickListener(this);


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

            filePut = picturePath;

            File imgFile = new File(filePut);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageViewFotoTrener.setImageBitmap(myBitmap);

            tvPahsFotoTrene1.setText(filePut);

            cursor.close();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ADD_Trener1: {

                if (filePut == null) {
                    Toast.makeText(view.getContext(), "выберите изображение", Toast.LENGTH_LONG).show();
                } else {

                    trenierEntity = new TrenierEntity();
                    trenierEntity.setIdtrainers("255");
                    trenierEntity.setNameTener(String.valueOf(editTextTrenerName.getText()));
                    trenierEntity.setDateObirth(String.valueOf(editTextTrenerDataB.getText()));
                    trenierEntity.setAboutTrener(String.valueOf(editTextAbautTrner.getText()));
                    trenierEntity.setGrafikZanjatiy(String.valueOf(editTextGraficZanjatiy.getText()));
                    trenierEntity.setAdminOrUser(String.valueOf(spinerAdmonOrUserArray[positionSpinerAdminOrUser]));
                    trenierEntity.setIdEnterUser(String.valueOf(editTextIdEnterUserTrener1.getText()));


                    File destinationDirectory = new File(this.getCacheDir().getAbsolutePath());
                    String filePath = SiliCompressor.with(this).compress(filePut, destinationDirectory);

                    Uri file = Uri.fromFile(new File(filePath));
                    StorageReference riversRef = mStorageRef.child("trenerFoto/" + editTextTrenerName.getText() + ".jpg");

                    riversRef.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            photoLink = uri.toString();
                                            trenierEntity.setUrlFotoTrener(photoLink);
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

                    Toast.makeText(view.getContext(), "добавлен тренер \n" + editTextTrenerName.getText(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, TrenerActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            }
            case R.id.btn_getPahsFotoTrener1: {
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
