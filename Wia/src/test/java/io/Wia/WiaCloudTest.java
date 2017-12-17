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

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
    System.out.println("-----  RUNNING TEST BUILDER");

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
          .subscribe(spacesResponse -> {
            System.out.println("Spaces count: " + spacesResponse.count());
            done.release();
          }, error -> {
            System.out.println(error.toString());
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

    Observable<WiaSpace> result = Wia.retrieveSpace("spc_UQhscsFI");
    result.subscribeOn(Schedulers.io())
          .subscribe(space -> {
            System.out.println("Space ID:" + space.id());
            System.out.println("Space Name:" + space.name());
            done.release();
          }, error -> {
            System.out.println(error.toString());
          });

    assertTrue(done.tryAcquire(1, 10, TimeUnit.SECONDS));
  }
}
