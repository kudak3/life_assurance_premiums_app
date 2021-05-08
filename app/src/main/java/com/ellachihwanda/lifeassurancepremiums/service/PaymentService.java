package com.ellachihwanda.lifeassurancepremiums.service;

import com.ellachihwanda.lifeassurancepremiums.model.Payment;
import com.ellachihwanda.lifeassurancepremiums.model.PaymentType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PaymentService {





    @POST("payments")
    Call<Payment> makePayment(@Body Payment payment);

    @GET("payment-methods")
    Call<List<PaymentType>> getPaymentMethods();
}
