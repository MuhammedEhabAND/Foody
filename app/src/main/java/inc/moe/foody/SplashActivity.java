package inc.moe.foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import inc.moe.foody.auth_feature.view.MainActivity;
import inc.moe.foody.utils.NetworkConnection;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                if(NetworkConnection.isConnected(getApplicationContext())){


                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                 } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                }else{
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                 }
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}