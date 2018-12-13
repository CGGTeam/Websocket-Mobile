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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cgodin.qc.ca.projet.adapter.CombatAdapter;
import cgodin.qc.ca.projet.asynctasks.RequeteAnciennete;
import cgodin.qc.ca.projet.models.Combat;
import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.models.CompteImpl;
import cgodin.qc.ca.projet.models.Groupe;


public class HistoriqueFragment extends Fragment implements View.OnClickListener {
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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardview);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Compte rouge = new CompteImpl();
        ((CompteImpl) rouge).setAlias("rouge");
        Compte arbitre = new CompteImpl();
        ((CompteImpl) arbitre).setAlias("arbitre");
        Compte blanc = new CompteImpl();
        ((CompteImpl) blanc).setAlias("blanc");

        Groupe ceintureRouge = new Groupe(1, "blanc");
        Groupe ceintureBlanc = new Groupe(2, "jaune");

        mCombatList = new ArrayList<>();
        //Combat combaTest = new Combat(rouge,blanc,arbitre,10,0,1ceintureRouge,ceintureBlanc)
        for(int i = 1; i <= 10; i++){
           Combat c = new Combat(rouge,blanc,arbitre,i,i+10,1,ceintureRouge,ceintureBlanc);
            mCombatList.add(c);
        }

        mAdapter = new CombatAdapter(mCombatList,getContext());
        mRecyclerView.setAdapter(mAdapter);

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
            case R.id.btnAnciennete :
                //promotion();
                break;
        }

    }

}
