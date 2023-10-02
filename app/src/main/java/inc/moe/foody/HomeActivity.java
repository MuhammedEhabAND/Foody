package inc.moe.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity {
    NavController navController;
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