package com.team1.se2018.closetcloser;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Fragment4 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signOut();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("normal_tag",3);
        startActivity(intent);
        return inflater.inflate(R.layout.activity_fragment4,container,false);
    }
}
