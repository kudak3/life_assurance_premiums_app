package com.ellachihwanda.lifeassurancepremiums.service;

import com.ellachihwanda.lifeassurancepremiums.model.InsuranceClaim;
import com.ellachihwanda.lifeassurancepremiums.model.dto.ClaimDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClaimService {

    @GET("claims")
    Call<List<InsuranceClaim>> getClaimHistory();

    @POST("claims")
    Call<ResponseBody> makeClaim(@Body ClaimDto claim);
}
