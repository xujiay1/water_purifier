package xebia.ismail.water_purifier.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import xebia.ismail.water_purifier.R;

/**
 * Created by Admin on 3/13/2017.
 */
public class SearchLocationFragment extends Fragment {

    private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null;     //地级市
    private Spinner countySpinner = null;    //县级（区、县、县级市）
    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器
    ArrayAdapter<String> countyAdapter = null;    //县级适配器
    static int provincePosition = 3;


    //省级选项值
    private String[] province = new String[] {"请选择省份","北京","上海","天津","广东"};//,"重庆","黑龙江","江苏","山东","浙江","香港","澳门"};
    //地级选项值
    private String[][] city = new String[][]
            {
                    {"请选择市/区"},
                    { "东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区", "房山区", "通州区", "顺义区", "大兴区", "昌平区", "平谷区", "怀柔区", "密云县", "延庆县" },
                    { "长宁区", "静安区", "普陀区", "闸北区", "虹口区" },
                    { "和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区", "东丽区" },
                    { "广州", "深圳", "韶关" }//,"珠海","汕头","佛山","湛江","肇庆","江门","茂名","惠州","梅州", "汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮"}
            };

    //县级选项值
   /* private String[][][] county = new String[][][]
            {
                    {   //北京
                            {"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},
                            {"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"}
                    },
                    {    //上海
                            {"无"},{"无"},{"无"},{"无"},{"无"}
                    },
                    {    //天津
                            {"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"}
                    },
                    {    //广东
                            {"海珠区","荔湾区","越秀区","白云区","萝岗区","天河区","黄埔区","花都区","从化市","增城市","番禺区","南沙区"}, //广州
                            {"宝安区","福田区","龙岗区","罗湖区","南山区","盐田区"}, //深圳
                            {"武江区","浈江区","曲江区","乐昌市","南雄市","始兴县","仁化县","翁源县","新丰县","乳源县"}  //韶关
                    }
            };
            */
 //   private TextView one;
   // private TextView twe;
   // private TextView three;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);setSpinner();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_searchlocation, container, false);
        return v;
    }

    /*
 * 设置下拉框
 */
    private void setSpinner() {
        provinceSpinner = (Spinner)getActivity().findViewById(R.id.spin_province);
        citySpinner = (Spinner)getActivity().findViewById(R.id.spin_city);
      //  countySpinner = (Spinner)getActivity().findViewById(R.id.spin_county);
      //  one = (TextView) findViewById(R.id.one);
       // twe = (TextView) findViewById(R.id.twe);
       // three = (TextView) findViewById(R.id.three);

        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, province);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setSelection(0,true);  //设置默认选中项，此处为默认选中第4个值

        cityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, city[0]);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setSelection(0,true);  //默认选中第0个

    //    countyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, county[3][0]);
    //    countySpinner.setAdapter(countyAdapter);
     //   countySpinner.setSelection(2, true);

        //省级下拉框监听
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                //position为当前省级选中的值的序号
                //将地级适配器的值改变为city[position]中的值
                cityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, city[position]);
                // 设置二级下拉列表的选项内容适配器
                citySpinner.setAdapter(cityAdapter);
                provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
                String city = provinceSpinner.getSelectedItem().toString();
                String  province= citySpinner.getSelectedItem().toString();
                Log.d("zzz0", city);
                Log.d("zzz1", province);
               // one.setText(city);
              //  twe.setText(province);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
        //地级下拉监听
        /*
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                countyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, county[provincePosition][position]);
                countySpinner.setAdapter(countyAdapter);
                String county = countySpinner.getSelectedItem().toString();
                Log.d("zzz2", county);
                //three.setText(county);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });*/
    }



}

