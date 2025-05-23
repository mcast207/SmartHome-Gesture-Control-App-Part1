package com.example.smarthomegesturecontrolapplication_part1_miguelcastillo;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    Button restartButton;
    Button uploadButton;
    Button relearnButton;
    String userLastName = "CASTILLO";
    Integer COUNTER_ONE = 1;
    Uri practiceVideoUri;
    VideoView practiceVideoView;

    public static Map<String, Integer> countPracticeNumber = new HashMap<>();
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //Get the gesture file name
        practiceFileNameGesture = getIntent().getStringExtra("practice gesture file name");

        //Display third screen after recording practice video
        setContentView(R.layout.activity_third_screen);

        //Play recorded practice video
        practiceVideoView = findViewById(R.id.practiceVideoView);

        //Logic to display recorded video on third screen
        practiceVideoUri = Uri.parse(getIntent().getStringExtra("videoUri"));
        practiceVideoView.setVideoURI(practiceVideoUri);
        practiceVideoView.setMediaController(new MediaController(this));
        practiceVideoView.requestFocus();
        practiceVideoView.start();

        restartButton = findViewById(R.id.restartButtonScreen3);
        Intent backToMainIntent = new Intent(ThirdScreenActivity.this, MainActivity.class);
        restartButton.setOnClickListener(v -> startActivity(backToMainIntent));

        relearnButton = findViewById(R.id.relearnButton);
        Intent backToSecondScreen = new Intent(ThirdScreenActivity.this, SecondScreenActivity.class);
        relearnButton.setOnClickListener(v -> startActivity(backToSecondScreen));

        uploadButton = findViewById(R.id.uploadButton);

        httpMultiFromRequestBody(getIntent().getStringExtra("file path"), backToMainIntent);
    }

    public void httpMultiFromRequestBody(String videoUri, Intent backToMainIntent){

        //Use a hash map to keep track of the practice count number
        if(countPracticeNumber.get(practiceFileNameGesture) == null){
            countPracticeNumber.put(practiceFileNameGesture, COUNTER_ONE);
        }else{
            Integer counterNum = countPracticeNumber.get(practiceFileNameGesture);
            countPracticeNumber.put(practiceFileNameGesture, counterNum + COUNTER_ONE);
        }
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check permission of device
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, 1);
                }

                //Flask server ip and port address
                String postUrl = "http://" + "192.168.1.102" + ":" + "5000" + "/uploadVideo";

                File streamData = null;
                RequestBody postBody = null;
                try {
                    streamData = new File(videoUri);

                    postBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("image",
                                    practiceFileNameGesture + "_PRACTICE_"  +
                                            countPracticeNumber.get(practiceFileNameGesture) + "_" + userLastName + ".mp4", RequestBody.create(MediaType.parse("video/*"), streamData))
                            .build();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(postUrl)
                        .post(postBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        call.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ThirdScreenActivity.this, "ERROR: Connection was not established, check that your Flask server is running with the correct IP address", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call callback, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ThirdScreenActivity.this, "Video successfully uploaded to Flask server!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(backToMainIntent);
                            }
                        });
                    }
                });
            }
        });

    }
}

