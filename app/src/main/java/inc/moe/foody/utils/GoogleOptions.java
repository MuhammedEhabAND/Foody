package inc.moe.foody.utils;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public  class GoogleOptions {
    public static GoogleOptions instance = null ;
    public static GoogleOptions getInstance(){
        if(instance == null){
             instance = new GoogleOptions();
        }
        return instance;

    }

    GoogleSignInOptions gso = new GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("342708608392-autqt8fm6ucp8nrs7fqp7p6jo7acj1bs.apps.googleusercontent.com")
            .requestEmail()
            .build();

    public GoogleSignInOptions getGso() {
        return gso;
    }

    private GoogleOptions() {
    }
}
