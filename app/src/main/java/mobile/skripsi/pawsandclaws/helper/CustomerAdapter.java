package mobile.skripsi.pawsandclaws.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.activities.DoctorPetListActivity;
import mobile.skripsi.pawsandclaws.model.User;

/**
 * Customer Adapter
 * Created by @lukmanadelt on 12/3/2017.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private List<User> customers;
    private Context mCtx;

    public CustomerAdapter(List<User> customers, Context mCtx) {
        this.customers = customers;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_customer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CustomerAdapter.ViewHolder holder, int position) {
        final User customer = customers.get(position);

        holder.tvFullname.setText(customer.getFullname());
        holder.tvFullname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.customer, 0, 0, 0);
        holder.tvFullname.setCompoundDrawablePadding(10);
        holder.tvFullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listPet = new Intent(mCtx, DoctorPetListActivity.class);

                listPet.putExtra("customer_id", customer.getId());
                listPet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(listPet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFullname;

        private ViewHolder(View itemView) {
            super(itemView);

            tvFullname = itemView.findViewById(R.id.tvFullname);
        }
    }
}
