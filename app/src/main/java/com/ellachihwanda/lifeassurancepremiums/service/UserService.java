package com.ellachihwanda.lifeassurancepremiums.service;

import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.model.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserService {

    @GET("users/login")
    Call<User> basicLogin(@Query("username") String username, @Query("password") String password);

    @GET("users/{id}")
    Call<User> getUserProfile(@Path("id") Long id);

    @POST("users")
    Call<User> registerUser(@Body UserDto userDto);

    @PUT("users")
    Call<User> updateProfile(@Body User user);
}
