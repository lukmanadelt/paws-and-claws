package mobile.skripsi.pawsandclaws.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.activities.CustomerDetailActivity;
import mobile.skripsi.pawsandclaws.activities.PetDetailActivity;
import mobile.skripsi.pawsandclaws.activities.ProfileActivity;
import mobile.skripsi.pawsandclaws.model.Pet;

/**
 * Pet Adapter
 * Created by @lukmanadelt on 11/28/2017.
 */

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {
    private List<Pet> pets;
    private Context mCtx;

    public PetAdapter(List<Pet> pets, Context mCtx) {
        this.pets = pets;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_pet, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PetAdapter.ViewHolder holder, int position) {
        final Pet pet = pets.get(position);
        holder.tvName.setText(pet.getName());

        switch (pet.getPetCategoryId()) {
            case 1:
                holder.tvName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pet, 0, 0, 0);
                break;
            case 2:
                holder.tvName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cat, 0, 0, 0);
                break;
        }

        holder.tvName.setCompoundDrawablePadding(10);

        holder.tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx, holder.tvAction);
                // Inflating menu from xml resource
                popup.inflate(R.menu.action_pet);
                // Adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent actionPet = null;

                        switch (item.getItemId()) {
                            case R.id.iViewDetail:
                                actionPet = new Intent(mCtx, PetDetailActivity.class);
                                break;
                            case R.id.iExaminationHistory:
//                                actionPet = new Intent(mCtx, PetExaminationHistoryActivity.class);
                                break;
                        }

                        actionPet.putExtra("pet_id", pet.getId());
                        actionPet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mCtx.startActivity(actionPet);

                        return true;
                    }
                });

                // Displaying the popup
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAction;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAction = itemView.findViewById(R.id.tvAction);
        }
    }
}
