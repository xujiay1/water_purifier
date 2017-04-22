package xebia.ismail.water_purifier.fragment;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import xebia.ismail.water_purifier.AccountManager;
import xebia.ismail.water_purifier.CartActivity;
import xebia.ismail.water_purifier.MainActivity;
import xebia.ismail.water_purifier.R;
import xebia.ismail.water_purifier.WaterQualityActivity;


public class UserInfoFragment extends Fragment {

    private NavigationView navigationView;
    private AccountManager accountManager;

    public UserInfoFragment() {
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
    public void onResume() {
        super.onResume();
        TextView tv1 =(TextView)getActivity().findViewById(R.id.balance);
        tv1.setText("余额: "+accountManager.getBalance());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        accountManager=AccountManager.getInstance();

        Button button_cart = (Button) getActivity().findViewById(R.id.button_cart);
        button_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        TextView tv1 =(TextView)getActivity().findViewById(R.id.balance);
        tv1.setText("余额: "+accountManager.getBalance());

        TextView tv=(TextView)getActivity().findViewById(R.id.uifuname);
        tv.setText(accountManager.getUserName());
        Button button_logout=(Button)getActivity().findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) getActivity().findViewById(R.id.headeruname);
                textView.setText("请登录");
                accountManager.signout();
                Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_LONG).show();
                navigationView.getMenu().getItem(3).setTitle("登录/注册");
                final View elevation = getActivity().findViewById(R.id.elevation);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                loginFragment.setNavigationView(navigationView);
                Fragment navFragment=loginFragment;
                getActivity().setTitle("登录/注册");
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
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }


}
