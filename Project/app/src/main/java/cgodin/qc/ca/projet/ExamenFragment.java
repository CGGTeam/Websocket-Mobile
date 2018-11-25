package cgodin.qc.ca.projet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cgodin.qc.ca.projet.asynctasks.RequeteAnciennete;
import cgodin.qc.ca.projet.asynctasks.RequeteExamen;


public class ExamenFragment extends Fragment implements View.OnClickListener{
    View view;
    public ExamenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_examen,container, false);
        this.view=view;
        (view.findViewById(R.id.btnExamenReeussi)).setOnClickListener(this);
        (view.findViewById(R.id.btnExamenEchec)).setOnClickListener(this);

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
            case R.id.btnExamenReeussi :
                passerExamen();
                break;
            case R.id.btnExamenEchec :
                echouerExamen();
                break;
        }

    }

    /**
     *  génère un examen réussi pour la session courante. Le bouton est toujours
     *  actif . Le Grand Vénérable est l’évaluateur. L’examen est généré uniquement si le
     *  nombre de points et de crédit sont suffisants.
     */
    private void passerExamen(){
        String strUrl = MyLogin.path+"/api/examen/reussi/"+  ((MainActivity)getActivity()).email;
        Log.i("anciennete", "ANCIENNETE URL : "+strUrl);

        new RequeteExamen(this).execute(strUrl,  ((MainActivity)getActivity()).email);
    }

    /**
     * génère un examen non réussi pour la session courante. Le bouton est
     * toujours actif. Le Grand Vénérable est l’évaluateur. L’examen est généré uniquement
     * si le nombre de points et de crédit sont suffisants.
     */
    private void echouerExamen(){
        String strUrl = MyLogin.path+"/api/examen/echec/"+  ((MainActivity)getActivity()).email;
        Log.i("anciennete", "ANCIENNETE URL : "+strUrl);

        new RequeteExamen(this).execute(strUrl,  ((MainActivity)getActivity()).email);
    }

    public void postExecuted(String reponse){
        ((TextView)view.findViewById(R.id.textEtatExamen)).setText(reponse);
        ((MainActivity)getActivity()).reconnexion();
    }
}
