plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.4"
}

// 루트 프로젝트 공통 설정
allprojects {
    group = "com.marketplace"
    version = "1.0.0"

    repositories {
        mavenCentral()
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

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "io.spring.dependency-management")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
        withSourcesJar()
        withJavadocJar()
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.2")
        }
    }

    // market-place-common만 GitHub Packages에 배포
    if (project.name == "market-place-common") {
        configure<PublishingExtension> {
            publications {
                create<MavenPublication>("maven") {
                    from(components["java"])

                    pom {
                        name.set(project.name)
                        description.set("Market Place Common Library")
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
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
    }

    tasks.withType<GenerateModuleMetadata> {
        enabled = false
    }
}
