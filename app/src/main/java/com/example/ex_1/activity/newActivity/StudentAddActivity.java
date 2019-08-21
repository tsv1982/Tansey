package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.ex_1.Entity.StudentGroupEntity;
import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.example.ex_1.ZaprosF;
import com.example.ex_1.activity.ListPeopleActivity;
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

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class StudentAddActivity extends AppCompatActivity implements View.OnClickListener {

    StudentСardEntity studentСardEntity;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("student");
    private DatabaseReference databaseReferenceGroup = firebaseDatabase.getReference("group");

    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    private ArrayAdapter<String> adapterSpiner;
    private ArrayAdapter<String> adapterSpinerUserOrAdmin;
    private ArrayList<String> arrayListNameGroupStudent = new ArrayList<>();
    String spinerAdmonOrUserArray[] = {"user", "admin"};

    private Spinner spinnerGroup;
    private Spinner spinerTextUserOrAdmin;


    private EditText editTextidEnterStudent;
    private EditText editTextnameStudent;
    private EditText editTextves;
    private EditText editTexttitul;
    private EditText editTextdate;
    private EditText editTextseson;
    private EditText editTextturnir;
    private EditText editTextboiov;
    private EditText editTextpobed;
    private EditText editTextporagenii;
    private EditText editTextochkov;
    private EditText editTextpressNorma;
    private EditText editTextpressFact;
    private EditText editTextotgimaniyNorma;
    private EditText editTextotgimaniyFact;
    private EditText editTextpodtjagNorma;
    private EditText editTextpodtjagFact;
    private EditText editTextroznogkaNorma;
    private EditText editTextroznogkaFact;
    private EditText editTextprugkiNorma;
    private EditText editTextprugkiFact;

    private ImageView imageViewFotoStudent;

    private Button btnPashUrlFoto;
    private Button btnAddStudent;

    private String photoLink;
    private String filePut;

    private int positionSpiner;
    private int positionSpinerAdminOrUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_add_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        editTextidEnterStudent = findViewById(R.id.ET_Add_ID_Enter_Student1);
        editTextnameStudent = findViewById(R.id.ET_Add_NemeStudent_Student1);
        editTextves = findViewById(R.id.ET_Add_ves_Student1);
        editTexttitul = findViewById(R.id.ET_Add_TitulStudent_Student1);
        editTextdate = findViewById(R.id.ET_Add_DataStudent_Student1);
        editTextseson = findViewById(R.id.ET_Add_sezon_Student1);
        editTextturnir = findViewById(R.id.ET_Add_turnir_Student1);
        editTextboiov = findViewById(R.id.ET_Add_boiov_Student1);
        editTextpobed = findViewById(R.id.ET_Add_pobed_Student1);
        editTextporagenii = findViewById(R.id.ET_Add_poragenii_Student1);
        editTextochkov = findViewById(R.id.ET_Add_ochkov_Student1);
        editTextpressNorma = findViewById(R.id.ET_Add_press_norma_Student1);
        editTextpressFact = findViewById(R.id.ET_Add_press_fakt_Student1);
        editTextotgimaniyNorma = findViewById(R.id.ET_Add_otgimanii_norma_Student1);
        editTextotgimaniyFact = findViewById(R.id.ET_Add_otgimanii_fact_Student1);
        editTextpodtjagNorma = findViewById(R.id.ET_Add_podtjag_norma_Student1);
        editTextpodtjagFact = findViewById(R.id.ET_Add_podtjag_fakt_Student1);
        editTextroznogkaNorma = findViewById(R.id.ET_Add_roznogka_norma_Student1);
        editTextroznogkaFact = findViewById(R.id.ET_Add_roznogka_fact_Student1);
        editTextprugkiNorma = findViewById(R.id.ET_Add_prigki_norma_Student1);
        editTextprugkiFact = findViewById(R.id.ET_Add_prigki_fact_Student1);


        imageViewFotoStudent = findViewById(R.id.IV_student_image_Add);

        btnPashUrlFoto = findViewById(R.id.btn_Add_getPahsFotoStudent1);
        btnPashUrlFoto.setOnClickListener(this);
        btnAddStudent = findViewById(R.id.btn_ADD_Student1);
        btnAddStudent.setOnClickListener(this);


        adapterSpinerUserOrAdmin = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinerAdmonOrUserArray);
        adapterSpinerUserOrAdmin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerTextUserOrAdmin = findViewById(R.id.ET_Add_AdminOrUser_Student1);
        spinerTextUserOrAdmin.getBackground().setColorFilter(getResources().getColor(R.color.colorText), PorterDuff.Mode.SRC_ATOP);
        spinerTextUserOrAdmin.setAdapter(adapterSpinerUserOrAdmin);
        spinerTextUserOrAdmin.setSelection(0);

        spinerTextUserOrAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                positionSpinerAdminOrUser = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        adapterSpiner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListNameGroupStudent);
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroup = findViewById(R.id.SP_Group_Id_Student_Enter);
        spinnerGroup.getBackground().setColorFilter(getResources().getColor(R.color.colorText), PorterDuff.Mode.SRC_ATOP);
        spinnerGroup.setAdapter(adapterSpiner);
        spinnerGroup.setSelection(0);

        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                positionSpiner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        databaseReferenceGroup.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                StudentGroupEntity studentGroupEntity = new Gson().fromJson(s1, StudentGroupEntity.class);
                studentGroupEntity.setIdGroupBD(dataSnapshot.getKey());
                arrayListNameGroupStudent.add(studentGroupEntity.getNameGroup());
                adapterSpiner.notifyDataSetChanged();

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

    private void addJson(StudentСardEntity studentСardEntity) {   //  c EDIT TEXT парсим JSON
        String jsonAdd = new Gson().toJson(new StudentСardEntity(

                studentСardEntity.getIdstudent(),
                studentСardEntity.getIdEnterStudent(),
                studentСardEntity.getUserOrAdmin(),
                studentСardEntity.getNameStudent(),
                studentСardEntity.getGroupName(),
                studentСardEntity.getVes(),
                studentСardEntity.getTitul(),
                studentСardEntity.getDate(),
                studentСardEntity.getSeson(),
                studentСardEntity.getTurnir(),
                studentСardEntity.getBoiov(),
                studentСardEntity.getPobed(),
                studentСardEntity.getPoragenii(),
                studentСardEntity.getOchkov(),
                studentСardEntity.getPressNorma(),
                studentСardEntity.getPressFact(),
                studentСardEntity.getOtgimaniyNorma(),
                studentСardEntity.getOtgimaniyFact(),
                studentСardEntity.getPodtjagNorma(),
                studentСardEntity.getPodtjagFact(),
                studentСardEntity.getRoznogkaNorma(),
                studentСardEntity.getRoznogkaFact(),
                studentСardEntity.getPrugkiNorma(),
                studentСardEntity.getPrugkiFact(),
                studentСardEntity.getUrlStudentFotoCard(),
                studentСardEntity.getReiting()));

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
            imageViewFotoStudent.setImageBitmap(myBitmap);

            cursor.close();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Add_getPahsFotoStudent1: {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 101);
                break;
            }
            case R.id.btn_ADD_Student1: {
                if (filePut == null) {
                    Toast.makeText(view.getContext(), "выберите изображение", Toast.LENGTH_LONG).show();
                } else {

                    studentСardEntity = new StudentСardEntity();

                    studentСardEntity.setIdstudent("000");
                    studentСardEntity.setIdEnterStudent(String.valueOf(editTextidEnterStudent.getText()));
                    studentСardEntity.setUserOrAdmin(String.valueOf(spinerAdmonOrUserArray[positionSpinerAdminOrUser]));
                    studentСardEntity.setNameStudent(String.valueOf(editTextnameStudent.getText()));

                    if (arrayListNameGroupStudent.size() == 0) {
                        Toast.makeText(view.getContext(), "создайте сначала группу", Toast.LENGTH_LONG).show();
                    } else {

                        studentСardEntity.setGroupName(arrayListNameGroupStudent.get(positionSpiner));
                        studentСardEntity.setVes(String.valueOf(editTextves.getText()));
                        studentСardEntity.setTitul(String.valueOf(editTexttitul.getText()));
                        studentСardEntity.setDate(String.valueOf(editTextdate.getText()));
                        studentСardEntity.setSeson(String.valueOf(editTextseson.getText()));
                        studentСardEntity.setTurnir(String.valueOf(editTextturnir.getText()));
                        studentСardEntity.setBoiov(String.valueOf(editTextboiov.getText()));
                        studentСardEntity.setPobed(String.valueOf(editTextpobed.getText()));
                        studentСardEntity.setPoragenii(String.valueOf(editTextporagenii.getText()));
                        studentСardEntity.setOchkov(String.valueOf(editTextochkov.getText()));
                        studentСardEntity.setPressNorma(String.valueOf(editTextpressNorma.getText()));
                        studentСardEntity.setPressFact(String.valueOf(editTextpressFact.getText()));
                        studentСardEntity.setOtgimaniyNorma(String.valueOf(editTextotgimaniyNorma.getText()));
                        studentСardEntity.setOtgimaniyFact(String.valueOf(editTextotgimaniyFact.getText()));
                        studentСardEntity.setPodtjagNorma(String.valueOf(editTextpodtjagNorma.getText()));
                        studentСardEntity.setPodtjagFact(String.valueOf(editTextpodtjagFact.getText()));
                        studentСardEntity.setRoznogkaNorma(String.valueOf(editTextroznogkaNorma.getText()));
                        studentСardEntity.setRoznogkaFact(String.valueOf(editTextroznogkaFact.getText()));
                        studentСardEntity.setPrugkiNorma(String.valueOf(editTextprugkiNorma.getText()));
                        studentСardEntity.setPrugkiFact(String.valueOf(editTextprugkiFact.getText()));
                        studentСardEntity.setReiting("0");


                        File destinationDirectory = new File(this.getCacheDir().getAbsolutePath());
                        String filePath = SiliCompressor.with(this).compress(filePut, destinationDirectory);

                        Uri file = Uri.fromFile(new File(filePath));
                        StorageReference riversRef = mStorageRef.child("StudentFoto/" + editTextnameStudent.getText() + ".jpg");

                        riversRef.putFile(file)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                photoLink = uri.toString();
                                                studentСardEntity.setUrlStudentFotoCard(photoLink);
                                                addJson(studentСardEntity);
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

                        Toast.makeText(view.getContext(), "добавлен тренер \n" + editTextnameStudent.getText(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, StudentListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            }


        }
    }
}

