package cgodin.qc.ca.projet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
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

import cgodin.qc.ca.projet.MainActivity;
import cgodin.qc.ca.projet.MyLogin;
import cgodin.qc.ca.projet.R;
import cgodin.qc.ca.projet.asynctasks.RequeteAvatar;
import cgodin.qc.ca.projet.asynctasks.RequeteListe;
import cgodin.qc.ca.projet.models.Combat;
import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.models.Examen;
import cgodin.qc.ca.projet.models.Groupe;
import cgodin.qc.ca.projet.models.SanitizedUser;
import cgodin.qc.ca.projet.stomp.LobbyRole;

import static cgodin.qc.ca.projet.MainActivity.HOTE;
import static cgodin.qc.ca.projet.MyLogin.compteCourant;

public class ExamenAdapter extends RecyclerView.Adapter<ExamenAdapter.CombatViewHolder> {
    private List<Examen> examenItemList;
    private Context context;
    private List<Combat> lstCombats;
    private boolean estAncien = false;

    public ExamenAdapter(List<Examen> combatItemList, Context context, List<Combat> lstCombats) {
        this.examenItemList = combatItemList;
        this.context = context;
        this.lstCombats = lstCombats;
    }
    private int getCreditBeforeExamen(Examen examen, final int position){
        int credits = 0;
        if(position==getItemCount()-1)
            return credits;

        for (Combat comb : lstCombats)
        {
            if(examen.getEleve().getCourriel().equals(comb.getArbitre().getCourriel())
                    && comb.getTemps() < examen.getTemps()){
                credits += comb.getCreditsArbitre();
            }
        }
        credits = getCreditsLostFromExam(examen, credits) +10 - removeCreditIfAncien(examen);
        return credits;
    }
    private int getPointsBeforeExamen(Examen examen, final int position){
        int points = 0;
        if(position==getItemCount()-1 )
            return points;

        Examen lastExam = getLastExamenReussi(examen);
        for (Combat comb : lstCombats)
        {
            if(estParticipant(comb)
                && comb.getTemps() < examen.getTemps()
                    &&  comb.getTemps() > lastExam.getTemps()){
                points += estRouge(comb) ? CombatAdapter.pointsPourRouge(comb)
                        : CombatAdapter.pointsPourBlanc(comb) ;
            }
        }
        return points;
    }
    private Examen getLastExamenReussi(Examen examen){
        Examen lastExam = examen;
        for(int i = 0;i<=getItemCount()-1;i++){
            if(examenItemList.get(i).getTemps() < examen.getTemps()
                    && examenItemList.get(i).isReussi()){
                lastExam = examenItemList.get(i);
                break;
            }
        }
        return lastExam;
    }
    private int getCreditsLostFromExam(Examen examen, int credits){
        for (Examen exam: examenItemList)
        {
            if(exam.getTemps() < examen.getTemps()){
                credits -= exam.isReussi() ? 10 : 5;
            }
        }
        return credits;
    }
    private int removeCreditIfAncien(Examen examen){
        if(examen.getEleve().getRole().getId()==2
                && examen.getTemps() < examen.getEleve().getAncienDepuis()
                && !estAncien){
            estAncien = true;
            return 10;
        }
        return 0;
    }
    private boolean estRouge(Combat c){
        String courriel = examenItemList.get(0).getEleve().getCourriel();

        return courriel.equals(c.getRouge().getCourriel());
    }
    private boolean estParticipant(Combat c){
        String courriel = examenItemList.get(0).getEleve().getCourriel();

        return courriel.equals(c.getBlanc().getCourriel())
                    || courriel.equals(c.getRouge().getCourriel());
    }
    @Override
    public CombatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View combatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_examen, parent, false);
        CombatViewHolder combatViewH = new CombatViewHolder(combatView);
        return combatViewH;
    }

    @Override
    public void onBindViewHolder(CombatViewHolder holder, final int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(examenItemList.get(position).getTemps());
        int credit = getCreditBeforeExamen(examenItemList.get(position), position);
        int points = getPointsBeforeExamen(examenItemList.get(position), position);

        holder.cardView.setCardBackgroundColor(examenItemList.get(position).isReussi()? Color.parseColor("#ccffb3"):Color.parseColor("#ffb3b3"));

        holder.txtDate.setText(sdf.format(date));
        holder.txtEtat.setText(examenItemList.get(position).isReussi() ? "Passé":"Échec");

        try {
            holder.txtCeinture.setText(examenItemList.get(position).getCeinture().getGroupe());
        }
        catch (NullPointerException e){
            holder.txtCeinture.setText("N/A");
        }
        holder.txtCredits.setText(String.valueOf(credit)+ " credits");
        holder.txtPoints.setText(String.valueOf(points)+ " points");

        holder.txtProf.setText(examenItemList.get(position).getProfesseur().getAlias());
        new RequeteAvatar(holder.imgProf).execute(examenItemList.get(position).getProfesseur().getAvatarId());
    }

    @Override
    public int getItemCount() {
        return examenItemList.size();
    }
    public class CombatViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txtDate;
        TextView txtEtat;

        TextView txtCeinture;
        TextView txtPoints;
        TextView txtCredits;

        TextView txtProf;
        ImageView imgProf;
        public CombatViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.idCardView);

            txtDate=view.findViewById(R.id.date);
            txtEtat = view.findViewById(R.id.etat);

            txtCeinture=view.findViewById(R.id.id_Ceinture);
            txtPoints=view.findViewById(R.id.id_points);
            txtCredits=view.findViewById(R.id.id_credits);

            imgProf=view.findViewById(R.id.img_Prof);
            txtProf = view.findViewById(R.id.id_Prof);
        }
    }
}
