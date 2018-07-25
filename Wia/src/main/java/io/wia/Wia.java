/*
 * Copyright (c) 2017-present, Wia Technologies Limited.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.wia;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.JsonElement;

import okhttp3.OkHttpClient;

import io.reactivex.Observable;

public class Wia {
  private static final String TAG = "io.wia.Wia";

  /**
   * Represents an opaque configuration for the {@code Wia} SDK configuration.
   */
  public static final class Configuration {
    /**
     * Allows for simple constructing of a {@code Configuration} object.
     */
    public static final class Builder {
      private Context context;
      private String accessToken;
      private String appKey;
      private String clientKey;
      private String server;
      private OkHttpClient.Builder clientBuilder;

      /**
       * Initialize a bulider with a given context.
       * <p>
       * This context will then be passed through to the rest of the Wia SDK for use during
       * initialization.
       * <p>
       * <p/>
       * You may define (optional) {@code io.wia.SERVER_URL} and {@code io.wia.CLIENT_KEY}
       * {@code meta-data} in your {@code AndroidManifest.xml}:
       * <pre>
       * &lt;manifest ...&gt;
       *
       * ...
       *
       *   &lt;application ...&gt;
       *     &lt;meta-data
       *       android:name="io.wia.SERVER_URL"
       *       android:value="@string/wia_server_url" /&gt;
       *     &lt;meta-data
       *       android:name="io.wia.CLIENT_KEY"
       *       android:value="@string/wia_client_key" /&gt;
       *
       *       ...
       *
       *   &lt;/application&gt;
       * &lt;/manifest&gt;
       * </pre>
       * <p/>
       * <p>
       * This will cause the values for {@code server} and {@code clientKey} to be set to
       * those defined in your manifest.
       *
       * @param context The active {@link Context} for your application. Cannot be null.
       */
      public Builder(Context context) {
        this.context = context;

        if (context != null) {
          Context applicationContext = context.getApplicationContext();
          Bundle metaData = ManifestInfo.getApplicationMetadata(applicationContext);
          if (metaData != null) {
            server(metaData.getString(WIA_SERVER_URL));
            clientKey = metaData.getString(WIA_CLIENT_KEY);
            appKey = metaData.getString(WIA_APP_KEY);
          }
        }
      }

      /**
       * Set the client key to be used by Wia.
       * <p>
       * This method is only required if you intend to use a different {@code clientKey} than
       * is defined by {@code io.wia.CLIENT_KEY} in your {@code AndroidManifest.xml}.
       *
       * @param clientKey The client key to set.
       * @return The same builder, for easy chaining.
       */
      public Builder clientKey(String clientKey) {
        this.clientKey = clientKey;
        return this;
      }

      /**
       * Set the application key to be used by Wia.
       * <p>
       * This method is only required if you intend to use a different {@code appKey} than
       * is defined by {@code io.wia.APPLICATION_KEY} in your {@code AndroidManifest.xml}.
       *
       * @param appKey The application key to set.
       * @return The same builder, for easy chaining.
       */
      public Builder appKey(String appKey) {
        this.appKey = appKey;
        return this;
      }

      /**
       * Set the server URL to be used by Wia.
       *
       * @param server The server URL to set.
       * @return The same builder, for easy chaining.
       */
      public Builder server(String server) {

        // Add an extra trailing slash so that Wia REST commands include
        // the path as part of the server URL
        if (server != null && !server.endsWith("/")) {
          server = server + "/";
        }

        this.server = server;
        return this;
      }

      /**
       * Set the {@link okhttp3.OkHttpClient.Builder} to use when communicating with the Wia
       * REST API
       * <p>
       *
       * @param builder The client builder, which will be modified for compatibility
       * @return The same builder, for easy chaining.
       */
      public Builder clientBuilder(OkHttpClient.Builder builder) {
        clientBuilder = builder;
        return this;
      }

      public Builder accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
      }

      /**
       * Construct this builder into a concrete {@code Configuration} instance.
       *
       * @return A constructed {@code Configuration} object.
       */
      public Configuration build() {
        return new Configuration(this);
      }
    }

    final Context context;
    final String clientKey;
    final String appKey;
    final String server;
    final OkHttpClient.Builder clientBuilder;

    public String accessToken;

    private Configuration(Builder builder) {
      this.context = builder.context;
      this.clientKey = builder.clientKey;
      this.appKey = builder.appKey;
      this.server = builder.server;
      this.clientBuilder = builder.clientBuilder;
      this.accessToken = builder.accessToken;
    }
  }

  /**
   * Authenticates this client as belonging to your application.
   * <p/>
   * You may define {@code io.wia.SERVER_URL}, {@code io.wia.APPLICATION_ID} and (optional) {@code io.wia.CLIENT_KEY}
   * {@code meta-data} in your {@code AndroidManifest.xml}:
   * <pre>
   * &lt;manifest ...&gt;
   *
   * ...
   *
   *   &lt;application ...&gt;
   *     &lt;meta-data
   *       android:name="io.wia.SERVER_URL"
   *       android:value="@string/parse_server_url" /&gt;
   *     &lt;meta-data
   *       android:name="io.wia.APPLICATION_ID"
   *       android:value="@string/parse_app_id" /&gt;
   *     &lt;meta-data
   *       android:name="io.wia.CLIENT_KEY"
   *       android:value="@string/parse_client_key" /&gt;
   *
   *       ...
   *
   *   &lt;/application&gt;
   * &lt;/manifest&gt;
   * </pre>
   * <p/>
   * This must be called before your application can use the Wia library.
   * The recommended way is to put a call to {@code Wia.initialize}
   * in your {@code Application}'s {@code onCreate} method:
   * <p/>
   * <pre>
   * public class MyApplication extends Application {
   *   public void onCreate() {
   *     Wia.initialize(this);
   *   }
   * }
   * </pre>
   *
   * @param context The active {@link Context} for your application.
   */
  public static void initialize(Context context) {
    Configuration.Builder builder = new Configuration.Builder(context);
    if (builder.server == null) {
      throw new RuntimeException("ServerUrl not defined. " +
              "You must provide ServerUrl in AndroidManifest.xml.\n" +
              "<meta-data\n" +
              "    android:name=\"io.wia.SERVER_URL\"\n" +
              "    android:value=\"<Your Server Url>\" />");
    }
    initialize(builder.build());
  }

  /**
   * Authenticates this client as belonging to your application.
   * <p/>
   * This method is only required if you intend to use a different {@code applicationId} or
   * {@code clientKey} than is defined by {@code io.wia.APPLICATION_ID} or
   * {@code io.wia.CLIENT_KEY} in your {@code AndroidManifest.xml}.
   * <p/>
   * This must be called before your
   * application can use the Wia library. The recommended way is to put a call to
   * {@code Wia.initialize} in your {@code Application}'s {@code onCreate} method:
   * <p/>
   * <pre>
   * public class MyApplication extends Application {
   *   public void onCreate() {
   *     Wia.initialize(this, &quot;your application id&quot;, &quot;your client key&quot;);
   *   }
   * }
   * </pre>
   *
   * @param context       The active {@link Context} for your application.
   * @param clientKey     The client key provided in the Wia dashboard.
   */
  public static void initialize(Context context, String clientKey) {
    initialize(new Configuration.Builder(context)
            .clientKey(clientKey)
            .build()
    );
  }

  public static void initialize(Configuration configuration) {
    if (isInitialized()) {
      WLog.w(TAG, "Wia is already initialized");
      return;
    }

    WiaPlugins.Android.initialize(configuration.context, configuration);

    // try {
    //   WiaRESTCommand.server = new URL(configuration.server);
    // } catch (MalformedURLException ex) {
    //   throw new RuntimeException(ex);
    // }

    Context applicationContext = configuration.context.getApplicationContext();

    // WiaHttpClient.setKeepAlive(true);
    // WiaHttpClient.setMaxConnections(20);

    // WiaObject.registerWiaSubclasses();
  }

  /**
   * @return {@code True} if {@link #initialize} has been called, otherwise {@code false}.
   */
  static boolean isInitialized() {
    return WiaPlugins.get() != null;
  }

  static Context getApplicationContext() {
    // checkContext();
    return WiaPlugins.Android.get().applicationContext();
  }

  private static final String WIA_SERVER_URL = "io.wia.SERVER_URL";
  private static final String WIA_CLIENT_KEY = "io.wia.CLIENT_KEY";
  private static final String WIA_APP_KEY = "io.wia.APP_KEY";

  private Wia() {
    throw new AssertionError();
  }

  public static void reset() {
    WiaPlugins.reset();
  }

  public static void accessToken(String token) {
    WiaPlugins.get().accessToken(token);
  }

  public static String accessToken() {
    return WiaPlugins.get().accessToken();
  }

  public static Observable<WiaUser> createUser(String fullName, String emailAddress, String password) {
    WiaSignupRequest signupRequest = new WiaSignupRequest(
            fullName, emailAddress, password
    );

    return WiaPlugins.get().wiaService().createUser(signupRequest);
  }

  public static Observable<WiaUser> retrieveUser(String id) {
    return WiaPlugins.get().wiaService().retrieveUser(id);
  }

  public static Observable<WiaUserList> listUsers(String spaceId) {
    return WiaPlugins.get().wiaService().listUsers(spaceId);
  }

  public static Observable<WiaSpace> retrieveSpace(String id) {
    return WiaPlugins.get().wiaService().retrieveSpace(id);
  }

  public static Observable<WiaSpace> createSpace(String name) {
    WiaCreateSpaceRequest createSpaceRequest = new WiaCreateSpaceRequest(
        name
    );

    return WiaPlugins.get().wiaService().createSpace(createSpaceRequest);
  }

  public static Observable<WiaSpaceList> listSpaces() {
    return WiaPlugins.get().wiaService().listSpaces();
  }

  public static Observable<WiaSpaceUserInvite> addUserToSpace(String spaceId, String emailAddress) {
    WiaSpaceUserInviteRequest inviteRequest = new WiaSpaceUserInviteRequest(
            emailAddress
    );

    return WiaPlugins.get().wiaService().addUserToSpace(spaceId, inviteRequest);
  }

