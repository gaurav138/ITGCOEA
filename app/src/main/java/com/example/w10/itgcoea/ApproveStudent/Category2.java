package com.example.w10.itgcoea.ApproveStudent;

/**
 * Created by W10 on 30/03/2018.
 */


    public class Category2 {
        private int cateogry_id;
        private int category_Name;
        private String email;
    private String name;

        private boolean isSelected;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCateogry_id() {
            return cateogry_id;
        }
        public void setCateogry_id(int cateogry_id) {
            this.cateogry_id = cateogry_id;
        }
        public int getCategory_Name() {
            return category_Name;
        }
        public void setCategory_Name(int category_Name) {
            this.category_Name = category_Name;
        }
        public boolean isSelected() {
            return isSelected;
        }
        public void setSelected(boolean selected) {
            isSelected = selected;
        }

    }

