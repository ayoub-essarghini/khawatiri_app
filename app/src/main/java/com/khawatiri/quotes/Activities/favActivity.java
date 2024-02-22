package com.khawatiri.quotes.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.khawatiri.quotes.Adapters.CustomAdapter;
import com.khawatiri.quotes.AdsControles.AdsControle;
import com.khawatiri.quotes.Databases.favDB;
import com.khawatiri.quotes.R;

import java.util.ArrayList;

public class favActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    private ArrayList<String> quotes_fav;
    TextView fav_title;
    favDB myDB;
    AdsControle adsControle;
    RelativeLayout layout_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_tool_bar);
        setContentView(R.layout.activity_fav);
        adsControle = new AdsControle(this);
        adsControle.LoadInterstitial();
        adsControle.ShowBanner(findViewById(R.id.bannerContainer));
        myDB = new favDB(this);
        fav_title = findViewById(R.id.toolbar_title);
        layout_empty = findViewById(R.id.empty_fav);
        fav_title.setText("المفضلة");

        quotes_fav = new ArrayList<>();
        recyclerView = findViewById(R.id.res_Fav);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        customAdapter = new CustomAdapter(favActivity.this, quotes_fav, true);
        storeDataInArrays();

        recyclerView.setAdapter(customAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        //    customAdapter.notifyDataSetChanged();


    }


    //get all data from database
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            layout_empty.setVisibility(View.VISIBLE);

        } else {
            while (cursor.moveToNext()) {

                quotes_fav.add(new String(cursor.getString(1)));

            }

            myDB.close();


        }

    }
}