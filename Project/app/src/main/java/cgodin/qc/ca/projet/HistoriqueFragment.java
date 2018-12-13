package cgodin.qc.ca.projet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cgodin.qc.ca.projet.adapter.CombatAdapter;
import cgodin.qc.ca.projet.asynctasks.RequeteAnciennete;
import cgodin.qc.ca.projet.asynctasks.RequeteListe;
import cgodin.qc.ca.projet.models.Combat;
import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.models.CompteImpl;
import cgodin.qc.ca.projet.models.Groupe;


public class HistoriqueFragment extends Fragment implements View.OnClickListener, RequeteListe.Handler<Combat> {
    View view;
    private RecyclerView mRecyclerView;
    private CombatAdapter mAdapter;
    private List<Combat> mCombatList;
    public HistoriqueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique,container, false);
        this.view=view;
        //(view.findViewById(R.id.btnAnciennete)).setOnClickListener(this);


                /*
                Compte rouge = new CompteImpl();
                ((CompteImpl) rouge).setAlias("rouge");
                Compte arbitre = new CompteImpl();
                ((CompteImpl) arbitre).setAlias("arbitre");
                Compte blanc = new CompteImpl();
                ((CompteImpl) blanc).setAlias("blanc");

                Groupe ceintureRouge = new Groupe(1, "blanc");
                Groupe ceintureBlanc = new Groupe(2, "jaune");

                //Combat combaTest = new Combat(rouge,blanc,arbitre,10,0,1ceintureRouge,ceintureBlanc)
                for(int i = 1; i <= 10; i++){
                   Combat c = new Combat(rouge,blanc,arbitre,i,i+10,1,ceintureRouge,ceintureBlanc);
                    mCombatList.add(c);
                }
                */

        remplirListeCombattants();

        return view;
    }

    private void remplirListeCombattants() {
        String url = "http://"+MainActivity.HOTE+"/api/myCombats";
        RequeteListe<Combat> requeteCombat = new RequeteListe<>(this, Combat.class);

        requeteCombat.execute(url);
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
            /*
            case R.id.btnAnciennete :
                //promotion();
                break;
                */
        }

    }

    @Override
    public void afficherList(List<Combat> lstCombats) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardview);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCombatList = new ArrayList<>();
        mAdapter = new CombatAdapter(lstCombats,getContext());
        mRecyclerView.setAdapter(mAdapter);

        Toast.makeText(getContext(),"Requete", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void echecRequete() {
        Toast.makeText(getContext(),"Erreur en recueillant la liste des combats", Toast.LENGTH_SHORT).show();
    }

}
