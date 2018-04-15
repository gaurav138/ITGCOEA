package com.example.w10.itgcoea;

/**
 * Created by W10 on 12/10/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest1 extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://10.162.11.47/facultyreg.php";
    private Map<String, String> params;

    public RegisterRequest1(String name, String username, String email, String password, String qualification, String courses, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("email",email);
        params.put("password", password);

        params.put("qualification",qualification);

        params.put("courses",courses);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
