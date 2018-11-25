package cgodin.qc.ca.projet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import cgodin.qc.ca.projet.asynctasks.RequeteAvatar;
import cgodin.qc.ca.projet.asynctasks.RequeteInfoCompte;
import cgodin.qc.ca.projet.models.Compte;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import cgodin.qc.ca.projet.stomp.StompTopic;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String STOMP_URL = "ws://424v.cgodin.qc.ca:8082/webSocket/websocket"; // <= Pas une erreur
    private static final Stomp.ConnectionProvider PROVIDER = Stomp.ConnectionProvider.OKHTTP;

    DrawerLayout drawer;
    ConstraintLayout constraintLayout;
    NavigationView navigationView;
    View header;

    MyLogin myLogin = new MyLogin();
    StompClient stompClient = Stomp.over(PROVIDER, STOMP_URL);

    public static String SESSIONREST = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //le main layout ou les fragments sont inflate
        constraintLayout = (ConstraintLayout) findViewById(R.id.main_container);

        //le layout du drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //le header ou les infos du compte sont affichés
        header=navigationView.getHeaderView(0);

        /*
        //affiche le fragment de connexion
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AccueilFragment(), getString(R.string.text_Connexion)).commit();
        setTitle(R.string.text_Connexion);
        */

        stompClient.lifecycle()
                    .subscribe(lsEvent -> {
                        switch (lsEvent.getType()) {
                            case OPENED:
                                Log.d("STOMP", "Stomp connection opened");
                                break;
                            case ERROR:
                                Log.d("STOMP", "Error", lsEvent.getException());
                                break;
                            case CLOSED:
                                Log.d("STOMP", "Stomp connection closed");
                                break;
                        }
                    });

        stompClient.connect();

        final RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        int titre;
        switch(id) {
            case R.id.nav_accueil:
                fragment = new AccueilFragment();
                titre = R.string.text_Connexion;
                break;
            case R.id.nav_combat:
                fragment = new CombatFragment();
                titre = R.string.combat;
                break;
            case R.id.nav_examen:
                fragment = new ExamenFragment();
                titre = R.string.examen;
                break;
            case R.id.nav_anciennete:
                fragment = new AncienneteFragment();
                titre = R.string.anciennete;
                break;
            case R.id.nav_messagerie:
                fragment = new MessagerieFragment();
                ((MessagerieFragment)fragment).setStompClient(stompClient);
                titre = R.string.messagerie;
                break;
            default:
                fragment = new AccueilFragment();
                titre = R.string.text_Connexion;
                break;
        }
        setTitle(titre);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment, getString(titre)).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Permet de se connecter
     * @param email
     * @param password
     * @return si erreur
     */
    public boolean connexion(String email, String password){

        final RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        myLogin.etablirConnexion(MyRequestQueue,email,password, this);
        return true;
    }

    public void afficherInformationCompte(){
        String strUrl = myLogin.path+"/api/monCompte";

        new RequeteInfoCompte(this).execute(strUrl);
    }
    public void updateHeaderInfo(String feed){
        try {
            JSONObject jsonObj = new JSONObject(feed);

            String email = jsonObj.getString("courriel");
            String alias = jsonObj.getString("alias");

            JSONObject jsonObjCeinture = new JSONObject(jsonObj.getString("groupe"));
            JSONObject jsonObjRole = new JSONObject(jsonObj.getString("role"));
            String ceinture = jsonObjCeinture.getString("groupe");
            String role = jsonObjRole.getString("role");

            String img = jsonObj.getString("avatarId");

            String strUrl = myLogin.path+"/api/avatars/"+img;

            ((TextView)header.findViewById(R.id.txtEmail)).setText(email);
            ((TextView)header.findViewById(R.id.txtAlias)).setText(alias);
            ((TextView)header.findViewById(R.id.txtCeinture)).setText(getString(R.string.ceinture, ceinture,role));

            new RequeteAvatar(this).execute(strUrl);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void showAvatar(byte[] img){

        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        ((ImageView)header.findViewById(R.id.imgHeaderAccount)).setImageBitmap(bitmap);
    }

    public StompClient getStompClient() {
        return stompClient;
    }
}
