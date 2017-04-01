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
import android.widget.Toast;

import java.util.Random;

import xebia.ismail.water_purifier.R;
import xebia.ismail.water_purifier.fragment.DashboardView;

public class WaterQualityActivity extends AppCompatActivity  implements View.OnClickListener {
    private DashboardView mDashboardView;
    private PublicTitleBar titleBar;
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.dashboard_view:
                mDashboardView.setCreditValueWithAnim(new Random().nextInt(510));

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
