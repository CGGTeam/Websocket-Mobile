package cgodin.qc.ca.projet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cgodin.qc.ca.projet.asynctasks.JsonUtils;
import cgodin.qc.ca.projet.stomp.Commande;
import cgodin.qc.ca.projet.stomp.DonneesReponseCommande;
import cgodin.qc.ca.projet.stomp.HearbeatLoop;
import cgodin.qc.ca.projet.stomp.LobbyPosition;
import cgodin.qc.ca.projet.stomp.LobbyRole;
import cgodin.qc.ca.projet.stomp.ReponseCommande;
import cgodin.qc.ca.projet.stomp.SanitizedLobbyUser;
import cgodin.qc.ca.projet.stomp.SerializableLobby;
import cgodin.qc.ca.projet.stomp.StompTopic;
import cgodin.qc.ca.projet.stomp.TypeCommande;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.client.StompClient;


public class CombatFragment extends Fragment implements View.OnClickListener {

    View view;
    private StompClient client;
    private TextView txtAilleurs;
    private TextView txtSpectateurs;
    private TextView txtCombattants;
    private TextView txtArbitre;
    private RadioGroup group;
    private HearbeatLoop hearbeatLoop;
    private TextView txtDerniereCommande;
    private MainActivity mainActivity;
    private SanitizedLobbyUser monCompte;

    private List<Disposable> subscriptions = new ArrayList<>();

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

        mainActivity = (MainActivity) getContext();


        (view.findViewById(R.id.btnGagner)).setOnClickListener(this);
        (view.findViewById(R.id.btnPerdre)).setOnClickListener(this);
        (view.findViewById(R.id.btnNul)).setOnClickListener(this);

        (view.findViewById(R.id.btnArbitre_sansFautes)).setOnClickListener(this);
        (view.findViewById(R.id.btnAbritre_avecFaute)).setOnClickListener(this);

        view.findViewById(R.id.cbArbitre).setOnClickListener(this);

        ((RadioButton)view.findViewById(R.id.radioButton5)).setChecked(true);

