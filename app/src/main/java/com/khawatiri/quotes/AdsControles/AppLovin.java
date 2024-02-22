/*
 *     Created by AYOUB ES-SARGHINI on 9/5/22, 9:55 PM
 *     ayoubes9922@gmail.com
 *     Last modified 9/5/22, 9:54 PM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.khawatiri.quotes.AdsControles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.khawatiri.quotes.R;

import java.util.concurrent.TimeUnit;


@SuppressLint("ResourceAsColor")
public class AppLovin implements MaxAdListener, MaxAdViewAdListener {
    Activity context;
    private MaxAdView adView2;
    private MaxInterstitialAd interstitialAd;
    ActionListener actionListener;
    private int retryAttempt;
    MaxNativeAdLoader nativeAdLoader;
    MaxAd nativeAd;
    SharedPrefData sharedPrefData;

    public AppLovin(Activity cont) {
        this.context = cont;
        sharedPrefData = new SharedPrefData(context);
        AppLovinSdk.initializeSdk( context, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
            }
        } );

    }

    public void Showbanner(LinearLayout layout) {
        adView2 = new MaxAdView(new SharedPrefData(context).LoadString("applovin_banner"), context);
        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = context.getResources().getDimensionPixelSize(R.dimen.banner_height);
        adView2.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
        adView2.loadAd();
        ((ViewGroup) layout.getParent()).removeView(adView2);
        layout.addView(adView2);
        // Load the ad
        Log.d("Applovin", "LoadBanner");
    }

    public void LoadInterstitial() {
        Log.d("Applovin", "Load Inter");
        interstitialAd = new MaxInterstitialAd(sharedPrefData.LoadString("applovin_inter"), context);
        interstitialAd.setListener(this);
        // Load the first ad
        interstitialAd.loadAd();

    }

    public void ShowAds(ActionListener li) {
        actionListener = li;
        if (interstitialAd!=null) {
            Log.d("Applovin", "Show INTER");

            try {
                interstitialAd.showAd();
            }catch (Exception e){
                e.printStackTrace();
                if (actionListener!=null){
                    actionListener.onDone();
                }
            }



        } else if (li != null){
            actionListener.onDone();
        }
        else {
            actionListener.onDone();
        }



    }




    // MAX Ad Listener
    @Override
    public void onAdDisplayFailed(final MaxAd maxAd, final MaxError error) {
        // Interstitial ad failed to display. We recommend loading the next ad
        if (actionListener != null){
            actionListener.onDone();
        }

    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        retryAttempt = 0;
    }

    @Override
    public void onAdDisplayed(final MaxAd maxAd) {
    }

    @Override
    public void onAdClicked(final MaxAd maxAd) {
    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {
        Log.d("Applovin", "failed INTER" + error.getMessage());
        if (actionListener!=null){
            actionListener.onDone();
        }
        retryAttempt++;
        long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                interstitialAd.loadAd();
            }
        }, delayMillis);
    }

    @Override
    public void onAdHidden(final MaxAd maxAd) {
        // Interstitial ad is hidden. Pre-load the next ad
        interstitialAd.loadAd();
        if (actionListener != null)
            actionListener.onDone();
    }

    @Override
    public void onAdExpanded(MaxAd ad) {

    }

    @Override
    public void onAdCollapsed(MaxAd ad) {

    }


}

