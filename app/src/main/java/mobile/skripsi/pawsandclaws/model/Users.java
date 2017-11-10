package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Users Model
 * Created by @lukmanadelt on 11/7/2017.
 */

public class Users {
    @SerializedName("doctors")
    @Expose
    private ArrayList<User> doctors = new ArrayList<>();

    public ArrayList<User> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<User> doctors) {
        this.doctors = doctors;
    }

    @SerializedName("customers")
    @Expose
    private ArrayList<User> customers = new ArrayList<>();

    public ArrayList<User> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<User> customers) {
        this.customers = customers;
    }
}
