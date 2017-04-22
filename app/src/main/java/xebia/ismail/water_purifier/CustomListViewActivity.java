package xebia.ismail.water_purifier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xujiayi on 17/4/20.
 */

public class CustomListViewActivity extends BaseAdapter {

    private ProductManager productManager;
    private ArrayList<Integer> productIds;
    private Context mContext;
    private ArrayList<Integer> checkedItems;

    public CustomListViewActivity(Context context, ArrayList<Integer> productIds, ArrayList<Integer> checkedItems)
    {
        productManager=ProductManager.getInstance();
        this.productIds=productIds;
        this.checkedItems=checkedItems;
        mContext=context;
    }

    public ArrayList<Integer> getCheckedItems(){
        return checkedItems;
    }

    @Override
    public int getCount() {
        return productIds.size();
    }

    @Override
    public Object getItem(int position) {
        return productManager.getProduct(productIds.get(position));
    }

    @Override
    public long getItemId(int position) {
        return ((Product)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            listViewAndroid = new View(mContext);
            listViewAndroid = inflater.inflate(R.layout.listview_layout, null);
            TextView textViewAndroid = (TextView) listViewAndroid.findViewById(R.id.android_listview_text);
            TextView textViewAndroidPrice = (TextView) listViewAndroid.findViewById(R.id.android_listview_price);
            ImageView imageViewAndroid = (ImageView) listViewAndroid.findViewById(R.id.android_listview_image);
            CheckBox checkBoxAndroid =(CheckBox) listViewAndroid.findViewById(R.id.checkbox_item);

            if(checkedItems.contains(position))
                checkBoxAndroid.setChecked(true);

            Product product = (Product)getItem(position);

            textViewAndroid.setText(product.getBrand()+" "+product.getType());
            textViewAndroidPrice.setText(product.getPrice()+"");
            imageViewAndroid.setImageResource(product.getImageid());
        } else {
            listViewAndroid = (View) convertView;
        }

        return listViewAndroid;
    }
}
