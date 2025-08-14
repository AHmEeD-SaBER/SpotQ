pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SpotQ"
include(":app")
include(":splash")
include(":core-ui")
include(":onboarding")
include(":authentication")
include(":authentication:domain")
include(":authentication:data")
include(":authentication:ui")
include(":location-provider")
include(":core-data")
include(":places")
include(":places:data")
include(":places:domain")
include(":places:ui")
include(":errors")
include(":favorites")
include(":favorites:data")
include(":favorites:domain")
include(":favorites:ui")
