package com.example.smarthomegesturecontrolapplication_part1_miguelcastillo;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

import androidx.core.content.FileProvider;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ThirdScreenActivity extends AppCompatActivity {
    String practiceFileNameGesture;
    String filenameGesture;
    File practiceFileVideo;
    String practiceVideoFileName;
    String practiceVideoPath;
    Button uploadButton;
    Button restartButton;
    Button practiceButton;
    String userLastName = "CASTILLO";
    Uri practiceVideoUri;
    VideoView practiceVideoView;

    int option = 1;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        practiceFileNameGesture = getIntent().getStringExtra("practice gesture file name");

        String practiceVideoFileName = practiceFileNameGesture + "_PRACTICE_" + userLastName + ".mp4";


        //Display third screen after recording practice video
        setContentView(R.layout.activity_third_screen);
        //Play recorded practice video
        practiceVideoView = findViewById(R.id.practiceVideoView);

        practiceVideoUri = Uri.parse(getIntent().getStringExtra("videoUri"));
        practiceVideoView.setVideoURI(practiceVideoUri);
        practiceVideoView.setMediaController(new MediaController(this));
        practiceVideoView.requestFocus();
        practiceVideoView.start();

        //Create buttons
//        uploadButton = findViewById(R.id.uploadButton);
//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                videoUploadToServer();
//                finish();
//            }
//        });
        restartButton = findViewById(R.id.restartButtonScreen3);
        Intent intent = new Intent(ThirdScreenActivity.this, MainActivity.class);
        restartButton.setOnClickListener(v -> startActivity(intent));
        httpMultiFromRequestBody(getIntent().getStringExtra("file path"));
    }

    public void httpMultiFromRequestBody(String videoUri){
        Button upload_video = findViewById(R.id.uploadButton);
        upload_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(checkUploadCount()) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, 1);
                }
//                progressText.setVisibility(View.VISIBLE);
//                if (counterMap.get(gesture_text) == null) {
//                    counterMap.put(gesture_text, 1);
//                } else {
//                    Integer num = counterMap.get(gesture_text);
//                    counterMap.put(gesture_text, num + 1);
//                }

                String postUrl = "http://" + "192.168.1.67" + ":" + "5000" + "/";

                File stream = null;
                RequestBody postBodyImage = null;
                try {
                    stream = new File(videoUri);

                    postBodyImage = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("image",
                                    practiceFileNameGesture + "_PRACTICE_"  +
                                            "_"+ "CASTILLO" + ".mp4", RequestBody.create(MediaType.parse("video/*"), stream))
                            .build();

                } catch (Exception ioexp) {
                    ioexp.printStackTrace();
                }

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(postUrl)
                        .post(postBodyImage)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        call.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ThirdScreenActivity.this, "Connection was not established", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ThirdScreenActivity.this, "Video uploaded into server", Toast.LENGTH_SHORT).show();
                                Intent backToSquareOne = new Intent(ThirdScreenActivity .this, MainActivity.class);
                                finish();
                                startActivity(backToSquareOne);
                            }
                        });
                    }
                });
            }
        });

    }

//
}