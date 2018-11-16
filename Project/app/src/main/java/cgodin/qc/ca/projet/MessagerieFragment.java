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

        ((Button) view.findViewById(R.id.btnMessagePublic)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.btnMessagePrive)).setOnClickListener(this);

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
                Toast.makeText(v.getContext(), "message public",   Toast.LENGTH_LONG).show();
                break;
            case R.id.btnMessagePrive :
                Toast.makeText(v.getContext(), "message privé",   Toast.LENGTH_LONG).show();
                break;
        }

    }
}
