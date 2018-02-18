package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.model.Pets;
import mobile.skripsi.pawsandclaws.model.Vaccines;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Report View Activity
 * Created by @lukmanadelt on 12/11/2017.
 */

public class ReportViewActivity extends AppCompatActivity {
    private View parentView;
    private TextView tvEmpty;
    private TableLayout tlReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_view);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        TextView tvReport = findViewById(R.id.tvReport);
        TextView tvPeriod = findViewById(R.id.tvPeriod);
        tvEmpty = findViewById(R.id.tvEmpty);
        tlReport = findViewById(R.id.tlReport);

        int report_type = getIntent().getIntExtra("report_type", 0);
        String period_start = getIntent().getStringExtra("period_start");
        String period_end = getIntent().getStringExtra("period_end");

        tvPeriod.setText(getString(R.string.report_period, period_start, period_end));

        switch (report_type) {
            case 0:
                tvReport.setText(getString(R.string.report_vaccine));
                getReportVaccine(period_start, period_end);
                break;
            case 1:
                tvReport.setText(getString(R.string.report_pet));
                getReportPet(period_start, period_end);
                break;
        }
    }

    /**
     * Method to getting vaccine report
     */
    private void getReportVaccine(String period_start, String period_end) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Vaccines> call = service.getReportVaccines(period_start, period_end);

        call.enqueue(new Callback<Vaccines>() {
            @Override
            public void onResponse(Call<Vaccines> call, Response<Vaccines> response) {
                progressDialog.dismiss();

                if (response.body().getVaccines().size() == 0) {
                    tlReport.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    tlReport.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);

                    tlReport.removeAllViews();

                    TableRow rowHeader = new TableRow(ReportViewActivity.this);
                    rowHeader.setBackgroundColor(Color.parseColor("#6E797D"));
                    rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    rowHeader.setGravity(Gravity.CENTER);
                    String[] headerText = {"TANGGAL", "JENIS HEWAN", "JENIS VAKSIN", "JUMLAH"};

                    int column_header = 0;

                    for (String c : headerText) {
                        TextView tv = new TextView(ReportViewActivity.this);
                        tv.setLayoutParams(new TableRow.LayoutParams(column_header));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(12);
                        tv.setTextColor(Color.WHITE);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(c);

                        rowHeader.addView(tv);

                        column_header = column_header + 1;
                    }

                    tlReport.addView(rowHeader);

                    String[][] size = new String[response.body().getVaccines().size()][];

                    for (int i = 0; i < response.body().getVaccines().size(); i++) {
                        size[i] = new String[]{response.body().getVaccines().get(i).getDate(), response.body().getVaccines().get(i).getPetCategory(), response.body().getVaccines().get(i).getVaccine(), response.body().getVaccines().get(i).getAmount()};
                    }

                    for (int i = 0; i < response.body().getVaccines().size(); i++) {
                        TableRow row = new TableRow(ReportViewActivity.this);

                        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                        row.setGravity(Gravity.CENTER);

                        int column_detail = 0;

                        for (String text : size[i]) {
                            final TextView tv = new TextView(ReportViewActivity.this);

                            tv.setLayoutParams(new TableRow.LayoutParams(column_detail));
                            tv.setGravity(Gravity.CENTER);
                            tv.setTextSize(12);
                            tv.setPadding(5, 5, 5, 5);
                            tv.setText(text);

                            row.addView(tv);

                            column_detail = column_detail + 1;
                        }

                        tlReport.addView(row);
                    }
                }
            }

            @Override
            public void onFailure(Call<Vaccines> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method to getting pet report
     */
    private void getReportPet(String period_start, String period_end) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Pets> call = service.getReportPets(period_start, period_end);

        call.enqueue(new Callback<Pets>() {
            @Override
            public void onResponse(Call<Pets> call, Response<Pets> response) {
                progressDialog.dismiss();

                if (response.body().getPets().size() == 0) {
                    tlReport.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    tlReport.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);

                    tlReport.removeAllViews();

                    TableRow rowHeader = new TableRow(ReportViewActivity.this);
                    rowHeader.setBackgroundColor(Color.parseColor("#6E797D"));
                    rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    rowHeader.setGravity(Gravity.CENTER);
                    String[] headerText = {"TANGGAL", "JENIS HEWAN", "JUMLAH HEWAN"};

                    int column_header = 0;

                    for (String c : headerText) {
                        TextView tv = new TextView(ReportViewActivity.this);
                        tv.setLayoutParams(new TableRow.LayoutParams(column_header));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(12);
                        tv.setTextColor(Color.WHITE);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(c);

                        rowHeader.addView(tv);

                        column_header = column_header + 1;
                    }

                    tlReport.addView(rowHeader);

                    String[][] size = new String[response.body().getPets().size()][];

                    for (int i = 0; i < response.body().getPets().size(); i++) {
                        size[i] = new String[]{response.body().getPets().get(i).getDate(), response.body().getPets().get(i).getPetCategory(), response.body().getPets().get(i).getAmount()};
                    }

                    for (int i = 0; i < response.body().getPets().size(); i++) {
                        TableRow row = new TableRow(ReportViewActivity.this);

                        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                        row.setGravity(Gravity.CENTER);

                        int column_detail = 0;

                        for (String text : size[i]) {
                            final TextView tv = new TextView(ReportViewActivity.this);

                            tv.setLayoutParams(new TableRow.LayoutParams(column_detail));
                            tv.setGravity(Gravity.CENTER);
                            tv.setTextSize(12);
                            tv.setPadding(5, 5, 5, 5);
                            tv.setText(text);

                            row.addView(tv);

                            column_detail = column_detail + 1;
                        }

                        tlReport.addView(row);
                    }
                }
            }

            @Override
            public void onFailure(Call<Pets> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
