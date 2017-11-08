package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Users Model
 * Created by @lukmanadelt on 11/7/2017.
 */

public class Users {
    @SerializedName("users")
    @Expose
    private ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
