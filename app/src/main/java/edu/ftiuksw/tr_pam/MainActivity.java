package edu.ftiuksw.tr_pam;
//api access key unsplash : xPWzLrTW_O7ENshovgkp8Ipz4EkbiwOJZ_aKnQsv6xI
//api secret key unsplash : sBBrml-vr0eYWVOoLfZpErIkINhNqmgbmH41dATv6EM

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private static final String TAG = "inputToDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
