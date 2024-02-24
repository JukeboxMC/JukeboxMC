package org.jukeboxmc.api.player.skin

/**
 * The image of the skin
 */
class SkinImage(
    val width: Int,
    val height: Int,
    val data: ByteArray
) {
    override fun toString(): String {
        return "SkinImage(width=$width, height=$height)"
    }

    companion object {
        fun getImage(data: ByteArray): SkinImage? {
            return when (data.size) {
                Skin.SINGLE_SKIN_SIZE -> SkinImage(64, 32, data)
                Skin.DOUBLE_SKIN_SIZE -> SkinImage(64, 64, data)
                Skin.SKIN_128_64_SIZE -> SkinImage(128, 64, data)
                Skin.SKIN_128_128_SIZE -> SkinImage(128, 128, data)
                else -> null
            }
        }
    }
}