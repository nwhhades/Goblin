# 随便混混
-keep class com.whiner.**{*;}
-dontwarn com.whiner.**


# OkHttp3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** {*;}
-keep interface okhttp3.** {*;}
-dontwarn okhttp3.**
-dontwarn okio.**


#Eventbus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode {*;}
-keepclassmembers class org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keep class org.greenrobot.eventbus.android.AndroidComponentsImpl


# Gson
-keepattributes Signature
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken


# 下载组件
-keep class com.liulishuo.okdownload.**{*;}
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings
