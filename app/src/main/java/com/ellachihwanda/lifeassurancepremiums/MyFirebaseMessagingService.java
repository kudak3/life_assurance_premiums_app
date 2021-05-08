package com.ellachihwanda.lifeassurancepremiums;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.service.UserService;
import com.google.firebase.messaging.FirebaseMessagingService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        sendRegistrationToServer(token);

    }

    private void sendRegistrationToServer(String token) {
        UserService notificationService =
                ApiClient.createService(UserService.class);
        Call<ResponseBody> call = notificationService.subscribeToTopic(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registered for push notifications", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to register for notifications", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


}
