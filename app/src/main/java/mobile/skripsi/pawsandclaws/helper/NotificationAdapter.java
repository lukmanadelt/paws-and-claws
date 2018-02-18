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
import mobile.skripsi.pawsandclaws.model.Notification;

/**
 * Notification Adapter
 * Created by @lukmanadelt on 12/12/2017.
 */

public class NotificationAdapter extends ArrayAdapter<Notification> {
    private List<Notification> notificationList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public NotificationAdapter(Context context, List<Notification> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        notificationList = objects;
    }

    @Override
    public Notification getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;

        if (convertView == null) {
            View view = mInflater.inflate(R.layout.list_view_notification, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Notification item = getItem(position);

        if (item != null) {
            vh.tvDescription.setText(item.getDescription());
        }

        return vh.rootView;
    }

    private static class ViewHolder {
        private final RelativeLayout rootView;
        private final TextView tvDescription;

        private ViewHolder(RelativeLayout rootView, TextView tvDescription) {
            this.rootView = rootView;
            this.tvDescription = tvDescription;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView tvDescription = rootView.findViewById(R.id.tvDescription);

            return new ViewHolder(rootView, tvDescription);
        }
    }
}
