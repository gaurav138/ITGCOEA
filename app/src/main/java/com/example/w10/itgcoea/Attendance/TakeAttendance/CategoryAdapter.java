package com.example.w10.itgcoea.Attendance.TakeAttendance;

/**
 * Created by W10 on 09/02/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.w10.itgcoea.R;

import java.util.List;

/**
 * Created by W10 on 09/01/2018.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {
    private final List<Category> list;

    public CategoryAdapter(Context context, int resource, List<Category> list) {
        super(context, resource, list);
        this.list = list;
    }

    static class ViewHolder {
        protected TextView categoryName;
        protected CheckBox categoryCheckBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.row_category, null);
            viewHolder = new ViewHolder();
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.row_categoryname_textview);
            viewHolder.categoryCheckBox = (CheckBox) convertView.findViewById(R.id.row_category_checkbox);
            viewHolder.categoryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    list.get(getPosition).setSelected(buttonView.isChecked());
                    if (buttonView.isChecked()) {
                        if (!FavouriteCategoriesJsonParser.selectedCategories.contains(String.valueOf(list.get(getPosition).getCategory_Name()))) {
                            FavouriteCategoriesJsonParser.selectedCategories.add(String.valueOf(list.get(getPosition).getCategory_Name()));
                        }
                    } else {
                        if (FavouriteCategoriesJsonParser.selectedCategories.contains(String.valueOf(list.get(getPosition).getCategory_Name()))) {
                            FavouriteCategoriesJsonParser.selectedCategories.remove(String.valueOf(list.get(getPosition).getCategory_Name()));
                        }
                    }
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.row_categoryname_textview, viewHolder.categoryName);
            convertView.setTag(R.id.row_category_checkbox, viewHolder.categoryCheckBox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.categoryCheckBox.setTag(position);
        viewHolder.categoryName.setText(String.valueOf(list.get(position).getCategory_Name()));
        viewHolder.categoryCheckBox.setChecked(list.get(position).isSelected());

        return convertView;
    }
}