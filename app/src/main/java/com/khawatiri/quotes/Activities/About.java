package com.khawatiri.quotes.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.khawatiri.quotes.R;
import com.khawatiri.quotes.utiles.CustomDialogClass;
import com.khawatiri.quotes.utiles.Utils;

public class About extends AppCompatActivity {

    CardView share_me, rate_me, follow_me;
    CustomDialogClass c_dialog;
    Utils ut_about;
    TextView text_title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_tool_bar);
        setContentView(R.layout.activity_about);

        c_dialog = new CustomDialogClass();
        ut_about = new Utils(this);

        share_me = findViewById(R.id.share_me_about);
        rate_me = findViewById(R.id.rate_me_about);
        follow_me = findViewById(R.id.follow_me_about);
        text_title = findViewById(R.id.toolbar_title);
        text_title.setText("حول التطبيق");


        share_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ut_about.Share();
            }
        });

        rate_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ut_about.Rate();
            }
        });


        follow_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c_dialog.showDialog(About.this);

            }
        });

    }
}