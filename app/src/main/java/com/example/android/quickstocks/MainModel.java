package com.example.android.quickstocks;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class MainModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String companyName;
    private String companyUrl;


    public MainModel(String companyName, String companyUrl) {
        this.companyName = companyName;
        this.companyUrl = companyUrl;
    }

    @Ignore
    public MainModel() {
    }

    protected MainModel(Parcel in) {
        companyName = in.readString();
        companyUrl = in.readString();
    }

    public static final Creator<MainModel> CREATOR = new Creator<MainModel>() {
        @Override
        public MainModel createFromParcel(Parcel in) {
            return new MainModel(in);
        }

        @Override
        public MainModel[] newArray(int size) {
            return new MainModel[size];
        }
    };

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String value) {
        this.companyName = value;
    }

    public String getCompanyUrl() {
        return this.companyUrl.substring(1);
    }

    public void setCompanyUrl(String value) {
        this.companyUrl = value;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(companyName);
        parcel.writeValue(companyUrl);
    }
}