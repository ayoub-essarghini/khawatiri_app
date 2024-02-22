package com.khawatiri.quotes.AdsControles;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;


import java.util.ArrayList;

public class AdsControle {

    private Activity activity;
    private SharedPrefData sharedPrefData;
    Admob admob;
    FacebookAds facebookAds;
    AppLovin appLovin;
    private ArrayList<String> AdNetworks;

    int size = 1;

    public AdsControle(Context activity) {
        this.activity = (Activity) activity;

        sharedPrefData = new SharedPrefData(activity);
        AdNetworks = new ArrayList<>();
        AdNetworks.add("");
        if (sharedPrefData.LoadBoolean("enableAdmob"))
            AdNetworks.add("Admob");
        if (sharedPrefData.LoadBoolean("enableApplovin"))
            AdNetworks.add("AppLovin");
        if (sharedPrefData.LoadBoolean("enableFb"))
            AdNetworks.add("Fan");
        if (AdNetworks.size() != 1) {
            AdNetworks.remove(0);
            size = AdNetworks.size();
        }
        ChooseAdNetword();
    }

    public void LoadInterstitial() {
        Log.i("AdNetword", AdNetworks.get(sharedPrefData.LoadInt("AdNetwork") % size));
        if (sharedPrefData.LoadInt("ads") % sharedPrefData.LoadInt("maxclick") == 0) {
            switch (AdNetworks.get(sharedPrefData.LoadInt("AdNetwork") % size)) {
                case "Admob":
                    admob.LoadInter();
                    break;
                case "AppLovin":
                    appLovin.LoadInterstitial();
                    break;
                case "Fan":
                    facebookAds.LoadInterstitial();
                    break;


            }
        }
    }

    public void ShowInterstitial(ActionListener Listen) {
        Log.i("AdNetword", " " + sharedPrefData.LoadInt("ads"));
        try {
            if (sharedPrefData.LoadInt("ads") % sharedPrefData.LoadInt("maxclick") == 0) {
                switch (AdNetworks.get(sharedPrefData.LoadInt("AdNetwork") % size)) {
                    case "Admob":
                        admob.showads(Listen);
                        break;
                    case "AppLovin":
                        appLovin.ShowAds(Listen);
                        break;
                    case "Fan":
                        facebookAds.ShowAds(Listen);
                        break;


                    default:
                        if (Listen != null)
                            Listen.onDone();
                }
                sharedPrefData.SaveInt("AdNetwork", sharedPrefData.LoadInt("AdNetwork") + 1);

            } else if (Listen != null)
                Listen.onDone();
        } catch (Exception e) {
            if (Listen != null) Listen.onDone();
        }
    //    LoadInterstitial();
        sharedPrefData.SaveInt("ads", sharedPrefData.LoadInt("ads") + 1);
          LoadInterstitial();
    }




    public void ShowBanner(LinearLayout layout) {
        Log.i("AdNetword", " " + AdNetworks.get(sharedPrefData.LoadInt("AdNetwork") % size));
        switch (AdNetworks.get(sharedPrefData.LoadInt("AdNetwork") % size)) {
            case "Admob":
                admob.loadBannerAdView(layout);
                break;
            case "AppLovin":
                appLovin.Showbanner(layout);
                break;
            case "Fan":
                facebookAds.Showbanner(layout);
                break;


        }

    }

    private void ChooseAdNetword() {
        if (sharedPrefData.LoadBoolean("enableAdmob"))
            admob = new Admob(activity);
        if (sharedPrefData.LoadBoolean("enableFb")) {
            facebookAds = new FacebookAds(activity);
        }
        if (sharedPrefData.LoadBoolean("enableApplovin")) {
            appLovin = new AppLovin(activity);
        }


    }


}

