/*
 * Copyright (c) 2017-present, Wia Technologies Limited.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.wia;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.reactivex.Observable;
import java.util.concurrent.CountDownLatch;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.UUID;

// For android.os.Looper
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = io.wia.TestHelper.ROBOLECTRIC_SDK_VERSION)
public class WiaCloudTest {
  private static final String TAG = "io.wia.WiaCloudTest";

  private final String DEFAULT_SERVER_URL = "https://api.wia.io/v1";
  private String WIA_SERVER_URL = "io.wia.SERVER_URL";
  private String WIA_ACCESS_TOKEN = null;
  private String WIA_APP_KEY = null;
  private String WIA_CLIENT_KEY = null;
  private String WIA_SPACE_ID = null;
  private String WIA_DEVICE_ID = null;

  /**
   * Countdown latch
   */
  private CountDownLatch lock = new CountDownLatch(1);

  @Mock
  Context context;

  @Before
  public void setUp() throws Exception {
    Wia.reset();

    WIA_SERVER_URL = System.getenv("WIA_SERVER_URL") != null ? System.getenv("WIA_SERVER_URL") : DEFAULT_SERVER_URL;
    WIA_ACCESS_TOKEN = System.getenv("WIA_ACCESS_TOKEN");
    WIA_APP_KEY = System.getenv("WIA_APP_KEY");
    WIA_CLIENT_KEY = System.getenv("WIA_CLIENT_KEY");
    WIA_SPACE_ID = System.getenv("WIA_SPACE_ID");
    WIA_DEVICE_ID = System.getenv("WIA_DEVICE_ID");
  }

  @Test
  public void testBuilder() {
    Wia.Configuration.Builder builder = new Wia.Configuration.Builder(null);
    builder.appKey("bar");
    builder.server("http://abcdef.com");
    Wia.Configuration configuration = builder.build();

    assertNull(configuration.context);
    assertEquals(configuration.appKey, "bar");
  }

  @Test
  public void testInitializeApplicationBuilder() throws Exception {
      Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

      Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
//        .appKey(WIA_APP_KEY)
        .build()
      );
  }

  @Test
  public void testRetrieveCurrentUser() throws Exception {
    Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
        .clientKey(WIA_CLIENT_KEY)
//        .appKey(WIA_APP_KEY)
        .server(WIA_SERVER_URL)
        .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaUser> result = Wia.retrieveUser("me");
    result.subscribeOn(Schedulers.io())
          .subscribe(user -> {
            assertNotNull("Verify that user is NOT null", user);
            assertNotNull("Verify that user.id() is NOT null", user.id());
            done.release();
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

  @Test
  public void testListSpace() throws Exception {
    Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
        .clientKey(WIA_CLIENT_KEY)
//        .appKey(WIA_APP_KEY)
        .server(WIA_SERVER_URL)
        .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaSpaceList> result = Wia.listSpaces();
    result.subscribeOn(Schedulers.io())
          // NOTE: Add this for Android device testing
          // .observeOn(AndroidSchedulers.mainThread())
          .subscribe(response -> {
            assertNotNull("Verify that response is NOT null", response);
            assertNotNull("Verify that response.spaces() is NOT null", response.spaces());
            assertNotNull("Verify that response.count() is NOT null", response.count());
            done.release();
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

  @Test
  public void testRetrieveSpace() throws Exception {
    Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
        .clientKey(WIA_CLIENT_KEY)
//        .appKey(WIA_APP_KEY)
        .server(WIA_SERVER_URL)
        .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaSpace> result = Wia.retrieveSpace(WIA_SPACE_ID);
    result.subscribeOn(Schedulers.io())
          .subscribe(space -> {
            assertNotNull("Verify that space is NOT null", space);
            assertNotNull("Verify that space.id() is NOT null", space.id());
            assertNotNull("Verify that space.name() is NOT null", space.name());
            done.release();
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 15, TimeUnit.SECONDS));
  }

  @Test
  public void testListDevices() throws Exception {
    Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
        .clientKey(WIA_CLIENT_KEY)
//        .appKey(WIA_APP_KEY)
        .server(WIA_SERVER_URL)
        .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaDeviceList> result = Wia.listDevices(WIA_SPACE_ID);
    result.subscribeOn(Schedulers.io())
          // NOTE: Add this for Android device testing
          // .observeOn(AndroidSchedulers.mainThread())
          .subscribe(response -> {
            assertNotNull("Verify that response is NOT null", response);
            assertNotNull("Verify that response.devices() is NOT null", response.devices());
            assertNotNull("Verify that response.count() is NOT null", response.count());
            done.release();
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

    @Test
    public void testCreateDevice() throws Exception {
        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
                .clientKey(WIA_CLIENT_KEY)
//                .appKey(WIA_APP_KEY)
                .server(WIA_SERVER_URL)
                .build()
        );

        Wia.accessToken(WIA_ACCESS_TOKEN);

        final Semaphore done = new Semaphore(0);
        final String name = "Test Device " + String.valueOf(System.currentTimeMillis());
        final int deviceTypeId = 2001;

        Observable<WiaDevice> result = Wia.createDevice(name, deviceTypeId, WIA_SPACE_ID);
        result.subscribeOn(Schedulers.io())
                // NOTE: Add this for Android device testing
                // .observeOn(AndroidSchedulers.mainThread())
                .subscribe(device -> {
                    assertNotNull("Verify that device is NOT null", device);
                    done.release();
                }, error -> {
                    System.err.println(error.toString());
                });

        assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
    }

    @Test
    public void testListDeviceTypes() throws Exception {
        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
                .clientKey(WIA_CLIENT_KEY)
//                .appKey(WIA_APP_KEY)
                .server(WIA_SERVER_URL)
                .build()
        );

        Wia.accessToken(WIA_ACCESS_TOKEN);

        final Semaphore done = new Semaphore(0);

        Observable<WiaDeviceTypeList> result = Wia.listDeviceTypes();
        result.subscribeOn(Schedulers.io())
                // NOTE: Add this for Android device testing
                // .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    assertNotNull("Verify that response is NOT null", response);
                    assertNotNull("Verify that response.devices() is NOT null", response.deviceTypes());
                    assertNotNull("Verify that response.count() is NOT null", response.count());
                    done.release();
                }, error -> {
                    System.err.println(error.toString());
                });

        assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
    }

  @Test
  public void testListWidgets() throws Exception {
      Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

      Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
              .clientKey(WIA_CLIENT_KEY)
//              .appKey(WIA_APP_KEY)
              .server(WIA_SERVER_URL)
              .build()
      );

      Wia.accessToken(WIA_ACCESS_TOKEN);

      final Semaphore done = new Semaphore(0);

      Observable<WiaWidgetList> result = Wia.listWidgets(WIA_DEVICE_ID);
      result.subscribeOn(Schedulers.io())
              // NOTE: Add this for Android device testing
              // .observeOn(AndroidSchedulers.mainThread())
              .subscribe(response -> {
                  assertNotNull("Verify that response is NOT null", response);
                  assertNotNull("Verify that response.widgets() is NOT null", response.widgets());
                  assertNotNull("Verify that response.count() is NOT null", response.count());
                  done.release();
              }, error -> {
                  System.err.println(error.toString());
              });

      assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

  @Test
  public void testQueryEvents() throws Exception {
      Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

      Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
              .clientKey(WIA_CLIENT_KEY)
//              .appKey(WIA_APP_KEY)
              .server(WIA_SERVER_URL)
              .build()
      );

      Wia.accessToken(WIA_ACCESS_TOKEN);

      final Semaphore done = new Semaphore(0);
      String name = "UPQUETeE8YsyEEr2svQNxK875Vl1Vri8";
      long since = 1533304451362L;
      long until = 1535982851362L;
      String resolution = "day";
      String aggregateFunction = "avg";
      String sort = "asc";

      Observable<WiaEventQuery> result = Wia.queryEvents(WIA_DEVICE_ID, name, since, until, aggregateFunction, resolution, sort);
      result.subscribeOn(Schedulers.io())
              // NOTE: Add this for Android device testing
              // .observeOn(AndroidSchedulers.mainThread())
              .subscribe(response -> {
                  assertNotNull("Verify that response is NOT null", response);
                  assertNotNull("Verify that response.result() is NOT null", response.result());
                  done.release();
              }, error -> {
                  System.err.println(error.toString());
              });

      assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

    @Test
    public void testGetEvents() throws Exception {
        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
                        .clientKey(WIA_CLIENT_KEY)
//              .appKey(WIA_APP_KEY)
                        .server(WIA_SERVER_URL)
                        .build()
        );

        Wia.accessToken(WIA_ACCESS_TOKEN);

        final Semaphore done = new Semaphore(0);
        String name = "UPQUETeE8YsyEEr2svQNxK875Vl1Vri8";
        long since = 1533304451362L;
        long until = 1735982851362L;
        int limit = 1;
        int page = 1;

        Observable<WiaEventList> result = Wia.getEvents(WIA_DEVICE_ID, limit, page, since, until, name);
        result.subscribeOn(Schedulers.io())
                // NOTE: Add this for Android device testing
                // .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    System.out.println(response.events().toString());
                    assertNotNull("Verify that response is NOT null", response);
                    assertNotNull("Verify that response.result() is NOT null", response.events());
                    done.release();
                }, error -> {
                    System.err.println(error.toString());
                });

        assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
    }

//  @Test
//  public void testCreateUser() throws Exception {
//    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);
//
//    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
//      .clientKey(WIA_CLIENT_KEY)
////        .appKey(WIA_APP_KEY)
//        .server(WIA_SERVER_URL)
//        .build()
//    );
//
//    final Semaphore done = new Semaphore(0);
//    final String fullName = "Test User";
//    final String emailAddress = "team+" + String.valueOf(System.currentTimeMillis()) + "@wia.io";
//    final String password = String.valueOf(System.currentTimeMillis());
//
//    Observable<WiaUser> result = Wia.createUser(
//      fullName, emailAddress, password
//    );
//    result.subscribeOn(Schedulers.io())
//          // NOTE: Add this for Android device testing
//          // .observeOn(AndroidSchedulers.mainThread())
//          .subscribe(user -> {
//            assertNotNull("Verify that user is NOT null", user);
//            assertNotNull("Verify that user.id() is NOT null", user.id());
//            done.release();
//          }, error -> {
//            System.err.println(error.toString());
//          });
//
//    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
//  }

    @Test
    public void testListUsers() throws Exception {
        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
                .clientKey(WIA_CLIENT_KEY)
//                .appKey(WIA_APP_KEY)
                .server(WIA_SERVER_URL)
                .build()
        );

        Wia.accessToken(WIA_ACCESS_TOKEN);

        final Semaphore done = new Semaphore(0);

        Observable<WiaUserList> result = Wia.listUsers(WIA_SPACE_ID);
        result.subscribeOn(Schedulers.io())
                // NOTE: Add this for Android device testing
                // .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    assertNotNull("Verify that response is NOT null", response);
                    assertNotNull("Verify that response.users() is NOT null", response.users());
                    assertNotNull("Verify that response.count() is NOT null", response.count());
                    done.release();
                }, error -> {
                    System.err.println(error.toString());
                });

        assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
    }

    @Test
    public void testCreateSpace() throws Exception {
        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
                .clientKey(WIA_CLIENT_KEY)
//                .appKey(WIA_APP_KEY)
                .server(WIA_SERVER_URL)
                .build()
        );

        Wia.accessToken(WIA_ACCESS_TOKEN);

        final Semaphore done = new Semaphore(0);
        final String name = "Test Space " + String.valueOf(System.currentTimeMillis());

        Observable<WiaSpace> result = Wia.createSpace(
                name
        );
        result.subscribeOn(Schedulers.io())
                // NOTE: Add this for Android device testing
                // .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    assertNotNull("Verify that space is NOT null", user);
                    assertNotNull("Verify that space.id() is NOT null", user.id());
                    done.release();
                }, error -> {
                    System.err.println(error.toString());
                });

        assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
    }

    @Test
    public void testUpdateAvatar() throws Exception {
        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
                        .clientKey(WIA_CLIENT_KEY)
//                .appKey(WIA_APP_KEY)
                        .server(WIA_SERVER_URL)
                        .build()
        );

        Wia.accessToken(WIA_ACCESS_TOKEN);

        final Semaphore done = new Semaphore(0);

        File localImage = new File("./src/test/java/io/Wia/test.png");

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), localImage);

        MultipartBody.Part file = MultipartBody.Part.createFormData("file", localImage.getName(), requestFile);

        String id = WIA_SPACE_ID;

        retrofit2.Call<ResponseBody> call = Wia.updateAvatar(file, id);
        try {
            Response response = call.execute();
//            System.out.println(response.toString());
            assertNotNull("Verify that response is NOT null", response);
            done.release();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
    }

