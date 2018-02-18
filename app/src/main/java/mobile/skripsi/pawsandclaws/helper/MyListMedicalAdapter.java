package mobile.skripsi.pawsandclaws.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mobile.skripsi.pawsandclaws.R;

/**
 * My List Medical Adapter
 * Created by @lukmanadelt on 12/8/2017.
 */

public class MyListMedicalAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<HeaderInfo> deptList;

    public MyListMedicalAdapter(Context context, ArrayList<HeaderInfo> deptList) {
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

        DetailInfo detailInfo = (DetailInfo) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (infalInflater != null) view = infalInflater.inflate(R.layout.child_row_medical, parent, false);
        }

        if (view != null) {
            TextView childItemRemark = view.findViewById(R.id.childItemRemark);
            childItemRemark.setText(context.getString(R.string.remark_detail, detailInfo.getRemark()));
            TextView childItemMedicine = view.findViewById(R.id.childItemMedicine);
            childItemMedicine.setText(context.getString(R.string.medicine_detail, detailInfo.getMedicine()));
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

            if (inf != null) view = inf.inflate(R.layout.group_heading_vaccine, parent, false);
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
