package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Vaccine Model
 * Created by @lukmanadelt on 12/7/2017.
 */

public class Vaccine {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("current")
    @Expose
    private int current;

    private int checked;

    private String next_date;

    private String remark;

    /* Used for reports */

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("pet_category")
    @Expose
    private String pet_category;

    @SerializedName("vaccine")
    @Expose
    private String vaccine;

    @SerializedName("amount")
    @Expose
    private String amount;

    /* End used for reports */

    public Vaccine(int id, String name, int current) {
        this.id = id;
        this.name = name;
        this.current = current;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCurrent() {
        return current;
    }

    public int getChecked() {
        return this.checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getNextDate() {
        return this.next_date;
    }

    public void setNextDate(String next_date) {
        this.next_date = next_date;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /* Used for reports */

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPetCategory() {
        return pet_category;
    }

    public void setPetCategory(String pet_category) {
        this.pet_category = pet_category;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /* End used for reports */
}
