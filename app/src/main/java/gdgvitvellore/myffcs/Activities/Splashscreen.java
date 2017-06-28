package gdgvitvellore.myffcs.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 21/6/17.
 */

public class Splashscreen extends AppCompatActivity {
    public static int DelayedTime=1000;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        sharedPreferences=getApplicationContext().getSharedPreferences("Prefs",MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getBoolean("isLoggedIn",false)==false) {
                    Intent mainIntent = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else{
                    Intent mainIntent = new Intent(Splashscreen.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        },DelayedTime);
    }
}
