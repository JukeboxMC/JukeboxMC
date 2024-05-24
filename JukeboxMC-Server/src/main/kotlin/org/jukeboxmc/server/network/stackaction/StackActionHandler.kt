package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType

class StackActionHandler {

    companion object {
        private val stackActionHandler: MutableMap<ItemStackRequestActionType, StackAction<out ItemStackRequestAction>> =
            mutableMapOf()

        init {
            this.stackActionHandler[ItemStackRequestActionType.TAKE] = TakeStackAction()
            this.stackActionHandler[ItemStackRequestActionType.PLACE] = PlaceStackAction()
            this.stackActionHandler[ItemStackRequestActionType.DESTROY] = DestroyStackAction()
            this.stackActionHandler[ItemStackRequestActionType.SWAP] = SwapStackAction()
            this.stackActionHandler[ItemStackRequestActionType.DROP] = DropStackAction()
            this.stackActionHandler[ItemStackRequestActionType.CRAFT_LOOM] = LoomStackAction()
            this.stackActionHandler[ItemStackRequestActionType.CRAFT_RECIPE] = CraftRecipeStackAction()
            this.stackActionHandler[ItemStackRequestActionType.MINE_BLOCK] = MineBlockStackAction()
            this.stackActionHandler[ItemStackRequestActionType.CRAFT_CREATIVE] = CraftCreativeStackAction()
            this.stackActionHandler[ItemStackRequestActionType.CRAFT_REPAIR_AND_DISENCHANT] = GrindStoneStackAction()


        }

        fun getPacketHandler(stackRequestActionType: ItemStackRequestActionType): StackAction<out ItemStackRequestAction>? {
            return this.stackActionHandler[stackRequestActionType]
        }
    }
}

