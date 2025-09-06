plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "core"
version = "1.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven("https://jitpack.io") // для java-object-diff
}

dependencies {
	
    // Hibernate core
	implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")

    // Flywaydb + PostgreSQL
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core:9.22.3")
    implementation("org.postgresql:postgresql")

	// JPA API
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    
	implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.18.2")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.18.2")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("de.danielbechler:java-object-diff:0.93.1")
	implementation("org.springframework.boot:spring-boot-starter")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
}