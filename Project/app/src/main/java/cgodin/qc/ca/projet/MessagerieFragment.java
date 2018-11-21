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
import android.widget.Switch;
import android.widget.Toast;


public class MessagerieFragment extends Fragment implements View.OnClickListener {
    View view;
    public MessagerieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messagerie,container, false);
        this.view=view;
        (view.findViewById(R.id.btnMessagePublic)).setOnClickListener(this);
        (view.findViewById(R.id.btnMessagePrive)).setOnClickListener(this);

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
            case R.id.btnMessagePublic :
                envoyerMessagePublic();
                break;
            case R.id.btnMessagePrive :
                envoyerMessagePrive();
                break;
        }
    }

    /**
     * Permet l'envoie de message public
     */
    private void envoyerMessagePublic(){
        // TODO : implémenter l'envoie de message public
    }

    /**
     * Permet l'envoie de message privé
     */
    private void envoyerMessagePrive(){
        // TODO : implémenter l'envoie de message privé
    }
}
