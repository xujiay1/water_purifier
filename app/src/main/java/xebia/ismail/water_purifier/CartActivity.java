package xebia.ismail.water_purifier;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private PublicTitleBar titleBar;
    private ListView listView;
    private AccountManager accountManager;
    private Button payButton;
    private double price;
    CustomListViewActivity adapterViewAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        accountManager = AccountManager.getInstance();

        payButton =(Button) findViewById(R.id.button_pay);
        price=0;

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> pids=new ArrayList<Integer>();
                ArrayList<Integer> checkedItems = adapterViewAndroid.getCheckedItems();
                for(int i=0;i<checkedItems.size();i++){
                    Product product=(Product)adapterViewAndroid.getItem(checkedItems.get(i));
                    pids.add(product.getId());
                }
                if(AccountManager.getInstance().purchase(pids, price)){
                    Toast.makeText(CartActivity.this, "购买成功", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(CartActivity.this, "购买失败", Toast.LENGTH_LONG).show();
                }

                ArrayList<Integer> cart=accountManager.getCart();

                price = 0;
                payButton.setText("待支付 " + price + "¥");
                adapterViewAndroid = new CustomListViewActivity(CartActivity.this, accountManager.getCart(), new ArrayList<Integer>());
                listView=(ListView) findViewById(R.id.listview);
                listView.setAdapter(adapterViewAndroid);
            }
        });

        titleBar = (PublicTitleBar) findViewById(R.id.titleBar);
        titleBar.setOnTitleBarClickListener(new PublicTitleBar.OnTitleBarClick() {
            @Override
            public void onLeftClick() {
                onBackPressed();
                // Toast.makeText(WaterQualityActivity.this, "左边按钮", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightClick() {
                // Toast.makeText(PurifierSolutionsActivity.this, "右边按钮", Toast.LENGTH_LONG).show();
            }
        });

        adapterViewAndroid = new CustomListViewActivity(CartActivity.this, accountManager.getCart(), new ArrayList<Integer>());
        listView=(ListView) findViewById(R.id.listview);
        listView.setAdapter(adapterViewAndroid);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Integer> checkItems=adapterViewAndroid.getCheckedItems();
                if(!checkItems.contains(position)) {
                    Product product = (Product) adapterViewAndroid.getItem(position);
                    price += product.getPrice();
                    payButton.setText("待支付 " + price + "¥");
                    checkItems.add(position);
                }
                else
                {
                    Product product = (Product) adapterViewAndroid.getItem(position);
                    price -= product.getPrice();
                    payButton.setText("待支付 " + price + "¥");
                    checkItems.remove((Integer)position);
                }
                adapterViewAndroid = new CustomListViewActivity(CartActivity.this, accountManager.getCart(), checkItems);
                listView=(ListView) findViewById(R.id.listview);
                listView.setAdapter(adapterViewAndroid);
            }
        });
    }
}
