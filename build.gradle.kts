plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "7.0.2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

spotless {
    java {
        removeUnusedImports()
        importOrder()
        googleJavaFormat().aosp()
    }

    kotlinGradle {
        target("**/*.gradle.kts", "*.gradle.kts")

        ktlint()
        trimTrailingWhitespace()
        endWithNewline()
    }

    val prettierConfig by extra("$rootDir/.prettierrc.yml")

    format("markdown") {
        target("**/*.md", "*.md")

        prettier().configFile(prettierConfig)
    }

    format("yaml") {
        target("*.yml", "src/main/resources/*.yml")

        prettier().configFile(prettierConfig)
    }
}

repositories {
    mavenCentral()
}

val springCloudVersion = "2024.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion"))
    implementation("io.micrometer:micrometer-registry-prometheus:1.15.0-M2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.6.2")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("lintCheck") {
    dependsOn("spotlessCheck")

    doLast {
        println("\u001B[32m✔ Lint check completed successfully!\u001B[0m")
    }
}

tasks.register("lintApply") {
    dependsOn("spotlessApply")
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    archiveFileName.set("order-service.jar")
}