        group = view.findViewById(R.id.radioGroup);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int resId) {
                RadioButton checkedRadioButton = group.findViewById(resId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    ((CheckBox)view.findViewById(R.id.cbArbitre)).setChecked(false);
                    envoyerChangement(trouverRole(resId));
                }
            }

            private LobbyRole trouverRole(int resId) {
                switch (resId) {
                    case R.id.radioButton4:
                        //Ailleurs
                        return LobbyRole.AILLEURS;
                    case R.id.radioButton5:
                        //Spectateurs
                        return LobbyRole.SPECTATEUR;
                    case R.id.radioButton6:
                        //Combattants
                        return LobbyRole.COMBATTANT;
                }

                throw new IllegalArgumentException();
            }

            private void envoyerChangement(LobbyRole nouveauRole) {
                Commande commande = new Commande(TypeCommande.ROLE, nouveauRole.toString());
                sendCommande(commande);
            }
        });

        txtAilleurs = view.findViewById(R.id.lstAilleurs);
        txtCombattants = view.findViewById(R.id.lstAttente);
        txtSpectateurs = view.findViewById(R.id.lstSpectateur);
        txtDerniereCommande = view.findViewById(R.id.txt_derniereCommande);
        txtArbitre = view.findViewById(R.id.lstArbitre);

        subscriptions.add(client.topic(StompTopic.Subscribe.COMMANDE)
                .subscribe(
                stompMessage -> {
                    try {
                        ReponseCommande reponseCommande = JsonUtils.jsonToObject(stompMessage.getPayload(), ReponseCommande.class);
                        DonneesReponseCommande donneesReponseCommande = reponseCommande.getDonnees();
                        mainActivity.runOnUiThread(() -> txtDerniereCommande.setText("OK"));

                        switch (donneesReponseCommande.getTypeCommande()) {
                            case ROLE:
                            case JOINDRE:
                                mainActivity.runOnUiThread(() -> initLobby(donneesReponseCommande));
                                break;
                            case COMBAT:
                                mainActivity.runOnUiThread(() -> updateUser(donneesReponseCommande));
                                break;
                            case ERREUR:
                                mainActivity.runOnUiThread(() -> {
                                    txtDerniereCommande.setText(reponseCommande.getTexte());
                                });
                                break;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ));

        Commande commande = new Commande(TypeCommande.JOINDRE);
        try {
            client.send(StompTopic.Send.COMMANDE, JsonUtils.objectToJson(commande))
                    .subscribe();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        hearbeatLoop = new HearbeatLoop(client);
        new Thread(hearbeatLoop).start();

        return view;
    }

    private void updateUser(DonneesReponseCommande donneesReponseCommande) {
        try {
            if (MyLogin.compteCourant.getCourriel().equals(donneesReponseCommande.getDe().getCourriel())) {
                String[] params = donneesReponseCommande.getParametres();
                params[0] = params[0].replaceAll("\"", "");
                params[1] = params[1].replaceAll("\"", "");

                MyLogin.compteCourant.setPoints(Integer.parseInt(params[0].replace("\"", "")));
                MyLogin.compteCourant.setCredits(Integer.parseInt(params[1].replace("\"", "")));

                mainActivity.points = params[0];
                mainActivity.credits = params[1];

                mainActivity.updateHeaderView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendCommande(Commande commande) {
        new Thread(() -> {
            try {
                client.send(StompTopic.Send.COMMANDE, JsonUtils.objectToJson(commande))
                .subscribe();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initLobby(DonneesReponseCommande donneesReponseCommande) {
        try {
            if (donneesReponseCommande.getDe() != null && donneesReponseCommande.getDe().getCourriel().equals(MyLogin.compteCourant.getCourriel())) {
                monCompte = donneesReponseCommande.getDe();
            }

            SerializableLobby lobby = JsonUtils.jsonToObject(donneesReponseCommande.getParametres()[0], SerializableLobby.class);
            initListe(txtAilleurs, lobby.getAilleurs(), getResources().getString(R.string.list_Ailleurs));
            initListe(txtCombattants, lobby.getCombattants(), getResources().getString(R.string.liste_Attente));
            initListe(txtSpectateurs, lobby.getSpectateurs(), getResources().getString(R.string.liste_Spectateur));

            if (lobby.getArbitre() == null) {
                txtArbitre.setText("Aucun arbitre");
            } else {
                txtArbitre.setText("Arbitre: " + lobby.getArbitre().getAlias());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListe(TextView textView, SanitizedLobbyUser[] users, String textInitial) {
        textView.setText(textInitial);
        for (SanitizedLobbyUser user : users) {
            if (user != null) {
                textView.append(user.getAlias() + "\n");
            }
        }
    }

    private void UpdateListe(DonneesReponseCommande donnees) throws IOException {
        String[] parametres = donnees.getParametres();

        SanitizedLobbyUser de = donnees.getDe();
        if (parametres.length > 1 && parametres[1] != null && parametres[1] != "null") {
            LobbyPosition anciennePosition = JsonUtils.jsonToObject(parametres[1], LobbyPosition.class);
            switch (anciennePosition.getRole()) {
                case BLANC:
                    break;
                case ROUGE:
                    break;
                case ARBITRE:
                    break;
                case COMBATTANT:
                    removeFromList(txtCombattants, de);
                    break;
                case SPECTATEUR:
                    removeFromList(txtSpectateurs, de);
                    break;
                case AILLEURS:
                    removeFromList(txtAilleurs, de);
                    break;
            }
        }

        LobbyPosition nouvellePosition = JsonUtils.jsonToObject(parametres[0], LobbyPosition.class);

        switch (nouvellePosition.getRole()) {
            case BLANC:
                break;
            case ROUGE:
                break;
            case ARBITRE:
                break;
            case COMBATTANT:
                addToList(txtCombattants, de);
                break;
            case SPECTATEUR:
                addToList(txtSpectateurs, de);
                break;
            case AILLEURS:
                addToList(txtAilleurs, de);
                break;
        }
    }

    private void removeFromList(TextView list, SanitizedLobbyUser user) {
        String nouvelleListe = ((String)list.getText()).replace(user.getAlias() + "\n", "");
        list.setText(nouvelleListe);
    }

    private void addToList(TextView list, SanitizedLobbyUser user) {
        list.append(user.getAlias() + "\n");
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setClient(StompClient client) {
        this.client = client;
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
    public void onDestroy() {
        super.onDestroy();
        for (Disposable subscription : subscriptions) {
            subscription.dispose();
        }
        hearbeatLoop.interrupt();
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
            case R.id.cbArbitre:
                ((RadioButton)view.findViewById(R.id.radioButton4)).setChecked(false);
                ((RadioButton)view.findViewById(R.id.radioButton5)).setChecked(false);
                ((RadioButton)view.findViewById(R.id.radioButton6)).setChecked(false);
                Commande commande = new Commande(TypeCommande.ROLE, LobbyRole.ARBITRE.toString());
                sendCommande(commande);
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
        envoyerCombat("ROUGE");
    }

    /**
     * Génère un combat où le combattant de la session courante est rouge et
     * s1@dojo est blanc. Le blanc gagne. L’arbitre de ce
     * combat est v1@dojo. Aucune faute n’est commise par l’arbitre. L’autorisation et la
     * validation doivent se faire au niveau du serveur.
     */
    private void perdreMatch(){
        envoyerCombat("BLANC");
    }

    /**
     * Génère un combat où le combattant de la session courante est rouge et
     * s1@dojo est blanc. C’est un match nul. L’arbitre de ce
     * combat est v1@dojo. Aucune faute n’est commise par l’arbitre. L’autorisation et la
     * validation doivent se faire au niveau du serveur.
     */
    private void annulerMatch(){
        envoyerCombat("NUL");
    }

    /**
     * Génère un combat où le combattant de la session courante est arbitre
     * entre rouge v1@dojo et blanc s1@dojo. Le rouge gagne.
     * Aucune faute n’est commise par l’arbitre. L’autorisation et la validation doivent se faire au
     * niveau du serveur
     */
    private void arbitreSansFautes(){
        envoyerCombat("ARBITRE");
    }

    /**
     * Génère un combat où le combattant de la session courante est arbitre
     * entre rouge v1@dojo et blanc s1@dojo. Le rouge gagne,
     * cependant une faute est commise par l’arbitre. L’autorisation et la validation doivent se
     * faire au niveau du serveur.
     */
    private void arbitreAvecFautes(){
        envoyerCombat("FAUTE");
    }


    private void envoyerCombat(String resultat) {
        Commande commande = new Commande(TypeCommande.COMBAT, resultat);
        sendCommande(commande);
    }
}
