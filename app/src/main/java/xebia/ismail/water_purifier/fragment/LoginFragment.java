package xebia.ismail.water_purifier.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import xebia.ismail.water_purifier.AccountManager;
import xebia.ismail.water_purifier.MainActivity;
import xebia.ismail.water_purifier.R;


public class LoginFragment extends Fragment {

    private EditText unameEditText;
    private EditText passwdEditText;
    private CheckBox isSignInCheckBox;
    private Button executeButton;
    private LinearLayout linearLayout1;

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
                boolean result=accountManager.login(unameEditText.getText().toString(),passwdEditText.getText().toString());

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
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


}
