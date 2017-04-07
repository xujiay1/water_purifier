package xebia.ismail.water_purifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import xebia.ismail.water_purifier.fragment.DashboardView;

public class WaterQualityActivity extends AppCompatActivity  implements View.OnClickListener {
    private DashboardView mDashboardView;
    private PublicTitleBar titleBar;
    private TextView tdsTextview;
    private TextView tdsText;
    private TextView clTextview;
    private TextView clText;
    private TextView recommand;
    private TextView locationTxt;
    private int tds = 0;
    private double cl  = 0;
    private String location = "";



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
        tdsTextview = (TextView) findViewById(R.id.tds);
        tdsText = (TextView) findViewById(R.id.tdsText);

        clTextview = (TextView) findViewById(R.id.cl);
        clText = (TextView) findViewById(R.id.clText);
        locationTxt = (TextView) findViewById(R.id.textviewlocation);
        Intent intent = getIntent();
        location = intent.getStringExtra(MainActivity.EXTRA_MESSAGELOCATION);
        locationTxt.setText("您查询的区域是："+location);

        recommand = (TextView) findViewById(R.id.recommand);


         tds = intent.getIntExtra(MainActivity.EXTRA_MESSAGETDS,0);
        mDashboardView.setCreditValueWithAnim(tds);
        tdsTextview.setText("TDS\n"+tds+"mg/L");
        if(tds<=50){
            tdsText.setText(R.string.verylowtds);
        }else if(tds>50 && tds<=100){
            tdsText.setText(R.string.lowtds);
        }else if(tds>100 && tds<=300){
            tdsText.setText(R.string.normaltds);
        }else if(tds>300 && tds<=600){
            tdsText.setText(R.string.hightds);
        }else if(tds>600 && tds<=1000){
            tdsText.setText(R.string.veryhightds);
        }else{
            tdsText.setText(R.string.extremelyhightds);
        }

         cl = intent.getDoubleExtra(MainActivity.EXTRA_MESSAGECL,0);
        clTextview.setText("余氟\n"+cl +"mg/L");
        if(cl <=0.05){
            clText.setText(R.string.verylowcl);
        }else if(cl >0.05 && cl <=0.1){
            clText.setText(R.string.lowcl);
        }else if(cl >0.1 && cl <=0.2){
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
        intent.putExtra(MainActivity.EXTRA_MESSAGELOCATION, location);
        intent.putExtra(MainActivity.EXTRA_MESSAGETDS, tds);
        intent.putExtra(MainActivity.EXTRA_MESSAGECL, cl);
        startActivity(intent);
    }
}
