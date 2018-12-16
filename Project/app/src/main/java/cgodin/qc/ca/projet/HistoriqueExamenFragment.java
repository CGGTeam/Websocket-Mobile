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
import cgodin.qc.ca.projet.adapter.ExamenAdapter;
import cgodin.qc.ca.projet.asynctasks.RequeteListe;
import cgodin.qc.ca.projet.asynctasks.RequeteListeExamens;
import cgodin.qc.ca.projet.models.Combat;
import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.models.Examen;


public class HistoriqueExamenFragment extends Fragment implements View.OnClickListener, RequeteListe.Handler<Combat>, RequeteListeExamens.Handler<Examen> {
    View view;
    private RecyclerView mRecyclerView;
    private ExamenAdapter mAdapter;
    private List<Combat> lstCombat;
    public HistoriqueExamenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique_examen,container, false);
        this.view=view;

        remplirListeCombats();

        return view;
    }

    private void remplirListeCombats() {
        String url = "http://"+MainActivity.HOTE+"/api/myCombats";
        RequeteListe<Combat> requeteExamen = new RequeteListe<>(this, Combat.class);

        requeteExamen.execute(url);
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
    public void remplirListeExamens(List<Examen> lstExamens) {

        if(lstExamens.isEmpty()){
            mRecyclerView.setVisibility(View.GONE);
            view.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            return;
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardview);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new ExamenAdapter(lstExamens,getContext(), lstCombat);
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public void afficherList(List<Combat> lstCombat) {
        this.lstCombat = lstCombat;
        String url = "http://"+MainActivity.HOTE+"/api/myExamens";
        RequeteListeExamens<Examen> requeteExamen = new RequeteListeExamens<>(this, Examen.class);

        requeteExamen.execute(url);
    }
    @Override
    public void echecRequete() {
        Toast.makeText(getContext(),"Erreur en recueillant la liste d'examens", Toast.LENGTH_SHORT).show();
    }

}
