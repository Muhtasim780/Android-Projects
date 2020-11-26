package com.muhtasim.parkmycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    public EditText etUserName, etPassword;
    public Button etLogginButton;
    public TextView etCreateButton;
    FirebaseDatabase Database;
    DatabaseReference etDatabase;
    public FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        etUserName = findViewById(R.id.editTextUserName);
        etPassword = findViewById(R.id.editTextLoginPassword);
        etCreateButton = findViewById(R.id.CreateNewAccountTextView);
        fAuth = FirebaseAuth.getInstance();
        etLogginButton = findViewById(R.id.LoginButton);

        etLogginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    etUserName.setError("Email id Required! :( ");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Password required! :( ");
                    return;
                }
                if(password.length()<6){
                    etPassword.setError("Password must be six character long! :( ");
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Successfully Logged In :)", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this,
                                    "OOPS! Something Went Wrong :(" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        etCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
}