package com.vorozhbicky.dmitry.calculationoflineardcelectriccircuits;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DiagramActivity extends AppCompatActivity {
    private static final int offset = 30;
    private TextView descriptions;
    private TextView descriptionsTwo;
    private ImageView diagramContainer;
    private Canvas canvas;

    private int screenWidth;
    private int screenHeight;

    private CalculationActivity.State state;

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

        prepareActivity();
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

    private void prepareActivity() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels / 2;
        diagramContainer.getLayoutParams().height = screenHeight;
        diagramContainer.getLayoutParams().width = screenWidth;

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        System.out.println(type);

        if (type.equals(CalculationActivity.State.star.toString())) {
            state = CalculationActivity.State.star;
        } else {
            state = CalculationActivity.State.triangle;
        }
    }

    private void drawCoordinate() {
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
        prepareDiagram();
        Bundle extra = getIntent().getExtras();

        double fUA = 0;
        double fUB = 0;
        double fUC = 0;

        if(extra != null) {
            switch (state) {
                case triangle:
                    double mUfAABA = Double.parseDouble(extra.getString("mUfAABA"));
                    double mUfABCA = Double.parseDouble(extra.getString("mUfABCA"));
                    double mUfAACA = Double.parseDouble(extra.getString("mUfAACA"));

                    double mUfAABR = Double.parseDouble(extra.getString("mUfAABR"));
                    double mUfABCR = Double.parseDouble(extra.getString("mUfABCR"));
                    double mUfAACR = Double.parseDouble(extra.getString("mUfAACR"));

                    double mIABA1 = Double.parseDouble(extra.getString("mIABA1"));
                    double mIBCA1 = Double.parseDouble(extra.getString("mIBCA1"));
                    double mIACA1 = Double.parseDouble(extra.getString("mIACA1"));

                    double mIABR1 = Double.parseDouble(extra.getString("mIABR1"));
                    double mIBCR1 = Double.parseDouble(extra.getString("mIBCR1"));
                    double mIACR1 = Double.parseDouble(extra.getString("mIACR1"));

                    double mIA1A = Double.parseDouble(extra.getString("mIA1A"));
                    double mIB1A = Double.parseDouble(extra.getString("mIB1A"));
                    double mIC1A = Double.parseDouble(extra.getString("mIC1A"));

                    double mIA1R = Double.parseDouble(extra.getString("mIA1R"));
                    double mIB1R = Double.parseDouble(extra.getString("mIB1R"));
                    double mIC1R = Double.parseDouble(extra.getString("mIC1R"));

                    double ul = Double.parseDouble(extra.getString("ul"));
                    fUA = Double.parseDouble(extra.getString("fUA"));
                    fUB = Double.parseDouble(extra.getString("fUB"));
                    fUC = Double.parseDouble(extra.getString("fUC"));
                    double IAB1 = Double.parseDouble(extra.getString("IAB1"));
                    double IBC1 = Double.parseDouble(extra.getString("IBC1"));
                    double IAC1 = Double.parseDouble(extra.getString("IAC1"));
                    double fIAB1 = Double.parseDouble(extra.getString("fIAB1"));
                    double fIBC1 = Double.parseDouble(extra.getString("fIBC1"));
                    double fIAC1 = Double.parseDouble(extra.getString("fIAC1"));


                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth/2+mUfAABA, screenHeight/2-mUfAABR,
                            getResources().getColor(R.color.blue), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth/2+mUfABCA, screenHeight/2-mUfABCR,
                            getResources().getColor(R.color.lightBlue), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth/2+mUfAACA, screenHeight/2-mUfAACR,
                            getResources().getColor(R.color.purple), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth/2+mIABA1, screenHeight/2-mIABR1,
                            getResources().getColor(R.color.red), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth/2+mIBCA1, screenHeight/2-mIBCR1,
                            getResources().getColor(R.color.pink), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth/2+mIACA1, screenHeight/2-mIACR1,
                            getResources().getColor(R.color.darkRed), false);
                    drawLine(screenWidth/2+mIA1A, screenHeight/2-mIA1R, screenWidth/2+mIABA1, screenHeight/2-mIABR1,
                            getResources().getColor(R.color.red), true);
                    drawLine(screenWidth/2+mIB1A, screenHeight/2-mIB1R, screenWidth/2+mIBCA1, screenHeight/2-mIBCR1,
                            getResources().getColor(R.color.pink), true);
                    drawLine(screenWidth/2+mIC1A, screenHeight/2-mIC1R, screenWidth/2+mIACA1, screenHeight/2-mIACR1,
                            getResources().getColor(R.color.darkRed), true);
                    drawLine(screenWidth/2, screenHeight/2, screenWidth/2+mIA1A, screenHeight/2-mIA1R,
                            getResources().getColor(R.color.yellow), false);
                    drawLine(screenWidth/2, screenHeight/2, screenWidth/2+mIB1A, screenHeight/2-mIB1R,
                            getResources().getColor(R.color.orange), false);
                    drawLine(screenWidth/2, screenHeight/2, screenWidth/2+mIC1A, screenHeight/2-mIC1R,
                            getResources().getColor(R.color.green), false);

                    descriptions.setText("Векторы напряжения изображеные следующими цветами:UAB-cиний, " +
                            "UBC-голубой,UAC-фиолетовый\n"+ "Векторы фазных токов изображены следующими цветами:" +
                            "IАB-красный, IBC-розовый, IAC-бардовый\n"+ "Векторы линейных токов изображены следующими " +
                            "цветами:IА-желтый, IB-оранжевый, IC-зеленый\n"+ "Векторы Отрицательных фазных токов " +
                            "изображены следующими цветами:-IАB-пуктирный красный, -IBC- пуктирный розовый, " +
                            "-IAC-пуктирный бардовый\n");

                    descriptionsTwo.setText("1.Строим в масштабе векторы напряжения" + Math.ceil(ul) +
                                    "под углами" +fUA+","+fUB+","+fUC+"°\n"+
                                    "2.Строим в масштабе под углом к действительной оси +1 векторы фазных токов IAB,IBC,IAC:\n"+
                            "Вектор тока IAB="+String.format("%.2f",IAB1)+"IBC="+String.format("%.2f",IBC1)+"IAC="+
                            String.format("%.2f",IAC1)+"\n"+ "углы сдвига фаз соответственно:ψА="+
                            String.format("%.2f",(Math.toDegrees(fIAB1)))+"ψВ="+String.format("%.2f",(Math.toDegrees(fIBC1)))+
                                    "ψС="+String.format("%.2f",(Math.toDegrees(fIAC1)))+"\n"+
                                    "3.К концам векторов IAB,IBC,IAC пристраииваются отрицательные фазные токи " +
                                    "согласно уравнениям в п.4\n"+ "4.Соединив концы отрицательных векторов с началом " +
                                    "координат получим линейные токи IA,IB,IC");

                    break;
                case star:
                    double mUfAAA = Double.parseDouble(extra.getString("mUfAAA"));
                    double mUfABA = Double.parseDouble(extra.getString("mUfABA"));
                    double mUfACA = Double.parseDouble(extra.getString("mUfACA"));

                    double mUfAAR = Double.parseDouble(extra.getString("mUfAAR"));
                    double mUfABR = Double.parseDouble(extra.getString("mUfABR"));
                    double mUfACR = Double.parseDouble(extra.getString("mUfACR"));

                    double mIalgAA = Double.parseDouble(extra.getString("mIalgAA"));
                    double mIalgBA = Double.parseDouble(extra.getString("mIalgBA"));
                    double mIalgCA = Double.parseDouble(extra.getString("mIalgCA"));

                    double mIalgAR = Double.parseDouble(extra.getString("mIalgAR"));
                    double mIalgBR = Double.parseDouble(extra.getString("mIalgBR"));
                    double mIalgCR = Double.parseDouble(extra.getString("mIalgCR"));

                    double mINA = Double.parseDouble(extra.getString("mINA"));
                    double mINR = Double.parseDouble(extra.getString("mINR"));


                    double Uf = Double.parseDouble(extra.getString("Uf"));
                    fUA = Double.parseDouble(extra.getString("fUA"));
                    fUB = Double.parseDouble(extra.getString("fUB"));
                    fUC = Double.parseDouble(extra.getString("fUC"));
                    double IKA = Double.parseDouble(extra.getString("IKA"));
                    double IKB = Double.parseDouble(extra.getString("IKB"));
                    double IKC = Double.parseDouble(extra.getString("IKC"));
                    double fIKA = Double.parseDouble(extra.getString("fIKA"));
                    double fIKB = Double.parseDouble(extra.getString("fIKB"));
                    double fIKC = Double.parseDouble(extra.getString("fIKC"));

                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth/2+mUfAAA, screenHeight/2-mUfAAR,
                            getResources().getColor(R.color.blue), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth/2+mUfABA, screenHeight/2-mUfABR,
                            getResources().getColor(R.color.lightBlue), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth/2+mUfACA, screenHeight/2-mUfACR,
                            getResources().getColor(R.color.purple), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth / 2+mIalgAA, screenHeight/2-mIalgAR,
                            getResources().getColor(R.color.red), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth / 2+mIalgBA, screenHeight/2-mIalgBR,
                            getResources().getColor(R.color.pink), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth / 2+mIalgCA, screenHeight/2-mIalgCR,
                            getResources().getColor(R.color.darkRed), false);
                    drawLine(screenWidth / 2, screenHeight / 2, screenWidth / 2+mINA, screenHeight/2-mINR,
                            getResources().getColor(R.color.black), false);

                    descriptions.setText("Векторы напряжения изображеные следующими цветами:UfA-cиний, " +
                            "UfB-голубой,UfC-фиолетовый\n"+ "Векторы токов изображены следующими цветами:IфА-красный, " +
                            "IфB-розовый, IфC-бардовый\n"+ "Вектор тока в нулевом проводе Iн представлен черным цветом\n");

                    descriptionsTwo.setText("1.Строим в масштабе векторы напряжения" +
                            Math.ceil(Uf) + "под углами" +fUA+","+fUB+","+fUC+"°\n"+
                            "2.Строим в масштабе векторы фазных токов:IфА,IфB,IфC, под углом к действительной оси +1,\n" +
                            "значения которых равны:IфА="+ String.format("%.2f",IKA) +"IфВ="+ String.format("%.2f",IKB)+
                            "IфС="+String.format("%.2f",IKC)+"А\n"+ "значения углов которых равны: ψА="+
                            String.format("%.2f",fIKA) + "°\n"+"ψB="+ String.format("%.2f",fIKB)+"° ψС="+
                            String.format("%.2f",fIKC)+
                            "°\n" + "Для нахождения тока в нулевом проводе векторно складываем фазные токи: из " +
                            "конца вектора фазного тока IфА строим вектор IфВ под углом"+ String.format("%.2f",fIKB)+
                            "°\n" +"к концу вектора IфВ пристраиваем вектор IфС под углом"+ String.format("%.2f",fIKC)+
                            "°\n"+ "соединяем конец вектора IфС с началом координат и получаем значение тока в нейтральном " +
                            "проводе в масштабе IN");

                    break;
            }
        }

        // Display the newly created bitmap on app interface
        diagramContainer.setImageBitmap(bitmap);
    }

    private void prepareDiagram() {
        // Initialize a new Paint instance to draw the line
        Paint paint = new Paint();
        // Line color
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        // Line width in pixels
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);

        // Draw a line on canvas at the center position
        canvas.drawLine(screenWidth / 2, offset, screenWidth / 2, screenHeight - offset, paint);
        canvas.drawLine( offset, screenHeight / 2, screenWidth - offset, screenHeight / 2, paint);

        canvas.drawLine(screenWidth / 2, offset, screenWidth / 2 - offset / 2, offset * 2, paint);
        canvas.drawLine(screenWidth / 2, offset, screenWidth / 2 + offset / 2, offset * 2, paint);

        canvas.drawLine( screenWidth - offset, screenHeight / 2, screenWidth - offset * 2, screenHeight / 2 - offset / 2, paint);
        canvas.drawLine( screenWidth - offset, screenHeight / 2, screenWidth - offset * 2, screenHeight / 2 + offset / 2, paint);

    }
    private void drawLine(double startX, double startY, double stopX, double stopY,
                          int color, Boolean isDashedLine) {
        // Initialize a new Paint instance to draw the line
        Paint paint = new Paint();
        // Line color
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        // Line width in pixels
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        if (isDashedLine) {
            paint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
        }

        // Draw a line on canvas at the center position
        canvas.drawLine((float)startX, (float)startY, (float)stopX, (float)stopY, paint);
    }
}
