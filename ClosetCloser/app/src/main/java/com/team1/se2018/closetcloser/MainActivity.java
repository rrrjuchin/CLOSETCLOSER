package com.team1.se2018.closetcloser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    // 구글  로그인 버튼
    private Button buttonSeller;
    private CallbackManager callbackManager;

    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference coll = db.collection("Id_collect");
    Map<String,Object> data1 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    FirebaseAuth.getInstance().signOut();
                }else{

                }
            }
        };
        LoginButton buttonGoogle = (LoginButton) findViewById(R.id.login_button);

        System.out.println(firebaseAuth.getCurrentUser());
        if(firebaseAuth.getCurrentUser() != null){
            System.out.println(firebaseAuth.getUid());
            Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
            startActivity(intent);
            finish();
        }

        buttonGoogle.setReadPermissions("email");
        buttonGoogle.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                System.out.println(firebaseAuth.getUid());
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        buttonSeller = findViewById(R.id.sellerButton);
        buttonSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("판매자로 로그인");
                //System.out.println();
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if(user != null){
                            FirebaseAuth.getInstance().signOut();
                        }else{

                        }
                    }
                };
                Intent intent = new Intent(getApplicationContext(),SellerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode,resultCode,data);
    }
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential =
                FacebookAuthProvider.getCredential(token.getToken());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(credential);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(MainActivity.this,
                                    R.string.success_login, Toast.LENGTH_SHORT).show();
                        } else {
                            // 로그인 실패
                            Toast.makeText(MainActivity.this,
                                    R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
