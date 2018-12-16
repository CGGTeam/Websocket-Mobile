package cgodin.qc.ca.projet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cgodin.qc.ca.projet.MyLogin;
import cgodin.qc.ca.projet.R;
import cgodin.qc.ca.projet.asynctasks.RequeteAvatar;
import cgodin.qc.ca.projet.models.Combat;
import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.models.Groupe;
import cgodin.qc.ca.projet.stomp.LobbyRole;

public class CombatAdapter extends RecyclerView.Adapter<CombatAdapter.CombatViewHolder>{
    private List<Combat> combatItemList;
    Context context;
    private static Map<Integer, Integer> recompensesSelonDelta;
    static {
        recompensesSelonDelta = new HashMap<>();

        recompensesSelonDelta.put(7, 50);
        recompensesSelonDelta.put(6, 50);
        recompensesSelonDelta.put(5, 30);
        recompensesSelonDelta.put(4, 25);
        recompensesSelonDelta.put(3, 20);
        recompensesSelonDelta.put(2, 15);
        recompensesSelonDelta.put(1, 12);
        recompensesSelonDelta.put(0, 10);
        recompensesSelonDelta.put(-1, 9);
        recompensesSelonDelta.put(-2, 7);
        recompensesSelonDelta.put(-3, 5);
        recompensesSelonDelta.put(-4, 3);
        recompensesSelonDelta.put(-5, 2);
        recompensesSelonDelta.put(-6, 1);
        recompensesSelonDelta.put(-7, 1);
    }

    private static Map<Integer, String> couleurSelonCeinture;
    static {
        couleurSelonCeinture = new HashMap<>();

        couleurSelonCeinture.put(8, "#001a00");
        couleurSelonCeinture.put(7, "#001a00");
        couleurSelonCeinture.put(6, "#996633");
        couleurSelonCeinture.put(5, "#0000ff");
        couleurSelonCeinture.put(4, "#33cc33");
        couleurSelonCeinture.put(3, "#ff9900");
        couleurSelonCeinture.put(2, "#ffff4d");
        couleurSelonCeinture.put(1, "#ffffff");

    }

    public CombatAdapter(List<Combat> combatItemList, Context context) {
        this.combatItemList = combatItemList;
        this.context = context;
    }

