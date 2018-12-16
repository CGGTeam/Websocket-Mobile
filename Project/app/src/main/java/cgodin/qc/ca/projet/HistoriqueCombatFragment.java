package cgodin.qc.ca.projet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cgodin.qc.ca.projet.adapter.CombatAdapter;
import cgodin.qc.ca.projet.asynctasks.RequeteListe;
import cgodin.qc.ca.projet.models.Combat;


public class HistoriqueCombatFragment extends Fragment implements View.OnClickListener, RequeteListe.Handler<Combat> {
    View view;
    private RecyclerView mRecyclerView;
    private CombatAdapter mAdapter;

    public HistoriqueCombatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique_combat,container, false);
        this.view=view;

        remplirListeCombats();

        return view;
    }

    private void remplirListeCombats() {
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
    }

    @Override
    public void afficherList(List<Combat> lstCombats) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardview);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new CombatAdapter(lstCombats,getContext());
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public void echecRequete() {
        Toast.makeText(getContext(),"Erreur en recueillant la liste des combats", Toast.LENGTH_SHORT).show();
    }
}
