/*
 * Copyright (c) 2015-present, Wia, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package io.wia;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.annotation.SuppressLint;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import java.security.cert.CertificateException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HostnameVerifier;
import java.security.cert.X509Certificate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ConnectionSpec;
import okhttp3.CipherSuite;
import okhttp3.TlsVersion;
import okhttp3.CertificatePinner;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okio.Buffer;

class WiaPlugins {

    private static final Object LOCK = new Object();
    private static WiaPlugins instance;

    // TODO(grantland): Move towards a Config/Builder parameter pattern to allow other configurations
    // such as path (disabled for Android), etc.
    static void initialize(Wia.Configuration configuration) {
        WiaPlugins.set(new WiaPlugins(configuration));
    }

    static void set(WiaPlugins plugins) {
        synchronized (LOCK) {
            if (instance != null) {
                throw new IllegalStateException("WiaPlugins is already initialized");
            }
            instance = plugins;
        }
    }

    static WiaPlugins get() {
        synchronized (LOCK) {
            return instance;
        }
    }

    static void reset() {
        synchronized (LOCK) {
            instance = null;
        }
    }

    final Object lock = new Object();
    private Wia.Configuration configuration;

    // TODO: Change to WiaService
    // WiaHttpClient restClient;

    private WiaPlugins(Wia.Configuration configuration) {
        this.configuration = configuration;
    }

    String clientKey() {
        return configuration.clientKey;
    }

    void accessToken(String token) {
        configuration.accessToken = token;
    }

    String accessToken() {
        return configuration.accessToken;
    }

    WiaService wiaService() {
      Gson gson = new GsonBuilder()
              .setLenient()
              .create();

      ConnectionSpec spec = new
        ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(
                      TlsVersion.TLS_1_2
                    )
                    .cipherSuites(
                      CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                      CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                      CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA
                    )
                    .build();

      OkHttpClient client = new OkHttpClient.Builder()
              .certificatePinner(new CertificatePinner.Builder()
                  .add("api.wia.io", "sha256/Y2sYr/MXtA3/cbE06pNmPZ8M3gHyb38L4Yw0ovYBWvQ=")
                  .build())
              .connectionSpecs(Collections.singletonList(spec))
              .addInterceptor(new WiaHttpInterceptor())
              .build();

      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl(configuration.server)
              .client(client)
              .addConverterFactory(GsonConverterFactory.create(gson))
              .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
              .build();

      return retrofit.create(WiaService.class);
    }

    // TODO: Change this to WiaService and return Retrofit service
    // WiaHttpClient restClient() {
    //     synchronized (lock) {
    //         if (restClient == null) {
    //             OkHttpClient.Builder clientBuilder = configuration.clientBuilder;
    //             if (clientBuilder == null) {
    //                 clientBuilder = new OkHttpClient.Builder();
    //             }
    //             //add it as the first interceptor
    //             clientBuilder.interceptors().add(0, new Interceptor() {
    //                 @Override
    //                 public Response intercept(Chain chain) throws IOException {
    //                     Request request = chain.request();
    //                     Headers.Builder headersBuilder = request.headers().newBuilder()
    //                             .set(WiaRESTCommand.HEADER_APPLICATION_ID, configuration.applicationId)
    //                             .set(WiaRESTCommand.HEADER_CLIENT_VERSION, Wia.externalVersionName())
    //                             .set(WiaRESTCommand.HEADER_APP_BUILD_VERSION,
    //                                     String.valueOf(ManifestInfo.getVersionCode()))
    //                             .set(WiaRESTCommand.HEADER_APP_DISPLAY_VERSION,
    //                                     ManifestInfo.getVersionName())
    //                             .set(WiaRESTCommand.HEADER_OS_VERSION, Build.VERSION.RELEASE)
    //                             .set(WiaRESTCommand.USER_AGENT, userAgent());
    //                     if (request.header(WiaRESTCommand.HEADER_INSTALLATION_ID) == null) {
    //                         // We can do this synchronously since the caller is already on a background thread
    //                         headersBuilder.set(WiaRESTCommand.HEADER_INSTALLATION_ID, installationId().get());
    //                     }
    //                     // client key can be null with self-hosted Wia Server
    //                     if (configuration.clientKey != null) {
    //                         headersBuilder.set(WiaRESTCommand.HEADER_CLIENT_KEY, configuration.clientKey);
    //                     }
    //                     request = request.newBuilder()
    //                             .headers(headersBuilder.build())
    //                             .build();
    //                     return chain.proceed(request);
    //                 }
    //             });
    //             restClient = WiaHttpClient.createClient(clientBuilder);
    //         }
    //         return restClient;
    //     }
    // }

    // TODO(grantland): Pass through some system values.
    String userAgent() {
        return "Wia Android SDK";
    }

    static class Android extends WiaPlugins {

        static void initialize(Context context, Wia.Configuration configuration) {
            WiaPlugins.set(new Android(context, configuration));
        }

        static WiaPlugins.Android get() {
            return (WiaPlugins.Android) WiaPlugins.get();
        }

        private final Context applicationContext;

        private Android(Context context, Wia.Configuration configuration) {
            super(configuration);
            applicationContext = context.getApplicationContext();
        }

        Context applicationContext() {
            return applicationContext;
        }

        @Override
        String userAgent() {
            String packageVersion = "unknown";
            try {
                String packageName = applicationContext.getPackageName();
                int versionCode = applicationContext
                        .getPackageManager()
                        .getPackageInfo(packageName, 0)
                        .versionCode;
                packageVersion = packageName + "/" + versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                // Should never happen.
            }
            // return "Wia Android SDK " + WiaObject.VERSION_NAME + " (" + packageVersion +
            //         ") API Level " + Build.VERSION.SDK_INT;
            return "Wia Android SDK (" + packageVersion +
                    ") API Level " + Build.VERSION.SDK_INT;
        }
    }

}
