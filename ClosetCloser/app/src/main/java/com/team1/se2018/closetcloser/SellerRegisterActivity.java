package com.team1.se2018.closetcloser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SellerRegisterActivity extends AppCompatActivity {

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email = "";
    final String emailData = "";
    private String password = "";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String,Object> dataDB = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        editTextEmail = findViewById(R.id.mallemailText);
        editTextPassword = findViewById(R.id.mallpassText);
        final EditText mallname = (EditText) findViewById(R.id.mallnameText);

        final Button regButton = (Button) findViewById(R.id.mallregisterButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                final String editmallname = mallname.getText().toString();
                if (isValidEmail() && isValidPasswd()) {
                    createUser(email, password);
                    dataDB.put("email", email);
                    //dataDB.put("uid",uidDataFrom);
                    dataDB.put("mall_name", editmallname);
                    db.collection("Seller").document(editmallname)
                            .set(dataDB, SetOptions.merge());

                    Intent registerIntent = new Intent(SellerRegisterActivity.this, SellerLoginActivity.class);
                    registerIntent.putExtra("mallname", editmallname);
                    registerIntent.putExtra("tag", 1);
                    SellerRegisterActivity.this.startActivity(registerIntent);
                }
            }
        });

    }

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 회원가입
    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            Toast.makeText(SellerRegisterActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();

                        } else {

                            // 회원가입 실패
                            Toast.makeText(SellerRegisterActivity.this, R.string.failed_signup, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
