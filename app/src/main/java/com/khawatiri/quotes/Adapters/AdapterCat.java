package com.khawatiri.quotes.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khawatiri.quotes.Activities.MainActivity;
import com.khawatiri.quotes.AdsControles.ActionListener;
import com.khawatiri.quotes.AdsControles.AdsControle;
import com.khawatiri.quotes.R;

import java.util.ArrayList;

public class AdapterCat extends RecyclerView.Adapter<AdapterCat.MyViewHolder> {


    public Context context;
    private ArrayList<String> catquotes;
    AdsControle adsControle;


    public AdapterCat(Context context, ArrayList<String> quotes) {
        this.context = context;
        this.catquotes = quotes;
        adsControle = new AdsControle((Activity) context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_cat, parent, false);
        return new MyViewHolder(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.categ_text.setText(catquotes.get(position));
        adsControle.LoadInterstitial();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adsControle.ShowInterstitial(new ActionListener() {
                    @Override
                    public void onDone() {
                        Intent intent= new Intent(context, MainActivity.class);
                        intent.putExtra("catpos",catquotes.get(position));
                        context.startActivity(intent);
                    }
                });


            }
        });





    }

    @Override
    public int getItemCount() {
        return catquotes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categ_text;
        CardView cardView;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categ_text = itemView.findViewById(R.id.quote);
            cardView=itemView.findViewById(R.id.categ_click);


            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            cardView.setAnimation(translate_anim);

        }



    }


}
