plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "uz.futuresoft.searchfind"
    compileSdk = 34

    defaultConfig {
        applicationId = "uz.futuresoft.searchfind"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Material design
    implementation("com.google.android.material:material:1.12.0")

    // Constraint layout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Firebase Auth
    implementation("com.google.firebase:firebase-auth:23.0.0")

    // Firebase realtime database
    implementation("com.google.firebase:firebase-database:21.0.0")

    // Firebase cloud firestore
    implementation("com.google.firebase:firebase-firestore:25.0.0")

    // Firebase storage
    implementation("com.google.firebase:firebase-storage:21.0.0")

    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    implementation("androidx.fragment:fragment-ktx:1.7.1")
    ksp("com.google.dagger:hilt-android-compiler:2.50")

    // Circle indicator
    implementation("me.relex:circleindicator:2.1.6")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    //noinspection KaptUsageInsteadOfKsp
    ksp("androidx.room:room-compiler:2.6.1")

    // Paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.3.0")

    // Gson
    implementation("com.google.code.gson:gson:2.11.0")

    // Lottie
    implementation("com.airbnb.android:lottie:6.0.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Masked Edit Text
    implementation("io.github.vicmikhailau:MaskedEditText:5.0.2")

    // OTP view
    implementation("com.github.fraggjkee:sms-confirmation-view:1.8.1")

    //Hawk
    implementation("com.orhanobut:hawk:2.0.1")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}