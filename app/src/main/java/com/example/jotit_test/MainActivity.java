package com.example.jotit_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button Maths, Physics, Coding, Online, DLD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To hide the App Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hides Action Bar


        setContentView(R.layout.activity_main);

        Maths = findViewById(R.id.maths_button);
        Physics = findViewById(R.id.phy_button);
        Coding = findViewById(R.id.coding_button);
        Online = findViewById(R.id.online_button);
        DLD = findViewById(R.id.digital_button);

        Maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1u5_C8Uu6mMoL9UNb4aFV13r8jPY4-D23?usp=sharing");
            }
        });
        Physics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1Y3UXRk95MPXXsdD0XDZv38dyUcmXjOLo?usp=sharing");
            }
        });

        Coding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1O1PAVe0bI75GIN0sq_gF6lp_8Vy2Vghp?usp=sharing");
            }
        });

        Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1hY2np-8SP_ImwACnQ2gyHNbde3ocFW6l?usp=sharing");
            }
        });

        DLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://drive.google.com/drive/folders/1JPfxvLMTBfnGfQj6f770CVrXuAXijzAU?usp=sharing");
            }
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}