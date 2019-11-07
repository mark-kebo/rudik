package com.vorozhbicky.dmitry.calculationoflineardcelectriccircuits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Remove title bar
        getSupportActionBar().hide(); //<< this
    }

    public void triangleAction(View view) {
        Intent intent = new Intent(MainActivity.this, TriangleActivity.class);
        startActivity(intent);
    }

    public void starAction(View view) {
        Intent intent = new Intent(MainActivity.this, StarActivity.class);
        startActivity(intent);
    }
}

