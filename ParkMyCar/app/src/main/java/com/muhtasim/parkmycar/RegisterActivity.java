package com.muhtasim.parkmycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    public EditText etFullname, etEmail, etPassword, etAddress, etPhone;
    public Button etRegButton;
    public TextView etLogginButton;
    public String UserID;
    FirebaseFirestore ftStore;
    public FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullname = findViewById(R.id.editTextFullName);
        etEmail = findViewById(R.id.editTextEmailAddress);
        etPassword = findViewById(R.id.editTextPassword);
        etAddress = findViewById(R.id.editTextAddress);
        etPhone =findViewById(R.id.editTextPhone);
        etLogginButton = findViewById(R.id.alreadyHaveAccountTextView);
        etRegButton = findViewById(R.id.SignUpButton);
        fAuth = FirebaseAuth.getInstance();
        ftStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        etRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                final String fullname = etFullname.getText().toString();
                final String contactNumber = etPhone.getText().toString();
                final String Address = etAddress.getText().toString();

                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email id Required! :( ");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Password required! :( ");
                    return;
                }
                if(password.length()<6){
                    etPassword.setError("Password must be six character long! :( ");
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created Successfully :) ", Toast.LENGTH_SHORT).show();
                            UserID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = ftStore.collection("Users").document(UserID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullname);
                            user.put("email",email);
                            user.put("Address",Address);
                            user.put("Contact",contactNumber);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "OnSuccess: USER profile has created " + UserID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "OnFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,
                                    "Registration Was Unsuccessful :(" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        etLogginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}