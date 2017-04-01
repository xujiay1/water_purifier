package xebia.ismail.water_purifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xebia.ismail.water_purifier.R;

public class PurifierSolutionsActivity extends AppCompatActivity {
    private PublicTitleBar titleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purifier_solutions);
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
}
