package org.jukeboxmc.api.blockentity

interface BlockEntitySkull : BlockEntity {

    fun getSkullType(): Byte

    fun setSkullType(skullType: Byte)

    fun getRotation(): Byte

    fun setRotation(rotation: Byte)

}