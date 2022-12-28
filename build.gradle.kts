import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("co.uzzu.dotenv.gradle") version "2.0.0"
    id("maven-publish")
}

group = "com.danvhae.minecraft.siege"
version = "0.3.0-a1"

repositories {
    mavenCentral()

    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")

    compileOnly(files(env.fetch("WORLD_EDIT_FILE")))
    compileOnly(files(env.fetch("WORLD_GUARD_FILE")))
    compileOnly(files(env.fetch("DP_CORE_FILE")))
    compileOnly(files(env.fetch("DP_RPG_FILE")))


}


tasks.withType<ProcessResources>{
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml"){
        expand(project.properties)
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing{

    publications{
        create<MavenPublication>("maven"){
            groupId = group.toString()
            artifactId = "SiegeCore"
            version = project.version.toString()

        }
    }

    repositories{
        maven{
            name ="GitHubPackages"
            url = uri("https://maven.pkg.github.com/romeo-rkpk/DVHSiegeCore")
            credentials{
                username = env.fetch("GITHUB_NAME")
                password = env.fetch("GITHUB_TOKEN")
            }

        }
    }
}