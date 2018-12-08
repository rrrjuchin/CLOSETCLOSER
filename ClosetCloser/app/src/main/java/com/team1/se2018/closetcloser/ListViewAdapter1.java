package com.team1.se2018.closetcloser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ListViewAdapter1 extends BaseAdapter {

    ArrayList<SRListItem1> items = new ArrayList<>();
    private Context mContext;

    public ListViewAdapter1(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(SRListItem1 item){
        items.add(item);
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SRListItemView1 view;

        if(convertView == null){
            view  = new SRListItemView1(mContext);

//            imageView.setLayoutParams(new ViewGroup.LayoutParams(350,350));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8,8,8,8);

        }else{
            view = (SRListItemView1) convertView;
        }

        SRListItem1 item = items.get(position);

        view.setItemImage(item.getItemimage_sr1());
        view.setCodiImageOuter(item.getCodiimageouter_sr1());
        view.setCodiImageTop(item.getCodiimagetop_sr1());
        view.setCodiImageBottom(item.getCodiimagebottom_sr1());
        view.setMallName(item.getShopName());
        view.setItemName(item.getItemName());
        view.setItemPrice(item.getPrice());
        //view.gotoShop();

        //imageView.setImageResource(mThumbIds[position]);

        return view;
    }

//    private Integer[] mThumbIds = {
//            R.drawable.logo,R.drawable.right,R.drawable.logo, R.drawable.icon_mycloset, R.drawable.icon_daily
//    };

}