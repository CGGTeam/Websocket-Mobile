package cgodin.qc.ca.projet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cgodin.qc.ca.projet.MyLogin;
import cgodin.qc.ca.projet.R;
import cgodin.qc.ca.projet.asynctasks.RequeteAvatar;
import cgodin.qc.ca.projet.models.Combat;

public class CombatAdapter extends RecyclerView.Adapter<CombatAdapter.CombatViewHolder>{
    private List<Combat> combatItemList;
    Context context;

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
        //TODO : implémenter comme du monde
        String strUrl;

        String points = String.valueOf(combatItemList.get(position).getPointsRouge());
        String credits = String.valueOf(combatItemList.get(position).getCreditsArbitre());

        holder.txtDate.setText(sdf.format(new Date(combatItemList.get(position).getTemps() * 1000)));
        holder.txtPoints.setText(context.getString(R.string.Points, points));

        strUrl = MyLogin.path+"/api/avatars/"+combatItemList.get(position).getRouge().getCourriel();
        new RequeteAvatar(holder.imgCombattantAutre).execute(strUrl);
        holder.txtCombattantAutre.setText(combatItemList.get(position).getRouge().getAlias());


        strUrl = MyLogin.path+"/api/avatars/"+combatItemList.get(position).getBlanc().getCourriel();
        new RequeteAvatar(holder.imgCombattant).execute(strUrl);
        holder.txtCombattant.setText(combatItemList.get(position).getBlanc().getAlias());

        strUrl = MyLogin.path+"/api/avatars/"+combatItemList.get(position).getArbitre().getCourriel();
        new RequeteAvatar(holder.imgAbritre).execute(strUrl);
        holder.txtArbitre.setText(combatItemList.get(position).getArbitre().getAlias());
        holder.txtCredits.setText(context.getString(R.string.Credits, credits));
    }

    @Override
    public int getItemCount() {
        return combatItemList.size();
    }

    public class CombatViewHolder extends RecyclerView.ViewHolder {
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
