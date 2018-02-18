package mobile.skripsi.pawsandclaws.helper;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.model.Vaccine;

/**
 * Vaccine Adapter
 * Created by @lukmanadelt on 12/7/2017.
 */

public class VaccineAdapter extends ArrayAdapter<Vaccine> {
    Context context;
    private List<Vaccine> vaccineList;
    private LayoutInflater mInflater;

    // Constructors
    public VaccineAdapter(Context context, List<Vaccine> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        vaccineList = objects;

        // Initial Date Picker element
        DecimalFormat decimalFormat = new DecimalFormat("00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
    }

    @Override
    public Vaccine getItem(int position) {
        return vaccineList.get(position);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;

        if (convertView == null) {
            View view = mInflater.inflate(R.layout.list_view_vaccine, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Vaccine item = getItem(position);

        vh.tvName.setText(item != null ? item.getName() : null);

        if (item != null && item.getCurrent() == 1) {
            vh.etNextDate.setVisibility(View.GONE);
            vh.etRemark.setVisibility(View.VISIBLE);
        } else {
            vh.etNextDate.setVisibility(View.VISIBLE);
            vh.etRemark.setVisibility(View.GONE);
        }

        vh.cbVaccine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (item != null) item.setChecked(1);

                    vh.etNextDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Get Current Date
                            final Calendar c = Calendar.getInstance();
                            int mYear = c.get(Calendar.YEAR);
                            int mMonth = c.get(Calendar.MONTH);
                            int mDay = c.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {
                                            vh.etNextDate.setText(context.getString(R.string.date, year, (monthOfYear + 1), dayOfMonth));
                                        }
                                    }, mYear, mMonth, mDay);
                            datePickerDialog.show();
                        }
                    });

                    vh.etNextDate.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            if (item != null)
                                item.setNextDate(vh.etNextDate.getText().toString().trim());
                        }
                    });

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
                } else {
                    if (item != null) item.setChecked(0);
                    vh.etNextDate.setText("");
                    vh.etRemark.setText("");
                }
            }
        });

        return vh.rootView;
    }

    private static class ViewHolder {
        private final LinearLayout rootView;
        private final CheckBox cbVaccine;
        public final TextView tvName;
        private final EditText etNextDate, etRemark;

        private ViewHolder(LinearLayout rootView, CheckBox cbVaccine, TextView tvName, EditText etNextDate, EditText etRemark) {
            this.rootView = rootView;
            this.cbVaccine = cbVaccine;
            this.tvName = tvName;
            this.etNextDate = etNextDate;
            this.etRemark = etRemark;
        }

        public static ViewHolder create(LinearLayout rootView) {
            CheckBox cbVaccine = rootView.findViewById(R.id.cbVaccine);
            TextView tvName = rootView.findViewById(R.id.tvName);
            EditText etNextDate = rootView.findViewById(R.id.etNextDate);
            EditText etRemark = rootView.findViewById(R.id.etRemark);

            return new ViewHolder(rootView, cbVaccine, tvName, etNextDate, etRemark);
        }
    }
}
