package com.example.w10.itgcoea.Attendance.TakeAttendance;

/**
 * Created by W10 on 09/02/2018.
 */

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by W10 on 09/01/2018.
 */

public class FavouriteCategoriesJsonParser {

    public static ArrayList<String> selectedCategories = new ArrayList<>();

    public ArrayList<Category> getParsedCategories(String years) {
        String JsonFavouriteCategories = "";
        ArrayList<Category> MyArraylist = new ArrayList<>();
        HttpClient httpClient = new DefaultHttpClient();
        String a="http://10.162.11.47/Attendance/getAttendance.php?year=";
        String b=years.replace(" ","%20");
        HttpGet httpGet = new HttpGet(a+b);

        //HttpGet httpGet = new HttpGet("http://10.162.11.47/Attendance/getAttendance.php?year=first%20year");
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            JsonFavouriteCategories = EntityUtils.toString(httpResponse.getEntity());
            JSONArray jsonArray = new JSONArray(JsonFavouriteCategories);

            for (int i = 0; i < jsonArray.length(); i++) {
                Category genres = new Category();
                JSONObject MyJsonObject = jsonArray.getJSONObject(i);
                genres.setCateogry_id(Integer.parseInt(MyJsonObject.getString("user_id")));
                genres.setCategory_Name(Integer.parseInt(MyJsonObject.getString("roll_no")));
                genres.setSelected(Boolean.parseBoolean(MyJsonObject.getString("selected")));
                MyArraylist.add(genres);
                if (MyJsonObject.getString("selected").equals("true")) {
                    selectedCategories.add(MyJsonObject.getString("roll_no"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return MyArraylist;
    }
}