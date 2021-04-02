package com.ellachihwanda.lifeassurancepremiums.service;

import com.ellachihwanda.lifeassurancepremiums.model.Payment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PaymentService {

    @GET("payments")
    Call<List<Payment>> getPaymentsHistory();

    @POST("payment")
    Call<Payment> makePayment();
}
