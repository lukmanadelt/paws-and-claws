package mobile.skripsi.pawsandclaws.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.model.Pet;

/**
 * Pet Customer Adapter
 * Created by @lukmanadelt on 12/3/2017.
 */

public class PetCustomerAdapter extends ArrayAdapter<Pet> {
    Context context;
    private List<Pet> petList;
    private LayoutInflater mInflater;

    // Constructors
    public PetCustomerAdapter(Context context, List<Pet> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        petList = objects;
    }

    @Override
    public Pet getItem(int position) {
        return petList.get(position);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;

        if (convertView == null) {
            View view = mInflater.inflate(R.layout.list_view_pet, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Pet item = getItem(position);

        vh.tvName.setText(item != null ? item.getName() : null);

        return vh.rootView;
    }

    private static class ViewHolder {
        private final RelativeLayout rootView;
        public final TextView tvName;

        private ViewHolder(RelativeLayout rootView, TextView tvName) {
            this.rootView = rootView;
            this.tvName = tvName;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView tvName = rootView.findViewById(R.id.tvName);

            return new ViewHolder(rootView, tvName);
        }
    }
}
