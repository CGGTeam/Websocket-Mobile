package cgodin.qc.ca.projet;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ConstraintLayout constraintLayout;
    NavigationView navigationView;
    View header;

    //StompSession stomp = new StompSession();
    Login login = new Login();

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
     * @param alias
     * @return si erreur
     */
    public boolean connexion(String email, String password, String alias, String ceinture, String role, String img){

        login.etablirConnexion(getString(R.string.path), email, password);



        ((TextView)header.findViewById(R.id.txtEmail)).setText(email);
        ((TextView)header.findViewById(R.id.txtAlias)).setText(alias);
        ((TextView)header.findViewById(R.id.txtCeinture)).setText(getString(R.string.ceinture, ceinture,role));

        return true;
    }
}
