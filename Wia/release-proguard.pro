-keepnames class io.wia.** { *; }

# Required for Wia
-keepattributes *Annotation*
-keepattributes Signature
# https://github.com/square/okio#proguard
-dontwarn okio.**
