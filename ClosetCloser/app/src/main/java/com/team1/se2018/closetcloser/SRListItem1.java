package com.team1.se2018.closetcloser;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class SRListItem1 implements Parcelable
{
    private String mallname_sr1;
    private String itemname_sr1;
    private String itemprice_sr1;
    private Bitmap itemimage_sr1;
    private Bitmap codiimageouter_sr1;
    private Bitmap codiimagetop_sr1;
    private Bitmap codiimagebottom_sr1;

    public SRListItem1(String mallname_sr1, String itemname_sr1, String itemprice_sr1,
                       Bitmap itemimage_sr1, Bitmap codiimageouter_sr1, Bitmap codiimagetop_sr1, Bitmap codiimagebottom_sr1)
    {
        this.mallname_sr1 = mallname_sr1;
        this.itemname_sr1 = itemname_sr1;
        this.itemprice_sr1 = itemprice_sr1;
        this.itemimage_sr1 = itemimage_sr1;
        this.codiimageouter_sr1 = codiimageouter_sr1;
        this.codiimagetop_sr1 = codiimagetop_sr1;
        this.codiimagebottom_sr1 = codiimagebottom_sr1;
    }

    protected SRListItem1(Parcel in) {
        mallname_sr1 = in.readString();
        itemname_sr1 = in.readString();
        itemprice_sr1 = in.readString();
        itemimage_sr1 = in.readParcelable(Bitmap.class.getClassLoader());
        codiimageouter_sr1 = in.readParcelable(Bitmap.class.getClassLoader());
        codiimagetop_sr1 = in.readParcelable(Bitmap.class.getClassLoader());
        codiimagebottom_sr1 = in.readParcelable(Bitmap.class.getClassLoader());
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
        dest.writeString(mallname_sr1);
        dest.writeString(itemname_sr1);
        dest.writeString(itemprice_sr1);
        dest.writeValue(itemimage_sr1);
        dest.writeValue(codiimageouter_sr1);
        dest.writeValue(codiimagetop_sr1);
        dest.writeValue(codiimagebottom_sr1);
    }

    public String getShopName() {
        return mallname_sr1;
    }

    public String getItemName() {
        return itemname_sr1;
    }

    public String getPrice() {
        return itemprice_sr1;
    }

    public Bitmap getItemimage_sr1(){
        return itemimage_sr1;
    }

    public Bitmap getCodiimageouter_sr1(){
        return codiimageouter_sr1;
    }

    public Bitmap getCodiimagetop_sr1(){
        return codiimagetop_sr1;
    }

    public Bitmap getCodiimagebottom_sr1(){
        return codiimagebottom_sr1;
    }

    public void setShopName(String mallname) {
        this.mallname_sr1 = mallname;
    }

    public void setItemName(String itemName) {
        this.itemname_sr1 = itemName;
    }

    public void setPrice(String price) {
        this.itemprice_sr1 = price;
    }

    public void setItemimage_sr1(Bitmap itemimage) {
        this.itemimage_sr1 = itemimage;
    }

    public void setCodiimageouter_sr1(Bitmap codiimage) {
        this.codiimageouter_sr1 = codiimage;
    }

    public void setCodiimagetop_sr1(Bitmap codiimage) {
        this.codiimagetop_sr1 = codiimage;
    }

    public void setCodiimagebottom_sr1(Bitmap codiimage) {
        this.codiimagebottom_sr1 = codiimage;
    }
}