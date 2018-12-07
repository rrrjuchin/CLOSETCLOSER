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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    FirebaseFirestore db;
    Map<String,Object> dataDB = new HashMap<>();
    Map<String,Object> dataDB1 = new HashMap<>();

    private String name = null;
    private String age = null;
    private String gender = null;

    private RadioButton male;
    private RadioButton female;
    private Spinner ageSpinner;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,15}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        male = (RadioButton) findViewById(R.id.maleButton);
        female = (RadioButton) findViewById(R.id.femaleButton);

        final EditText cusname = (EditText) findViewById(R.id.nameTextnorm);
        cusname.setPrivateImeOptions("defaultInputmode=korean");

        ageSpinner = (Spinner)findViewById(R.id.ageSpinner);

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age = parent.getItemAtPosition(position).toString();
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

                name = cusname.getText().toString();

                if( isValidName() && isValidAge() && isValidGender() ) {

                    dataDB.put("age",age);
                    dataDB.put("sex", gender);
                    dataDB.put("user_name", name);
                    dataDB.put("uid", firebaseUser.getUid());
                    db.collection("Normmem").document(firebaseUser.getUid())
                            .set(dataDB, SetOptions.merge());
                    dataDB1.put("uid",firebaseAuth.getUid());
                    db.collection("Id_collect").document(firebaseUser.getUid())
                            .set(dataDB1, SetOptions.merge());

                    Toast.makeText(getApplicationContext(), R.string.success_signup,
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), LoginEmailActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }


    public boolean isValidName(){

        if (name==null || name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "이름을 입력해 주십시오", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    public boolean isValidAge(){

        if (age==null || age.equals(ageSpinner.getItemAtPosition(0))) {
            Toast.makeText(getApplicationContext(), "나이를 입력해 주십시오", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    public boolean isValidGender(){
        if(male.isChecked()){
            gender = "M";
            return true;
        }
        else if(female.isChecked()){
            gender = "F";
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(), "성별을 입력해 주십시오", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed()
    {
        firebaseUser.delete();
        super.onBackPressed();
    }


}