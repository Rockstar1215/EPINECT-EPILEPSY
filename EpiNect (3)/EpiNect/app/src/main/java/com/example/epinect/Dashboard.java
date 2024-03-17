package com.example.epinect;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.epinect.Activities.Emergencysos.SmsActivity;
import com.example.epinect.Activities.EpilepsyGuide.Instructions;
import com.example.epinect.Activities.HealthTracker.healthtracker;
import com.example.epinect.Activities.Medibot.chatbot;
import com.example.epinect.Activities.Post.Post;
import com.example.epinect.Activities.Reminder.ReminderMain;
import com.example.epinect.Activities.calenderData.calender;
import com.example.epinect.Activities.location.location;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        // Find the CardViews by their IDs
        CardView emergencyCard = findViewById(R.id.emergencyCard);
        CardView guideCard = findViewById(R.id.guideCard);
        CardView scheduleCard = findViewById(R.id.scheduleCard);
        CardView askCard = findViewById(R.id.askCard);
        CardView healthCard = findViewById(R.id.healthCard);
        CardView locationCard = findViewById(R.id.locationCard);
        CardView recordCard = findViewById(R.id.seizureeventCard);
        CardView dataCard = findViewById(R.id.health_Card);


        // Set click listeners for each CardView
        emergencyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Community CardView
                openActivity(SmsActivity.class);
                vibrate();
            }
        });

        guideCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Medicine Reminder CardView
                      openActivity(Instructions.class);
                    vibrate();
            }
        });

        scheduleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Emergency SOS CardView
                openActivity(ReminderMain.class);
                vibrate();
            }
        });

        askCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Magnifying Glass CardView
                   openActivity(chatbot.class);
                vibrate();
            }
        });
       healthCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Magnifying Glass CardView
                openActivity(Post.class);
                vibrate();
            }
        });
        locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Magnifying Glass CardView
                   openActivity(location.class);
                vibrate();
            }
        });
        recordCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Magnifying Glass CardView
                openActivity(calender.class);
                vibrate();
            }
        });
        dataCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Magnifying Glass CardView
                openActivity(healthtracker.class);
                vibrate();
            }
        });


    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Check if the device has a vibrator
        if (vibrator != null) {
            // Vibrate for 500 milliseconds (adjust duration as needed)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // Deprecated in API 26
                vibrator.vibrate(500);
            }
        }
    }
}