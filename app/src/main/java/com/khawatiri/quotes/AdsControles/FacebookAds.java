package com.khawatiri.quotes.AdsControles;

import static android.content.ContentValues.TAG;
import static com.facebook.ads.AdSize.BANNER_HEIGHT_50;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;


public class FacebookAds extends Activity {
    Context context;
    AdView adView;
    ActionListener listen;
    InterstitialAd fullAdFb;
    AdListener adListener;
    SharedPrefData sharedPrefData;

    public FacebookAds(Context cont) {
        this.context = cont;
        AudienceNetworkAds.initialize(context);
        sharedPrefData = new SharedPrefData(context);

    }

    public void Showbanner(LinearLayout layout) {
        adView = new AdView(context, sharedPrefData.LoadString("Fb_banner"), BANNER_HEIGHT_50);
        facebook_listener();
        AdView.AdViewLoadConfig loadAdConfig = adView.buildLoadAdConfig()
                .withAdListener(adListener)
                .build();
        adView.loadAd(loadAdConfig);

        layout.addView(adView);


    }


    public void LoadInterstitial() {
            fullAdFb = new InterstitialAd(context, sharedPrefData.LoadString("Fb_inter"));

            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial ad displayed callback
                    Log.e(TAG, "Interstitial ad displayed.");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {

                    // Interstitial dismissed callback
                    Log.e(TAG, "Interstitial ad dismissed.");

                    if (listen != null) {
                        listen.onDone();
                    }

                }

                @Override
                public void onError(Ad ad, AdError adError) {

                    if (listen !=null){
                        listen.onDone();
                    }

                    Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Interstitial ad is loaded and ready to be displayed
                    Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");

                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                    Log.d(TAG, "Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback

                    Log.d(TAG, "Interstitial ad impression logged!");
                }

            };

            fullAdFb.loadAd(
                    fullAdFb.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
    }

    public void ShowAds(ActionListener li) {
        listen = li;
            if (fullAdFb.isAdLoaded()) {
                try {
                    fullAdFb.show();
                }catch (Exception e){
                    if (listen !=null){
                        listen.onDone();
                    }
                }

            } else if (li != null) {
                listen.onDone();
            }

    }

    private void facebook_listener() {
        adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d("fb", "facebook banner failed" + adError.getErrorMessage());

            }


            @Override
            public void onAdLoaded(Ad ad) {

                Log.d("fb", "facebook banner loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                adView.destroy();
                Log.d("fb", "facebook banner clicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
    }






}

