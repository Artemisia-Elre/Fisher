import net.minecraftforge.gradle.userdev.UserDevExtension
import org.spongepowered.asm.gradle.plugins.MixinExtension
import java.net.URI

plugins{
    id("net.minecraftforge.gradle") version ("6.0.25")
    id("org.spongepowered.mixin") version ("0.7+")
    id("idea")
}

apply(plugin = "org.spongepowered.mixin")

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

group = mod_group
version = mod_version

base{
    archivesName = mod_name
}

repositories{
    maven {
        name = "Iron`s Maven - Release"
        url = URI("https://code.redspace.io/releases")
    }
    maven {
        name = "Iron's Maven - Snapshots"
        url = URI("https://code.redspace.io/snapshots")
    }
    maven ("https://maven.enginehub.org/repo/" )
    maven ("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/" )
    maven ("https://maven.theillusivec4.top" )
    maven ("https://cursemaven.com" )
    maven ("https://maven.blamejared.com" )
    maven ("https://maven.kosmx.dev/")
}

dependencies{
    minecraft()
    mixinProcessor()
    gson("2.11.0")
}

minecraft {
    mappings(mapping_channel, mapping_version)
    runs {
        create("client") {
            workingDirectory(project.file("run"))
            property("forge.logging.markers", "SCAN,LOADING,CORE")
            property("forge.logging.console.level", "debug")
        }
        create("server") {
            workingDirectory(project.file("run/server"))
            property("forge.logging.markers", "SCAN,LOADING,CORE")
            property("forge.logging.console.level", "debug")
        }
    }
}

mixin {
    add(sourceSets.main.get(), "mixins.$mod_name.refmap.json")
    config("mixins.$mod_name.json")
}

java{
    withSourcesJar()
}



tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

fun minecraft(block: UserDevExtension.() -> Unit){
    extensions.getByType(UserDevExtension::class).apply(block)
}

fun mixin(block: MixinExtension.() -> Unit){
    extensions.getByType(MixinExtension::class).apply(block)
}

