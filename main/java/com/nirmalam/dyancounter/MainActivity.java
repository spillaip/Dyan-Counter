package com.nirmalam.dyancounter;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    String repeatcount;
    String durationseconds;
    int intRep = 0;
    int intDur = 0;
    ProgressBar progressBar;
    Button btnPrev, btnNext, startButton, btnStop;
    TextView tvDisplay, tvTotalCounter;
    EditText edRepeat, edDuration;
    Handler handler1;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edRepeat = (EditText) findViewById(R.id.etRepeatCount);
        edDuration = (EditText) findViewById(R.id.etDuration);
        startButton = (Button) findViewById(R.id.btnStart);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.nextBtn);
        btnStop = (Button) findViewById(R.id.btnStop);

        progressBar = (ProgressBar) findViewById(R.id.pbProgress);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        tvTotalCounter = (TextView) findViewById(R.id.tvTotalCounter);
        repeatcount = edRepeat.getText().toString();
        durationseconds = edDuration.getText().toString();

        Log.d("NIR", "repeatCount is " + repeatcount);
        if (TextUtils.isEmpty(repeatcount)) {
            repeatcount = "0";
        }
        if (TextUtils.isEmpty(durationseconds)) {
            durationseconds = "0";
        }
        intRep = Integer.parseInt(repeatcount);
        intDur = Integer.parseInt(durationseconds);

        counter = intRep;

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counter = 0;

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatcount = edRepeat.getText().toString();
                intRep = Integer.parseInt(repeatcount);

                durationseconds = edDuration.getText().toString();
                intDur = Integer.parseInt(durationseconds);

                counter = intRep;

                if (counter > 0) {
                    countMeDown();
                }


                Log.d("NIR", "Value of Counter is " + counter);
                tvTotalCounter.setText(String.valueOf(counter));
                //counter--;
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter > 0) {
                    countMeDown();
                }

                counter--;
                if (counter <= 0) {
                    counter = 0;
                }
                Log.d("NIR", "Value of Counter is " + counter);
                tvTotalCounter.setText(String.valueOf(counter));
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter > 0) {
                    countMeDown();
                }
                counter++;
                if (counter <= 0) {
                    counter = 0;
                }
                Log.d("NIR", "Value of Counter is " + counter);
                tvTotalCounter.setText(String.valueOf(counter));
            }
        });


    }

    private void countMeDown() {

        new CountDownTimer(intDur * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

                tvDisplay.setText("To go: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                onStart();
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (counter > 0) {
                    counter--;
                    tvTotalCounter.setText(String.valueOf(counter));
                    countMeDown();
                }

            }
        }.start();
    }

}


