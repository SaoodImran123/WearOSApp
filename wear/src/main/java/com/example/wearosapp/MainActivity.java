package com.example.wearosapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wearosapp.databinding.ActivityMainBinding;

public class MainActivity extends Activity{

    private TextView mTextView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //mTextView = binding.text;
        View clock = findViewById(R.id.analogClock);
        clock.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                //Do stuff
                Log.d("DEBUG MainActivity", "Swiped Left");

                Intent intent = new Intent(MainActivity.this, Timer.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onSwipeRight(){
                //Do stuff
                Log.d("DEBUG MainActivity", "Swiped Right");
            }
        });
    }
}