package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * User Model
 * Created by @lumanadelt on 06/11/2017.
 */

public class User {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("role_id")
    @Expose
    private int role_id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("phone")
    @Expose
    private int phone;

    @SerializedName("status")
    @Expose
    private int status;

    public User(int id, int role_id, String username, String password, String fullname, int phone, int status) {
        this.id = id;
        this.role_id = role_id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
    }

    public User(int id, int role_id, String username, String fullname) {
        this.id = id;
        this.role_id = role_id;
        this.username = username;
        this.fullname = fullname;
    }

    public User(int id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public int getRoleId() {
        return role_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() { return fullname; }
}
