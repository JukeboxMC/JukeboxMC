package org.jukeboxmc.server.block

import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition

class RuntimeBlockDefinition(private val networkId: Int) : BlockDefinition {

    override fun getRuntimeId(): Int {
        return this.networkId
    }
}