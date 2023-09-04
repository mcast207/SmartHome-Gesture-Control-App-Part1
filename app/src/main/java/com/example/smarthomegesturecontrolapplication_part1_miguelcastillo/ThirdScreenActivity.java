package com.example.smarthomegesturecontrolapplication_part1_miguelcastillo;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.Button;
import android.media.MediaPlayer;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import android.os.Environment;

import androidx.core.content.FileProvider;


public class ThirdScreenActivity extends AppCompatActivity {
    String practiceFileNameGesture;
    String filenameGesture;
//    String practiceVideoFileName;
    String practiceVideoPath;
    Button uploadButton;
    Button restartButton;
    Button practiceButton;
    String userLastName = "CASTILLO";
    Uri savedPracticeVideoLocation;
    VideoView practiceVideoView;

    int option = 1;
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

//        Intent gestureName = getIntent();
//        gestureAction = getIntent().getStringExtra("Gesture Name");
        practiceFileNameGesture = getIntent().getStringExtra("Practice Gesture File Name");

        String practiceVideoFileName = practiceFileNameGesture + "_PRACTICE_" + userLastName + ".mp4";

        //logic to open camera
        recordPracticeVideo(practiceVideoFileName);//Block execution

        //Display third screen after recording practice video
        setContentView(R.layout.activity_third_screen);

        //Play recorded practice video
        practiceVideoView = findViewById(R.id.practiceVideoView);
//        practiceVideoPath = "android.resource://" + getPackageName() + "/raw/" + practiceVideoFileName;
        practiceVideoView.setVideoURI(savedPracticeVideoLocation);
        practiceVideoView.start();
        practiceVideoView.setOnCompletionListener(mediaPlayer -> practiceVideoView.start());

        //Create buttons
        uploadButton = findViewById(R.id.uploadButton);
//        Intent uploadVideo = new Intent()

        restartButton = findViewById(R.id.restartButtonScreen3);
        Intent intent = new Intent(ThirdScreenActivity.this,MainActivity.class);
        restartButton.setOnClickListener(v-> startActivity(intent));
    }


    public void recordPracticeVideo(String practiceVideoFileName){

        File practiceFileVideo = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Videos/"+ practiceVideoFileName );

        Intent recordPracticeVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        recordPracticeVideo.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);//Record for max 5 seconds

        //Get file location where is being saved
//        Uri savedPracticeVideoLocation = Uri.fromFile(practiceFileVideo);
        savedPracticeVideoLocation = FileProvider.getUriForFile(getApplicationContext(), "com.example.smarthomegesturecontrolapplication_part1_miguelcastillo.provider", practiceFileVideo);
        recordPracticeVideo.putExtra(MediaStore.EXTRA_OUTPUT, savedPracticeVideoLocation);
        startActivity(recordPracticeVideo);
    }
}
