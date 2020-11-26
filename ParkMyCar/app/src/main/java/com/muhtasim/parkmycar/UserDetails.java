package com.muhtasim.parkmycar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserDetails extends AppCompatActivity {

    public TextView fullname, email, phone, address;
    FirebaseAuth fAuth;
    FirebaseFirestore ftStore;
    String UsersID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        fullname = findViewById(R.id.ProfileUserNameTextView);
        email = findViewById(R.id.ProfileEmailTextView);
        phone = findViewById(R.id.ProfileUserNumberTextView);
        address = findViewById(R.id.ProfileAddressTextView);
        fAuth = FirebaseAuth.getInstance();
        ftStore = FirebaseFirestore.getInstance();
        UsersID = fAuth.getCurrentUser().getUid();


        DocumentReference dc = ftStore.collection("Users").document("Users");
        dc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot dcSnapshot, @Nullable FirebaseFirestoreException error) {
                phone.setText(dcSnapshot.getString("Contact"));
                fullname.setText(dcSnapshot.getString("fName"));
                email.setText(dcSnapshot.getString("email"));
                address.setText(dcSnapshot.getString("Address"));
            }
        });

    }
 /*   public void LogOut(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }*/
}