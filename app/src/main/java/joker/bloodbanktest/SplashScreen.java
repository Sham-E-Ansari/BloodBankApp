package joker.bloodbanktest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SplashScreen extends Activity {

    ProgressBar pBar;
    int progressStatus=0;
    Handler hn = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        pBar = (ProgressBar)findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i=0; i<5;i++){
                    progressStatus += 20;

                    hn.post(new Runnable() {
                        @Override
                        public void run() {
                            pBar.setProgress(progressStatus);
                            if(progressStatus == 100){
                                startActivity(new Intent(SplashScreen.this,LoginPage.class));
                                finish();
                            }
                        }
                    });
                    try{
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e){

                    }
                }

            }
        }).start();
    }
}
