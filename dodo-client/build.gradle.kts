import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vanniktech.publish)
}

kotlin {
    explicitApi()

    androidTarget {
        publishLibraryVariants("release")
    }
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
            api(project(":dodo-core"))
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        appleMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
        }
        linuxX64Main.dependencies {
            implementation(libs.ktor.client.cio)
        }
        mingwX64Main.dependencies {
            implementation(libs.ktor.client.cio)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

android {
    namespace = "io.github.androidpoet.dodopayments.client"
    compileSdk = Configuration.COMPILE_SDK
    defaultConfig {
        minSdk = Configuration.MIN_SDK
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.DEFAULT, automaticRelease = true)
    signAllPublications()
    coordinates(
        groupId = Configuration.GROUP,
        artifactId = "dodo-client",
        version = Configuration.VERSION,
    )
    pom {
        name.set("dodo-client")
        description.set("HTTP client for Dodo Payments KMP SDK")
        inceptionYear.set("2025")
        url.set("https://github.com/AndroidPoet/dodopayments-kmp")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("androidpoet")
                name.set("Ranbir Singh")
                url.set("https://github.com/AndroidPoet")
            }
        }
        scm {
            url.set("https://github.com/AndroidPoet/dodopayments-kmp")
            connection.set("scm:git:git://github.com/AndroidPoet/dodopayments-kmp.git")
            developerConnection.set("scm:git:ssh://git@github.com/AndroidPoet/dodopayments-kmp.git")
        }
    }
}
