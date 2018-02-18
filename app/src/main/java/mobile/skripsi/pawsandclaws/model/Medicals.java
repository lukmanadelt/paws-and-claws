package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Medicals Model
 * Created by @lukmanadelt on 12/7/2017.
 */

public class Medicals {
    @SerializedName("medicals")
    @Expose
    private ArrayList<Medical> medicals = new ArrayList<>();

    public ArrayList<Medical> getMedicals() {
        return medicals;
    }

    public void setMedicals(ArrayList<Medical> medicals) {
        this.medicals = medicals;
    }
}
