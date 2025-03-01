plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")  // Añade este plugin
}

android {
    namespace = "com.example.examen2evaluacion"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.examen2evaluacion"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true   //Además, hay que darle a sincronizar
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //Picasso
    implementation (libs.picasso)

    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)  // Usa kapt en lugar de annotationProcessor
    implementation(libs.androidx.room.ktx)  // Para soporte de corrutinas

    // Corrutinas
    implementation(libs.kotlinx.coroutines.core)
    //noinspection GradleDependency
    implementation(libs.kotlinx.coroutines.android)



}