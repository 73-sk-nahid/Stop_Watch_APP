package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView timeDisplay;
    private Button startButton, stopButton, resetButton;
    private boolean isRunning = false;
    private long startTime = 0;
    private Handler handler = new Handler();
    private Runnable updateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeDisplay = findViewById(R.id.time_display);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);

        // Set the initial text with milliseconds
        timeDisplay.setText("Time: 00:00:000");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    isRunning = true;
                    startButton.setEnabled(false);
                    stopButton.setEnabled(true);
                    resetButton.setEnabled(false);
                    startTime = System.currentTimeMillis() - startTime;
                    updateTime = new Runnable() {
                        @Override
                        public void run() {
                            long currentTime = System.currentTimeMillis() - startTime;
                            int minutes = (int) (currentTime / 60000);
                            int seconds = (int) (currentTime / 1000) % 60;
                            int milliseconds = (int) (currentTime % 1000);
                            timeDisplay.setText(String.format("Time: %02d:%02d:%03d", minutes, seconds, milliseconds));
                            handler.postDelayed(this, 1); // Update every millisecond
                        }
                    };
                    handler.post(updateTime);
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    isRunning = false;
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    resetButton.setEnabled(true);
                    handler.removeCallbacks(updateTime);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(false);
                timeDisplay.setText("Time: 00:00:000");
                handler.removeCallbacks(updateTime);
                startTime = 0;
            }
        });
    }
}
