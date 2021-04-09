package com.example.login_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.login_practice.DatabaseStore.COLUMN_EMAIL;
import static com.example.login_practice.DatabaseStore.COLUMN_NAME;
import static com.example.login_practice.DatabaseStore.COLUMN_PASSWORD;
import static com.example.login_practice.DatabaseStore.COLUMN_PHONE;
import static com.example.login_practice.DatabaseStore.COLUMN_SURNAME;
import static com.example.login_practice.DatabaseStore.TABLE_NAME;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseStore databaseStore;
    SQLiteDatabase sqLiteDatabase;
    Button btnRegister;
    TextInputEditText name, surname, phone_number, email, password;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    User user;
    String emailFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initWidgets();
        btnRegister.setOnClickListener(this);

    }

    public void initWidgets(){
        btnRegister = findViewById(R.id.btnRegister);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        phone_number = findViewById(R.id.phone_number);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        databaseStore = new DatabaseStore(this);
        sqLiteDatabase = databaseStore.getWritableDatabase();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    public void onClick(View view) {

        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Толық толтырыңыз!");
            return;
        }
        if (TextUtils.isEmpty(surname.getText())) {
            surname.setError("Толық толтырыңыз!");
            return;
        }
        if (TextUtils.isEmpty(phone_number.getText())) {
            phone_number.setError("Толық толтырыңыз!");
            return;
        }
        if (TextUtils.isEmpty(email.getText())) {
            email.setError("Толық толтырыңыз!");
            return;
        }
        if (TextUtils.isEmpty(password.getText())) {
            password.setError("Толық толтырыңыз!");
            return;
        }

        String uemail = email.getText().toString();
        String upassword = password.getText().toString();
        createAccount(uemail, upassword );

//        ContentValues dataValues = new ContentValues();
//        dataValues.put(COLUMN_NAME, name.getText().toString());
//        dataValues.put(COLUMN_SURNAME, surname.getText().toString());
//        dataValues.put(COLUMN_PHONE, phone_number.getText().toString());
//        dataValues.put(COLUMN_EMAIL, email.getText().toString());
//        dataValues.put(COLUMN_PASSWORD, password.getText().toString());
//
//        sqLiteDatabase.insert(TABLE_NAME, null, dataValues);
//
//        Toast.makeText(this, "You signed up correctly", Toast.LENGTH_SHORT).show();
//
//        startActivity(new Intent(RegistrationActivity. this, LoginActivity.class));

    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            addDatatoFirebase();
                            Toast.makeText(RegistrationActivity.this, "Welcome Home!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(RegistrationActivity.this, "Error: " + task.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addDatatoFirebase() {
        String uname = name.getText().toString();
        String usurname = surname.getText().toString();
        String uphone = phone_number.getText().toString();
        String uemail = email.getText().toString();

        user = new User(uname, usurname, uphone, uemail);

        emailFormat = user.getEmail().replace(".", "-");

        databaseReference.child("users").child(emailFormat).setValue(user);
    }
}