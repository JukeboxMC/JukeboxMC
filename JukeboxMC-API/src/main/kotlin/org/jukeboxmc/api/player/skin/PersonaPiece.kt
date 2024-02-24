package org.jukeboxmc.api.player.skin

/**
 * Represents a persona piece of a persona skin
 */
class PersonaPiece(
    val pieceId: String,
    val pieceType: String,
    val packId: String,
    val productId: String,
    val isDefault: Boolean
) {
    override fun toString(): String {
        return "PersonaPiece(pieceId='$pieceId', pieceType='$pieceType', packId='$packId', productId='$productId', isDefault=$isDefault)"
    }
}

