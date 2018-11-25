package cgodin.qc.ca.projet;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class MyLogin {
    public static String path = "http://10.0.2.2:8082";


    public void etablirConnexion( RequestQueue MyRequestQueue, String username, String password)   {

        Log.d("STOMP", "etablirConnexion()");
        String strUrl = path+"/api/authenticate/"+username+"/"+password;

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Log.i("reponse", "REPONSE : "+response);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", "ERROR : "+error);
            }

        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                // since we don't know which of the two underlying network vehicles
                // will Volley use, we have to handle and store session cookies manually
                Log.i("response",response.headers.toString());
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
                Log.i("cookies",rawCookies);
                return super.parseNetworkResponse(response);
            }


        };

        MyRequestQueue.add(MyStringRequest);

    }
}
