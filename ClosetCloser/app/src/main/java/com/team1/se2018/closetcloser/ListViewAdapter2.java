package com.team1.se2018.closetcloser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ListViewAdapter2 extends BaseAdapter {

    ArrayList<SRListItem2> items = new ArrayList<>();
    private Context mContext;

    public ListViewAdapter2(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(SRListItem2 item){
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

        SRListItemView2 view;

        if(convertView == null){
            view  = new SRListItemView2(mContext);

//            imageView.setLayoutParams(new ViewGroup.LayoutParams(350,350));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8,8,8,8);

        }else{
            view = (SRListItemView2) convertView;
        }

        SRListItem2 item = items.get(position);

        view.setCodiImageOuter(item.getCodiimageouter_sr2());
        view.setCodiImageTop(item.getCodiimagetop_sr2());
        view.setCodiImageBottom(item.getCodiimagebottom_sr2());
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
