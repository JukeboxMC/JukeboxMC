package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.PortalAxis

interface Portal : Block {

   fun getPortalAxis(): PortalAxis

   fun setPortalAxis(value: PortalAxis): Portal

}