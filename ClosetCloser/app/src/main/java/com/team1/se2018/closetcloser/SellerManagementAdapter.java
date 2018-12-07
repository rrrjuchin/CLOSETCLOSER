package com.team1.se2018.closetcloser;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by guswn_000 on 2017-04-13.
 */




public class SellerManagementAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<SellerManagement> datalist = new ArrayList<>();
    private ArrayList<SellerManagement> filteredItemList = new ArrayList<>();
    Filter listFilter;
    ArrayList<Integer> checked = new ArrayList<>();

    public SellerManagementAdapter(Context context, ArrayList<SellerManagement> list) {
        this.context = context;
        this.datalist = list;
        this.filteredItemList = list;
    }

    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Comparator<SellerManagement> nameAsc = new Comparator<SellerManagement>() {
        @Override
        public int compare(SellerManagement o1, SellerManagement o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    Comparator<SellerManagement> catAsc = new Comparator<SellerManagement>() {
        @Override
        public int compare(SellerManagement o1, SellerManagement o2) {
            if (o1.getCategorynum() < o2.getCategorynum())
                return -1;
            else if (o1.getCategorynum() > o2.getCategorynum())
                return 1;
            else
                return 0;
        }
    };

    public void setNameAsc()
    {
        Collections.sort(filteredItemList, nameAsc);
        this.notifyDataSetChanged();
    }

    public void setCatAsc() {
        Collections.sort(filteredItemList, catAsc);
        this.notifyDataSetChanged();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_restaurant, null);
        }
        TextView t1 = (TextView) convertView.findViewById(R.id.tvname);
        TextView t2 = (TextView) convertView.findViewById(R.id.tvtelnum);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView);
        CheckBox c1 = (CheckBox)convertView.findViewById(R.id.checkBox);

        SellerManagement one = filteredItemList.get(position);

//        filteredItemList.set(position, one);
//        datalist.set(position, one);
        one.setCheckBox(c1);

        t1.setText(filteredItemList.get(position).getName());
        t2.setText(filteredItemList.get(position).getTel());
        if(one.getCategorynum() == 1)//치킨=1,피자=2,햄버거=3
        {
            img.setImageResource(R.drawable.chicken);
        }
        else if(one.getCategorynum() == 2)
        {
            img.setImageResource(R.drawable.pizza);
        }
        else
        {
            img.setImageResource(R.drawable.hamburger);
        }

        return convertView;
    }



    @Override
    public Filter getFilter()
    {
        if (listFilter == null)
        {
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0)
            {
                results.values = datalist;
                results.count = datalist.size();
            }
            else
            {
                ArrayList<SellerManagement> itemList = new ArrayList<>();
                for (SellerManagement item : datalist)
                {
                    if (item.getName().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item);
                    }
                }
                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            filteredItemList = (ArrayList<SellerManagement>) results.values;
            if (results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
            {
                notifyDataSetInvalidated();
            }
        }
    }

    //체크박스

    public void showCheckBox(){
        for(int i = 0 ; i < filteredItemList.size() ; i++ )
        {
            filteredItemList.get(i).getCheckBox().setVisibility(View.VISIBLE);
        }
        this.notifyDataSetChanged();
    }

    public void deleteitem() {
        for (int i = 0 ; i < filteredItemList.size() ; i++) {
            final int index = i;
            if (filteredItemList.get(i).getCheckBox().isChecked()) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle("삭제확인")
                        .setIcon(R.drawable.potato)
                        .setMessage("선택한 맛집을 정말 삭제할까요?")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                filteredItemList.get(index).getCheckBox().setChecked(false);
                                filteredItemList.get(index).getCheckBox().setVisibility(View.INVISIBLE);
                                filteredItemList.remove(index);
                                notifyDataSetChanged();
                            }
                        })
                        .show();
            }
            else
            {
                filteredItemList.get(i).getCheckBox().setVisibility(View.INVISIBLE);
            }
        }
    }

}
