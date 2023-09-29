package inc.moe.foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private Scene loginScene , signUpScene;
    private RadioGroup authRadioGroup;
    private boolean isSignUp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout container = findViewById(R.id.frameLayout);

        loginScene = Scene.getSceneForLayout(container, R.layout.login, this);
        signUpScene = Scene.getSceneForLayout(container, R.layout.sign_up, this);
        authRadioGroup = findViewById(R.id.auth_radio_group);
        TransitionManager.go(loginScene);
        authRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchScene();
            }
        });
    }
    private void switchScene() {
        // Animation Transtion
        Transition fade = new ChangeBounds();
        if (isSignUp) {
            TransitionManager.go(loginScene, fade);
        } else {
            TransitionManager.go(signUpScene, fade);
        }
        isSignUp = !isSignUp;
    }
    public void onLoginClicked(View view){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    public void onSignUpClicked(View view){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}