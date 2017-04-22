package xebia.ismail.water_purifier.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import xebia.ismail.water_purifier.AccountManager;
import xebia.ismail.water_purifier.CommunityData;
import xebia.ismail.water_purifier.MainActivity;
import xebia.ismail.water_purifier.R;
import xebia.ismail.water_purifier.WaterQualityActivity;


public class LoginFragment extends Fragment {

    private EditText unameEditText;
    private EditText passwdEditText;
    private CheckBox isSignInCheckBox;
    private Button executeButton;
    private LinearLayout linearLayout1;
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private AccountManager accountManager;

    private NavigationView navigationView;

    private boolean isChecked;

    public LoginFragment() {
        // Required empty public constructor
    }

    public void setNavigationView(NavigationView navigationView)
    {
        this.navigationView=navigationView;
    }

    public int dp(float value) {
        float density = getActivity().getApplicationContext().getResources().getDisplayMetrics().density;

        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        accountManager=AccountManager.getInstance();

        unameEditText=(EditText) getActivity().findViewById(R.id.username);
        passwdEditText=(EditText) getActivity().findViewById(R.id.passwd);
        isSignInCheckBox =(CheckBox) getActivity().findViewById(R.id.islogin);
        executeButton = (Button) getActivity().findViewById(R.id.button_login);

        isSignInCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    executeButton.setText("注册");
                    executeButton.setBackgroundResource(R.drawable.gra_yellow);
                }
                else{
                    executeButton.setText("登录");
                    executeButton.setBackgroundResource(R.drawable.gra_green);
                }
            }
        });

        executeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean result=accountManager.login(unameEditText.getText().toString(),passwdEditText.getText().toString());
                new AsyncRetrieve().execute();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    private class AsyncRetrieve extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        //this method will interact with UI, here display loading message
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\t载入中...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        // This method does not interact with UI, You need to pass result to onPostExecute to display
        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL("https://www.semreya.com/service.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        // this method will interact with UI, display result sent from doInBackground method
        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();

            accountManager.login(unameEditText.getText().toString(), passwdEditText.getText().toString());

            if(accountManager.isLogined()) {
                TextView textView = (TextView) getActivity().findViewById(R.id.headeruname);
                textView.setText(accountManager.getUserName());
                Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_LONG).show();

                navigationView.getMenu().getItem(3).setTitle("我的信息");

                final View elevation = getActivity().findViewById(R.id.elevation);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                UserInfoFragment userInfoFragment = new UserInfoFragment();
                userInfoFragment.setNavigationView(navigationView);
                Fragment navFragment=userInfoFragment;
                getActivity().setTitle("我的信息");
                final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(4));
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                try {
                    transaction.replace(R.id.content_frame, navFragment).commit();

                    //setting jarak elevasi bayangan ketika menggunakan tabs
                    if (elevation != null) {
                        params.topMargin = navFragment instanceof VolumeFragment ? dp(48) : 0;

                        Animation a = new Animation() {
                            @Override
                            protected void applyTransformation(float interpolatedTime, Transformation t) {
                                elevation.setLayoutParams(params);
                            }
                        };
                        a.setDuration(150);
                        elevation.startAnimation(a);
                    }
                } catch (IllegalStateException ignored) {
                }
            }
        }
    }
}
