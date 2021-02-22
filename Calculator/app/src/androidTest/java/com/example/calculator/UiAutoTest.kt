package com.example.calculator

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Test


class UiAutoTest {
    private var device: UiDevice? = null
    @Before
    fun startMainActivityFromHomeScreen() {
        device = UiDevice.getInstance(getInstrumentation())
        device?.pressHome()
        val launcherPackage = launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device?.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)
        val context: Context = getApplicationContext()
        val intent: Intent? = context.packageManager
            .getLaunchIntentForPackage(SAMPLE_PACKAGE)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        device?.wait(Until.hasObject(By.pkg(SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT)
    }

    @Test
    fun checkMainActivity() {
        val intSortButton = device!!.wait(Until.findObject(By.res(SAMPLE_PACKAGE, "button1")), 500)
        assertThat(intSortButton, `is`(notNullValue()))
    }

    private val launcherPackageName:String
        get() {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            val pm = getApplicationContext<Context>().packageManager
            val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
            return resolveInfo!!.activityInfo.packageName
        }

    companion object {
        const val SAMPLE_PACKAGE = "com.example.calculator"
        const val LAUNCH_TIMEOUT: Long = 5000
    }
}