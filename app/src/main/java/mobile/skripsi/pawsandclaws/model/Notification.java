package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Notification Model
 * Created by @lukmanadelt on 12/12/2017.
 */

public class Notification {
    @SerializedName("description")
    @Expose
    private String description;

    public Notification(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
