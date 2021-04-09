package com.example.login_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.login_practice.DatabaseStore.COLUMN_EMAIL;
import static com.example.login_practice.DatabaseStore.COLUMN_LOGIN_STATE;
import static com.example.login_practice.DatabaseStore.COLUMN_NAME;
import static com.example.login_practice.DatabaseStore.COLUMN_PASSWORD;
import static com.example.login_practice.DatabaseStore.TABLE_LOGIN;
import static com.example.login_practice.DatabaseStore.TABLE_NAME;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseStore databaseStore;
    SQLiteDatabase sqLiteDatabase;
    Button btnRegister, btnLogin;
    TextInputEditText email, password;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    TextView tvText;
    User user;
    String emailFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User("Aruzhan", "Sabitova", "656565", "aruzhansabit@gmail.com");
        emailFormat = user.getEmail().replace(".", "-");

        initWidgets();
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);


        //        checkLogin();

    }

//    private void checkLogin() {
//        Cursor loginCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_LOGIN + " WHERE "
//                + COLUMN_LOGIN_STATE + "=?", new String[]{"yes"});
//
//        if (loginCursor!=null & loginCursor.getCount() > 0){
//            startActivity(new Intent(LoginActivity.this, EmptyPage.class));
//        }
//    }

    public void initWidgets(){
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        tvText = findViewById(R.id.tvText);

        databaseStore = new DatabaseStore(this);
        sqLiteDatabase = databaseStore.getWritableDatabase();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            user.getEmail();
            Intent intent = new Intent(LoginActivity.this, EmptyPage.class);
            startActivity(intent);
        }
//        addDatatoFirebase();
//          getfromFirebase();
    }

//    public void addDatatoFirebase(){
//        User user = new User("Aruzhan", "Sabitova", "656565", "aruzhansabit@gmail.com");
//        String emailFormat = user.getEmail().replace(".", "-");
//        databaseReference.child("users").child(emailFormat).setValue(user);
//    }

//    public void getfromFirebase(){
//        databaseReference.child("users").child(emailFormat).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    User newUser = snapshot.getValue(User.class);
//                    tvText.setText(newUser.getSurname());
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnLogin:

                String uemail = email.getText().toString();
                String upassword = password.getText().toString();

                btnLogin.setVisibility(View.GONE);

                if (TextUtils.isEmpty(email.getText())){
                    email.setError("Толық толтырыңыз!");

                    btnLogin.setVisibility(View.VISIBLE);
                    return;
                }

                if (TextUtils.isEmpty(password.getText())){
                    password.setError("Толық толтырыңыз!");

                    btnLogin.setVisibility(View.VISIBLE);
                    return;
                }

                signIn(uemail, upassword);


//                Cursor loginCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME  + " WHERE "
//                        + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{uemail, upassword});
//





//                if (loginCursor != null & loginCursor.getCount() > 0){
//                    loginCursor.moveToFirst();
//
//                    String fullName = loginCursor.getString(loginCursor.getColumnIndex(COLUMN_NAME));
//
//                    Toast.makeText(this, "You are welcome, " + fullName, Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(LoginActivity.this, EmptyPage.class);
//                    startActivity(intent);
//
//                    ContentValues userValues = new ContentValues();
//                    userValues.put(COLUMN_EMAIL, uemail);
//                    userValues.put(COLUMN_LOGIN_STATE, "yes");
//                    sqLiteDatabase.update(TABLE_LOGIN, userValues,  "userstate =?", new String[]{"no"});
//

//                }



                break;

            case R.id.btnRegister:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                break;
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(LoginActivity.this, "Welcome Main Activity!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, EmptyPage.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}