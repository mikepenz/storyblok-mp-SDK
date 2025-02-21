-dontwarn com.aayushatharva.brotli4j.**
-dontwarn org.eclipse.jetty.**
-dontwarn io.javalin.**
-dontwarn org.slf4j.**

-keep class kotlinx.coroutines.swing.SwingDispatcherFactory
-keep class io.ktor.client.engine.cio.CIOEngineContainer
-keep class io.ktor.serialization.kotlinx.json.KotlinxSerializationJsonExtensionProvider


# Most of volatile fields are updated with AtomicFU and should not be mangled/removed
-keepclassmembers class io.ktor.** {
    volatile <fields>;
}
-keepclassmembernames class io.ktor.** {
    volatile <fields>;
}
# client engines are loaded using ServiceLoader so we need to keep them
-keep class io.ktor.client.engine.** implements io.ktor.client.HttpClientEngineContainer
-keep class io.ktor.client.**


# Keep Serializers
-keep,includedescriptorclasses class com.mikepenz.guesses.**$$serializer { *; }
-keepclassmembers class com.mikepenz.guesses.** {
    *** Companion;
}
-keepclasseswithmembers class com.mikepenz.guesses.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class io.github.jan.supabase.**$$serializer { *; }
-keepclassmembers class io.github.jan.supabase.** {
    *** Companion;
}
-keepclasseswithmembers class io.github.jan.supabase.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# When kotlinx.serialization.json.JsonObjectSerializer occurs
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}