package com.example.w10.itgcoea.Attendance.TakeAttendance;

/**
 * Created by W10 on 09/02/2018.
 */

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

/**
 * Created by W10 on 09/01/2018.
 */

public class InsertUpdateFavouriteCategories {
    public static String insertUpdateCall(String categoriesCsv,String date,String subject) {
        String response = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://10.162.11.47/Attendance/insertAttendance.php");
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", "tutorials@codingtrickshub.com"));
            nameValuePairs.add(new BasicNameValuePair("favouriteCategories", categoriesCsv));
            nameValuePairs.add(new BasicNameValuePair("subject", subject));
            nameValuePairs.add(new BasicNameValuePair("date", date));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            response = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}