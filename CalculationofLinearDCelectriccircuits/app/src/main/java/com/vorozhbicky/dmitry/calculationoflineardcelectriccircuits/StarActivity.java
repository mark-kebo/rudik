package com.vorozhbicky.dmitry.calculationoflineardcelectriccircuits;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class StarActivity extends AppCompatActivity {
    private EditText ra;
    private EditText rb;
    private EditText rc;
    private EditText xla;
    private EditText xlb;
    private EditText xlc;
    private EditText xca;
    private EditText xcb;
    private EditText xcc;
    private EditText ul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        setTitle("Calculation 'Star'");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ra = findViewById(R.id.ra);
        rb = findViewById(R.id.rb);
        rc = findViewById(R.id.rc);
        xla = findViewById(R.id.xla);
        xlb = findViewById(R.id.xlb);
        xlc = findViewById(R.id.xlc);
        xca = findViewById(R.id.xca);
        xcb = findViewById(R.id.xcb);
        xcc = findViewById(R.id.xcc);
        ul = findViewById(R.id.ulStar);

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

    public void starNext(View view) {
        if (ra.getText().toString().equals("") || rb.getText().toString().equals("") ||
                rc.getText().toString().equals("") || xla.getText().toString().equals("") ||
                xlb.getText().toString().equals("") || xlc.getText().toString().equals("") ||
                xca.getText().toString().equals("") || xcb.getText().toString().equals("") ||
                xcc.getText().toString().equals("") || ul.getText().toString().equals("")) {
            showAlert();
        } else {
            Intent intent = new Intent(StarActivity.this, CalculationActivity.class);
            intent.putExtra("type", "star");
            intent.putExtra("ra", ra.getText().toString());
            intent.putExtra("rb", rb.getText().toString());
            intent.putExtra("rc", rc.getText().toString());
            intent.putExtra("xla", xla.getText().toString());
            intent.putExtra("xlb", xlb.getText().toString());
            intent.putExtra("xlc", xlc.getText().toString());
            intent.putExtra("xca", xca.getText().toString());
            intent.putExtra("xcb", xcb.getText().toString());
            intent.putExtra("xcc", xcc.getText().toString());
            intent.putExtra("ul", ul.getText().toString());
            startActivity(intent);
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StarActivity.this);
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
