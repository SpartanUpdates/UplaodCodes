# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class bitter.jnibridge.* { *; }
-keep class com.unity3d.player.* { *; }
-keep class org.fmod.* { *; }

-keep class okhttp3.**{ *; }
-dontwarn okhttp3.**

-keep class okio.**{ *; }
-dontwarn okio.**

-keep class com.squareup.**
-dontwarn com.squareup.**

-keep class com.liulishuo.** { *; }
-dontwarn com.liulishuo.**

-keep class com.intel.inde.mp.** { *; }
-dontwarn com.intel.inde.mp.**

-keep class com.yasirkula.unity.** { *; }
-dontwarn com.yasirkula.unity.**

-dontwarn de.hdodenhof.circleimageview.**

-keep class com.root.unity.Capturing { *; }

-keep class com.root.unity.AndroidUnityCall { *; }

-keep public class com.google.android.gms.* { public *; }
-keep class com.google.firebase.** { *; }

# Retrofit
-keep class com.google.gson.** { *; }
-keep public class com.google.gson.** {public private protected *;}

-keep class com.google.inject.** { *; }

-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }

-keep class javax.inject.** { *; }
-keep class javax.xml.stream.** { *; }

-keep class retrofit.** { *; }
-keep class com.google.appengine.** { *; }
-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.squareup.okhttp.*
-dontwarn rx.**
-dontwarn javax.xml.stream.**
-dontwarn com.google.appengine.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.**

-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations
-keepattributes EnclosingMethod
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}
-verbose
-renamesourcefileattribute SourceFile
-repackageclasses ""
-keepattributes SourceFile,LineNumberTable
-optimizations !code/simplification/arithmatic,!field/*,!class/merging/*
