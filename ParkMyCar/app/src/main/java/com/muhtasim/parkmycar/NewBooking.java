package com.muhtasim.parkmycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewBooking extends AppCompatActivity {
    public TextView carName, carNumber, phone, checkInTime, checkOutTime, date;
    public Button placeBooking;
    FirebaseFirestore ftStore;
    FirebaseAuth fAuth;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);
        carName = findViewById(R.id.CarNameEditText);
        carNumber = findViewById(R.id.CarNumberEditText);
        phone = findViewById(R.id.PhoneNumberOnBookingEditText);
        checkInTime = findViewById(R.id.CheckingINTimeEditText);
        checkOutTime = findViewById(R.id.CheckingOUTTimeEditText);
        date = findViewById(R.id.DateEditText);
        placeBooking = findViewById(R.id.PlaceBookingBTN);
        fAuth = FirebaseAuth.getInstance();
        ftStore = FirebaseFirestore.getInstance();

        placeBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String CarName = carName.getText().toString().trim();
                final String CarNumber = carNumber.getText().toString().trim();
                final String ContactNumber = phone.getText().toString();
                final String CheckINTime = checkInTime.getText().toString();
                final String CheckOUTTime = checkOutTime.getText().toString();
                final String DATE = date.getText().toString();

                if(TextUtils.isEmpty(CarName)){
                    carName.setError("CAR Name id Required! :( ");
                    return;
                }
                if(TextUtils.isEmpty(CarNumber)){
                    carNumber.setError("Car Plate Number required! :( ");
                    return;
                }
                if(TextUtils.isEmpty(ContactNumber)){
                    phone.setError("Users Contact Number Required! :( ");
                    return;
                }
                if(TextUtils.isEmpty(CheckINTime)){
                    checkInTime.setError("Check In Required! :( ");
                    return;
                }
                if(TextUtils.isEmpty(CheckOUTTime)){
                    checkOutTime.setError("Check Out Required! :( ");
                    return;
                }
                if(TextUtils.isEmpty(DATE)){
                    date.setError("Present Date Required! :( ");
                }

                Map<String,Object> UsersBooking = new HashMap<>();
                UsersBooking.put("Car Name",CarName);
                UsersBooking.put("Car Number",CarNumber);
                UsersBooking.put("Contact",ContactNumber);
                UsersBooking.put("Check IN Time",CheckINTime);
                UsersBooking.put("Check OUT Time",CheckOUTTime);
                UsersBooking.put("Date",DATE);
                ftStore.collection("Booking Details").add(UsersBooking).addOnSuccessListener(
                        new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Successfully Booking Complete :)" );
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "The Booking Could Not Take Place" + e.toString());
                    }
                });
            }
        });
    }
}