    @Override
    public CombatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View combatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_combat, parent, false);
        CombatViewHolder combatViewH = new CombatViewHolder(combatView);
        return combatViewH;
    }

    @Override
    public void onBindViewHolder(CombatViewHolder holder, final int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String strUrl;
        Compte compteCourant = MyLogin.compteCourant;

        boolean estRouge = compteCourant.getCourriel().equals(combatItemList.get(position).getRouge().getCourriel());
        boolean estBlanc = compteCourant.getCourriel().equals(combatItemList.get(position).getBlanc().getCourriel());

        String points = estRouge ? String.valueOf(combatItemList.get(position).getPointsRouge())
                : estBlanc ? String.valueOf(combatItemList.get(position).getPointsBlanc()) : "5";
        String credits = String.valueOf(combatItemList.get(position).getCreditsArbitre());

        if(Integer.parseInt(points) == 10 ) holder.cardView.setCardBackgroundColor(Color.parseColor("#ccffb3"));
        else if(Integer.parseInt(points) == 0 ) holder.cardView.setCardBackgroundColor(Color.parseColor("#ffb3b3"));
        else if(Integer.parseInt(points) == 5 ) holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffb3"));

        int intPoints =estRouge? pointsPourMatch(combatItemList.get(position), LobbyRole.ROUGE)
                : estBlanc ? pointsPourMatch(combatItemList.get(position), LobbyRole.BLANC) : 0;

        points = String.valueOf(intPoints);

        //Date et Points
        holder.txtDate.setText(sdf.format(new Date(combatItemList.get(position).getTemps())));
        holder.txtPoints.setText(context.getString(R.string.Points, points));

        //Rouge
        new RequeteAvatar(holder.imgCombattantAutre).execute(combatItemList.get(position).getRouge().getAvatarId());
        holder.txtCombattantAutre.setText(combatItemList.get(position).getRouge().getAlias());
        holder.txtCombattantAutre.setTextColor(Color.parseColor(couleurSelonCeinture.get(combatItemList.get(position).getCeintureRouge().getId())));

        //Blanc
        new RequeteAvatar(holder.imgCombattant).execute(combatItemList.get(position).getBlanc().getAvatarId());
        holder.txtCombattant.setText(combatItemList.get(position).getBlanc().getAlias());
        holder.txtCombattant.setTextColor(Color.parseColor(couleurSelonCeinture.get(combatItemList.get(position).getCeintureBlanc().getId())));

        //Arbitre
        new RequeteAvatar(holder.imgAbritre).execute(combatItemList.get(position).getArbitre().getAvatarId());
        holder.txtArbitre.setText(combatItemList.get(position).getArbitre().getAlias());

        //Credits
        holder.txtCredits.setText(context.getString(R.string.Credits, credits));
    }
    public static int pointsPourMatch(Combat combat, LobbyRole role) {
        int pointsRouge;
        int pointsBlanc;

        if (combat.getPointsRouge() == 10 && combat.getPointsBlanc() == 10) {
            pointsRouge = calculerPointsGagnant(combat.getCeintureRouge(), combat.getCeintureBlanc()) / 2;
            pointsBlanc = calculerPointsGagnant(combat.getCeintureBlanc(), combat.getCeintureRouge()) / 2;
        } else if (combat.getPointsRouge() == 5 && combat.getPointsBlanc() == 5) {
            pointsRouge = calculerPointsGagnant(combat.getCeintureRouge(), combat.getCeintureBlanc()) / 2;
            pointsBlanc = calculerPointsGagnant(combat.getCeintureBlanc(), combat.getCeintureRouge()) / 2;
        } else if (combat.getPointsRouge() == 0 && combat.getPointsBlanc() == 0) {
            pointsRouge = 0;
            pointsBlanc = 0;
        } else if (combat.getPointsRouge() == 10) {
            pointsRouge = calculerPointsGagnant(combat.getCeintureRouge(), combat.getCeintureBlanc());
            pointsBlanc = 0;
        } else if (combat.getPointsBlanc() == 10) {
            pointsRouge = 0;
            pointsBlanc = calculerPointsGagnant(combat.getCeintureBlanc(), combat.getCeintureRouge());
        } else {
            throw new IllegalArgumentException("échec calcul points");
        }

        return role == LobbyRole.BLANC ? pointsBlanc : pointsRouge;
    }
    public static int calculerPointsGagnant(Groupe gagnant, Groupe perdant) {
        int delta = perdant.getId() - gagnant.getId();
        return recompensesSelonDelta.get(delta);
    }

    @Override
    public int getItemCount() {
        return combatItemList.size();
    }

    public class CombatViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txtDate;
        TextView txtPoints;

        ImageView imgCombattant;
        TextView txtCombattant;

        ImageView imgCombattantAutre;
        TextView txtCombattantAutre;

        ImageView imgAbritre;
        TextView txtArbitre;
        TextView txtCredits;
        public CombatViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.idCardView);

            txtDate=view.findViewById(R.id.date);
            txtPoints = view.findViewById(R.id.point);

            imgCombattant=view.findViewById(R.id.img_Combattant);
            txtCombattant = view.findViewById(R.id.id_Combattant);

            imgCombattantAutre=view.findViewById(R.id.img_Combattant_Autre);
            txtCombattantAutre = view.findViewById(R.id.id_Combattant_Autre);

            imgAbritre=view.findViewById(R.id.img_Arbitre);
            txtArbitre = view.findViewById(R.id.id_Arbitre);
            txtCredits = view.findViewById(R.id.credit);
        }
    }
}
