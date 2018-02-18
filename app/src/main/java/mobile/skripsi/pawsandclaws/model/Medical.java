package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Medical Model
 * Created by @lukmanadelt on 12/7/2017.
 */

public class Medical {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    private int checked;

    private String remark;

    private String medicine;

    public Medical(int id, String name, String medicine) {
        this.id = id;
        this.name = name;
        this.medicine = medicine;
    }

    public int getId() {
        return id;
    }

    public String getName() { return name; }

    public int getChecked() {
        return this.checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMedicine() {
        return this.medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}
