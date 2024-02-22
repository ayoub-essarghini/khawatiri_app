package com.khawatiri.quotes.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.khawatiri.quotes.Adapters.AdapterCat;
import com.khawatiri.quotes.Databases.dbQuotes;
import com.khawatiri.quotes.R;
import com.khawatiri.quotes.utiles.CustomDialogClass;
import com.khawatiri.quotes.utiles.RateDialog;
import com.khawatiri.quotes.utiles.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CategoriesQuotes extends AppCompatActivity {
    RecyclerView resView;
    AdapterCat cAdapter;
    dbQuotes dbqt;
    TextView cat_title;
    ArrayList<String> Category;
    GridLayoutManager gridLayoutManager;
    private ArrayList<String> categories_text;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Utils utils;
    CustomDialogClass c_dialog;
    RateDialog rateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //  getSupportActionBar().setCustomView(R.layout.custom_tool_bar);
        setContentView(R.layout.activity_categories_quotes);


        utils = new Utils(this);
        c_dialog = new CustomDialogClass();
        rateDialog = new RateDialog(this);
        navigationView = findViewById(R.id.navview);
        drawerLayout = findViewById(R.id.drawer);

        //adding customised toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        rateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        rateDialog.setCancelable(false);
        //toggle button
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.classes:
                        Toast.makeText(getApplicationContext(), "الأقسام", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CategoriesQuotes.this, CategoriesQuotes.class);
                        startActivity(intent);
                        CategoriesQuotes.this.finish();
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.favorites_app:
                        Intent favInt = new Intent(CategoriesQuotes.this, favActivity.class);
                        startActivity(favInt);
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.about_us:
                        Intent abInt = new Intent(CategoriesQuotes.this, About.class);
                        startActivity(abInt);
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.share_app:
                        utils.Share();
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.rate_app:
                        utils.Rate();
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.send_obs:
                        utils.sendObserve();
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.follow_us:
                        c_dialog.showDialog(CategoriesQuotes.this);
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });

        dbqt = new dbQuotes(this);
        cat_title = findViewById(R.id.title_text);
        categories_text = new ArrayList<>();
        resView = findViewById(R.id.resCat);
        //   Category = FiltreCat(storeCat());
        Category = storeCat();
        cat_title.setText("الأقسام");
        for (int i = 0; i < Category.size(); i++)
            categories_text.add(Category.get(i));

        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1);
            }
        });


        resView.setLayoutManager(gridLayoutManager);
        cAdapter = new AdapterCat(CategoriesQuotes.this, categories_text);
        resView.setAdapter(cAdapter);

        // Toast.makeText(this, "" + Category.size(), Toast.LENGTH_SHORT).show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //menu tool bar functions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fav_act) {
            Intent intent = new Intent(CategoriesQuotes.this, favActivity.class);
            startActivity(intent);

        }
        if (item.getItemId() == R.id.btn_inf) {
            Intent intent = new Intent(CategoriesQuotes.this, About.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        try {
            rateDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    ArrayList<String> storeCat() {
        ArrayList<String> cat = new ArrayList<>();
        Cursor cursor = dbqt.readAllData();
        if (cursor.getCount() != 0) {

            while (cursor.moveToNext()) {
                cat.add(cursor.getString(1));
            }

            dbqt.close();

        }
        return cat;
    }


    ArrayList<String> FiltreCat(ArrayList<String> fcat) {
        ArrayList<String> filter = new ArrayList<>();
        filter.add(fcat.get(0));
        for (int i = 0; i < fcat.size() - 1; i++) {
            if (!fcat.get(i + 1).equals(fcat.get(i))) {
                filter.add(fcat.get(i + 1));
                Log.d("Filtra", "" + fcat.get(i + 1));
            }

        }

        return filter;
    }
}