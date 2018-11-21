package cgodin.qc.ca.projet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CombatFragment extends Fragment implements View.OnClickListener {

    View view;
    public CombatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_combat,container, false);
        this.view = view;

        (view.findViewById(R.id.btnGagner)).setOnClickListener(this);
        (view.findViewById(R.id.btnPerdre)).setOnClickListener(this);
        (view.findViewById(R.id.btnNul)).setOnClickListener(this);

        (view.findViewById(R.id.btnArbitre_sansFautes)).setOnClickListener(this);
        (view.findViewById(R.id.btnAbritre_avecFaute)).setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGagner :
                gagnerMatch();
                break;
            case R.id.btnPerdre :
                perdreMatch();
                break;
            case R.id.btnNul :
                annulerMatch();
                break;

            case R.id.btnArbitre_sansFautes :
                arbitreSansFautes();
                break;
            case R.id.btnAbritre_avecFaute :
                arbitreAvecFautes();
                break;
        }
    }

    /**
     * Génère un combat où le combattant de la session courante est rouge et
     * s1@dojo est blanc. Le rouge gagne. L’arbitre de ce
     * combat est v1@dojo. Aucune faute n’est commise par l’arbitre. L’autorisation et la
     * validation doivent se faire au niveau du serveur.
     */
    private void gagnerMatch(){

    }

    /**
     * Génère un combat où le combattant de la session courante est rouge et
     * s1@dojo est blanc. Le blanc gagne. L’arbitre de ce
     * combat est v1@dojo. Aucune faute n’est commise par l’arbitre. L’autorisation et la
     * validation doivent se faire au niveau du serveur.
     */
    private void perdreMatch(){

    }

    /**
     * Génère un combat où le combattant de la session courante est rouge et
     * s1@dojo est blanc. C’est un match nul. L’arbitre de ce
     * combat est v1@dojo. Aucune faute n’est commise par l’arbitre. L’autorisation et la
     * validation doivent se faire au niveau du serveur.
     */
    private void annulerMatch(){

    }

    /**
     * Génère un combat où le combattant de la session courante est arbitre
     * entre rouge v1@dojo et blanc s1@dojo. Le rouge gagne.
     * Aucune faute n’est commise par l’arbitre. L’autorisation et la validation doivent se faire au
     * niveau du serveur
     */
    private void arbitreSansFautes(){

    }

    /**
     * Génère un combat où le combattant de la session courante est arbitre
     * entre rouge v1@dojo et blanc s1@dojo. Le rouge gagne,
     * cependant une faute est commise par l’arbitre. L’autorisation et la validation doivent se
     * faire au niveau du serveur.
     */
    private void arbitreAvecFautes(){

    }

}
