plugins {
    id("com.android.application")
}

android {
    namespace = "com.erykjuraszczyk.steamandroidhub"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.erykjuraszczyk.steamandroidhub"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}
