package com.github.subat0m1c.hatecheaters.modules

import com.github.subat0m1c.hatecheaters.HateCheatersObject.screen
import com.github.subat0m1c.hatecheaters.modules.ProfileViewer.mc
import com.github.subat0m1c.hatecheaters.pvgui.PVGui
import com.github.subat0m1c.hatecheaters.pvgui.PVGui.loadPlayer
import com.github.subat0m1c.hatecheaters.utils.LogHandler.logger
import me.odinmain.features.Category
import me.odinmain.features.Module
import me.odinmain.features.settings.AlwaysActive
import me.odinmain.features.settings.Setting.Companion.withDependency
import me.odinmain.features.settings.impl.*
import me.odinmain.ui.clickgui.util.ColorUtil.withAlpha
import me.odinmain.utils.render.Color

@AlwaysActive
object ProfileViewer : Module(
    name = "Profile Viewer",
    description = "Lets you view profiles. /pv name or /hcpv name",
    category = Category.RENDER
) {
    val maxRows: Int by NumberSetting("Tali Rows", default = 7, increment = 1, min = 1, max = 7, description = "Maximum number of rows that can be displayed in the talisman page. Lower will give more performance, but will render less items.")
    private val themesList = arrayListOf("Classic", "Light", "Custom")
    val themes: Int by SelectorSetting("Theme", defaultSelected = "Classic", themesList, description = "Preferred theme")
    val main: Color by ColorSetting("Main", default = Color.DARK_GRAY, false, description = "Main color (primarily background).").withDependency { themes == themesList.lastIndex }
    val accent: Color by ColorSetting("Accent", default = Color.BLUE, true, description = "Accent color (primarily outlines).").withDependency { themes == themesList.lastIndex }
    val font: Color by ColorSetting("Font", default = Color.WHITE, true, description = "Font Color (only affects items without vanilla color codes).").withDependency { themes == themesList.lastIndex }
    val items: Color by ColorSetting("Items", default = Color.GRAY, true, description = "Background color of items").withDependency { themes == themesList.lastIndex }
    val line: Color by ColorSetting("Line", default = Color.BLACK, true, description = "Line Color (primarily separators).").withDependency { themes == themesList.lastIndex }
    val code: String by StringSetting("Code", default = "f", 1, description = "White Text Color Code (so white on white isn't bad).").withDependency { themes == themesList.lastIndex }
    val selected: Color by ColorSetting("Selected", default = Color.CYAN.withAlpha(0.8f), true, description = "Color for selected buttons.").withDependency { themes == themesList.lastIndex }
    val button: Color by ColorSetting("Button", default = Color.DARK_GRAY, description = "Color for buttons").withDependency { themes == themesList.lastIndex }

    val petsList: MutableList<String> by ListSetting("PETsS", description = "PETS", default = mutableListOf<String>())

    val themeEntries = listOf(
        Theme(
            "Classic",
            Color.DARK_GRAY,
            Color.BLUE,
            Color.WHITE,
            Color.GRAY,
            Color.WHITE,
            "f",
            Color.CYAN.withAlpha(0.8f),
            Color.DARK_GRAY,
        ),
        Theme(
            "Light",
            Color.WHITE,
            Color.DARK_GRAY,
            Color.BLACK,
            Color.DARK_GRAY,
            Color.BLACK,
            "0",
            Color.BLUE,
            Color.WHITE,
        )
    )

    inline val currentTheme get() = themeEntries.getOrNull(themes) ?: Theme(
        "Custom",
        main,
        accent,
        font,
        items,
        line,
        code,
        selected,
        button
    )

    override fun onKeybind() {
        mc.thePlayer?.name?.let { launchPV(it) }
        super.onKeybind()
    }

    override fun onEnable() {
        mc.thePlayer?.name?.let { launchPV(it) }
        super.onEnable()
        toggle()
    }
}

fun launchPV(name: String) {
    loadPlayer(name)
    screen = PVGui
    logger.info("Trying to display pvgui")
}

data class Theme(
    val name: String,
    val main: Color,
    val accent: Color,
    val font: Color,
    val items: Color,
    val line: Color,
    val fontCode: String,
    val selected: Color,
    val button: Color,
)