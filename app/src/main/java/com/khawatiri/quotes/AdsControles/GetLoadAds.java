package com.khawatiri.quotes.AdsControles;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GetLoadAds {

    // todo change link json
    Context mContext;
    ActionListener Mylistener;
    SharedPrefData sharedPrefData;
    public  static String json_url ="https://www.dropbox.com/s/6marj80nengf6pv/khawatiri_ads_controle.json?dl=1";

    public GetLoadAds(Context context, ActionListener listener) {
        mContext = context;
        sharedPrefData = new SharedPrefData(context);
        getMyIdsFromServers();
        Mylistener = listener;
    }

    private void getMyIdsFromServers() {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject object = null;
                try {
                    object = response.getJSONObject("QuotesDB");
                    sharedPrefData.SaveInt("maxclick", object.getInt("maxclick"));

                    sharedPrefData.SaveString("Fb_banner", object.getString("Fb_banner"));
                    sharedPrefData.SaveString("Fb_inter", object.getString("Fb_inter"));


                    sharedPrefData.SaveString("applovin_banner", object.getString("applovin_banner"));
                    sharedPrefData.SaveString("applovin_inter", object.getString("applovin_inter"));
                    //ADMOB
                    sharedPrefData.SaveString("admob_banner", object.getString("admob_banner"));
                    sharedPrefData.SaveString("admob_inter", object.getString("admob_inter"));

                    sharedPrefData.SaveString("fb_page_link", object.getString("fb_page_link"));
                    sharedPrefData.SaveString("insta_page_link", object.getString("insta_page_link"));


                    sharedPrefData.SaveBoolean("enableAdmob", object.getBoolean("admob"));
                    sharedPrefData.SaveBoolean("enableFb", object.getBoolean("fan"));
                    sharedPrefData.SaveBoolean("enableApplovin", object.getBoolean("applovin"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                //initialise Ads Admob
                if (Mylistener != null)
                    Mylistener.onDone();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getMyIdsFromServers();
                Log.d("getAds", "error" + error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}