package com.example.w10.itgcoea.ApproveStudent;

/**
 * Created by W10 on 30/03/2018.
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

public class CategoryAdapter2 extends ArrayAdapter<Category2> {
    private final List<Category2> list;

    public CategoryAdapter2(Context context, int resource, List<Category2> list) {
        super(context, resource, list);
        this.list = list;
    }

    static class ViewHolder {
        protected TextView categoryName;
        protected TextView name;
        protected TextView email;
        protected CheckBox categoryCheckBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.row_category2, null);
            viewHolder = new ViewHolder();
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.row_categoryname_textview);
            viewHolder.categoryCheckBox = (CheckBox) convertView.findViewById(R.id.row_category_checkbox);
            viewHolder.name=(TextView) convertView.findViewById(R.id.textView10);
            viewHolder.email=(TextView) convertView.findViewById(R.id.textView15);
            viewHolder.categoryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    list.get(getPosition).setSelected(buttonView.isChecked());
                    if (buttonView.isChecked()) {
                        if (!FavouriteCategoriesJsonParser2.selectedCategories.contains(String.valueOf(list.get(getPosition).getCategory_Name()))) {
                            FavouriteCategoriesJsonParser2.selectedCategories.add(String.valueOf(list.get(getPosition).getCategory_Name()));
                        }
                    } else {
                        if (FavouriteCategoriesJsonParser2.selectedCategories.contains(String.valueOf(list.get(getPosition).getCategory_Name()))) {
                            FavouriteCategoriesJsonParser2.selectedCategories.remove(String.valueOf(list.get(getPosition).getCategory_Name()));
                        }
                    }
                }
            });
            convertView.setTag(viewHolder);
        //    convertView.setTag(R.id.textView10, viewHolder.name);
        // convertView.setTag(R.id.textView15, viewHolder.email);

            convertView.setTag(R.id.row_categoryname_textview, viewHolder.categoryName);
            convertView.setTag(R.id.row_category_checkbox, viewHolder.categoryCheckBox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.categoryCheckBox.setTag(position);
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.email.setText(list.get(position).getEmail());
        viewHolder.categoryName.setText(String.valueOf(list.get(position).getCategory_Name()));
        viewHolder.categoryCheckBox.setChecked(list.get(position).isSelected());

        return convertView;
    }
}