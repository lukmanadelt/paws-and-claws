package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Pet Model
 * Created by @lukmanadelt on 11/28/2017.
 */

public class Pet {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("pet_category_id")
    @Expose
    private int pet_category_id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("age")
    @Expose
    private int age;

    @SerializedName("breed")
    @Expose
    private String breed;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("photo")
    @Expose
    private String photo;

    public Pet(int id, int pet_category_id, int user_id, String name, String sex, String dob, int age, String breed, String color, String photo) {
        this.id = id;
        this.pet_category_id = pet_category_id;
        this.user_id = user_id;
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.age = age;
        this.breed = breed;
        this.color = color;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public int getPetCategoryId() {
        return pet_category_id;
    }

    public int getUserId() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getDOB() {
        return dob;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public String getColor() {
        return color;
    }

    public String getPhoto() {
        return photo;
    }
}
