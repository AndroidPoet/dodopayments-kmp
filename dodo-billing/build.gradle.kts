import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vanniktech.publish)
}

kotlin {
    explicitApi()
    androidTarget { publishLibraryVariants("release") }
    jvm()
    iosX64(); iosArm64(); iosSimulatorArm64()
    macosX64(); macosArm64()
    linuxX64(); mingwX64()
    wasmJs { browser() }
    jvmToolchain(17)
    sourceSets {
        commonMain.dependencies {
            api(project(":dodo-client"))
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "io.github.androidpoet.dodopayments.billing"
    compileSdk = Configuration.COMPILE_SDK
    defaultConfig { minSdk = Configuration.MIN_SDK }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()
    coordinates(groupId = Configuration.GROUP, artifactId = "dodo-billing", version = Configuration.VERSION)
    pom {
        name.set("dodo-billing")
        description.set("Billing (refunds, discounts) module for Dodo Payments KMP SDK")
        inceptionYear.set("2025")
        url.set("https://github.com/AndroidPoet/dodopayments-kmp")
        licenses { license { name.set("MIT License"); url.set("https://opensource.org/licenses/MIT") } }
        developers { developer { id.set("androidpoet"); name.set("Ranbir Singh"); url.set("https://github.com/AndroidPoet") } }
        scm { url.set("https://github.com/AndroidPoet/dodopayments-kmp") }
    }
}
