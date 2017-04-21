package xebia.ismail.water_purifier.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import xebia.ismail.water_purifier.R;

/**
 * Created by Admin on 3/13/2017.
 */
public class SearchLocationFragment extends Fragment {

    private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null;     //地级市

    private JSONObject jsonObject = null;

    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器

    private Map<String, String[]> cityMap = new HashMap<String, String[]>();//key:省p---value:市n  value是一个集合
    private Map<String, String[]> areaMap = new HashMap<String, String[]>();//key:市n---value:区s    区也是一个集合

    static int provincePosition = 3;
    private EditText community;

    //省级选项值
    private String[] province = null;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle("水质查询");
        initJsonData();//初始化json数据
        initDatas();
        setSpinner();

        community = (EditText) getActivity().findViewById(R.id.editText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_searchlocation, container, false);

        return v;
    }

       //初始化省市区数据
    private void initDatas() {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("citylist");//获取整个json数据
            province = new String[jsonArray.length()];//封装数据
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);//jsonArray转jsonObject
                String provStr = jsonP.getString("p");//获取所有的省
                province[i] = provStr;//封装所有的省
                JSONArray jsonCity = null;

                try {
                    jsonCity = jsonP.getJSONArray("c");//在所有的省中取出所有的市，转jsonArray
                } catch (Exception e) {
                    continue;
                }
                //所有的市
                String[] allCity = new String[jsonCity.length()];//所有市的长度
                for (int c = 0; c < jsonCity.length(); c++) {
                    JSONObject jsonCy = jsonCity.getJSONObject(c);//转jsonObject
                    String cityStr = jsonCy.getString("n");//取出所有的市
                    allCity[c] = cityStr;//封装市集合

                    JSONArray jsonArea = null;
                    try {
                        jsonArea = jsonCy.getJSONArray("a");//在从所有的市里面取出所有的区,转jsonArray
                    } catch (Exception e) {
                        continue;
                    }
                    String[] allArea = new String[jsonArea.length()];//所有的区
                    for (int a = 0; a < jsonArea.length(); a++) {
                        JSONObject jsonAa = jsonArea.getJSONObject(a);
                        String areaStr = jsonAa.getString("s");//获取所有的区
                        allArea[a] = areaStr;//封装起来
                    }

                    areaMap.put(cityStr, allArea);//某个市取出所有的区集合


                }
                cityMap.put(provStr, allCity);//某个省取出所有的市,
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObject = null;//清空所有的数据
    }

    /**
     * 从assert文件夹中获取json数据
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getResources().getAssets().open("city.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len = -1;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, "gb2312"));//根据字节长度设置编码
            }
            is.close();//关闭流
            jsonObject = new JSONObject(sb.toString());//为json赋值
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        provinceAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        for (int i = 0; i < province.length; i++)
        {
            provinceAdapter.add(province[i]);//添加每一个省
        }
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//按下的效果
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setSelection(0,true);  //设置默认选中项，此处为默认选中第4个值

        cityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        String [] cities=cityMap.get(province[0]);
        for(int i=0;i<cities.length;i++)
        {
            cityAdapter.add(cities[i]);
        }
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                cityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);

                String [] cities=cityMap.get(province[position]);
                for(int i=0;i<cities.length;i++)
                {
                    cityAdapter.add(cities[i]);
                }

                // 设置二级下拉列表的选项内容适配器
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(cityAdapter);
                provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
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

