import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.vanniktech.publish)
}

kotlin {
    explicitApi()

    androidTarget { publishLibraryVariants("release") }
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    linuxX64()
    mingwX64()
    wasmJs { browser() }

    jvmToolchain(17)

    sourceSets {
        commonMain.dependencies {
            api(project(":dodo-payments"))
            api(project(":dodo-subscriptions"))
            api(project(":dodo-customers"))
            api(project(":dodo-products"))
            api(project(":dodo-billing"))
            // Koin is an internal impl detail — not api()
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "io.github.androidpoet.dodopayments.koin"
    compileSdk = Configuration.COMPILE_SDK
    defaultConfig { minSdk = Configuration.MIN_SDK }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.DEFAULT, automaticRelease = true)
    signAllPublications()
    coordinates(groupId = Configuration.GROUP, artifactId = "dodo-koin", version = Configuration.VERSION)
    pom {
        name.set("dodo-koin")
        description.set("Optional aggregate facade with isolated Koin DI for Dodo Payments KMP SDK")
        inceptionYear.set("2025")
        url.set("https://github.com/AndroidPoet/dodopayments-kmp")
        licenses { license { name.set("MIT License"); url.set("https://opensource.org/licenses/MIT") } }
        developers { developer { id.set("androidpoet"); name.set("Ranbir Singh"); url.set("https://github.com/AndroidPoet") } }
        scm { url.set("https://github.com/AndroidPoet/dodopayments-kmp") }
    }
}
