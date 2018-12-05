package com.team1.se2018.closetcloser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by guswn_000 on 2017-04-06.
 */

public class SRListItemView1 implements Parcelable
{
    private String sn_sr1;
    private String in_sr1;
    private String price_sr1;

    public SRListItemView1(String sn_sr1, String in_sr1, String price_sr1)
    {
        this.sn_sr1 = sn_sr1;
        this.in_sr1 = in_sr1;
        this.price_sr1 = price_sr1;
    }

    protected SRListItemView1(Parcel in) {
        sn_sr1 = in.readString();
        in_sr1 = in.readString();
        price_sr1 = in.readString();
    }

    public static final Creator<SellerManagement> CREATOR = new Creator<SellerManagement>() {
        @Override
        public SellerManagement createFromParcel(Parcel in) {
            return new SellerManagement(in);
        }

        @Override
        public SellerManagement[] newArray(int size) {
            return new SellerManagement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sn_sr1);
        dest.writeString(in_sr1);
        dest.writeString(price_sr1);
    }

    public String getShopName() {
        return sn_sr1;
    }

    public String getItemName() {
        return in_sr1;
    }

    public String getPrice()
    {
        return price_sr1;
    }
    public void setShopName(String snname) {
        this.sn_sr1 = snname;
    }

    public void setItemName(String itemName) {
        this.in_sr1 = itemName;
    }

    public void setPrice(String price) {
        this.price_sr1 = price;
    }
}