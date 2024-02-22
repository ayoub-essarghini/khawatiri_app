package com.khawatiri.quotes.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.khawatiri.quotes.AdsControles.ActionListener;
import com.khawatiri.quotes.AdsControles.GetLoadAds;
import com.khawatiri.quotes.AdsControles.SharedPrefData;
import com.khawatiri.quotes.BuildConfig;
import com.khawatiri.quotes.Databases.dbQuotes;
import com.khawatiri.quotes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;


public class Splash extends AppCompatActivity {
    dbQuotes dbqt;
    dbQuotes data;
    GetLoadAds getLoadAds;
    SharedPrefData sharedPrefData;

  //  public static final String Url = "https://www.dropbox.com/s/1auleoo1f0wcfel/khawatiriDB2.json?dl=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPrefData = new SharedPrefData(this);
        data = new dbQuotes(this);
        getFrimSever();

    }

    public void getFrimSever() {

        RequestQueue requestQueue = Volley.newRequestQueue(Splash.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BuildConfig.API_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                   data.deleteAllData();
                    JSONObject jsonObject = ((JSONObject) response).getJSONObject("controleData");
                    JSONArray categories = jsonObject.getJSONArray("categories");
                    JSONArray quotes = jsonObject.getJSONArray("quotes");

                   for (int i = 0; i < categories.length(); i++) {
                        String categ_name = categories.getJSONObject(i).getString("categoryname");
                        data.addCateg(categ_name);


                    }

                    //for add quotes and category name

                       for (int j = 0; j < quotes.length(); j++) {
                            String quote =quotes.getJSONObject(j).getString("quote");
                            String category =quotes.getJSONObject(j).getString("category");
                            data.addQuotes(quote,category);
                        }






                    getLoadAds = new GetLoadAds(Splash.this, new ActionListener() {
                        @Override
                        public void onDone() {
                            goToMain();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                    if (!checkData()) {
                        checkAgain();
                    } else {
                        goToMain();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getAds", "error" + error.getMessage());
                if (!checkData()) {
                    Toasty.error(Splash.this, "المرجوا التحقق من الانترنت", Toast.LENGTH_SHORT, true).show();
                    checkAgain();
                } else {
                    goToMain();
                }

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    boolean checkData() {
        dbqt = new dbQuotes(this);
        Cursor cursor = dbqt.readAllData();
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    void checkAgain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Go to main after the consent is done.
                getFrimSever();
            }
        }, 5000);
    }

    private void goToMain() {
        // Wait few seconds just to show my stunning loading indication, you like it right :P.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Go to main after the consent is done.
              isFirstTime();
            }
        }, 3500);
    }

    private void isFirstTime() {
        SharedPreferences preferences = getApplication().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime",true);
        if (isFirstTime){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime",false);
            editor.apply();
            //start auth activity
            startActivity(new Intent(Splash.this,OnBoardActivity.class));
            finish();
        }else{
            //start home activity
            startActivity(new Intent(Splash.this,CategoriesQuotes.class));
            finish();
        }
    }
}