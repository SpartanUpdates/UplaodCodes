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

-keep class androidx.palette.** {*;}
-keep class com.squareup.moshi.** {*;}

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

-keep public class com.itextpdf.* { public *; }

-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}

-keep class com.kotlinz.vehiclemanager.rtoownerdetails.rtoowner.model.OwnerInfoModel{ *; }
-keep class com.kotlinz.vehiclemanager.rtoownerdetails.rtoowner.model.OwnerInfoModel$Response { *; }

-keep class com.kotlinz.vehiclemanager.fuel.model.FuelModel { *; }
-keep class com.kotlinz.vehiclemanager.fuel.model.FuelModel$Response { *; }

-keep class androidx.room.** {*;}

-keep class ticker.views.com.ticker.** {*;}

-keep public class com.google.android.gms.* { public *; }
-keep class com.google.firebase.** { *; }

-verbose
-renamesourcefileattribute SourceFile
-repackageclasses ""
-keepattributes SourceFile,LineNumberTable
-optimizations !code/simplification/arithmatic,!field/*,!class/merging/*