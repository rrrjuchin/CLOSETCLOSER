package com.team1.se2018.closetcloser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterEmailActivity extends AppCompatActivity {

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    Map<String,Object> dataDB = new HashMap<>();
    Map<String,Object> dataDB1 = new HashMap<>();

    private String email = null;
    private String password = null;
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
        setContentView(R.layout.activity_registeremail);
        db = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();

        male = (RadioButton) findViewById(R.id.maleButton);
        female = (RadioButton) findViewById(R.id.femaleButton);

        final EditText InputEmail = findViewById(R.id.email);
        final EditText InputPassword = findViewById(R.id.pwd);
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

                email = InputEmail.getText().toString();
                password = InputPassword.getText().toString();
                name = cusname.getText().toString();

                if(isValidEmail() && isValidPassword() &&isValidName() && isValidAge() && isValidGender()) {


                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterEmailActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("create", "createUserWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();

                                        if (user == null){
                                            Log.d("create", "current user is null");
                                            Toast.makeText(getApplicationContext(), R.string.failed_signup +" 다시 시도해주세요.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                        else{
                                            dataDB.put("age",age);
                                            dataDB.put("sex", gender);
                                            dataDB.put("user_name", name);
                                            dataDB.put("uid", user.getUid());
                                            db.collection("Normmem").document(user.getUid())
                                                    .set(dataDB, SetOptions.merge());
                                            dataDB1.put("uid",firebaseAuth.getUid());
                                            db.collection("Id_collect").document(user.getUid())
                                                    .set(dataDB1, SetOptions.merge());

                                            Toast.makeText(getApplicationContext(), R.string.success_signup,
                                                    Toast.LENGTH_SHORT).show();

                                            finish();

                                        }


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d("create", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), R.string.failed_signup,
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });
    }

    public boolean isValidEmail(){

        if (email==null || email.isEmpty()) {
            // 이메일 공백
            Toast.makeText(getApplicationContext(), "이메일을 입력해주십시오", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "이메일을 정확히 입력해주십시오", Toast.LENGTH_SHORT).show();
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }

    }
    public boolean isValidPassword(){

        if (password==null || password.isEmpty()) {
            // 비밀번호 공백
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주십시오", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(getApplicationContext(), "비밀번호는 6자이상 16자 미만의 영문자, 숫자, 특수문자 조합이어야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

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
}