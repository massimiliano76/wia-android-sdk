package io.wia;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

class WiaHttpInterceptor implements Interceptor {
  @Override public Response intercept(Interceptor.Chain chain) throws IOException {
    Request request = chain.request();
    Request newRequest;

    Map<String, String> headersMap = new HashMap<String, String>();
    if (WiaPlugins.get().accessToken() != null) {
      headersMap.put("Authorization", String.format("Bearer %s", WiaPlugins.get().accessToken()));
    }

    if (WiaPlugins.get().clientKey() != null) {
      headersMap.put("x-client-key", WiaPlugins.get().clientKey());
    }

    Headers headers = Headers.of(headersMap);

    newRequest = request.newBuilder()
            .headers(headers)
            .build();

    return chain.proceed(newRequest);

    // Request request = chain.request();
    //
    // long t1 = System.nanoTime();
    // logger.info(String.format("Sending request %s on %s%n%s",
    //     request.url(), chain.connection(), request.headers()));
    //
    // Response response = chain.proceed(request);
    //
    // long t2 = System.nanoTime();
    // logger.info(String.format("Received response for %s in %.1fms%n%s",
    //     response.request().url(), (t2 - t1) / 1e6d, response.headers()));
    //
    // return response;
  }
}
