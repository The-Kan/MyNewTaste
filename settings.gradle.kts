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

rootProject.name = "MyNewsTaste"
include(":app")
include(":feature:settings")
include(":feature:articledetail")
include(":core:local")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:common")
include(":core:di")
include(":core:ui")
include(":feature:allcategoryarticles")
include(":feature:bookmarkarticles")
include(":feature:categoryarticles")
include(":feature:tastearticles")
