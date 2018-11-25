package cgodin.qc.ca.projet;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.stomp.StompUtils;
import ua.naiksoftware.stomp.ConnectionProvider;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;

public class MyLogin {
    public static String path = "http://424v.cgodin.qc.ca:8082";
    public static String JSESSIONID = "";
    public static Compte compteCourant = null;

    public void etablirConnexion( RequestQueue MyRequestQueue, String username, String password, final MainActivity activity)   {

        String strUrl = path+"/api/authenticate/"+username+"/"+password;
        Log.d("STOMP", "etablirConnexion() : "+strUrl);

        final MainActivity mainActivity = activity;

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("reponse", "REPONSE : "+response);
                mainActivity.afficherInformationCompte();
                mainActivity.stompClient.disconnect();

                HashMap<String, String> connectHeaders = new HashMap<>();
                connectHeaders.put("Cookie", "JSESSIONID=" + JSESSIONID);
                mainActivity.stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, MainActivity.STOMP_URL, connectHeaders);

                mainActivity.stompClient.connect();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", "ERROR : "+error);
            }
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
                Log.i("cookies",rawCookies);

                String[] cookieSplit = rawCookies.split("=");
                if ((cookieSplit[0] != null) && (cookieSplit[0].trim().matches("JSESSIONID"))) {
                    if (cookieSplit[1] != null)
                        JSESSIONID = (cookieSplit[1].split(";")[0]);
                    else
                        JSESSIONID = null;
                    Log.i("JSESSIONSID",JSESSIONID);
                }

                return super.parseNetworkResponse(response);
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }
}
