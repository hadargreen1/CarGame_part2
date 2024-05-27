package com.example.classone;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textview.MaterialTextView;

public class ScoreActivity extends AppCompatActivity {
    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String KEY_STATUS = "KEY_STATUS";


    private MaterialTextView score_LBL_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

    findViews();
    initViews();
    }

    private void initViews() {
        Intent previousActivity = getIntent();

        String status = previousActivity.getStringExtra(KEY_STATUS);
        int score = previousActivity.getIntExtra(KEY_SCORE,0);

        score_LBL_status.setText(status + "\n" + score);
    }

    private void findViews() {
        score_LBL_status = findViewById(R.id.score_LBL_status);
    }
}