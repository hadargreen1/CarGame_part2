package com.ex2.CarGame_part2;

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

import com.ex2.CarGame_part2.R;

public class OpeningActivity extends AppCompatActivity {

    private ToggleButton speedTGL;
    private MaterialButton arrows_BTN;
    private MaterialButton sensors_BTN;
    private AppCompatImageView car_gif;
    private MaterialButton scoreList_BTN;
    private LinearLayout name_LL;
    private EditText nameEnter;
    private MaterialButton enterBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        findViews();
        initViews();
        arrows_BTN.setOnClickListener(view -> startArrows());
        sensors_BTN.setOnClickListener(view -> startSensors());
        scoreList_BTN.setOnClickListener(view -> goToScoreList());
        enterBTN.setOnClickListener(view -> {
            if (!nameEnter.getText().toString().equals("")){
                name_LL.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                sensors_BTN.setEnabled(true);
                arrows_BTN.setEnabled(true);
            }
            else{
                Toast.makeText(this, "Let us know your name!", Toast.LENGTH_SHORT).show();
            }
        });
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
        Glide.with(this).load(car_res).into(car_gif);
    }

    private void startArrows() {
        Intent arrowsIntent = new Intent(this,GameActivity.class);
        arrowsIntent.putExtra(GameActivity.KEY_MODE,arrows_BTN.getText().toString());
        arrowsIntent.putExtra(GameActivity.KEY_SPEED,speedTGL.getText());
        arrowsIntent.putExtra(GameActivity.KEY_NAME,nameEnter.getText().toString());
        startActivity(arrowsIntent);
        finish();
    }

    private void startSensors() {
        Intent sensorsIntent = new Intent(this,GameActivity.class);
        sensorsIntent.putExtra(GameActivity.KEY_MODE,sensors_BTN.getText().toString());
        sensorsIntent.putExtra(GameActivity.KEY_SPEED,speedTGL.getText());
        sensorsIntent.putExtra(GameActivity.KEY_NAME,nameEnter.getText().toString());
        startActivity(sensorsIntent);
        finish();
    }

    private void goToScoreList(){
        Intent sensorsIntent = new Intent(this,FinishingActivity.class);
        startActivity(sensorsIntent);
        finish();
    }

    private void findViews() {
        car_gif = findViewById(R.id.opening_gif);
        speedTGL = findViewById(R.id.opening_toggle_speed);
        arrows_BTN = findViewById(R.id.opening_BTN_arrows);
        sensors_BTN = findViewById(R.id.opening_BTN_sensors);
        scoreList_BTN = findViewById(R.id.opening_BTN_records);
        name_LL = findViewById(R.id.LL_name);
        nameEnter = findViewById(R.id.ET_name);
        enterBTN = findViewById(R.id.BTN_enterText);

    }
}