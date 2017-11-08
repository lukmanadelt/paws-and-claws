package mobile.skripsi.pawsandclaws.helper;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.model.User;

/**
 * User Adapter
 * Created by @lukmanadelt on 11/8/2017.
 */

public class UserAdapter extends ArrayAdapter<User> {
    List<User> userList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public UserAdapter(Context context, List<User> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        userList = objects;
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.list_view_user, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        User item = getItem(position);

        vh.tvFullname.setText(item.getFullname());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView tvFullname;

        private ViewHolder(RelativeLayout rootView, TextView tvFullname) {
            this.rootView = rootView;
            this.tvFullname = tvFullname;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView tvFullname = (TextView) rootView.findViewById(R.id.tvFullname);

            return new ViewHolder(rootView, tvFullname);
        }
    }
}
