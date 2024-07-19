import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Applies the Minecraft Maven dependencies to the DependencyHandler.
 *
 * This includes setting up the Forge dependency with specific versions.
 *
 * If you want to set the minecraft apply version:
 * @see minecraft_version
 * @see forge_version
 * @author Artemisia
 *
 */
fun DependencyHandler.minecraft() {
    add("minecraft", "net.minecraftforge:forge:${minecraft_version}-${forge_version}")
}


/**
 * Applies the Mixin processor dependency to the DependencyHandler.
 *
 * If you want to set the mixin version:
 * @see mixin_version
 */
fun DependencyHandler.mixinProcessor() {
    add("annotationProcessor", "org.spongepowered:mixin:${mixin_version}:processor")
}

fun DependencyHandler.gson(version:String){
    add("implementation", "com.google.code.gson:gson:${version}")
}
