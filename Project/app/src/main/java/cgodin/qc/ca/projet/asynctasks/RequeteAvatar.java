package cgodin.qc.ca.projet.asynctasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LongSparseArray;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import cgodin.qc.ca.projet.MainActivity;
import cgodin.qc.ca.projet.MyLogin;
import cgodin.qc.ca.projet.R;
import okhttp3.OkHttpClient;

public class RequeteAvatar extends AsyncTask<Long, Void, byte[]> {
    private static final HashMap<Long, byte[]> avatarCache = new HashMap<>();

    private ImageView imgView;

    public RequeteAvatar(ImageView imgView){
        this.imgView = imgView;
    }

    private Exception exception;

    protected byte[] doInBackground(Long... ids) {
        Log.d("URL", " URL =" + ids[0]);
        URL url = null;

        Long id = ids[0];
        if (avatarCache.containsKey(id)) {
            return avatarCache.get(id);
        }

        try {
            url = new URL(MainActivity.REST_URL + "/api/avatars/" + ids[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();



        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("cookie", "JSESSIONID=" + MyLogin.JSESSIONID )
                .addHeader("rest","oui")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .get()

                .build();

        okhttp3.Response response = null;
        byte[] bytes  = null;
        try {
            response = client.newCall(request).execute();
            bytes = response.body().bytes();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        avatarCache.put(id, bytes);

        return bytes;
    }

    protected void onPostExecute(byte[] img) {
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            imgView.setImageBitmap(bitmap);
        } catch (Exception ignored) {

        }
    }
}