package io.wia;

import java.util.Map;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public interface WiaService {
  @GET("users/{id}")
  Observable<WiaUser> retrieveUser(
    @Path("id") String id
  );

  @GET("spaces/{id}")
  Observable<WiaSpace> retrieveSpace(
    @Path("id") String id
  );

  @GET("spaces")
  Observable<WiaSpaceList> listSpaces();

  @GET("devices/{id}")
  Observable<WiaDevice> retrieveDevice(
    @Path("id") String id
  );

  @GET("devices")
  Observable<WiaDeviceList> listDevices(
    @Query("space.id") String spaceId
  );

  @POST("auth/token")
  Observable<WiaAccessToken> generateAccessToken(
    @Body WiaLoginRequest loginRequest
  );

  @POST("users")
  Observable<WiaUser> createUser(
    @Body WiaSignupRequest signupRequest
  );
}
