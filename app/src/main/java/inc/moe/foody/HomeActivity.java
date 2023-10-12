package inc.moe.foody;

import static java.security.AccessController.getContext;

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import inc.moe.foody.auth_feature.view.MainActivity;
import inc.moe.foody.favourite_feature.view.FavFragment;
import inc.moe.foody.full_details_feature.view.DetailedMeal;
import inc.moe.foody.home_feature.view.HomeFragment;
import inc.moe.foody.search_feature.view.SearchFragment;


public class HomeActivity extends AppCompatActivity  {
    public static NavController navController;
    int currentDestinationId =0;
    int startDestinationId = R.id.homeFragment;

    boolean isUser;
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

        NavDestination currentDestination = navController.getCurrentDestination();
        if(currentDestination!=null){
            currentDestinationId = currentDestination.getId();

        }
        if (currentDestinationId != startDestinationId) {
            navController.navigateUp();

        } else {
            super.onBackPressed();
            finishAffinity();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}