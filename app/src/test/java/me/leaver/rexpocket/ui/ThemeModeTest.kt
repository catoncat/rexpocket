package me.leaver.rexpocket.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class ThemeModeTest {
    @Test
    fun nextCyclesSystemLightDark() {
        assertEquals(ThemeMode.Light, ThemeMode.System.next())
        assertEquals(ThemeMode.Dark, ThemeMode.Light.next())
        assertEquals(ThemeMode.System, ThemeMode.Dark.next())
    }
}
