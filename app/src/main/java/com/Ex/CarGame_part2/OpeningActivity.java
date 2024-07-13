package com.Ex.CarGame_part2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import android.util.Log;
public class OpeningActivity extends AppCompatActivity {
    private LinearLayout name_LL;
    private EditText nameEnter;
    private MaterialButton enter_BTN;
    private ToggleButton speed_TGL;
    private MaterialButton arrows_BTN;
    private MaterialButton sensors_BTN;
    private AppCompatImageView car_IMG_open;
    private MaterialButton scoreList_BTN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        findViews();
        initViews();

        arrows_BTN.setOnClickListener(view -> startArrows());
        sensors_BTN.setOnClickListener(view -> startSensors());
        scoreList_BTN.setOnClickListener(view -> goToScoreList());
        enter_BTN.setOnClickListener(view -> {
            if (!nameEnter.getText().toString().isEmpty()) {
                name_LL.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                sensors_BTN.setEnabled(true);
                arrows_BTN.setEnabled(true);
            } else {
                Toast.makeText(this, "Enter your name!", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("OpeningActivity", "onCreate: Initialization complete");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initViews() {
        int car_res = R.drawable.car;
        Glide.with(this).load(car_res).into(car_IMG_open);
    }

    private void startArrows() {
        Intent arrowsIntent = new Intent(this,GameActivity.class);
        arrowsIntent.putExtra(GameActivity.MODE,arrows_BTN.getText().toString());
        arrowsIntent.putExtra(GameActivity.SPEED, speed_TGL.getText());
        arrowsIntent.putExtra(GameActivity.NAME,nameEnter.getText().toString());
        startActivity(arrowsIntent);
        finish();
    }

    private void startSensors() {
        Intent sensorsIntent = new Intent(this, GameActivity.class);
        sensorsIntent.putExtra(GameActivity.MODE, sensors_BTN.getText().toString());
        sensorsIntent.putExtra(GameActivity.SPEED, speed_TGL.getText());
        sensorsIntent.putExtra(GameActivity.NAME, nameEnter.getText().toString());
        startActivity(sensorsIntent);
        Log.d("OpeningActivity", "startSensors: Sensors mode started");
        finish();
    }

    private void goToScoreList(){
        Intent sensorsIntent = new Intent(this, FinishingActivity.class);
        startActivity(sensorsIntent);
        finish();
    }

    private void findViews() {
        name_LL = findViewById(R.id.LL_name);
        nameEnter = findViewById(R.id.ET_name);
        enter_BTN = findViewById(R.id.BTN_enterText);
        car_IMG_open = findViewById(R.id.opening_gif);
        speed_TGL = findViewById(R.id.opening_toggle_speed);
        arrows_BTN = findViewById(R.id.opening_BTN_arrows);
        sensors_BTN = findViewById(R.id.opening_BTN_sensors);
        scoreList_BTN = findViewById(R.id.opening_BTN_records);
    }
}