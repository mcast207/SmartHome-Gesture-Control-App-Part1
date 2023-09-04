package com.example.smarthomegesturecontrolapplication_part1_miguelcastillo;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.SharedPreferences;
import android.Manifest;
import android.Manifest.permission;
import android.media.MediaMetadataRetriever;
import android.content.Context;
import androidx.activity.result.ActivityResult;
import android.app.Activity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;

//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

import androidx.core.content.FileProvider;

import org.jetbrains.annotations.NotNull;


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
//        recordPracticeVideo(practiceVideoFileName);//Block execution

        //Display third screen after recording practice video
        setContentView(R.layout.activity_third_screen);
        getPermissions();
        //Play recorded practice video
        practiceVideoView = findViewById(R.id.practiceVideoView);
//        practiceVideoPath = "android.resource://" + getPackageName() + "/raw/" + practiceVideoFileName;
//        practiceVideoView.setVideoURI(savedPracticeVideoLocation);
//        practiceVideoView.start();
//        practiceVideoView.setOnCompletionListener(mediaPlayer -> practiceVideoView.start());

        //Create buttons
        uploadButton = findViewById(R.id.uploadButton);
//        Intent uploadVideo = new Intent()

        restartButton = findViewById(R.id.restartButtonScreen3);
        Intent intent = new Intent(ThirdScreenActivity.this,MainActivity.class);
        restartButton.setOnClickListener(v-> startActivity(intent));

        recordPracticeVideo(practiceVideoFileName);//Block execution
    }


    public void recordPracticeVideo(String practiceVideoFileName){

        File practiceFileVideo = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Videos/"+ practiceVideoFileName );

        Intent recordPracticeVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        recordPracticeVideo.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);//Record for max 5 seconds

        savedPracticeVideoLocation = FileProvider.getUriForFile(getApplicationContext(), "com.example.smarthomegesturecontrolapplication_part1_miguelcastillo.provider", practiceFileVideo);
        recordPracticeVideo.putExtra(MediaStore.EXTRA_OUTPUT, savedPracticeVideoLocation);
//        startActivity(recordPracticeVideo);

        if (getPackageManager().resolveActivity(recordPracticeVideo, 0) != null) {
            videoActivityResultLauncher.launch(recordPracticeVideo);
        } else {
            Toast.makeText(this, "No apps supports this action", Toast.LENGTH_SHORT).show();
        }

    }
    private final ActivityResultLauncher<Intent> videoActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                savedPracticeVideoLocation = result.getData().getData();
                practiceVideoView.setVideoURI(savedPracticeVideoLocation);
                practiceVideoView.start();
            }
        }
    });
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            uploadButton.setVisibility(View.VISIBLE);
//            practiceVideoView.setVisibility(View.VISIBLE);
//
//            practiceVideoView.setVideoURI(savedPracticeVideoLocation);
//            practiceVideoView.start();
//            practiceVideoView.setOnCompletionListener (mediaPlayer -> practiceVideoView.start());
//
//            Toast.makeText(this, "video loc: /storage/emulated/0/Videos/" + practiceFileNameGesture,Toast.LENGTH_LONG).show();
//        }
//    }

    private void getPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 200);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    200);
        }
    }

//    public void videoUploadToServer(){
//        File fileToUpload = new File(recordedVideoPath, practiceFileNameGesture);
//
//        okHttp
//        MultipartBody.Builder buildMethod =new MultipartBody.Builder();
//        buildMethod.setType(MultipartBody.FORM);
//        buildMethod.addFormDataPart("myfile",sourceFile.getName().toString(), RequestBody.create(MediaType.parse("/"),sourceFile));
//        MultipartBody multipartBody = builder.build();
//
//        String urlStr = "http://" + "0.0.0.0" + ":" + 5000 + "/api/uploadVideo";
//        Request request = new Request
//                .Builder()
//                .post(multipartBody)
//                .url(urlStr)
//                .build();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(DemoActivity.this, "Something went wrong:" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Toast.makeText(DemoActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });
//
//    }

}
