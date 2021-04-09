package com.example.login_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import static com.example.login_practice.DatabaseStore.COLUMN_PHONE;
import static com.example.login_practice.DatabaseStore.COLUMN_SURNAME;
import static com.example.login_practice.DatabaseStore.TABLE_LOGIN;
import static com.example.login_practice.DatabaseStore.TABLE_NAME;

public class EmptyPage extends AppCompatActivity implements View.OnClickListener{

    SQLiteDatabase sqLiteDatabase;
    DatabaseStore dbStore;
    TextView uEmail, uInfo, uPhoneNumber;
    Button bLogout;
    DatabaseReference databaseReference;
    String emailFormat;
    User user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_page);


        user = new User();
        dbStore = new DatabaseStore(this);
        sqLiteDatabase = dbStore.getWritableDatabase();

        uEmail = findViewById(R.id.uEmail);
        uInfo = findViewById(R.id.uInfo);
        uPhoneNumber = findViewById(R.id.uPhoneNumber);
        bLogout = findViewById(R.id.logout);
        bLogout.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailFormat = currentUser.getEmail().replace(".", "-");

        getfromFireBase();
    }

    private void getfromFireBase() {
        databaseReference.child("users").child(emailFormat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User nUser = snapshot.getValue(User.class);
                    uInfo.setText("Welcome to your profile, " + nUser.getName());
                    uEmail.setText("Email: " + nUser.getEmail());
                    uPhoneNumber.setText("Phone Number: " + nUser.getPhoneNumber());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//    public void initUser() {
//        Cursor loginCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_LOGIN + " WHERE " +
//                COLUMN_LOGIN_STATE + "=?", new String[]{"yes"});
//        if (loginCursor != null && loginCursor.getCount() > 0 && loginCursor.moveToFirst()) {
//
//            String email = loginCursor.getString(loginCursor.getColumnIndex(COLUMN_EMAIL));
//            uEmail.setText("Your email is: " + email);
//
//            Cursor emailCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
//                    COLUMN_EMAIL + "=?", new String[]{email});
//
//
//            if (emailCursor != null & emailCursor.getCount() > 0) {
//                emailCursor.moveToFirst();
//
//                String name = emailCursor.getString(emailCursor.getColumnIndex(COLUMN_NAME));
//                String surname = emailCursor.getString(emailCursor.getColumnIndex(COLUMN_SURNAME));
//                String phonenumber = emailCursor.getString(emailCursor.getColumnIndex(COLUMN_PHONE));
//
//                uInfo.setText("You are Welcome, " + name + surname);
//                uPhoneNumber.setText("Your phone number: " + phonenumber);
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
//        Cursor loginCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_LOGIN + " WHERE " +
//                COLUMN_LOGIN_STATE + "=?", new String[]{"yes"});
//
//        if (loginCursor != null && loginCursor.getCount() > 0 && loginCursor.moveToFirst()) {
//            String email = loginCursor.getString(loginCursor.getColumnIndex(COLUMN_EMAIL));
//
//            Cursor emailCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
//                    COLUMN_EMAIL + "=?", new String[]{email});
//
//            if (emailCursor != null & emailCursor.getCount() > 0) {
//                emailCursor.moveToFirst();
//
//                ContentValues userValues = new ContentValues();
//                userValues.put(COLUMN_EMAIL, email);
//                userValues.put(COLUMN_LOGIN_STATE, "no");
//                sqLiteDatabase.update(TABLE_LOGIN, userValues,  "userstate =?", new String[]{"yes"});

                Intent intent = new Intent(EmptyPage.this, LoginActivity.class);
                startActivity(intent);



            }
        }