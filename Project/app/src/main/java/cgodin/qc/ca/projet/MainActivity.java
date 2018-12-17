package cgodin.qc.ca.projet;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import cgodin.qc.ca.projet.asynctasks.RequeteAvatar;
import cgodin.qc.ca.projet.asynctasks.RequeteInfoCompte;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String HOTE = "424v.cgodin.qc.ca:8086";
    public static final String REST_URL = "http://" + HOTE;
    public static final String STOMP_URL = "ws://" + HOTE + "/webSocket/websocket"; // <= Pas une erreur
    private static final Stomp.ConnectionProvider PROVIDER = Stomp.ConnectionProvider.OKHTTP;

    public String email;
    public String alias;
    public String role;
    public String groupe;
    public String img;
    public String points;
    public String credits;

    DrawerLayout drawer;
    ConstraintLayout constraintLayout;
    NavigationView navigationView;
    View header;

    MyLogin myLogin = new MyLogin();
    public StompClient stompClient = Stomp.over(PROVIDER, STOMP_URL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
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
                ((CombatFragment)fragment).setClient(stompClient);
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
            case R.id.nav_historique_combat:
                fragment = new HistoriqueCombatFragment();
                titre = R.string.titre_historique_combat;
                break;
            case R.id.nav_historique_examen:
                fragment = new HistoriqueExamenFragment();
                titre = R.string.titre_historique_examen;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        stompClient.disconnect();
    }

    /**
     * Permet de se connecter
     * @param email
     * @param password
     * @return si erreur
     */
    public boolean connexion(String email, String password){

        final RequestQueue RequestQueue = Volley.newRequestQueue(this);
        myLogin.etablirConnexion(RequestQueue,email,password, this);
        return true;
    }

    public void reconnexion(){
        final RequestQueue RequestQueue = Volley.newRequestQueue(this);
        myLogin.etablirConnexion(RequestQueue,email,"Patate123", this);
    }
    public void deconnexion(){
        ((TextView)header.findViewById(R.id.txt_pointsCredits)).setText("");
        ((TextView)header.findViewById(R.id.txtEmail)).setText(R.string.email_Anonyme);
        ((TextView)header.findViewById(R.id.txtAlias)).setText(R.string.alias_Anonyme);
        ((TextView)header.findViewById(R.id.txtCeinture)).setText(R.string.ceinture_Anonyme);

        ((ImageView)header.findViewById(R.id.imgHeaderAccount)).setImageResource(R.mipmap.ic_launcher_round);

        stompClient.disconnect();
        ((TextView)findViewById(R.id.txt_etat_connection)).setText(getString(R.string.test_etat_connexion));
    }
    public void afficherErreurConnexion(String message){
        if((TextView)findViewById(R.id.txt_etat_connection) != null)
            ((TextView)findViewById(R.id.txt_etat_connection)).setText(message);
    }
    public void afficherInformationCompte(){
        String strUrl = myLogin.path+"/api/monCompte";

        new RequeteInfoCompte(this).execute(strUrl);
    }
    public void updateHeaderInfo(String feed){
        try {
            JSONObject jsonObj = new JSONObject(feed);

            email = jsonObj.getString("courriel");
            alias = jsonObj.getString("alias");
            points = jsonObj.getString("points");
            credits = jsonObj.getString("credits");

            JSONObject jsonObjCeinture = new JSONObject(jsonObj.getString("groupe"));
            JSONObject jsonObjRole = new JSONObject(jsonObj.getString("role"));
            groupe = jsonObjCeinture.getString("groupe");
            role = jsonObjRole.getString("role");

            img = jsonObj.getString("avatarId");

            String strUrl = myLogin.path+"/api/avatars/"+img;

            if((TextView)findViewById(R.id.txt_etat_connection) != null)
                ((TextView)findViewById(R.id.txt_etat_connection)).setText(getString(R.string.test_etat_connexion_reussi,email));

            updateHeaderView();

            new RequeteAvatar(((ImageView)header.findViewById(R.id.imgHeaderAccount))).execute(Long.parseLong(img));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public View getHeader() {
        return header;
    }

    public void updateHeaderView() {
        ((TextView)header.findViewById(R.id.txtEmail)).setText(email);
        ((TextView)header.findViewById(R.id.txtAlias)).setText(alias);
        ((TextView)header.findViewById(R.id.txtCeinture)).setText(getString(R.string.ceinture, groupe,role));
        ((TextView)header.findViewById(R.id.txt_pointsCredits)).setText(getString(R.string.header_points, points, credits));
    }
}
