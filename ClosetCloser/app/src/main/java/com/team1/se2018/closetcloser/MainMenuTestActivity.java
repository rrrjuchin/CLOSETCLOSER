package com.team1.se2018.closetcloser;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import static com.team1.se2018.closetcloser.DRChildFragment.*;

public class MainMenuTestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DailyRecommendationActivity.OnFragmentInteractionListener,DRChildFragment.OnFragmentInteractionListener, DRChildFragment2.OnFragmentInteractionListener, DRChildFragment3.OnFragmentInteractionListener, MCChildFragment.OnFragmentInteractionListener,MCChildFragment2.OnFragmentInteractionListener,MCChildFragment3.OnFragmentInteractionListener,MyClosetActivity.OnFragmentInteractionListener {
    private DrawerLayout drawer;

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyClosetActivity()).commit();
                break;
            case R.id.nav_shop:
                break;
            case R.id.nav_signout:
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
    public void messageFromParentFragment(Uri uri) {

    }
}
