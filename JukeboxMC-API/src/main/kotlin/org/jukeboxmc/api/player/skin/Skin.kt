package org.jukeboxmc.api.player.skin

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*
import javax.imageio.ImageIO

/**
 * The player's skin and all related information
 */
class Skin {

    private var skinId: String? = null
    private var resourcePatch: String? = legacyGeometryName("geometry.humanoid.custom")
    private var geometryName = ""
    private var geometryData: String? = ""
    private var animationData: String? = ""
    private var capeId: String? = ""
    private var fullSkinId = UUID.randomUUID().toString()
    private var skinColor = "#0"
    private var armSize = "wide"
    private var playFabId = ""

    private var skinData = SkinImage(0, 0, ByteArray(0))
    private var capeData: SkinImage = SkinImage(0, 0, ByteArray(0))

    private var isPremium = false
    private var isPersona = false
    private var isCapeOnClassic = false
    private var isTrusted = false
    private var isPrimaryUser = true

    private var skinAnimations: MutableList<SkinAnimation> = ArrayList()
    private var personaPieces: MutableList<PersonaPiece> = ArrayList()
    private var personaPieceTints: MutableList<PersonaPieceTint> = ArrayList()

    companion object {
        const val SINGLE_SKIN_SIZE = 8192
        const val DOUBLE_SKIN_SIZE = 16384
        const val SKIN_128_64_SIZE = 32768
        const val SKIN_128_128_SIZE = 65536

        fun legacyGeometryName(geometryName: String): String {
            return "{\"geometry\" : {\"default\" : \"$geometryName\"}}"
        }

        fun parseBufferedImage(image: BufferedImage): SkinImage {
            ByteArrayOutputStream().use { outputStream ->
                for (y in 0 until image.height) {
                    for (x in 0 until image.width) {
                        val color = Color(image.getRGB(x, y), true)
                        outputStream.write(color.red)
                        outputStream.write(color.green)
                        outputStream.write(color.blue)
                        outputStream.write(color.alpha)
                    }
                }
                image.flush()
                return SkinImage(image.width, image.height, outputStream.toByteArray())
            }
        }

        fun fromInputStream(inputStream: InputStream?): Skin {
            val skin = Skin()
            skin.setTrusted(true)
            var skinData: BufferedImage? = null
            try {
                skinData = ImageIO.read(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (skinData != null) {
                skin.setSkinData(skinData)
            }
            return skin
        }

        fun fromFile(file: File?): Skin {
            val skin = Skin()
            skin.setTrusted(true)
            var skinData: BufferedImage? = null
            try {
                skinData = ImageIO.read(file)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (skinData != null) {
                skin.setSkinData(skinData)
            }
            return skin
        }
    }

    fun getSkinId(): String? {
        return skinId
    }

    fun setSkinId(skinId: String?) {
        this.skinId = skinId
    }

    fun getResourcePatch(): String? {
        return if (resourcePatch == null) "" else resourcePatch
    }

    fun setResourcePatch(resourcePatch: String?) {
        this.resourcePatch = resourcePatch
    }

    fun getGeometryName(): String {
        return geometryName
    }

    fun setGeometryName(geometryName: String) {
        this.geometryName = geometryName
    }

    fun getGeometryData(): String? {
        return if (geometryData == null) "" else geometryData
    }

    fun setGeometryData(geometryData: String?) {
        this.geometryData = geometryData
    }

    fun getAnimationData(): String? {
        return if (animationData == null) "" else animationData
    }

    fun setAnimationData(animationData: String?) {
        this.animationData = animationData
    }

    fun getCapeId(): String? {
        return if (capeId == null) "" else capeId
    }

    fun setCapeId(capeId: String?) {
        this.capeId = capeId
    }

    fun getSkinColor(): String {
        return skinColor
    }

    fun setSkinColor(skinColor: String) {
        this.skinColor = skinColor
    }

    fun getArmSize(): String {
        return armSize
    }

    fun setArmSize(armSize: String) {
        this.armSize = armSize
    }

    fun getPlayFabId(): String {
        return playFabId
    }

    fun setPlayFabId(playFabId: String) {
        this.playFabId = playFabId
    }

    fun getFullSkinId(): String {
        return fullSkinId
    }

    fun setFullSkinId(fullSkinId: String) {
        this.fullSkinId = fullSkinId
    }

    fun getSkinData(): SkinImage {
        return skinData
    }

    fun setSkinData(skinData: SkinImage) {
        this.skinData = skinData
    }

    fun setSkinData(bufferedImage: BufferedImage) {
        skinData = parseBufferedImage(bufferedImage)
    }

    fun getCapeData(): SkinImage {
        return capeData
    }

    fun setCapeData(capeData: SkinImage) {
        this.capeData = capeData
    }

    fun isPremium(): Boolean {
        return isPremium
    }

    fun setPremium(premium: Boolean) {
        isPremium = premium
    }

    fun isPersona(): Boolean {
        return isPersona
    }

    fun setPersona(persona: Boolean) {
        isPersona = persona
    }

    fun isCapeOnClassic(): Boolean {
        return isCapeOnClassic
    }

    fun setCapeOnClassic(capeOnClassic: Boolean) {
        isCapeOnClassic = capeOnClassic
    }

    fun isTrusted(): Boolean {
        return isTrusted
    }

    fun setTrusted(trusted: Boolean) {
        isTrusted = trusted
    }

    fun isPrimaryUser(): Boolean {
        return isPrimaryUser
    }

    fun setPrimaryUser(primaryUser: Boolean) {
        isPrimaryUser = primaryUser
    }

    fun getSkinAnimations(): MutableList<SkinAnimation> {
        return skinAnimations
    }

    fun setSkinAnimations(skinAnimations: MutableList<SkinAnimation>) {
        this.skinAnimations = skinAnimations
    }

    fun getPersonaPieces(): MutableList<PersonaPiece> {
        return personaPieces
    }

    fun setPersonaPieces(personaPieces: MutableList<PersonaPiece>) {
        this.personaPieces = personaPieces
    }

    fun getPersonaPieceTints(): MutableList<PersonaPieceTint> {
        return personaPieceTints
    }

    fun setPersonaPieceTints(personaPieceTints: MutableList<PersonaPieceTint>) {
        this.personaPieceTints = personaPieceTints
    }

    fun generateSkinId(name: String): String {
        val data: ByteArray = appendBytes(skinData.data, resourcePatch!!.toByteArray(StandardCharsets.UTF_8))
        return UUID.nameUUIDFromBytes(data).toString() + "." + name
    }

    private fun appendBytes(bytes1: ByteArray, vararg bytes2: ByteArray): ByteArray {
        var length = bytes1.size
        for (bytes in bytes2) {
            length += bytes.size
        }
        val appendedBytes = ByteArray(length)
        System.arraycopy(bytes1, 0, appendedBytes, 0, bytes1.size)
        var index = bytes1.size
        for (b in bytes2) {
            System.arraycopy(b, 0, appendedBytes, index, b.size)
            index += b.size
        }
        return appendedBytes
    }

    override fun toString(): String {
        return "Skin(skinId=$skinId, resourcePatch=$resourcePatch, geometryName='$geometryName', geometryData=$geometryData, animationData=$animationData, capeId=$capeId, fullSkinId='$fullSkinId', skinColor='$skinColor', armSize='$armSize', playFabId='$playFabId', skinData=$skinData, capeData=$capeData, isPremium=$isPremium, isPersona=$isPersona, isCapeOnClassic=$isCapeOnClassic, isTrusted=$isTrusted, isPrimaryUser=$isPrimaryUser, skinAnimations=$skinAnimations, personaPieces=$personaPieces, personaPieceTints=$personaPieceTints)"
    }


}