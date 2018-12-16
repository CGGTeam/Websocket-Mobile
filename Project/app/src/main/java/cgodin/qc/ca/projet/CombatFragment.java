package cgodin.qc.ca.projet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cgodin.qc.ca.projet.adapter.CompteAdapter;
import cgodin.qc.ca.projet.asynctasks.JsonUtils;
import cgodin.qc.ca.projet.asynctasks.RequeteInfoCompte;
import cgodin.qc.ca.projet.models.Attack;
import cgodin.qc.ca.projet.models.Combat;
import cgodin.qc.ca.projet.models.SanitizedUser;
import cgodin.qc.ca.projet.stomp.Commande;
import cgodin.qc.ca.projet.stomp.DonneesReponseCommande;
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
    private RadioGroup group;
    private TextView txtDerniereCommande;
    private MainActivity mainActivity;
    private SanitizedLobbyUser monCompte;

    private RecyclerView rvArbitre;
    private RecyclerView rvBlanc;
    private RecyclerView rvRouge;
    private RecyclerView rvAilleurs;
    private RecyclerView rvSpectateurs;
    private RecyclerView rvCombattants;

    private CompteAdapter adArbitre;
    private CompteAdapter adBlanc;
    private CompteAdapter adRouge;
    private CompteAdapter adAilleurs;
    private CompteAdapter adSpectateurs;
    private CompteAdapter adCombattants;

    private List<Disposable> subscriptions = new ArrayList<>();
    private ImageView imvBlanc;
    private ImageView imvRouge;
    private ImageView imvBlancChoisi;
    private ImageView imvRougeChoisi;

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

        imvBlanc = view.findViewById(R.id.imChoixGauche);
        imvRouge = view.findViewById(R.id.imChoixDroite);
        imvBlancChoisi = view.findViewById(R.id.imWinGauche);
        imvRougeChoisi = view.findViewById(R.id.imWinDroite);

        mainActivity = (MainActivity) getContext();

        txtDerniereCommande =  view.findViewById(R.id.txt_derniereCommande);

        rvAilleurs = view.findViewById(R.id.rcAilleurs);
        rvArbitre = view.findViewById(R.id.rcArbitre);
        rvBlanc = view.findViewById(R.id.rcBlanc);
        rvRouge = view.findViewById(R.id.rcRouge);
        rvCombattants = view.findViewById(R.id.rcCombattant);
        rvSpectateurs = view.findViewById(R.id.rcSpectateur);

        view.findViewById(R.id.cbArbitre).setOnClickListener(this);

        ((RadioButton)view.findViewById(R.id.radioButton5)).setChecked(true);

        group = view.findViewById(R.id.radioGroup);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int resId) {
                RadioButton checkedRadioButton = group.findViewById(resId);
                if (checkedRadioButton == null) return;
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

        subscriptions.add(client.topic(StompTopic.Subscribe.COMMANDE)
                .subscribe(
                stompMessage -> {
                    try {
                        ReponseCommande reponseCommande = JsonUtils.jsonToObject(stompMessage.getPayload(), ReponseCommande.class);
                        DonneesReponseCommande donneesReponseCommande = reponseCommande.getDonnees();
                        if (donneesReponseCommande == null) return;
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
                            case SIGNALER:
                                mainActivity.runOnUiThread(() ->afficherChoixArbitre(donneesReponseCommande));
                                break;
                            case ATTAQUER:
                                mainActivity.runOnUiThread(() -> afficherAttaques(donneesReponseCommande));
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

        return view;
    }

    private void afficherChoixArbitre(DonneesReponseCommande donneesReponseCommande) {
        LobbyRole choixArbitre = LobbyRole.valueOf(donneesReponseCommande.getParametres()[0].replace("\"", ""));

        if (choixArbitre == LobbyRole.BLANC) {
            imvRougeChoisi.setVisibility(View.VISIBLE);
            imvRougeChoisi.setImageResource(R.drawable.flag_red);
        } else if (choixArbitre == LobbyRole.ROUGE) {
            imvBlancChoisi.setVisibility(View.VISIBLE);
            imvBlancChoisi.setImageResource(R.drawable.flag_red);
        } else {
            imvRougeChoisi.setVisibility(View.VISIBLE);
            imvBlancChoisi.setVisibility(View.VISIBLE);
            imvRougeChoisi.setImageResource(R.drawable.flag_red);
            imvBlancChoisi.setImageResource(R.drawable.flag_red);
        }
    }

    private void afficherAttaques(DonneesReponseCommande donneesReponseCommande) {
        Attack attaqueRouge = Attack.valueOf(donneesReponseCommande.getParametres()[0].replace("\"", ""));
        Attack attaqueBlanc = Attack.valueOf(donneesReponseCommande.getParametres()[1].replace("\"", ""));

        imvBlanc.setVisibility(View.VISIBLE);
        imvRouge.setVisibility(View.VISIBLE);

        int idImageBlanc = getImagePourAttaque(attaqueBlanc);
        int idImageRouge = getImagePourAttaque(attaqueRouge);

        imvBlanc.setImageResource(idImageBlanc);
        imvRouge.setImageResource(idImageRouge);
    }

    private int getImagePourAttaque(Attack attack) {
        switch (attack) {
            case CISEAUX:
                return R.drawable.ciseaux;
            case ROCHE:
                return R.drawable.roche;
            case PAPIER:
                return R.drawable.papier;
        }
        return 0;
    }

    private void updateUser(DonneesReponseCommande donneesReponseCommande) {
        imvBlanc.setVisibility(View.GONE);
        imvRouge.setVisibility(View.GONE);
        imvBlancChoisi.setVisibility(View.GONE);
        imvRougeChoisi.setVisibility(View.GONE);
        try {
            String myEmail = MyLogin.compteCourant.getCourriel();
            Combat combat = JsonUtils.jsonToObject(donneesReponseCommande.getParametres()[0], Combat.class);

            if (myEmail.equals(combat.getBlanc().getCourriel()) ||
                    myEmail.equals(combat.getRouge().getCourriel()) ||
                    myEmail.equals(combat.getArbitre().getCourriel())) {

                new RequeteInfoCompte(mainActivity).execute(MainActivity.REST_URL + "/api/monCompte");
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
            initListe(rvAilleurs, lobby.getAilleurs());
            initListe(rvCombattants, lobby.getCombattants());
            initListe(rvSpectateurs, lobby.getSpectateurs());
            initListe(rvArbitre, lobby.getArbitre());
            initListe(rvRouge, lobby.getRouge());
            initListe(rvBlanc, lobby.getBlanc());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListe(RecyclerView recyclerView, SanitizedUser... users) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CompteAdapter(users));
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cbArbitre:
                ((RadioGroup)view.findViewById(R.id.radioGroup)).clearCheck();
                Commande commande = new Commande(TypeCommande.ROLE, LobbyRole.ARBITRE.toString());
                sendCommande(commande);
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
