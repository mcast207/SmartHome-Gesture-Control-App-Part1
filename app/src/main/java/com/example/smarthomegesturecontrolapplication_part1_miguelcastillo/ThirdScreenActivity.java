package com.example.smarthomegesturecontrolapplication_part1_miguelcastillo;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdScreenActivity extends AppCompatActivity {

    String gestureName;

    Button uploadButton;
    Button restartButton;
    Button practiceButton;
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_third_screen);

//        Intent gestureName = getIntent();
        gestureName = getIntent().getStringExtra("Gesture Name");


        //Create buttons
        uploadButton = findViewById(R.id.uploadButton);
//        Intent uploadVideo = new Intent()

        restartButton = findViewById(R.id.restartButtonScreen3);
        Intent intent = new Intent(ThirdScreenActivity.this,MainActivity.class);
        restartButton.setOnClickListener(v-> startActivity(intent));
    }
}
