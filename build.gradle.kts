plugins {
    kotlin("jvm") version "2.0.21"
    id("com.google.devtools.ksp").version("2.0.21-1.0.25") // Or latest version of KSP
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.squareup.moshi:moshi:1.15.1")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")
    testImplementation("io.mockk:mockk:1.13.13")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks.test {
    useJUnitPlatform()
}
tasks.withType<Test> {
    jvmArgs("--add-opens", "java.base/java.io=ALL-UNNAMED")
}
kotlin {
    jvmToolchain(21)
}