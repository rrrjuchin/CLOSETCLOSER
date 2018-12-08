package com.team1.se2018.closetcloser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MallMenuActivity extends AppCompatActivity {

    SellerManagementAdapter adapter2;

    ArrayList<SellerManagement> restlist = new ArrayList<SellerManagement>();
    ListView listView;
    final int REST_INFO = 21;
    final int NEW_REST = 22;
    Button seldel;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_menu);
        setTitle("나의 상품");
        setListView();
        seldel = (Button) findViewById(R.id.btnselect);
        et = (EditText) findViewById(R.id.editText);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String filterText = s.toString();
                if (filterText.length() > 0) {
                    listView.setFilterText(filterText);
                } else {
                    listView.clearTextFilter();
                }
            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plus:
                System.out.println("??");

                Intent intent = new Intent(MallMenuActivity.this, SellerAddItem.class);
                System.out.println("!!");
                startActivity(intent);
                finish();
                System.out.println("wow");

                break;

            case R.id.btnnamesort:
                adapter2.setNameAsc();
                break;
            case R.id.btncat:
                adapter2.setCatAsc();
                break;
            case R.id.btnselect:
                if (seldel.getText().toString().equals("선택")) {
                    seldel.setText("삭제");
                    adapter2.showCheckBox();
                } else {
                    seldel.setText("선택");
                    adapter2.deleteitem();
                }


                break;
        }
    }

    public void setListView() {
        listView = (ListView) findViewById(R.id.listview);
        adapter2 = new SellerManagementAdapter(this, restlist);
        listView.setAdapter(adapter2);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MallMenuActivity.this, Main3Activity.class);
                SellerManagement res = restlist.get(position);
                intent.putExtra("restinfo", res);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_REST) {
            if (resultCode == RESULT_OK) {
                SellerManagement res = data.getParcelableExtra("newrest");
                restlist.add(res);
                adapter2.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


