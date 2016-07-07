package com.project.cs496.tab_app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.res.Resources;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TabB_Fragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    public TabB_Fragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabB_Fragment newInstance() {
        TabB_Fragment fragment = new TabB_Fragment();

        return fragment;
    }

    private static final String TAG = TabB_Fragment.class.getSimpleName();

    private GridView mGridView;
    private ProgressBar mProgressBar;

    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;
    private String FEED_URL = "http://javatechig.com/?json=get_recent_posts&count=45";
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_gridview, container, false);

        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gridview);
        verifyStoragePermissions(this.getActivity());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mGridView = (GridView) rootView.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        btn = (Button) rootView.findViewById(R.id.button);

        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(this.getContext(), R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);

        //Grid view click event
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                GridItem item = (GridItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(TabB_Fragment.this.getActivity(), DetailsActivity.class);
                ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);

                // Interesting data to pass across are the thumbnail size/location, the
                // resourceId of the source bitmap, the picture description, and the
                // orientation (to avoid returning back to an obsolete configuration if
                // the device rotates again in the meantime)

                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);

                //Pass the image title and url to DetailsActivity
                intent.putExtra("left", screenLocation[0]).
                        putExtra("top", screenLocation[1]).
                        putExtra("width", imageView.getWidth()).
                        putExtra("height", imageView.getHeight()).
                        putExtra("image", item.getImage());

                //Start details activity
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Intent intent = new Intent(TabB_Fragment.this.getActivity() , SettingActicity.class);
                //startActivityForResult(intent,1);
                //intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        //Start download
        new AsyncHttpTask().execute(FEED_URL);
        mProgressBar.setVisibility(View.VISIBLE);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Toast.makeText(getApplicationContext(), (CharSequence) data.getData(), Toast.LENGTH_LONG).show();

        String accessKey = "AKIAIARNA7T3BCRYIZWA";
        String secretKey = "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";

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);

        Cursor c = getActivity().getContentResolver().query(data.getData(), null,null,null,null);
        c.moveToFirst();
        String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
        c.close();
        String filename = path.substring(path.lastIndexOf("/")+1);
        File file = new File(path);

        AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey),clientConfig);
        s3.setEndpoint("s3-ap-northeast-2.amazonaws.com");

        TransferUtility tf = new TransferUtility(s3, getActivity().getApplicationContext());
        TransferObserver ob = tf.upload("cs496-prj2", filename, file);

        GridItem item = new GridItem();
        item.setImage("https://s3.ap-northeast-2.amazonaws.com/cs496-prj2/" + filename);
        mGridData.add(item);
        mGridAdapter.setGridData(mGridData);
    }

    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 1) {
                mGridAdapter.setGridData(mGridData);
            } else {
                Toast.makeText(TabB_Fragment.this.getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            mProgressBar.setVisibility(View.GONE);
        }
    }


    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            GridItem item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                item = new GridItem();
                JSONArray attachments = post.getJSONArray("attachments");
                if (null != attachments && attachments.length() > 0) {
                    JSONObject attachment = attachments.getJSONObject(0);
                    if (attachment != null)
                        item.setImage(attachment.getString("url"));
                }
                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}