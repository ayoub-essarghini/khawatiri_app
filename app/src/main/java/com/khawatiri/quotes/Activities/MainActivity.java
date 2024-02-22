package com.khawatiri.quotes.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.khawatiri.quotes.Adapters.CustomAdapter;
import com.khawatiri.quotes.AdsControles.AdsControle;
import com.khawatiri.quotes.Databases.dbQuotes;
import com.khawatiri.quotes.R;
import com.khawatiri.quotes.utiles.SpeedyLinearLayoutManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    private ArrayList<String> quotes_text;
    public static final String TAG = "Logging Data";
    dbQuotes dbqt;
    String Categorie;
    TextView ch_title;
    AdsControle adsControle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_tool_bar);
        setContentView(R.layout.activity_main);

        adsControle = new AdsControle(this);
        adsControle.LoadInterstitial();
        adsControle.ShowBanner(findViewById(R.id.bannerContainer));
        ch_title = findViewById(R.id.toolbar_title);
        quotes_text = new ArrayList<>();
        dbqt = new dbQuotes(this);

        Categorie = getIntent().getStringExtra("catpos");

        storeItem(Categorie);
        ch_title.setText(Categorie);


        recyclerView = findViewById(R.id.res_view);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new SpeedyLinearLayoutManager(this, SpeedyLinearLayoutManager.VERTICAL, false));
        customAdapter = new CustomAdapter(MainActivity.this, quotes_text, false);
        recyclerView.setAdapter(customAdapter);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (customAdapter != null)
            customAdapter.notifyDataSetChanged();
    }

    public void storeItem(String categorie) {
        Cursor cursor = dbqt.readQuotesData();
        if (cursor.getCount() != 0) {

            while (cursor.moveToNext()) {
                if (cursor.getString(2).equals(categorie))
                    quotes_text.add(cursor.getString(1));

            }

            dbqt.close();

        }
    }


}