import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "com.theapache64"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // JSON In Java : JSON is a light-weight, language independent, data interchange format.See http://www.JSON.org/The
	// files in this package implement JSON encoders/decoders in Java.It also includes
	// the capability to convert between JSON and XML, HTTPheaders, Cookies, and CDL.This
	// is a reference implementation. There is a large number of JSON packagesin Java.
	// Perhaps someday the Java community will standardize on one. Untilthen, choose carefully.
    implementation("org.json:json:20220924")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}