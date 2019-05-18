package com.vorozhbicky.dmitry.calculationoflineardcelectriccircuits;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class TriangleActivity extends AppCompatActivity {
    private EditText rab;
    private EditText rbc;
    private EditText rac;
    private EditText xlab;
    private EditText xlbc;
    private EditText xlac;
    private EditText xcab;
    private EditText xcbc;
    private EditText xcac;
    private EditText ul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);
        setTitle("Calculation 'Triangle'");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        rab = findViewById(R.id.rab);
        rbc = findViewById(R.id.rbc);
        rac = findViewById(R.id.rac);
        xlab = findViewById(R.id.xlab);
        xlbc = findViewById(R.id.xlbc);
        xlac = findViewById(R.id.xlac);
        xcab = findViewById(R.id.xcab);
        xcbc = findViewById(R.id.xcbc);
        xcac = findViewById(R.id.xcac);
        ul = findViewById(R.id.ulTriangle);

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

    public void triangleNext(View view) {
        if (rab.getText().toString().equals("") || rbc.getText().toString().equals("") ||
                rac.getText().toString().equals("") || xlab.getText().toString().equals("") ||
                xlbc.getText().toString().equals("") || xlac.getText().toString().equals("") ||
                xcab.getText().toString().equals("") || xcbc.getText().toString().equals("") ||
                xcac.getText().toString().equals("") || ul.getText().toString().equals("")) {
            showAlert();
        } else {
            Intent intent = new Intent(TriangleActivity.this, CalculationActivity.class);
            intent.putExtra("type", "triangle");
            intent.putExtra("rab", rab.getText().toString());
            intent.putExtra("rbc", rbc.getText().toString());
            intent.putExtra("rac", rac.getText().toString());
            intent.putExtra("xlab", xlab.getText().toString());
            intent.putExtra("xlbc", xlbc.getText().toString());
            intent.putExtra("xlac", xlac.getText().toString());
            intent.putExtra("xcab", xcab.getText().toString());
            intent.putExtra("xcbc", xcbc.getText().toString());
            intent.putExtra("xcac", xcac.getText().toString());
            intent.putExtra("ul", ul.getText().toString());
            startActivity(intent);
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TriangleActivity.this);
        builder.setTitle("Не заполнены все поля")
                .setMessage("Если элемент отсутствует, то вводим '0'")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
