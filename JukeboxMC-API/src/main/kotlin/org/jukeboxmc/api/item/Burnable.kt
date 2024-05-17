package org.jukeboxmc.api.item

import java.time.Duration

interface Burnable : Item {

    fun getBurnTime(): Duration

}