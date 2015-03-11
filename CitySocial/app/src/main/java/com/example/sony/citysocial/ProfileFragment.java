package com.example.sony.citysocial;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends ListActivity{
    ListView list;
    String[] title = {
            "Restaurants and Bars",
            "Entertainment",
            "Tourist Places",
            "Events and Concerts",
            "Malls,Shops and Bazaars",
            "Transportation",
            "Hotels and Habitation",
            "Medical and Healthcare"

    };









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprofilefragment);
        CircleImageView vw=(CircleImageView)findViewById(R.id.profimage);
        TextView nameview=(TextView)findViewById(R.id.usrname);
        TextView idview=(TextView)findViewById(R.id.uid);
        String a=AppController.getInstance().getUserProfilePicUrl();
        String name=AppController.getInstance().getUserName();
        String id=AppController.getInstance().getUserIdString();
        nameview.setText(name);
        idview.setText(id);
       // int index=id.indexOf("app_scoped_user_id");
        //String croppeduserid=id.substring(index);
        //idview.setText(croppeduserid);
        new LoadProfileImage(vw).execute(a);
        this.setListAdapter(new ArrayAdapter<String>(
                this, R.layout.list_row1,
                R.id.title, title));



    }



    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



}