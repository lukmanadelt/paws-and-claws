package mobile.skripsi.pawsandclaws.api;

import mobile.skripsi.pawsandclaws.model.User;
import mobile.skripsi.pawsandclaws.model.Users;
import mobile.skripsi.pawsandclaws.model.Result;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * API Service
 * Created by @lukmanadelt on 06/11/2017.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("doctors")
    Call<Users> getDoctors();

    @GET("doctors/{id}")
    Call<Result> getDoctor(@Path("id") int id);

    @FormUrlEncoded
    @POST("doctors/update/{id}")
    Call<Result> updateDoctor(
            @Path("id") int id,
            @Field("username") String username,
            @Field("fullname") String fullname,
            @Field("status") int status
    );

    @FormUrlEncoded
    @POST("doctors/insert")
    Call<Result> insertDoctor(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fullname") String fullname
    );

    @GET("customers")
    Call<Users> getCustomers();

    @FormUrlEncoded
    @POST("customers/insert")
    Call<Result> insertCustomer(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fullname") String fullname,
            @Field("phone") String phone,
            @Field("address") String address
    );

    @GET("customers/{id}")
    Call<Result> getCustomer(@Path("id") int id);

    @FormUrlEncoded
    @POST("customers/update/{id}")
    Call<Result> updateCustomer(
            @Path("id") int id,
            @Field("username") String username,
            @Field("fullname") String fullname,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("status") int status
    );

    @FormUrlEncoded
    @POST("profiles/update/{id}")
    Call<Result> updateProfile(
            @Path("id") int id,
            @Field("username") String username,
            @Field("fullname") String fullname,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("password") String password
    );

    @FormUrlEncoded
    @Multipart
    @POST("pets/insert")
    Call<Result> insertPet(
            @Field("pet_category_id") int pet_category_id,
            @Field("user_id") int user_id,
            @Field("name") String name,
            @Field("sex") String sex,
            @Field("dob") String dob,
            @Field("age") int age,
            @Field("breed") String breed,
            @Field("color") String color,
            @Part MultipartBody.Part file
    );
}
