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
    namespace = "io.github.androidpoet.dodopayments.products"
    compileSdk = Configuration.COMPILE_SDK
    defaultConfig { minSdk = Configuration.MIN_SDK }
}

mavenPublishing {
    coordinates(Configuration.GROUP, "dodo-products", Configuration.VERSION)
    pom {
        name.set("dodo-products")
        description.set("Products module for Dodo Payments KMP SDK")
    }
}
