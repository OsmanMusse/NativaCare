import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
    id("app.cash.sqldelight").version("2.0.1")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
            export("dev.icerock.moko:resources:0.23.0")
            export("dev.icerock.moko:graphics:0.9.0")
        }
    }
    
    sourceSets {

        androidMain.dependencies {

        }
        val androidMain by getting {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
                implementation("app.cash.sqldelight:android-driver:2.0.1")
                implementation(libs.ktor.client.okhttp)
                implementation ("com.github.commandiron:WheelPickerCompose:1.1.11")
            }
        }


        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            val voyagerVersion = "1.0.0-rc10"

            // Navigator
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

            // Transitions
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

            api("dev.icerock.moko:resources:0.23.0")
            api("dev.icerock.moko:resources-compose:0.23.0")

            // For Adaptive UI components
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.material)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.decompose)
            implementation(libs.kotlinx.serialization.json)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            // For parsing HTML
            implementation("com.fleeksoft.ksoup:ksoup:0.1.0")

            // Media Loading Library
            implementation("media.kamel:kamel-image:0.9.1")

            implementation("io.github.epicarchitect:calendar-compose-basis:1.0.5")
            implementation("io.github.epicarchitect:calendar-compose-ranges:1.0.5") // includes basis
            implementation("io.github.epicarchitect:calendar-compose-pager:1.0.5") // includes basis
            implementation("io.github.epicarchitect:calendar-compose-datepicker:1.0.5")

            // SQL DELIGHT
            implementation("app.cash.sqldelight:runtime:2.0.1")

            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")

            // only ViewModel, EventsDispatcher, Dispatchers.UI
            api("dev.icerock.moko:mvvm-flow:0.16.1")
            api("dev.icerock.moko:mvvm-flow-compose:0.16.1")
            api("dev.icerock.moko:mvvm-core:0.16.1")
            api("dev.icerock.moko:mvvm-compose:0.16.1")
        }


        val iosX64Main by getting {
            resources.srcDirs("build/generated/moko/iosX64Main/src")
        }
        val iosArm64Main by getting {
            resources.srcDirs("build/generated/moko/iosArm64Main/src")
        }
        val iosSimulatorArm64Main by getting {
            resources.srcDirs("build/generated/moko/iosSimulatorArm64Main/src")
        }

        val iosMain by creating {
            dependsOn(commonMain.get())
            this.dependencies {
                implementation(libs.ktor.client.darwin)
                implementation("app.cash.sqldelight:native-driver:2.0.1")
            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "org.example.nativacare"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.nativacare"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("org.example.nativacare")
            generateAsync.set(true)
        }
    }

    linkSqlite.set(true)
}

multiplatformResources {
    multiplatformResourcesPackage = "org.example.nativacare" // required
    multiplatformResourcesClassName = "MR"
}

