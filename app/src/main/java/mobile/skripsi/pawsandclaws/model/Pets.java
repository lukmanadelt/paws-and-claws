package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Pets
 * Created by @lukmanadelt on 11/28/2017.
 */

public class Pets {
    @SerializedName("pets")
    @Expose
    private ArrayList<Pet> pets = new ArrayList<>();

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }
}
