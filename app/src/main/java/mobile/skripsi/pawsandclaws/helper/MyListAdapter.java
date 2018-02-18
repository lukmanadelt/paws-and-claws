package mobile.skripsi.pawsandclaws.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tooltip.Tooltip;

import java.util.ArrayList;

import mobile.skripsi.pawsandclaws.R;

/**
 * My List Adapter
 * Created by @lukmanadelt on 12/2/2017.
 */

public class MyListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<HeaderInfo> deptList;

    public MyListAdapter(Context context, ArrayList<HeaderInfo> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<DetailInfo> productList = deptList.get(groupPosition).getProductList();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        final DetailInfo detailInfo = (DetailInfo) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (infalInflater != null)
                view = infalInflater.inflate(R.layout.child_row, parent, false);
        }

        if (view != null) {
            CheckBox cbVaccine = view.findViewById(R.id.cbVaccine);
            TextView childItem = view.findViewById(R.id.childItem);
            TextView tvPeriod = view.findViewById(R.id.tvPeriod);
            final Button bTooltip = view.findViewById(R.id.bTooltip);

            if (detailInfo.getCompleted() == 1) {
                cbVaccine.setChecked(true);
            }

            childItem.setText(detailInfo.getVaccine().trim());
            tvPeriod.setText(context.getString(R.string.period, String.valueOf(detailInfo.getPeriod())));

            bTooltip.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new Tooltip.Builder(bTooltip)
                            .setText(detailInfo.getDescription().trim())
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                }
            });
        }

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<DetailInfo> productList = deptList.get(groupPosition).getProductList();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        HeaderInfo headerInfo = (HeaderInfo) getGroup(groupPosition);

        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (inf != null) view = inf.inflate(R.layout.group_heading, parent, false);
        }

        if (view != null) {
            TextView heading = view.findViewById(R.id.heading);
            heading.setText(headerInfo.getName().trim());
        }

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
