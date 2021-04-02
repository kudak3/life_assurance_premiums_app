package com.ellachihwanda.lifeassurancepremiums.service;

import com.ellachihwanda.lifeassurancepremiums.model.InsuranceClaim;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClaimService {

    @GET("claims")
    Call<List<InsuranceClaim>> getClaimHistory();
}
