package com.humanid.auth.data.source.remote.api.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.humanid.auth.data.source.remote.api.APIResponse;
import com.humanid.auth.data.source.remote.api.user.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.user.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.user.login.check.CheckLoginResponse;
import com.humanid.auth.data.source.remote.api.user.otp.OTPRequest;
import com.humanid.auth.data.source.remote.api.user.otp.OTPResponse;
import com.humanid.auth.data.source.remote.api.user.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.user.register.RegisterResponse;
import com.humanid.auth.data.source.remote.api.user.update.UpdateRequest;
import com.humanid.auth.data.source.remote.api.user.update.UpdateResponse;
import com.humanid.auth.data.source.remote.api.user.updatephone.UpdatePhoneRequest;
import com.humanid.auth.data.source.remote.api.user.updatephone.UpdatePhoneResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserAPIService {

    @NonNull
    @POST("users/verifyPhone")
    LiveData<APIResponse<OTPResponse>> requestOTP(@Body OTPRequest request);

    @NonNull
    @POST("users/register")
    LiveData<APIResponse<RegisterResponse>> register(@Body RegisterRequest request);

    @NonNull
    @POST("users/login")
    LiveData<APIResponse<LoginResponse>> login(@Body LoginRequest request);

    @NonNull
    @GET("users/login")
    LiveData<APIResponse<CheckLoginResponse>> checkLogin(
            @Query("hash") String userHash, @Query("appId") String applicationID,
            @Query("appSecret") String applicationSecret);

    @NonNull
    @PUT("users")
    LiveData<APIResponse<UpdateResponse>> update(@Body UpdateRequest request);

    @NonNull
    @POST("users/updatePhone")
    LiveData<APIResponse<UpdatePhoneResponse>> updatePhone(@Body UpdatePhoneRequest request);
}
