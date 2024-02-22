package com.khawatiri.quotes.utiles;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.khawatiri.quotes.R;

public class CustomDialogClass  {

    Utils ut;

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        ut=new Utils(dialog.getContext());
        ImageView btn_fb = (ImageView) dialog.findViewById(R.id.btn_fb);
        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ut.goToFb();
                dialog.dismiss();
            }
        });
        ImageView btn_inst = (ImageView) dialog.findViewById(R.id.btn_inst);
        btn_inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ut.goToInsta();
                dialog.dismiss();
            }
        });
        ImageView btn_ferme = (ImageView) dialog.findViewById(R.id.btn_fermer);
        btn_ferme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}
