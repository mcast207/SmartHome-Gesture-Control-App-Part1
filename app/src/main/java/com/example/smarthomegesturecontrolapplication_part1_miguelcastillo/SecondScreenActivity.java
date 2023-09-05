package com.example.smarthomegesturecontrolapplication_part1_miguelcastillo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.VideoView;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;

public class SecondScreenActivity extends AppCompatActivity {
    String gestureAction;
    String demoFileName;
    String practiceFileNameGesture;
    String demoVideoPath;
    String filePathRecordedVideo;
    String userLastName = "CASTILLO";
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

        //Set demo and practice file names
        videoAndFileName(gestureAction);

        //Display corresponding demo video
        demoVideoView = findViewById(R.id.demoVideoView);
        demoVideoPath = "android.resource://" + getPackageName() + "/raw/" + demoFileName;
        demoVideoView.setVideoURI(Uri.parse(demoVideoPath));
        demoVideoView.start();
        demoVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);//loop video playback forever

            }
        });

        practiceButton = findViewById(R.id.practiceButtonScreen2);
        practiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //open camera to record
                recordPracticeVideo(v);
            }
        });

        restartButton = findViewById(R.id.restartButtonScreen2);
        Intent backToMainIntent = new Intent(SecondScreenActivity.this, MainActivity.class);
        restartButton.setOnClickListener(v -> startActivity(backToMainIntent));
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

    public void recordPracticeVideo(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        101);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        }
        else {
            File file = new File(Environment.getExternalStorageDirectory(), "projectVideos");

            if (!file.exists()) {
                file.mkdirs();
            }

            File practiceFileVideo = new File(Environment.getExternalStorageDirectory().getPath() + "/projectVideos/" + practiceFileNameGesture + "_PRACTICE_" +  userLastName + ".mp4");
            Intent recordPracticeVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//            recordPracticeVideo.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
//        recordPracticeVideo.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
//        recordPracticeVideo.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            recordPracticeVideo.putExtra(MediaStore.EXTRA_OUTPUT, practiceFileVideo.getPath());
            recordPracticeVideo.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);//Record for max 5 seconds
            recordPracticeVideo.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//Record for max 5 seconds
            recordPracticeVideo.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//Record for max 5 seconds
            startActivityForResult(recordPracticeVideo, 101);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Uri originalRecordedVideo = data.getData();
            filePathRecordedVideo = getPathFromRecordedVideoUri(getApplicationContext(), originalRecordedVideo );
            Intent displayIntent = new Intent(getApplicationContext(),ThirdScreenActivity.class);
            displayIntent.putExtra("file path", filePathRecordedVideo);
            displayIntent.putExtra("gesture name", gestureAction);
            displayIntent.putExtra("practice gesture file name", practiceFileNameGesture);
            displayIntent.putExtra("videoUri", originalRecordedVideo.toString());
            startActivity(displayIntent);
        }
    }
    public String getPathFromRecordedVideoUri(Context context, Uri contentVideoUri) {
        if ( contentVideoUri.toString().indexOf("file:///") > -1 ){
            return contentVideoUri.getPath();
        }
        Cursor thisCursor = null;
        try {
            String[] tempData = { MediaStore.Images.Media.DATA };
            thisCursor = context.getContentResolver().query(contentVideoUri,  tempData, null, null, null);
            int column_index = thisCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            thisCursor.moveToFirst();
            return thisCursor.getString(column_index);
        }finally {
            if (thisCursor != null) {
                thisCursor.close();
            }
        }
    }


}