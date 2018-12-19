package com.example.whyko.labproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static android.Manifest.*;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private int currentFragmentId = R.id.profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        navController = Navigation.findNavController(this,R.id.main_content);
        bottomNavigationView = findViewById(R.id.bottom_main_navigation);
        BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.profile_item:
                        if(currentFragmentId != R.id.profile){
                            navController.navigate(R.id.profile);
                            currentFragmentId = R.id.profile;
                        }
                        return true;
                    case R.id.rss_item:
                        if(currentFragmentId != R.id.rssReader){
                            navController.navigate(R.id.rssReader);
                            currentFragmentId = R.id.rssReader;
                        }
                        return true;
                    case R.id.favorites_item:
                        if(currentFragmentId != R.id.favorites_item){
                            navController.navigate(R.id.favorites);
                            currentFragmentId = R.id.favorites;
                        }
                        return true;
                    case R.id.likes_item:
                        if(currentFragmentId != R.id.likes){
                            navController.navigate(R.id.likes);
                            currentFragmentId = R.id.likes;
                        }
                        return true;
                }
                return false;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_menu_item:
                navController.navigate(R.id.about);
                break;
        }
        return true;
    }
}
