package com.team1.se2018.closetcloser;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mImageUrl;
    private String mName;
    private String mKey;
    private String mType;
    //private String mrec_1;
    //private String mrec_2;
    //private String mhomepage;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl, String type/**, String rec1url, String rec2url, String homepageadress**/) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        mType= type;

        //mrec_1 = rec1url;
        //mrec_2 = rec2url;
        //mhomepage = homepageadress;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

//    public String getRec1ImageUrl(){return mrec_1;}

//    public String getRec2ImageUrl(){return mrec_2;}

//    public String gethpadress(){return mhomepage}
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}