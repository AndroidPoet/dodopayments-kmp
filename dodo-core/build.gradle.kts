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
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "io.github.androidpoet.dodopayments.core"
    compileSdk = Configuration.COMPILE_SDK
    defaultConfig {
        minSdk = Configuration.MIN_SDK
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()
    coordinates(
        groupId = Configuration.GROUP,
        artifactId = "dodo-core",
        version = Configuration.VERSION,
    )
    pom {
        name.set("dodo-core")
        description.set("Core types for Dodo Payments KMP SDK")
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
