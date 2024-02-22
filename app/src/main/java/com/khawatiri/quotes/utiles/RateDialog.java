package com.khawatiri.quotes.utiles;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.widget.AppCompatButton;

import com.khawatiri.quotes.R;

public class RateDialog extends Dialog {

    Utils utils;
    Activity context;
    public RateDialog(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_rating);
        utils = new Utils(context);
        final AppCompatButton rateNow = findViewById(R.id.rate_now);
        final AppCompatButton later = findViewById(R.id.later_btn);
        final RatingBar ratingBar = findViewById(R.id.rating_bar_dialog);
        final ImageView dismissLayout = findViewById(R.id.dismiss_layout);
        final ImageView emoji = findViewById(R.id.emoji_rate);

        rateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.Rate();
            }
        });

        dismissLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finishAffinity();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating <= 1) {

                    emoji.setImageResource(R.drawable.etoile1);


                } else if (rating <= 2) {
                    emoji.setImageResource(R.drawable.etoile2);
                } else if (rating <= 3) {
                    emoji.setImageResource(R.drawable.etoile3);
                } else if (rating <= 4) {
                    emoji.setImageResource(R.drawable.etoile4);
                } else if (rating <= 5) {
                    emoji.setImageResource(R.drawable.etoile5);
                }

                animateImage(emoji);
            }
        });


    }

    private void animateImage(ImageView img){

        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1f,0,1f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        img.startAnimation(scaleAnimation);


    }
}
