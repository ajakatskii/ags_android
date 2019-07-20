package com.example.ags_hack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSettings(View view) {
        Intent st = new Intent(this,PosActivity.class);
        this.startActivity(st);
    }

    public void openFace(View view) {
        Intent st = new Intent(this,PosActivity.class);
        this.startActivity(st);
    }

    public void openPayment(View view) {
        Intent st = new Intent(this,paymentActivity.class);
        this.startActivity(st);
    }
}
