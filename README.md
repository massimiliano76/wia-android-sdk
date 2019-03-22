Wia Android SDK
===============

[![Build Status](https://travis-ci.org/wiaio/wia-android-sdk.svg)](https://travis-ci.org/wiaio/wia-android-sdk/builds#)

Official Android SDK for [Wia's][1] API.

[Wia][1] provides a cloud infrastructure for developers building Internet of Things applications. Start conversations with your things using just a few lines of code.

Setup
=====

Grab via Maven:
```xml
<dependency>
  <groupId>io.wia</groupId>
  <artifactId>wia-android-sdk</artifactId>
  <version>0.4.1</version>
</dependency>
```
or Gradle:
```groovy
compile 'io.wia:wia-android-sdk:0.4.1'
```

The SDK requires at minimum Java 6 or Android 2.3.

Usage
=====

This manages all your interaction with the Wia API.
```java
Wia.initialize(new Wia.Configuration.Builder(activity.getApplicationContext())
  .appKey("YOUR_APP_KEY")
  .build()
);
```

To retrieve a list of Spaces.
```java
Observable<WiaSpaceList> result = Wia.listSpaces();
result.subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(response -> {
        // Spaces: response.spaces()
        // Count: response.count
      }, error -> {
        // Do something with error
      });
```

Documentation
=============

For further information, check out our official [API documentation][2].

License
=======

Copyright (c) 2017-present, Wia Technologies Limited.
All rights reserved.

This source code is licensed under the MIT-style license found in the
LICENSE file in the root directory of this source tree.

[1]: https://www.wia.io
[2]: http://docs.wia.io
