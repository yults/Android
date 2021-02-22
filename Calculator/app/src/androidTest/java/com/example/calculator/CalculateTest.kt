package com.example.calculator

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.calculator.EspressoFunctions.Companion.clickOnView
import com.example.calculator.EspressoFunctions.Companion.match
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class CalculateTest {
    private lateinit var expectedValue: String

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initialValidResult() {
        expectedValue = "28" //12 - 8 / 4  + 3 * 6
    }

    @Test
    fun checkCalculateScenario() {
        clickOnView(R.id.button1)
        clickOnView(R.id.button2)
        clickOnView(R.id.substr)
        clickOnView(R.id.button8)
        clickOnView(R.id.div)
        clickOnView(R.id.button4)
        clickOnView(R.id.add)
        clickOnView(R.id.button3)
        clickOnView(R.id.mul)
        clickOnView(R.id.button6)
        clickOnView(R.id.calc)
        match(R.id.input, expectedValue)

    }
}