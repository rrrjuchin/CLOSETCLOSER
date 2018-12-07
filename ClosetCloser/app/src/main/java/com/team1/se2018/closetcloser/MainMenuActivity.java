package com.team1.se2018.closetcloser;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DailyRecommendationActivity.OnFragmentInteractionListener,
        DRChildFragment.OnFragmentInteractionListener,
        DRChildFragment2.OnFragmentInteractionListener,
        DRChildFragment3.OnFragmentInteractionListener,
        MCChildFragmentOuter.OnFragmentInteractionListener,
        MCChildFragment2.OnFragmentInteractionListener,
        MCChildFragment3.OnFragmentInteractionListener,
        MyClosetActivity.OnFragmentInteractionListener,
        ShoppingActivity.OnFragmentInteractionListener,
        SRChildFragment.OnFragmentInteractionListener,
        SRChildFragment2.OnFragmentInteractionListener,
        SRChildFragment3.OnFragmentInteractionListener,
        BadFeedbackBSD.BottomSheetListener,
        GoodFeedbackBSD.BottomSheetListener {

    private DrawerLayout drawer;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LoginManager fblogin = LoginManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_test);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DailyRecommendationActivity()).commit();
            navigationView.setCheckedItem(R.id.nav_daily);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_daily:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DailyRecommendationActivity()).commit();
                break;
            case R.id.nav_mycloset:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyClosetActivity()).commit();
                break;
            case R.id.nav_shop:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ShoppingActivity()).commit();
                break;
            case R.id.nav_signout:
                openDialogSO();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void messageFromChildFragment(Uri uri) {
        Log.i("TAG", "received communication from child fragment");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onButtonClicked(String text) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromParentFragment(Uri uri);
    }

    @Override
    public void messageFromParentFragment(Uri uri) {

    }


    private void openDialogSO(){
        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton(R.string.Confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        fblogin.logOut();
                        mAuth.signOut();
                        Intent i =new Intent(MainMenuActivity.this,MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}
