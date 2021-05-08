package com.ellachihwanda.lifeassurancepremiums.service;

import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.model.dto.UserDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserService {

    @POST("users/login")
    Call<User> basicLogin(@Query("username") String username, @Query("password") String password, @Query("token") String token);

    @GET("users/{id}")
    Call<User> getUserProfile(@Path("id") Long id);

    @POST("users")
    Call<User> registerUser(@Body UserDto userDto);

    @PUT("users")
    Call<User> updateProfile(@Body User user);

    @POST("notifications/subscribe")
    Call<ResponseBody> subscribeToTopic(@Body String token);
}
