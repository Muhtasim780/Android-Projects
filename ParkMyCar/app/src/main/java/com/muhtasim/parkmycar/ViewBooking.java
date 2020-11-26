package com.muhtasim.parkmycar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaDrm;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ViewBooking extends AppCompatActivity {

    public TextView BookingID, CarName, CarNumber, CheckIn, CheckOut, Date;
    FirebaseAuth fAuth;
    FirebaseFirestore ftStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        fAuth = FirebaseAuth.getInstance();
        ftStore = FirebaseFirestore.getInstance();

        BookingID = findViewById(R.id.BookingIDTextView);
        CarName = findViewById(R.id.CarNameTextView);
        CarNumber = findViewById(R.id.CarNumberTextView);
        CheckIn = findViewById(R.id.CheckINTextView);
        CheckOut = findViewById(R.id.CheckOUTTextView);
        Date = findViewById(R.id.DateTextView);

        DocumentReference db = ftStore.collection("Booking Details").document("Booking Details");
        db.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot dcSnapshot, @Nullable FirebaseFirestoreException error) {
                CarName.setText(dcSnapshot.getString("Car Name"));
                CarNumber.setText(dcSnapshot.getString("Car Number"));
                CheckIn.setText(dcSnapshot.getString("Check In Time"));
                CheckOut.setText(dcSnapshot.getString("Check Out Time"));
                Date.setText(dcSnapshot.getString("Date"));
            }
        });

    }
}