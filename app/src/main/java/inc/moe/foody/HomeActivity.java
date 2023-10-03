package inc.moe.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
    boolean isBottomNavigationDestination = false;
    int[] bottomNavigationIds = new int[]{R.id.homeFragment, R.id.searchFragment, R.id.favFragment };
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

}