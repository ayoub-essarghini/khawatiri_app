package com.khawatiri.quotes.AdsControles;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Admob {
    Context context;
    ActionListener listen;
    private InterstitialAd mInterstitialAd;
    SharedPrefData sharedPrefData;
    public Admob(Context context) {
        this.context = context;
        sharedPrefData = new SharedPrefData(context);
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
    }

    public void loadBannerAdView(LinearLayout layout) {
        Log.i("AdNetword", "bannerAdmob");
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(new SharedPrefData(context).LoadString("admob_banner"));
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
        ((ViewGroup) layout.getParent()).removeView(adView);
        layout.addView(adView);
    }


    public void LoadInter() {

        AdRequest adRequest = new AdRequest.Builder().build();
        if (sharedPrefData.LoadInt("ads") % sharedPrefData.LoadInt("maxclick") == 0) {
            InterstitialAd.load(context, new SharedPrefData(context).LoadString("admob_inter"), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd;
                    Log.d("ads_inter", "onAdLoaded");
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
                            Log.d("TAG", "The ad was dismissed.");
                            if (listen != null) {
                                listen.onDone();
                            }
                            LoadInter();
                            // loadFullAds();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd = null;
                            Log.d("TAG", "The ad was shown.");
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                    Log.i("TAG", loadAdError.getMessage());
                    Log.d("ads_inter", "onFailed");
                    mInterstitialAd = null;
                    LoadInter();
                }
            });
        }
    }

    public void showads(ActionListener li) {
        listen = li;
        if (sharedPrefData.LoadInt("ads") % sharedPrefData.LoadInt("maxclick") == 0) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show((Activity) context);
            } else if (listen != null) {
                listen.onDone();
                sharedPrefData.SaveInt("ads", sharedPrefData.LoadInt("ads") + 1);
            }
        } else {
            if (listen != null)
                listen.onDone();
            sharedPrefData.SaveInt("ads", sharedPrefData.LoadInt("ads") + 1);
        }
    }


}
