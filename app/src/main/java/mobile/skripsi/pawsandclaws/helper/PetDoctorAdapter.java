package mobile.skripsi.pawsandclaws.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.activities.ExaminationHistoryActivity;
import mobile.skripsi.pawsandclaws.activities.ExaminationMedicalActivity;
import mobile.skripsi.pawsandclaws.activities.ExaminationNextActivity;
import mobile.skripsi.pawsandclaws.activities.ExaminationVaccineActivity;
import mobile.skripsi.pawsandclaws.model.Pet;

/**
 * Pet Doctor Adapter
 * Created by @lukmanadelt on 12/7/2017.
 */

public class PetDoctorAdapter extends RecyclerView.Adapter<PetDoctorAdapter.ViewHolder> {
    private List<Pet> pets;
    private Context mCtx;

    public PetDoctorAdapter(List<Pet> pets, Context mCtx) {
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
    public void onBindViewHolder(final PetDoctorAdapter.ViewHolder holder, int position) {
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
                popup.inflate(R.menu.action_pet_doctor);
                // Adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent actionPet = null;

                        switch (item.getItemId()) {
                            case R.id.iExaminationHistory:
                                actionPet = new Intent(mCtx, ExaminationHistoryActivity.class);
                                break;
                            case R.id.iNextVaccinationSchedule:
                                actionPet = new Intent(mCtx, ExaminationNextActivity.class);
                                break;
                            case R.id.iVaccineExamination:
                                actionPet = new Intent(mCtx, ExaminationVaccineActivity.class);
                                break;
                            case R.id.iMedicalExamination:
                                actionPet = new Intent(mCtx, ExaminationMedicalActivity.class);
                                break;
                        }

                        if (actionPet != null) {
                            actionPet.putExtra("pet_id", pet.getId());
                            actionPet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mCtx.startActivity(actionPet);
                        }

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
        private TextView tvName, tvAction;

        private ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAction = itemView.findViewById(R.id.tvAction);
        }
    }
}