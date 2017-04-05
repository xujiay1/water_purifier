package xebia.ismail.water_purifier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import xebia.ismail.water_purifier.R;
import xebia.ismail.water_purifier.fragment.DashboardView;

public class WaterQualityActivity extends AppCompatActivity  implements View.OnClickListener {
    private DashboardView mDashboardView;
    private PublicTitleBar titleBar;
    private TextView tds;
    private TextView tdsText;
    private TextView cl;
    private TextView clText;
    private TextView recommand;
    private TextView locationTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_quality);
        mDashboardView = (DashboardView) findViewById(R.id.dashboard_view);
        mDashboardView.setOnClickListener(this);
        titleBar = (PublicTitleBar) findViewById(R.id.titleBar);
        titleBar.setOnTitleBarClickListener(new PublicTitleBar.OnTitleBarClick() {
            @Override
            public void onLeftClick() {
                onBackPressed();
               // Toast.makeText(WaterQualityActivity.this, "左边按钮", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightClick() {
             //   Toast.makeText(WaterQualityActivity.this, "右边按钮", Toast.LENGTH_LONG).show();
            }
        });
        tds = (TextView) findViewById(R.id.tds);
        tdsText = (TextView) findViewById(R.id.tdsText);

        cl = (TextView) findViewById(R.id.cl);
        clText = (TextView) findViewById(R.id.clText);
        locationTxt = (TextView) findViewById(R.id.textviewlocation);
        Intent intent = getIntent();

        locationTxt.setText("您查询的区域是："+intent.getStringExtra(MainActivity.EXTRA_MESSAGELOCATION));

        recommand = (TextView) findViewById(R.id.recommand);


        int tdsint = intent.getIntExtra(MainActivity.EXTRA_MESSAGETDS,0);
        mDashboardView.setCreditValueWithAnim(tdsint);
        tds.setText("TDS\n"+tdsint+"mg/L");
        if(tdsint<=50){
            tdsText.setText(R.string.verylowtds);
        }else if(tdsint>50 && tdsint<=100){
            tdsText.setText(R.string.lowtds);
        }else if(tdsint>100 && tdsint<=300){
            tdsText.setText(R.string.normaltds);
        }else if(tdsint>300 && tdsint<=600){
            tdsText.setText(R.string.hightds);
        }else if(tdsint>600 && tdsint<=1000){
            tdsText.setText(R.string.veryhightds);
        }else{
            tdsText.setText(R.string.extremelyhightds);
        }

        double cldoub = intent.getDoubleExtra(MainActivity.EXTRA_MESSAGECL,0);
        cl.setText("余氟\n"+cldoub+"mg/L");
        if(cldoub<=0.05){
            clText.setText(R.string.verylowcl);
        }else if(cldoub>0.05 && cldoub<=0.1){
            clText.setText(R.string.lowcl);
        }else if(cldoub>0.1 && cldoub<=0.2){
            clText.setText(R.string.normalcl);
        }else {
            clText.setText(R.string.highcl);
        }

        recommand.setText(R.string.recommand);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.dashboard_view:


                break;

        }
    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, PurifierSolutionsActivity.class);
        //  EditText et = (EditText) getActivity().findViewById(R.id.editText);
        // String locationMsg = provinceSpinner.getSelectedItem().toString()+citySpinner.getSelectedItem().toString()+et.getText().toString();
        // intent.putExtra(EXTRA_MESSAGE, locationMsg);
        startActivity(intent);
    }
}
