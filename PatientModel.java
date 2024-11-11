package com.example.cad;

import android.os.Parcel;
import android.os.Parcelable;

public class PatientModel {
    private String patientName;
    private String userName;
    private String password;
    private String latitude;
    private String longitude;



    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    // creating an empty constructor.
    public PatientModel() {

    }

    protected PatientModel(Parcel in) {
        patientName = in.readString();
        userName = in.readString();
        password = in.readString();
        latitude = in.readString();
        longitude = in.readString();

    }

    public static final Parcelable.Creator<PatientModel> CREATOR = new Parcelable.Creator<PatientModel>() {
        @Override
        public PatientModel createFromParcel(Parcel in) {
            return new PatientModel(in);
        }

        @Override
        public PatientModel[] newArray(int size) {
            return new PatientModel[size];
        }
    };

    // creating getter and setter methods.
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }





    public PatientModel(String patientName, String userName, String password, String latitude, String longitude) {
        this.patientName = patientName;
        this.userName = userName;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(patientName);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(latitude);
        dest.writeString(longitude);

    }

}
