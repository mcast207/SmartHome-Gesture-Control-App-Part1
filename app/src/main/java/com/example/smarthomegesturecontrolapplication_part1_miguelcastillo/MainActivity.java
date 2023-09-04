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
    String gestureName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        Spinner dropDownSpinner = findViewById(R.id.dropdown_spinner);

        ArrayAdapter<CharSequence> gestureSelectionList = ArrayAdapter.createFromResource(this, R.array.gestureSelectionList, android.R.layout.simple_spinner_dropdown_item);
        gestureSelectionList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownSpinner.setAdapter(gestureSelectionList);
        dropDownSpinner.setOnItemSelectedListener(this);

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