package org.jukeboxmc.api.player.skin

/**
 * Represents a persona piece tint of a persona skin
 */
class PersonaPieceTint(
    val pieceType: String,
    val colors: List<String>
) {
    override fun toString(): String {
        return "PersonaPieceTint(pieceType='$pieceType', colors=$colors)"
    }
}