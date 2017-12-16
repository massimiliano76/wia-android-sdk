package io.wia;

import java.util.Map;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public interface WiaService {
  @GET("spaces")
  Observable<WiaSpacesResponse> listSpaces();

  // @GET("spaces/{id}")
  // Observable<WiaSpace> retrieveSpace(
  //   @Query("id") String id
  // );

  // @GET("spaces/{space}")
  // Flowable<Response<CDASpace>> space(
  //     @Path("space") String space);
  //
  // @GET("spaces/{space}/{type}/{identifier}")
  // Flowable<Response<CDAResource>> one(
  //     @Path("space") String space,
  //     @Path("type") String type,
  //     @Path("identifier") String identifier);
  //
  // @GET("spaces/{space}/{type}")
  // Flowable<Response<CDAArray>> array(
  //     @Path("space") String space,
  //     @Path("type") String type,
  //     @QueryMap Map<String, String> query);
  //
  // @GET("spaces/{space}/sync")
  // Flowable<Response<SynchronizedSpace>> sync(
  //     @Path("space") String space,
  //     @Query("initial") Boolean initial,
  //     @Query("sync_token") String syncToken);
}
