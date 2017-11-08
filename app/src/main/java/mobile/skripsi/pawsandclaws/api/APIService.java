package mobile.skripsi.pawsandclaws.api;

import java.util.List;

import mobile.skripsi.pawsandclaws.model.Result;
import mobile.skripsi.pawsandclaws.model.User;
import mobile.skripsi.pawsandclaws.model.Users;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    Call<Users> getUsers();
}
