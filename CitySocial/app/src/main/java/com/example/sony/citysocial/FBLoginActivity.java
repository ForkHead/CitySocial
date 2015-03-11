package com.example.sony.citysocial;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FBLoginActivity extends FragmentActivity {

    private LoginButton loginBtn;
    private UiLifecycleHelper uiHelper;
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions","user_location", "user_birthday", "user_likes");
    private static String message = "Sample status posted from android app";
    final ArrayList<String> param=new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fblogin);
        loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
        //loginBtn.setReadPermissions(Arrays.asList("user_location", "user_birthday", "user_likes"));
    }



    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                onSessionStateChange(session,state,exception);
                Log.e("FacebookSampleActivity", "Facebook session opened");
            } else if (state.isClosed()) {
                Log.e("FacebookSampleActivity", "Facebook session closed");
            }
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception)
    {


        if (state.isOpened())
        {
            // Request user data and show the results
            new Request(session,"/me", null, HttpMethod.GET, new Request.Callback() {

                @Override
                public void onCompleted(Response response)
                {
                    GraphObject graphObject = response.getGraphObject();
                    Object nm=graphObject.getProperty("first_name");
                    String name=nm.toString();
                    AppController.getInstance().setUserName(name);
                    param.add(name);
                    Log.e("User data",name);
                    nm=graphObject.getProperty("id");
                    String id=nm.toString();
                    param.add(id);
                    AppController.getInstance().setUserIdString(id);
                    Log.e("User data",id);

                    nm=graphObject.getProperty("gender");
                    name=nm.toString();
                    Log.e("User data",name);
                    nm=graphObject.getProperty("link");
                    name=nm.toString();
                    Log.e("User data",name);
                    nm=graphObject.getProperty("timezone");
                    name=nm.toString();
                    Log.e("User data",name);

                    TextView userInfoTextView = (TextView) findViewById(R.id.userInfoTextView);
                    userInfoTextView.setText(name);


                }
            }
            ).executeAsync();



            final Bundle params = new Bundle();
            params.putBoolean("redirect", false);
            params.putInt("height", 200);
            params.putInt("width", 200);
            params.putString("type", "normal");

            new Request(session,"/me/picture", params, HttpMethod.GET, new Request.Callback() {

                @Override
                public void onCompleted(Response response) {
                    GraphObject graphObject = response.getGraphObject();
                    JSONObject job=graphObject.getInnerJSONObject();
                    if (job != null)
                    {
                        Log.e("User image ","Here");
                        try {
                            JSONObject k = job.getJSONObject("data");
                            String url=k.getString("url");
                            Log.e("Image",url);
                            param.add(url);
                            //Saving User Login Information to global context.

                            AppController.getInstance().setUserProfilePicUrl(url);
                            Intent i = new Intent(FBLoginActivity.this, CategoryPicker.class);
                            startActivity(i);
                           // new NetCall().execute();

                        } catch (Exception e) {
                            Log.e( "error",e.toString());
                        }

                        }
                }
            }
            ).executeAsync();
            Log.i("TAG", "Logged in...");
        }
        else if (state.isClosed())
        {
            Log.i("TAG", "Logged out...");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }




    private class NetCall extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            Log.e("Atleast","Here");
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
            String requrl="http://10.0.2.2/citysocial/user_registration.php";
            HttpPost httpPost = new HttpPost(requrl);
            String url=param.get(2);
            String username=param.get(0);
            String id=param.get(1);

            nameValuePair.add(new BasicNameValuePair("user_id",id));
            nameValuePair.add(new BasicNameValuePair("user_name",username));
            nameValuePair.add(new BasicNameValuePair("user_url",url));


            try
            {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            }
            catch (UnsupportedEncodingException e)
            {
                // writing error to Log
                Log.e("Exception 3", "E3  "+e);
            }
            try {
                httpResponse = httpClient.execute(httpPost);
            }
            catch (Exception e){
                Log.e("Network Error",e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void res) {


        }
    }


}
