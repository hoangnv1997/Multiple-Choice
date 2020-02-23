package com.hoangnguyen.multiplechoice.activity;

import android.content.Context;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.hoangnguyen.multiplechoice.R;
import com.hoangnguyen.multiplechoice.fragment.BiologyFragment;
import com.hoangnguyen.multiplechoice.fragment.ChemistryFragment;
import com.hoangnguyen.multiplechoice.fragment.HomeFragment;
import com.hoangnguyen.multiplechoice.fragment.MathFragment;
import com.hoangnguyen.multiplechoice.fragment.PhysicsFragment;
import com.hoangnguyen.multiplechoice.fragment.SearchQuestionFragment;
import com.hoangnguyen.multiplechoice.utilities.DBHelper;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        Fragment fragment = new HomeFragment();
        loadFragment(fragment);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DBHelper dbHelper = new DBHelper(this);
//        //clear database lúc trước import trong thư mục assets
//
//        try {
//            dbHelper.deleteDataBase();
//            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "bi loi rui", Toast.LENGTH_SHORT).show();
//        }

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                loadFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_math:
                fragment = new MathFragment();
                loadFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_physics:
                fragment = new PhysicsFragment();
                loadFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_chemistry:
                fragment = new ChemistryFragment();
                loadFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_biology:
                fragment = new BiologyFragment();
                loadFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_search:
                fragment = new SearchQuestionFragment();
                loadFragment(fragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }

        return false;
    }


}
