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
import android.widget.Toast;

import cgodin.qc.ca.projet.asynctasks.RequeteAnciennete;


public class AncienneteFragment extends Fragment implements View.OnClickListener {
    View view;
    public AncienneteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anciennete,container, false);
        this.view=view;
        (view.findViewById(R.id.btnAnciennete)).setOnClickListener(this);

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
                promotion();
                break;
        }

    }

    /**
     * Permet d'augmenter le role du compte courant
     */
    private void promotion(){
        String strUrl = MyLogin.path+"/api/compte/role/"+((MainActivity)getActivity()).email;
        Log.i("anciennete", "ANCIENNETE URL : "+strUrl);

        new RequeteAnciennete(this).execute(strUrl);
    }
    public void postExecuted(){
        ((MainActivity)getActivity()).reconnexion();
    }
}