//  public static Observable<WiaUserRemove> removeUserFromSpace(String spaceId, String id) {
//    return WiaPlugins.get().wiaService().removeUserFromSpace(spaceId, id);
//  }

  public static Observable<WiaDevice> retrieveDevice(String id) {
    return WiaPlugins.get().wiaService().retrieveDevice(id);
  }

  public static Observable<WiaDeviceList> listDevices(String spaceId) {
    return WiaPlugins.get().wiaService().listDevices(spaceId);
  }
  
  public static Observable<WiaAccessToken> loginUser(String username, String password) {
    WiaLoginRequest loginRequest = new WiaLoginRequest(
      username, password, "user", "password"
    );

    return WiaPlugins.get().wiaService().generateAccessToken(loginRequest);
  }

  public static Observable<WiaId> registerNotificationDevice(String token) {
    return WiaPlugins.get().wiaService().registerNotificationDevice(token, "android");
  }

  public static Observable<WiaEvent> createEvent(String name) {
    WiaCreateEventRequest createEventRequest = new WiaCreateEventRequest(
            name
    );

    return WiaPlugins.get().wiaService().createEvent(createEventRequest);
  }

  public static Observable<WiaEvent> createEvent(String name, JsonElement data) {
    WiaCreateEventRequest createEventRequest = new WiaCreateEventRequest(
            name, data
    );

    return WiaPlugins.get().wiaService().createEvent(createEventRequest);
  }

  public static Observable<WiaLocation> createLocation(Double latitude, Double longitude) {
    WiaCreateLocationRequest createLocationRequest = new WiaCreateLocationRequest(
            latitude, longitude
    );

    return WiaPlugins.get().wiaService().createLocation(createLocationRequest);
  }

  public static Observable<WiaLocation> createLocation(Double latitude, Double longitude, Double altitude) {
    WiaCreateLocationRequest createLocationRequest = new WiaCreateLocationRequest(
            latitude, longitude, altitude
    );

    return WiaPlugins.get().wiaService().createLocation(createLocationRequest);
  }

}
