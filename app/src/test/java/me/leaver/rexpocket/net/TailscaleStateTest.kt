package me.leaver.rexpocket.net

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class TailscaleStateTest {
    @get:Rule
    val temp = TemporaryFolder()

    @Test
    fun hasTailscaleStateFileRequiresNonEmptyFile() {
        val dir = temp.newFolder("tailnet-empty")
        assertFalse(hasTailscaleStateFile(dir))

        val nested = File(dir, "sub").apply { mkdirs() }
        assertFalse(hasTailscaleStateFile(dir))

        val emptyFile = File(dir, "tailscaled.state").apply { writeBytes(byteArrayOf()) }
        assertFalse(hasTailscaleStateFile(dir))

        emptyFile.writeText("state")
        assertTrue(hasTailscaleStateFile(dir))
        assertFalse(hasTailscaleStateFile(nested))
    }

    @Test
    fun clearTailscaleStateDirRemovesAllFilesAndSubdirs() {
        val dir = temp.newFolder("tailnet-populated")
        File(dir, "tailscaled.state").writeText("secret-node-key")
        val nested = File(dir, "certs/nested").apply { mkdirs() }
        File(nested, "cert.pem").writeText("cert")

        assertTrue(clearTailscaleStateDir(dir))
        assertFalse(dir.exists())
        assertFalse(hasTailscaleStateFile(dir))
    }
}
