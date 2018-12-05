package com.team1.se2018.closetcloser;

import android.os.Parcel;
import android.os.Parcelable;

public class OuterImageModel implements Parcelable {

    private String name, url;


    protected OuterImageModel(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<OuterImageModel> CREATOR = new Creator<OuterImageModel>() {
        @Override
        public OuterImageModel createFromParcel(Parcel in) {
            return new OuterImageModel(in);
        }

        @Override
        public OuterImageModel[] newArray(int size) {
            return new OuterImageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }
}
