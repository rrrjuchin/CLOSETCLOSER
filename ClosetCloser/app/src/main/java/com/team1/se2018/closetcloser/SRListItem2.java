package com.team1.se2018.closetcloser;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class SRListItem2 implements Parcelable
{
    private String mallname_sr2;
    private String itemname_sr2;
    private String itemprice_sr2;
    private Bitmap codiimageouter_sr2;
    private Bitmap codiimagetop_sr2;
    private Bitmap codiimagebottom_sr2;

    public SRListItem2(String mallname_sr2, String itemname_sr2, String itemprice_sr2,
                       Bitmap codiimageouter_sr2, Bitmap codiimagetop_sr2, Bitmap codiimagebottom_sr2)
    {
        this.mallname_sr2 = mallname_sr2;
        this.itemname_sr2 = itemname_sr2;
        this.itemprice_sr2 = itemprice_sr2;
        this.codiimageouter_sr2 = codiimageouter_sr2;
        this.codiimagetop_sr2 = codiimagetop_sr2;
        this.codiimagebottom_sr2 = codiimagebottom_sr2;
    }

    protected SRListItem2(Parcel in) {
        mallname_sr2 = in.readString();
        itemname_sr2 = in.readString();
        itemprice_sr2 = in.readString();
        codiimageouter_sr2 = in.readParcelable(Bitmap.class.getClassLoader());
        codiimagetop_sr2 = in.readParcelable(Bitmap.class.getClassLoader());
        codiimagebottom_sr2 = in.readParcelable(Bitmap.class.getClassLoader());
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
        dest.writeString(mallname_sr2);
        dest.writeString(itemname_sr2);
        dest.writeString(itemprice_sr2);
        dest.writeValue(codiimageouter_sr2);
        dest.writeValue(codiimagetop_sr2);
        dest.writeValue(codiimagebottom_sr2);
    }

    public String getShopName() {
        return mallname_sr2;
    }

    public String getItemName() {
        return itemname_sr2;
    }

    public String getPrice() {
        return itemprice_sr2;
    }

    public Bitmap getCodiimageouter_sr2(){
        return codiimageouter_sr2;
    }

    public Bitmap getCodiimagetop_sr2(){
        return codiimagetop_sr2;
    }

    public Bitmap getCodiimagebottom_sr2(){
        return codiimagebottom_sr2;
    }

    public void setShopName(String mallname) {
        this.mallname_sr2 = mallname;
    }

    public void setItemName(String itemName) {
        this.itemname_sr2 = itemName;
    }

    public void setPrice(String price) {
        this.itemprice_sr2 = price;
    }

    public void setCodiimageouter_sr2(Bitmap codiimage) {
        this.codiimageouter_sr2 = codiimage;
    }

    public void setCodiimagetop_sr2(Bitmap codiimage) {
        this.codiimagetop_sr2 = codiimage;
    }

    public void setCodiimagebottom_sr2(Bitmap codiimage) {
        this.codiimagebottom_sr2 = codiimage;
    }
}