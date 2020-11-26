package com.muhtasim.parkmycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static int TIME_LAPS = 5000;    //for 5 sec
    public Button etNewBookingBtn, etViewBookingbtn, etCancelBookingbtn, etUserDetailsbtn;
    FirebaseFirestore ftStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNewBookingBtn = findViewById(R.id.NewBookingButton);
        etViewBookingbtn = findViewById(R.id.ViewBookingButton);
        etCancelBookingbtn = findViewById(R.id.DeleteBookingButton);
        etUserDetailsbtn = findViewById(R.id.UserDetailsButton);

        ftStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        final DocumentReference dc = ftStore.collection("Users").document("Users");

        etUserDetailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Object id = documentSnapshot.get("ID");
                            documentSnapshot.get("fName");
                            documentSnapshot.get("Contact");
                            documentSnapshot.get("email");
                            documentSnapshot.get("Address");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Something Went Wrong" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(getApplicationContext(), UserDetails.class));
            }
        });

        etNewBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewBooking.class));
            }
        });


        final DocumentReference db = ftStore.collection("Booking Details").document("Booking Details");
        etViewBookingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Object id = documentSnapshot.get("ID");
                        documentSnapshot.get("Car Name");
                        documentSnapshot.get("Car Number");
                        documentSnapshot.get("Check IN Time");
                        documentSnapshot.get("Check Out Time");
                        documentSnapshot.get("Date");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Something Wrong " + e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
                startActivity(new Intent(getApplicationContext(), ViewBooking.class));
            }
        });
        etCancelBookingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CancelBooking.class));
            }
        });

    }
    public void LogOut(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}