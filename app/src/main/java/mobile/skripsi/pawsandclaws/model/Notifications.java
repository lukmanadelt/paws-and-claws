package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Notifications Model
 * Created by @lukmanadelt on 12/13/2017.
 */

public class Notifications {
    @SerializedName("notifications")
    @Expose
    private ArrayList<Notification> notifications = new ArrayList<>();

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
}
