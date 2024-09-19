package com.github.subat0m1c.hatecheaters.commands.impl

import com.github.subat0m1c.hatecheaters.commands.commodore
import com.github.subat0m1c.hatecheaters.modules.HateCheaters
import com.github.subat0m1c.hatecheaters.utils.ApiUtils.magicalPower
import com.github.subat0m1c.hatecheaters.utils.ChatUtils.modMessage
import com.github.subat0m1c.hatecheaters.utils.JsonParseUtils.getSkyblockProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.odinmain.OdinMain.scope
import me.odinmain.config.Config
import me.odinmain.utils.skyblock.sendCommand
import java.util.*
import kotlin.concurrent.schedule

val DevCommand = commodore("hcdev") {

    var server: String? = null

    literal("apitest").runs { name: String ->

        scope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                val profiles = getSkyblockProfile(name) ?: return@withContext modMessage("Player does not exist!")

                val profile = profiles.profileData.profiles.find { it.selected }?.members?.get(profiles.uuid)
                    ?: return@withContext modMessage("Could not find player data.")
                modMessage("Succeeded!")
            }
        }
    }

    literal("server").runs { serverInput: String ->
        modMessage("""
            Are you sure you want to change the api server to $serverInput? 
            Changing it as an end user violates Hypixel's API terms of service.
            The expected server uses a custom endpoint, which means the input server must be complete until the uuid, for example: "http://servernamehere/v2/skyblock/profiles?uuid="
            Run /hcserver default to set the server to the default.
            Run /hcdev confirm to confirm.
            """.trimIndent())
        server = serverInput
        Timer().schedule(2000) {
            server = null
        }
    }

    literal("confirm").runs {
        server?.let {
            HateCheaters.server = it
            Config.save()
            modMessage("Set server to $it")
        } ?: modMessage("Server not set! Run /hcdev server to set one.")
    }
}