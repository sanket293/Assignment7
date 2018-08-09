package khushboo.sanket.assignment7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(Splash.this,MainActivity.class));
            }
        });

        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        thread.start();


    }
}
