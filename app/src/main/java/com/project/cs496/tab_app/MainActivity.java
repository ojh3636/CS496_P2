package com.project.cs496.tab_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.RestAdapter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
   /* private String sfName = "login";
    private SharedPreferences sf;*/
   /* private String userId;
    private String userToken;
    private String base_URL = "http://50.112.20.91/api";*/

   /* private SharedPreferences.Editor editor;*/
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
   /* private HttpURLConnection con = null ;
    private InputStream is = null;*/
    private TextView info;

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessTokenTracker fbTracker;

    private Activity activity;

    private String user_id;
    private String user_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.facebook_login);
        activity = this;

        user_id = null;
        user_token = null;

        info = (TextView)findViewById(R.id.info);
        Button start_button = (Button)findViewById(R.id.start_button);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id != null && user_token !=null){
                    Intent intent = new Intent(activity,MainActivity2.class);
                    //MainActivity2 로 user_id, user_token 보내주기.
                    intent.putExtra("user_id",user_id);
                    intent.putExtra("user_token",user_token);

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"로그인이 되어야 다른페이지를 볼 수 있습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });





        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        callbackManager = CallbackManager.Factory.create();


        if(AccessToken.getCurrentAccessToken()!=null){
            user_id = AccessToken.getCurrentAccessToken().getUserId();
            user_token = AccessToken.getCurrentAccessToken().getToken();
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /* sf = getSharedPreferences(sfName,0);
                    editor = sf.edit();
                    editor.putString("userID", loginResult.getAccessToken().getUserId());
                    editor.putString("userToken", loginResult.getAccessToken().getToken());
                    editor.commit();*/
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                        "Login상태: "
                                + "로그인 완료!"
                );
                user_id = loginResult.getAccessToken().getUserId();
                user_token = loginResult.getAccessToken().getToken();
            }

                @Override
                public void onCancel() {
                    info.setText("Login attempt canceled.");
                }

                @Override
                public void onError(FacebookException e) {
                    info.setText("Login attempt canceled.");
                }
            });

        fbTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    user_id = null;
                    user_token = null;
                    info.setText("");
                }
            }
        };


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}

/*  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sf = getSharedPreferences(sfName,0);
        userId = sf.getString("userID",null);
        userToken = sf.getString("userToken",null);

        RestAdapter adapter = new RestAdapter(getApplicationContext(), "http://50.112.20.91/api");*/

/*if(userId == null || userToken == null){
            Intent login_intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(login_intent);
        } else {

            try {
                con = (HttpURLConnection) (new URL(base_URL + "/fb_users/validCheck?user_token=" + userToken)).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.connect();

                // Let's read the response
                StringBuffer buffer = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null)
                    buffer.append(line);

                is.close();
                con.disconnect();

                JSONObject jObj = new JSONObject(buffer.toString());

                if(jObj.getString("valid") == "false") {
                    Intent login_intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(login_intent);
                }


            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (Throwable t) {
                }
                try {
                    con.disconnect();
                } catch (Throwable t) {
                }
            }


        }

        userId = sf.getString("userID",null);
        userToken = sf.getString("userToken",null);

        try {

            con = (HttpURLConnection) (new URL(base_URL + "/addresses/sync?user_token="+ userToken + "&" + "user_id=" + userId)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();
            Log.d("haha",userToken);
            con.disconnect();

        *//*    con.disconnect();
*//*
        } catch (Throwable t) {
            Log.d("haha","haha2");
            t.printStackTrace();
        }
*/
