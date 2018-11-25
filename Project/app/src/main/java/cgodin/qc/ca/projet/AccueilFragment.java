package cgodin.qc.ca.projet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

import cgodin.qc.ca.projet.asynctasks.RequeteListe;
import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.models.SessionUtilisateur;


public class AccueilFragment extends Fragment implements View.OnClickListener, RequeteListe.Handler<Compte> {
    private static final String URL_COMPTES = "http://10.0.2.2:8082/api/comptes/defaults";

    View view;
    public AccueilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accueil,container, false);
        this.view=view;

        (view.findViewById(R.id.btnConnexion)).setOnClickListener(this);
        ((TextView)view.findViewById(R.id.txt_etat_connection)).setText(SessionUtilisateur.getEtatConnexion());

        remplirListeCombattants();

        return view;
    }

    private void remplirListeCombattants() {
        RequeteListe<Compte> requeteComptes = new RequeteListe<>(this);

        requeteComptes.execute(URL_COMPTES);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnConnexion :
                tentativeConnexion();
                break;
        }

    }

    /**
     * Permet la tentative de connexion
     */
    private void tentativeConnexion(){
        Toast.makeText(view.getContext(), "Connexion",   Toast.LENGTH_LONG).show();
        // TODO : implémenter la connexion

        String email = "v1@dojo";
        String password = "Patate123";
        String alias = "Test";
        String ceinture = "Rouge";
        String role = "Sensei";
        String img = "";

        //Pour afficher les information du compte dans le header du drawer
        ((MainActivity)getActivity()).connexion(email, password, alias, ceinture, role, img);
    }

    @Override
    public void afficherList(List<Compte> listeComptes) {
        Spinner combattants = view.findViewById(R.id.spinner_combattants);

        String[] usernames = listeComptes.stream().map(Compte::getCourriel).toArray(String[]::new);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),
                R.layout.support_simple_spinner_dropdown_item,
                usernames);

        combattants.setAdapter(adapter);
    }

    @Override
    public void echecRequete() {
//        Toast.makeText(getContext(),"Erreur en recueillant la liste des combattants.", Toast.LENGTH_SHORT).show();
    }
}
