package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.DetailInfo;
import mobile.skripsi.pawsandclaws.helper.HeaderInfo;
import mobile.skripsi.pawsandclaws.helper.MyListAdapter;
import mobile.skripsi.pawsandclaws.helper.SharedPreferencesManager;
import mobile.skripsi.pawsandclaws.model.Pets;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Vaccine Recommendation Activity
 * Created by @lukmanadelt on 12/2/2017.
 */

public class VaccineRecommendationActivity extends AppCompatActivity implements View.OnClickListener {
    private MyListAdapter listAdapter;
    private ExpandableListView expandableListView;
    private Button bNext;
    private LinkedHashMap<String, HeaderInfo> mySection = new LinkedHashMap<>();
    private ArrayList<HeaderInfo> SectionList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_recommendation);

        // Initial customer id
        int id = SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getId();

        //Just add some data to start with
        AddProduct(id);

        //get reference to the ExpandableListView
        expandableListView = findViewById(R.id.myList);
        bNext = findViewById(R.id.bNext);

        //create the adapter by passing your ArrayList data
        listAdapter = new MyListAdapter(VaccineRecommendationActivity.this, SectionList);
        //attach the adapter to the list
        expandableListView.setAdapter(listAdapter);

        bNext.setOnClickListener(this);

        //expand all Groups
        expandAll();
    }


    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandableListView.expandGroup(i);
        }
    }

    //load some initial data into out list
    private void AddProduct(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Pets> call = service.getVaccineRecommendation(id);

        call.enqueue(new Callback<Pets>() {
            @Override
            public void onResponse(Call<Pets> call, Response<Pets> response) {
                progressDialog.dismiss();

                for (int i = 0; i < response.body().getPets().size(); i++) {
                    addProduct(response.body().getPets().get(i).getName(), response.body().getPets().get(i).getVaccine(), response.body().getPets().get(i).getPeriod(), response.body().getPets().get(i).getCompleted(), response.body().getPets().get(i).getDescription());
                }
            }

            @Override
            public void onFailure(Call<Pets> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //here we maintain our products in various departments
    private void addProduct(String name, String vaccine, int period, int completed, String description) {
        //check the hash map if the group already exists
        HeaderInfo headerInfo = mySection.get(name);
        //add the group if doesn't exists
        if (headerInfo == null) {
            headerInfo = new HeaderInfo();
            headerInfo.setName(name);
            mySection.put(name, headerInfo);
            SectionList.add(headerInfo);
        }

        //get the children for the group
        ArrayList<DetailInfo> productList = headerInfo.getProductList();

        //create a new child and add that to the group
        DetailInfo detailInfo = new DetailInfo();
        detailInfo.setVaccine(vaccine);
        detailInfo.setPeriod(period);
        detailInfo.setCompleted(completed);
        detailInfo.setDescription(description);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);
    }

    @Override
    public void onClick(View v) {
        if (v == bNext) {
            startActivity(new Intent(this, CustomerActivity.class));
            finish();
        }
    }
}
