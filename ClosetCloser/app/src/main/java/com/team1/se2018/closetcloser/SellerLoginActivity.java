package com.team1.se2018.closetcloser;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SellerLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String email = "";
    private String password = "";
    int tag = 0;
    String mallname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    mAuth.signOut();
                }else{

                }

            }

        };



        Intent savedData = getIntent();
        System.out.println(mAuth.getCurrentUser());

        if (mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }
        System.out.println(mAuth.getCurrentUser());
        mallname = savedData.getStringExtra("mallname");
        tag = savedData.getIntExtra("tag", 0 );
        System.out.println("PRINT>>>>>>>>>>>>>>>>>>>>>>>>>>>"+tag);

        if(tag ==1 ){
            openDialogE5();
        }

        editTextEmail = findViewById(R.id.idText);
        editTextPassword = findViewById(R.id.passwordText);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    로그인 정보가 맞다면 판매자 메인화면으로 이동
                 */

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if(isValidEmail() && isValidPasswd()) {
                    loginUser(email, password);

                }else if(!isValidEmail()){
                    openDialogE3();
                }else if(!isValidPasswd()){
                    openDialogE4();
                }
            }
        });

        TextView registerButton = (TextView) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SellerRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void openDialogE3(){
        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(R.string.ERROR3)
                .setPositiveButton(R.string.Confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }
    private void openDialogE4(){
        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(R.string.ERROR4)
                .setPositiveButton(R.string.Confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }
    private void openDialogE5(){
        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(R.string.MSG1)
                .setPositiveButton(R.string.Confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
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
    // 로그인
    private void loginUser(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(SellerLoginActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
                            Intent registerIntent = new Intent(SellerLoginActivity.this, MallMenuActivity.class);
                            if(tag == 1){
                                registerIntent.putExtra("tag",1);
                                registerIntent.putExtra("mallname",mallname);
                            }
                            //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 여ㅛ기까지");
                            SellerLoginActivity.this.startActivity(registerIntent);
                        } else {
                            // 로그인 실패
                            Toast.makeText(SellerLoginActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}