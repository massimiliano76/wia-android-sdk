package io.wia;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WiaService {
  // Authentication
  @POST("auth/token")
  Observable<WiaAccessToken> generateAccessToken(
    @Body WiaLoginRequest loginRequest
  );

  // Devices
  @GET("devices/{id}")
  Observable<WiaDevice> retrieveDevice(
          @Path("id") String id
  );

  @GET("devices")
  Observable<WiaDeviceList> listDevices(
          @Query("space.id") String spaceId
  );

  @GET("devices/{id}/apiKeys")
  Observable<WiaDeviceApiKeys> getDeviceApiKeys(
          @Path("id") String id
  );

  @POST("devices")
  Observable<WiaDevice> createDevice(
          @Body WiaCreateDeviceRequest createDeviceRequest
  );

  @GET("devices/types?order=manufacturer&sort=asc")
  Observable<WiaDeviceTypeList> listDeviceTypes();

  //Widgets
  @GET("widgets")
  Observable<WiaWidgetList> listWidgets(
          @Query("device.id") String deviceId
  );

  // Events
  @POST("events")
  Observable<WiaEvent> createEvent(
    @Body WiaCreateEventRequest createEventRequest
  );

  @GET("events/query")
  Observable<WiaEventQuery> queryEvents(
          @Query("device.id") String deviceId,
          @Query("name") String name,
          @Query("since") long since,
          @Query("until") long until,
          @Query("aggregateFunction") String aggregateFunction,
          @Query("resolution") String resolution,
          @Query("sort") String sort
  );

  // Locations
  @POST("locations")
  Observable<WiaLocation> createLocation(
    @Body WiaCreateLocationRequest createLocationRequest
  );

  // Commands
  @POST("commands/run")
  Observable<WiaRunCommandResponse> runCommand(
    @Body WiaRunCommandRequest runCommandRequest
  );

  // Notifications
  @POST("notifications/register")
  @FormUrlEncoded
  Observable<WiaId> registerNotificationDevice(
      @Field("token") String token,
      @Field("type") String type
  );

  // Spaces
  @POST("spaces")
  Observable<WiaSpace> createSpace(
    @Body WiaCreateSpaceRequest createSpaceRequest
  );

  // Upload avatar
  @Multipart
  @POST("spaces/{id}/avatar")
  Call<ResponseBody> updateAvatar(
    @Part MultipartBody.Part file,
    @Path("id") String id
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
}
