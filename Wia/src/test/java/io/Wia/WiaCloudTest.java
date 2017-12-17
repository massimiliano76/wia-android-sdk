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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.reactivex.Observable;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

// For android.os.Looper
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = TestHelper.ROBOLECTRIC_SDK_VERSION)
public class WiaCloudTest {
  private static final String TAG = "io.wia.WiaCloudTest";

  private final String serverUrl = "https://api.wia.io/v1";
  private final String clientKey = "ck_test";
  private String WIA_SERVER_URL = "io.wia.SERVER_URL";
  private String WIA_CLIENT_KEY = "io.wia.CLIENT_KEY";
  private String WIA_ACCESS_TOKEN = null;
  private String TEST_SPACE_ID = "spc_UQhscsFI";

  /**
   * Countdown latch
   */
  private CountDownLatch lock = new CountDownLatch(1);

  @Mock
  Context context;

  @Before
  public void setUp() throws Exception {
    Wia.reset();

    WIA_SERVER_URL = System.getenv("WIA_SERVER_URL") != null ? System.getenv("WIA_SERVER_URL") : "https://api.wia.io/v1";
    WIA_CLIENT_KEY = System.getenv("WIA_CLIENT_KEY");
    WIA_ACCESS_TOKEN = System.getenv("WIA_ACCESS_TOKEN");
  }

  @Test
  public void testBuilder() {
    Wia.Configuration.Builder builder = new Wia.Configuration.Builder(null);
    builder.clientKey("bar");
    builder.server("http://abcdef.com");
    Wia.Configuration configuration = builder.build();

    assertNull(configuration.context);
    assertEquals(configuration.clientKey, "bar");
  }

  @Test
  public void testInitializeBuilder() throws Exception {
    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
      .clientKey("ck_abcdef")
      .server("https://api.wia.io/v1")
      .build()
    );
  }

  @Test
  public void testRetrieveCurrentUser() throws Exception {
    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
      .clientKey(WIA_CLIENT_KEY)
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
    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
      .clientKey(WIA_CLIENT_KEY)
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
    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
      .clientKey(WIA_CLIENT_KEY)
      .server(WIA_SERVER_URL)
      .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaSpace> result = Wia.retrieveSpace(TEST_SPACE_ID);
    result.subscribeOn(Schedulers.io())
          .subscribe(space -> {
            assertNotNull("Verify that space is NOT null", space);
            assertNotNull("Verify that space.id() is NOT null", space.id());
            assertNotNull("Verify that space.name() is NOT null", space.name());
            done.release();
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

  @Test
  public void testListDevices() throws Exception {
    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
      .clientKey(WIA_CLIENT_KEY)
      .server(WIA_SERVER_URL)
      .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaDeviceList> result = Wia.listDevices(TEST_SPACE_ID);
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
  public void testCreateUser() throws Exception {
    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
      .clientKey(WIA_CLIENT_KEY)
      .server(WIA_SERVER_URL)
      .build()
    );

    final Semaphore done = new Semaphore(0);
    final String fullName = "Test User";
    final String emailAddress = "team+" + String.valueOf(System.currentTimeMillis()) + "@wia.io";
    final String password = String.valueOf(System.currentTimeMillis());

    Observable<WiaUser> result = Wia.createUser(
      fullName, emailAddress, password
    );
    result.subscribeOn(Schedulers.io())
          // NOTE: Add this for Android device testing
          // .observeOn(AndroidSchedulers.mainThread())
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
  public void testCreateAndLoginUser() throws Exception {
    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
      .clientKey(WIA_CLIENT_KEY)
      .server(WIA_SERVER_URL)
      .build()
    );

    final Semaphore done = new Semaphore(0);
    final String fullName = "Test User";
    final String emailAddress = "team+" + String.valueOf(System.currentTimeMillis()) + "@wia.io";
    final String password = String.valueOf(System.currentTimeMillis());

    Observable<WiaUser> signupUserObservable = Wia.createUser(
      fullName, emailAddress, password
    );
    signupUserObservable.subscribeOn(Schedulers.io())
          // NOTE: Add this for Android device testing
          // .observeOn(AndroidSchedulers.mainThread())
          .subscribe(createdUser -> {
            assertNotNull("Verify that user is NOT null", createdUser);
            assertNotNull("Verify that user.id() is NOT null", createdUser.id());
            assertNotNull("Verify that user.fullName() is NOT null", createdUser.fullName());

            Observable<WiaAccessToken> loginUserObservable = Wia.loginUser(
              emailAddress, password
            );
            loginUserObservable.subscribeOn(Schedulers.io())
                  // NOTE: Add this for Android device testing
                  // .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(accessToken -> {
                    assertNotNull("Verify that accessToken is NOT null", accessToken);
                    assertNotNull("Verify that accessToken.token() is NOT null", accessToken.token());

                    Wia.accessToken(accessToken.token());

                    Observable<WiaUser> currentUserObservable = Wia.retrieveUser("me");
                    currentUserObservable.subscribeOn(Schedulers.io())
                          // NOTE: Add this for Android device testing
                          // .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(retrievedUser -> {
                            assertNotNull("Verify that retrievedUser is NOT null", retrievedUser);
                            assertNotNull("Verify that retrievedUser.id() is NOT null", retrievedUser.id());
                            assertEquals(retrievedUser.id(), createdUser.id());
                            done.release();
                          }, error -> {
                            System.err.println(error.toString());
                          });
                  }, error -> {
                    System.err.println(error.toString());
                  });
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

  @Test
  public void testRetrieveDeviceWithLocation() throws Exception {
    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
      .clientKey(WIA_CLIENT_KEY)
      .server(WIA_SERVER_URL)
      .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaDevice> result = Wia.retrieveDevice("dev_zlMnBsYj");
    result.subscribeOn(Schedulers.io())
          .subscribe(device -> {
            assertNotNull("Verify that device is NOT null", device);
            assertNotNull("Verify that device.id() is NOT null", device.id());
            assertNotNull("Verify that device.name() is NOT null", device.name());
            assertNotNull("Verify that device.location() is NOT null", device.location());
            assertNotNull("Verify that device.location().timestamp() is NOT null", device.location().timestamp());
            System.out.println(device.toString());
            System.out.println(device.location().toString());
            done.release();
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

  @Test
  public void testRetrieveDeviceWithEvent() throws Exception {
    Activity activity = Robolectric.setupActivity(WiaTestActivity.class);

    Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
      .clientKey(WIA_CLIENT_KEY)
      .server(WIA_SERVER_URL)
      .build()
    );

    Wia.accessToken(WIA_ACCESS_TOKEN);

    final Semaphore done = new Semaphore(0);

    Observable<WiaDevice> result = Wia.retrieveDevice("dev_YIre07Mp");
    result.subscribeOn(Schedulers.io())
          .subscribe(device -> {
            assertNotNull("Verify that device is NOT null", device);
            assertNotNull("Verify that device.id() is NOT null", device.id());
            assertNotNull("Verify that device.name() is NOT null", device.name());
            assertNotNull("Verify that device.events() is NOT null", device.events());
            System.out.println(device.toString());
            System.out.println(device.events().toString());
            done.release();
          }, error -> {
            System.err.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }

}
