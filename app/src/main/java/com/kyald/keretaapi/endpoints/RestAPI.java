package com.kyald.keretaapi.endpoints;

import com.kyald.keretaapi.responses.AdminPassenggerRespon;
import com.kyald.keretaapi.responses.AuthResponse;
import com.kyald.keretaapi.responses.ChairResponse;
import com.kyald.keretaapi.responses.CleaningResponse;
import com.kyald.keretaapi.responses.DishResponse;
import com.kyald.keretaapi.responses.OrderResponse;
import com.kyald.keretaapi.responses.PassangerResponse;
import com.kyald.keretaapi.responses.PassangerSearchResponse;
import com.kyald.keretaapi.responses.PesananResponse;
import com.kyald.keretaapi.responses.StatusResponse;
import com.kyald.keretaapi.responses.TicketResponse;
import com.kyald.keretaapi.responses.TrackResponse;
import com.kyald.keretaapi.responses.TrackUpdateResponse;
import com.kyald.keretaapi.responses.TrainResponse;
import com.kyald.keretaapi.responses.TrainValidateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by gilangpramudya on 2/5/17.
 */

public interface RestAPI {

    @FormUrlEncoded
    @POST("api/v1/user/register")
    Call<AuthResponse> postRegist(@Field("name") String username, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/v1/user/login")
    Call<AuthResponse> postLogin(@Field("name") String username, @Field("email") String email, @Field("password") String password);

    @GET("api/v1/train")
    Call<TrainResponse> getTrain(@Header("X-Auth-Token") String lang);

    @FormUrlEncoded
    @POST("api/v1/train/validate")
    Call<TrainValidateResponse> postTrainValidate(@Header("X-Auth-Token") String lang, @Field("train_id") String train_id, @Field("date") String date);

    @FormUrlEncoded
    @POST("api/v1/train/chair")
    Call<ChairResponse> postChairValidate(@Header("X-Auth-Token") String lang, @Field("coach_id") String train_id, @Field("date") String date, @Field("chair") String chair);

    @FormUrlEncoded
    @POST("api/v1/ticket/order")
    Call<OrderResponse> postOrder(@Header("X-Auth-Token") String lang,
                                  @Field("user_id") Integer user_id,
                                  @Field("coach_id") Integer coach_id,
                                  @Field("train_id") Integer train_id,
                                  @Field("chair") Integer chair,
                                  @Field("date") String date);

    @FormUrlEncoded
    @POST("api/v1/ticket/status")
    Call<TicketResponse> getTicket(@Header("X-Auth-Token") String lang,
                                   @Field("code") String code);


    @GET("api/v1/dish/menu")
    Call<DishResponse> GetDataDish(@Header("X-Auth-Token") String lang);

    @GET("api/v1/dish/order/list")
    Call<StatusResponse> GetDataStatus(@Header("X-Auth-Token") String lang);


    @FormUrlEncoded
    @POST("api/v1/dish/order/user")
    Call<StatusResponse> getFoodStatusUser(@Header("X-Auth-Token") String lang,@Field("code") String code);

    @FormUrlEncoded
    @POST("api/v1/dish/status/update")
    Call<OrderResponse> postFoodStatus(@Header("X-Auth-Token") String lang,@Field("ordernumb") String code);


    @FormUrlEncoded
    @POST("api/v1/dish/order")
    Call<PesananResponse> PostDataStatus(@Header("X-Auth-Token") String lang, @Field("code") String code, @Field("makanan") String makanan, @Field("harga") String harga);


    @FormUrlEncoded
    @POST("api/v1/track/train")
    Call<TrackResponse> getTrack(@Header("X-Auth-Token") String lang,
                                 @Field("train_id") int train_id);

    @FormUrlEncoded
    @POST("api/v1/track/update")
    Call<TrackUpdateResponse> postTrack(@Header("X-Auth-Token") String lang,
                                        @Field("train_id") int train_id,
                                        @Field("track_id") int track_id,
                                        @Field("status") String status);

    @FormUrlEncoded
    @POST("api/v1/pass/user")
    Call<PassangerResponse> getPassanger(@Header("X-Auth-Token") String lang,
                                         @Field("code") String code);

    @FormUrlEncoded
    @POST("api/v1/pass/user/search")
    Call<PassangerSearchResponse> getPassangerSearch(@Header("X-Auth-Token") String lang,
                                                     @Field("code") String code);

    @FormUrlEncoded
    @POST("api/v1/pass/admin/update")
    Call<AdminPassenggerRespon> updatePasaanger(@Header("X-Auth-Token") String lang,
                                         @Field("code") String code);

    @FormUrlEncoded
    @POST("api/v1/pass/admin")
    Call<AdminPassenggerRespon> getPassangerAdmin(@Header("X-Auth-Token") String lang,
                                                  @Field("date") String date,
                                                  @Field("train_id") Integer train_id,
                                                  @Field("coach_id") Integer code);


    @FormUrlEncoded
    @POST("api/v1/clean/status/update")
    Call<CleaningResponse> postClean(@Header("X-Auth-Token") String lang,
                                     @Field("date") String date,
                                     @Field("train_id") int train_id,
                                     @Field("coach_id") int coach_id,
                                     @Field("coach") int coach,
                                     @Field("chair") int chair,
                                     @Field("toilet") int toilet);



}
