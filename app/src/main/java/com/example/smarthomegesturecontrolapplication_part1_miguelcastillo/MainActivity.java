package com.example.smarthomegesturecontrolapplication_part1_miguelcastillo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int duration = Toast.LENGTH_SHORT;
    String demoFileName;
    String filenameGesture;
    String gestureName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        Spinner dropDownSpinner = findViewById(R.id.dropdown_spinner);

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Create intent to bring user to second screen
                Toast toast = Toast.makeText(MainActivity.this, gestureName, duration);
                toast.show();
                Intent SecondScreenActivity = new Intent(MainActivity.this, SecondScreenActivity.class);
                SecondScreenActivity.putExtra("Gesture Name", gestureName);
                startActivity(SecondScreenActivity);
            }
        });


        ArrayAdapter<CharSequence> gestureSelectionList = ArrayAdapter.createFromResource(this, R.array.gestureSelectionList, android.R.layout.simple_spinner_dropdown_item);
        gestureSelectionList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownSpinner.setAdapter(gestureSelectionList);
        dropDownSpinner.setOnItemSelectedListener(this);

    }

    public void videoAndFileName(String gestureAction){
        switch (gestureAction){

            case "Turn On Light":
                demoFileName = "H-LightOn";
                filenameGesture = "LightOn";
                break;
            case "Turn Off Light":
                demoFileName = "H-LightOff";
                filenameGesture = "LightOff";
                break;
            case "Turn On Fan":
                demoFileName = "H-FanOn";
                filenameGesture = "FanOn";
                break;
            case "Turn Off Fan":
                demoFileName = "H-FanOff";
                filenameGesture = "FanOff";
                break;
            case "Increase Fan Speed":
                demoFileName = "H-increaseFanSpeed";
                filenameGesture = "FanUp";
                break;
            case "Decrease Fan Speed":
                demoFileName = "H-decreaseFanSpeed";
                filenameGesture = "FanDown";
                break;
            case "Set Thermostat to specified temperature":
                demoFileName = "H-setThermo";
                filenameGesture = "SetThermo";
                break;
            case "0":
                demoFileName = "H-0";
                filenameGesture = "Num0";
                break;
            case "1":
                demoFileName = "H-1";
                filenameGesture = "Num1";
                break;
            case "2":
                demoFileName = "H-2";
                filenameGesture = "Num2";
                break;
            case "3":
                demoFileName = "H-3";
                filenameGesture = "Num3";
                break;
            case "4":
                demoFileName = "H-4";
                filenameGesture = "Num4";
                break;
            case "5":
                demoFileName = "H-6";
                filenameGesture = "Num6";
                break;
            case "7":
                demoFileName = "H-7";
                filenameGesture = "Num0";
                break;
            case "8":
                demoFileName = "H-8";
                filenameGesture = "Num8";
                break;
            case "9":
                demoFileName = "H-9";
                filenameGesture = "Num9";
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        gestureName = parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent){
        //Do nothing
    }

}