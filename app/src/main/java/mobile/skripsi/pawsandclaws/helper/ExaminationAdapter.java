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
import mobile.skripsi.pawsandclaws.model.Examination;

/**
 * Examination Adapter
 * Created by @lukmanadelt on 12/8/2017.
 */

public class ExaminationAdapter extends ArrayAdapter<Examination> {
    private List<Examination> examinationList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public ExaminationAdapter(Context context, List<Examination> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        examinationList = objects;
    }

    @Override
    public Examination getItem(int position) {
        return examinationList.get(position);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;

        if (convertView == null) {
            View view = mInflater.inflate(R.layout.list_view_examination, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Examination item = getItem(position);

        if (item != null) {
            vh.tvGivenDate.setText(item.getGivenDate());
        }

        return vh.rootView;
    }

    private static class ViewHolder {
        private final RelativeLayout rootView;
        private final TextView tvGivenDate;

        private ViewHolder(RelativeLayout rootView, TextView tvGivenDate) {
            this.rootView = rootView;
            this.tvGivenDate = tvGivenDate;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView tvGivenDate = rootView.findViewById(R.id.tvGivenDate);

            return new ViewHolder(rootView, tvGivenDate);
        }
    }
}
