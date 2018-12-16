package cgodin.qc.ca.projet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Objects;

import cgodin.qc.ca.projet.MainActivity;
import cgodin.qc.ca.projet.R;
import cgodin.qc.ca.projet.asynctasks.RequeteAvatar;
import cgodin.qc.ca.projet.models.SanitizedUser;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.ViewHolder> {
    private SanitizedUser[] comptes;
    private Context context;

    public CompteAdapter(SanitizedUser[] comptes) {
        this.comptes = Arrays.stream(comptes).filter(Objects::nonNull).toArray(SanitizedUser[]::new);
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

        if (comptes[position].getAvatarId() != null) {
            new RequeteAvatar(holder.imAvatar).execute(MainActivity.REST_URL + "/api/avatars/" + comptes[position].getAvatarId());
        }
    }

    @Override
    public int getItemCount() {
        return comptes.length;
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
    }
}
