package io.wia;

import io.reactivex.Observable;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WiaService {
  // Users
  @POST("users")
  Observable<WiaUser> createUser(
    @Body WiaSignupRequest signupRequest
  );

  @GET("users/{id}")
  Observable<WiaUser> retrieveUser(
    @Path("id") String id
  );

  @GET("users")
  Observable<WiaUserList> listUsers(
    @Query("space.id") String spaceId
  );

  // Spaces
  @POST("spaces")
  Observable<WiaSpace> createSpace(
    @Body WiaCreateSpaceRequest createSpaceRequest
  );

  @GET("spaces/{id}")
  Observable<WiaSpace> retrieveSpace(
    @Path("id") String id
  );

  @GET("spaces")
  Observable<WiaSpaceList> listSpaces();

  // Space Users
  @POST("spaces/{spaceId}/users")
  Observable<WiaSpaceUserInvite> addUserToSpace(
    @Path("spaceId") String spaceId,
    @Body WiaSpaceUserInviteRequest spaceUserInviteRequest
  );

//  @DELETE("spaces/{spaceId}/users")
//  Observable<WiaUserRemove> removeUserFromSpace(
//    @Path("spaceId") String spaceId,
//    @Body String id
//  );

  // Devices
  @GET("devices/{id}")
  Observable<WiaDevice> retrieveDevice(
    @Path("id") String id
  );

  @GET("devices")
  Observable<WiaDeviceList> listDevices(
    @Query("space.id") String spaceId
  );

  // Authentication
  @POST("auth/token")
  Observable<WiaAccessToken> generateAccessToken(
    @Body WiaLoginRequest loginRequest
  );
}
