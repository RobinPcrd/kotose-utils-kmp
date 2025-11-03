import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import java.util.Properties

plugins {
    id("com.vanniktech.maven.publish")
    signing
}

// Load publish properties (for your project description, etc.)
val publishProperties = Properties().apply {
    load(file("publish.properties").inputStream())
}

// Check if we’re on GitHub Actions
val isGithubActions = System.getenv("GITHUB_ACTIONS") == "true"

version = System.getenv("VERSION") ?: if (isGithubActions)
    error("VERSION must be set for GitHub Actions")
else
    "0.0.0-LOCAL"

// TODO use Dokka and fix this
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    coordinates(project.group as String, project.name, project.version as String)
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = true,
        )
    )

    if (isGithubActions)
        signAllPublications()

    pom {
        name.set(project.name)
        description.set(publishProperties.getProperty("description"))
        url.set("https://github.com/robinpcrd/kotose-utils-kmp")

        licenses {
            license {
                name.set("Apache-2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0")
            }
        }
        developers {
            developer {
                id.set("robinpcrd")
                name.set("Robin Picard")
                email.set("robin.picard.dev@gmail.com")
            }
        }
        scm {
            connection.set("scm:git:https://github.com/robinpcrd/kotose-utils-kmp.git")
            developerConnection.set("scm:git:ssh://github.com/robinpcrd/kotose-utils-kmp.git")
            url.set("https://github.com/robinpcrd/kotose-utils-kmp")
        }
    }
}