//  @Test
//  public void testCreateAndLoginUser() throws Exception {
//    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);
//
//    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
//      .clientKey(WIA_CLIENT_KEY)
//      .server(WIA_SERVER_URL)
//      .build()
//    );
//
//    final Semaphore done = new Semaphore(0);
//    final String fullName = "Test User";
//    final String emailAddress = "team+" + String.valueOf(System.currentTimeMillis()) + "@wia.io";
//    final String password = String.valueOf(System.currentTimeMillis());
//
//    Observable<WiaUser> signupUserObservable = Wia.createUser(
//      fullName, emailAddress, password
//    );
//    signupUserObservable.subscribeOn(Schedulers.io())
//          // NOTE: Add this for Android device testing
//          // .observeOn(AndroidSchedulers.mainThread())
//          .subscribe(createdUser -> {
//            assertNotNull("Verify that user is NOT null", createdUser);
//            assertNotNull("Verify that user.id() is NOT null", createdUser.id());
//            assertNotNull("Verify that user.fullName() is NOT null", createdUser.fullName());
//
//            Observable<WiaAccessToken> loginUserObservable = Wia.loginUser(
//              emailAddress, password
//            );
//            loginUserObservable.subscribeOn(Schedulers.io())
//                  // NOTE: Add this for Android device testing
//                  // .observeOn(AndroidSchedulers.mainThread())
//                  .subscribe(accessToken -> {
//                    assertNotNull("Verify that accessToken is NOT null", accessToken);
//                    assertNotNull("Verify that accessToken.token() is NOT null", accessToken.token());
//
//                    Wia.accessToken(accessToken.token());
//
//                    Observable<WiaUser> currentUserObservable = Wia.retrieveUser("me");
//                    currentUserObservable.subscribeOn(Schedulers.io())
//                          // NOTE: Add this for Android device testing
//                          // .observeOn(AndroidSchedulers.mainThread())
//                          .subscribe(retrievedUser -> {
//                            assertNotNull("Verify that retrievedUser is NOT null", retrievedUser);
//                            assertNotNull("Verify that retrievedUser.id() is NOT null", retrievedUser.id());
//                            assertEquals(retrievedUser.id(), createdUser.id());
//                            done.release();
//                          }, error -> {
//                            System.err.println(error.toString());
//                          });
//                  }, error -> {
//                    System.err.println(error.toString());
//                  });
//          }, error -> {
//            System.err.println(error.toString());
//          });
//
//    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
//  }

  @Test
  public void testRetrieveDeviceWithLocation() throws Exception {
    Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
            .clientKey(WIA_CLIENT_KEY)
//            .appKey(WIA_APP_KEY)
            .server(WIA_SERVER_URL)
            .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaDevice> result = Wia.retrieveDevice(WIA_DEVICE_ID);
    result.subscribeOn(Schedulers.io())
          .subscribe(device -> {
            assertNotNull("Verify that device is NOT null", device);
            assertNotNull("Verify that device.id() is NOT null", device.id());
            assertNotNull("Verify that device.name() is NOT null", device.name());
            assertNotNull("Verify that device.location() is NOT null", device.location());
            assertNotNull("Verify that device.location().timestamp() is NOT null", device.location().timestamp());
            done.release();
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

  @Test
  public void testRetrieveDeviceWithEvent() throws Exception {
    Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
            .clientKey(WIA_CLIENT_KEY)
//            .appKey(WIA_APP_KEY)
            .server(WIA_SERVER_URL)
            .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaDevice> result = Wia.retrieveDevice(WIA_DEVICE_ID);
    result.subscribeOn(Schedulers.io())
          .subscribe(device -> {
            assertNotNull("Verify that device is NOT null", device);
            assertNotNull("Verify that device.id() is NOT null", device.id());
            assertNotNull("Verify that device.name() is NOT null", device.name());
            assertNotNull("Verify that device.events() is NOT null", device.events());
            done.release();
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

    @Test
    public void testGetDeviceApiKeys() throws Exception {
        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
                .clientKey(WIA_CLIENT_KEY)
//                .appKey(WIA_APP_KEY)
                .server(WIA_SERVER_URL)
                .build()
        );

        Wia.accessToken(WIA_ACCESS_TOKEN);

        final Semaphore done = new Semaphore(0);

        Observable<WiaDeviceApiKeys> result = Wia.getDeviceApiKeys(WIA_DEVICE_ID);
        result.subscribeOn(Schedulers.io())
                .subscribe(apiKeys -> {
                    assertNotNull("Verify that apiKeys is NOT null", apiKeys);
                    assertNotNull("Verify that apiKeys.secretKey() is NOT null", apiKeys.secretKey());
                    done.release();
                }, error -> {
                    System.err.println(error.toString());
                });

        assertTrue(done.tryAcquire(1, 15, TimeUnit.SECONDS));
    }

    @Test
    public void testAddUserToSpace() throws Exception {
        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
                .clientKey(WIA_CLIENT_KEY)
//                .appKey(WIA_APP_KEY)
                .server(WIA_SERVER_URL)
                .build()
        );

        Wia.accessToken(WIA_ACCESS_TOKEN);

        final Semaphore done = new Semaphore(0);

        Observable<WiaSpaceUserInvite> result = Wia.addUserToSpace(WIA_SPACE_ID, "test@wia.io");
        result.subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    assertNotNull("Verify that response is NOT null", response);
                    assertNotNull("Verify that response.invited() is NOT null", response.invited());
                    done.release();
                }, error -> {
                    System.err.println(error.toString());
                });

        assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
    }

    @Test
    public void testRegisterNotificationDevice() throws Exception {
        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);

        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
                .clientKey(WIA_CLIENT_KEY)
//                .appKey(WIA_APP_KEY)
                .server(WIA_SERVER_URL)
                .build()
        );

        Wia.accessToken(WIA_ACCESS_TOKEN);

        final Semaphore done = new Semaphore(0);

        String uuid = UUID.randomUUID().toString();

        Observable<WiaId> result = Wia.registerNotificationDevice(uuid);
        result.subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    assertNotNull("Verify that response is NOT null", response);
                    assertNotNull("Verify that response.id() is NOT null", response.id());
                    done.release();
                }, error -> {
                    System.err.println(error.toString());
                });

        assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
    }

//    @Test
//    public void testRunCommand() throws Exception {
//        Activity activity = Robolectric.setupActivity(io.wia.WiaTestActivity.class);
//
//        Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
//                .clientKey(WIA_CLIENT_KEY)
////                .appKey(WIA_APP_KEY)
//                .server(WIA_SERVER_URL)
//                .build()
//        );
//
//        Wia.accessToken(WIA_ACCESS_TOKEN);
//
//        final Semaphore done = new Semaphore(0);
//        String slug = "mk9znxb9rftaysmcaillvzaj";
//
//        Observable<WiaRunCommandResponse> result = Wia.runCommand(WIA_DEVICE_ID, slug);
//        result.subscribeOn(Schedulers.io())
//                // NOTE: Add this for Android device testing
//                // .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    assertNotNull("Verify that response is NOT null", response);
//                    done.release();
//                }, error -> {
//                    System.err.println(error.toString());
//                });
//
//        assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
//    }
}
