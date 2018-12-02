package com.team1.se2018.closetcloser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String,Object> dataDB = new HashMap<>();
    Map<String,Object> dataDB1 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText cusname = (EditText) findViewById(R.id.nameTextnorm);
        cusname.setPrivateImeOptions("defaultInputmode=korean");
        final String[] ageData = new String[1];
        final String[] genData = new String[1];

        final RadioButton male = (RadioButton) findViewById(R.id.maleButton);
        final RadioButton female = (RadioButton) findViewById(R.id.femaleButton);

        final TextView tv = (TextView) findViewById(R.id.selectedAge);
        final Spinner s = (Spinner)findViewById(R.id.ageSpinner);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ageData[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final CheckBox agtxt1 = (CheckBox) findViewById(R.id.agreeText1reg);
        final CheckBox agtxt2 = (CheckBox) findViewById(R.id.agreeText2reg);
        final CheckBox agtxt3 = (CheckBox) findViewById(R.id.agreeText3reg);

        final Button regButton = (Button) findViewById(R.id.registerButtonreg);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = firebaseAuth.getCurrentUser().toString();
                String nameData = cusname.getText().toString();

                if(male.isChecked()){
                    genData[0] = "M";
                }

                if(female.isChecked()){
                    genData[0] = "F";
                }

                dataDB.put("age",ageData[0]);
                dataDB.put("sex",genData[0]);
                dataDB.put("user_name",nameData);
                dataDB.put("uid",firebaseAuth.getUid());
                db.collection("Normmem").document(firebaseAuth.getUid())
                        .set(dataDB, SetOptions.merge());
                dataDB1.put("uid",firebaseAuth.getUid());
                db.collection("Id_collect").document(firebaseAuth.getUid())
                        .set(dataDB1, SetOptions.merge());
                Intent registerIntent = new Intent(RegisterActivity.this, MainMenuActivity.class);
                RegisterActivity.this.startActivity(registerIntent);
            }
        });
    }
}
