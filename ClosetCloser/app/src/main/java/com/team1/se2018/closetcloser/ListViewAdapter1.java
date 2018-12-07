package com.team1.se2018.closetcloser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ListViewAdapter1 extends BaseAdapter {

    private Context mContext;

    public ListViewAdapter1(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(350,350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }else{
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    private Integer[] mThumbIds = {
            R.drawable.logo,R.drawable.right,R.drawable.logo, R.drawable.icon_mycloset, R.drawable.icon_daily
    };
}