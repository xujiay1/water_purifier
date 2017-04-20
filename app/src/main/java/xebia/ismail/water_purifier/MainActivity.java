package xebia.ismail.water_purifier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import xebia.ismail.water_purifier.fragment.LoginFragment;
import xebia.ismail.water_purifier.fragment.SearchLocationFragment;
import xebia.ismail.water_purifier.fragment.FilterManagerFragment;
import xebia.ismail.water_purifier.fragment.TabGeometry;
import xebia.ismail.water_purifier.fragment.UserInfoFragment;
import xebia.ismail.water_purifier.fragment.VolumeFragment;

/* Ismail Xebia */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String SELECTED_ITEM_ID = "SELECTED_ITEM_ID";
    private final Handler mDrawerHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    private int mPrevSelectedId;
    private NavigationView mNavigationView;
    private int mSelectedId;
    private Toolbar mToolbar;
    public final static String EXTRA_MESSAGETDS = "value.tds";
    public final static String EXTRA_MESSAGECL = "value.cl";
    public final static String EXTRA_MESSAGELOCATION = "value.location";
    public final static String EXTRA_MESSAGEFRAGMENT = "value.main";
    private String data = "";
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public ArrayList<CommunityData> cd = new ArrayList<CommunityData>();
    private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null;     //地级市
    private EditText community;


    public NavigationView getNavigationView() {
        return mNavigationView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awal);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                super.onDrawerSlide(drawerView, 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSelectedId = mNavigationView.getMenu().getItem(prefs.getInt("default_view", 0)).getItemId();
        mSelectedId = savedInstanceState == null ? mSelectedId : savedInstanceState.getInt(SELECTED_ITEM_ID);
        mPrevSelectedId = mSelectedId;
        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);

        if (savedInstanceState == null) {
            mDrawerHandler.removeCallbacksAndMessages(null);
            mDrawerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigate(mSelectedId);
                }
            }, 250);

            boolean openDrawer = prefs.getBoolean("open_drawer", false);

            if (openDrawer)
                mDrawerLayout.openDrawer(GravityCompat.START);
            else
                mDrawerLayout.closeDrawers();
        }
    }

    public void switchFragment(int itemId) {
        mSelectedId = mNavigationView.getMenu().getItem(itemId).getItemId();
        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
    }

    private void navigate(final int itemId) {
        final View elevation = findViewById(R.id.elevation);
        Fragment navFragment = null;
        AccountManager accountManager=AccountManager.getInstance();
        switch (itemId) {
            case R.id.nav_1:
                mPrevSelectedId = itemId;
                setTitle("净水商城");
                navFragment = new TabGeometry();
                break;
            case R.id.nav_2:
                mPrevSelectedId = itemId;
                setTitle("水质查询");
                navFragment = new SearchLocationFragment();
                break;
            case R.id.nav_3:
                mPrevSelectedId = itemId;
                setTitle("滤芯管理");
                navFragment = new FilterManagerFragment();
                break;
            case R.id.nav_4:
                mPrevSelectedId = itemId;
                if(accountManager.isLogined()) {
                    setTitle("用户信息");
                    UserInfoFragment userInfoFragment = new UserInfoFragment();
                    userInfoFragment.setNavigationView(mNavigationView);
                    navFragment=userInfoFragment;
                }
                else {
                    setTitle("登录/注册");
                    LoginFragment loginFragment = new LoginFragment();
                    loginFragment.setNavigationView(mNavigationView);
                    navFragment=loginFragment;

                }
            //case R.id.nav_5:8
            //startActivity(new Intent(this, SettingsActivity.class));
            //mNavigationView.getMenu().findItem(mPrevSelectedId).setChecked(true);
            //return;
        }

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(4));

        if (navFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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

    public int dp(float value) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;

        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void sendMessage(View view) {

        new AsyncRetrieve().execute();

    }
    private class AsyncRetrieve extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
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

                data = result.toString();
                StringTokenizer st = new StringTokenizer(data, ";");
                while (st.hasMoreTokens()) {
                    String tmp =  st.nextToken();
                    String[] rst = tmp.split("\\|");

                    String name = rst[0];
                    int tdsa =  Integer.valueOf(rst[1].split(", ")[0].split(": ")[1]);
                    double cl =  Double.valueOf(rst[1].split(", ")[1].split(": ")[1]);
                    cd.add(new CommunityData(name,tdsa,cl));
                }

                provinceSpinner = (Spinner) MainActivity.this.findViewById(R.id.spin_province);
                citySpinner = (Spinner) MainActivity.this.findViewById(R.id.spin_city);
                community = (EditText) MainActivity.this.findViewById(R.id.editText);
                String input = provinceSpinner.getSelectedItem().toString()+citySpinner.getSelectedItem().toString()+community.getText();
                boolean found = false;
                for (CommunityData temp : cd) {
                    if (temp.getLocation().contains(input)){
                        Intent intent = new Intent(MainActivity.this, WaterQualityActivity.class);
                        intent.putExtra(EXTRA_MESSAGELOCATION, temp.getLocation());
                        intent.putExtra(EXTRA_MESSAGETDS, temp.getTds());
                        intent.putExtra(EXTRA_MESSAGECL, temp.getCl());
                        startActivity(intent);
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    Toast.makeText(MainActivity.this, "没有找到结果，请重试！", Toast.LENGTH_LONG).show();
                }
        }
    }
}