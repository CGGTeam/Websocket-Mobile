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
import cgodin.qc.ca.projet.models.Examen;
import cgodin.qc.ca.projet.models.Groupe;
import cgodin.qc.ca.projet.stomp.LobbyRole;

public class ExamenAdapter extends RecyclerView.Adapter<ExamenAdapter.CombatViewHolder>{
    private List<Examen> examenItemList;
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

    public ExamenAdapter(List<Examen> combatItemList, Context context) {
        this.examenItemList = combatItemList;
        this.context = context;
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
        holder.cardView.setBackgroundColor(examenItemList.get(position).isReussi()? Color.parseColor("#ccffb3"):Color.parseColor("#ffb3b3"));

        holder.txtDate.setText(sdf.format(new Date(examenItemList.get(position).getTemps())));
        holder.txtEtat.setText(examenItemList.get(position).isReussi() ? "Passé":"Échec");

        holder.txtCeinture.setText(examenItemList.get(position).getCeinture().getGroupe());

        holder.txtProf.setText(examenItemList.get(position).getProfesseur().getAlias());
        String strUrl = MyLogin.path+"/api/avatars/"+examenItemList.get(position).getProfesseur().getAvatarId();
        new RequeteAvatar(holder.imgProf).execute(strUrl);
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

        TextView txtProf;
        ImageView imgProf;
        public CombatViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.idCardView);

            txtDate=view.findViewById(R.id.date);
            txtEtat = view.findViewById(R.id.etat);

            txtCeinture=view.findViewById(R.id.id_Ceinture);

            imgProf=view.findViewById(R.id.img_Prof);
            txtProf = view.findViewById(R.id.id_Prof);
        }
    }
}
