package com.example.cad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientRegister extends AppCompatActivity {

    private Button Register;
    private TextInputEditText p_name, u_name, pass, lat, longi;
    private TextView login;
    private ProgressBar loadingPB;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        // Initialize views
        Register = findViewById(R.id.idBtnRegister);
        p_name = findViewById(R.id.idPatientName);
        u_name = findViewById(R.id.idEdtUserName);
        pass = findViewById(R.id.idEdtPassword);
        lat = findViewById(R.id.idLatitude);
        longi = findViewById(R.id.idLongitude);
        loadingPB = findViewById(R.id.idPBLoading);
        login = findViewById(R.id.idTVLoginUser);

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Patient");

        // Handle the register button click
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPatient();
            }
        });

        // Handle the login text click
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientRegister.this, PatientLogin.class));
            }
        });
    }

    private void registerPatient() {
        // Show progress bar
        loadingPB.setVisibility(View.VISIBLE);

        // Get input values
        String patientName = p_name.getText().toString().trim();
        String userName = u_name.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String latitude = lat.getText().toString().trim();
        String longitude = longi.getText().toString().trim();

        // Validate inputs
        if (patientName.isEmpty() || userName.isEmpty() || password.isEmpty() || latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            loadingPB.setVisibility(View.GONE);
            return;
        }

        // Check if latitude and longitude are valid numbers
        if (!isValidCoordinate(latitude) || !isValidCoordinate(longitude)) {
            Toast.makeText(this, "Invalid latitude or longitude", Toast.LENGTH_SHORT).show();
            loadingPB.setVisibility(View.GONE);
            return;
        }

        // Replace special characters in the username to make it Firebase-compatible
        String sanitizedUserName = userName.replaceAll("[@.#$]", "-");

        // Create a new patient model
        PatientModel patientModel = new PatientModel(patientName, sanitizedUserName, password, latitude, longitude);

        // Check if the username already exists in the database
        databaseReference.orderByChild("userName").equalTo(sanitizedUserName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            // Username doesn't exist, proceed with registration
                            registerNewPatient(sanitizedUserName, patientModel);
                        } else {
                            // Username already exists
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(PatientRegister.this, "Username already exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(PatientRegister.this, "Database error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void registerNewPatient(String sanitizedUserName, PatientModel patientModel) {
        // Add patient data to Firebase
        databaseReference.child(sanitizedUserName).setValue(patientModel)
                .addOnSuccessListener(unused -> {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(PatientRegister.this, "Patient Registered Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(PatientRegister.this, "Failed to register patient: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    // Utility method to validate latitude and longitude
    private boolean isValidCoordinate(String coordinate) {
        try {
            double value = Double.parseDouble(coordinate);
            return value >= -180 && value <= 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
