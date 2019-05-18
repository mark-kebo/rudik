package com.vorozhbicky.dmitry.calculationoflineardcelectriccircuits;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DiagramActivity extends AppCompatActivity {
    private TextView descriptions;
    private TextView descriptionsTwo;
    private ImageView diagramContainer;
    private Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);
        setTitle("Vector Diagram");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        descriptions = findViewById(R.id.descriptionsTextView);
        descriptionsTwo = findViewById(R.id.descriptionTwo);
        diagramContainer = findViewById(R.id.diagramContainer);

        drawCoordinate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void drawCoordinate() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels / 2;
        diagramContainer.getLayoutParams().height = screenHeight;
        diagramContainer.getLayoutParams().width = screenWidth;

        Bitmap bitmap = Bitmap.createBitmap(
                screenWidth, // Width
                screenHeight, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        canvas = new Canvas(bitmap);

        // Draw a solid color on the canvas as background
        canvas.drawColor(Color.TRANSPARENT);


        //Draw all
        prepareDiagram(screenWidth, screenHeight);


        // Display the newly created bitmap on app interface
        diagramContainer.setImageBitmap(bitmap);
    }

    private void prepareDiagram(int screenWidth, int screenHeight) {
        // Initialize a new Paint instance to draw the line
        Paint paint = new Paint();
        // Line color
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        // Line width in pixels
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);

        int offset = 30;

        // Draw a line on canvas at the center position
        canvas.drawLine(screenWidth / 2, offset, screenWidth / 2, screenHeight - offset, paint);
        canvas.drawLine( offset, screenHeight / 2, screenWidth - offset, screenHeight / 2, paint);

        canvas.drawLine(screenWidth / 2, offset, screenWidth / 2 - offset / 2, offset * 2, paint);
        canvas.drawLine(screenWidth / 2, offset, screenWidth / 2 + offset / 2, offset * 2, paint);

        canvas.drawLine( screenWidth - offset, screenHeight / 2, screenWidth - offset * 2, screenHeight / 2 - offset / 2, paint);
        canvas.drawLine( screenWidth - offset, screenHeight / 2, screenWidth - offset * 2, screenHeight / 2 + offset / 2, paint);

    }
}
