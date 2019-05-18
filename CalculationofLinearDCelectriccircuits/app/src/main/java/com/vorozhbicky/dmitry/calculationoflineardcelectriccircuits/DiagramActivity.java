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
    private ImageView diagramContainer;
    private Paint paint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);
        setTitle("Vector Diagram");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        descriptions = findViewById(R.id.descriptionsTextView);
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
        int screenHeight = displaymetrics.heightPixels;
        diagramContainer.getLayoutParams().height = screenHeight / 2;
        diagramContainer.getLayoutParams().width = screenWidth / 2;

        Bitmap bitmap = Bitmap.createBitmap(
                screenWidth / 2, // Width
                screenHeight / 2, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);

        // Draw a solid color on the canvas as background
        canvas.drawColor(Color.TRANSPARENT);

        // Initialize a new Paint instance to draw the line
        Paint paint = new Paint();
        // Line color
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        // Line width in pixels
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);

        int offset = 30;

        // Draw a line on canvas at the center position
        canvas.drawLine(
                0 + offset, // startX
                diagramContainer.getHeight() / 2, // startY
                500, // stopX
                500, // stopY
                paint // Paint
        );

        // Display the newly created bitmap on app interface
        diagramContainer.setImageBitmap(bitmap);
    }
}
