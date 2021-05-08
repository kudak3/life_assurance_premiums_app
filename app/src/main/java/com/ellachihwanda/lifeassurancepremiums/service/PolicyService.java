package com.ellachihwanda.lifeassurancepremiums.service;

import com.ellachihwanda.lifeassurancepremiums.model.dto.CoverDto;
import com.ellachihwanda.lifeassurancepremiums.model.Policy;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PolicyService {
    @GET("policies")
    Call<List<Policy>> getPolicies();

    @POST("policies")
    Call<PolicyCoverage> joinPolicy(@Body CoverDto coverDto);

    @GET("policies/{userId}")
    Call<List<PolicyCoverage>> getPolicyCovers(@Path("userId")Long id);
}
