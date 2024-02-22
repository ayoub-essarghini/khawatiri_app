package com.khawatiri.quotes.utiles;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.khawatiri.quotes.AdsControles.SharedPrefData;
import com.khawatiri.quotes.R;

public class Utils {

    private Context _context;
    SharedPrefData sharedPrefData;

    public Utils(Context context) {
        this._context = context;
        sharedPrefData = new SharedPrefData(context);
    }


    public void Rate() {
        Uri uri = Uri.parse("market://details?id=" + _context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            _context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            _context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + _context.getPackageName())));
        }
    }
    public void Share() {
        String nameshare=_context.getString(R.string.app_name)+_context.getString(R.string.ShareText);
        String sharelink ="http://play.google.com/store/apps/details?id=" + _context.getPackageName();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT,nameshare+"\n"+sharelink);
        _context.startActivity(share);
    }

    public void MoreApp() {
        final String NameStore = "ziko"; // here store name is hola android dev //TODO :change name store !!!! replace space by +

        try {
            _context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id="+NameStore)));
        }
        catch (android.content.ActivityNotFoundException anfe) {
            _context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id="+NameStore)));
        }
    }
    public void sendObserve(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"contact@khawatiri.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "ملاحظة بشأن تطبيق خواطري");
        i.putExtra(Intent.EXTRA_TEXT   , "اكتب ملاحظتك هنا");
        try {
         _context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(_context, "لا يوجد بريد الكتروني للارسال!", Toast.LENGTH_SHORT).show();
        }
    }
   public void  goToFb(){
       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPrefData.LoadString("fb_page_link")));//set privacy policy
       _context.startActivity(browserIntent);
    }

    public void  goToInsta(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPrefData.LoadString("insta_page_link")));//set privacy policy
        _context.startActivity(browserIntent);
    }
}
