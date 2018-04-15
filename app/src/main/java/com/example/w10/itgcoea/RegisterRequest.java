package com.example.w10.itgcoea;

/**
 * Created by W10 on 12/10/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://10.162.11.47/studentreg2.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String password, int roll, String year, String email, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);

        params.put("roll_no",roll+"");

        params.put("year",year);

        params.put("email",email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
