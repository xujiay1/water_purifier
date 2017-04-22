package xebia.ismail.water_purifier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public  class CustomGridViewActivity extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> gridViewString;
    private final ArrayList<Integer> gridViewImageId;
    private final ArrayList<Integer> priceAl;

    public  CustomGridViewActivity(Context context, ArrayList<String> gridViewString, ArrayList<Integer> gridViewImageId, ArrayList<Integer> price) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
        this.priceAl = price;
    }

    @Override
    public int getCount() {
        return gridViewString.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            TextView textViewAndroidPrice = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_price);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridViewString.get(i));
            textViewAndroidPrice.setText("参考价格："+String.valueOf(priceAl.get(i)));
            imageViewAndroid.setImageResource(gridViewImageId.get(i));
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
