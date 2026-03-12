plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.12.0"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdea(providers.gradleProperty("platformVersion"))
    }
}

intellijPlatform {
    buildSearchableOptions = false

    pluginConfiguration {
        id.set(providers.gradleProperty("pluginGroup"))
        name.set(providers.gradleProperty("pluginName"))
        version.set(providers.gradleProperty("pluginVersion"))

        ideaVersion {
            sinceBuild.set(providers.gradleProperty("pluginSinceBuild"))
            untilBuild.set(provider { null })
        }
    }

    publishing {
        token.set(providers.environmentVariable("PUBLISH_TOKEN"))
    }
}

tasks {
    runIde {
        jvmArgumentProviders += CommandLineArgumentProvider {
            listOf("-Dsun.java2d.uiScale=1.5")
        }
    }

    patchPluginXml {
        sinceBuild.set(providers.gradleProperty("pluginSinceBuild"))
        untilBuild.set(provider { null })
    }

    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }
}
