package cgodin.qc.ca.projet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class ExamenFragment extends Fragment implements View.OnClickListener{
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

        ((Button) view.findViewById(R.id.btnExamenReeussi)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnExamenEchec)).setOnClickListener(this);

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
                Toast.makeText(v.getContext(), "Examen réeussi",   Toast.LENGTH_LONG).show();
                // TODO : implémenter le passage d'examen
                break;
            case R.id.btnExamenEchec :
                Toast.makeText(v.getContext(), "Examen échec",   Toast.LENGTH_LONG).show();
                // TODO : implémenter l'échec d'examen
                break;
        }

    }
}
