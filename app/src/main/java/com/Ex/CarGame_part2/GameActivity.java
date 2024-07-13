package com.Ex.CarGame_part2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.Ex.CarGame_part2.interfaces.StepCallBack;
import com.Ex.CarGame_part2.model.Player;
import com.Ex.CarGame_part2.model.Records;
import com.Ex.CarGame_part2.utils.CrashSound;
import com.Ex.CarGame_part2.utils.MySPv;
import com.Ex.CarGame_part2.utils.StepDetector;
import com.Ex.CarGame_part2.utils.SuccessSound;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import im.delight.android.location.SimpleLocation;

public class GameActivity extends AppCompatActivity {

    public static final String KEY_SPEED = "KEY_SPEED";
    public static final String KEY_MODE = "KEY_MODE";
    public static final String KEY_NAME = "KEY_NAME";
    private final int MULT = 3;
    private double loc_latitude,loc_longitude;
    private Vibrator v;
    private GameManager GM;
    private CrashSound crashSound;
    private StepDetector stepDetector;
    private Toast toast;
    private ExtendedFloatingActionButton goLeft;
    private ExtendedFloatingActionButton goRight;
    private ShapeableImageView[][] obstacle;
    private ShapeableImageView[][] coins;
    private ShapeableImageView[] Hearts;
    private ShapeableImageView[] Cars;
    private AppCompatImageView background;
    private int DELAY = 1000;
    private boolean isFinish = false;
    private Timer timer;
    private long startTime;
    private int currentSpot;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        SimpleLocation location = new SimpleLocation(this);
        requestLocationPermission(location);
        currentSpot = 2;
        findViews();
        initGame();
        initViews();
        GM = new GameManager(Hearts.length);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (toast != null) {
            toast.cancel();
        }
        if (stepDetector != null)
            stepDetector.stop();
    }

    private void initGame() {
        Intent previousIntent = getIntent();
        String speed = previousIntent.getExtras().getString(KEY_SPEED).toLowerCase();
        String mode = previousIntent.getExtras().getString(KEY_MODE).toLowerCase();
        playerName = previousIntent.getStringExtra(KEY_NAME);
        if (mode.equalsIgnoreCase("arrows")) {
            goLeft.setOnClickListener(view -> slideLeft());
            goRight.setOnClickListener(view -> slideRight());
            if (speed.equalsIgnoreCase("fast"))
                DELAY = 600;
        } else if (mode.equalsIgnoreCase("sensors")) {
            initStepDetector();
            stepDetector.start();
            goLeft.setVisibility(View.INVISIBLE);
            goRight.setVisibility(View.INVISIBLE);
        }
    }

    private void initStepDetector() {
        stepDetector = new StepDetector(this, new StepCallBack() {
            @Override
            public void stepLeft() {
                slideLeft();
            }

            @Override
            public void stepRight() {
                slideRight();
            }
        });
    }

    private void findViews() {
        background = findViewById(R.id.game_IMG_background);
        goLeft = findViewById(R.id.game_FAB_goLeft);
        goRight = findViewById(R.id.game_FAB_goRight);
        ShapeableImageView[] Cars = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_car0),
                findViewById(R.id.game_IMG_car1),
                findViewById(R.id.game_IMG_car2),
                findViewById(R.id.game_IMG_car3),
                findViewById(R.id.game_IMG_car4)
        };


        Hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart_1),
                findViewById(R.id.game_IMG_heart_2),
                findViewById(R.id.game_IMG_heart_3)
        };
        obstacle = new ShapeableImageView[][]{
                {findViewById(R.id.game_cone_0_1), findViewById(R.id.game_cone_1_1), findViewById(R.id.game_cone_2_1), findViewById(R.id.game_cone_3_1), findViewById(R.id.game_cone_4_1)},
                {findViewById(R.id.game_cone_0_2), findViewById(R.id.game_cone_1_2), findViewById(R.id.game_cone_2_2), findViewById(R.id.game_cone_3_2), findViewById(R.id.game_cone_4_2)},
                {findViewById(R.id.game_cone_0_3), findViewById(R.id.game_cone_1_3), findViewById(R.id.game_cone_2_3), findViewById(R.id.game_cone_3_3), findViewById(R.id.game_cone_4_3)},
                {findViewById(R.id.game_cone_0_4), findViewById(R.id.game_cone_1_4), findViewById(R.id.game_cone_2_4), findViewById(R.id.game_cone_3_4), findViewById(R.id.game_cone_4_4)},
                {findViewById(R.id.game_cone_0_5), findViewById(R.id.game_cone_1_5), findViewById(R.id.game_cone_2_5), findViewById(R.id.game_cone_3_5), findViewById(R.id.game_cone_4_5)},
                {findViewById(R.id.game_cone_0_6), findViewById(R.id.game_cone_1_6), findViewById(R.id.game_cone_2_6), findViewById(R.id.game_cone_3_6), findViewById(R.id.game_cone_4_6)},
                {findViewById(R.id.game_cone_0_7), findViewById(R.id.game_cone_1_6), findViewById(R.id.game_cone_2_6), findViewById(R.id.game_cone_3_7), findViewById(R.id.game_cone_4_7)}
        };
        coins = new ShapeableImageView[][]{
                {findViewById(R.id.game_wrench_0_1), findViewById(R.id.game_wrench_1_1), findViewById(R.id.game_wrench_2_1), findViewById(R.id.game_wrench_3_1), findViewById(R.id.game_wrench_4_1)},
                {findViewById(R.id.game_wrench_0_2), findViewById(R.id.game_wrench_1_2), findViewById(R.id.game_wrench_2_2), findViewById(R.id.game_wrench_3_2), findViewById(R.id.game_wrench_4_2)},
                {findViewById(R.id.game_wrench_0_3), findViewById(R.id.game_wrench_1_3), findViewById(R.id.game_wrench_2_3), findViewById(R.id.game_wrench_3_3), findViewById(R.id.game_wrench_4_3)},
                {findViewById(R.id.game_wrench_0_4), findViewById(R.id.game_wrench_1_4), findViewById(R.id.game_wrench_2_4), findViewById(R.id.game_wrench_3_4), findViewById(R.id.game_wrench_4_4)},
                {findViewById(R.id.game_wrench_0_5), findViewById(R.id.game_wrench_1_5), findViewById(R.id.game_wrench_2_5), findViewById(R.id.game_wrench_3_5), findViewById(R.id.game_wrench_4_5)},
                {findViewById(R.id.game_wrench_0_6), findViewById(R.id.game_wrench_1_6), findViewById(R.id.game_wrench_2_6), findViewById(R.id.game_wrench_3_6), findViewById(R.id.game_wrench_4_6)},
                {findViewById(R.id.game_wrench_0_7), findViewById(R.id.game_wrench_1_6), findViewById(R.id.game_wrench_2_6), findViewById(R.id.game_wrench_3_7), findViewById(R.id.game_wrench_4_7)}
        };
    }

    private void initViews() {
        int lanes = R.drawable.lanes_v2;
        Glide.with(this).load(lanes).optionalCenterCrop().into(background);
        startGame();
    }

    private void slideLeft() {
        if (currentSpot > 0) {
            Cars[currentSpot].setVisibility(View.INVISIBLE);
            Cars[currentSpot - 1].setVisibility(View.VISIBLE);
            currentSpot--;
        }
    }

    private void slideRight() {
        if (currentSpot < Cars.length - 1) {
            Cars[currentSpot].setVisibility(View.INVISIBLE);
            Cars[currentSpot + 1].setVisibility(View.VISIBLE);
            currentSpot++;
        }
    }

    private void startGame() {
        startTime = System.currentTimeMillis();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (!isFinish)
                            runOnUiThread(GameActivity.this::updateConeLocation);
                    }
                }
                , DELAY, DELAY);
    }

    private void updateConeLocation() {
        checkHit();
        lowerLocations();
        initRandomCone();
    }

    private void lowerLocations() {
        for (int i = obstacle.length - 1; i > 0; i--) {
            for (int j = 0; j < obstacle[i].length; j++) {
                obstacle[i][j].setVisibility(obstacle[i - 1][j].getVisibility());
                coins[i][j].setVisibility(coins[i - 1][j].getVisibility());
            }
        }
    }

    private void initRandomCone() {
        Random rand = new Random();
        int rnd = rand.nextInt(9);
        for (int i = 0; i < obstacle[0].length; i++) {
            obstacle[0][i].setVisibility(View.INVISIBLE);
            coins[0][i].setVisibility(View.INVISIBLE);
        }
        if (rnd < 5) {
            obstacle[0][rnd].setVisibility(View.VISIBLE);
        } else if (rnd > 7) {
            int rnd2 = rand.nextInt(5);
            coins[0][rnd2].setVisibility(View.VISIBLE);
        }
    }

    private void checkHit() {
        if (obstacle[obstacle.length - 1][currentSpot].getVisibility() == View.VISIBLE) {
            GM.updateWrong();
            crashSound = new CrashSound(this);
            crashSound.playSound();
            if (GM.getWrong() != GM.getLife()) {
                Hearts[Hearts.length - GM.getWrong()].setVisibility(View.INVISIBLE);
            } else if (GM.getWrong() == GM.getLife()) {
                openFinishScreen();
                return;
            }
            VibrationHelper.vibrate(v, this);
            Toast.makeText(this, "You just got hit!", Toast.LENGTH_SHORT).show();
        }
        if (coins[coins.length - 1][currentSpot].getVisibility() == View.VISIBLE) {
            if (GM.getWrong() < GM.getLife() && GM.getWrong() > 0) {
                Hearts[Hearts.length - GM.getWrong()].setVisibility(View.VISIBLE);
            }
            SuccessSound successSound = new SuccessSound(this);
            successSound.playSound();
            GM.obtainLife();
            Toast.makeText(this, "You just got more lives!", Toast.LENGTH_SHORT).show();
        }
    }



    private void openFinishScreen() {
        //flag to stop the timer action
        isFinish = true;
        int now = (int) ((System.currentTimeMillis() - startTime) / 1000);
        timer.cancel();
        int score = now * MULT;
        String impGson = MySPv.getInstance().getString(MySPv.getInstance().getMyKey(), "");
        Records recs = new Gson().fromJson(impGson,Records.class);
        if (recs == null){
            recs = new Records();
        }
        recs.getRecords().add(new Player().setName(playerName).setScore(score).setLocation(loc_latitude,loc_longitude));
        recs.sortList();
        String expGson = new Gson().toJson(recs);
        MySPv.getInstance().putString(MySPv.getInstance().getMyKey(),expGson);
        Intent finishIntent = new Intent(this, FinishingActivity.class);
        startActivity(finishIntent);
        GameActivity.this.finish();
    }

    private void requestLocationPermission(SimpleLocation location) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            saveLocation(location);
        }
    }

    private void saveLocation(SimpleLocation location) {
        location.beginUpdates();
        loc_latitude = location.getLatitude();
        loc_longitude = location.getLongitude();
    }

    public static class VibrationHelper {

        public static void vibrate(Vibrator vibrator, Context context) {
            VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(effect);
        }
    }


}
