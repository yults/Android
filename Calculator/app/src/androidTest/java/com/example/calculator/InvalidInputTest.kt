package com.example.calculator


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.calculator.EspressoFunctions.Companion.clickOnView
import com.example.calculator.EspressoFunctions.Companion.match

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InvalidInputTest {
    private lateinit var expectedValue:String
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)
    @Before
    fun initialValidResult(){
        expectedValue = "1/0"
    }
    @Test
    fun checkDivisionByZero() {
        clickOnView(R.id.button1)
        clickOnView(R.id.div)
        clickOnView(R.id.button0)
        clickOnView(R.id.calc)
        match(R.id.input,expectedValue)
    }
}