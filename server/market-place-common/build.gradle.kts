plugins {
    `java-library`
    `maven-publish`
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.marketplace"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.2")
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-validation")
    api("com.fasterxml.jackson.core:jackson-databind")
    
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            
            groupId = "com.marketplace"
            artifactId = "market-place-common"
            version = project.version.toString()
            
            pom {
                name.set("Market Place Common")
                description.set("Common library for Market Place microservices")
                url.set("https://github.com/KrongDev/market-place")
                
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                
                developers {
                    developer {
                        id.set("krongdev")
                        name.set("KrongDev")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/KrongDev/market-place.git")
                    developerConnection.set("scm:git:ssh://github.com/KrongDev/market-place.git")
                    url.set("https://github.com/KrongDev/market-place")
                }
            }
        }
    }
    
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/KrongDev/market-place")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
