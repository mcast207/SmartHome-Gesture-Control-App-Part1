package com.example.smarthomegesturecontrolapplication_part1_miguelcastillo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;

import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import android.net.Uri;

import android.view.View;

import androidx.annotation.Nullable;

public class SecondScreenActivity extends AppCompatActivity {
    String gestureName;
    String demoFileName;
    String filenameGesture;
    String demoVideoPath;

    VideoView demoVideoView;
    Button practiceButton;
    Button restartButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second_screen);

        //Get user choice from dropdown
        Intent intent = getIntent();
        gestureName = intent.getStringExtra("Gesture Name");
        videoAndFileName(gestureName);

        //Set the demo video on gui
//        int resourceID = this.getResources().getIdentifier(demoFileName, "raw", this.getPackageName());
//        //Find the video location inside phone
//        Uri demoLocation = Uri.parse("android.resource://" + getPackageName() + "/" + resourceID);
//        demoView.setVideoURI(demoLocation);
//        demoView.start();
//        demoView.setOnCompletionListener(mediaPlayer -> demoView.start());


        //Display corresponding demo video
        demoVideoView = findViewById(R.id.demoVideoView);
        demoVideoPath = "android.resource://" + getPackageName() + "/raw/" + demoFileName;
        demoVideoView.setVideoURI(Uri.parse(demoVideoPath));
        demoVideoView.start();

        //Buttons
        practiceButton = findViewById(R.id.practiceButtonScreen2);
        practiceButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
            Intent intent = new Intent(SecondScreenActivity.this, ThirdScreenActivity.class);
            intent.putExtra("Gesture Name", gestureName);
            startActivity(intent);

            }
        });

        restartButton = findViewById(R.id.restartButtonScreen2);
        Intent intent2 = new Intent(SecondScreenActivity.this, MainActivity.class);

        restartButton.setOnClickListener(v -> startActivity(intent2));

    }
    public void videoAndFileName(String gestureAction){
        switch (gestureAction){

            case "Turn On Light":
                demoFileName = "lighton";
                filenameGesture = "LightOn";
                break;
            case "Turn Off Light":
                demoFileName = "lightoff";
                filenameGesture = "LightOff";
                break;
            case "Turn On Fan":
                demoFileName = "fanon";
                filenameGesture = "FanOn";
                break;
            case "Turn Off Fan":
                demoFileName = "fanoff";
                filenameGesture = "FanOff";
                break;
            case "Increase Fan Speed":
                demoFileName = "increasefanspeed";
                filenameGesture = "FanUp";
                break;
            case "Decrease Fan Speed":
                demoFileName = "decreaseFanSpeed";
                filenameGesture = "FanDown";
                break;
            case "Set Thermostat to specified temperature":
                demoFileName = "setthermo";
                filenameGesture = "SetThermo";
                break;
            case "0":
                demoFileName = "h0";
                filenameGesture = "Num0";
                break;
            case "1":
                demoFileName = "h1";
                filenameGesture = "Num1";
                break;
            case "2":
                demoFileName = "h2";
                filenameGesture = "Num2";
                break;
            case "3":
                demoFileName = "h3";
                filenameGesture = "Num3";
                break;
            case "4":
                demoFileName = "h4";
                filenameGesture = "Num4";
                break;
            case "5":
                demoFileName = "h6";
                filenameGesture = "Num6";
                break;
            case "7":
                demoFileName = "h7";
                filenameGesture = "Num0";
                break;
            case "8":
                demoFileName = "h8";
                filenameGesture = "Num8";
                break;
            case "9":
                demoFileName = "h9";
                filenameGesture = "Num9";
                break;
        }
    }

}
