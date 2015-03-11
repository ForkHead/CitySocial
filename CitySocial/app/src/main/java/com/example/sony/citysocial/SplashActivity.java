package com.example.sony.citysocial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
    CheckInternetConnection check;
    Boolean isInternetPresent = false;





    // Splash screen timer
    private static int SPLASH_TIME_OUT = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        TitanicTextView tv = (TitanicTextView) findViewById(R.id.my_text_view);
// set fancy typeface
        tv.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));
// start animation
        new Titanic().start(tv);

        Thread timer=new Thread()
        {
            public void run()
            {

                try {
                    sleep(SPLASH_TIME_OUT);
                }
                catch (Exception e) {

                }

                finally{
                    check = new CheckInternetConnection(getApplicationContext());
                    isInternetPresent = check.isConnectingToInternet();
                    if(isInternetPresent)
                    {

                        Intent i;
                        i = new Intent(SplashActivity.this,ViewAnswer.class);
                        startActivity(i);
                    }
                    else
                    {
                        /*runOnUiThread(new Runnable() {
                            public void run()
                            {
                                Toast.makeText(SplashActivity.this,"No internet connection!", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                        Intent i;
                        i= new Intent(SplashActivity.this,No_Connection.class);
                        startActivity(i);
                    }
                }}};
        timer.start();
    }
}