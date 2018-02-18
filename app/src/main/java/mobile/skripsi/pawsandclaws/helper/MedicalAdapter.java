package mobile.skripsi.pawsandclaws.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.model.Medical;

/**
 * Medical Adapter
 * Created by @lukmanadelt on 12/7/2017.
 */

public class MedicalAdapter extends ArrayAdapter<Medical> {
    Context context;
    private List<Medical> medicalList;
    private LayoutInflater mInflater;

    // Constructors
    public MedicalAdapter(Context context, List<Medical> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        medicalList = objects;

    }

    @Override
    public Medical getItem(int position) {
        return medicalList.get(position);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;

        if (convertView == null) {
            View view = mInflater.inflate(R.layout.list_view_medical, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Medical item = getItem(position);

        vh.tvName.setText(item != null ? item.getName() : null);

        vh.cbMedical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (item != null) item.setChecked(1);

                    vh.etRemark.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            if (item != null)
                                item.setRemark(vh.etRemark.getText().toString().trim());
                        }
                    });

                    vh.etMedicine.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            if (item != null)
                                item.setMedicine(vh.etMedicine.getText().toString().trim());
                        }
                    });
                } else {
                    if (item != null) item.setChecked(0);
                    vh.etRemark.setText("");
                    vh.etMedicine.setText("");
                }
            }
        });

        return vh.rootView;
    }

    private static class ViewHolder {
        private final LinearLayout rootView;
        private final CheckBox cbMedical;
        public final TextView tvName;
        private final EditText etRemark;
        private final EditText etMedicine;

        private ViewHolder(LinearLayout rootView, CheckBox cbMedical, TextView tvName, EditText etRemark, EditText etMedicine) {
            this.rootView = rootView;
            this.cbMedical = cbMedical;
            this.tvName = tvName;
            this.etRemark = etRemark;
            this.etMedicine = etMedicine;
        }

        public static ViewHolder create(LinearLayout rootView) {
            CheckBox cbMedical = rootView.findViewById(R.id.cbMedical);
            TextView tvName = rootView.findViewById(R.id.tvName);
            EditText etRemark = rootView.findViewById(R.id.etRemark);
            EditText etMedicine = rootView.findViewById(R.id.etMedicine);

            return new ViewHolder(rootView, cbMedical, tvName, etRemark, etMedicine);
        }
    }
}
