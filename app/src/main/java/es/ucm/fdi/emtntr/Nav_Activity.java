package es.ucm.fdi.emtntr;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import es.ucm.fdi.emtntr.ui.FavouriteFragment;
import es.ucm.fdi.emtntr.ui.FavouriteListFragment;
import es.ucm.fdi.emtntr.ui.HomeFragment;
import es.ucm.fdi.emtntr.ui.MapFragment;

public class Nav_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(navListener);

        Resources resources = getResources();
        getSupportActionBar().setTitle(resources.getString(R.string.search_fragment_name));
        getSupportActionBar().setIcon(R.drawable.app_logo);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/
    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

            Fragment selectedFragment = null;
            Resources resources = getResources();
            String title = resources.getString(R.string.default_fragment_name);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    title = resources.getString(R.string.search_fragment_name);
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment = new FavouriteListFragment();
                    title = resources.getString(R.string.favourites_fragment_name);
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = new MapFragment();
                    title = resources.getString(R.string.map_fragment_name);
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
            getSupportActionBar().setTitle(title);
            //getSupportActionBar().setIcon(R.drawable.app_logo);
            //getActionBar().setIcon(R.drawable.app_logo);

            return true;
        }
    };

}
