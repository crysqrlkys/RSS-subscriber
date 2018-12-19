package whyko.labproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.whyko.labproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.Manifest.*;

public class MainActivity extends AppCompatActivity  {
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
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen)
                            bottomNavigationView.setVisibility(View.GONE);
                        else
                            bottomNavigationView.setVisibility(View.VISIBLE);
                    }
                });
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
            case R.id.logout_menu_item:
                finish();
                break;
            case R.id.setting_menu_item:
                navigateToSettings();
                break;
        }
        return true;
    }

    private void navigateToSettings(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
