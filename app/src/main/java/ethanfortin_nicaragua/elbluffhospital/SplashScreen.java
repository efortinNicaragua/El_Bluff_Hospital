package ethanfortin_nicaragua.elbluffhospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

//    /** Duration of wait**/
//    private final int SPLASH_DISPLAY_LENGTH = 1000;
//
//    /** Called when the activity is first created. **/
//    @Override
//    public void onCreate(Bundle startup) {
//        super.onCreate(startup);
//        setContentView(R.layout.activity_splash_screen);
//
//        /* New Handler to start the main menu*/
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashScreen.this, MainMenu.class);
//                SplashScreen.this.startActivity(intent);
//                SplashScreen.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
//    }
//}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
