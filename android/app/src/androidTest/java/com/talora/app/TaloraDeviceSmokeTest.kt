package com.talora.app

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaloraDeviceSmokeTest {
    private val instrumentation get() = InstrumentationRegistry.getInstrumentation()
    private val device get() = UiDevice.getInstance(instrumentation)
    private val targetContext get() = instrumentation.targetContext

    private fun launchApp() {
        val intent = targetContext.packageManager.getLaunchIntentForPackage(targetContext.packageName)
        assertNotNull("TALORA launch activity must be installed", intent)
        targetContext.startActivity(intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        assertTrue("TALORA home screen did not appear", device.wait(Until.hasObject(By.text("TALORA")), 15_000))
    }

    @Test
    fun installLaunchLifecycleAndAccessibleLabels() {
        launchApp()
        assertNotNull("Amharic Open control should be exposed to accessibility services", device.findObject(By.text("ክፈት")))
        assertNotNull("Amharic New script control should be exposed to accessibility services", device.findObject(By.text("አዲስ ጽሑፍ")))

        device.findObject(By.text("አዲስ ጽሑፍ")).click()
        assertTrue("Editor did not open", device.wait(Until.hasObject(By.text("ወደ ውጭ ላክ")), 10_000))

        val fields = device.findObjects(By.clazz("android.widget.EditText"))
        assertTrue("Editor should expose title and body fields", fields.size >= 2)
        fields[0].text = "TALORA Device Lifecycle"
        fields[1].text = "INT. TEST ROOM - DAY\nA device lifecycle smoke test."
        instrumentation.waitForIdleSync()
        Thread.sleep(1_500)

        device.executeShellCommand("am force-stop ${targetContext.packageName}")
        launchApp()
        assertTrue("Saved project did not survive process restart", device.wait(Until.hasObject(By.text("TALORA Device Lifecycle")), 10_000))
        device.findObject(By.text("TALORA Device Lifecycle")).click()
        assertTrue("Saved script body did not reload", device.wait(Until.hasObject(By.textContains("A device lifecycle smoke test.")), 10_000))

        val root = instrumentation.uiAutomation.rootInActiveWindow
        assertNotNull("Accessibility node tree must be available", root)
        assertTrue("TALORA heading should be visible to accessibility services",
            root!!.findAccessibilityNodeInfosByText("TALORA").isNotEmpty())
    }

    @Test
    fun safExportThenImportTaloraArchive() {
        launchApp()
        val newScript = device.findObject(By.text("አዲስ ጽሑፍ"))
        assertNotNull("New script action is missing", newScript)
        newScript!!.click()
        assertTrue("Editor did not open", device.wait(Until.hasObject(By.text("ወደ ውጭ ላክ")), 10_000))

        val fields = device.findObjects(By.clazz("android.widget.EditText"))
        assertTrue("Editor title field is missing", fields.isNotEmpty())
        fields[0].text = "TALORA SAF Roundtrip"
        if (fields.size > 1) fields[1].text = "SAF import export verification payload."
        device.waitForIdle()
        Thread.sleep(1_500)

        device.findObject(By.text("ወደ ውጭ ላክ")).click()
        val saveField = device.wait(Until.findObject(By.clazz("android.widget.EditText")), 10_000)
        assertNotNull("Android Create Document picker did not expose a filename field", saveField)
        saveField!!.text = "TALORA_Device_Roundtrip.talora"
        val saveButton = device.findObject(By.text("Save")) ?: device.findObject(By.desc("Save"))
        assertNotNull("Android Create Document picker Save action is missing", saveButton)
        saveButton!!.click()

        assertTrue("TALORA did not report successful SAF export",
            device.wait(Until.hasObject(By.text("የTALORA ፋይል ተቀምጧል።"), 15_000)))

        val openButton = device.findObject(By.text("ክፈት"))
        assertNotNull("Open action is missing after export", openButton)
        openButton!!.click()

        val rootsButton = device.findObject(By.desc("Show roots"))
        if (rootsButton != null) {
            rootsButton.click()
        }
        val downloads = device.wait(Until.findObject(By.text("Downloads")), 10_000)
        assertNotNull("Downloads location is missing from Android document picker", downloads)
        downloads!!.click()

        val archive = device.wait(Until.findObject(By.text("TALORA_Device_Roundtrip.talora")), 15_000)
        assertNotNull("Exported .talora archive was not visible to the import picker", archive)
        archive!!.click()

        assertTrue("TALORA did not report successful SAF import",
            device.wait(Until.hasObject(By.text("ፕሮጀክቱ ተጨምሯል።"), 15_000)))
        assertTrue("Imported project title is missing",
            device.wait(Until.hasObject(By.text("TALORA SAF Roundtrip")), 10_000))
    }
}
