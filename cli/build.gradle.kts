import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.compile.JavaCompile

plugins {
    kotlin("jvm")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
}

dependencies {
    implementation(kotlin("stdlib"))
    // Test dependencies
    testImplementation(kotlin("test")) // kotlin.test interop (maps to JUnit)
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

// Use JUnit 5 platform for tests
tasks.test {
    useJUnitPlatform()
}
