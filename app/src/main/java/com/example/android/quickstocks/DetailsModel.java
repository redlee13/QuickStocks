package com.example.android.quickstocks;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailsModel implements Parcelable {

    private String companyName;
    private String change;
    private String percentChange;
    private String lastClose;
    private String highestPrice;
    private String lowestPrice;
    private String totalDeals;
    private String dealsAmount;

    public DetailsModel(String companyName, String change, String percentChange, String lastClose, String highestPrice, String lowestPrice, String totalDeals, String dealsAmount) {
        this.companyName = companyName;
        this.change = change;
        this.percentChange = percentChange;
        this.lastClose = lastClose;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.totalDeals = totalDeals;
        this.dealsAmount = dealsAmount;
    }

    protected DetailsModel(Parcel in) {
        companyName = in.readString();
        change = in.readString();
        percentChange = in.readString();
        lastClose = in.readString();
        highestPrice = in.readString();
        lowestPrice = in.readString();
        totalDeals = in.readString();
        dealsAmount = in.readString();
    }

    public static final Creator<DetailsModel> CREATOR = new Creator<DetailsModel>() {
        @Override
        public DetailsModel createFromParcel(Parcel in) {
            return new DetailsModel(in);
        }

        @Override
        public DetailsModel[] newArray(int size) {
            return new DetailsModel[size];
        }
    };

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(String percentChange) {
        this.percentChange = percentChange;
    }

    public String getLastClose() {
        return lastClose;
    }

    public void setLastClose(String lastClose) {
        this.lastClose = lastClose;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getTotalDeals() {
        return totalDeals;
    }

    public void setTotalDeals(String totalDeals) {
        this.totalDeals = totalDeals;
    }

    public String getDealsAmount() {
        return dealsAmount;
    }

    public void setDealsAmount(String dealsAmount) {
        this.dealsAmount = dealsAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(companyName);
        parcel.writeValue(change);
        parcel.writeValue(percentChange);
        parcel.writeValue(lastClose);
        parcel.writeValue(highestPrice);
        parcel.writeValue(lowestPrice);
        parcel.writeValue(totalDeals);
        parcel.writeValue(dealsAmount);
    }
}
