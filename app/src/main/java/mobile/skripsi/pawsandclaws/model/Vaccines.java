package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Vaccines Model
 * Created by @lukmanadelt on 12/7/2017.
 */

public class Vaccines {
    @SerializedName("vaccines")
    @Expose
    private ArrayList<Vaccine> vaccines = new ArrayList<>();

    public ArrayList<Vaccine> getVaccines() {
        return vaccines;
    }
}
