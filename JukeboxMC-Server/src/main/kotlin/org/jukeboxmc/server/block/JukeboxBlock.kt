@file:Suppress("UNCHECKED_CAST")

package org.jukeboxmc.server.block

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.cloudburstmc.protocol.bedrock.packet.UpdateBlockPacket
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.api.event.block.BlockBreakEvent
import org.jukeboxmc.api.item.Durability
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.TierType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.api.math.AxisAlignedBB
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.world.MovingObjectPosition
import org.jukeboxmc.api.world.Particle
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.extensions.*
import org.jukeboxmc.server.item.ItemRegistry
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.BlockAttributes
import org.jukeboxmc.server.util.BlockPalette
import org.jukeboxmc.server.world.JukeboxWorld
import java.awt.Color
import java.util.*
import java.util.function.Predicate
import kotlin.random.Random

open class JukeboxBlock(
    private var identifier: Identifier,
    private var blockStates: NbtMap?
) : Block, Cloneable {

    val states: Object2ObjectMap<Identifier, Object2ObjectMap<NbtMap, Int>> = Object2ObjectLinkedOpenHashMap()

    private var networkId: Int = 0
    private var blockType: BlockType = BlockType.AIR
    private var location: Location? = null
    private var layer: Int = 0
    private var blockProperties: BlockProperties
    private var boundingBox: AxisAlignedBB? = null
    private var random: java.util.Random = java.util.Random()

    init {
        this.blockType = BlockRegistry.getBlockType(this.identifier)
        if (!this.states.containsKey(this.identifier)) {
            val toNetworkId: Object2ObjectMap<NbtMap, Int> = Object2ObjectLinkedOpenHashMap()
            for (searchBlock in BlockPalette.searchBlocks { blockMap ->
                blockMap.getString("name").lowercase() == this.identifier.getFullName()
            }) {
                toNetworkId[searchBlock.getCompound("states")] = BlockPalette.getNetworkId(searchBlock)
            }
            this.states[this.identifier] = toNetworkId
        }

        if (this.blockStates == null) {
            this.states[this.identifier]?.let {
                val states: List<NbtMap> = LinkedList(it.keys)
                this.blockStates = if (states.isEmpty()) null else states[0]
            }
        }
        this.states[this.identifier]?.let { statesMap ->
            statesMap[this.blockStates]?.let {
                this.networkId = it
            }
        }
        this.blockProperties = BlockAttributes.getBlockAttributes(
            this.identifier,
            if (this.blockStates == null) NbtMap.EMPTY else this.blockStates!!
        )
    }

    companion object {
        val AIR by lazy { Block.create(BlockType.AIR).toJukeboxBlock() }
    }

    override fun getNetworkId(): Int {
        return this.networkId
    }

    override fun getIdentifier(): Identifier {
        return this.identifier
    }

    override fun getBlockStates(): NbtMap? {
        return this.blockStates
    }

    fun setBlockStates(blockStates: NbtMap?) {
        this.blockStates = blockStates
    }

    override fun getBoundingBox(): AxisAlignedBB {
        this.location?.let {
            if (this.boundingBox == null) {
                val boundingBoxByState = this.getBoundingBoxByState()
                this.boundingBox = AxisAlignedBB(
                    it.getX() + boundingBoxByState.getMinX(),
                    it.getY() + boundingBoxByState.getMinY(),
                    it.getZ() + boundingBoxByState.getMinZ(),
                    it.getX() + boundingBoxByState.getMaxX(),
                    it.getY() + boundingBoxByState.getMaxY(),
                    it.getZ() + boundingBoxByState.getMaxZ()
                )
            }
            return this.boundingBox!!
        }
        return AxisAlignedBB(0F, 0F, 0F, 0F, 0F, 0F)
    }

    private fun getBoundingBoxByState(): AxisAlignedBB {
        val state = if (this.blockStates == null) NbtMap.EMPTY else this.blockStates!!
        return BlockAttributes.getBlockAttributes(this.identifier, state).getBoundingBox()
    }

    override fun getType(): BlockType {
        return this.blockType
    }

    override fun getWorld(): JukeboxWorld {
        return this.location!!.getWorld().toJukeboxWorld()
    }

    override fun getLocation(): Location {
        return this.location!!
    }

    override fun isValid(): Boolean {
        return this.location != null
    }

    fun setLocation(location: Location) {
        this.location = location
    }

    override fun getX(): Float {
        return this.location!!.getX()
    }

    fun setX(x: Float) {
        this.location!!.setX(x)
    }

    override fun getY(): Float {
        return this.location!!.getY()
    }

    fun setY(y: Float) {
        this.location!!.setY(y)
    }

    override fun getZ(): Float {
        return this.location!!.getZ()
    }

    fun setZ(z: Float) {
        this.location!!.setZ(z)
    }

    override fun getBlockX(): Int {
        return this.location!!.getBlockX()
    }

    override fun getBlockY(): Int {
        return this.location!!.getBlockY()
    }

    override fun getBlockZ(): Int {
        return this.location!!.getBlockZ()
    }

    override fun getLayer(): Int {
        return this.layer
    }

    fun setLayer(layer: Int) {
        this.layer = layer
    }

    override fun getRelative(blockFace: BlockFace): Block {
        return this.getRelative(blockFace, 0)
    }

    override fun getRelative(blockFace: BlockFace, layer: Int): Block {
        return this.getRelative(blockFace, layer, 1)
    }

    override fun getRelative(blockFace: BlockFace, layer: Int, step: Int): Block {
        return when (blockFace) {
            BlockFace.DOWN -> this.getRelative(Vector(0, -1, 0), layer, step)
            BlockFace.UP -> this.getRelative(Vector(0, 1, 0), layer, step)
            BlockFace.SOUTH -> this.getRelative(Vector(0, 0, 1), layer, step)
            BlockFace.NORTH -> this.getRelative(Vector(0, 0, -1), layer, step)
            BlockFace.EAST -> this.getRelative(Vector(1, 0, 0), layer, step)
            else -> this.getRelative(Vector(-1, 0, 0), layer, step)
        }
    }

    override fun getRelative(direction: Direction): Block {
        return this.getRelative(direction, 0)
    }

    override fun getRelative(direction: Direction, layer: Int): Block {
        return this.getRelative(direction.toBlockFace(), layer, 1)
    }

    override fun getRelative(direction: Direction, layer: Int, step: Int): Block {
        return this.getRelative(direction.toBlockFace(), layer, step)
    }

    fun getRelative(position: Vector, layer: Int, step: Int): Block {
        val x: Int = location!!.getBlockX() + position.getBlockX() * step
        val y: Int = location!!.getBlockY() + position.getBlockY() * step
        val z: Int = location!!.getBlockZ() + position.getBlockZ() * step
        return this.location!!.getWorld().getBlock(x, y, z, layer, this.location!!.getDimension())
    }

    override fun breakNaturally() {

    }

    override fun getBlockColor(): Color {
        return this.blockProperties.getColor()
    }

    override fun isSolid(): Boolean {
        return this.blockProperties.isSolid()
    }

    override fun isTransparent(): Boolean {
        return this.blockProperties.isTransparent()
    }

    override fun getHardness(): Float {
        return this.blockProperties.getHardness()
    }

    override fun getFriction(): Float {
        return this.blockProperties.getFriction()
    }

    override fun getBlastResistance(): Float {
        return this.blockProperties.getBlastResistance()
    }

    override fun hasBlockEntity(): Boolean {
        return this.blockProperties.hasBlockEntity()
    }

    override fun getBlockEntityName(): String {
        return this.blockProperties.getBlockEntityName()
    }

    override fun hasCollision(): Boolean {
        return this.blockProperties.hasCollision()
    }

    override fun toItem(): Item {
        return Item.create(ItemRegistry.getItemType(this.identifier))
    }

    override fun getDrops(item: Item): MutableList<Item> {
        if (this.canBreakWithHand(item.toJukeboxItem())) {
            if (this.withoutItemDropsOnlyWithSilkTouch()) {
                if (item.hasEnchantment(EnchantmentType.SILK_TOUCH)) {
                    return mutableListOf(this.toItem())
                }
            } else {
                return mutableListOf(this.toItem())
            }
        }
        return mutableListOf()
    }

    override fun canPassThrough(): Boolean {
        return false
    }

    override fun canBeFlowedInto(): Boolean {
        return false
    }

    override fun canWaterloggingFlowInto(): Boolean {
        return this.canBeFlowedInto() || this.getWaterLoggingLevel() > 1
    }

    override fun getWaterLoggingLevel(): Int {
        return 0
    }

    override fun isWaterBlocking(): Boolean {
        return this.blockProperties.isWaterBlocking()
    }

    override fun getToolType(): ToolType {
        return this.blockProperties.getToolType()
    }

    override fun getTierType(): TierType {
        return this.blockProperties.getTierType()
    }

    override fun ignoreSilkTouch(): Boolean {
        return when (this.blockType) {
            BlockType.OAK_DOUBLE_SLAB, BlockType.SPRUCE_DOUBLE_SLAB, BlockType.BIRCH_DOUBLE_SLAB,
            BlockType.JUNGLE_DOUBLE_SLAB, BlockType.ACACIA_DOUBLE_SLAB, BlockType.DARK_OAK_DOUBLE_SLAB,
            BlockType.MANGROVE_DOUBLE_SLAB, BlockType.CHERRY_DOUBLE_SLAB, BlockType.BAMBOO_DOUBLE_SLAB,
            BlockType.BAMBOO_MOSAIC_DOUBLE_SLAB, BlockType.CRIMSON_DOUBLE_SLAB, BlockType.WARPED_DOUBLE_SLAB,
            BlockType.DOUBLE_STONE_BLOCK_SLAB, BlockType.DOUBLE_STONE_BLOCK_SLAB2, BlockType.DOUBLE_STONE_BLOCK_SLAB3,
            BlockType.DOUBLE_STONE_BLOCK_SLAB4, BlockType.BLACKSTONE_DOUBLE_SLAB, BlockType.POLISHED_BLACKSTONE_DOUBLE_SLAB,
            BlockType.POLISHED_BLACKSTONE_BRICK_DOUBLE_SLAB, BlockType.DOUBLE_CUT_COPPER_SLAB, BlockType.EXPOSED_DOUBLE_CUT_COPPER_SLAB,
            BlockType.WEATHERED_DOUBLE_CUT_COPPER_SLAB, BlockType.OXIDIZED_DOUBLE_CUT_COPPER_SLAB, BlockType.WAXED_DOUBLE_CUT_COPPER_SLAB,
            BlockType.WAXED_EXPOSED_DOUBLE_CUT_COPPER_SLAB, BlockType.WAXED_WEATHERED_DOUBLE_CUT_COPPER_SLAB,
            BlockType.WAXED_OXIDIZED_DOUBLE_CUT_COPPER_SLAB, BlockType.COBBLED_DEEPSLATE_DOUBLE_SLAB, BlockType.POLISHED_DEEPSLATE_DOUBLE_SLAB,
            BlockType.DEEPSLATE_TILE_DOUBLE_SLAB, BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB, BlockType.MUD_BRICK_DOUBLE_SLAB,
            BlockType.FARMLAND, BlockType.AIR, BlockType.ALLOW, BlockType.BARRIER, BlockType.BEDROCK, BlockType.CAKE, BlockType.BORDER_BLOCK, BlockType.BUDDING_AMETHYST,
            BlockType.CAMERA, BlockType.CAVE_VINES, BlockType.CAVE_VINES_HEAD_WITH_BERRIES, BlockType.CAVE_VINES_BODY_WITH_BERRIES,
            BlockType.COMMAND_BLOCK, BlockType.CHAIN_COMMAND_BLOCK, BlockType.REPEATING_COMMAND_BLOCK, BlockType.CLIENT_REQUEST_PLACEHOLDER_BLOCK,
            BlockType.DENY, BlockType.END_GATEWAY, BlockType.END_PORTAL, BlockType.END_PORTAL_FRAME, BlockType.FIRE, BlockType.FROG_SPAWN,
            BlockType.FROSTED_ICE, BlockType.INFO_UPDATE, BlockType.INFO_UPDATE2, BlockType.INVISIBLE_BEDROCK, BlockType.JIGSAW, BlockType.MOVING_BLOCK,
            BlockType.NETHERREACTOR, BlockType.PORTAL, BlockType.POWDER_SNOW, BlockType.REINFORCED_DEEPSLATE, BlockType.RESERVED6,
            BlockType.STRUCTURE_BLOCK, BlockType.STRUCTURE_VOID,
            -> true

            else -> false
        }
    }

    fun withoutItemDropsOnlyWithSilkTouch(): Boolean {
        return when (this.getType()) {
            BlockType.BEE_NEST, BlockType.BEEHIVE, BlockType.BLACK_STAINED_GLASS, BlockType.BLUE_STAINED_GLASS,
            BlockType.BROWN_STAINED_GLASS, BlockType.CYAN_STAINED_GLASS, BlockType.GRAY_STAINED_GLASS, BlockType.GREEN_STAINED_GLASS,
            BlockType.LIGHT_BLUE_STAINED_GLASS, BlockType.LIGHT_GRAY_STAINED_GLASS, BlockType.LIME_STAINED_GLASS, BlockType.MAGENTA_STAINED_GLASS,
            BlockType.ORANGE_STAINED_GLASS, BlockType.PINK_STAINED_GLASS, BlockType.PURPLE_STAINED_GLASS,
            BlockType.RED_STAINED_GLASS, BlockType.WHITE_STAINED_GLASS, BlockType.YELLOW_STAINED_GLASS, BlockType.BLUE_STAINED_GLASS_PANE,
            BlockType.BROWN_STAINED_GLASS_PANE, BlockType.CYAN_STAINED_GLASS_PANE, BlockType.GRAY_STAINED_GLASS_PANE, BlockType.GREEN_STAINED_GLASS_PANE,
            BlockType.LIGHT_BLUE_STAINED_GLASS_PANE, BlockType.LIGHT_GRAY_STAINED_GLASS_PANE, BlockType.LIME_STAINED_GLASS_PANE, BlockType.MAGENTA_STAINED_GLASS_PANE,
            BlockType.ORANGE_STAINED_GLASS_PANE, BlockType.PINK_STAINED_GLASS_PANE, BlockType.PURPLE_STAINED_GLASS_PANE,
            BlockType.RED_STAINED_GLASS_PANE, BlockType.WHITE_STAINED_GLASS_PANE, BlockType.YELLOW_STAINED_GLASS_PANE, BlockType.GLASS, BlockType.GLASS_PANE,
            BlockType.BLUE_ICE, BlockType.TUBE_CORAL, BlockType.BRAIN_CORAL, BlockType.BUBBLE_CORAL, BlockType.FIRE_CORAL, BlockType.HORN_CORAL,
            BlockType.CALIBRATED_SCULK_SENSOR, BlockType.DEAD_BRAIN_CORAL, BlockType.DEAD_BUBBLE_CORAL, BlockType.DEAD_FIRE_CORAL,
            BlockType.DEAD_HORN_CORAL, BlockType.DEAD_TUBE_CORAL, BlockType.BUBBLE_CORAL_FAN, BlockType.FIRE_CORAL_FAN, BlockType.HORN_CORAL_FAN,
            BlockType.BRAIN_CORAL_FAN, BlockType.TUBE_CORAL_FAN, BlockType.DEAD_BUBBLE_CORAL_FAN, BlockType.DEAD_FIRE_CORAL_FAN, BlockType.DEAD_HORN_CORAL_FAN,
            BlockType.DEAD_BRAIN_CORAL_FAN, BlockType.DEAD_TUBE_CORAL_FAN, BlockType.CORAL_FAN_HANG2, BlockType.CORAL_FAN_HANG3, BlockType.ICE, BlockType.INFESTED_DEEPSLATE,
            BlockType.SMALL_AMETHYST_BUD, BlockType.MEDIUM_AMETHYST_BUD, BlockType.LARGE_AMETHYST_BUD, BlockType.PACKED_ICE,
            BlockType.SCULK, BlockType.SCULK_CATALYST, BlockType.SCULK_SENSOR, BlockType.SCULK_SHRIEKER, BlockType.SCULK_VEIN,
            BlockType.TURTLE_EGG, BlockType.TALLGRASS -> true

            else -> false
        }
    }

    override fun getRandom(): java.util.Random {
        return this.random
    }

    open fun onBlockBreak(breakLocation: Vector) {
        this.location?.getWorld()?.setBlock(breakLocation, 0, breakLocation.getDimension(), AIR)
    }

    fun playBreakSound() {
        this.getWorld().playLevelSound(this.getLocation(), SoundEvent.BREAK, this.networkId)
    }

    open fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        world.setBlock(placePosition, 0, player.getDimension(), this, true)
        return true
    }

    open fun interact(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        return false
    }

    open fun canBeReplaced(block: JukeboxBlock): Boolean {
        return false
    }

    open fun playPlaceSound(placePosition: Vector) {
        this.getWorld().playLevelSound(placePosition, SoundEvent.PLACE, this.networkId)
    }

    open fun onUpdate(updateReason: UpdateReason): Long {
        return -1
    }

    fun update(player: JukeboxPlayer) {
        this.location?.let {
            val updateBlockPacket = UpdateBlockPacket()
            updateBlockPacket.definition = RuntimeBlockDefinition(this.networkId)
            updateBlockPacket.blockPosition = it.toVector3i()
            updateBlockPacket.flags.addAll(UpdateBlockPacket.FLAG_ALL_PRIORITY)
            updateBlockPacket.dataLayer = layer
            player.sendPacket(updateBlockPacket)
        }
    }

    fun update() {
        val updateBlockPacket = UpdateBlockPacket()
        updateBlockPacket.definition = RuntimeBlockDefinition(this.networkId)
        updateBlockPacket.blockPosition = this.getLocation().toVector3i()
        updateBlockPacket.flags.addAll(UpdateBlockPacket.FLAG_ALL_PRIORITY)
        updateBlockPacket.dataLayer = layer
        this.getWorld()
            .sendChunkPacket(this.getLocation().getChunkX(), this.getLocation().getChunkZ(), updateBlockPacket)
    }

    fun breakBlockHandling(player: JukeboxPlayer, item: JukeboxItem) {
        val itemDropList: MutableList<Item> = if (item.hasEnchantment(EnchantmentType.SILK_TOUCH)) {
            listOf(this.toItem()).toMutableList()
        } else {
            this.getDrops(item)
        }
        val blockBreakEvent = BlockBreakEvent(this, player, itemDropList)
        JukeboxServer.getInstance().getPluginManager().callEvent(blockBreakEvent)

        if (blockBreakEvent.isCancelled()) {
            player.getInventory().sendItemInHand()
            this.update(player)
            return
        }
        val breakLocation = this.location
        if (breakLocation != null) {
            this.onBlockBreak(breakLocation)
            if (player.getGameMode() == GameMode.SURVIVAL) {
                for (dropItem in blockBreakEvent.getDrops()) {
                    this.location?.let { it.getWorld().dropItemNaturally(it.clone().add(0.5f, 1f, 0.5f), dropItem) }
                }
            }
            this.getWorld().spawnParticle(Particle.PARTICLE_DESTROY_BLOCK, breakLocation, this.networkId)
            this.playBreakSound()
        }

        if (player.getGameMode() == GameMode.SURVIVAL) {
            if (item is Durability) {
                item.updateDurability(player, 1)
            }

            player.exhaust(0.025F)
        }
    }

    fun checkValidity(): Boolean {
        if (this.location == null) return false
        return this.location?.let {
            it.getWorld().getBlock(it.getBlockX(), it.getBlockY(), it.getBlockZ(), this.layer, it.getDimension())
                .getNetworkId() == this.networkId
        }!!
    }

    open fun firstInLayer(blockPredicate: Predicate<JukeboxBlock>): Optional<JukeboxBlock> {
        return this.firstInLayer(0, blockPredicate)
    }

    open fun firstInLayer(
        startingLayer: Int,
        blockPredicate: Predicate<JukeboxBlock>
    ): Optional<JukeboxBlock> {
        val maximumLayer = 1
        for (layer in startingLayer..maximumLayer) {
            val block: JukeboxBlock = this.location!!.getBlock(layer) as JukeboxBlock
            if (blockPredicate.test(block)) {
                return Optional.of(block)
            }
        }
        return Optional.empty()
    }

    fun <B : Block> setState(state: String, value: Any, update: Boolean = true): B {
        if (!blockStates!!.containsKey(state)) {
            throw AssertionError("State $state was not found in block $identifier")
        }
        if (blockStates!![state]!!.javaClass != value.javaClass) {
            throw AssertionError("State $state type is not the same for value $value")
        }
        val valid = checkValidity()
        val nbtMapBuilder = blockStates!!.toBuilder()
        nbtMapBuilder[state] = value
        for ((blockMap) in states[identifier]!!.entries) {
            if (blockMap == nbtMapBuilder) {
                blockStates = blockMap
            }
        }
        this.networkId = states[identifier]?.get(blockStates)!!
        if (valid && update) {
            this.update()
            val loadedChunk = location!!.getLoadedChunk()!!.toJukeboxChunk()
            loadedChunk.setBlock(
                location!!.getBlockX(),
                location!!.getBlockY(),
                location!!.getBlockZ(),
                layer,
                this
            )
            loadedChunk.setChanged(true)
        }
        return this as B
    }

    fun stateExists(value: String): Boolean {
        return blockStates!!.containsKey(value)
    }

    fun getStringState(value: String): String {
        return blockStates!!.getString(value).uppercase()
    }

    fun getBooleanState(value: String): Boolean {
        return blockStates!!.getByte(value).toInt() == 1
    }

    fun getIntState(value: String?): Int {
        return blockStates!!.getInt(value)
    }

    fun calculateBreakTime(item: JukeboxItem, player: JukeboxPlayer): Double {
        var seconds: Double
        val hardness = this.getHardness()
        val canBreakWithHand = this.canBreakWithHand(item)

        seconds = if (canBreakWithHand) {
            hardness * 1.5
        } else {
            hardness * 5.0
        }

        var speedMultiplier = 1.0
        val hasConduitPower = false
        val hasAquaAffinity =
            Optional.ofNullable(player.getArmorInventory().getHelmet().getEnchantment(EnchantmentType.AQUA_AFFINITY))
                .map(Enchantment::getLevel).map { l: Int -> l >= 1 }.orElse(false)
        var hasteEffectLevel = 0
        val miningFatigueLevel = 0

        if (player.hasEffect(EffectType.HASTE)) {
            hasteEffectLevel = player.getEffect(EffectType.HASTE)!!.getAmplifier()
        }

        if (this.correctTool0(this.getToolType(), item, this.getType())) {
            speedMultiplier =
                toolBreakTimeBonus0(this.toolType0(item, this.getType()), item.getTierType(), this.getType())
            val efficiencyLevel: Int =
                Optional.ofNullable(item.getEnchantment(EnchantmentType.EFFICIENCY)).map(Enchantment::getLevel)
                    .orElse(0)
            if (canBreakWithHand && efficiencyLevel > 0) {
                speedMultiplier += (efficiencyLevel xor 2 + 1).toDouble()
            }
            if (hasConduitPower) hasteEffectLevel = Integer.max(hasteEffectLevel, 2)
            if (hasteEffectLevel > 0) {
                speedMultiplier *= 1 + 0.2 * hasteEffectLevel
            }
        }

        if (miningFatigueLevel > 0) {
            speedMultiplier /= (3 xor miningFatigueLevel).toDouble()
        }
        seconds /= speedMultiplier

        if (player.isInWater() && !hasAquaAffinity) {
            seconds *= if (hasConduitPower && hardness >= 0.5) 2.5 else 5.0
        }
        return seconds
    }

    fun canBreakWithHand(item: JukeboxItem): Boolean {
        return this.getTierType() == TierType.NONE || this.getToolType() == ToolType.NONE || this.correctTool0(
            this.getToolType(),
            item,
            this.getType()
        ) && item.getTierType().ordinal >= this.getTierType().ordinal
    }

    private fun correctTool0(blockToolType: ToolType, item: JukeboxItem, blockType: BlockType): Boolean {
        return if (blockType.isLeaves() && item.getToolType() == ToolType.HOE) {
            blockToolType == ToolType.SHEARS && item.getToolType() == ToolType.HOE
        } else if (blockType == BlockType.BAMBOO && item.getToolType() == ToolType.SWORD) {
            blockToolType == ToolType.AXE && item.getToolType() == ToolType.SWORD
        } else {
            (blockToolType == ToolType.SWORD && item.getToolType() == ToolType.SWORD) ||
                    (blockToolType == ToolType.SHOVEL && item.getToolType() == ToolType.SHOVEL) ||
                    (blockToolType == ToolType.PICKAXE && item.getToolType() == ToolType.PICKAXE) ||
                    (blockToolType == ToolType.AXE && item.getToolType() == ToolType.AXE) ||
                    (blockToolType == ToolType.HOE && item.getToolType() == ToolType.HOE) ||
                    (blockToolType == ToolType.SHEARS && item.getToolType() == ToolType.SHEARS) ||
                    blockToolType == ToolType.NONE || (blockType == BlockType.WEB && item.getToolType() == ToolType.SHEARS)
        }
    }

    private fun toolType0(item: JukeboxItem, blockType: BlockType): ToolType {
        if (blockType.isLeaves() && item.getToolType() == ToolType.HOE) return ToolType.SHEARS
        if (item.getToolType() == ToolType.SWORD) return ToolType.SWORD
        if (item.getToolType() == ToolType.SHOVEL) return ToolType.SHOVEL
        if (item.getToolType() == ToolType.PICKAXE) return ToolType.PICKAXE
        if (item.getToolType() == ToolType.AXE) return ToolType.AXE
        if (item.getToolType() == ToolType.HOE) return ToolType.HOE
        if (item.getToolType() == ToolType.SHEARS) return ToolType.SHEARS
        return ToolType.NONE
    }

    private fun toolBreakTimeBonus0(toolType: ToolType, tierType: TierType, blockType: BlockType): Double {
        if (toolType == ToolType.SWORD) {
            if (blockType == BlockType.WEB) {
                return 15.0
            }
            return if (blockType == BlockType.BAMBOO) {
                30.0
            } else 1.0
        }
        if (toolType == ToolType.SHEARS) {
            if (blockType.isWool() || blockType.isLeaves()) {
                return 5.0
            } else if (blockType == BlockType.WEB) {
                return 15.0
            }
            return 1.0
        }
        return if (toolType == ToolType.NONE) 1.0 else when (tierType) {
            TierType.WOODEN -> 2.0
            TierType.STONE -> 4.0
            TierType.IRON -> 6.0
            TierType.DIAMOND -> 8.0
            TierType.NETHERITE -> 9.0
            TierType.GOLD -> 12.0
            else -> 1.0
        }
    }


    fun collisionRayTrace(blockposition: Vector, vec3d: Vector, vec3d1: Vector): MovingObjectPosition? {
        val vec3dTemp = vec3d.add(-blockposition.getX(), -blockposition.getY(), -blockposition.getZ())
        val vec3dTemp1 = vec3d1.add(-blockposition.getX(), -blockposition.getY(), -blockposition.getZ())
        var vec3d2 = vec3dTemp.getIntermediateWithXValue(vec3dTemp1, this.boundingBox!!.getMinX())
        var vec3d3 = vec3dTemp.getIntermediateWithXValue(vec3dTemp1, this.boundingBox!!.getMaxX())
        var vec3d4 = vec3dTemp.getIntermediateWithYValue(vec3dTemp1, this.boundingBox!!.getMinY())
        var vec3d5 = vec3dTemp.getIntermediateWithYValue(vec3dTemp1, this.boundingBox!!.getMaxY())
        var vec3d6 = vec3dTemp.getIntermediateWithZValue(vec3dTemp1, this.boundingBox!!.getMinZ())
        var vec3d7 = vec3dTemp.getIntermediateWithZValue(vec3dTemp1, this.boundingBox!!.getMaxZ())

        if (!isVecInsideYZBounds(vec3d2)) {
            vec3d2 = null
        }

        if (!isVecInsideYZBounds(vec3d3)) {
            vec3d3 = null
        }

        if (!isVecInsideXZBounds(vec3d4)) {
            vec3d4 = null
        }

        if (!isVecInsideXZBounds(vec3d5)) {
            vec3d5 = null
        }

        if (!isVecInsideXYBounds(vec3d6)) {
            vec3d6 = null
        }

        if (!isVecInsideXYBounds(vec3d7)) {
            vec3d7 = null
        }

        var vec3d8: Vector? = null
        if (vec3d2 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d2) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d2
        }
        if (vec3d3 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d3) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d3
        }
        if (vec3d4 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d4) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d4
        }

        if (vec3d5 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d5) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d5
        }
        if (vec3d6 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d6) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d6
        }
        if (vec3d7 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d7) < vec3d.distanceSquared(vec3d8))) {
            vec3d8 = vec3d7
        }
        if (vec3d8 == null) {
            return null
        } else {
            var enumdirection: BlockFace? = null

            if (vec3d8 == vec3d2) {
                enumdirection = BlockFace.WEST
            }

            if (vec3d8 == vec3d3) {
                enumdirection = BlockFace.EAST
            }

            if (vec3d8 == vec3d4) {
                enumdirection = BlockFace.DOWN
            }

            if (vec3d8 == vec3d5) {
                enumdirection = BlockFace.UP
            }

            if (vec3d8 == vec3d6) {
                enumdirection = BlockFace.NORTH
            }

            if (vec3d8 == vec3d7) {
                enumdirection = BlockFace.SOUTH
            }

            return MovingObjectPosition(
                vec3d8.add(blockposition.getX(), blockposition.getY(), blockposition.getZ()),
                enumdirection,
                blockposition
            )
        }
    }

    private fun isVecInsideYZBounds(point: Vector?): Boolean {
        return point?.let {
            it.getY() >= this.boundingBox!!.getMinY() && it.getY() <= this.boundingBox!!.getMaxY() && it.getZ() >= this.boundingBox!!.getMinZ() && it.getZ() <= this.boundingBox!!.getMaxZ()
        } ?: false
    }

    private fun isVecInsideXZBounds(point: Vector?): Boolean {
        return point?.let {
            it.getX() >= this.boundingBox!!.getMinX() && it.getX() <= this.boundingBox!!.getMaxX() && it.getZ() >= this.boundingBox!!.getMinZ() && it.getZ() <= this.boundingBox!!.getMaxZ()
        } ?: false
    }

    private fun isVecInsideXYBounds(point: Vector?): Boolean {
        return point?.let {
            it.getX() >= this.boundingBox!!.getMinX() && it.getX() <= this.boundingBox!!.getMaxX() && it.getY() >= this.boundingBox!!.getMinY() && it.getY() <= this.boundingBox!!.getMaxY()
        } ?: false
    }

    open fun canCollideCheck(block: JukeboxBlock, value: Boolean): Boolean {
        return this.isCollidable()
    }

    open fun isCollidable(): Boolean {
        return true
    }

    fun createItemDrop(
        itemInHand: Item,
        vararg itemDrop: Item,
    ): MutableList<Item> {
        if (this.canBreakWithHand(itemInHand.toJukeboxItem())) {
            return itemDrop.toMutableList()
        }
        return mutableListOf()
    }

    fun createItemOreDrop(itemInHand: Item, itemDrop: Item, startAmount: Int): MutableList<Item> {
        if (this.canBreakWithHand(itemInHand.toJukeboxItem())) {
            var amount = startAmount
            if (itemInHand.hasEnchantment(EnchantmentType.FORTUNE) && itemInHand.getEnchantment(EnchantmentType.FORTUNE) != null) {
                amount = Random.nextInt(amount, amount + itemInHand.getEnchantment(EnchantmentType.FORTUNE)!!.getLevel() * amount) + 1
            }
            return mutableListOf(itemDrop.apply { this.setAmount(amount) })
        }
        return mutableListOf()
    }

    fun createItemStemDrop(item: Item, growth: Int): MutableList<Item> {
        val dropProbabilities = Int2ObjectArrayMap<List<ProbabilityAmountHolder>>()
        dropProbabilities[0] = listOf(
            ProbabilityAmountHolder(0, 81.3f),
            ProbabilityAmountHolder(1, 17.43f),
            ProbabilityAmountHolder(2, 1.24f),
            ProbabilityAmountHolder(3, 0.03f)
        )
        dropProbabilities[1] = listOf(
            ProbabilityAmountHolder(0, 65.1f),
            ProbabilityAmountHolder(1, 30.04f),
            ProbabilityAmountHolder(2, 4.62f),
            ProbabilityAmountHolder(3, 0.24f)
        )
        dropProbabilities[2] = listOf(
            ProbabilityAmountHolder(0, 51.2f),
            ProbabilityAmountHolder(1, 38.4f),
            ProbabilityAmountHolder(2, 9.6f),
            ProbabilityAmountHolder(3, 0.8f)
        )
        dropProbabilities[3] = listOf(
            ProbabilityAmountHolder(0, 39.44f),
            ProbabilityAmountHolder(1, 43.02f),
            ProbabilityAmountHolder(2, 15.64f),
            ProbabilityAmountHolder(3, 1.9f)
        )
        dropProbabilities[4] = listOf(
            ProbabilityAmountHolder(0, 29.13f),
            ProbabilityAmountHolder(1, 44.44f),
            ProbabilityAmountHolder(2, 22.22f),
            ProbabilityAmountHolder(3, 3.7f)
        )
        dropProbabilities[5] = listOf(
            ProbabilityAmountHolder(0, 21.6f),
            ProbabilityAmountHolder(1, 43.2f),
            ProbabilityAmountHolder(2, 28.8f),
            ProbabilityAmountHolder(3, 6.4f)
        )
        dropProbabilities[6] = listOf(
            ProbabilityAmountHolder(0, 15.17f),
            ProbabilityAmountHolder(1, 39.82f),
            ProbabilityAmountHolder(2, 34.84f),
            ProbabilityAmountHolder(3, 10.16f)
        )
        dropProbabilities[7] = listOf(
            ProbabilityAmountHolder(0, 10.16f),
            ProbabilityAmountHolder(1, 34.84f),
            ProbabilityAmountHolder(2, 39.82f),
            ProbabilityAmountHolder(3, 15.17f)
        )
        val probability = this.random.nextFloat(100.0f)
        for (probabilityAmountHolder in dropProbabilities[growth]) {
            if (probabilityAmountHolder.probability <= probability) {
                return mutableListOf(item.apply { this.setAmount(probabilityAmountHolder.amount) })
            }
        }
        return mutableListOf()
    }

    data class ProbabilityAmountHolder(val amount: Int, val probability: Float)

    override fun clone(): JukeboxBlock {
        val block = super.clone() as JukeboxBlock
        block.identifier = this.identifier
        block.blockStates = this.blockStates
        block.networkId = this.networkId
        block.blockType = this.blockType
        block.location = this.location
        block.layer = this.layer
        block.blockProperties = this.blockProperties
        block.boundingBox = this.boundingBox
        return block
    }

}