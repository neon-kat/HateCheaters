package com.github.subat0m1c.hatecheaters.modules

import me.odinmain.features.Category
import me.odinmain.features.Module
import me.odinmain.features.settings.AlwaysActive
import me.odinmain.features.settings.impl.BooleanSetting
import me.odinmain.features.settings.impl.StringSetting

@AlwaysActive
object HateCheatersModule : Module(
    name = "Hate Cheaters",
    description = "Hate cheaters nonsense",
    category = Category.SKYBLOCK
) {
    val logJson: Boolean by BooleanSetting("Log Json", default = false, description = "Logs requested json data to Config/hatecheaters/json_logs")
    val debugMessages: Boolean by BooleanSetting("Debug messages", default = false, description = "Prints debug messages in your chat instead of needing to open logs.")
    val checkUpdates: Boolean by BooleanSetting("Update Checker", default = true, description = "Checks GitHub for latest HateCheaters releases and notifies you if you are on an old version!")
    val server: String by StringSetting("Api Server", default = "default", hidden = false, description = "Server to be used to connect to the api. set to \"default\" to use the default. Only change if you know what you're doing. format: \"subdomain.domain.tld\"")
}