package com.example.classone;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.classone.R;
import com.example.classone.interfaces.UserProtocolCallBack;
import com.example.classone.views.ListFragment;
import com.example.classone.views.MapFragment;
import com.google.android.material.imageview.ShapeableImageView;

public class FinishingActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;
    private ShapeableImageView returnIMG;

    UserProtocolCallBack callBack = new UserProtocolCallBack() {
        @Override
        public void sendLocation(double latitude, double longitude) {
            mapFragment.zoom(latitude,longitude);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishing);
        initFragments();
        findViews();
        returnIMG.setOnClickListener(v->{
            Intent openingIntent = new Intent(this,OpeningActivity.class);
            startActivity(openingIntent);
            finish();
        });
    }

    private void initFragments() {
        listFragment = new ListFragment();
        listFragment.setCallback(callBack);
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.upper_frame,listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.lower_frame,mapFragment).commit();
    }

    public void findViews(){
        returnIMG = findViewById(R.id.IMG_return);
    }
}