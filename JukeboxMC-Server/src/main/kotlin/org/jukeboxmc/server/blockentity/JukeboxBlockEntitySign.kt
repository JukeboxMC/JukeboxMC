package org.jukeboxmc.server.blockentity

import com.google.common.base.Joiner
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtMapBuilder
import org.cloudburstmc.protocol.bedrock.packet.BlockEntityDataPacket
import org.jukeboxmc.api.blockentity.BlockEntitySign
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.event.block.SignChangeEvent
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toVector3i
import org.jukeboxmc.server.player.JukeboxPlayer

open class JukeboxBlockEntitySign(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntitySign {

    private val frontTextList: MutableList<String> = mutableListOf()
    private val backTextList: MutableList<String> = mutableListOf()
    private var waxed: Boolean = false
    private var usedByEntity: Long = -1

    override fun fromCompound(compound: NbtMap) {
        super.fromCompound(compound)

        val frontTextCompound = compound.getCompound("FrontText")
        val frontText = frontTextCompound.getString("Text")
        this.frontTextList.addAll(frontText.split("\n"))

        val backTextCompound = compound.getCompound("BackText")
        val backText = backTextCompound.getString("Text")
        this.backTextList.addAll(backText.split("\n"))

        this.waxed = compound.getByte("IsWaxed").toInt() == 1
        this.usedByEntity = -1
    }

    override fun toCompound(): NbtMapBuilder {
        val compound = super.toCompound()
        compound.putCompound("FrontText", NbtMap.builder()
            .putString("Text", Joiner.on("\n").skipNulls().join(this.frontTextList))
            .putByte("PersistFormatting", 1)
            .putByte("HideGlowOutline", 1)
            .build())
        compound.putCompound("BackText", NbtMap.builder()
            .putString("Text", Joiner.on("\n").skipNulls().join(this.backTextList))
            .putByte("PersistFormatting", 1)
            .putByte("HideGlowOutline", 1)
            .build())
        compound.putByte("IsWaxed", 0)
        return compound
    }

    fun getUsedByEntity(): Long {
        return this.usedByEntity
    }

    fun setUsedByEntity(usedByEntity: Long) {
        this.usedByEntity = usedByEntity
    }

    fun update(compound: NbtMap, player: JukeboxPlayer) {
        if (player.isSignFrontSide() == null) return

        val frontTextCompound = compound.getCompound("FrontText")
        val frontText = frontTextCompound.getString("Text")

        val backTextCompound = compound.getCompound("BackText")
        val backText = backTextCompound.getString("Text")

        val signChangeEvent = SignChangeEvent(this.getBlock(), player, frontText.split("\n").toMutableList(), backText.split("\n").toMutableList())
        JukeboxServer.getInstance().getPluginManager().callEvent(signChangeEvent)

        if (!signChangeEvent.isCancelled() && player.isSignFrontSide() != null) {
            this.frontTextList.clear()
            this.frontTextList.addAll(signChangeEvent.getFrontText())
            this.backTextList.clear()
            this.backTextList.addAll(signChangeEvent.getBackText())
            this.usedByEntity = -1L

            val blockEntityDataPacket = BlockEntityDataPacket()
            blockEntityDataPacket.blockPosition = this.getBlock().getLocation().toVector3i()
            blockEntityDataPacket.data = this.toCompound().build()
            player.getWorld().sendDimensionPacket(player.getDimension(), blockEntityDataPacket)
        }
        player.setSignFrontSide(null)
    }

}