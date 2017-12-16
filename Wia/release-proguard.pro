-keepnames class io.wia.** { *; }

# Required for Wia
-keepattributes *Annotation*
-keepattributes Signature
# https://github.com/square/okio#proguard
-dontwarn okio.**

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
