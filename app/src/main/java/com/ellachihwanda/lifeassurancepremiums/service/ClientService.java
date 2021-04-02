package com.ellachihwanda.lifeassurancepremiums.service;

import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.model.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientService {

      @GET("clients/{id}")
      Call<Client> getClientProfile(@Path("id") Long id);

      @GET("clients/user/{userId}")
      Call<Client> getClientProfileByUserId(@Path("userId") Long id);

    @POST("clients")
    Call<Client> registerClient(@Body Client client);

    @PUT("clients")
    Call<Client> updateClientProfile(@Body Client client);

    @GET("clients/{id}/coverages")
    Call<List<PolicyCoverage>> getClientCovers(@Path("id")Long id);
}
