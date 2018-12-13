package com.team1.se2018.closetcloser;

import com.google.firebase.database.Exclude;

public class SRUpload {
    private String mImageUrl;
    private String mrec_1;
    private String mrec_2;
    //private String mhomepage;

    public SRUpload() {
        //empty constructor needed
    }

    public SRUpload(String imageUrl, String rec1url, String rec2url) {
        mImageUrl = imageUrl;

        mrec_1 = rec1url;
        mrec_2 = rec2url;
        //mhomepage = homepageadress;
    }



    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getRec1ImageUrl(){return mrec_1;}

    public String getRec2ImageUrl(){return mrec_2;}

    //    public String gethpadress(){return mhomepage}
}