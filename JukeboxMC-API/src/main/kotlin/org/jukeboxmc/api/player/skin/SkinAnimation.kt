package org.jukeboxmc.api.player.skin

/**
 * Represents a skin animation e.g. a twinkle in the eye
 */
class SkinAnimation(
    val skinImage: SkinImage,
    val type: Int,
    val frames: Float,
    val expression: Int
) {
    override fun toString(): String {
        return "SkinAnimation(skinImage=$skinImage, type=$type, frames=$frames, expression=$expression)"
    }
}