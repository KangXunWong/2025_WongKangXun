version = "1.0.1" // Replace with your desired version

plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(libs.guava)
    implementation(libs.slf4j)
    implementation(libs.logback)
    implementation(libs.dropwizard)
    implementation(libs.dropwizardLogging)
    implementation(libs.dropwizardJetty)
    implementation(libs.jakartaValidation)
    implementation(libs.javaxValidation)

    testImplementation(libs.junitJupiterApi)
    testRuntimeOnly(libs.junitJupiterEngine)
    testImplementation(libs.mockitoCore)
    testImplementation(libs.mockitoJUnitJupiter)
    testImplementation(libs.dropwizardTesting)
    testImplementation(libs.wiremock)
}

sourceSets {
    main {
        java {
            srcDirs("coinchange-backend/src/main/java")
        }
        resources {
            srcDirs("coinchange-backend/resources")
        }
    }
    test {
        java {
            srcDirs("coinchange-backend/src/test/java")
        }
        resources {
            srcDirs("coinchange-backend/resources")
        }
    }
}

application {
    mainClass.set("coinchange.CoinChangeApplication")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks {
    shadowJar {
        archiveBaseName.set("coinchange") // Replace with your project name
        archiveVersion.set(project.version.toString())  // Set version based on project version
        mergeServiceFiles()
        manifest {
            attributes["Main-Class"] = "coinchange.CoinChangeApplication"
        }
    }

    build {
        dependsOn(shadowJar)
    }
}