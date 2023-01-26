package com.danvhae.minecraft.siege.core.enums

import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.node.types.InheritanceNode
import java.util.*

enum class SiegeGroup(val groupName:String) {

    DEFAULT("default"),

    OPERATOR("support"),

    OWNER("owner"),

    WORKER("worker");

    fun setGroup(uuid: UUID){

        LuckPermsProvider.get().userManager.let { manger->
            manger.loadUser(uuid).thenAcceptAsync { user ->
                user.data().clear()
                user.data().add(InheritanceNode.builder(groupName).value(true).build())
                manger.saveUser(user)
            }
        }

    }
}