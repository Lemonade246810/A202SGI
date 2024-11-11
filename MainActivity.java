package com.example.cad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button administrator, clinic, doctor, patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        administrator   = findViewById(R.id.idBtnSuper);
        clinic          = findViewById(R.id.idBtnClinic);
        doctor          = findViewById(R.id.idBtnDoctor);
        patient         = findViewById(R.id.idBtnPatient);

        administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pro_admin = new Intent(MainActivity.this, AdministratorClinicView.class);
                startActivity(pro_admin);
            }
        });

        clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pro_clinic = new Intent(MainActivity.this, ClinicMainView.class);
                startActivity(pro_clinic);
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pro_doctor = new Intent(MainActivity.this, DoctorMainView.class);
                startActivity(pro_doctor);
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pro_patient = new Intent(MainActivity.this, PatientMainView.class);
                startActivity(pro_patient);
            }
        });

    }
}