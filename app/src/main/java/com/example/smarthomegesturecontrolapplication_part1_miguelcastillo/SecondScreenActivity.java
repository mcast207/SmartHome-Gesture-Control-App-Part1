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
    String gestureAction;
    String demoFileName;
    String practiceFileNameGesture;
    String demoVideoPath;
    int demoVideoReplayCount = 0;
    VideoView demoVideoView;
    Button practiceButton;
    Button restartButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second_screen);

        //Get user choice from dropdown
        Intent intent = getIntent();
        gestureAction = intent.getStringExtra("Gesture Name");

        videoAndFileName(gestureAction);

        //Display corresponding demo video
        demoVideoView = findViewById(R.id.demoVideoView);
        demoVideoPath = "android.resource://" + getPackageName() + "/raw/" + demoFileName;
        demoVideoView.setVideoURI(Uri.parse(demoVideoPath));
        demoVideoView.start();

        //Replay demo video 3 times
        demoVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            int maxReplayCount =3;
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(demoVideoReplayCount < maxReplayCount){
                    demoVideoReplayCount++;
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            }
        });

        //Buttons
        practiceButton = findViewById(R.id.practiceButtonScreen2);
        practiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SecondScreenActivity.this, ThirdScreenActivity.class);
//            intent.putExtra("Gesture Name", gestureAction);
                intent.putExtra("Practice Gesture File Name", practiceFileNameGesture);
                startActivity(intent);

            }
        });
//        practiceButton.setOnClickListener(new View.OnClickListener(){
////            practiceFileName = gestureName + "_PRACTICE_" + practiceNum + userName + ".mp4";
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(SecondScreenActivity.this, ThirdScreenActivity.class);
//                intent.putExtra("Gesture Name", gestureName);
//                startActivity(intent);
//
//            }
//        });

        restartButton = findViewById(R.id.restartButtonScreen2);
        Intent intent2 = new Intent(SecondScreenActivity.this, MainActivity.class);
        restartButton.setOnClickListener(v -> startActivity(intent2));



    }
    public void videoAndFileName(String gestureAction){
        switch (gestureAction){

            case "Turn On Light":
                demoFileName = "lighton";
                practiceFileNameGesture = "LightOn";
                break;
            case "Turn Off Light":
                demoFileName = "lightoff";
                practiceFileNameGesture = "LightOff";
                break;
            case "Turn On Fan":
                demoFileName = "fanon";
                practiceFileNameGesture = "FanOn";
                break;
            case "Turn Off Fan":
                demoFileName = "fanoff";
                practiceFileNameGesture = "FanOff";
                break;
            case "Increase Fan Speed":
                demoFileName = "increasefanspeed";
                practiceFileNameGesture = "FanUp";
                break;
            case "Decrease Fan Speed":
                demoFileName = "decreasefanspeed";
                practiceFileNameGesture = "FanDown";
                break;
            case "Set Thermostat to specified temperature":
                demoFileName = "setthermo";
                practiceFileNameGesture = "SetThermo";
                break;
            case "0":
                demoFileName = "h0";
                practiceFileNameGesture = "Num0";
                break;
            case "1":
                demoFileName = "h1";
                practiceFileNameGesture = "Num1";
                break;
            case "2":
                demoFileName = "h2";
                practiceFileNameGesture = "Num2";
                break;
            case "3":
                demoFileName = "h3";
                practiceFileNameGesture = "Num3";
                break;
            case "4":
                demoFileName = "h4";
                practiceFileNameGesture = "Num4";
                break;
            case "5":
                demoFileName = "h5";
                practiceFileNameGesture = "Num5";
                break;
            case "6":
                demoFileName = "h6";
                practiceFileNameGesture = "Num6";
                break;
            case "7":
                demoFileName = "h7";
                practiceFileNameGesture = "Num7";
                break;
            case "8":
                demoFileName = "h8";
                practiceFileNameGesture = "Num8";
                break;
            case "9":
                demoFileName = "h9";
                practiceFileNameGesture = "Num9";
                break;
        }
    }

}