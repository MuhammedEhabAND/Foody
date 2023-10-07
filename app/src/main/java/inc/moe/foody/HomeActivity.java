package inc.moe.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import inc.moe.foody.favourite_feature.view.FavFragment;
import inc.moe.foody.full_details_feature.view.DetailedMeal;
import inc.moe.foody.home_feature.view.HomeFragment;
import inc.moe.foody.search_feature.view.SearchFragment;


public class HomeActivity extends AppCompatActivity  {
    public static NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         BottomNavigationView bottomNavigationView = findViewById(R.id.navigator_bar);
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);

         NavigationUI.setupWithNavController(bottomNavigationView, navController);

         navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
             Log.d("Navigation", "Navigated to destination: " + destination.getLabel());

         });

     }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        // Get the NavController
        NavController navController = navHostFragment.getNavController();

        // Get the current destination ID
        int currentDestinationId = navController.getCurrentDestination().getId();

        // Get the start destination ID from your navigation graph XML
        int startDestinationId = R.id.homeFragment; // Replace with the actual ID

        // Check if the current destination is the same as the start destination
        if (currentDestinationId != startDestinationId) {
            // Pop the back stack if the current destination is not the start destination
            navController.popBackStack();
        } else {
            super.onBackPressed();

            finishAffinity();
        }
            // If there are no fragments in the stack, exit the HomeActivity

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

}