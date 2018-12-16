package cgodin.qc.ca.projet.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cgodin.qc.ca.projet.MainActivity;
import cgodin.qc.ca.projet.MyLogin;
import cgodin.qc.ca.projet.R;
import cgodin.qc.ca.projet.asynctasks.RequeteAvatar;
import cgodin.qc.ca.projet.models.SanitizedUser;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.ViewHolder> {
    private SanitizedUser[] comptes;
    private Context context;

    public CompteAdapter(SanitizedUser[] comptes) {
        setItems(comptes);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View compteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_compte, parent, false);
        ViewHolder combatViewH = new ViewHolder(compteView);
        return combatViewH;
    }

    @Override
    public void onBindViewHolder(CompteAdapter.ViewHolder holder, int position) {
        holder.setViewData(comptes[position]);

        holder.setIsCurrentUser(comptes[position].getCourriel().equals(MyLogin.compteCourant.getCourriel()));

        if (comptes[position].getAvatarId() != null) {
            new RequeteAvatar(holder.imAvatar).execute(comptes[position].getAvatarId());
        }
    }

    @Override
    public int getItemCount() {
        return comptes.length;
    }

    public void setItems(SanitizedUser[] users) {
        this.comptes = Arrays.stream(users).filter(Objects::nonNull).toArray(SanitizedUser[]::new);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imAvatar;

        private TextView nom;
        private TextView role;
        private TextView groupe;
        private TextView points;
        private TextView credits;

        public ViewHolder(View view) {
            super(view);
            imAvatar = view.findViewById(R.id.imAvatar);

            nom = view.findViewById(R.id.tvNom);
            role = view.findViewById(R.id.tvRole);
            groupe = view.findViewById(R.id.tvGroupe);
        }

        public void setViewData(SanitizedUser compte) {
            nom.setText(compte.getAlias());
            role.setText(compte.getRole().getRole().substring(0, 1));
            groupe.setText(compte.getGroupe().getGroupe().substring(0, 1));
        }

        public void setIsCurrentUser(boolean equals) {
            if (equals) {
                nom.setTextColor(ContextCompat.getColor(context, R.color.colorAccountHiglight));
            } else {
                nom.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
        }
    }
}
