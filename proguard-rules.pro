# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html
#-dontskipnonpubliclibraryclasses
-dontobfuscate
-forceprocessing


 -dontwarn okhttp3.**
 -dontwarn okio.**
 -dontwarn com.squareup.**
-dontwarn com.google.**

-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }
-keep class com.wooplr.spotlight.** { *; }
-keep interface com.wooplr.spotlight.**
-keep enum com.wooplr.spotlight.**

-keep class com.arthenica.mobileffmpeg.Config {
    native <methods>;
    void log(long, int, byte[]);
    void statistics(long, int, float, float, long , int, double, double);
}

-keep class com.arthenica.mobileffmpeg.AbiDetect {
    native <methods>;
}

-verbose
-repackageclasses ''
-renamesourcefileattribute
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
## Below are the suggested rules from the developer documentation:
## https://developers.optimizely.com/x/solutions/sdks/reference/index.html?language=android&platform=mobile#installation

# Optimizely