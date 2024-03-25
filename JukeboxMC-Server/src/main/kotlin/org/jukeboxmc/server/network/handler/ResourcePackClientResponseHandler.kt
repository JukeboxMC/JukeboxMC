package org.jukeboxmc.server.network.handler

import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.data.*
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.ShapelessRecipeData
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount
import org.cloudburstmc.protocol.bedrock.packet.*
import org.cloudburstmc.protocol.common.SimpleDefinitionRegistry
import org.cloudburstmc.protocol.common.util.OptionalBoolean
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.toNetwork
import org.jukeboxmc.server.extensions.toVector3f
import org.jukeboxmc.server.extensions.toVector3i
import org.jukeboxmc.server.network.BedrockServer
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.BlockPalette
import org.jukeboxmc.server.util.ItemPalette
import org.jukeboxmc.server.util.PaletteUtil
import java.util.*

/**
 * @author Kaooot
 * @version 1.0
 */
class ResourcePackClientResponseHandler : PacketHandler<ResourcePackClientResponsePacket> {

    override fun handle(packet: ResourcePackClientResponsePacket, server: JukeboxServer, player: JukeboxPlayer) {
        when (packet.status) {
            ResourcePackClientResponsePacket.Status.REFUSED -> {
                val disconnectPacket = DisconnectPacket()
                disconnectPacket.kickMessage = ResourcePackClientResponsePacket.Status.REFUSED.name
            }

            ResourcePackClientResponsePacket.Status.SEND_PACKS -> {
                for (resourcePack in server.getResourcePackManager().getResourcePacks()) {
                    val maxChunkSize: Long = 1048576

                    val resourcePackDataInfoPacket = ResourcePackDataInfoPacket()
                    resourcePackDataInfoPacket.packId = resourcePack.getUuid()
                    resourcePackDataInfoPacket.maxChunkSize = maxChunkSize
                    resourcePackDataInfoPacket.chunkCount = resourcePack.getSize() / maxChunkSize
                    resourcePackDataInfoPacket.compressedPackSize = resourcePack.getSize()
                    resourcePackDataInfoPacket.hash = resourcePack.getHash()
                    resourcePackDataInfoPacket.type = ResourcePackType.RESOURCES

                    player.sendPacket(resourcePackDataInfoPacket)
                }
            }

            ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS -> {
                val resourcePackStackPacket = ResourcePackStackPacket()
                resourcePackStackPacket.isForcedToAccept = server.isForceResourcePacks()

                val entries = arrayListOf<ResourcePackStackPacket.Entry>()

                for (resourcePack in server.getResourcePackManager().getResourcePacks()) {
                    entries.add(ResourcePackStackPacket.Entry(
                        resourcePack.getUuid().toString(),
                        resourcePack.getVersion(),
                        "")
                    )
                }

                resourcePackStackPacket.resourcePacks.addAll(entries)
                resourcePackStackPacket.gameVersion = "*"

                player.sendPacket(resourcePackStackPacket)
            }

            ResourcePackClientResponsePacket.Status.COMPLETED -> {
                player.setLoggedIn(true)
                val world = server.getDefaultWorld()
                val worldData = world.getWorldData()

                val startGamePacket = StartGamePacket()
                startGamePacket.uniqueEntityId = player.getEntityId()
                startGamePacket.runtimeEntityId = player.getEntityId()
                startGamePacket.playerGameType = GameType.valueOf(server.getDefaultGameMode().name)
                startGamePacket.playerPosition = player.getWorld().getSpawnLocation(player.getDimension()).toVector3f()
                    .add(0F, player.getEyeHeight(), 0F)
                startGamePacket.defaultSpawn = player.getWorld().getSpawnLocation(player.getDimension()).toVector3i()
                startGamePacket.rotation = Vector2f.ZERO
                startGamePacket.seed = worldData.randomSeed
                startGamePacket.spawnBiomeType = SpawnBiomeType.DEFAULT
                startGamePacket.customBiomeName = ""
                startGamePacket.dimensionId = 0
                startGamePacket.blockRegistryChecksum = 0
                startGamePacket.levelGameType = GameType.valueOf(worldData.gameType.name)
                startGamePacket.isAchievementsDisabled = true
                startGamePacket.dayCycleStopTime = 0
                startGamePacket.rainLevel = 0F
                startGamePacket.lightningLevel = 0F
                startGamePacket.difficulty = worldData.difficulty.ordinal
                startGamePacket.isEduFeaturesEnabled = false
                startGamePacket.educationProductionId = ""
                startGamePacket.isPlatformLockedContentConfirmed = false
                startGamePacket.isMultiplayerGame = true
                startGamePacket.isBroadcastingToLan = true
                startGamePacket.xblBroadcastMode = GamePublishSetting.PUBLIC
                startGamePacket.platformBroadcastMode = GamePublishSetting.PUBLIC
                startGamePacket.isCommandsEnabled = true
                startGamePacket.isTexturePacksRequired = server.isForceResourcePacks()
                startGamePacket.isExperimentsPreviouslyToggled = false
                startGamePacket.isBonusChestEnabled = worldData.bonusChestEnabled
                startGamePacket.isStartingWithMap = worldData.startWithMapEnabled
                startGamePacket.defaultPlayerPermission = PlayerPermission.MEMBER
                startGamePacket.serverChunkTickRange = worldData.serverChunkTickRange
                startGamePacket.isBehaviorPackLocked = false
                startGamePacket.isResourcePackLocked = false
                startGamePacket.isFromLockedWorldTemplate = false
                startGamePacket.isUsingMsaGamertagsOnly = false
                startGamePacket.isFromWorldTemplate = false
                startGamePacket.isWorldTemplateOptionLocked = false
                startGamePacket.isOnlySpawningV1Villagers = false
                startGamePacket.vanillaVersion = "*"
                startGamePacket.limitedWorldWidth = worldData.limitedWorldWidth
                startGamePacket.limitedWorldHeight = worldData.limitedWorldDepth
                startGamePacket.isNetherType = false
                startGamePacket.forceExperimentalGameplay = OptionalBoolean.empty()
                startGamePacket.chatRestrictionLevel = ChatRestrictionLevel.NONE
                startGamePacket.isDisablingPlayerInteractions = false
                startGamePacket.isDisablingPersonas = false
                startGamePacket.isDisablingCustomSkins = false
                startGamePacket.levelId = ""
                startGamePacket.levelName = worldData.levelName
                startGamePacket.premiumWorldTemplateId = ""
                startGamePacket.isTrial = false
                startGamePacket.authoritativeMovementMode = AuthoritativeMovementMode.CLIENT
                startGamePacket.isServerAuthoritativeBlockBreaking = false
                startGamePacket.currentTick = server.getCurrentTick()
                startGamePacket.itemDefinitions = ItemPalette.getItemDefinitions()
                startGamePacket.multiplayerCorrelationId = ""
                startGamePacket.isInventoriesServerAuthoritative = true
                startGamePacket.serverEngine = "JukeboxMC"
                startGamePacket.playerPropertyData = NbtMap.EMPTY
                startGamePacket.worldTemplateId = UUID(0, 0)
                startGamePacket.isWorldEditor = false
                startGamePacket.isClientSideGenerationEnabled = false
                startGamePacket.isEmoteChatMuted = false
                startGamePacket.isBlockNetworkIdsHashed = true
                startGamePacket.isCreatedInEditor = false
                startGamePacket.isExportedFromEditor = false
                startGamePacket.isTrustingPlayers = true
                startGamePacket.generatorId = worldData.generator
                startGamePacket.serverChunkTickRange = server.getSimulationDistance().coerceAtMost(12)
                startGamePacket.gamerules.addAll(worldData.gameRuleData.map { it.toNetwork() }.toList())
                player.sendPacket(startGamePacket)

                player.getSession().peer.codecHelper.itemDefinitions =
                    SimpleDefinitionRegistry.builder<ItemDefinition>()
                        .addAll(startGamePacket.itemDefinitions)
                        .build()

                player.getSession().peer.codecHelper.blockDefinitions =
                    SimpleDefinitionRegistry.builder<BlockDefinition>()
                        .addAll(BlockPalette.getBlockDefinitions())
                        .build()

                val availableEntityIdentifiersPacket = AvailableEntityIdentifiersPacket()
                availableEntityIdentifiersPacket.identifiers = PaletteUtil.getEntityIdentifiers()
                player.sendPacket(availableEntityIdentifiersPacket)

                val biomeDefinitionListPacket = BiomeDefinitionListPacket()
                biomeDefinitionListPacket.definitions = PaletteUtil.getBiomeDefinitions()
                player.sendPacket(biomeDefinitionListPacket)

                val creativeContentPacket = CreativeContentPacket()
                creativeContentPacket.contents = PaletteUtil.getCreativeItems().toTypedArray()
                player.sendPacket(creativeContentPacket)

                val craftingDataPacket = CraftingDataPacket()
                craftingDataPacket.craftingData.addAll(server.getRecipeManager().getCraftingData())
                craftingDataPacket.containerMixData.addAll(server.getRecipeManager().getContainerMixData())
                craftingDataPacket.potionMixData.addAll(server.getRecipeManager().getPotionMixData())
                craftingDataPacket.isCleanRecipes = true
                player.sendPacket(craftingDataPacket)
            }

            else -> {}
        }
    }
}