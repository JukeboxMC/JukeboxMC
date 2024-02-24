package org.jukeboxmc.server.extensions

import org.cloudburstmc.math.vector.Vector3d
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import org.cloudburstmc.math.vector.Vector3l
import org.cloudburstmc.protocol.bedrock.data.GameRuleData
import org.cloudburstmc.protocol.bedrock.data.skin.*
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.annotation.Name
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.player.skin.*
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.World
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.api.world.gamerule.GameRuleValue
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.blockentity.JukeboxBlockEntity
import org.jukeboxmc.server.command.CommandData
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import org.jukeboxmc.server.world.chunk.JukeboxChunk

fun Collection<Enchantment>.contentEquals(other: Collection<Enchantment>): Boolean {
    if (this.size != other.size) return false
    return this.zip(other).all { (enchantment1, enchantment2) -> enchantment1 == enchantment2 }
}
private val map: MutableMap<String, CommandData> = mutableMapOf()
fun Command.setCommandData(commandData: CommandData) {
    if (this::class.java.isAnnotationPresent(Name::class.java)) {
        map[this::class.java.getAnnotation(Name::class.java).value] = commandData
    }
}

fun Command.getCommandData(): CommandData? {
    if (this::class.java.isAnnotationPresent(Name::class.java)) {
       return map[this::class.java.getAnnotation(Name::class.java).value]
    }
    return null
}
inline fun <reified T : Enum<T>> Enum<T>.isOneOf(vararg values: T): Boolean {
    return values.contains(this)
}
fun Vector.toVector3f() : Vector3f {
    return Vector3f.from(this.getX(), this.getY(), this.getZ())
}
fun Vector.toVector3d(): Vector3d {
    return Vector3d.from(this.getX(), this.getY(), this.getZ())
}
fun Vector.toVector3l(): Vector3l {
    return Vector3l.from(this.getX().toDouble(), this.getY().toDouble(), this.getZ().toDouble())
}
fun Vector.toVector3i(): Vector3i {
    return Vector3i.from(this.getX().toDouble(), this.getY().toDouble(), this.getZ().toDouble())
}
fun Vector.fromVector3f(vector: Vector3f): Vector {
    return Vector(vector.x, vector.y, vector.z)
}
fun Vector.fromVector3f(vector: Vector3f, dimension: Dimension): Vector {
    return Vector(vector.x, vector.y, vector.z, dimension)
}
fun Vector.fromVector3d(vector: Vector3d): Vector {
    return Vector(vector.x.toFloat(), vector.y.toFloat(), vector.z.toFloat())
}
fun Vector.fromVector3l(vector: Vector3l): Vector {
    return Vector(vector.x.toInt(), vector.y.toInt(), vector.z.toInt())
}
fun Vector.fromVector3i(vector: Vector3i): Vector {
    return Vector(vector.x, vector.y, vector.z)
}
fun Vector.fromVector3i(vector: Vector3i, dimension: Dimension): Vector {
    return Vector(vector.x, vector.y, vector.z, dimension)
}
fun World.toJukeboxWorld(): JukeboxWorld {
    return this as JukeboxWorld
}
fun Chunk.toJukeboxChunk(): JukeboxChunk {
    return this as JukeboxChunk
}
fun Item.toJukeboxItem(): JukeboxItem {
    return this as JukeboxItem
}
fun Block.toJukeboxBlock(): JukeboxBlock {
    return this as JukeboxBlock
}
fun Player.toJukeboxPlayer(): JukeboxPlayer {
    return this as JukeboxPlayer
}
fun BlockEntity.toJukeboxBlockEntity(): JukeboxBlockEntity {
    return this as JukeboxBlockEntity
}
fun Skin.fromNetwork(serializedSkin: SerializedSkin) : Skin{
    val skin = Skin()
    skin.setSkinId(serializedSkin.skinId)
    skin.setPlayFabId(serializedSkin.playFabId)
    skin.setGeometryName(serializedSkin.geometryName)
    skin.setResourcePatch(serializedSkin.skinResourcePatch)
    skin.setSkinData(
        SkinImage(
            serializedSkin.skinData.width,
            serializedSkin.skinData.height,
            serializedSkin.skinData.image
        )
    )
    val skinAnimations: MutableList<SkinAnimation> = ArrayList()
    for (animation in serializedSkin.animations) {
        val image = SkinImage(animation.image.width, animation.image.height, animation.image.image)
        skinAnimations.add(
            SkinAnimation(
                image,
                animation.textureType.ordinal,
                animation.frames,
                animation.expressionType.ordinal
            )
        )
    }
    skin.setSkinAnimations(skinAnimations)
    skin.setCapeData(
        SkinImage(
            serializedSkin.capeData.width,
            serializedSkin.capeData.height,
            serializedSkin.capeData.image
        )
    )
    skin.setGeometryData(serializedSkin.geometryData)
    skin.setAnimationData(serializedSkin.animationData)
    skin.setPremium(serializedSkin.isPremium)
    skin.setPersona(serializedSkin.isPersona)
    skin.setCapeOnClassic(serializedSkin.isCapeOnClassic)
    skin.setCapeId(serializedSkin.capeId)
    skin.setFullSkinId(serializedSkin.fullSkinId)
    skin.setArmSize(serializedSkin.armSize)
    skin.setSkinColor(serializedSkin.skinColor)
    val personaPieces: MutableList<PersonaPiece> = ArrayList()
    for (personaPiece in serializedSkin.personaPieces) {
        personaPieces.add(
            PersonaPiece(
                personaPiece.id,
                personaPiece.type,
                personaPiece.packId,
                personaPiece.productId,
                personaPiece.isDefault
            )
        )
    }
    skin.setPersonaPieces(personaPieces)
    val pieceTints: MutableList<PersonaPieceTint> = ArrayList()
    for (tintColor in serializedSkin.tintColors) {
        pieceTints.add(PersonaPieceTint(tintColor.type, tintColor.colors))
    }
    skin.setPersonaPieceTints(pieceTints)
    return skin
}
fun Skin.toNetwork(): SerializedSkin {
    val animationDataList: MutableList<AnimationData> = ArrayList()
    for (animation in getSkinAnimations()) {
        animationDataList.add(
            AnimationData(
                ImageData.of(
                    animation.skinImage.width,
                    animation.skinImage.height,
                    animation.skinImage.data
                ),
                AnimatedTextureType.entries[animation.type], animation.frames
            )
        )
    }
    val personaPieceDataList: MutableList<PersonaPieceData> = ArrayList()
    for (piece in getPersonaPieces()) {
        personaPieceDataList.add(
            PersonaPieceData(
                piece.pieceId,
                piece.pieceType,
                piece.packId,
                piece.isDefault,
                piece.productId
            )
        )
    }
    val personaPieceTintList: MutableList<PersonaPieceTintData> = ArrayList()
    for (pieceTint in getPersonaPieceTints()) {
        personaPieceTintList.add(PersonaPieceTintData(pieceTint.pieceType, pieceTint.colors))
    }
    if (this.getSkinId() == null) {
        this.setSkinId(generateSkinId("Custom"))
    }
    return SerializedSkin.builder()
        .skinId(getSkinId())
        .playFabId(getPlayFabId())
        .geometryName(getGeometryName())
        .skinResourcePatch(getResourcePatch())
        .skinData(ImageData.of(getSkinData().width, getSkinData().height, getSkinData().data))
        .animations(animationDataList)
        .capeData(ImageData.of(getCapeData().width, getCapeData().height, getCapeData().data))
        .geometryData(getGeometryData())
        .animationData(getAnimationData())
        .premium(isPremium())
        .persona(isPersona())
        .capeOnClassic(isCapeOnClassic())
        .capeId(getCapeId())
        .fullSkinId(getFullSkinId())
        .armSize(getArmSize())
        .skinColor(getSkinColor())
        .personaPieces(personaPieceDataList)
        .tintColors(personaPieceTintList)
        .build()
}

fun GameRuleValue.toNetwork(): GameRuleData<Any> {
    return GameRuleData(this.identifier, this.value)
}
fun Boolean.toByte(): Byte {
    return if (this) 1 else 0
}

