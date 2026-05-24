rootProject.name = "dodopayments-kmp"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":dodo-core")
include(":dodo-client")
include(":dodo-payments")
include(":dodo-subscriptions")
include(":dodo-customers")
include(":dodo-products")
include(":dodo-billing")
include(":dodo-sdk")
