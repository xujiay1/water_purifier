package xebia.ismail.water_purifier;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PurifierSolutionsActivity extends AppCompatActivity {
    private PublicTitleBar titleBar;
    private int tds = 0;
    private double cl  = 0;
    private String location = "";
    private TextView locationTextView;
    private TextView tdsTextView;
    private TextView clTextView;
    private EditText lowerPriceEditText;
    private EditText upperPriceEditText;
    private Button button;
    int lowerPrice;
    int upperPrice;
    int currentPid;
    GridView androidGridView;
    ArrayList<String> check = new ArrayList<String>();
    ArrayList<Integer> pidAl = new ArrayList<Integer>();
    ArrayList<String> namesAl = new ArrayList<String>();
    ArrayList<Integer> ImageIdAl = new ArrayList<Integer>();
    ArrayList<Integer> pricesAL = new ArrayList<Integer>();
    ArrayList<String> product_lifesAL = new ArrayList<String>();
    ArrayList<String> principlesAL = new ArrayList<String>();

    final Context context = this;


    String[] gridViewString = {
            "海尔\n前置过滤器 HP05 管道过滤 大流量安全稳压",
            "海尔\n反渗透机 HRO5009-5 双出水 进口RO膜",
            "海尔\n反渗透机 HRO5066-4A 箱式 微废水 节水型 大出水量",
            "海尔\n沐浴净化器 HS-01 美肤净水器 婴儿洗浴",
            "海尔施特劳斯\nMAZE智能 V5HR 酒红 高端时尚，智能控温，婴儿水配方",
            "海尔\n中央净水机 HU601-500 前置 超滤 不锈钢机身",
    } ;

    int[] product_lifes = {
            699,
            1869,
            1549,
            199,
            4999,
            899,
    } ;
    String[] principles = {
            "优质阀头,大流量,水压可视，高精度不锈滤网，安全稳压更可靠，70年寿命",
            "五级过滤可直饮，陶氏RO膜，双膜双水质出水，便捷换芯，包安装",
            "节能微废水，卡接滤芯便捷更换，双水质出水，集成水路水电分离防电防漏水，大出水量",
            "复合净水，除重金属、细菌，有效除氯等对肌肤有害成分，保证沐浴水的洁净，安装方便",
            "MAZE-V技术净水直饮，智能调温、即热即饮，滤芯提醒，便捷换芯，定量出水，双重童锁，高端选择",
            "不锈钢机身经久耐用，内压超滤出水质稳定，通水量大，安全冲洗寿命长",
    } ;

    int[] gridViewImageId = {
            R.drawable.hp05,
            R.drawable.hro5009,
            R.drawable.hro5066,
            R.drawable.hs,
            R.drawable.v5hr,
            R.drawable.hu601500,
    };
    int[] prices = {
            699,
           1488,
            1999,
            199,
            4999,
            899,
    };

    String  [][] property= new String[][]{
            {"1"},
            {"3","6","8"},
            {"3","6","8"},
            {"4","7","9"},
            {"3","5","8"},
            {"2","6","8","9"}
    };
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
                   Toast.makeText(PurifierSolutionsActivity.this, "右边按钮", Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = getIntent();
        location = intent.getStringExtra(MainActivity.EXTRA_MESSAGELOCATION);
        tds = intent.getIntExtra(MainActivity.EXTRA_MESSAGETDS,0);
        cl = intent.getDoubleExtra(MainActivity.EXTRA_MESSAGECL,0);

       locationTextView = (TextView) findViewById(R.id.wslocation);
        tdsTextView = (TextView) findViewById(R.id.wstds);
         clTextView  = (TextView) findViewById(R.id.wscl);

        locationTextView.setText(location);
        tdsTextView.setText(String.valueOf(tds));
        clTextView.setText(String.valueOf(cl));

        button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountManager accountManager=AccountManager.getInstance();
                if(accountManager.isLogined()){
                    Intent intent=new Intent(PurifierSolutionsActivity.this, CartActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(PurifierSolutionsActivity.this, "请先登录", Toast.LENGTH_LONG).show();
                }

            }
        });

        lowerPrice=0;
        upperPrice=5000;
        lowerPriceEditText = (EditText) findViewById(R.id.editText_lb);
        upperPriceEditText = (EditText) findViewById(R.id.editText_ub);

        lowerPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lowerPrice=Integer.parseInt(s.toString());
                updateGridViewData();
            }
        });

        upperPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                upperPrice=Integer.parseInt(s.toString());
                updateGridViewData();
            }
        });

        CheckBoxGroupView cbGroup = (CheckBoxGroupView) findViewById(R.id.cbGroup);
        CheckBox cb1 = new CheckBox(this);
        cb1.setTag(1);
        cb1.setText("入户前置");
        cb1.setTextSize(9);
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(isChecked){
                    check.add("1");
                }else{
                    check.remove("1");
                }
                updateGridViewData();
            }
        });
        cbGroup.put(cb1);
        cbGroup.getCheckedIds().toString();


        CheckBox cb2 = new CheckBox(this);
        cb2.setTag(2);
        cb2.setText("中央净水");
        cb2.setTextSize(9);
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(isChecked){

                    check.add("2");
                }else{
                    check.remove("2");
                }
                updateGridViewData();
            }
        });
        cbGroup.put(cb2);
        cbGroup.getCheckedIds().toString();

        CheckBox cb3 = new CheckBox(this);
        cb3.setTag(3);
        cb3.setText("厨房用水");
        cb3.setTextSize(9);
        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(isChecked){

                    check.add("3");
                }else{
                    check.remove("3");
                }
                updateGridViewData();
            }
        });
        cbGroup.put(cb3);
        cbGroup.getCheckedIds().toString();

        CheckBox cb4 = new CheckBox(this);
        cb4.setTag(4);
        cb4.setText("卫生洗浴");
        cb4.setTextSize(9);
        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(isChecked){

                    check.add("4");
                }else{
                    check.remove("4");
                }
                updateGridViewData();
            }
        });
        cbGroup.put(cb4);
        cbGroup.getCheckedIds().toString();

        CheckBox cb5 = new CheckBox(this);
        cb5.setTag(5);
        cb5.setText("客厅用水");
        cb5.setTextSize(9);
        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(isChecked){

                    check.add("5");
                }else{
                    check.remove("5");
                }
                updateGridViewData();
            }
        });
        cbGroup.put(cb5);
        cbGroup.getCheckedIds().toString();

        CheckBox cb6 = new CheckBox(this);
        cb6.setTag(6);
        cb6.setText("洗菜做饭");
        cb6.setTextSize(9);
        cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(isChecked){

                    check.add("6");
                }else{
                    check.remove("6");
                }
                updateGridViewData();
            }
        });
        cbGroup.put(cb6);
        cbGroup.getCheckedIds().toString();

        CheckBox cb7 = new CheckBox(this);
        cb7.setTag(7);
        cb7.setText("沐浴护肤");
        cb7.setTextSize(9);
        cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(isChecked){

                    check.add("7");
                }else{
                    check.remove("7");
                }
                updateGridViewData();
            }
        });
        cbGroup.put(cb7);
        cbGroup.getCheckedIds().toString();

        CheckBox cb8 = new CheckBox(this);
        cb8.setTag(8);
        cb8.setText("直接饮水");
        cb8.setTextSize(9);
        cb8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(isChecked){

                    check.add("8");
                }else{
                    check.remove("8");
                }
                updateGridViewData();
            }
        });
        cbGroup.put(cb8);
        cbGroup.getCheckedIds().toString();

        CheckBox cb9 = new CheckBox(this);
        cb9.setTag(9);
        cb9.setText("清洁衣物");
        cb9.setTextSize(9);
        cb9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(isChecked){

                    check.add("9");
                }else{
                    check.remove("9");
                }
                updateGridViewData();
            }
        });
        cbGroup.put(cb9);
        cbGroup.getCheckedIds().toString();

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(PurifierSolutionsActivity.this, namesAl, ImageIdAl, pricesAL);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.customdialog);
                dialog.setTitle("产品介绍");
                currentPid=i;

                // set the custom dialog components - text, image and button
                TextView textName = (TextView) dialog.findViewById(R.id.product_name);
                textName.setText(namesAl.get(i));

                ImageView image = (ImageView) dialog.findViewById(R.id.productImage);
                image.setImageResource(ImageIdAl.get(i));

                TextView textPrice = (TextView) dialog.findViewById(R.id.product_price);
                textPrice.setText("价格："+pricesAL.get(i));

                TextView textLife= (TextView) dialog.findViewById(R.id.product_life);
                textLife.setText("生命成本："+product_lifesAL.get(i).toString());

                TextView textprinciple = (TextView) dialog.findViewById(R.id.principle);
                textprinciple.setText("工作原理："+principlesAL.get(i));

                Button dialogButton = (Button) dialog.findViewById(R.id.addtocart);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AccountManager accountManager=AccountManager.getInstance();
                        if(accountManager.addToCart(currentPid)) {
                            Toast.makeText(PurifierSolutionsActivity.this, "产品已经添加到购物车", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(PurifierSolutionsActivity.this, "请先登录", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Button dialogCancelButton = (Button) dialog.findViewById(R.id.cancelBtn);
                // if button is clicked, close the custom dialog
                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }


    private void updateGridViewData() {
        pidAl.clear();
        namesAl.clear();
        ImageIdAl.clear();
        product_lifesAL.clear();
        pricesAL.clear();
        principlesAL.clear();
          for (int i = 0; i<check.size();i++){
            for(int j = 0;j<property.length;j++){
                for(int k =0;k<property[j].length;k++){
                    if (check.get(i).toString().equals(property[j][k])&&product_lifes[j]>=lowerPrice&&product_lifes[j]<=upperPrice){
                        pidAl.add(j);
                        namesAl.add(gridViewString[j]);
                        ImageIdAl.add(gridViewImageId[j]);
                        pricesAL.add(prices[j]);
                        product_lifesAL.add(product_lifes[j]+"");
                        principlesAL.add(principles[j]);
                        break;
                    }
                }
            }
        }

        Set<String> hs = new HashSet<>();
        hs.addAll(namesAl);
        namesAl.clear();
        namesAl.addAll(hs);


        Set<Integer> hsi = new HashSet<>();
        hsi.addAll(ImageIdAl);
        ImageIdAl.clear();
        ImageIdAl.addAll(hsi);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(PurifierSolutionsActivity.this, namesAl, ImageIdAl,pricesAL);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);

    }
}
