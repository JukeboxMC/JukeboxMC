package org.jukeboxmc.api.resourcepack

import java.util.*

interface ResourcePackManager {

    fun getResourcePack(uuid: UUID): ResourcePack?

    fun getResourcePacks(): Collection<ResourcePack>

}