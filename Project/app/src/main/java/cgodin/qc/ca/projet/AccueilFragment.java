package cgodin.qc.ca.projet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Base64;


public class AccueilFragment extends Fragment implements View.OnClickListener {
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

        return view;
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
}
