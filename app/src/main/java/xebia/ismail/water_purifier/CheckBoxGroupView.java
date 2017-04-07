package xebia.ismail.water_purifier;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hui on 4/7/2017.
 */

public class CheckBoxGroupView extends GridLayout {
    List<CheckBox> checkboxes = new ArrayList<>();

    public CheckBoxGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void put(CheckBox checkBox) {
        checkboxes.add( checkBox);
        invalidate();
        requestLayout();
    }

    public void remove(Integer id) {
        // TODO: Remove items from ArrayList
    }

    public List<?> getCheckboxesChecked(){

        List<CheckBox> checkeds = new ArrayList<>();
        for (CheckBox c : checkboxes){
            if(c.isChecked())
                checkeds.add(c);
        }

        return checkeds;
    }

    public List<Object> getCheckedIds(){

        List<Object> checkeds = new ArrayList<>();
        for (CheckBox c : checkboxes){
            if(c.isChecked())
                checkeds.add(c.getTag());
        }
        return checkeds;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        for(CheckBox c: checkboxes) {
            addView(c);
        }

        invalidate();
        requestLayout();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
