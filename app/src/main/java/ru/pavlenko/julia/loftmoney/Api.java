package ru.pavlenko.julia.loftmoney;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("auth")
    Call<AuthResponse> auth(@Query("social_user_id") String socialUserId);

    @GET("items")
    Call<List<Item>> getItems(@Query("type") String type, @Query("auth-token") String authToken);

    @GET("balance")
    Call<BalanceResponse> getBalance(@Query("auth-token") String authToken);

    @POST("items/add")
    Call<Status> add(@Body AddItemRequest request, @Query("auth-token") String authToken);

    @POST("items/remove")
    Call<Status> remove(@Query("id") int id, @Query("auth-token") String authToken);
}
